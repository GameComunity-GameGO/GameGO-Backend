package sideproject.junior.gamego.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "members")
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "memberPassword")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "refreshToken")
    private String refreshToken;

    @Column(name = "imgurl")
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarmId")
    private Alarm alarm;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<CommunityBoard> communityBoards;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Heart> hearts;

    @OneToMany(mappedBy = "member")
    private List<ChatRoomJoinMember> chatRoomJoinMembers;
}
