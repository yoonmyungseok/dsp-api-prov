package go.kr.dsp.api.processor;

import go.kr.dsp.api.app.dto.DeployDto;
import go.kr.dsp.api.app.query.DeployQueryService;
import go.kr.dsp.api.exception.DspException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileSendProcessor implements Processor {
  private final FluentProducerTemplate producerTemplate;
  private final DeployQueryService deployQueryService;

  private static boolean isServerConnected(String urlString) {
    try {
      URL url = new URL(urlString);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("HEAD");
      connection.connect();

      int responseCode = connection.getResponseCode();
      if (responseCode >= 200 && responseCode < 400) {
        // 2xx and 3xx codes indicate successful responses
        log.info("응답 코드: {}",responseCode);
        return true;
      }
    } catch (Exception e) {
      log.error("연결 에러: {}",e.getMessage());

    }
    return false;
  }

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

    //헤더에 추가해야 할 듯

    if(isServerConnected("http://"+deployDto.getHost()+"/file?connectTimeout=10000")){
      log.info("연결 성공");
      String request = producerTemplate.withBody(exchange.getMessage().getBody())
        .withHeader("serviceName",split[1])
        .withHeader("fileExtension",fileExtension)
        .withHeader("fileName",fileName)
        .withHeader("agentName",split[3])
        .toF("http://" + deployDto.getHost() + "/file?socketTimeout=10000")
        .request(String.class);
      log.info(request);
    }else{
      throw new DspException("연결 실패");
    }
  }
}
