package sideproject.junior.gamego.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Gamer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gamerId")
    private Long id;

    @Column(name = "introduction")
    private String introdution;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "gamer")
    private List<Game> game;

    @OneToMany(mappedBy = "gamer")
    private List<HashTag> hashTags;
}
