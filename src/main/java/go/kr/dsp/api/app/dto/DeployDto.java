package go.kr.dsp.api.app.dto;

import go.kr.dsp.api.app.domain.Deploy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeployDto {
  private String host;
  private String seq;
  private String service;
  private String inst;
  private String dir;

  public static DeployDto toDto(Deploy deploy){
    return DeployDto.builder()
      .host(deploy.getHost())
      .seq(deploy.getSeq())
      .service(deploy.getService())
      .inst(deploy.getInst())
      .dir(deploy.getDir())
      .build();
  }

}
