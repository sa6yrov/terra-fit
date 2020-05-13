package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.Category;
import kg.sabyrov.terrafit.repository.CategoryRepository;
import kg.sabyrov.terrafit.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        return categoryOptional.orElse(null);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
