package sideproject.junior.gamego.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import sideproject.junior.gamego.model.entity.ChatRoomJoinMember;

public interface ChatRoomJoinMemberRepository extends JpaRepository<ChatRoomJoinMember, Long> {

    ChatRoomJoinMember findChatRoomJoinMemberByChatRoomIdAndMemberId(Long chatRoomId, Long memberId);
}
