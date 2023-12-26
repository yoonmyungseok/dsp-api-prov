package go.kr.dsp.api.app.repository;

import go.kr.dsp.api.app.domain.AgentAddr;
import go.kr.dsp.api.app.domain.AgentFile;
import go.kr.dsp.api.app.domain.AgentScript;
import go.kr.dsp.api.exception.DspException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AgentRepository {
    
    AgentAddr getAgentAddr(String agtInstlSysId) throws DspException;
    
    void insertAgentFile(AgentFile agentFile) throws DspException;
    
    void insertAgentScript(AgentScript agentScript) throws DspException;
}
