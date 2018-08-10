package hbi.core.hand.service;

import com.hand.hap.core.IRequest;
import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hbi.core.hand.dto.OmOrderHeaders;

import java.util.List;

public interface IOmOrderHeadersService extends IBaseService<OmOrderHeaders>, ProxySelf<IOmOrderHeadersService>{

    boolean omHeaderDelete(List<OmOrderHeaders> dto);

    List<OmOrderHeaders> selectInfo(OmOrderHeaders log);

    List<OmOrderHeaders> findAll();

    List<OmOrderHeaders> printPDF(IRequest requestCtx, List<OmOrderHeaders> dto);
}