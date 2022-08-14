package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sideproject.junior.gamego.model.dto.board.RequestBoardDTO;
import sideproject.junior.gamego.model.dto.board.ResponseBoardDTO;
import sideproject.junior.gamego.model.entity.Category;
import sideproject.junior.gamego.model.entity.CommunityBoard;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.repository.ImagesRepository;
import sideproject.junior.gamego.repository.MemberRepository;
import sideproject.junior.gamego.repository.board.BoardRepository;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardService {

    private final BoardRepository boardRepository;
    private final CategoryService categoryService;
    private final ImagesRepository imagesRepository;
    private final MemberRepository memberRepository;

    public Page<ResponseBoardDTO> getBoardList(Pageable pageable) {

        return boardRepository.getBoardList(pageable);
    }

    public ResponseBoardDTO getBoard(Long boardId){
        return boardRepository.getBoard(boardId).toDTO();
    }

    public ResponseBoardDTO createBoard(Long memberId, RequestBoardDTO dto) {

        log.info("BoardService.createBoard 호출");

        String category = dto.getCategory();

        Member member = memberRepository.findById(memberId).get();

        Category getCategory = categoryService.getCategory(category);

        CommunityBoard createBoard = CommunityBoard.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .member(member)
                .category(getCategory)
                .build();

        CommunityBoard board = boardRepository.save(createBoard);

        log.info("board = " + board);

        return board.toDTO();
    }

    public ResponseBoardDTO updateBoard(Long memberId, RequestBoardDTO dto, Long boarId) {

        CommunityBoard getBoard = boardRepository.findById(boarId).get();

        if(getBoard.getMember().getId() == memberId) {

            String category = dto.getCategory();

            Category getCategory = categoryService.getCategory(category);

            CommunityBoard updateBoard = getBoard.update(dto.getTitle(), dto.getContents(), getCategory);

            return updateBoard.toDTO();
        }else{
            return null;
        }
    }

    public int deleteBoard(Long boardId, Long memberId) {

        if(boardRepository.findById(boardId).get().getMember().getId() != memberId) {

            imagesRepository.deleteImagesByCommunityBoardId(boardId);

            boardRepository.deleteById(boardId);

            return 1;
        }else{
            return 0;
        }
    }
}
