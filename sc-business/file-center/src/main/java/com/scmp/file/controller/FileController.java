package com.scmp.file.controller;

import com.alibaba.nacos.api.naming.pojo.AbstractHealthChecker;
import com.scmp.domain.FileSystem;
import com.scmp.domain.Result;
import com.scmp.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Coconut Tree
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    /**
     * 上传文件
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("fileTag") String fileTag,
                         @RequestParam("fileInfo") String fileInfo,
                         HttpServletRequest request) throws IOException {
        String fileName = file.getOriginalFilename();
        FileSystem duplicatedFile = fileService.getFileSystemByFileName(fileName);
        if (duplicatedFile != null) {
            return Result.failed("文件名已存在，请修改文件名后再次尝试");
        }
        String uploaderName = request.getHeader("userAccount");
        FileSystem fileSystem = fileService.upload(file, fileTag, fileInfo, uploaderName);
        if (fileSystem == null) {
            log.error("file-service|uploadFailed|uploaderName: {}", uploaderName);
            return Result.failed("文件上传失败");
        }
        fileService.insert(fileSystem);
        log.warn("file-service|uploadSuccess|uploaderName: {}", uploaderName);
        return Result.succeed("文件上传成功");
    }
    @GetMapping("/download")
    public Result<File> download(@RequestParam("fileName") String fileName,
                           HttpServletRequest request) {
        String uploaderName = request.getHeader("userAccount");
        FileSystem fileSystem = fileService.getFileSystemByFileName(fileName);
        if (fileSystem == null) {
            log.error("file-service|downloadFileFailed|cause: {}", "没有此文件");
            return Result.failed("没有此文件");
        }
        String fileUrl = fileSystem.getFileUrl();

        File file = fileService.download(fileName, fileUrl);
        if (file.length() == 0) {
            log.error("file-service|downloadFileFailed|cause: {}", "file-server connect error");
            return Result.failed("文件下载失败");
        }
        log.warn("file-service|downloadFileSuccess|uploaderName: {}", uploaderName);
        return Result.succeed(file,"文件下载成功");
    }

    @GetMapping("/list")
    public Result<List<FileSystem>> getAllFiles() {
        return Result.succeed(fileService.getAllFiles(), "success");
    }

}
