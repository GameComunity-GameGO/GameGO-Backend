package sideproject.junior.gamego.model.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ReqChatRoomDTO {

    private String roomName;
    private String capacity;
    private List<String> HashTag;
}
