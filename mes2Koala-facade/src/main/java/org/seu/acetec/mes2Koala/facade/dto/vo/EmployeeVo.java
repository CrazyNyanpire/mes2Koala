package org.seu.acetec.mes2Koala.facade.dto.vo;

import java.io.Serializable;

/**
 * Created by LCN on 2016/3/29.
 */
public class EmployeeVo implements Serializable {

    private Long id;

    private String name;

    public EmployeeVo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
