package com.product.api.dto;

// import org.antlr.v4.runtime.misc.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public class DtoCategoryIn {

    @JsonProperty("Category")
    @NotNull(message = "La categoria es obligatoria")
    private String category;

    @JsonProperty ("tag")
    @NotNull(message = "La etiqueta es obligatoria")
    private String tag;

    @JsonProperty("parentCategoryId")
    private Integer parentCategoryId;

    public String getCategory() {
        return category;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public String getTag() {
        return tag;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
