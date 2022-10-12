package sideproject.junior.gamego.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import sideproject.junior.gamego.model.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomCustomRepository {
}
