package hbi.core.hand.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import hbi.core.hand.mapper.ArCustomersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hbi.core.hand.dto.ArCustomers;
import hbi.core.hand.service.IArCustomersService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ArCustomersServiceImpl extends BaseServiceImpl<ArCustomers> implements IArCustomersService{
    @Autowired
    ArCustomersMapper arCustomersMapper;

    @Override
    public List<ArCustomers> getName(Long customerId) {
        return arCustomersMapper.getName(customerId);
    }
}