package sideproject.junior.gamego.model.dto.board;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestBoardDTO {

    private String title;

    private String contents;

    private String imgUrl;

    private String category;

    private String type;

    private List<String> images;
}
