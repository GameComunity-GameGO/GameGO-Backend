package sideproject.junior.gamego.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sideproject.junior.gamego.model.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByCommunityBoardId(Long communityBoardId);
}
