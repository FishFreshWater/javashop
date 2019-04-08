package com.plusesb.dto;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;

import java.util.List;

public class SearchDTO {

    private Long pageIndex = 1L;
    private Long pageSize = 10L ;
    //查询结果集
    private String[] sqlSelect;
    //实体名称
    private Class<?> entityClass;
    //返回数量
    private Integer limit = 0;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String[] getSqlSelect() {
        return sqlSelect;
    }

    public void setSqlSelect(String[] sqlSelect) {
        this.sqlSelect = sqlSelect;
    }
    //条件参数
    private List<SearchFieldDTO> fields =Lists.newArrayList();
    //排序参数
    private List<SearchSortDTO> sorts = Lists.newArrayList();

    public Object getDataByFiled(String filed){

        for (SearchFieldDTO searchFieldDTO :fields){
            if (searchFieldDTO.getFiled().equals(filed)){
                return searchFieldDTO.getData();
            }
        }
        return "";
    }

    public void addFiled(String filed,String op,Object data){
        fields.add(new SearchFieldDTO(filed,op,data));
    }

    public void addSort(String sortField,boolean asc){
        sorts.add(new SearchSortDTO(sortField,asc));
    }

    public Long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public List<SearchFieldDTO> getFields() {
        return fields;
    }

    public void setFields(List<SearchFieldDTO> fields) {
        this.fields = fields;
    }

    public List<SearchSortDTO> getSorts() {
        return sorts;
    }

    public void setSorts(List<SearchSortDTO> sorts) {
        this.sorts = sorts;
    }

    public void deleteByFiled(String filed) {
        List<SearchFieldDTO> newFields = Lists.newArrayList();
        for (SearchFieldDTO searchFieldDTO :fields){
            if (!searchFieldDTO.getFiled().equals(filed)){
                newFields.add(searchFieldDTO);
            }
        }
        fields = newFields;
    }


}
