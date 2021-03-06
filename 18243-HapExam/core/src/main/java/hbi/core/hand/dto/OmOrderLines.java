package hbi.core.hand.dto;

/**Auto Generated By Hap Code Generator**/

import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@ExtensionAttribute(disable=true)
@Table(name = "hap_om_order_lines")
public class OmOrderLines extends BaseDTO {

     public static final String FIELD_LINE_ID = "lineId";
     public static final String FIELD_HEADER_ID = "headerId";
     public static final String FIELD_LINE_NUMBER = "lineNumber";
     public static final String FIELD_INVENTORY_ITEM_ID = "inventoryItemId";
     public static final String FIELD_ORDERD_QUANTITY = "orderdQuantity";
     public static final String FIELD_ORDER_QUANTITY_UOM = "orderQuantityUom";
     public static final String FIELD_UNIT_SELLING_PRICE = "unitSellingPrice";
     public static final String FIELD_DESCRIPTION = "description";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_ADDITION1 = "addition1";
     public static final String FIELD_ADDITION2 = "addition2";
     public static final String FIELD_ADDITION3 = "addition3";
     public static final String FIELD_ADDITION4 = "addition4";
     public static final String FIELD_ADDITION5 = "addition5";


     @Id
     @GeneratedValue
     private Long lineId; //������ID

     @NotNull
     private Long headerId; //����ͷID

     @NotNull
     private Long lineNumber; //�к�

     @NotNull
     private Long inventoryItemId; //��ƷID

     @NotNull
     private Long orderdQuantity; //����

     @NotEmpty
     @Length(max = 30)
     private String orderQuantityUom; //��Ʒ��λ

     @NotNull
     private Long unitSellingPrice; //���۵���

     @NotEmpty
     @Length(max = 240)
     private String description; //��ע

     @NotNull
     private Long companyId; //��˾ID

     @Length(max = 150)
     private String addition1; //������Ϣ1

     @Length(max = 150)
     private String addition2; //������Ϣ2

     @Length(max = 150)
     private String addition3; //������Ϣ3

     @Length(max = 150)
     private String addition4; //������Ϣ4

     @Length(max = 150)
     private String addition5; //������Ϣ5

//    @Children
//    @Transient
//    private List<InvInventoryItems> invInventoryItemsList;
//
//    public List<InvInventoryItems> getInvInventoryItemsList() {
//        return invInventoryItemsList;
//    }
//
//    public void setInvInventoryItemsList(List<InvInventoryItems> invInventoryItemsList) {
//        this.invInventoryItemsList = invInventoryItemsList;
//    }

    public void setLineId(Long lineId){
         this.lineId = lineId;
     }

     public Long getLineId(){
         return lineId;
     }

     public void setHeaderId(Long headerId){
         this.headerId = headerId;
     }

     public Long getHeaderId(){
         return headerId;
     }

     public void setLineNumber(Long lineNumber){
         this.lineNumber = lineNumber;
     }

     public Long getLineNumber(){
         return lineNumber;
     }

     public void setInventoryItemId(Long inventoryItemId){
         this.inventoryItemId = inventoryItemId;
     }

     public Long getInventoryItemId(){
         return inventoryItemId;
     }

     public void setOrderdQuantity(Long orderdQuantity){
         this.orderdQuantity = orderdQuantity;
     }

     public Long getOrderdQuantity(){
         return orderdQuantity;
     }

     public void setOrderQuantityUom(String orderQuantityUom){
         this.orderQuantityUom = orderQuantityUom;
     }

     public String getOrderQuantityUom(){
         return orderQuantityUom;
     }

     public void setUnitSellingPrice(Long unitSellingPrice){
         this.unitSellingPrice = unitSellingPrice;
     }

     public Long getUnitSellingPrice(){
         return unitSellingPrice;
     }

     public void setDescription(String description){
         this.description = description;
     }

     public String getDescription(){
         return description;
     }

     public void setCompanyId(Long companyId){
         this.companyId = companyId;
     }

     public Long getCompanyId(){
         return companyId;
     }

     public void setAddition1(String addition1){
         this.addition1 = addition1;
     }

     public String getAddition1(){
         return addition1;
     }

     public void setAddition2(String addition2){
         this.addition2 = addition2;
     }

     public String getAddition2(){
         return addition2;
     }

     public void setAddition3(String addition3){
         this.addition3 = addition3;
     }

     public String getAddition3(){
         return addition3;
     }

     public void setAddition4(String addition4){
         this.addition4 = addition4;
     }

     public String getAddition4(){
         return addition4;
     }

     public void setAddition5(String addition5){
         this.addition5 = addition5;
     }

     public String getAddition5(){
         return addition5;
     }

     }
