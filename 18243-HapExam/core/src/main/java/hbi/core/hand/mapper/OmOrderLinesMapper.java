package hbi.core.hand.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hbi.core.hand.dto.OmOrderHeaders;
import hbi.core.hand.dto.OmOrderLines;

import java.util.List;

public interface OmOrderLinesMapper extends Mapper<OmOrderLines>{


    void deleteLinesByHeaderId(OmOrderLines omOrderLines);

    List<OmOrderLines> selectmax(OmOrderLines dto);

    //List<OmOrderLines> findAll();

    List<OmOrderLines> findAll(Long headerId);

    List<OmOrderLines> selectSumPrice(OmOrderHeaders notice);
}