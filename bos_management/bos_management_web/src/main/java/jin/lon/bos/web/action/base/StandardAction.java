package bos_management_web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import jin.lon.bos.bean.base.Standard;
import jin.lon.bos.service.base.StandardService;

/**  
 * ClassName:StandardAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午9:49:14 <br/>
 * Author:   郑云龙 
 */

@Namespace("/")// 等价于以前struts.xml中<package>节点的namespace属性
@ParentPackage("struts-default")// 等价于以前struts.xml中<package>节点的extends属性
@Scope("prototype")// 等价于以前applicationContext.xml中<bean>节点的scope属性
@Controller// 代表本类是一个控制器,即web层
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {
    private static final long serialVersionUID = 4138765956393944316L;
    private Standard standard = new Standard();
    @Autowired
    private StandardService service;
    @Override
    public Standard getModel() {
        return standard;
    }
    // 保存派送标准
    //Action中的value等价于以前struts.xml中<action>节点的name
    //Result中的name等价于以前struts.xml中<result>节点的name
    //Result中的location等价于以前struts.xml中<result>节点之间的内容
    @Action(value = "standardAction_save", results = {@Result(name="success",location="/pages/base/standard.html")})
    public String save() {
        service.save(standard);
        return SUCCESS;
    }
}
  
