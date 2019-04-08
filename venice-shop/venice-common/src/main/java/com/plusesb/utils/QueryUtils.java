package com.plusesb.utils;


import com.plusesb.dto.SearchDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

public class QueryUtils {

    private static final Logger logger = LoggerFactory.getLogger(QueryUtils.class);

    /**
     * 把jqgrid的参数转成SearchDTO,
     * @param request
     * @param otherParams 除了request里的参数可以再加自定义的参数:格式如,"eq_name","名称","like_tilte",“标题”
     * @return
     */
    public static SearchDTO buildSearchDtoFromJqGrid(HttpServletRequest request, String ...otherParams){
        //1.处理默认的查询
        SearchDTO searchDTO = new SearchDTO();

        Enumeration<String> requestEnum =  request.getParameterNames();
        while(requestEnum.hasMoreElements()){
            String key = requestEnum.nextElement();
            String value = request.getParameter(key);
            String [] keyAndOp = key.split("_");
            if(keyAndOp.length==2 && StringUtils.isNotBlank(keyAndOp[1]) && StringUtils.isNotBlank(keyAndOp[0]) && StringUtils.isNotBlank(value)){
                searchDTO.addFiled(BaseUtils.camelToUnderline(keyAndOp[0]),keyAndOp[1],value);
            }
        }

        //2.处理额外的查询值
        if(otherParams!=null){
            int size = otherParams.length/2;
            for(int i=0;i<size;i++){
                String key = otherParams[i*2];
                String value = otherParams[i*2+1];
                if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)){
                    if(key.equals("_orderBy")){//处理额外的排序
                        addSortToSimpleSearch(searchDTO,value.trim());
                    }else{//处理额外查询条件
                        String [] keyAndOp = key.split("_");
                        if(keyAndOp.length==2 && StringUtils.isNotBlank(keyAndOp[1]) && StringUtils.isNotBlank(keyAndOp[0])){
                                searchDTO.addFiled(keyAndOp[0],keyAndOp[1],value);
                        }
                    }
                }
            }
        }
        //3.处理排序,格式:ordery = name asc,id desc
        String sidx = request.getParameter("orderBy");
        if(StringUtils.isNotBlank(sidx)){
            addSortToSimpleSearch(searchDTO,sidx.trim());
        }

        //处理分页数据
        String pageObj = request.getParameter("page");
        if(StringUtils.isNotBlank(pageObj)){
            searchDTO.setPageIndex(toLongObject(pageObj));
        }
        String pageSizeObj = request.getParameter("limit");
        if(StringUtils.isNotBlank(pageSizeObj)){
            searchDTO.setPageSize(toLongObject(pageSizeObj));
        }

        return searchDTO;
    }
    //将排序字符串转成SearchDTO用的排序对象
    private static void addSortToSimpleSearch(SearchDTO simpleSearchDTO , String sortKeyValueString){
        String [] sortArr = sortKeyValueString.split(",");
        Arrays.stream(sortArr).forEach(oneSortKeyValueString->{
            String [] sortKeyValue = oneSortKeyValueString.split(" ");
            if(sortKeyValue.length==2){
                boolean asc = true;
                if("desc".equals(sortKeyValue[1])){
                    asc = false;
                }
                simpleSearchDTO.addSort(BaseUtils.camelToUnderline(sortKeyValue[0]),asc);
            }
        });
    }
    private static Long toLongObject(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Integer) {
            return (Long) obj;
        }
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        Long result = null;
        try {
            if(obj instanceof String){
                result = Long.parseLong((String)obj);
            }else {
                result = Long.parseLong(obj.toString());
            }
        } catch (Exception e) {
        }
        return result;
    }
}
