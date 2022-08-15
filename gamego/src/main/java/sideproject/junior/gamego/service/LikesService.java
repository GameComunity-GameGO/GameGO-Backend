package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.junior.gamego.model.entity.CommunityBoard;
import sideproject.junior.gamego.model.entity.Likes;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.repository.LikesRepository;
import sideproject.junior.gamego.repository.MemberRepository;
import sideproject.junior.gamego.repository.board.BoardRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class LikesService {

    private final LikesRepository likesRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public int checkLike(Long memberId, String boardId) {
        if(likesRepository.findLikesByMemberIdAndCommunityBoardId(memberId, Long.parseLong(boardId)) == null){
            return 0;
        }else{
            return 1;
        }
    }

    public int boardLike(Long boardId, Long memberId) {

        CommunityBoard findBoard = boardRepository.findById(boardId).get();

        Member member = memberRepository.findById(memberId).get();

        Likes newLike = Likes.builder()
                .member(member)
                .communityBoard(findBoard)
                .build();

        likesRepository.save(newLike);

        return likesRepository.findLikesByCommunityBoardId(boardId).size();
    }

    public int boardLikeDelete(Long boardId, Long memberId) {

        if(likesRepository.findLikesByMemberIdAndCommunityBoardId(memberId, boardId) != null){
            likesRepository.delete(likesRepository.findLikesByMemberIdAndCommunityBoardId(memberId, boardId));
            return 1;
        }else{
            return 0;
        }
    }
}
