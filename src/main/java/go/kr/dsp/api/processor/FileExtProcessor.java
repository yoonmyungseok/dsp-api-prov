package go.kr.dsp.api.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FileExtProcessor implements Processor {

  @Override
  public void process(Exchange exchange) throws Exception {
    String fileName = exchange.getMessage().getHeader("CamelFileName", String.class);
    String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
    String[] split=fileName.split("-");

    double fileSizeInMB=Math.round(convertBytesToMegabytes(exchange.getMessage().getHeader("CamelFileLength", long.class))*100)/100.0;

    exchange.getMessage().setHeader("fileExtension",fileExtension);//확장자
    exchange.getMessage().setHeader("fileName",fileName);//파일 명
    exchange.getMessage().setHeader("serviceName",split[0]);//서비스 명
    exchange.getMessage().setHeader("agentName",split[1].toLowerCase());//에이전트 구분(I/F, Deploy)
    log.info("파일 크기: {} mb",fileSizeInMB);
  }

  private double convertBytesToMegabytes(long bytes) {
    return bytes / 1024.0;
  }
}
