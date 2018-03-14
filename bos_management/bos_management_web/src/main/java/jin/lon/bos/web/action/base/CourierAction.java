package jin.lon.bos.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import jin.lon.bos.bean.base.Courier;
import jin.lon.bos.service.base.CourierService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CourierAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午5:55:26 <br/>
 * Author:   郑云龙 
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier>{
    
    private Courier model;
    @Override
    public Courier getModel() {
        if (model==null) {
            model=new Courier();
        }
        return model;
    }
    
    @Autowired
    private CourierService service;
    
    @Action(value="courier_save",results={@Result(name="success",location="pages/base/courier.html",type="redirect") })
    public String save(){
        service.save(model);
        return SUCCESS;
    }
    
    private int page;
    
    public void setPage(int page) {
        this.page = page;
    }

    private int rows;
    
    public void setRows(int rows) {
        this.rows = rows;
    }

    @Action(value="courier_pageQuery",results={@Result(name="success",location="pages/base/courier.html",type="redirect") })
    public String pageQuery() throws IOException{
        Pageable pageable=new PageRequest(page-1, rows);
        Page<Courier> page =service.findAll(pageable);
        
        long total = page.getTotalElements();
        List<Courier> list = page.getContent();
        Map<String, Object> map=new HashMap<>();
        map.put("total", total);
        map.put("rows", list);
        
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"fixedAreas","takeTime"});
        String json = JSONObject.fromObject(map,config).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        return NONE;
    }
    
    private String ids;
    
    public void setIds(String ids) {
        this.ids = ids;
    }

    @Action(value="courier_delete",results={@Result(name="success",location="pages/base/courier.html",type="redirect")})
    public String delete(){
        System.out.println(ids);
        service.delete(ids);
        return SUCCESS;
    }

}
  
