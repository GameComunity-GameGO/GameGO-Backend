package sideproject.junior.gamego.model.entity;

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

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "gamer")
    private List<Game> game;

    @OneToMany(mappedBy = "gamer")
    private List<HashTag> hashTags;

    @Builder
    public Gamer(String gameUsername, String introdution, Member member, List<HashTag> hashTags) {
        this.gameUsername = gameUsername;
        this.introdution = introdution;
        this.member = member;
        this.hashTags = hashTags;
    }

    public void setGame(String game){
    }
}
