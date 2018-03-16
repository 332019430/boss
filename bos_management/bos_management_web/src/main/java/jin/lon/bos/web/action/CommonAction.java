package jin.lon.bos.web.action;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

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
    
    public void returnJson(){
        PageRequest pageRequest = new PageRequest(page - 1, rows);
        Page<T> page = areaService.pageQuery(pageRequest);
        List<T> list = page.getContent();
        long total = page.getTotalElements();
        Map<String, Object> map = new HashMap<>();
        map.put("rows", list);
        map.put("total", total);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});
        String json = JSONObject.fromObject(map, jsonConfig).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }
}
