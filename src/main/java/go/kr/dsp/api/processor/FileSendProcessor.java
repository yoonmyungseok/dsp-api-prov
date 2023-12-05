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
    String[] split=exchange.getIn().getHeader("CamelFileName", String.class).split("-");

    DeployDto deployDto = deployQueryService.selectService(DeployDto.builder().inst(split[0]).service(split[1]).seq(split[2]).build());

    log.info("주소: {}",deployDto.getHost());
    log.info("서버번호: {}",split[2]);
    log.info("서비스코드: {}",split[1]);
    log.info("기관코드: {}",split[0]);
    log.info("에이전트: {}",split[3]);

    if(isServerConnected("http://"+deployDto.getHost()+"/file?connectTimeout=10000")){
      log.info("연결 성공");
//      Exchange sendExchange = producerTemplate.withExchange(exchange).to("http://"+deployDto.getHost()+"/file?socketTimeout=10000").send();
      String request = producerTemplate.withBody(exchange.getMessage().getBody())
        .withHeaders(exchange.getMessage().getHeaders())
        .to("http://" + deployDto.getHost() + "/file?socketTimeout=10000")
        .request(String.class);
      System.out.println(request);
    }else{
      throw new DspException("연결 실패");
    }


//    String filePath = "yaml/host.yaml";
//    try (InputStream input = new FileInputStream(filePath)) {
//      Yaml yaml = new Yaml();
//      Map<String, Map<String, Object>> yamlMap = yaml.load(input);
//      List<String> urls=(List<String>) yamlMap.get(split[0]).get("url");
//      for(String url: urls){
//        Exchange sendExchange = producerTemplate.withExchange(exchange).to(url+"/file?connectTimeout=10000").send();
//        exchange.getMessage().setBody(sendExchange.getMessage().getBody());
//        exchange.getMessage().setHeaders(sendExchange.getMessage().getHeaders());
//        log.info("====url===="+url);
//      }
//    } catch (IOException e) {
//      log.error("에러: "+e.getMessage());
//    }
  }
}
