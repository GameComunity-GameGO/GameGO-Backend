package sideproject.junior.gamego.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import sideproject.junior.gamego.model.dto.MemberDTO;

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

    @Column(name = "imgurl")
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarmId")
    private Alarm alarm;

    @JsonIgnore
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<CommunityBoard> communityBoards;

    @JsonIgnore
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Heart> hearts;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<ChatRoomJoinMember> chatRoomJoinMembers;

    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 1000)
    private String refreshToken;



    @Builder
    public Member(String username, String password,String nickname,Role role) {
        this.username = username;
        this.password = password;
        this.nickname=nickname;
        this.role=role;
    }

    public void changeRefreshToken(String refreshToken){
        System.out.println("토큰이 변경되었습니다");
        this.refreshToken=refreshToken;
    }

    public void destroyRefreshToken(){
        this.refreshToken=null;
    }

    public boolean ChangeMemberState(MemberDTO.ChangeStateDTO changeStateDTO){
        if (changeStateDTO.getUsername()==null&&changeStateDTO.getPassword()==null){
            return false;
        }else {
            this.username=changeStateDTO.getUsername();
            this.password=changeStateDTO.getPassword();
            return true;
        }
    }
    //권한 추가
    public void addMemberAuthority(){
        this.role=Role.MEMBER;
    }

    public void encodeToPassword(PasswordEncoder passwordEncoder){
        this.password=passwordEncoder.encode(password);
    }

    public MemberDTO toDTO(){
        return MemberDTO.builder()
                .id(this.id)
                .username(this.username)
                .nickname(this.nickname)
                .build();
    }
}
