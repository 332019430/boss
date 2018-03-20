package jin.lon.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jin.lon.crm.bean.Customer;
import jin.lon.crm.dao.CustomerDao;
import jin.lon.crm.service.CustomerService;

/**
 * ClassName:CustomerServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月18日 下午8:06:21 <br/>
 * Author: 郑云龙
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao dao;

    @Override
    public List<Customer> findByfixedAreaIdIsNull() {
        return dao.findByfixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findByfixedAreaIdIsNotNull() {

        return dao.findByfixedAreaIdIsNotNull();
    }

    @Override
    public void assignCustomers2FixedArea(String fixedAreaId, Long[] customerIds) {
        System.out.println("定区id"+fixedAreaId);
        System.out.println(""+customerIds.length);
        if (fixedAreaId.length() > 0) {
            dao.unbindCustomerByFixedArea(fixedAreaId);
        }

        if (customerIds.length > 0 && fixedAreaId.length() > 0) {
            for (Long long1 : customerIds) {
                dao.bingCustomerByFixedArae(fixedAreaId, long1);
            }
        }
    }

    @Override
    public void assignCustomers2null(String fixedAreaId) {
          
        if (fixedAreaId.length()>0) {
            dao.unbindCustomerByFixedArea(fixedAreaId);
        }
        
    }

    @Override
    public void save(Customer customer) {
          
        dao.save(customer);
        
    }

    
}
