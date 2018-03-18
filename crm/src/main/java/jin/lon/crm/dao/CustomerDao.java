package jin.lon.crm.dao;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils.Null;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jin.lon.crm.bean.Customer;

/**  
 * ClassName:CustomerDao <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午8:03:52 <br/>
 * Author:   郑云龙 
 */
public interface CustomerDao extends JpaRepository<Customer, Long> {
    List<Customer> findByfixedAreaIdIsNull();
    List<Customer> findByfixedAreaIdIsNotNull();
    
    @Modifying
    @Query("update Customer Set fixedAreaId = null where fixedAreaId=?")
    void unbindCustomerByFixedArea(String fixedAreaID);
    
    @Modifying
    @Query("update Customer set fixedAreaId = ? where id=? ")
    void bingCustomerByFixedArae(String fixedAreaId, Long customerIds);
}
  
