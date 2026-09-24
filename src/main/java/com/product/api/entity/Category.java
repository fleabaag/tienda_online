package com.product.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("category_id")
    @Column(name = "category_id")
    private Integer category_id;

    @JsonProperty("category")
    @Column(name = "category")
    private String category;

    @JsonProperty("tag")
    @Column(name = "tag")
    private String tag;

    @JsonProperty("parent_category_id")
    @Column(name = "parent_category_id")
    private Integer parentCategory_id;

    @JsonProperty("status")
    @Column(name = "status")
    private Integer status;

    // Constructores

    public Category(String category, String tag, Integer parentCategory_id) {
        this.category = category;
        this.tag = tag;
        this.parentCategory_id = parentCategory_id;
    }

    public Category() {
    }

    // getters
    public Integer getCategory_id() {
        return this.category_id;
    }

    public String getCategory() {
        return this.category;
    }

    public String getTag() {
        return this.tag;
    }

    public Integer getParentCategory_id() {
        return this.parentCategory_id;
    }

    public Integer getStatus() {
        return this.status;
    }

    // Setters
    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setParentCategory_id(Integer parentCategory_id) {
        this.parentCategory_id = parentCategory_id;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    // ToString
    @Override
    public String toString() {
        return "{" + this.category_id + "," + this.category + "," + this.tag + "," + this.parentCategory_id + ","
                + this.status + "}";
    }
}
