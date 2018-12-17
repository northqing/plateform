package com.suncompass.plateform.sys.entity;

import com.suncompass.plateform.kernel.entity.AbstractTreeDataEntity;

import javax.persistence.*;

/**
 * @author alex
 * Created by alex on 2017/11/23.
 */
@Entity
@Table(name="sys_organization")
public class Organization extends AbstractTreeDataEntity<Organization> {
    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String name;
    private String code;
}
