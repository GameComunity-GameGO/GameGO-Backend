package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.junior.gamego.model.dto.reply.ReplyDTO;
import sideproject.junior.gamego.model.dto.reply.RequestReplyDTO;
import sideproject.junior.gamego.model.entity.CommunityBoard;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.model.entity.Reply;
import sideproject.junior.gamego.repository.MemberRepository;
import sideproject.junior.gamego.repository.ReplyRepository;
import sideproject.junior.gamego.repository.board.BoardRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public List<ReplyDTO> createReply(Long memberId, RequestReplyDTO dto, Long boardId) {

        Member member = memberRepository.findById(memberId).get();

        CommunityBoard board = boardRepository.findById(boardId).get();

        replyRepository.save(Reply.builder()
                .content(dto.getContent())
                .communityBoard(board)
                .member(member)
                .build());

        return replyRepository.findAllByCommunityBoardId(boardId).stream().map(Reply::toDTO).collect(Collectors.toList());
    }

    public ReplyDTO updateReply(Long replyId, Long memberId, String content) {

        Reply reply = replyRepository.findById(replyId).get();

        if (Objects.equals(reply.getMember().getId(), memberId)){
            Reply updateReply = reply.update(content);
            return updateReply.toDTO();
        }else{
            return null;
        }
    }

    public boolean deleteReply(Long memberId, Long replyId) {

        if(Objects.equals(memberId, replyRepository.findById(replyId).get().getId())){
            replyRepository.deleteById(replyId);
            return true;
        }else{
            return false;
        }
    }
}
