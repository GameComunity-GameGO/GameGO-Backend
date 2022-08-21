package sideproject.junior.gamego.repository.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import sideproject.junior.gamego.model.dto.board.ResponseBoardDTO;
import sideproject.junior.gamego.model.entity.CommunityBoard;
import sideproject.junior.gamego.model.entity.QMember;
import sideproject.junior.gamego.model.entity.QUnlike;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static sideproject.junior.gamego.model.entity.QCommunityBoard.communityBoard;
import static sideproject.junior.gamego.model.entity.QImages.images;
import static sideproject.junior.gamego.model.entity.QLikes.likes;
import static sideproject.junior.gamego.model.entity.QMember.*;
import static sideproject.junior.gamego.model.entity.QReply.reply;
import static sideproject.junior.gamego.model.entity.QUnlike.*;

public class BoardCustomRepositoryImpl implements BoardCustomRepository{

    private final JPAQueryFactory queryFactory;

    public BoardCustomRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ResponseBoardDTO> getBoardList(Pageable pageable) {

        List<CommunityBoard> queryResult = queryFactory
                .selectFrom(communityBoard)
                .leftJoin(communityBoard.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(communityBoard.createdDate.desc())
                .fetch();

        List<ResponseBoardDTO> result = queryResult.stream().map(CommunityBoard::toDTO).collect(Collectors.toList());

        return new PageImpl<>(result, pageable, result.size());
    }

    @Override
    public CommunityBoard getBoard(Long boardId){

        return queryFactory
                .selectFrom(communityBoard)
                .where(communityBoard.id.eq(boardId))
                .leftJoin(communityBoard.replyList, reply)
                .leftJoin(communityBoard.imageList, images)
                .leftJoin(communityBoard.likes, likes)
                .leftJoin(communityBoard.unlike, unlike)
                .leftJoin(communityBoard.member, member)
                .fetchOne();
    }
}
