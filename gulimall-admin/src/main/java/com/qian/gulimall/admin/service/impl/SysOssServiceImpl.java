package com.qian.gulimall.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qian.gulimall.admin.api.criteria.SysOssCriteria;
import com.qian.gulimall.admin.api.dto.SysOssDto;
import com.qian.gulimall.admin.api.result.SysOssResult;
import com.qian.gulimall.admin.dao.SysOssDao;
import com.qian.gulimall.admin.entity.SysOssEntity;
import com.qian.gulimall.admin.service.SysOssService;
import com.qian.gulimall.common.utils.BeanKit;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

    @Value("${fdfs.service}")
    private String fdfsService;

    @Autowired
    private SysOssDao sysOssDao;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    /**
     * 分页查询
     *
     * @param pageable
     * @param sysOssCriteria
     * @return
     */
    @Override
    public PageUtils queryPage(Pageable pageable, SysOssCriteria sysOssCriteria) {
        Page<SysOssCriteria> sysOssCriteriaPage = new Page<SysOssCriteria>(pageable.getPageNumber(), pageable.getPageSize());
        IPage<SysOssResult> sysOssResultIPage = this.baseMapper.querySysOssPage(sysOssCriteriaPage, sysOssCriteria);
        if (!CollectionUtils.isEmpty(sysOssResultIPage.getRecords())) {
            for (SysOssResult sysOssResult : sysOssResultIPage.getRecords()) {
                // 加上IP地址
                sysOssResult.setUrl(fdfsService + "/" + sysOssResult.getUrl());
            }
            // 加上IP地址
//            List<SysOssResult> collect = sysOssResultIPage.getRecords().stream().map(result -> {
//                result.setUrl(fdfsService + "/" + result.getUrl());
//                return result;
//            }).collect(Collectors.toList());
        }
        return new PageUtils(sysOssResultIPage, sysOssResultIPage.getRecords());
    }

    @Override
    public Long saveSysOss(SysOssDto sysOssDto) {
        SysOssEntity sysOssEntity = BeanKit.convertBean(SysOssEntity.class, sysOssDto);
        this.baseMapper.insert(sysOssEntity);
        return sysOssEntity.getId();
    }

    @Override
    public Integer updateStatusByIds(Integer stauts, List<String> list) {

        List<SysOssEntity> all = this.baseMapper.selectList(null);
        if (!CollectionUtils.isEmpty(all)) {
            List<String> collect = all.stream().map(item -> item.getId() + "").collect(Collectors.toList());
            List<String> intersection = collect.stream().filter(item -> !list.contains(item)).collect(Collectors.toList());

            // 差集 (list2 - list1)
//            List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(toList());
            if (!CollectionUtils.isEmpty(intersection)) {
                return sysOssDao.updateStatusByIds(stauts, intersection);
            }
        }
        return 0;
    }

    @Override
    public void removeFiles(List<SysOssEntity> sysOssEntityList, Long[] ids) {
        // 从文件系统中删除文件
        if (!CollectionUtils.isEmpty(sysOssEntityList)) {
            sysOssEntityList.stream().forEach(sysOss -> {
                String url = sysOss.getUrl();
                String group = url.substring(0, url.indexOf('/'));
                String path = url.substring(url.indexOf('/') + 1);

                fastFileStorageClient.deleteFile(group, path);
            });
            // 删除表数据
            this.removeByIds(Arrays.asList(ids));
        }
    }

    public static void main(String[] args) {
//        List<String> list1 = new ArrayList<String>();
//        list1.add("1");
//        list1.add("2");
//
//        List<String> list2 = new ArrayList<String>();
//        list2.add("2");
//
//        // 交集
//        List<String> intersection = list1.stream().filter(item -> !list2.contains(item)).collect(Collectors.toList());
//        System.out.println("---交集 intersection---");
//        intersection.parallelStream().forEach(System.out::println);

        String url = "group1/M00/00/00/qf5zCl8zj3OALRFvAAE_Gm50HCI27.jpeg";

        String group = url.substring(0, url.indexOf('/'));
        String path = url.substring(url.indexOf('/') + 1);

        System.out.println(group);
        System.out.println(path);
    }

}
