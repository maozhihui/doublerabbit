package com.comba.server.dao.tenant;


import org.springframework.data.jpa.repository.JpaRepository;

import com.comba.server.dao.model.TenantEntity;

import java.util.List;


/**
 * 租户 数据层
 * @author wengzhonghui
 *
 */
public interface TenantDao extends JpaRepository<TenantEntity, String>{


    /**
     * 根据租户名称查找租户
     *
     * @param name 租户名称
     * @return
     */
    TenantEntity findByName(String name);

    /**
     * 根据租户名称统计租户
     *
     * @param name 租户名称
     * @return
     */
    int countByName(String name);

}
