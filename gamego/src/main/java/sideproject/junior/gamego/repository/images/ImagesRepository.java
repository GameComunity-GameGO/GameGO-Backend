package sideproject.junior.gamego.repository.images;

import org.springframework.data.jpa.repository.JpaRepository;
import sideproject.junior.gamego.model.entity.Images;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Long>, ImageCustomRepository{

    List<Images> findAllByCommunityBoardId(Long communityBoardId);
}
