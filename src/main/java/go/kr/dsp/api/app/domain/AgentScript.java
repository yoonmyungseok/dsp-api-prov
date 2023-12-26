package go.kr.dsp.api.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentScript {
    private String agtInstlSysId;   //에이전트설치시스템ID
    private Timestamp excnDt;       //실행일시
    private String scrpCn;          //스크립트내용
    private String resultMsgVl;     //결과메시지값
}
