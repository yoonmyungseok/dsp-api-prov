package go.kr.dsp.api.app.dto;

import go.kr.dsp.api.app.domain.AgentScript;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentScriptDto {
    private String agtInstlSysId;   //에이전트설치시스템ID
    private Timestamp excnDt;       //실행일시
    private String scrpCn;          //스크립트내용
    private String resultMsgVl;     //결과메시지값
    
    public static AgentScriptDto toDto(AgentScript agentScript){
        return AgentScriptDto.builder()
            .agtInstlSysId(agentScript.getAgtInstlSysId())
            .scrpCn(agentScript.getScrpCn())
            .resultMsgVl(agentScript.getResultMsgVl())
            .build();
    }
    
    public static AgentScript toEntity(AgentScriptDto agentScriptDto){
        return AgentScript.builder()
            .agtInstlSysId(agentScriptDto.getAgtInstlSysId())
            .scrpCn(agentScriptDto.getScrpCn())
            .resultMsgVl(agentScriptDto.getResultMsgVl())
            .build();
    }
}
