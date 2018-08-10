package hbi.core.hand.mapper;

import com.hand.hap.mybatis.common.Mapper;
import hbi.core.hand.dto.ArCustomers;

import java.util.List;

public interface ArCustomersMapper extends Mapper<ArCustomers>{

    List<ArCustomers> getName(Long customerId);
}