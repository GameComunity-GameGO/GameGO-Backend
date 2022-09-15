package sideproject.junior.gamego.repository.images;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import sideproject.junior.gamego.model.entity.Images;
import java.util.List;
import static sideproject.junior.gamego.model.entity.QImages.images;


public class ImagesCustomRepositoryImpl implements ImageCustomRepository{

    private final JPAQueryFactory queryFactory;

    public ImagesCustomRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Images> deleteImagesByBoardId(Long boardId) {

        return queryFactory.selectFrom(images)
                .where(images.communityBoard.id.eq(boardId))
                .fetch();
    }
}
