package hbi.core.hand.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hbi.core.hand.dto.OrgCompanys;

import java.util.List;

public interface IOrgCompanysService extends IBaseService<OrgCompanys>, ProxySelf<IOrgCompanysService>{

    List<OrgCompanys> getAll();


    List<OrgCompanys> getName(Long companyId);
}