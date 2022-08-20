package sideproject.junior.gamego.repository.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sideproject.junior.gamego.model.dto.board.ResponseBoardDTO;
import sideproject.junior.gamego.model.entity.CommunityBoard;

public interface BoardCustomRepository {
    Page<ResponseBoardDTO> getBoardList(Pageable pageable);
    CommunityBoard getBoard(Long boardId);
}
