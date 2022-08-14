package sideproject.junior.gamego.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sideproject.junior.gamego.model.entity.Game;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game,Long> {
    Optional<Game> findByName (String name);
}
