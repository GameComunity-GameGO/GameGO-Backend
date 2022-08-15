package sideproject.junior.gamego.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sideproject.junior.gamego.model.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByUsername (String username);

    Optional<Member> findByNickname (String nickname);

    Optional<Member> findByRefreshToken (String refreshToken);

    Member findMemberByUsername(String username);
}
