package com.peroductservice.productservice.repositories;

import com.peroductservice.productservice.models.Category;
import com.peroductservice.productservice.repositories.projections.CategoryTitleAndProductTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category getCategoryByTitle(String title);

    List<Category> findAll();

    @Query("SELECT c.title as title FROM Category c")
    List<CategoryTitleAndProductTitle> findAllProductByCategory();

    @Query(value = "SELECT c.title FROM Category c WHERE c.title = :categoryName", nativeQuery = true)
    List<CategoryTitleAndProductTitle> findAllTitleByGivenName(String categoryName);
}
