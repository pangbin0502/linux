package hbi.core.hand.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hbi.core.hand.dto.ArCustomers;

import java.util.List;

public interface IArCustomersService extends IBaseService<ArCustomers>, ProxySelf<IArCustomersService>{

    List<ArCustomers> getName(Long customerId);

}