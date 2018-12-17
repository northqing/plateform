package com.suncompass.plateform.kernel.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 数据实体
 * @author alex
 * @version v1.0
 * Created by alex on 2017/11/23.
 */
@MappedSuperclass
public abstract class AbstractDataEntity<T> extends BaseEntity<T> {
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Byte getDeleted() {
        return deleted;
    }

    public void setDeleted(Byte deleted) {
        this.deleted = deleted;
    }

    /**
     * 备注
     */
    protected String remark;

    /**
     * 创建日期
     */
    @Column(name="gmt_created")
    protected Date created;

    /**
     * 更新日期
     */
    @Column(name = "gmt_modified")
    protected Date modified;

    /**
     * 删除标记（0：正常；1：删除；2：审核）
     */
    @Column(nullable = false)
    protected Byte deleted;
}
