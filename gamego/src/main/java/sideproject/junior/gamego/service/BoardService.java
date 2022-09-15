package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.junior.gamego.model.dto.board.RequestBoardDTO;
import sideproject.junior.gamego.model.dto.board.ResponseBoardDTO;
import sideproject.junior.gamego.model.dto.reply.ReplyDTO;
import sideproject.junior.gamego.model.entity.*;
import sideproject.junior.gamego.repository.images.ImagesRepository;
import sideproject.junior.gamego.repository.MemberRepository;
import sideproject.junior.gamego.repository.ReplyRepository;
import sideproject.junior.gamego.repository.board.BoardRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class BoardService {

    private final BoardRepository boardRepository;
    private final CategoryService categoryService;
    private final MemberRepository memberRepository;
    private final ReplyRepository replyRepository;
    private final ImagesRepository imagesRepository;
    private final AwsS3Service awsS3Service;

    public Page<ResponseBoardDTO> getBoardList(Pageable pageable) {

        return boardRepository.getBoardList(pageable);
    }

    public Page<ResponseBoardDTO> getPopularBoardList(Pageable pageable) {

        return boardRepository.getPopularBoardList(pageable);
    }

    public ResponseBoardDTO getBoard(Long boardId, Long memberId){

        ResponseBoardDTO responseBoardDTO = boardRepository.getBoard(boardId).toDTO();

        List<Reply> replyList = replyRepository.findAllByCommunityBoardId(boardId);

        for (Reply reply : replyList) {
            if(reply.getMember().getId().equals(memberId)){
                ReplyDTO myReply = responseBoardDTO.getReplyList().stream().filter(replyDTO -> replyDTO.getId().equals(reply.getId())).findAny().get();
                myReply.setCheckMyReply(1);
            }
        }

        return responseBoardDTO;
    }

    public ResponseBoardDTO createBoard(Long memberId, RequestBoardDTO dto) {

        log.info("BoardService.createBoard 호출");

        String category = dto.getCategory();

        String type = dto.getType();

        Member member = memberRepository.findById(memberId).get();

        log.info("BoardService.createBoard - member = " + member.getId());

        Category getCategory = categoryService.getCategory(category);
        BoardType boardType = categoryService.getType(type);

        log.info("BoardService.createBoard - category = " + getCategory.getTitle());

        CommunityBoard createBoard = CommunityBoard.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .member(member)
                .category(getCategory)
                .boardType(boardType)
                .build();

        CommunityBoard board = boardRepository.save(createBoard);

        for (String s : dto.getImgArray()) {
            Images images = Images.builder()
                    .imgURL(s)
                    .communityBoard(board)
                    .build();
            board.insertImage(imagesRepository.save(images));
        }

        log.info("board = " + board.getId() );

        return board.toResponseDTO(member);
    }

    public ResponseBoardDTO updateBoard(RequestBoardDTO dto, Long boarId) {

        CommunityBoard getBoard = boardRepository.findById(boarId).get();

        String category = dto.getCategory();
        String type = dto.getType();

        Category getCategory = categoryService.getCategory(category);

        BoardType boardType = categoryService.getType(type);

        List<Images> imagesList = imagesRepository.findAllByCommunityBoardId(getBoard.getId());

        for (Images images : imagesList) {
            awsS3Service.deleteImage(images.getImgURL());
            imagesRepository.delete(images);
        }

        for (String s : dto.getImgArray()) {
            Images images = Images.builder()
                    .imgURL(s)
                    .communityBoard(getBoard)
                    .build();
            getBoard.insertImage(imagesRepository.save(images));
        }

        CommunityBoard updateBoard = getBoard.update(dto.getTitle(), dto.getContents(), getCategory, boardType);

        return updateBoard.toDTO();
    }

    public int checkUpdateBoard(Long memberId, Long boardId){

        CommunityBoard getBoard = boardRepository.findById(boardId).get();

        if(Objects.equals(getBoard.getMember().getId(), memberId)){
            return 1;
        }else
            return 0;
    }

    public int deleteBoard(Long boardId, Long memberId) {

        if(Objects.equals(boardRepository.findById(boardId).get().getMember().getId(), memberId)) {

            List<Images> getImageList = imagesRepository.findAllByCommunityBoardId(boardId);

            for (Images images : getImageList) {
                awsS3Service.deleteImage(images.getImgURL());
            }

            boardRepository.deleteById(boardId);

            return 1;
        }else{
            return 0;
        }
    }
}
