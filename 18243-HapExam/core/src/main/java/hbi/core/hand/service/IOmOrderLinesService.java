package hbi.core.hand.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hbi.core.hand.dto.OmOrderLines;

import java.util.List;

public interface IOmOrderLinesService extends IBaseService<OmOrderLines>, ProxySelf<IOmOrderLinesService>{

    List<OmOrderLines> selectmax(OmOrderLines dto);
}