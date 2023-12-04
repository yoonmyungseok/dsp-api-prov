package go.kr.dsp.api.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deploy {
  private String id;
  private String host;
  private Long seq;
  private String service;
  private String inst;
  private String dir;
}
