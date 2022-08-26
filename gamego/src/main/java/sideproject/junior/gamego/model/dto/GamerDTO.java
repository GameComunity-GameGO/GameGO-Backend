package sideproject.junior.gamego.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sideproject.junior.gamego.model.entity.Gamer;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.model.entity.RegiTime;


@Getter
@Setter
@NoArgsConstructor
public class GamerDTO {

    @Getter
    @Setter
    public static class GamerRegistationDTO{
        private String gameUsername;
        private String introdution;
        private String game;
        private Member member;
        private RegiTime regiTime;
        private String hashTag;

        public Gamer toEntity(){
            return Gamer.builder()
                    .gameUsername(gameUsername)
                    .member(member)
                    .regiTime(regiTime)
                    .introdution(introdution)
                    .build();
        }

    }




}
