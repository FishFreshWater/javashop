package com.plusesb.dto;

public class SearchSortDTO<T> {
    private String filed;
    private boolean asc;

    public SearchSortDTO(String filed, boolean asc) {
        this.filed = filed;
        this.asc = asc;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }
}
