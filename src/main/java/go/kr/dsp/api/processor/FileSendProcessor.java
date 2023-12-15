package go.kr.dsp.api.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import go.kr.dsp.api.app.dto.DeployDto;
import go.kr.dsp.api.app.dto.DeployScriptDto;
import go.kr.dsp.api.app.query.DeployQueryService;
import go.kr.dsp.api.exception.DspException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.FluentProducerTemplate;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Component
@Slf4j
@RequiredArgsConstructor
public class FileSendProcessor implements Processor {
    @Value("${agent.deploy.port.camel}")
    private String deployCamelPort;
    @Value("${agent.deploy.port.server}")
    private String deployServerPort;
    @Value("${agent.if.port.camel}")
    private String ifCamelPort;
    @Value("${agent.if.port.server}")
    private String ifServerPort;

    private final DeployQueryService deployQueryService;
    private final FluentProducerTemplate fluentProducerTemplate;
//  RestTemplate restTemplate = new RestTemplate();

    @Override
    public void process(Exchange exchange) throws Exception {
        String fileName = exchange.getMessage().getHeader("CamelFileName", String.class);
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
        String[] split = fileName.split("-");

        DeployDto deployDto = deployQueryService.selectService(DeployDto.builder().inst(split[0]).service(split[1]).seq(split[2]).build());

        log.info("주소: {}", deployDto.getHost());

        String port = split[3].equals("deploy") ? deployCamelPort : ifCamelPort;
        exchange.setProperty("url", deployDto.getHost() + ":" + port + "/file?socketTimeout=30000&bridgeEndpoint=true");

//    String healthCheckPort=split[3].equals("deploy")?deployServerPort:ifServerPort;
//
//    String forObject=restTemplate.getForObject(deployDto.getHost()+":"+healthCheckPort+"/actuator/health", String.class);
//    ObjectMapper objectMapper=new ObjectMapper();
//    Map map=objectMapper.readValue(forObject,Map.class);

//    if(map.get("status").equals("UP")){
//      log.info("연결 성공");
//      exchange.getMessage().setHeader("Host",deployDto.getHost());
//      exchange.getMessage().setHeader("fileExtension",fileExtension);
//      exchange.getMessage().setHeader(Exchange.HTTP_METHOD,"POST");
//      exchange.setProperty("url",deployDto.getHost()+":");
//    }else{
//      throw new DspException("연결 실패");
//    }
    }
}
