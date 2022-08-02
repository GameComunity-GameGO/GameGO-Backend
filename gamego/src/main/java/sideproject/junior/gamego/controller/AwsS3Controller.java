package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sideproject.junior.gamego.service.AwsS3Service;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @PostMapping("/images")
    public ResponseEntity<List<String>> uploadFile(@RequestPart List<MultipartFile> multipartFile) {
        return ResponseEntity.ok(awsS3Service.uploadImage(multipartFile));
    }

    @PostMapping("/board/{id}/images")
    public ResponseEntity<?> insertBoardImages(@RequestPart List<MultipartFile> multipartFile,
                                               @PathVariable String id){

        List<String> imgURL = awsS3Service.uploadImage(multipartFile);

        awsS3Service.insertBoardImages(Long.parseLong(id), imgURL);

        return new ResponseEntity<>(imgURL, HttpStatus.OK);
    }

    @PutMapping("/board/{id}/images")
    public ResponseEntity<?> updateBoardImages(@RequestPart List<MultipartFile> multipartFile,
                                               @PathVariable String id){

        List<String> imgURL = awsS3Service.uploadImage(multipartFile);

        awsS3Service.updateBoardImages(Long.parseLong(id), imgURL);

        return new ResponseEntity<>(imgURL, HttpStatus.OK);
    }

    @DeleteMapping("/board/{id}/images") // s3 api 먼저 실행
    public ResponseEntity<?> deleteBoardImages(@PathVariable String id){

        awsS3Service.deleteBoardImage(Long.parseLong(id));

        return new ResponseEntity<>(1, HttpStatus.OK);
    }


    @DeleteMapping("/images")
    public ResponseEntity<Void> deleteFile(@RequestParam String fileName) {

        awsS3Service.deleteImage(fileName);
        return ResponseEntity.ok(null);
    }
}
