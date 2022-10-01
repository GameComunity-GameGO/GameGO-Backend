package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.junior.gamego.model.dto.MemberDTO;
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

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
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

        log.info("ChatService.joinRoom-chatRoom.id = " + chatRoom.getId());

        Member member = memberRepository.findById(memberId).get();

        log.info("ChatService.joinRoom-member.id = " + member.getId());

        ChatRoomJoinMember joinMember = ChatRoomJoinMember.builder()
                .chatRoom(chatRoom)
                .member(member)
                .build();

        chatRoom.joinMember(joinMember);
    }

    public List<ResChatRoomDTO> getChatRoomList(Long memberId) {

        Member member = memberRepository.findById(memberId).get();

        List<ResChatRoomDTO> getRoomList = new ArrayList<>();

        for (ChatRoomJoinMember chatRoomJoinMember : member.getChatRoomJoinMembers()) {
            getRoomList.add(chatRoomJoinMember.getChatRoom().toResDTO());
        }

        return getRoomList;
    }

    public MemberDTO chatRoomEnter(Long memberId) {

        Member member = memberRepository.findById(memberId).get();

        return member.toDTO();
    }
}
