package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import sideproject.junior.gamego.model.dto.MemberDTO;
import sideproject.junior.gamego.model.dto.chat.MessageDTO;
import sideproject.junior.gamego.model.dto.chat.ReqChatMessageDTO;
import sideproject.junior.gamego.model.dto.chat.ResChatMessageDTO;
import sideproject.junior.gamego.model.dto.chat.ResChatRoomDTO;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.principal.SecurityUtil;
import sideproject.junior.gamego.repository.MemberRepository;
import sideproject.junior.gamego.service.ChatService;
import sideproject.junior.gamego.service.NoticeService;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Log4j2
public class SocketController {

    private final SecurityUtil securityUtil;
    private final NoticeService noticeService;
    private final ChatService chatService;
    private final SimpMessagingTemplate template;
    private final MemberRepository memberRepository;

    private static final Map<String, String> SESSIONS = new HashMap<>();

    @EventListener(SessionConnectEvent.class)
    public void onConnect(SessionConnectEvent event){
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
        String username = event.getMessage().getHeaders().get("nativeHeaders").toString().split("User=\\[")[1].split("]")[0];

        SESSIONS.put(sessionId, username);
    }

    @EventListener(SessionConnectEvent.class)
    public void onDisconnect(SessionDisconnectEvent event){
        SESSIONS.remove(event.getSessionId());
    }

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
    public void chatting(@DestinationVariable String roomId, ReqChatMessageDTO dto, SimpMessageHeaderAccessor accessor){

        log.info("채팅 api 호출");

        String username = SESSIONS.get(accessor.getSessionId());

        log.info("username = " + username);

        Member member = memberRepository.findByUsername(username).get();

        ResChatMessageDTO chatMessage = chatService.createChat(Long.parseLong(roomId), member.getId(), dto);

        template.convertAndSend("/topic/chat/room/" + roomId, new MessageDTO<>(1, chatMessage));
    }

    @MessageMapping("/chat/room/{roomId}/enter")
    public void chatRoomEnter(@DestinationVariable String roomId, ReqChatMessageDTO dto, SimpMessageHeaderAccessor accessor){

        log.info("Enter_message = " , dto.getContent());

        log.info("ChatController.chatRoomEnter 호출");

        String username = SESSIONS.get(accessor.getSessionId());

        log.info("username = " + username);

        Member member = memberRepository.findByUsername(username).get();

        MemberDTO memberDTO = chatService.chatRoomEnter(member.getId());

        template.convertAndSend("/topic/chat/room/" + roomId, new MessageDTO<>(2, memberDTO));
    }
}
