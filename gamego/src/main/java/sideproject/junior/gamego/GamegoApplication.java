package sideproject.junior.gamego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GamegoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamegoApplication.class, args);
    }

}
