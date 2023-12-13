package go.kr.dsp.api.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import go.kr.dsp.api.app.command.DeployRecordEditService;
import go.kr.dsp.api.app.dto.DeployFileDto;
import go.kr.dsp.api.app.dto.DeployScriptDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.converter.stream.InputStreamCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
@Slf4j
@RequiredArgsConstructor
public class ResultProcessor implements Processor {

    private final DeployRecordEditService deployRecordEditService;

    @Value("${spring.config.activate.on-profile}")
    private String agentName;

    @Override
    public void process(Exchange exchange) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Object body = exchange.getMessage().getBody();
        String dataString = "";
        if (body instanceof InputStreamCache) {
            dataString = new String(((InputStreamCache) body).readAllBytes(), StandardCharsets.UTF_8);
        } else {
            dataString = body.toString();
        }
        Map map = mapper.readValue(dataString, Map.class);
        String logFile = map.get("logFile").toString();
        String fileSize = map.get("fileSize").toString();

        if (exchange.getMessage().getHeader("fileExtension", String.class).equals("sh")) {
            String result = map.get("request").toString();
            String execSh = map.get("execSh").toString();
            log.info("\n{}", result);
            DeployScriptDto build = DeployScriptDto.builder()
                .instCd(exchange.getMessage().getHeader("instName", String.class))
                .svcCd(exchange.getMessage().getHeader("serviceName", String.class))
                .seq(exchange.getMessage().getHeader("serverNum", String.class))
                .agentCd(exchange.getMessage().getHeader("agentName", String.class))
                .execSh(execSh)
                .resultSh(result)
                .build();
            deployRecordEditService.insertScriptRecord(DeployScriptDto.toEntity(build));
        } else {
            DeployFileDto build = DeployFileDto.builder()
                .instCd(exchange.getMessage().getHeader("instName", String.class))
                .svcCd(exchange.getMessage().getHeader("serviceName", String.class))
                .seq(exchange.getMessage().getHeader("serverNum", String.class))
                .agentCd(exchange.getMessage().getHeader("agentName", String.class))
                .fileName(exchange.getMessage().getHeader("fileName", String.class))
                .version(exchange.getMessage().getHeader("version", String.class))
                .filesize(fileSize)
                .build();
            deployRecordEditService.insertFileRecord(DeployFileDto.toEntity(build));
        }
        
        log.info("보낸 파일 크기: {}, 받은 파일 크기: {}", exchange.getMessage().getHeader("CamelFileLength", String.class), fileSize);
        if (!(exchange.getMessage().getHeader("CamelFileLength", String.class).equals(fileSize))) {
            log.info("파일 손상 됨");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = LocalDateTime.now().format(formatter);
        String fileName = exchange.getMessage().getHeader("instName", String.class) + "-" +
            exchange.getMessage().getHeader("serviceName", String.class) + "-" +
            exchange.getMessage().getHeader("serverNum") + "-" +
            exchange.getMessage().getHeader("agentName", String.class) + "-" +
            formattedDate + ".log";
        Path filePath = Path.of(agentName, fileName);
        try {
            Files.writeString(filePath, logFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }
}
