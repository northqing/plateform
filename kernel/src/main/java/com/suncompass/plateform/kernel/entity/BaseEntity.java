package com.suncompass.plateform.kernel.entity;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 实体基类
 * @author alex
 * @version v1.0
 * Created by alex on 2017/11/23.
 */
@MappedSuperclass
public abstract class BaseEntity<T> implements Serializable{
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     *实体编号（唯一标识）
     */
    @Id
    @GeneratedValue
    protected Long id;

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        BaseEntity<?> that = (BaseEntity<?>) obj;
        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
