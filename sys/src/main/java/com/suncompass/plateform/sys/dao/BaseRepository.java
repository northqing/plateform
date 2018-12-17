package com.suncompass.plateform.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 数据访问对象基类，所有实体数据访问都应继承该接口
 * Created by alex on 2017/12/2.
 * @author by alex.
 * @version 1.0.0.
 */
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long> {
}
