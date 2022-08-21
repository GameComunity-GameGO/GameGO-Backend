package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.junior.gamego.model.dto.board.RequestBoardDTO;
import sideproject.junior.gamego.model.dto.board.ResponseBoardDTO;
import sideproject.junior.gamego.model.dto.reply.ReplyDTO;
import sideproject.junior.gamego.model.entity.Category;
import sideproject.junior.gamego.model.entity.CommunityBoard;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.model.entity.Reply;
import sideproject.junior.gamego.repository.ImagesRepository;
import sideproject.junior.gamego.repository.MemberRepository;
import sideproject.junior.gamego.repository.ReplyRepository;
import sideproject.junior.gamego.repository.board.BoardRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class BoardService {

    private final BoardRepository boardRepository;
    private final CategoryService categoryService;
    private final ImagesRepository imagesRepository;
    private final MemberRepository memberRepository;
    private final ReplyRepository replyRepository;

    public Page<ResponseBoardDTO> getBoardList(Pageable pageable) {

        return boardRepository.getBoardList(pageable);
    }

    public ResponseBoardDTO getBoard(Long boardId, Long memberId){

        ResponseBoardDTO responseBoardDTO = boardRepository.getBoard(boardId).toDTO();

        List<Reply> replyList = replyRepository.findAllByCommunityBoardId(boardId);

        for (Reply reply : replyList) {
            if(reply.getMember().getId().equals(memberId)){
                ReplyDTO myReply = responseBoardDTO.getReplyList().stream().filter(replyDTO -> replyDTO.getId().equals(reply.getId())).findAny().get();
                myReply.setCheckMyReply(1);
            }
        }

        return responseBoardDTO;
    }

    public ResponseBoardDTO createBoard(Long memberId, RequestBoardDTO dto) {

        log.info("BoardService.createBoard 호출");

        String category = dto.getCategory();

        Member member = memberRepository.findById(memberId).get();

        log.info("BoardService.createBoard - member = " + member.getId());

        Category getCategory = categoryService.getCategory(category);

        log.info("BoardService.createBoard - category = " + getCategory.getTitle());

        CommunityBoard createBoard = CommunityBoard.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .member(member)
                .category(getCategory)
                .build();

        CommunityBoard board = boardRepository.save(createBoard);

        log.info("board = " + board.getId() );

        return board.toResponseDTO(member);
    }

    public ResponseBoardDTO updateBoard(Long memberId, RequestBoardDTO dto, Long boarId) {

        CommunityBoard getBoard = boardRepository.findById(boarId).get();

        if(Objects.equals(getBoard.getMember().getId(), memberId)) {

            String category = dto.getCategory();

            Category getCategory = categoryService.getCategory(category);

            CommunityBoard updateBoard = getBoard.update(dto.getTitle(), dto.getContents(), getCategory);

            return updateBoard.toDTO();
        }else{
            return null;
        }
    }

    public int deleteBoard(Long boardId, Long memberId) {

        if(Objects.equals(boardRepository.findById(boardId).get().getMember().getId(), memberId)) {


            imagesRepository.deleteImagesByCommunityBoardId(boardId);

            boardRepository.deleteById(boardId);

            return 1;
        }else{
            return 0;
        }
    }
}
