package hbi.core.hand.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hbi.core.hand.dto.OmOrderLines;
import hbi.core.hand.mapper.OmOrderLinesMapper;
import hbi.core.hand.service.IOmOrderLinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OmOrderLinesServiceImpl extends BaseServiceImpl<OmOrderLines> implements IOmOrderLinesService{
    @Autowired
    OmOrderLinesMapper omOrderLinesMapper;


    @Override
    public List<OmOrderLines> selectmax(OmOrderLines dto) {
        return omOrderLinesMapper.selectmax(dto);
    }
}