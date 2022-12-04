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
    
    @GetMapping("/chat/all/room/list")
    public ResponseEntity<?> getAllChatRoomList(){
        
        log.info("chatController.getAllChatRoomList 호출");

        return new ResponseEntity<>(chatService.getAllChatRoomList(), HttpStatus.OK);
    }

    @PostMapping("/chat/room/{roomId}/join") // 채팅방 처음 입장
    public ResponseEntity<?> joinRoom(@PathVariable String roomId){

        Long memberId = securityUtil.getMemberId();

        log.info("ChatController.joinRoom 호출");

        if(chatService.joinRoom(memberId, Long.parseLong(roomId)) == 0){
            return new ResponseEntity<>("채팅방 처음 입장 성공", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("채팅방에 이미 있음", HttpStatus.OK);
    }

    @PostMapping("/chat/room/{roomId}/msg/{msgId}/checkPoint")
    public void chatRoomCheckPoint(@PathVariable String roomId, @PathVariable String msgId){

        Long memberId = securityUtil.getMemberId();

        log.info("ChatController.checkPoint 호출");

        chatService.updateCheckPoint(memberId, Long.parseLong(roomId), Long.parseLong(msgId));
    }
}
