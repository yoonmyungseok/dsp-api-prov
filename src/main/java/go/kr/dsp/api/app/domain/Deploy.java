package go.kr.dsp.api.app.domain;

import go.kr.dsp.api.app.dto.DeployDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deploy {
  private String id;
  private String host;
  private String seq;
  private String service;
  private String inst;
  private String dir;

  public static Deploy toEntity(DeployDto dto){
    return Deploy.builder()
      .host(dto.getHost())
      .seq(dto.getSeq())
      .service(dto.getService())
      .inst(dto.getInst())
      .dir(dto.getDir())
      .build();
  }
}
