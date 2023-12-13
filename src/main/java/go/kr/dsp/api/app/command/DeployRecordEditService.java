package go.kr.dsp.api.app.command;

import go.kr.dsp.api.app.domain.DeployFile;
import go.kr.dsp.api.app.domain.DeployScript;
import go.kr.dsp.api.app.repository.DeployRepository;
import go.kr.dsp.api.exception.DspException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class DeployRecordEditService {
    private final DeployRepository deployRepository;

    public void insertScriptRecord(DeployScript entity) throws DspException {
        deployRepository.insertScriptRecord(entity);
    }

    public void insertFileRecord(DeployFile entity) throws DspException {
        deployRepository.insertFileRecord(entity);
    }
}
