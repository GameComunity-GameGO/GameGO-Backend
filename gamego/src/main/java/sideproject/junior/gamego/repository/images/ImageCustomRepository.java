package sideproject.junior.gamego.repository.images;

import sideproject.junior.gamego.model.entity.Images;

import java.util.List;

public interface ImageCustomRepository{
    List<Images> deleteImagesByBoardId(Long boardId);
}
