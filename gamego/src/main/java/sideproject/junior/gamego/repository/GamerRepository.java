package sideproject.junior.gamego.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sideproject.junior.gamego.model.entity.Gamer;

import java.util.Optional;

public interface GamerRepository extends JpaRepository<Gamer,Long> {
}
