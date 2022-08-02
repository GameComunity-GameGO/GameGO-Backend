package sideproject.junior.gamego.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class HealthCheck {

    @PostMapping("/ping")
    public ResponseEntity<String> healthCheck(){
        log.info("health Check");
        System.out.println("pingpong = " );
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }
}
