package com.example.prototype.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * ERPProduct - A large, complex entity representing a product in an ERP system.
 * This class demonstrates a real-world scenario where you need to create copies
 * of a complex object without modifying the original class.
 * 
 * Note: This class is intentionally NOT implementing Cloneable or any copy methods
 * to demonstrate the Prototype pattern working as a wrapper.
 */
public class ERPProduct implements Serializable {
    
    private static final long serialVersionUID = 1L;

    // Basic Information
    private String id;
    private String name;
    private String description;
    private String sku;
    private String barcode;
    
    // Pricing
    private BigDecimal price;
    private BigDecimal cost;
    private BigDecimal taxRate;
    
    // Category and Classification
    private ProductCategory category;
    
    // Supplier Information
    private Supplier supplier;
    
    // Inventory
    private List<InventoryItem> inventory;
    
    // Flexible Attributes
    private Map<String, Object> attributes;
    
    // Metadata
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String createdBy;
    private String modifiedBy;
    
    // Additional ERP fields
    private boolean active;
    private int minimumStockLevel;
    private int maximumStockLevel;
    private String warehouseLocation;
    private double weight;
    private String weightUnit;
    private double volume;
    private String volumeUnit;
    private String manufacturer;
    private String brand;
    private String warrantyPeriod;
    private String returnPolicy;
    private boolean taxable;
    private boolean discountAllowed;
    private BigDecimal discountPercentage;

    /**
     * Default constructor
     */
    public ERPProduct() {
        this.inventory = new ArrayList<>();
        this.attributes = new HashMap<>();
        this.active = true;
        this.taxable = true;
        this.discountAllowed = true;
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    /**
     * Copy constructor - Note: This is a shallow copy for demonstration purposes.
     * In a real scenario, you might not even have this constructor.
     * 
     * @param other The product to copy from
     */
    public ERPProduct(ERPProduct other) {
        this.id = other.id;
        this.name = other.name;
        this.description = other.description;
        this.sku = other.sku;
        this.barcode = other.barcode;
        this.price = other.price;
        this.cost = other.cost;
        this.taxRate = other.taxRate;
        this.category = other.category; // Shallow copy
        this.supplier = other.supplier; // Shallow copy
        this.inventory = new ArrayList<>(other.inventory); // Shallow copy of list
        this.attributes = new HashMap<>(other.attributes); // Shallow copy of map
        this.createdDate = other.createdDate;
        this.modifiedDate = other.modifiedDate;
        this.createdBy = other.createdBy;
        this.modifiedBy = other.modifiedBy;
        this.active = other.active;
        this.minimumStockLevel = other.minimumStockLevel;
        this.maximumStockLevel = other.maximumStockLevel;
        this.warehouseLocation = other.warehouseLocation;
        this.weight = other.weight;
        this.weightUnit = other.weightUnit;
        this.volume = other.volume;
        this.volumeUnit = other.volumeUnit;
        this.manufacturer = other.manufacturer;
        this.brand = other.brand;
        this.warrantyPeriod = other.warrantyPeriod;
        this.returnPolicy = other.returnPolicy;
        this.taxable = other.taxable;
        this.discountAllowed = other.discountAllowed;
        this.discountPercentage = other.discountPercentage;
    }

    // Getters and Setters

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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<InventoryItem> getInventory() {
        return inventory;
    }

    public void setInventory(List<InventoryItem> inventory) {
        this.inventory = inventory;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getMinimumStockLevel() {
        return minimumStockLevel;
    }

    public void setMinimumStockLevel(int minimumStockLevel) {
        this.minimumStockLevel = minimumStockLevel;
    }

    public int getMaximumStockLevel() {
        return maximumStockLevel;
    }

    public void setMaximumStockLevel(int maximumStockLevel) {
        this.maximumStockLevel = maximumStockLevel;
    }

    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getVolumeUnit() {
        return volumeUnit;
    }

    public void setVolumeUnit(String volumeUnit) {
        this.volumeUnit = volumeUnit;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getReturnPolicy() {
        return returnPolicy;
    }

    public void setReturnPolicy(String returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    public boolean isTaxable() {
        return taxable;
    }

    public void setTaxable(boolean taxable) {
        this.taxable = taxable;
    }

    public boolean isDiscountAllowed() {
        return discountAllowed;
    }

    public void setDiscountAllowed(boolean discountAllowed) {
        this.discountAllowed = discountAllowed;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ERPProduct that = (ERPProduct) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(name, that.name) &&
               Objects.equals(sku, that.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sku);
    }

    @Override
    public String toString() {
        return "ERPProduct{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sku='" + sku + '\'' +
                ", price=" + price +
                ", category=" + (category != null ? category.getName() : "null") +
                ", supplier=" + (supplier != null ? supplier.getName() : "null") +
                ", inventorySize=" + inventory.size() +
                ", attributesSize=" + attributes.size() +
                '}';
    }
}
