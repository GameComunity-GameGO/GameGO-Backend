package sideproject.junior.gamego.repository.chat;

public interface ChatRoomCustomRepository {

    Long getNoticeCount(Long roomId, Long messageId);
}
