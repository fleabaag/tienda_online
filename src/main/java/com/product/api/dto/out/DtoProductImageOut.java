package com.product.api.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoProductImageOut {

    @JsonProperty("product_image_id")
    private Integer product_image_id;

    @JsonProperty("product_id")
    private Integer product_id;

    @JsonProperty("image")
    private String image;

    public Integer getProductImageId() {
        return product_image_id;
    }

    public void setProductImageId(Integer product_image_id) {
        this.product_image_id = product_image_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
