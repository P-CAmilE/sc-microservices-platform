package com.scmp.file.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scmp.domain.FileSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Coconut Tree
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

    @Autowired
    FileMapper fileMapper;

    @Test
    public void getFileSystemByFileName() {
        String fileName = "The Road.md";
        QueryWrapper<FileSystem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_name", fileName);
        FileSystem fileSystem = fileMapper.selectOne(queryWrapper);
        System.out.println(fileSystem.toString());
    }
}
