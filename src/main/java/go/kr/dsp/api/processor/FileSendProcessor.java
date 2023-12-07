package go.kr.dsp.api.processor;

import com.fasterxml.jackson.databind.json.JsonMapper;
import go.kr.dsp.api.app.dto.DeployDto;
import go.kr.dsp.api.app.query.DeployQueryService;
import go.kr.dsp.api.app.service.ServerConnection;
import go.kr.dsp.api.exception.DspException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.Processor;
import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileSendProcessor implements Processor {
  private final FluentProducerTemplate producerTemplate;
  private final DeployQueryService deployQueryService;
  private final ServerConnection serverConnection;

  @Override
  public void process(Exchange exchange) throws Exception {
    String fileName = exchange.getMessage().getHeader("CamelFileName", String.class);
    String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
    String[] split=exchange.getIn().getHeader("CamelFileName", String.class).split("-");

    DeployDto deployDto = deployQueryService.selectService(DeployDto.builder().inst(split[0]).service(split[1]).seq(split[2]).build());

    log.info("주소: {}",deployDto.getHost());
    log.info("서버번호: {}",split[2]);
    log.info("서비스코드: {}",split[1]);
    log.info("기관코드: {}",split[0]);
    log.info("에이전트: {}",split[3]);


    if(serverConnection.isServerConnected("http://"+deployDto.getHost()+"/file?connectTimeout=10000")){
      log.info("연결 성공");
      exchange.getMessage().setHeader("Host",deployDto.getHost());
      exchange.getMessage().setHeader("fileExtension",fileExtension);
      exchange.getMessage().setHeader(Exchange.HTTP_METHOD,"POST");
    }else{
      throw new DspException("연결 실패");
    }
  }
}
