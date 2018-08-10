package hbi.core.hand.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hbi.core.hand.dto.OrgCompanys;

import java.util.List;

public interface OrgCompanysMapper extends Mapper<OrgCompanys>{

    List<OrgCompanys> getAll();

    List<OrgCompanys> getName(Long companyId);
}