package com.product.api.service;

import java.util.List;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.dto.out.DtoProductImageOut;

public interface SvcProductImage {

    List<DtoProductImageOut> getProductImages(Integer productId);

    void createProductImage(Integer productId, DtoProductImageIn in);

    void deleteProductImage(Integer productId, Integer productImageId);
}
