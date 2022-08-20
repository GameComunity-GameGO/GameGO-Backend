package sideproject.junior.gamego.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Gamer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gamerId")
    private Long id;

    @Column(name = "gameUsername")
    private String gameUsername;

    @Column(name = "introduction")
    private String introdution;

    @Column(name = "regiTime")
    @Enumerated(EnumType.STRING)
    private RegiTime regiTime;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "gamer")
    private List<Game> game;

    @JsonIgnore
    @OneToMany(mappedBy = "gamer")
    private List<HashTag> hashTags;

    @Builder
    public Gamer(String gameUsername, String introdution,RegiTime regiTime, Member member, List<HashTag> hashTags) {
        this.gameUsername = gameUsername;
        this.introdution = introdution;
        this.regiTime=regiTime;
        this.member = member;
        this.hashTags = hashTags;
    }

    public void setGame(String game){
    }

    public void setRegiTimeExpired(){
        this.regiTime=RegiTime.EXPIRED;
    }
}
