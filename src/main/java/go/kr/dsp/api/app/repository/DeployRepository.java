package go.kr.dsp.api.app.repository;

import go.kr.dsp.api.app.domain.Deploy;
import go.kr.dsp.api.exception.DspException;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface DeployRepository {
  Deploy selectService(Deploy deploy) throws DspException;
}