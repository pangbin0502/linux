package hbi.core.hand.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hbi.core.hand.mapper.OrgCompanysMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hbi.core.hand.dto.OrgCompanys;
import hbi.core.hand.service.IOrgCompanysService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrgCompanysServiceImpl extends BaseServiceImpl<OrgCompanys> implements IOrgCompanysService{
    @Autowired
    OrgCompanysMapper orgCompanysMapper;

    @Override
    public List<OrgCompanys> getAll() {
        return orgCompanysMapper.getAll();
    }

    @Override
    public List<OrgCompanys> getName(Long companyId) {
        return orgCompanysMapper.getName(companyId);
    }
}