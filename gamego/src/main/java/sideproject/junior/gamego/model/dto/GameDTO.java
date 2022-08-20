package sideproject.junior.gamego.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sideproject.junior.gamego.model.entity.Game;

@Getter
@Setter
@NoArgsConstructor
public class GameDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RegistrationDTO{
        private String name;
        private String content;

        public Game toEntity(){
            return Game.builder()
                    .name(name)
                    .content(content)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ChangeStateDTO{
        private String name;
        private String content;
    }
}
