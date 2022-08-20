package sideproject.junior.gamego.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sideproject.junior.gamego.model.dto.LikeDTO;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "likes_uk",
                        columnNames = {"communityBoard_id","member_id"}
                )
        }
)
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communityBoard_id")
    private CommunityBoard communityBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public LikeDTO toDTO(){
        return LikeDTO.builder()
                .id(this.id)
                .boardId(this.communityBoard.getId())
                .memberId(this.member.getId())
                .build();
    }
}
