package com.qian.gulimall.product.service.impl;

import com.qian.gulimall.product.api.dto.CategoryDto;
import com.qian.gulimall.product.api.result.CategoryResult;
import com.qian.gulimall.product.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void listWithTree() {
        List<CategoryResult> categoryResultList = categoryService.listWithTree();
        System.out.println(categoryResultList);
    }
}