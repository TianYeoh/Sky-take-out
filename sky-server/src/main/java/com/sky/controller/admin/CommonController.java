package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common/")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传:{}",file);
        // 使用uid生成，重命名

        try {
            // 获得原始文件名
            String originalFileName = file.getOriginalFilename();
            // 获得原始文件名的后缀
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            // 构造新文件名
            String objectName = UUID.randomUUID().toString()+extension;
            // 文件请求路径 // 文件上传到阿里云服务器
            String filePath =  aliOssUtil.upload(file.getBytes(),objectName);

            return Result.success(filePath);

        } catch (IOException e) {
            log.error("文件上传失败:{}",e);
            throw new RuntimeException(e);

        }
        // return Result.error(MessageConstant.UPLOAD_FAILED);

    }
}
