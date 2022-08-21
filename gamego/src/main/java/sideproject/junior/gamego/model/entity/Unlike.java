package sideproject.junior.gamego.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sideproject.junior.gamego.model.dto.LikeDTO;
import sideproject.junior.gamego.model.dto.UnlikeDTO;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unlike_uk",
                        columnNames = {"communityBoard_id","member_id"}
                )
        }
)
public class Unlike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communityBoard_id")
    private CommunityBoard communityBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public UnlikeDTO toDTO(){
        return UnlikeDTO.builder()
                .id(this.id)
                .boardId(this.communityBoard.getId())
                .memberId(this.member.getId())
                .build();
    }
}
