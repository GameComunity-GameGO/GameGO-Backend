package sideproject.junior.gamego.repository.images;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import sideproject.junior.gamego.model.entity.Images;
import sideproject.junior.gamego.model.entity.QImages;
import sideproject.junior.gamego.model.entity.CommunityBoard;

import java.util.List;

import static sideproject.junior.gamego.model.entity.QCommunityBoard.communityBoard;
import static sideproject.junior.gamego.model.entity.QImages.images;


public class ImagesCustomRepositoryImpl implements ImageCustomRepository{

    private final JPAQueryFactory queryFactory;

    public ImagesCustomRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Images> deleteImagesByBoardId(Long boardId) {

        List<Images> getImages = queryFactory.selectFrom(images)
                .where(images.communityBoard.id.eq(boardId))
                .fetch();

        return getImages;
    }
}
