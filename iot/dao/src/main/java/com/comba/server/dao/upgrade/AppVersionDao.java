package com.comba.server.dao.upgrade;


import com.comba.server.dao.model.AppVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *  数据库实现类
 *
 * @作者 sujinxian
 * @创建时间 2018-01-24 16:49:36
 */
public interface AppVersionDao extends JpaRepository<AppVersionEntity, String> {

}
