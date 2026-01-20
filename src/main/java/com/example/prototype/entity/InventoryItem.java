package com.example.prototype.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * InventoryItem - Represents inventory information for a product in a specific warehouse.
 * This is a nested object within ERPProduct's inventory list.
 */
public class InventoryItem implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private String warehouseId;
    private String warehouseName;
    private int quantity;
    private int reservedQuantity;
    private int availableQuantity;
    private LocalDateTime lastUpdated;
    private String location;
    private String binNumber;

    public InventoryItem() {
        this.lastUpdated = LocalDateTime.now();
    }

    public InventoryItem(String warehouseId, String warehouseName, int quantity) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.quantity = quantity;
        this.availableQuantity = quantity;
        this.reservedQuantity = 0;
        this.lastUpdated = LocalDateTime.now();
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateAvailableQuantity();
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(int reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
        updateAvailableQuantity();
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    private void updateAvailableQuantity() {
        this.availableQuantity = quantity - reservedQuantity;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBinNumber() {
        return binNumber;
    }

    public void setBinNumber(String binNumber) {
        this.binNumber = binNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItem that = (InventoryItem) o;
        return Objects.equals(warehouseId, that.warehouseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(warehouseId);
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "warehouseId='" + warehouseId + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", quantity=" + quantity +
                ", availableQuantity=" + availableQuantity +
                ", reservedQuantity=" + reservedQuantity +
                '}';
    }
}
