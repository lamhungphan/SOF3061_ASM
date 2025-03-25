package com.fpoly.asm.service.impl;

import com.fpoly.asm.entity.Category;
import com.fpoly.asm.repository.CategoryRepository;
import com.fpoly.asm.service.AbstractService;
import com.fpoly.asm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl extends AbstractService<Category, Integer> implements CategoryService {
    public CategoryServiceImpl(CategoryRepository repository) {
        super(repository);
    }

    @Override
    public void update(Category request) {

    }
}


