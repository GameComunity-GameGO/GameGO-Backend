package sideproject.junior.gamego.model.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "hashTag")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashTagId")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gamerId")
    private Gamer gamer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoomId")
    private ChatRoom chatRoom;

    @Builder
    public HashTag(String name, Gamer gamer, ChatRoom chatRoom) {
        this.name = name;
        this.gamer = gamer;
        this.chatRoom = chatRoom;
    }
}