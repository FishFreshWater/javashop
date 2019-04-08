package com.plusesb.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public class PageDTO<T> {

    private Long currPage = 1L;
    private Long pageSize = 30L;
    private Long totalCount;
    private List<T> list;

    public Long getPageSize() {
        return pageSize;
    }

    public PageDTO(Long currPage, Long pageSize, long totalCount, List<T> list){
        this.currPage = currPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.list = list;
    }
    public PageDTO(IPage page){
        this.currPage = page.getCurrent();
        this.pageSize = page.getSize();
        this.totalCount = page.getTotal();
        this.list = page.getRecords();
    }


    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalPage() {
        if(pageSize!=0){
            long result = totalCount/pageSize;
            if(totalCount % pageSize !=0){
                result ++;
            }
            return result;
        }
        return 0;
    }


    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean hasPreviousPage(){
        if(isFirstPage()){
            return false;
        }
        return true;
    }
    public boolean isFirstPage(){
         if(currPage<=1){
             return true;
         }
        return false;
    }

    public boolean hasNextPage(){
        if(isLastPage()){
            return false;
        }else{
            return true;
        }
    }

    public boolean isLastPage(){
        if(currPage>=this.getTotalPage()){
            return true;
        }else{
            return false;
        }
    }

    public Long getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Long currPage) {
        this.currPage = currPage;
    }
}
