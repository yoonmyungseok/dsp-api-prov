package go.kr.dsp.api.app.repository;

import go.kr.dsp.api.app.domain.Deploy;
import go.kr.dsp.api.app.dto.RequestDto;
import go.kr.dsp.api.exception.DspException;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeployRepository {
  List<Deploy> selectServiceList(RequestDto requestDto) throws DspException;
}
