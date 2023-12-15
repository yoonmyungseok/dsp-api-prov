package go.kr.dsp.api.processor;

import go.kr.dsp.api.exception.DspException;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
@Slf4j
public class FileExtProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String fileName = exchange.getMessage().getHeader("CamelFileName", String.class);
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
        String[] split = fileName.split("-");
        String inst = "";
        String service = "";
        String seq = "";
        String agent = "";

        try {
            inst = split[0];
            service = split[1];
            seq = split[2];
            agent = split[3];
        } catch (IndexOutOfBoundsException e) {
            throw new DspException("파일명 잘못됨");
        }

        //버전 추출
        Pattern pattern = Pattern.compile("-([\\d.]+)\\.[^.]+$");
        Matcher matcher = pattern.matcher(fileName);
        String version = "";
        if (matcher.find()) {
            version = matcher.group(1); // Extracts the version number
        } else {
            throw new DspException("버전명 잘못됨");
        }

        /**
         * I0000001-S00001-1-IF-script-template-1.0.0.yaml
         * 기관코드-서비스코드-서버번호-에이전트-???-버전.확장자
         */

        if (!(inst.startsWith("I") && inst.length() == 8)) {
            throw new DspException("기관코드 잘못됨");
        } else if (!(service.startsWith("S") && service.length() == 6)) {
            throw new DspException("서비스코드 잘못됨");
        } else if (!(seq.length() <= 2 && Integer.parseInt(seq) >= 1 && Integer.parseInt(seq) <= 99)) {
            throw new DspException("서버번호 잘못됨");
        } else if (!(agent.equalsIgnoreCase("if") || agent.equalsIgnoreCase("deploy"))) {
            throw new DspException("에이전트 잘못됨");
        }

//    double fileSizeInMB=Math.round(convertBytesToMegabytes(exchange.getMessage().getHeader("CamelFileLength", long.class))*100)/100.0;

        exchange.getMessage().setHeader("serviceName", service);//서비스 코드
        exchange.getMessage().setHeader("instName", inst);// 기관 코드
        exchange.getMessage().setHeader("fileExtension", fileExtension);//확장자
        exchange.getMessage().setHeader("fileName", fileName);//파일 명
        exchange.getMessage().setHeader("serverNum", seq); //서버 번호
        exchange.getMessage().setHeader("version", version); //버전
        exchange.getMessage().setHeader("agentName", agent.toLowerCase());//에이전트 구분(I/F, Deploy)
//    log.info("파일 크기: {} mb",fileSizeInMB);
    }

    private double convertBytesToMegabytes(long bytes) {
        return bytes / 1024.0;
    }
}
