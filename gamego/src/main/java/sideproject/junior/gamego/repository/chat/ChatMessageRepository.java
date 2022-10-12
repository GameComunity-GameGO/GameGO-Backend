package sideproject.junior.gamego.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import sideproject.junior.gamego.model.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
