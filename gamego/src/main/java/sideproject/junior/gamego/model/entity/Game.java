package sideproject.junior.gamego.model.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "game")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gameId")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gamerId")
    private Gamer gamer;

    @Builder
    public Game(String name, String content, Gamer gamer) {
        this.name = name;
        this.content = content;
        this.gamer = gamer;
    }
}