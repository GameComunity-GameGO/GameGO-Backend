package sideproject.junior.gamego.model.dto.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sideproject.junior.gamego.model.dto.MemberDTO;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private Long id;

    private String content;

    private MemberDTO memberDTO;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
