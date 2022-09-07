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
import sideproject.junior.gamego.model.entity.RegiTime;
import sideproject.junior.gamego.principal.SecurityUtil;
import sideproject.junior.gamego.repository.GamerRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GamerService {

    private final GamerRepository gamerRepository;

    private final SecurityUtil securityUtil;

    private final MemberService memberService;

    private final GameService gameService;

    @Transactional
    public List<Gamer> getGamerListApi(){
        List<Gamer> all = gamerRepository.findAll();
        List<Gamer> returnList=new ArrayList<>();
        for (Gamer gamer:all){
            boolean regiExpried = setTimeList(gamer.getCreatedDate());
            System.out.println("regiExpried = " + regiExpried);
            if (regiExpried==false){
                gamer.setRegiTimeExpired();
            }
            returnList.add(gamer);
        }
        return returnList;
    }

    public Gamer getGamerApi(Long id){
        Gamer gamer = gamerRepository.findById(id).get();
        return gamer;
    }

    @Transactional
    public ResponseEntity<?> registationGamerApi(GamerDTO.GamerRegistationDTO gamerRegistationDTO) throws IllegalStateException{;
        System.out.println("gamerRegistationDTO.getGameUsername() = " + gamerRegistationDTO.getGameUsername());


        boolean nameCheck = gameNameCheckApi(gamerRegistationDTO.getGame());
//        if (nameCheck) {
        String username = securityUtil.returnLoginMemberInfo();
        Member findMember = memberService.MemberStateApi(username);
        boolean alreadyRegis = alreadyGamerRegistation(findMember);
        if (alreadyRegis){
            gamerRegistationDTO.setMember(findMember);
            gamerRegistationDTO.setRegiTime(RegiTime.HALF);
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
            if (findGamer.isPresent()){
                gamerRepository.delete(findGamer.get());
            }
        }
    }

    public boolean setTimeList(LocalDateTime dbTime){
        int year = Integer.parseInt(LocalDateTime.now().toString().substring(0, 4));
        int month = Integer.parseInt(LocalDateTime.now().toString().substring(6, 7));
        int day = Integer.parseInt(LocalDateTime.now().toString().substring(8, 10));
        int hours = Integer.parseInt(LocalDateTime.now().toString().substring(11, 13));
        int minute = Integer.parseInt(LocalDateTime.now().toString().substring(14, 16));
        System.out.println(year+""+month+""+day+""+hours+""+minute);
        int now = year + month + day + minute + hours;
//        System.out.println("now = " + now);
        int year2 = Integer.parseInt(dbTime.toString().substring(0, 4));
        int month2 = Integer.parseInt(dbTime.toString().substring(6, 7));
        int day2 = Integer.parseInt(dbTime.toString().substring(8, 10));
        int hours2 = Integer.parseInt(dbTime.toString().substring(11, 13));
        int minute2 = Integer.parseInt(dbTime.toString().substring(14, 16));
        System.out.println(year2+""+month2+""+day2+""+hours2+""+minute2);
        int db = year2 + month2 + day2 + minute2 + hours2;
        System.out.println("now = " + now);
        System.out.println("db = " + db);
        if (year==year2&&month==month2&&day==day2&&hours==hours2){
            if (minute-minute2>=0&&minute-minute2<=30) return true;
            else return false;
        }
        else return false;
    }
}
