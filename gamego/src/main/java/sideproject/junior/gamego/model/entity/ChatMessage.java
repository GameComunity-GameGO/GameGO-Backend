package sideproject.junior.gamego.model.entity;

import lombok.*;
import sideproject.junior.gamego.model.dto.chat.ResChatMessageDTO;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ChatMessage extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatMessageId")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "imgUrl")
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoomId")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    public ResChatMessageDTO toDTO(){
        return ResChatMessageDTO.builder()
                .id(this.id)
                .content(this.content)
                .member(this.member.toDTO())
                .createdDate(this.createdDate)
                .lastModifiedDate(this.lastModifiedDate)
                .build();
    }
}
