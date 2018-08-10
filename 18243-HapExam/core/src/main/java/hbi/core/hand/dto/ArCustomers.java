package hbi.core.hand.dto;

/**Auto Generated By Hap Code Generator**/
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import com.hand.hap.mybatis.annotation.ExtensionAttribute;
import org.hibernate.validator.constraints.Length;
import javax.persistence.Table;
import com.hand.hap.system.dto.BaseDTO;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
@ExtensionAttribute(disable=true)
@Table(name = "hap_ar_customers")
public class ArCustomers extends BaseDTO {

     public static final String FIELD_CUSTOMER_ID = "customerId";
     public static final String FIELD_CUSTOMER_NUMBER = "customerNumber";
     public static final String FIELD_CUSTOMER_NAME = "customerName";
     public static final String FIELD_COMPANY_ID = "companyId";
     public static final String FIELD_ENABLED_FLAG = "enabledFlag";


     @Id
     @GeneratedValue
     private Long customerId; //�ͻ�ID

     @NotEmpty
     @Length(max = 60)
     private String customerNumber; //�ͻ����

     @NotEmpty
     @Length(max = 240)
     private String customerName; //�ͻ�����

     @NotNull
     private Long companyId; //��˾ID

     @Length(max = 1)
     private String enabledFlag; //���ñ�ʶ


     public void setCustomerId(Long customerId){
         this.customerId = customerId;
     }

     public Long getCustomerId(){
         return customerId;
     }

     public void setCustomerNumber(String customerNumber){
         this.customerNumber = customerNumber;
     }

     public String getCustomerNumber(){
         return customerNumber;
     }

     public void setCustomerName(String customerName){
         this.customerName = customerName;
     }

     public String getCustomerName(){
         return customerName;
     }

     public void setCompanyId(Long companyId){
         this.companyId = companyId;
     }

     public Long getCompanyId(){
         return companyId;
     }

     public void setEnabledFlag(String enabledFlag){
         this.enabledFlag = enabledFlag;
     }

     public String getEnabledFlag(){
         return enabledFlag;
     }

     }
