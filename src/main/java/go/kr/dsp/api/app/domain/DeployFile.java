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
public class DeployFile {
    private String id;
    private String instCd;
    private String svcCd;
    private String seq;
    private String agentCd;
    private String fileName;
    private String updt;
    private String version;
    private String filesize;
}
