package sideproject.junior.gamego.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sideproject.junior.gamego.model.entity.HashTag;

import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag,Long> {
}
