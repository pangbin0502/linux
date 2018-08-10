package hbi.core.hand.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import hbi.core.hand.dto.InvInventoryItems;
import hbi.core.hand.service.IInvInventoryItemsService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class InvInventoryItemsServiceImpl extends BaseServiceImpl<InvInventoryItems> implements IInvInventoryItemsService{

}