package go.kr.dsp.api.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeployScript {
    private String id;
    private String instCd;
    private String svcCd;
    private String seq;
    private String agentCd;
    private String updt;
    private String execSh;
    private String resultSh;

}
