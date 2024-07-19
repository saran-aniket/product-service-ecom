package com.peroductservice.productservice;

import com.peroductservice.productservice.models.Category;
import com.peroductservice.productservice.models.Product;
import com.peroductservice.productservice.repositories.CategoryRepository;
import com.peroductservice.productservice.repositories.ProductRepository;
import com.peroductservice.productservice.repositories.projections.CategoryTitleAndProductTitle;
import com.peroductservice.productservice.services.ProductService;
import jakarta.persistence.EntityManager;
import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ProductServiceApplicationTests {
    @Autowired
    private EntityManager session;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void contextLoads() {
    }

//    @Test
//    @Transactional
//    public void testCascadeTypes(){
//        Product product = new Product();
//        Category category = new Category();
//        category.setTitle("category title");
//        product.setCategory(category);
//        product.setDescription("description");
//        product.setPrice(1.0);
//        product.setImageUrl("imageURl");
//        category.setProducts(Arrays.asList(product));
//        session.persist(category);
//        session.flush();
//        session.clear();
//    }
//    @Test
//    @Transactional
//    public void whenParentSavedThenMerged() {
//        int addressId;
//        Category category = new Category();
//        category.setTitle("category title");
//        Product product = new Product();
//        product.setTitle("product title");
//        product.setCategory(category);
//        category.setProducts(Arrays.asList(product));
//        session.persist(category);
//        session.flush();
//        Long productId = product.getId();
//        session.clear();
//
//        Product savedProductEntity = session.find(Product.class, productId);
//        Category savedCategoryEntity = savedProductEntity.getCategory();
//        savedCategoryEntity.setTitle("NEw category title");
//        savedProductEntity.setDescription("description");
//        session.merge(savedCategoryEntity);
//        session.flush();
//    }
//
//    @Test
//    @Transactional
//    public void whenParentDetachedThenChildDetached() {
//        Category category = new Category();
//        category.setTitle("category title");
//        Product product = new Product();
//        product.setTitle("product title");
//        product.setCategory(category);
//        category.setProducts(Arrays.asList(product));
//        session.persist(category);
//        session.flush();
//
//        assertThat(session.contains(category)).isTrue();
//        assertThat(session.contains(product)).isTrue();
//
//        session.detach(category);
//        assertThat(session.contains(category)).isFalse();
//        assertThat(session.contains(product)).isFalse();
//    }
//
//    @Test
//    @Transactional
//    public void testDeclaredQueries(){
//        List<Product> productWithCategoryTitle = productRepository.findProductByCategory_Title("category 5");
//        for(Product product : productWithCategoryTitle){
//            System.out.println(product.getTitle());
//            System.out.println(product.getCategory().getTitle());
//
//        }
//    }
//    @Test
//    public void testDeclaredQueries_1(){
//        List<CategoryTitleAndProductTitle> categoryWithAllProducts = categoryRepository.findAllProductByCategory();
////        for(List<String> outerList : categoryWithAllProducts){
////            for(String innerItem : outerList){
////                System.out.print(innerItem+ " ");
////            }
////            System.out.println();
////        }
//        for(CategoryTitleAndProductTitle innerItem : categoryWithAllProducts){
//            System.out.println(innerItem.getTitle());
//        }
//    }
//    @Test
//    public void testNativeQueries(){
//        List<CategoryTitleAndProductTitle> categoryWithAllProducts = categoryRepository.findAllTitleByGivenName("category 5");
//        for(CategoryTitleAndProductTitle innerItem : categoryWithAllProducts){
//            System.out.println(innerItem.getTitle());
//        }
//    }
//    @Test
//    @Transactional
//    public void getAllCategories(){
//        List<Category> categoryList = categoryRepository.findAll();
//        for(Category innerItem : categoryList){
//            System.out.println(innerItem.getTitle() + " " + innerItem.getDescription());
////            if(!innerItem.getProducts().isEmpty()){
////                for(Product p : innerItem.getProducts()){
////                    System.out.println(" "+p.getTitle());
////                }
////            }
//        }
//    }
//    @Test
////    @Transactional
//    public void getAllProducts(){
//        List<Product> productList = productRepository.findAll();
//        for(Product innerItem : productList){
//            System.out.println(innerItem.getTitle() + " " + innerItem.getDescription());
////            System.out.println("category "+innerItem.getCategory().getTitle());
//        }
//    }
    @Test
    public void getAllProducts(){
//        ProductService productService = context.getBean()

    }
    @Test
    void addManyProducts() {
        double basePrice = 2000;
        String productName = "toy";
        String productDescription = "toy number";
        String imageUrl = "www.imgur.com/";
        String categoryName = "toys";

        for (int i = 1; i <= 100; i++) {
            Product product = new Product();
            product.setTitle(productName + "-" + i);
            product.setDescription(productDescription + " # " + i);
            product.setPrice(basePrice + i);
            Category category = new Category();
            category.setTitle(categoryName);
            product.setCategory(category);
            product.setImageUrl(imageUrl + i);
            productRepository.save(product);
        }
    }
}
