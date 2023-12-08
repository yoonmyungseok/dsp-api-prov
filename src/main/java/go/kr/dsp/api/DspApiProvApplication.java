package go.kr.dsp.api;

import org.apache.camel.opentelemetry.starter.CamelOpenTelemetry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@CamelOpenTelemetry
public class DspApiProvApplication{

  public static void main(String[] args) {
    SpringApplication.run(DspApiProvApplication.class, args);
  }

}
