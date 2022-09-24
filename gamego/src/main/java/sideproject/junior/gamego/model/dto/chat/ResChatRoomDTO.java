package sideproject.junior.gamego.model.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sideproject.junior.gamego.model.dto.MemberDTO;

import java.util.List;

@Getter
@Setter
@Builder
public class ResChatRoomDTO {

    private Long roomId;
    private String roomName;
    private List<ChatRoomJoinMemberDTO> memberList;
    private MemberDTO host;
    private List<ResChatMessageDTO> chatMessageList;
}
