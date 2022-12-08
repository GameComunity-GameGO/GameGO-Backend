package sideproject.junior.gamego.repository.chat;

public interface ChatMessageCustomRepository {

    Long getNoticeCount(Long roomId, Long messageId);
}
