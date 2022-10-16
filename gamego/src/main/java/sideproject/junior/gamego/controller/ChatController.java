package sideproject.junior.gamego.controller;

import com.amazonaws.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import sideproject.junior.gamego.model.dto.MemberDTO;
import sideproject.junior.gamego.model.dto.chat.*;
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

        log.info("chatController.createChatRoom 호출");

        chatService.createChatRoom(memberId, dto);

        return new ResponseEntity<>("chatRoom 생성!", HttpStatus.OK);
    }

    @GetMapping("/chat/room/{roomId}")
    public ResponseEntity<?> getRoom(@PathVariable String roomId){

        Long memberId = securityUtil.getMemberId();

        log.info("chatController.getRoom 호출");

        ResChatRoomDTO chatRoom = chatService.getChatRoom(Long.parseLong(roomId), memberId);

        if(chatRoom == null){
            return new ResponseEntity<>("잘못된 접근입니다", HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(chatRoom, HttpStatus.OK);
        }
    }

    @GetMapping("/chat/room/list")
    public ResponseEntity<?> getChatRoomList(){

        Long memberId = securityUtil.getMemberId();

        log.info("chatController.getChatRoomList 호출");

        return new ResponseEntity<>(chatService.getChatRoomList(memberId), HttpStatus.OK);
    }

    @PostMapping("/chat/room/{roomId}/join") // 채팅방 처음 입장
    public ResponseEntity<?> joinRoom(@PathVariable String roomId){

        Long memberId = securityUtil.getMemberId();

        log.info("ChatController.joinRoom 호출");

        chatService.joinRoom(memberId, Long.parseLong(roomId));

        return new ResponseEntity<>("채팅방 처음 입장 성공", HttpStatus.OK);
    }

    @MessageMapping("/chatting/room/{roomId}")
    public void chatting(@DestinationVariable String roomId, ReqChatMessageDTO dto){

        Long memberId = securityUtil.getMemberId();

        log.info("채팅 api 호출");

        ResChatMessageDTO chatMessage = chatService.createChat(Long.parseLong(roomId), memberId, dto);

        template.convertAndSend("/topic/chat/room/" + roomId, new MessageDTO<>(1, chatMessage));
    }

    @MessageMapping("/chat/room/{roomId}/enter")
    public void chatRoomEnter(@DestinationVariable String roomId, ReqChatMessageDTO dto){

        Long memberId = securityUtil.getMemberId();

        log.info("Enter_message = " , dto.getContent());

        log.info("ChatController.chatRoomEnter 호출");

        MemberDTO memberDTO = chatService.chatRoomEnter(memberId);

        template.convertAndSend("/topic/chat/room/" + roomId, new MessageDTO<>(2, memberDTO));
    }

    @PostMapping("/chat/room/{roomId}/msg/{msgId}/checkPoint")
    public void chatRoomCheckPoint(@PathVariable String roomId, @PathVariable String msgId){

        Long memberId = securityUtil.getMemberId();

        log.info("ChatController.checkPoint 호출");

        chatService.updateCheckPoint(memberId, Long.parseLong(roomId), Long.parseLong(msgId));
    }
}
