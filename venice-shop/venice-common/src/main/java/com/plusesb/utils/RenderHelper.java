package com.plusesb.utils;

import com.google.common.collect.Maps;
import com.plusesb.dto.PageDTO;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: linyuchi
 * Date: 2018/4/14
 * Time: 18:50
 * Description: No Description
 */
public class RenderHelper {

    public static Map<String, Object> renderPageJson(PageDTO<? extends Object> page, String props){
        List<Map<String,Object>> list = transListProp(page.getList(),props);
        Map<String, Object> result = Maps.newHashMap();
        result.put("code", "success");
        result.put("content", list);
        result.put("pageIndex", page.getCurrPage());
        result.put("pageSize", page.getPageSize());
        result.put("totalSize", page.getTotalCount());
        result.put("totalPage", page.getTotalPage());
        return result;
    }


    //通过指定转成的属性,props：多个以,分隔
    public static List<Map<String,Object>> transListProp(List<? extends Object> objList,String props){
        List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>(objList.size());
        for(Object obj:objList){
            Map<String, Object> map = objectChangeToMap(obj, props);
            mapList.add(map);
        }
        return mapList;
    }

    public static Map<String, Object> objectChangeToMap(Object obj,String props) {
        Map<String,Object> map = Maps.newHashMap();
        String [] propArr = props.split(",");
        for(String prop:propArr){
            if(StringUtils.isNotBlank(prop)){
                try {
                    prop = prop.trim();
                    map.put(StringUtils.replace(prop,".","_"), PropertyUtils.getProperty(obj, prop));
                }catch (NestedNullException exception){
                    map.put(prop, null);
                }catch (Exception e){
                    throw new RuntimeException(e);
                }

            }
        }
        return map;
    }
}
