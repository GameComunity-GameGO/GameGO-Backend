package sideproject.junior.gamego.repository.chat;

import com.querydsl.jpa.impl.JPAQueryFactory;
import sideproject.junior.gamego.model.entity.QChatMessage;
import sideproject.junior.gamego.model.entity.QChatRoom;

import javax.persistence.EntityManager;

import static sideproject.junior.gamego.model.entity.QChatMessage.*;
import static sideproject.junior.gamego.model.entity.QChatRoom.*;

public class ChatMessageCustomRepositoryImpl implements ChatMessageCustomRepository{

    private final JPAQueryFactory queryFactory;

    public ChatMessageCustomRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Long getNoticeCount(Long roomId, Long messageId) {

        int size = queryFactory.selectFrom(chatMessage)
                .where(chatMessage.chatRoom.id.eq(roomId))
                .where(chatMessage.id.gt(messageId))
                .fetch().size();

        return (long) size;
    }
}
