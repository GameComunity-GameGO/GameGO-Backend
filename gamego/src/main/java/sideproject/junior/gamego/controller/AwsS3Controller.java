package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sideproject.junior.gamego.service.AwsS3Service;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
@Log4j2
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @PostMapping("/images")
    public ResponseEntity<Object> uploadFile(@RequestParam("img") MultipartFile[] multipartFile) {

        log.info("AwsS3Controller.uploadFile 호출");
        
        MultipartFile file = multipartFile[0];

        return ResponseEntity.ok(awsS3Service.uploadImage(file));
    }

    @DeleteMapping("/images")
    public ResponseEntity<Void> deleteFile(@RequestParam String fileName) {
        awsS3Service.deleteImage(fileName);
        return ResponseEntity.ok(null);
    }
}
