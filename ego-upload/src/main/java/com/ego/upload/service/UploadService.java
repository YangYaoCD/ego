package com.ego.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author YangYao
 * @date 2020/9/13 16:04
 * @Description
 */
//lombok提供的日志
@Slf4j
@Service
public class UploadService {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    //支持的文件类型
    private static final List<String> suffixes= Arrays.asList("image/png","image/jpeg");

    public String upload(MultipartFile file) {
        //检查图片文件类型
        if (!suffixes.contains(file.getContentType())){
            log.debug("文件类型不匹配!",file.getContentType());
            return null;
        }
        //检查图片内容是否正确
        try {
            if (ImageIO.read(file.getInputStream())==null){
                log.info("文件内容不正确。");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //保存到硬盘
        File dir = new File("D:\\Java\\ego\\ego-upload\\src\\main\\resources\\images\\");
        if (!dir.exists()){
            dir.mkdirs();
        }
//        try {
//            file.transferTo(new File("D:\\Java\\ego\\ego-upload\\src\\main\\resources\\images\\", file.getOriginalFilename()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "http://image.ego.com/"+file.getOriginalFilename();

        String fullPath=null;
        try {
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1), null);
            fullPath = storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "http://image.ego.com/"+fullPath;
    }
}
