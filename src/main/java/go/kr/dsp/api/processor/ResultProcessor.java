package go.kr.dsp.api.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import go.kr.dsp.api.app.command.AgentEditService;
import go.kr.dsp.api.app.dto.AgentFileDto;
import go.kr.dsp.api.app.dto.AgentScriptDto;
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


@Component
@Slf4j
@RequiredArgsConstructor
public class ResultProcessor implements Processor {
    
    private final AgentEditService agentEditService;
    
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
        String agtInstlSysId = exchange.getMessage().getHeader("agtInstlSysId", String.class);
        
        if (exchange.getMessage().getHeader("fileExtension", String.class).equals("sh")) {
            String resultMsgVl = map.get("request").toString();
            String scrpCn = map.get("execSh").toString();
            log.info("\n{}", resultMsgVl);
            
            AgentScriptDto agentScriptDto = AgentScriptDto.builder()
                .agtInstlSysId(agtInstlSysId)
                .scrpCn(scrpCn)
                .resultMsgVl(resultMsgVl)
                .build();
            
            agentEditService.insertAgentScript(agentScriptDto);
        } else {
            AgentFileDto agentFileDto = AgentFileDto.builder()
                .agtInstlSysId(agtInstlSysId)
                .trsmFileNm(exchange.getMessage().getHeader("fileName", String.class))
                .trsmFileVerVl(exchange.getMessage().getHeader("version", String.class))
                .build();
            agentEditService.insertAgentFile(agentFileDto);
        }
        
        log.info("보낸 파일 크기: {}, 받은 파일 크기: {}", exchange.getMessage().getHeader("CamelFileLength", String.class), fileSize);
        if (!(exchange.getMessage().getHeader("CamelFileLength", String.class).equals(fileSize))) {
            log.info("파일 손상 됨");
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = LocalDateTime.now().format(formatter);
        String fileName = agtInstlSysId + "_" + formattedDate + ".log";
        Path filePath = Path.of(agentName, fileName);
        try {
            Files.writeString(filePath, logFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }
}
