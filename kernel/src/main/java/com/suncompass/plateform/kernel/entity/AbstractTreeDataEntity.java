package com.suncompass.plateform.kernel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

/**
 * @author alex
 * Created by alex on 2017/11/27.
 */
@MappedSuperclass
public abstract class AbstractTreeDataEntity<T extends BaseEntity> extends AbstractDataEntity<T> {
    public T getParent() {
        return parent;
    }

    public void setParent(T parent) {
        this.parent = parent;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    /**
     * 父对象
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="parentId")
    private T parent;

    /**
     * 子集
     */
    @JsonManagedReference
    @OneToMany(mappedBy = "parent")
    private List<T> children;
}
