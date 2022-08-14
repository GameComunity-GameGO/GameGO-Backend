package sideproject.junior.gamego.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "chatRoom")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "chatRoom")
    private List<HashTag> hashTags;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "chatRoom")
    private List<ChatRoomJoinMember> chatRoomJoinMembers;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "chatRoom")
    private List<ChatMessage> chatMessages;
}