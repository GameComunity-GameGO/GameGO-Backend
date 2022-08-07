package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1")
public class RefreshTokenApiController {

    @PostMapping("/accessToken")
    public void accessTokenRePublishing(){
        log.info("액세스 토큰 새 발급");
    }
}
