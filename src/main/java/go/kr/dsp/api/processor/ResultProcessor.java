package go.kr.dsp.api.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ResultProcessor implements Processor {

  @Value("${spring.config.activate.on-profile}")
  private String agentName;
  @Override
  public void process(Exchange exchange) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    Object body = exchange.getMessage().getBody();
    String dataString = "";
    if(body instanceof InputStreamCache){
      dataString = new String(((InputStreamCache) body).readAllBytes(), StandardCharsets.UTF_8);
    }else{
      dataString = body.toString();
    }
    Map map = mapper.readValue(dataString, Map.class);
    if(exchange.getMessage().getHeader("fileExtension",String.class).equals("sh")){
      String result = map.get("request").toString();
      log.info(result);
    }
    String logFile = map.get("logFile").toString();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String formattedDate = LocalDateTime.now().format(formatter);
    String fileName=agentName+"_log_"+formattedDate+".txt";
    Path filePath=Path.of(agentName,fileName);
    try {
      Files.writeString(filePath,logFile, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }catch (IOException e){
      log.info(e.getMessage());
    }
  }
}
