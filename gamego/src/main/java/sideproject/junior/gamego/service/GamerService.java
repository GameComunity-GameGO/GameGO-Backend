package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.junior.gamego.model.dto.GamerDTO;
import sideproject.junior.gamego.model.entity.Gamer;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.principal.SecurityUtil;
import sideproject.junior.gamego.repository.GamerRepository;
import sideproject.junior.gamego.repository.MemberRepository;

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

    public List<Gamer> getGamerListApi(){
        List<Gamer> all = gamerRepository.findAll();
        return all;
    }

    public Gamer getGamerApi(Long id){
        Gamer gamer = gamerRepository.findById(id).get();
        return gamer;
    }

    @Transactional
    public void registationGamerApi(GamerDTO.GamerRegistationDTO gamerRegistationDTO){
        String username = securityUtil.returnLoginMemberInfo();
        Member findMember = memberService.MemberStateApi(username);
        gamerRegistationDTO.setMember(findMember);
        Gamer entity = gamerRegistationDTO.toEntity();
        gamerRepository.save(entity);

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
}
