package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import sideproject.junior.gamego.exception.gamer.GamerException;
import sideproject.junior.gamego.exception.gamer.GamerExceptionType;
import sideproject.junior.gamego.model.dto.GamerDTO;
import sideproject.junior.gamego.model.entity.Gamer;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.principal.SecurityUtil;
import sideproject.junior.gamego.repository.GamerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GamerService {

    private final GamerRepository gamerRepository;

    private final SecurityUtil securityUtil;

    private final MemberService memberService;

    private final GameService gameService;

    public List<Gamer> getGamerListApi(){
        List<Gamer> all = gamerRepository.findAll();
        return all;
    }

    public Gamer getGamerApi(Long id){
        Gamer gamer = gamerRepository.findById(id).get();
        return gamer;
    }

    @Transactional
    public ResponseEntity<?> registationGamerApi(GamerDTO.GamerRegistationDTO gamerRegistationDTO) throws IllegalStateException{;
        System.out.println("gamerRegistationDTO.getGameUsername() = " + gamerRegistationDTO.getGameUsername());
        System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
        boolean nameCheck = gameNameCheckApi(gamerRegistationDTO.getGame());
//        if (nameCheck) {
        String username = securityUtil.returnLoginMemberInfo();
        Member findMember = memberService.MemberStateApi(username);
        boolean alreadyRegis = alreadyGamerRegistation(findMember);
        if (alreadyRegis){
            gamerRegistationDTO.setMember(findMember);
            Gamer entity = gamerRegistationDTO.toEntity();
            gamerRepository.save(entity);
            return new ResponseEntity<>("게이머 등록 API 성공!",HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new GamerException(GamerExceptionType.ALEARY_REGISTATION_MEMBER),HttpStatus.OK);
        }
//            throw new IllegalStateException("게임이름이 없습니다");
//        }
    }

    @Transactional
    public void deleteGamerApi(Long id)throws NullPointerException {
        Optional<Gamer> findGamer = gamerRepository.findById(id);
        if (findGamer.isEmpty()){
            throw new NullPointerException("아이디가 없습니다");
        }else {
            gamerRepository.delete(findGamer.get());
        }
    }

    public boolean gameNameCheckApi(String game){
        boolean b = gameService.gameNameCheckApi(game);
        if (b) return true;
        else return false;
    }

    private boolean alreadyGamerRegistation(Member member){
        List<Gamer> gamerList = gamerRepository.findAll();
        boolean ALREADY = true;
        for (Gamer gamer:gamerList){
            if (gamer.getMember().getId()==member.getId()){
                ALREADY=false;
                break;
//                return new ResponseEntity<>(new GamerException(GamerExceptionType.ALEARY_REGISTATION_MEMBER), HttpStatus.OK);
            }else {
                ALREADY=true;
            }
        }
        return ALREADY;
    }

    @Transactional
    public void gamerDeleteByLogout(String username){
        Member member = memberService.MemberStateApi(username);
        if (member.getId()!=null){
            Optional<Gamer> findGamer = gamerRepository.findByMember(member);
            gamerRepository.delete(findGamer.get());
        }
    }
}
