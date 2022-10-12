package sideproject.junior.gamego.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import sideproject.junior.gamego.model.entity.BoardType;

public interface BoardTypeRepository extends JpaRepository<BoardType, Long> {
    BoardType findByTitle(String type);

}
