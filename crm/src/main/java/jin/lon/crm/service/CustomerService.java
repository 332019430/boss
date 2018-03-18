package jin.lon.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.data.jpa.repository.Query;

import jin.lon.crm.bean.Customer;

/**
 * ClassName:CustomerService <br/>
 * Function: <br/>
 * Date: 2018年3月18日 下午8:05:50 <br/>
 * Author: 郑云龙
 */
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public interface CustomerService {
    @GET
    @Path("/findByfixedAreaIdIsNull")
    List<Customer> findByfixedAreaIdIsNull();

    @GET
    @Path("/findByfixedAreaIdIsNotNull")
    List<Customer> findByfixedAreaIdIsNotNull();

    @PUT
    @Path("/assignCustomers2FixedArea")
    void assignCustomers2FixedArea(@QueryParam("fixedAreaId")String fixedAreaId
            ,@QueryParam("customerIds")Long[] customerIds);
}
