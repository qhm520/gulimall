package com.qian.gulimall.admin.controller;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qian.gulimall.admin.api.criteria.SysOssCriteria;
import com.qian.gulimall.admin.api.dto.SysOssDto;
import com.qian.gulimall.admin.entity.SysOssEntity;
import com.qian.gulimall.admin.feign.BrandFeignService;
import com.qian.gulimall.admin.service.SysOssService;
import com.qian.gulimall.common.entity.vo.UserDetailsVo;
import com.qian.gulimall.common.utils.Base64ToMultipart;
import com.qian.gulimall.common.utils.IPUtils;
import com.qian.gulimall.common.utils.PageUtils;
import com.qian.gulimall.common.utils.Pageable;
import com.qian.gulimall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 文件上传
 *
 * @author QIAN
 * @email 794308528@qq.com
 * @date 2020-04-19 08:57:20
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController {
    @Autowired
    private SysOssService sysOssService;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Value("${fdfs.service}")
    private String fdfsService;

    @Autowired
    private BrandFeignService brandFeignService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("admin:sysoss:list")
    public R list(Pageable pageable, @ModelAttribute SysOssCriteria sysOssCriteria){
        PageUtils page = sysOssService.queryPage(pageable, sysOssCriteria);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("admin:sysoss:info")
    public R info(@PathVariable("id") Long id){
		SysOssEntity sysOss = sysOssService.getById(id);
        sysOss.setUrl(fdfsService + "/" + sysOss.getUrl());
        return R.ok().put("data", sysOss);
    }

    /**
     * 信息
     */
    @RequestMapping("/url/{id}")
    //@RequiresPermissions("admin:sysoss:info")
    public R url(@PathVariable("id") Long id){
        SysOssEntity sysOss = sysOssService.getById(id);
        sysOss.setUrl(fdfsService + "/" + sysOss.getUrl());
        return R.ok().put("data", sysOss.getUrl());
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("admin:sysoss:save")
    public R save(@RequestBody SysOssEntity sysOss){
		sysOssService.save(sysOss);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("admin:sysoss:update")
    public R update(@RequestBody SysOssEntity sysOss){
		sysOssService.updateById(sysOss);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("admin:sysoss:delete")
    public R delete(@RequestBody Long[] ids){
        // TODO 要查询是否在用，在用不允许删除
        List<SysOssEntity> sysOssEntityList = sysOssService.listByIds(Arrays.asList(ids));
        if (!CollectionUtils.isEmpty(sysOssEntityList)) {
            List<SysOssEntity> collect = sysOssEntityList.stream().filter(item -> item.getStatus() != 2).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(collect)) {
                sysOssService.removeFiles(sysOssEntityList, ids);
            } else
                return R.error("图片正在使用，不允许删除");
        }
        return R.ok();
    }


    /**
     * 刷新数据
     */
    @RequestMapping("/refresh")
    //@RequiresPermissions("admin:sysoss:delete")
    public R refresh(){
        R r = brandFeignService.queryAllLogo();
        if (r.getCode() == 0) {
            List<String> list = (List<String>) r.get("list");
            Integer num = sysOssService.updateStatusByIds(2, list);
            return R.ok().put("data", num);
        }
        return R.error("没有要刷新数据");
    }

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public R upload(@RequestBody String base64, Authentication authentication, HttpServletRequest request) throws IOException {
        R r = Base64ToMultipart.base64ToMultipart(base64);
        if (r.getCode() == 0) {
            MultipartFile file = (MultipartFile) r.get("multipartFile");
            String fileName = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            long size = file.getSize();
            StorePath storePath = fastFileStorageClient.uploadFile(inputStream, size, fileName.substring(fileName.lastIndexOf(".") + 1), null);
            // TODO 保存到数据库
            String resAccessUrl = getResAccessUrl(storePath);
            UserDetailsVo userDetailsVo = (UserDetailsVo)authentication.getPrincipal();
            SysOssDto sysOssDto = new SysOssDto();
            sysOssDto.setOriginalFilename(fileName);
            sysOssDto.setUrl(resAccessUrl);
            sysOssDto.setUploadUser(userDetailsVo.getUsername());
            sysOssDto.setIp(IPUtils.getIPAddress(request));
            Long id = sysOssService.saveSysOss(sysOssDto);

            return R.ok().put("id", id);
        }

        return r;
    }

    /**
     * 下载文件
     *
     * @param fileUrl
     * @return
     * @throws IOException
     */
    @RequestMapping("/download")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileUrl = "group1/M00/00/00/qf5zCl8rxVeAAvygAAAADEHIqB4224.txt";  // TODO 从参数中获取ID查询获取
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = fastFileStorageClient.downloadFile(group, path, downloadByteArray);

        String filename = "qian2020080617.txt";
        try (OutputStream outputStream = new FileOutputStream(new File(filename));) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            outputStream.write(bytes);
            outputStream.flush();
        }
    }

    /**
     * 测试文件删除
     */
    @RequestMapping("/ossdelete")
    public void deleteFile() {
        fastFileStorageClient.deleteFile("group1", "M00/00/00/wKiAjVlpQVyARpQwAADGA0F72jo566.jpg");
    }


    // 封装文件完整URL地址
    private String getResAccessUrl(StorePath storePath) {
        String fileUrl = storePath.getFullPath();
        return fileUrl;
    }

}
