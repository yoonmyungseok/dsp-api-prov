package go.kr.dsp.api.app.dto;


import go.kr.dsp.api.app.domain.AgentFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentFileDto {
    private String agtInstlSysId;   //에이전트설치시스템ID
    private Timestamp trsmDt;       //전송일시
    private String trsmFileNm;      //전송파일명
    private String trsmFileVerVl;   //전송파일버전값
    
    public static AgentFileDto toDto(AgentFile agentFile){
        return AgentFileDto.builder()
            .agtInstlSysId(agentFile.getAgtInstlSysId())
            .trsmFileNm(agentFile.getTrsmFileNm())
            .trsmFileVerVl(agentFile.getTrsmFileVerVl())
            .build();
    }
    
    public static AgentFile toEntity(AgentFileDto agentFileDto){
        return AgentFile.builder()
            .agtInstlSysId(agentFileDto.getAgtInstlSysId())
            .trsmFileNm(agentFileDto.getTrsmFileNm())
            .trsmFileVerVl(agentFileDto.getTrsmFileVerVl())
            .build();
    }
}
