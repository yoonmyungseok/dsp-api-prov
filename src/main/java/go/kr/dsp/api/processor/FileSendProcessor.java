package go.kr.dsp.api.processor;

import go.kr.dsp.api.app.dto.AgentAddrDto;
import go.kr.dsp.api.app.query.AgentQueryService;
import go.kr.dsp.api.exception.DspException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
@Slf4j
@RequiredArgsConstructor
public class FileSendProcessor implements Processor {
    private String agtInstlSysId;   //에이전트설치시스템ID
    private String srvcInstCd;      //서비스기관코드
    private String srvcCd;          //서비스코드
    private String sysIpAddr;       //시스템IP주소
    private String sysPortNo;       //시스템포트번호
    private String sysNm;           //시스템명
    private String agtInstlLocSeCd; //에이전트설치장소구분코드
    private String agtSeCd;         //에이전트구분코드
    
    private final AgentQueryService agentQueryService;
    
    @Override
    public void process(Exchange exchange) throws Exception {
        //파일명 추출
        String fileName = exchange.getMessage().getHeader("CamelFileName", String.class);
        String[] split = fileName.split("-");
        
        //확장자 추출
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
        
        //버전 추출
        String version = "";
        if (!fileExtension.equalsIgnoreCase("sh")) {
            Pattern pattern = Pattern.compile("-([\\d.]+)\\.[^.]+$");
            Matcher matcher = pattern.matcher(fileName);
            if (matcher.find()) {
                version = matcher.group(1); // Extracts the version number
            }
        }
        
        
        
        //에이전트설치시스템ID
        try {
            agtInstlSysId = split[0];
            //파일명 검증
            if (!(fileName.startsWith("ASID") && agtInstlSysId.length() == 20)) {
                throw new DspException("파일명 잘못됨");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new DspException("파일명 잘못됨");
        }
        
        //DB에서 기관 시스템 정보 추출
        AgentAddrDto agentAddr = agentQueryService.getAgentAddr(agtInstlSysId);
        sysIpAddr = agentAddr.getSysIpAddr();
        sysPortNo = agentAddr.getSysPortNo();
        
        //Header에 추가
        exchange.getMessage().setHeader("agtInstlSysId", agtInstlSysId);
        exchange.getMessage().setHeader("srvcInstCd", agentAddr.getSrvcInstCd());
        exchange.getMessage().setHeader("srvcCd", agentAddr.getSrvcCd());
        exchange.getMessage().setHeader("sysIpAddr", sysIpAddr);
        exchange.getMessage().setHeader("sysPortNo", sysPortNo);
        exchange.getMessage().setHeader("sysNm", agentAddr.getSysNm());
        exchange.getMessage().setHeader("agtInstlLocSeCd", agentAddr.getAgtInstlLocSeCd());
        exchange.getMessage().setHeader("agtSeCd", agentAddr.getAgtSeCd());
        exchange.getMessage().setHeader("fileExtension", fileExtension);
        exchange.getMessage().setHeader("version", version);
        exchange.getMessage().setHeader("fileName", fileName);
        
        //주소 설정
        exchange.setProperty("url", "http://" + sysIpAddr + ":" + sysPortNo + "/file?socketTimeout=30000&bridgeEndpoint=true");
//        exchange.setProperty("url", "https://"+sysIpAddr+ ":" + sysPortNo + "/file?socketTimeout=30000&bridgeEndpoint=true");
    
    }
}
