package go.kr.dsp.api.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class FileSendProcessor implements Processor {
  private final FluentProducerTemplate producerTemplate;
  public FileSendProcessor(FluentProducerTemplate producerTemplate){
    this.producerTemplate=producerTemplate;
  }
  @Override
  public void process(Exchange exchange) throws Exception {
    String[] split=exchange.getIn().getHeader("CamelFileName", String.class).split("-");
    String filePath = "yaml/host.yaml";
    try (InputStream input = new FileInputStream(filePath)) {
      Yaml yaml = new Yaml();
      Map<String, Map<String, Object>> yamlMap = yaml.load(input);
      List<String> urls=(List<String>) yamlMap.get(split[0]).get("url");
      for(String url: urls){
        Exchange sendExchange = producerTemplate.withExchange(exchange).to(url+"/file?connectTimeout=10000").send();
        exchange.getMessage().setBody(sendExchange.getMessage().getBody());
        exchange.getMessage().setHeaders(sendExchange.getMessage().getHeaders());
        log.info("====url===="+url);
      }
    } catch (IOException e) {
      log.error("에러: "+e.getMessage());
    }
  }
}
