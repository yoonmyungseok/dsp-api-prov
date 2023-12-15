package go.kr.dsp.api.app.dto;

import go.kr.dsp.api.app.domain.DeployFile;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeployFileDto {
    private String instCd;
    private String svcCd;
    private String seq;
    private String agentCd;
    private String fileName;
    private String updt;
    private String version;
    private String filesize;

    public static DeployFileDto toDto(DeployFile deployFile) {
        return DeployFileDto.builder()
            .instCd(deployFile.getInstCd())
            .svcCd(deployFile.getSvcCd())
            .seq(deployFile.getSeq())
            .agentCd(deployFile.getAgentCd())
            .fileName(deployFile.getFileName())
            .updt(deployFile.getUpdt())
            .version(deployFile.getVersion())
            .filesize(deployFile.getFilesize())
            .build();
    }

    public static DeployFile toEntity(DeployFileDto dto) {
        return DeployFile.builder()
            .instCd(dto.getInstCd())
            .svcCd(dto.getSvcCd())
            .seq(dto.getSeq())
            .agentCd(dto.getAgentCd())
            .fileName(dto.getFileName())
            .updt(dto.getUpdt())
            .version(dto.getVersion())
            .filesize(dto.getFilesize())
            .build();
    }
}
