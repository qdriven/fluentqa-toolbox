package io.qmeta.wps.controller;

import io.qmeta.wps.yunfile.WpsYunFileService;
import io.qmeta.wps.yunfile.exception.YunException;
import io.qmeta.wps.yunfile.response.CreateCommitFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class WpiYunFileController {

    @Autowired
    WpsYunFileService yunFileService;

    @PostMapping("/wps/yunfile")
    public ResponseEntity<CreateCommitFileResponse> updateFile(@RequestParam("file") MultipartFile file,
                                                               @RequestParam(value = "openFileId", required = false) String openFileId) {

        try {
            CreateCommitFileResponse resp = yunFileService.createOrUpdateFile(file, openFileId);
            return ResponseEntity.ok(resp);
        } catch (IOException | YunException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
