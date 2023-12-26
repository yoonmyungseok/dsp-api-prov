package go.kr.dsp.api.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentAddr {
    private String agtInstlSysId;   //에이전트설치시스템ID
    private String srvcInstCd;      //서비스기관코드
    private String srvcCd;          //서비스코드
    private String sysIpAddr;       //시스템IP주소
    private String sysPortNo;       //시스템포트번호
    private String sysNm;           //시스템명
    private String agtInstlLocSeCd; //에이전트설치장소구분코드
    private String agtSeCd;         //에이전트구분코드
}
