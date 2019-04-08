package com.plusesb.dto;

public class SearchFieldDTO {

    private String filed;
    private String op;
    private Object data;

    public SearchFieldDTO(String filed, String op, Object data) {
        this.filed = filed;
        this.op = op;
        this.data = data;
    }

    public String getFiled() {
        return filed;
    }

    public void setFiled(String filed) {
        this.filed = filed;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
