package sideproject.junior.gamego.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.junior.gamego.model.entity.CommunityBoard;

public interface BoardRepository extends JpaRepository<CommunityBoard, Long>, BoardCustomRepository {

}
