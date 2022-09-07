package sideproject.junior.gamego.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sideproject.junior.gamego.model.entity.CommunityBoard;
import sideproject.junior.gamego.model.entity.Images;
import sideproject.junior.gamego.repository.ImagesRepository;
import sideproject.junior.gamego.repository.board.BoardRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final BoardRepository boardRepository;
    private final ImagesRepository imagesRepository;

    public String uploadImage(MultipartFile multipartFile) {

        // forEach 구문을 통해 multipartFile로 넘어온 파일들 하나씩 fileNameList에 추가

        String fileName = multipartFile.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try(InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch(IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }

        return "https://seongho-bucket.s3.ap-northeast-2.amazonaws.com/board/" + fileName;
    }

    public void deleteImage(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }

    private String createFileName(String fileName) { // 먼저 파일 업로드 시, 파일명을 난수화하기 위해 random으로 돌립니다.
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) { // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단하였습니다.
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }

    public void insertBoardImages(Long boardId, String imgURL) {

        CommunityBoard board = boardRepository.findById(boardId).get();

            Images image = Images.builder()
                    .communityBoard(board)
                    .imgURL(imgURL)
                    .build();

            Images savedImg = imagesRepository.save(image);

            board.insertImage(savedImg);
        }

    public void updateBoardImages(Long boardId, String imgURL) {

        if(imagesRepository.findAllByCommunityBoardId(boardId) != null){
            imagesRepository.deleteImagesByCommunityBoardId(boardId);
        }
        insertBoardImages(boardId, imgURL);
    }

    public void deleteBoardImage(Long boardId) {

        CommunityBoard board = boardRepository.findById(boardId).get();

        for (Images images : board.getImageList()) {
            deleteImage(images.getImgURL());
        }
    }
}
