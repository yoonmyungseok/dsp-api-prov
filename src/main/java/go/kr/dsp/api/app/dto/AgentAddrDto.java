package go.kr.dsp.api.app.dto;

import go.kr.dsp.api.app.domain.AgentAddr;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentAddrDto {
    private String agtInstlSysId;   //에이전트설치시스템ID
    private String srvcInstCd;      //서비스기관코드
    private String srvcCd;          //서비스코드
    private String sysIpAddr;       //시스템IP주소
    private String sysPortNo;       //시스템포트번호
    private String sysNm;           //시스템명
    private String agtInstlLocSeCd; //에이전트설치장소구분코드
    private String agtSeCd;         //에이전트구분코드
    
    public static AgentAddrDto toDto(AgentAddr agentAddr){
        return AgentAddrDto.builder()
            .agtInstlSysId(agentAddr.getAgtInstlSysId())
            .srvcInstCd(agentAddr.getSrvcInstCd())
            .srvcCd(agentAddr.getSrvcCd())
            .sysIpAddr(agentAddr.getSysIpAddr())
            .sysPortNo(agentAddr.getSysPortNo())
            .sysNm(agentAddr.getSysNm())
            .agtInstlLocSeCd(agentAddr.getAgtInstlLocSeCd())
            .agtSeCd(agentAddr.getAgtSeCd())
            .build();
    }
    
    public static AgentAddr toEntity(AgentAddrDto agentAddrDto){
        return AgentAddr.builder()
            .agtInstlSysId(agentAddrDto.getAgtInstlSysId())
            .srvcInstCd(agentAddrDto.getSrvcInstCd())
            .srvcCd(agentAddrDto.getSrvcCd())
            .sysIpAddr(agentAddrDto.getSysIpAddr())
            .sysPortNo(agentAddrDto.getSysPortNo())
            .sysNm(agentAddrDto.getSysNm())
            .agtInstlLocSeCd(agentAddrDto.getAgtInstlLocSeCd())
            .agtSeCd(agentAddrDto.getAgtSeCd())
            .build();
    }
}
