package sideproject.junior.gamego.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sideproject.junior.gamego.model.dto.reply.RequestReplyDTO;
import sideproject.junior.gamego.principal.SecurityUtil;
import sideproject.junior.gamego.service.ReplyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReplyController {

    private final ReplyService replyService;
    private final SecurityUtil securityUtil;

    @PostMapping("/board/{id}/reply")
    public ResponseEntity<?> createReply(@PathVariable String id,
                                         @RequestBody RequestReplyDTO dto){

        Long memberId = securityUtil.getMemberId();

        return new ResponseEntity<>(replyService.createReply(memberId, dto, Long.parseLong(id)), HttpStatus.OK);

    }

    @PutMapping("/reply/{replyId}")
    public ResponseEntity<?> updateReply(@PathVariable String replyId,
                                         @RequestBody String content){

        Long memberId = securityUtil.getMemberId();

        if(replyService.updateReply(Long.parseLong(replyId), memberId, content) != null){
            return new ResponseEntity<>(replyService.updateReply(Long.parseLong(replyId), memberId, content), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("작성된 댓글의 사용자가 아닙니다", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/reply/{replyId}")
    public ResponseEntity<?> deleteReply(@PathVariable String replyId){

        Long memberId = securityUtil.getMemberId();

        if(replyService.deleteReply(memberId, Long.parseLong(replyId))){
            return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("작성된 댓글의 사용자가 아닙니다", HttpStatus.BAD_REQUEST);
        }
    }
}
