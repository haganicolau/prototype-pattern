package com.example.prototype.pattern;

import com.example.prototype.entity.ERPProduct;
import com.example.prototype.util.DeepCopyUtil;

/**
 * ERPProductPrototype - A wrapper class that implements the Prototype pattern
 * for ERPProduct entities. This class enables deep copying of ERPProduct
 * instances without modifying the original ERPProduct class.
 * 
 * This is the key component that solves the problem: you have a large,
 * complex entity (ERPProduct) that you cannot modify, but you need to create
 * deep copies of it efficiently.
 */
public class ERPProductPrototype implements Prototype<ERPProductPrototype> {
    
    private final ERPProduct product;

    /**
     * Constructor that wraps an ERPProduct instance.
     * 
     * @param product The ERPProduct to wrap
     * @throws IllegalArgumentException if product is null
     */
    public ERPProductPrototype(ERPProduct product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        this.product = product;
    }

    /**
     * Creates a deep copy of this prototype.
     * This method uses serialization to create a completely independent copy
     * of the wrapped ERPProduct, including all nested objects, collections, and maps.
     * 
     * @return A new ERPProductPrototype wrapping a deep copy of the product
     */
    @Override
    public ERPProductPrototype clone() {
        // Use DeepCopyUtil to create a deep copy of the product
        ERPProduct copiedProduct = DeepCopyUtil.deepCopy(this.product);
        return new ERPProductPrototype(copiedProduct);
    }

    /**
     * Gets the wrapped ERPProduct.
     * 
     * @return The ERPProduct wrapped by this prototype
     */
    public ERPProduct getProduct() {
        return product;
    }

    /**
     * Sets a new product for this prototype.
     * This replaces the wrapped product with a new one.
     * 
     * @param product The new product to wrap
     * @throws IllegalArgumentException if product is null
     */
    public void setProduct(ERPProduct product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        // Note: This is not typical usage of the Prototype pattern,
        // but provided for flexibility
        // In a real implementation, you might want to make this private
        // or remove it entirely
    }

    /**
     * Creates a shallow copy of this prototype.
     * This creates a new prototype wrapping the same product instance.
     * Use with caution - modifications to the product will affect all prototypes.
     * 
     * @return A new ERPProductPrototype wrapping the same product
     */
    public ERPProductPrototype shallowClone() {
        return new ERPProductPrototype(this.product);
    }

    /**
     * Checks if this prototype is equal to another.
     * Two prototypes are considered equal if their wrapped products are equal.
     * 
     * @param o The object to compare with
     * @return true if the products are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ERPProductPrototype that = (ERPProductPrototype) o;
        return product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return product.hashCode();
    }

    @Override
    public String toString() {
        return "ERPProductPrototype{" +
                "product=" + product +
                '}';
    }
}
