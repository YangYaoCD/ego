package com.ego.upload.controller;

import com.ego.upload.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author YangYao
 * @date 2020/9/13 15:58
 * @Description
 */
@RestController
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/image")
    public ResponseEntity<String> upload(@RequestParam("file")MultipartFile file){
        String imageUrl=uploadService.upload(file);
        if (StringUtils.isBlank(imageUrl)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(imageUrl);
    }
}
