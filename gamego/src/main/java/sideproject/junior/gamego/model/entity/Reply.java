package sideproject.junior.gamego.model.entity;

import lombok.*;
import sideproject.junior.gamego.model.dto.reply.ReplyDTO;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communityBoard_id")
    private CommunityBoard communityBoard;

    public ReplyDTO toDTO(){
        return ReplyDTO.builder()
                .id(this.id)
                .content(this.content)
                .memberDTO(this.member.toDTO())
                .createdDate(this.createdDate)
                .lastModifiedDate(this.lastModifiedDate)
                .build();
    }

    public Reply update(String content) {
        this.content = content;
        return this;
    }
}
