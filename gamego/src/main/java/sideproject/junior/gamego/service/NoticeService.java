package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.junior.gamego.model.dto.chat.ResChatRoomDTO;
import sideproject.junior.gamego.model.entity.ChatRoomJoinMember;
import sideproject.junior.gamego.model.entity.Member;
import sideproject.junior.gamego.repository.chat.ChatRoomRepository;
import sideproject.junior.gamego.repository.chat.ChatMessageRepository;
import sideproject.junior.gamego.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class NoticeService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;
    private final ChatMessageRepository chatMessageRepository;


    public List<ResChatRoomDTO> getChatNoticeList(Long memberId) {

        Member member = memberRepository.findById(memberId).get();

        List<ResChatRoomDTO> getRoomList = new ArrayList<>();

        for (ChatRoomJoinMember chatRoomJoinMember : member.getChatRoomJoinMembers()) {
            Long noticeCount = chatMessageRepository.getNoticeCount(chatRoomJoinMember.getChatRoom().getId(), chatRoomJoinMember.getCheckPoint());
            ResChatRoomDTO resChatRoomDTO = chatRoomJoinMember.getChatRoom().toResDTO();
            resChatRoomDTO.setNoticeCount(noticeCount);
            getRoomList.add(resChatRoomDTO);
        }

        return getRoomList;
    }
}
