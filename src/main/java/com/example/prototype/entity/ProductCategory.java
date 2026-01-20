package com.example.prototype.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * ProductCategory - Represents a product category in the ERP system.
 * This is a nested object within ERPProduct.
 */
public class ProductCategory implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String description;
    private String parentId;
    private int level;

    public ProductCategory() {
    }

    public ProductCategory(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategory that = (ProductCategory) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level +
                '}';
    }
}
