package sideproject.junior.gamego.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heartId")
    private Long id;

    @Column(name = "count")
    private Long count;

    @Column(name = "temperature")
    private int temperature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="communityBoardId")
    private CommunityBoard communityBoard;

    @OneToOne(fetch = FetchType.LAZY)
    private Gamer gamer;
}
