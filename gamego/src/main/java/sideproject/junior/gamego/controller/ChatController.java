package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import sideproject.junior.gamego.model.dto.chat.ReqChatMessageDTO;
import sideproject.junior.gamego.model.dto.chat.ReqChatRoomDTO;
import sideproject.junior.gamego.model.dto.chat.ResChatMessageDTO;
import sideproject.junior.gamego.model.dto.chat.ResChatRoomDTO;
import sideproject.junior.gamego.model.entity.ChatMessage;
import sideproject.junior.gamego.principal.SecurityUtil;
import sideproject.junior.gamego.service.ChatService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Log4j2
public class ChatController {

    private final SecurityUtil securityUtil;
    private final ChatService chatService;
    private final SimpMessagingTemplate template;

    @PostMapping("/chat/room")
    public ResponseEntity<?> createChatRoom(@RequestBody ReqChatRoomDTO dto){

        Long memberId = securityUtil.getMemberId();

        chatService.createChatRoom(memberId, dto);

        return new ResponseEntity<>("chatRoom 생성!", HttpStatus.OK);
    }

    @GetMapping("/chat/room/{roomId}")
    public ResponseEntity<?> getRoom(@PathVariable String roomId){

        Long memberId = securityUtil.getMemberId();

        ResChatRoomDTO chatRoom = chatService.getChatRoom(Long.parseLong(roomId), memberId);

        if(chatRoom == null){
            return new ResponseEntity<>("잘못된 접근입니다", HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(chatRoom, HttpStatus.OK);
        }
    }

    @MessageMapping("/chatting/room/{roomId}")
    public void chatting(@DestinationVariable String roomId, ReqChatMessageDTO dto){

        Long memberId = securityUtil.getMemberId();

        log.info("채팅 api 호출");

        ResChatMessageDTO chatMessage = chatService.createChat(Long.parseLong(roomId), memberId, dto);

        template.convertAndSend("/topic/chat/room/" + roomId, chatMessage);
    }
}
