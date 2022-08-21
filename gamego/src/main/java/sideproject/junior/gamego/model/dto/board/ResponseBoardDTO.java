package sideproject.junior.gamego.model.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sideproject.junior.gamego.model.dto.ImageDTO;
import sideproject.junior.gamego.model.dto.LikeDTO;
import sideproject.junior.gamego.model.dto.MemberDTO;
import sideproject.junior.gamego.model.dto.UnlikeDTO;
import sideproject.junior.gamego.model.dto.reply.ReplyDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class ResponseBoardDTO {

    private Long boardId;

    private String title;

    private String contents;

    private String category;

    private List<LikeDTO> likes;

    private List<UnlikeDTO> unlike;

    private MemberDTO memberDTO;

    private List<ReplyDTO> replyList;

    private List<ImageDTO> imageList;

    private int checkLikes;

    private int checkUnlike;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;


}
