package com.comba.server.dao.upgrade;


import com.comba.server.common.data.AppVersion;
import com.comba.server.dao.common.CommonService;

import java.util.List;

/**
 *  业务实现接口
 *
 * @作者 sujinxian
 * @创建时间 2018-01-24 16:49:36
 */
public interface AppVersionService extends CommonService<AppVersion> {
    /**
     * 批量删除
     * @param ids
     */
    void deleteByIds(String[] ids);

    /**
     * 通过多个ID查找
     * @param ids
     * @return
     */
    public List<AppVersion> findByIds(Iterable<String> ids);
}
