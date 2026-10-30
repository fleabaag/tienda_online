package com.product.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.api.entity.PoductImage;

@Repository
public interface RepoProductImage extends JpaRepository<PoductImage, Integer> {

    List<PoductImage> findByProductIdAndStatusOrderByProductImageIdAsc(Integer productId, Integer status);

    Optional<PoductImage> findByProductImageIdAndProductId(Integer productImageId, Integer productId);
}
