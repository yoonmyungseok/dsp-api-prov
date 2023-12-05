package go.kr.dsp.api.app.query;

import go.kr.dsp.api.app.domain.Deploy;
import go.kr.dsp.api.app.dto.DeployDto;
import go.kr.dsp.api.app.repository.DeployRepository;
import go.kr.dsp.api.exception.DspException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeployQueryService {
  private final DeployRepository deployRepository;
  public DeployDto selectService(DeployDto deployDto) throws DspException{
    return DeployDto.toDto(deployRepository.selectService(Deploy.toEntity(deployDto)));
  }
}
