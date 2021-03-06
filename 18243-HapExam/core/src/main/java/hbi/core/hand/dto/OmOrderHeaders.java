package hbi.core.hand.dto;

/**Auto Generated By Hap Code Generator**/

import com.hand.hap.core.annotation.Children;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import com.hand.hap.system.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ExtensionAttribute(disable=true)
@Table(name = "hap_om_order_headers")
public class OmOrderHeaders extends BaseDTO {

     public static final String FIELD_HEADER_ID = "headerId";
     public static final String FIELD_ORDER_NUMBER = "orderNumber";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_ORDER_DATE = "orderDate";
     public static final String FIELD_ORDER_STATUS = "orderStatus";
     public static final String FIELD_CUSTOMER_ID = "customerId";


     @Id
     @GeneratedValue
     private Long headerId; //����ͷID

     @NotEmpty
     @Length(max = 60)
     private String orderNumber; //�������

     @NotNull
     private Long companyId; //��˾ID

     private Date orderDate; //��������

     @NotEmpty
     @Length(max = 30)
     private String orderStatus; //����״̬

     @NotNull
     private Long customerId; //�ͻ�ID

    @Children
    @Transient
    private List<OmOrderLines> omOrderLinesList;

    public List<OmOrderLines> getOmOrderLinesList() {
        return omOrderLinesList;
    }

    public void setOmOrderLinesList(List<OmOrderLines> omOrderLinesList) {
        this.omOrderLinesList = omOrderLinesList;
    }

    public void setHeaderId(Long headerId){
         this.headerId = headerId;
     }

     public Long getHeaderId(){
         return headerId;
     }

     public void setOrderNumber(String orderNumber){
         this.orderNumber = orderNumber;
     }

     public String getOrderNumber(){
         return orderNumber;
     }

     public void setCompanyId(Long companyId){
         this.companyId = companyId;
     }

     public Long getCompanyId(){
         return companyId;
     }

     public void setOrderDate(Date orderDate){
         this.orderDate = orderDate;
     }

     public Date getOrderDate(){
         return orderDate;
     }

     public void setOrderStatus(String orderStatus){
         this.orderStatus = orderStatus;
     }

     public String getOrderStatus(){
         return orderStatus;
     }

     public void setCustomerId(Long customerId){
         this.customerId = customerId;
     }

     public Long getCustomerId(){
         return customerId;
     }

     }
