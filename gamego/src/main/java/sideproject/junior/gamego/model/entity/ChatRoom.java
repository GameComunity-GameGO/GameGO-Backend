package sideproject.junior.gamego.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import sideproject.junior.gamego.model.dto.chat.ResChatRoomDTO;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "chatRoom")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoomId")
    private Long id;

    @Column(name = "roomName")
    private String roomName;

    @Column(name = "roomCode")
    private String roomCode;

    @Column(name = "capacity")
    private String capacity;

    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom")
    private List<HashTag> hashTags;

    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom")
    private List<ChatRoomJoinMember> chatRoomJoinMembers;

    @JsonIgnore
    @OneToMany(mappedBy = "chatRoom")
    private List<ChatMessage> chatMessages;

    public ResChatRoomDTO toResDTO() {
        return ResChatRoomDTO.builder()
                .roomName(this.roomName)
                .roomId(this.id)
                .memberList(this.chatRoomJoinMembers.stream().map(ChatRoomJoinMember::toDTO).collect(Collectors.toList()))
                .chatMessageList(this.chatMessages.stream().map(ChatMessage::toDTO).collect(Collectors.toList()))
                .build();
    }

    public void joinMember(ChatRoomJoinMember member) {
        this.chatRoomJoinMembers.add(member);
    }
}