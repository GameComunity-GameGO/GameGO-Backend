package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sideproject.junior.gamego.model.dto.chat.ResChatRoomDTO;
import sideproject.junior.gamego.principal.SecurityUtil;
import sideproject.junior.gamego.service.NoticeService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class NoticeController {

    private final SecurityUtil securityUtil;
    private final NoticeService noticeService;
    private final SimpMessagingTemplate template;

    @GetMapping("/notice/chat/room/list")
    public ResponseEntity<?> getChatAlarmList(){

        Long memberId = securityUtil.getMemberId();

        List<ResChatRoomDTO> chatAlarmList = noticeService.getChatNoticeList(memberId);

        return new ResponseEntity<>(chatAlarmList, HttpStatus.OK);
    }

    @MessageMapping("/notice/chat/room/{roomId}")
    public void chatNotice(@DestinationVariable String roomId){
        template.convertAndSend("/topic/chat/room/" + roomId, "메세지 알림 추가");
    }
}
