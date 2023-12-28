package go.kr.dsp.api.app.command;

import go.kr.dsp.api.app.dto.AgentFileDto;
import go.kr.dsp.api.app.dto.AgentScriptDto;
import go.kr.dsp.api.app.repository.AgentRepository;
import go.kr.dsp.api.exception.DspException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AgentEditService {
    private final AgentRepository agentRepository;
    
    public void insertAgentScript(AgentScriptDto agentScriptDto) throws DspException {
        agentRepository.insertAgentScript(AgentScriptDto.toEntity(agentScriptDto));
    }
    
    public void insertAgentFile(AgentFileDto agentFileDto) throws DspException {
        agentRepository.insertAgentFile(AgentFileDto.toEntity(agentFileDto));
    }
}
