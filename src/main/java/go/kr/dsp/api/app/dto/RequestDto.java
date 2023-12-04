package go.kr.dsp.api.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDto {
  private String host;
  private Long seq;
  private String service;
  private String inst;
  private String dir;
}
