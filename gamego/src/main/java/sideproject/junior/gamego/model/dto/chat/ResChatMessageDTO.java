package sideproject.junior.gamego.model.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sideproject.junior.gamego.model.dto.MemberDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ResChatMessageDTO {

    private Long id;
    private String content;
    private MemberDTO member;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
