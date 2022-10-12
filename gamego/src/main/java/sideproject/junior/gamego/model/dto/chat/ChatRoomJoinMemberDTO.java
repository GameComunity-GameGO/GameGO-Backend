package sideproject.junior.gamego.model.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sideproject.junior.gamego.model.dto.MemberDTO;

@Getter
@Setter
@Builder
public class ChatRoomJoinMemberDTO {

    private Long id;
    private MemberDTO member;
    private ResChatRoomDTO chatRoom;
    private Long checkPoint;
}
