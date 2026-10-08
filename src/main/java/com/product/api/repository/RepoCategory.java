package com.product.api.repository;

import java.util.List;
import com.product.api.entity.Category;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoCategory extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT * FROM category ORDER BY category", nativeQuery = true)
    List<Category> findAll();

    List<Category> findByStatusOrderByCategory(@Param("status") Integer status);

    @Query(value = "SELECT * FROM category WHERE :parent_category_id = parent_category_id", nativeQuery = true)
    List<Category> findChilds(@Param("parent_category_id") Integer parent_category_id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "INSERT INTO category(category, tag, status, parent_category_id) VALUES (:category, :tag, 1, :parent_category_id)", nativeQuery = true)
    void create(@Param("category") String category, @Param("tag") String tag,
            @Param("parent_category_id") Integer parent_category_id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE category SET category = :category, tag = :tag, parent_category_id = :parent_category_id"
            + "WHERE category_id = :category_id", nativeQuery = true)
    void update(@Param("category_id") Integer category_id, @Param("category") String category, @Param("tag") String tag,
            @Param("parent_category_id") Integer parent_category_id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE category SET status = :status " + "WHERE category_id = :category_id", nativeQuery = true)
    void updateStatus(@Param("category_id") Integer category_id, @Param("status") Integer status);

    // excludeId = -1 en create, el id propio en update
    @Query(value = "SELECT COUNT(*) FROM category WHERE LOWER(category) = LOWER(:category) AND category_id <> :excludeId", nativeQuery = true)
    int countByName(@Param("category") String category, @Param("excludeId") Integer excludeId);

    @Query(value = "SELECT COUNT(*) FROM category WHERE LOWER(tag) = LOWER(:tag) AND category_id <> :excludeId", nativeQuery = true)
    int countByTag(@Param("tag") String tag, @Param("excludeId") Integer excludeId);

}
