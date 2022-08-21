package sideproject.junior.gamego.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sideproject.junior.gamego.model.entity.Unlike;

import java.util.List;

public interface UnlikeRepository extends JpaRepository<Unlike, Long> {
    Unlike findUnlikeByMemberIdAndCommunityBoardId(Long memberId, Long communityBoardId);

    List<Unlike> findUnlikeByCommunityBoardId(Long boardId);
}
