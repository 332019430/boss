package jin.lon.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.bouncycastle.jce.provider.JDKDSASigner.noneDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import jin.lon.bos.bean.base.Area;
import jin.lon.bos.service.base.AreaService;
import jin.lon.bos.web.action.CommonAction;
import jin.lon.utils.PinYin4jUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:AreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月15日 下午8:07:07 <br/>
 * Author: 郑云龙
 */
@Controller
@ParentPackage("struts-default")
@Namespace("/")
@Scope("prototype")
public class AreaAction extends CommonAction<Area> {

    

    private File file;

    public void setFile(File file) {
        this.file = file;
    }

    @Autowired
    private AreaService areaService;

    @Action("areaAction_importXLS")
    public String importXLS() throws Exception {

        List<Area> list = new ArrayList<>();
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            String sheng = row.getCell(1).getStringCellValue();
            String shi = row.getCell(2).getStringCellValue();
            String qu = row.getCell(3).getStringCellValue();
            String bianma = row.getCell(4).getStringCellValue();
            sheng = sheng.substring(0, sheng.length() - 1);
            shi = shi.substring(0, shi.length() - 1);
            qu = qu.substring(0, qu.length() - 1);
            shi = PinYin4jUtils.hanziToPinyin(shi, "");
            String[] headByString = PinYin4jUtils.getHeadByString(sheng + shi + qu);
            String shortCode = PinYin4jUtils.stringArrayToString(headByString);
            Area area = new Area();
            area.setProvince(sheng);
            area.setCity(shi);
            area.setDistrict(qu);
            area.setPostcode(bianma);
            area.setShortcode(shortCode);

            list.add(area);
        }

        areaService.save(list);
        workbook.close();
        return NONE;
    }

    
    
    
    
    
    
    

    @Action("areaAction_pageQuery")
    public String pageQuery() throws IOException {
        /*PageRequest pageRequest = new PageRequest(page - 1, rows);
        Page<Area> page = areaService.pageQuery(pageRequest);
        List<Area> list = page.getContent();
        long total = page.getTotalElements();
        Map<String, Object> map = new HashMap<>();
        map.put("rows", list);
        map.put("total", total);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas"});
        String json = JSONObject.fromObject(map, jsonConfig).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);*/
        return NONE;
    }
}