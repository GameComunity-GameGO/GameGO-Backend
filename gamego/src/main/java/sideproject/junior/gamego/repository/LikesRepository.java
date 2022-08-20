package sideproject.junior.gamego.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sideproject.junior.gamego.model.entity.Likes;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Likes findLikesByMemberIdAndCommunityBoardId(Long memberId, Long communityBoardId);

    List<Likes> findLikesByCommunityBoardId(Long boardId);
}
