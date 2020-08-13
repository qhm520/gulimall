package com.qian.gulimall.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qian.gulimall.admin.api.criteria.SysOssCriteria;
import com.qian.gulimall.admin.api.result.SysOssResult;
import com.qian.gulimall.admin.entity.SysOssEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件上传
 * 
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-04-19 08:57:20
 */
@Mapper
public interface SysOssDao extends BaseMapper<SysOssEntity> {

    public IPage<SysOssResult> querySysOssPage(Page<SysOssCriteria> page, @Param("sysOss") SysOssCriteria sysOssCriteria);

    Integer updateStatusByIds(@Param("status") int status, @Param("list") List<String> list);
}
