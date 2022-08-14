package sideproject.junior.gamego.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sideproject.junior.gamego.model.entity.Category;
import sideproject.junior.gamego.repository.category.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public Category getCategory(String category) {

        return categoryRepository.findByTitle(category);
    }
}
