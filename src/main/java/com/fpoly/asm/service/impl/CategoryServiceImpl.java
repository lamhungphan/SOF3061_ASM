package com.fpoly.asm.service.impl;

import com.fpoly.asm.controller.request.CategoryRequest;
import com.fpoly.asm.entity.Category;
import com.fpoly.asm.mapper.CategoryMapper;
import com.fpoly.asm.repository.CategoryRepository;
import com.fpoly.asm.service.AbstractService;
import com.fpoly.asm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl extends AbstractService<Category, Integer, CategoryRequest> implements CategoryService {
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository repository, CategoryMapper categoryMapper) {
        super(repository);
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void update(CategoryRequest  request) {
        Category category = getById(request.getId());
        categoryMapper.updateCategory(category, request);
        save(category);
    }
}
