package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.junior.gamego.model.entity.BoardType;
import sideproject.junior.gamego.model.entity.Category;
import sideproject.junior.gamego.repository.board.BoardTypeRepository;
import sideproject.junior.gamego.repository.category.CategoryRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final BoardTypeRepository boardTypeRepository;


    public Category getCategory(String category) {

        return categoryRepository.findByTitle(category);
    }

    public BoardType getType(String type){
        return boardTypeRepository.findByTitle(type);
    }
}
