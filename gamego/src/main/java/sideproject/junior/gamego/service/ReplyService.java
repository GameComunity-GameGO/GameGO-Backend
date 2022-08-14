package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sideproject.junior.gamego.model.dto.reply.ReplyDTO;
import sideproject.junior.gamego.model.dto.reply.RequestReplyDTO;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.model.entity.Reply;
import sideproject.junior.gamego.repository.MemberRepository;
import sideproject.junior.gamego.repository.ReplyRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    public List<ReplyDTO> createReply(Long memberId, RequestReplyDTO dto, Long boardId) {

        Member member = memberRepository.findById(memberId).get();

        replyRepository.save(Reply.builder()
                .content(dto.getContent())
                .member(member)
                .build());

        return replyRepository.findAllByCommunityBoardId(boardId).stream().map(Reply::toDTO).collect(Collectors.toList());
    }

    public ReplyDTO updateReply(Long replyId, Long memberId, String content) {

        Reply reply = replyRepository.findById(replyId).get();

        Member member = memberRepository.findById(memberId).get();

        if (Objects.equals(reply.getMember().getId(), memberId)){
            Reply updateReply = reply.update(content);
            return updateReply.toDTO();
        }else{
            return null;
        }
    }

    public boolean deleteReply(Long memberId, Long replyId) {

        if(memberId == replyRepository.findById(replyId).get().getId()){
            replyRepository.deleteById(replyId);
            return true;
        }else{
            return false;
        }
    }
}
