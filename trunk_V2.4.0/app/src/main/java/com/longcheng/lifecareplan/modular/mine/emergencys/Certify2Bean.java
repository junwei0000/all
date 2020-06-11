package com.longcheng.lifecareplan.modular.mine.emergencys;

import java.io.Serializable;

public class Certify2Bean implements Serializable {
    public String name;
    public String idNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    @Override
    public String toString() {
        return "Certify2Bean{" +
                "name='" + name + '\'' +
                ", idNo='" + idNo + '\'' +
                '}';
    }
}
