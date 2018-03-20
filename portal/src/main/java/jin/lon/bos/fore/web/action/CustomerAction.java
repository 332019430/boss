package jin.lon.bos.fore.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import jin.lon.bos.bore.bean.Customer;
import jin.lon.utils.MSNUtils;
import jin.lon.utils.Md5Util;

/**
 * ClassName:CustomerAction <br/>
 * Function: <br/>
 * Date: 2018年3月19日 下午5:40:37 <br/>
 * Author: 郑云龙
 */
@Namespace("/")
@Scope("prototype")
@ParentPackage("struts-default")
@Controller
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
    private Customer model = new Customer();

    @Override
    public Customer getModel() {
        return model;
    }

    @Action("customer_sendMsn")
    public String sendMsn() {
        String number = RandomStringUtils.randomNumeric(6);
        System.out.println(number);
        ServletActionContext.getRequest().getSession().setAttribute("number", number);

        // MSNUtils.sendSms(model.getTelephone(), number);

        return NONE;
    }

    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    @Action(value = "customerAction_regist",
            results = {
                    @Result(name = "success", location = "/signup-success.html", type = "redirect"),
                    @Result(name = "error", location = "/signup-fail.html", type = "redirect")})
    public String customerAction_regist() {

        String attribute =
                (String) ServletActionContext.getRequest().getSession().getAttribute("number");
        model.setPassword(Md5Util.encodePwd(model.getPassword()));
        if (StringUtils.isNotEmpty(attribute) && StringUtils.isNotEmpty(checkcode)
                && attribute.equals(checkcode)) {

            WebClient.create("http://localhost:8181/crm/webServicd/customerService/save")
                    .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .post(model);
            return SUCCESS;
        }
        return ERROR;
    }

}
