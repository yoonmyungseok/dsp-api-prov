package go.kr.dsp.api.app.dto;

import go.kr.dsp.api.app.domain.DeployScript;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeployScriptDto {
    private String instCd;
    private String svcCd;
    private String seq;
    private String agentCd;
    private String updt;
    private String execSh;
    private String resultSh;

    public static DeployScriptDto toDto(DeployScript deployScript) {
        return DeployScriptDto.builder()
            .instCd(deployScript.getInstCd())
            .svcCd(deployScript.getSvcCd())
            .seq(deployScript.getSeq())
            .agentCd(deployScript.getAgentCd())
            .updt(deployScript.getUpdt())
            .execSh(deployScript.getExecSh())
            .resultSh(deployScript.getResultSh())
            .build();
    }

    public static DeployScript toEntity(DeployScriptDto dto) {
        return DeployScript.builder()
            .instCd(dto.getInstCd())
            .svcCd(dto.getSvcCd())
            .seq(dto.getSeq())
            .agentCd(dto.getAgentCd())
            .updt(dto.getUpdt())
            .execSh(dto.getExecSh())
            .resultSh(dto.getResultSh())
            .build();
    }
}
