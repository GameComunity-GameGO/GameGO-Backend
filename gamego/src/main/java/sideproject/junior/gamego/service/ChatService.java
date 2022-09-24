package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.junior.gamego.model.dto.chat.ResChatMessageDTO;
import sideproject.junior.gamego.model.dto.chat.ReqChatMessageDTO;
import sideproject.junior.gamego.model.dto.chat.ReqChatRoomDTO;
import sideproject.junior.gamego.model.dto.chat.ResChatRoomDTO;
import sideproject.junior.gamego.model.entity.ChatMessage;
import sideproject.junior.gamego.model.entity.ChatRoom;
import sideproject.junior.gamego.model.entity.ChatRoomJoinMember;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.repository.ChatMessageRepository;
import sideproject.junior.gamego.repository.ChatRoomRepository;
import sideproject.junior.gamego.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;

    public void createChatRoom(Long memberId, ReqChatRoomDTO dto) {

        Member member = memberRepository.findById(memberId).get();

        ChatRoom chatRoom = ChatRoom.builder()
                .roomName(dto.getRoomName())
                .capacity(dto.getCapacity())
                .build();

        chatRoomRepository.save(chatRoom);
    }

    public ResChatRoomDTO getChatRoom(Long roomId, Long memberId) {

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();

        List<ChatRoomJoinMember> members = new ArrayList<>(chatRoom.getChatRoomJoinMembers());

        for (ChatRoomJoinMember member : members) {
            if(member.getMember().getId().equals(memberId)){
                return chatRoom.toResDTO();
            }
        }

        return null;
    }

    public ResChatMessageDTO createChat(Long roomId, Long memberId, ReqChatMessageDTO dto) {

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();

        Member member = memberRepository.findById(memberId).get();

        ChatMessage chat = ChatMessage.builder()
                .content(dto.getContent())
                .member(member)
                .chatRoom(chatRoom)
                .build();

        ChatMessage savedChat = chatMessageRepository.save(chat);

        return savedChat.toDTO();
    }

    public void joinRoom(Long memberId, Long roomId) {

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();

        Member member = memberRepository.findById(memberId).get();

        ChatRoomJoinMember joinMember = ChatRoomJoinMember.builder()
                .chatRoom(chatRoom)
                .member(member)
                .build();

        chatRoom.joinMember(joinMember);
    }
}
