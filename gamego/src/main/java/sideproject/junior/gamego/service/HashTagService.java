package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.junior.gamego.exception.hashtag.HashTagException;
import sideproject.junior.gamego.exception.hashtag.HashTagExceptionType;
import sideproject.junior.gamego.model.dto.HashTagDTO;
import sideproject.junior.gamego.model.entity.HashTag;
import sideproject.junior.gamego.repository.HashTagRepository;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashTagService {

    private final HashTagRepository hashTagRepository;

    public List<HashTag> getHashTagListApi(){
        return hashTagRepository.findAll();
    }

    @Transactional
    public ResponseEntity<?> hashTagRegistrationApi(HashTagDTO.RegistrationDTO registrationDTO){
        if (registrationDTO.getName()==null){
            return new ResponseEntity<>(new HashTagException(HashTagExceptionType.NAME_REQUEST_NULL), HttpStatus.OK);
        }else {
            HashTag hashTag = registrationDTO.toEntity();
            hashTagRepository.save(hashTag);
            return new ResponseEntity<>("해쉬태그 등록 API 성공",HttpStatus.OK);
        }
    }
}
