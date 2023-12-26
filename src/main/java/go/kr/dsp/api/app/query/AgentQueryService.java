package go.kr.dsp.api.app.query;

import go.kr.dsp.api.app.domain.AgentAddr;
import go.kr.dsp.api.app.dto.AgentAddrDto;
import go.kr.dsp.api.app.repository.AgentRepository;
import go.kr.dsp.api.exception.DspException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AgentQueryService {
    private final AgentRepository agentRepository;
    
    public AgentAddrDto getAgentAddr(String agtInstlSysId) throws DspException {
        return AgentAddrDto.toDto(agentRepository.getAgentAddr(agtInstlSysId));
    }
}
