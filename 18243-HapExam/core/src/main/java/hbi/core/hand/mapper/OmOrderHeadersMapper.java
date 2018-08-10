package hbi.core.hand.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hbi.core.hand.dto.OmOrderHeaders;

import java.util.List;

public interface OmOrderHeadersMapper extends Mapper<OmOrderHeaders>{

    void omHeaderDelete(List<OmOrderHeaders> dto);

    List<OmOrderHeaders> findAll();
}