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
public class AgentFile {
    private String agtInstlSysId;   //에이전트설치시스템ID
    private Timestamp trsmDt;       //전송일시
    private String trsmFileNm;      //전송파일명
    private String trsmFileVerVl;   //전송파일버전값
}
