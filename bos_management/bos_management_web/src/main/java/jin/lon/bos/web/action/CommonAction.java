package jin.lon.bos.web.action;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import jin.lon.bos.bean.base.Area;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:BaseAction <br/>
 * Function: <br/>
 * Date: 2018年3月15日 下午9:38:28 <br/>
 * Author: 郑云龙
 * 
 * @param <T>
 */
public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {
    protected T model;

    @Override
    public T getModel() {

        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<T> class1 = (Class<T>) type.getActualTypeArguments()[0];

        try {
            return class1.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    protected int page;

    public void setPage(int page) {
        this.page = page;
    }

    protected int rows;

    public void setRows(int rows) {
        this.rows = rows;
    }
    
    public String page2json(Page<T> page, JsonConfig jsonConfig) throws IOException {
        String json;
        List<T> list = (List<T>) page.getContent();
        long total = page.getTotalElements();
        Map<String, Object> map = new HashMap<>();
        map.put("rows", list);
        map.put("total", total);
        if (jsonConfig==null) {
            json = JSONObject.fromObject(map).toString();
        }else {
            json = JSONObject.fromObject(map, jsonConfig).toString();
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        return NONE;
    }
}
