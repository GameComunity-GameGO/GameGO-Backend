package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.junior.gamego.model.entity.CommunityBoard;
import sideproject.junior.gamego.model.entity.Likes;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.model.entity.Unlike;
import sideproject.junior.gamego.repository.LikesRepository;
import sideproject.junior.gamego.repository.MemberRepository;
import sideproject.junior.gamego.repository.UnlikeRepository;
import sideproject.junior.gamego.repository.board.BoardRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class LikesService {

    private final LikesRepository likesRepository;
    private final UnlikeRepository unlikeRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public int checkLike(Long memberId, String boardId) {
        if(likesRepository.findLikesByMemberIdAndCommunityBoardId(memberId, Long.parseLong(boardId)) == null){
            return 0;
        }else{
            return 1;
        }
    }

    public int checkULike(Long memberId, String boardId) {
        if(unlikeRepository.findUnlikeByMemberIdAndCommunityBoardId(memberId, Long.parseLong(boardId)) == null){
            return 0;
        }else{
            return 1;
        }
    }

    public int boardLike(Long boardId, Long memberId) {

        CommunityBoard findBoard = boardRepository.findById(boardId).get();

        Member member = memberRepository.findById(memberId).get();

        if(unlikeRepository.findUnlikeByMemberIdAndCommunityBoardId(memberId, boardId) == null){
            Likes newLike = Likes.builder()
                    .member(member)
                    .communityBoard(findBoard)
                    .build();

            likesRepository.save(newLike);

            return likesRepository.findLikesByCommunityBoardId(boardId).size();
        }else{
            return -1;
        }
    }

    public int boardUnlike(Long boardId, Long memberId) {

        CommunityBoard findBoard = boardRepository.findById(boardId).get();

        Member member = memberRepository.findById(memberId).get();

        if(likesRepository.findLikesByMemberIdAndCommunityBoardId(memberId, boardId) == null){
            Unlike newUnlike = Unlike.builder()
                    .member(member)
                    .communityBoard(findBoard)
                    .build();

            unlikeRepository.save(newUnlike);

            return unlikeRepository.findUnlikeByCommunityBoardId(boardId).size();
        }else{
            return -1;
        }
    }

    public int boardLikeDelete(Long boardId, Long memberId) {

        if(likesRepository.findLikesByMemberIdAndCommunityBoardId(memberId, boardId) != null){
            likesRepository.delete(likesRepository.findLikesByMemberIdAndCommunityBoardId(memberId, boardId));
            return 1;
        }else{
            return 0;
        }
    }

    public int boardUnlikeDelete(Long boardId, Long memberId) {

        if(unlikeRepository.findUnlikeByMemberIdAndCommunityBoardId(memberId, boardId) != null){
            unlikeRepository.delete(unlikeRepository.findUnlikeByMemberIdAndCommunityBoardId(memberId, boardId));
            return 1;
        }else{
            return 0;
        }
    }
}
