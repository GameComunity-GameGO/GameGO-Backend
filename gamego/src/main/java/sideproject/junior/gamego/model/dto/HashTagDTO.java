package sideproject.junior.gamego.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sideproject.junior.gamego.model.entity.HashTag;

@Getter
@Setter
@NoArgsConstructor
public class HashTagDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RegistrationDTO{
        private String name;

        public HashTag toEntity(){
            return HashTag.builder()
                    .name(name)
                    .build();
        }
    }
}
