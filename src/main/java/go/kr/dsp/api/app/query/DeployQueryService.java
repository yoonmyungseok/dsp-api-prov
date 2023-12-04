package go.kr.dsp.api.app.query;

import go.kr.dsp.api.app.dto.RequestDto;
import go.kr.dsp.api.app.dto.ResponseDto;
import go.kr.dsp.api.app.repository.DeployRepository;
import go.kr.dsp.api.exception.DspException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeployQueryService {
  private final DeployRepository deployRepository;
  public List<ResponseDto> selectServiceList(RequestDto requestDto) throws DspException{
    return null;
  }
}
