package jin.lon.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import jin.lon.bos.bean.base.SubArea;

/**  
 * ClassName:SubAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月17日 下午10:43:19 <br/>
 * Author:   郑云龙 
 */
public interface SubAreaService {

    void save(SubArea model);

    Page<SubArea> pageQuery(Pageable pageRequest);

    List<SubArea> findFixedAreaIsNull();

    void sub_Arae2s2FixedArea(Long id, Long[] sub_Arae2Ids);

    List<SubArea> findFixedAreaIsNotNull();

    void sub_AreaSetFixedAreaNull();

    

}
  
