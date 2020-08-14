package com.scmp.file.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scmp.domain.FileSystem;
import com.scmp.domain.Result;
import com.scmp.file.mapper.FileMapper;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author Coconut Tree
 */
@Slf4j
@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    public FileSystem upload(@RequestParam("file") MultipartFile file,
                             @RequestParam("fileTag") String fileTag,
                             @RequestParam("fileInfo") String fileInfo,
                             String uploaderName) throws IOException {
        // 上传文件返回类型
        FileSystem fileSystem = new FileSystem();
        //得到 文件的原始名称
        String originalFilename = file.getOriginalFilename();
        //扩展名
        String extention = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        // 文件大小
        String fileSize = file.getSize() + " bytes";
        try {
            //加载fastDFS客户端的配置 文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //创建tracker的客户端
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            //定义storage的客户端
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);
            //文件元信息, 可以省略
            NameValuePair[] metaList = new NameValuePair[3];
            metaList[0] = new NameValuePair("fileName", originalFilename);
            metaList[1] = new NameValuePair("filesize", fileSize);
            metaList[2] = new NameValuePair("fileType", extention);
            //执行上传
            String fileUrl = storageClient1.upload_file1(file.getBytes(), extention, metaList);
            log.info("文件中心|上传文件|上传成功|文件url:{}", fileUrl);

            fileSystem.setFileName(originalFilename);
            fileSystem.setFileType(extention);
            fileSystem.setFileUrl(fileUrl);
            fileSystem.setFileSize(fileSize);
            fileSystem.setFileInfo(fileInfo);
            fileSystem.setUploaderName(uploaderName);

            //关闭trackerServer的连接
            trackerServer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return fileSystem;
    }

    public File download(String fileName, String fileUrl) {
        try {
            //加载fastDFS客户端的配置 文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");

            //创建tracker的客户端
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            //定义storage的客户端
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
            byte[] bytes = client.download_file1(fileUrl);
            File file = new File(fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
            //关闭trackerServer的连接
            trackerServer.close();
            return file;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 还需要通过调用service及dao将 fileSystem 存入数据库
     */
    public void insert(FileSystem fileSystem) {
        fileMapper.insert(fileSystem);
    }

    public FileSystem getFileSystemByFileName(String fileName) {
        QueryWrapper<FileSystem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_name", fileName);
        return fileMapper.selectOne(queryWrapper);
    }

    public List<FileSystem> getAllFiles() {
        return fileMapper.selectList(null);
    }
}
