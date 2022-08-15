package sideproject.junior.gamego.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sideproject.junior.gamego.model.entity.Gamer;
import sideproject.junior.gamego.model.entity.Member;

import java.util.Optional;

public interface GamerRepository extends JpaRepository<Gamer,Long> {
    Optional<Gamer> findByMember (Member member);
}
