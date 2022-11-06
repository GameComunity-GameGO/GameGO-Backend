package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sideproject.junior.gamego.model.dto.MemberDTO;
import sideproject.junior.gamego.model.dto.chat.MessageDTO;
import sideproject.junior.gamego.model.dto.chat.ReqChatMessageDTO;
import sideproject.junior.gamego.model.dto.chat.ResChatMessageDTO;
import sideproject.junior.gamego.model.dto.chat.ResChatRoomDTO;
import sideproject.junior.gamego.principal.SecurityUtil;
import sideproject.junior.gamego.service.ChatService;
import sideproject.junior.gamego.service.NoticeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class SocketController {

    private final SecurityUtil securityUtil;
    private final NoticeService noticeService;
    private final ChatService chatService;
    private final SimpMessagingTemplate template;

    @GetMapping("/notice/chat/room/list")
    public ResponseEntity<?> getChatAlarmList(){

        Long memberId = securityUtil.getMemberId();

        List<ResChatRoomDTO> chatAlarmList = noticeService.getChatNoticeList(memberId);

        return new ResponseEntity<>(chatAlarmList, HttpStatus.OK);
    }

    @MessageMapping("/notice/chat/room/{roomId}")  // /app/notice/chat/room/{roomId}
    public void chatNotice(@DestinationVariable String roomId){
        template.convertAndSend("/topic/chat/room/" + roomId, "메세지 알림 추가");
    }

    @MessageMapping("/chatting/room/{roomId}")
    public void chatting(@DestinationVariable String roomId, ReqChatMessageDTO dto){

        log.info("채팅 api 호출");

        /*Long memberId = securityUtil.getMemberId();

        ResChatMessageDTO chatMessage = chatService.createChat(Long.parseLong(roomId), memberId, dto);

        template.convertAndSend("/topic/chat/room/" + roomId, new MessageDTO<>(1, chatMessage));*/
    }

    @MessageMapping("/chat/room/{roomId}/enter")
    public void chatRoomEnter(@DestinationVariable String roomId, ReqChatMessageDTO dto){

        log.info("Enter_message = " , dto.getContent());

        log.info("ChatController.chatRoomEnter 호출");

        /*Long memberId = securityUtil.getMemberId();

        MemberDTO memberDTO = chatService.chatRoomEnter(memberId);

        template.convertAndSend("/topic/chat/room/" + roomId, new MessageDTO<>(2, memberDTO));*/
    }
}
