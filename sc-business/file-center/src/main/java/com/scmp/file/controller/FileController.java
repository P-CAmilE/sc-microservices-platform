package com.scmp.file.controller;

import com.scmp.domain.FileSystem;
import com.scmp.domain.Result;
import com.scmp.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
    public Result upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("fileTag") String fileTag,
                         @RequestParam("fileInfo") String fileInfo) throws IOException {

        FileSystem fileSystem = fileService.upload(file, fileTag, fileInfo);
        if (fileSystem == null) {
            return Result.failed("文件上传失败");
        }
        fileService.insert(fileSystem);
        return Result.succeed("文件上传成功");
    }
    @GetMapping("/download")
    public Result download(@RequestParam("fileName") String fileName) {

        FileSystem fileSystem = fileService.getFileSystemByFileName(fileName);
        if (fileSystem == null) {
            return Result.failed("没有此文件");
        }
        String fileUrl = fileSystem.getFileUrl();

//        File file = fileService.download(fileName, fileUrl);
        File file = fileService.download(fileName, fileUrl);
        if (file.length() == 0) {
            return Result.failed("文件下载失败");
        }
        return Result.succeed(file,"文件下载成功");
    }

}
