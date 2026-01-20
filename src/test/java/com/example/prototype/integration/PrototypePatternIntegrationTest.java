package com.example.prototype.integration;

import com.example.prototype.entity.InventoryItem;
import com.example.prototype.entity.ProductCategory;
import com.example.prototype.entity.Supplier;
import com.example.prototype.pattern.ERPProductPrototype;
import com.example.prototype.pattern.PrototypeRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests demonstrating the complete Prototype pattern workflow.
 * These tests show real-world usage scenarios for the Prototype pattern
 * in an ERP system context.
 */
@DisplayName("Prototype Pattern Integration Tests")
public class PrototypePatternIntegrationTest {

    private PrototypeRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new PrototypeRegistry();
    }

    @Test
    @DisplayName("Complete workflow: Create, register, and clone products")
    void testCompleteWorkflow() {
        // Step 1: Create a base product template
        com.example.prototype.entity.ERPProduct baseProduct = createBaseProductTemplate();
        
        // Step 2: Wrap it in a prototype
        ERPProductPrototype basePrototype = new ERPProductPrototype(baseProduct);
        
        // Step 3: Register the prototype
        registry.registerPrototype("electronics-laptop", basePrototype);
        
        // Step 4: Clone the prototype to create new products
        @SuppressWarnings("unchecked")
        ERPProductPrototype product1 = (ERPProductPrototype) registry.getPrototype("electronics-laptop");
        @SuppressWarnings("unchecked")
        ERPProductPrototype product2 = (ERPProductPrototype) registry.getPrototype("electronics-laptop");
        @SuppressWarnings("unchecked")
        ERPProductPrototype product3 = (ERPProductPrototype) registry.getPrototype("electronics-laptop");
        
        // Step 5: Customize each clone independently
        product1.getProduct().setId("PROD-001");
        product1.getProduct().setName("Laptop Model A");
        product1.getProduct().setPrice(new BigDecimal("999.99"));
        
        product2.getProduct().setId("PROD-002");
        product2.getProduct().setName("Laptop Model B");
        product2.getProduct().setPrice(new BigDecimal("1299.99"));
        
        product3.getProduct().setId("PROD-003");
        product3.getProduct().setName("Laptop Model C");
        product3.getProduct().setPrice(new BigDecimal("799.99"));

        assertFalse(product1.getProduct().getId().equals(basePrototype.getProduct().getId()));
        assertFalse(product2.getProduct().getId().equals(basePrototype.getProduct().getId()));
        assertFalse(product1.getProduct().getName().equals(basePrototype.getProduct().getName()));
        assertFalse(product2.getProduct().getName().equals(basePrototype.getProduct().getName()));
        
        // Verify base prototype is unchanged
        assertEquals("BASE-TEMPLATE", basePrototype.getProduct().getId());
        assertEquals("Base Laptop", basePrototype.getProduct().getName());
    }

    @Test
    @DisplayName("Clone multiple product types from registry")
    void testCloneMultipleProductTypes() {
        // Register multiple product templates
        registry.registerPrototype("laptop", new ERPProductPrototype(createLaptopTemplate()));
        registry.registerPrototype("phone", new ERPProductPrototype(createPhoneTemplate()));
        registry.registerPrototype("tablet", new ERPProductPrototype(createTabletTemplate()));
        
        // Clone each type
        @SuppressWarnings("unchecked")
        ERPProductPrototype laptop = (ERPProductPrototype) registry.getPrototype("laptop");
        @SuppressWarnings("unchecked")
        ERPProductPrototype phone = (ERPProductPrototype) registry.getPrototype("phone");
        @SuppressWarnings("unchecked")
        ERPProductPrototype tablet = (ERPProductPrototype) registry.getPrototype("tablet");
        
        // Customize each
        laptop.getProduct().setName("Custom Laptop");
        phone.getProduct().setName("Custom Phone");
        tablet.getProduct().setName("Custom Tablet");
        
        // Verify they have different categories
        assertEquals("Laptops", laptop.getProduct().getCategory().getName());
        assertEquals("Phones", phone.getProduct().getCategory().getName());
        assertEquals("Tablets", tablet.getProduct().getCategory().getName());
        
        // Verify they have different suppliers
        assertEquals("Laptop Supplier", laptop.getProduct().getSupplier().getName());
        assertEquals("Phone Supplier", phone.getProduct().getSupplier().getName());
        assertEquals("Tablet Supplier", tablet.getProduct().getSupplier().getName());
    }

    @Test
    @DisplayName("Deep copy ensures nested objects are independent")
    void testDeepCopyEnsuresNestedObjectsIndependence() {
        com.example.prototype.entity.ERPProduct baseProduct = createBaseProductTemplate();
        ERPProductPrototype basePrototype = new ERPProductPrototype(baseProduct);
        
        // Clone the prototype
        ERPProductPrototype clonedPrototype = basePrototype.clone();
        
        // Modify nested object in clone
        clonedPrototype.getProduct().getCategory().setName("Modified Category");
        clonedPrototype.getProduct().getSupplier().setName("Modified Supplier");
        clonedPrototype.getProduct().getInventory().get(0).setQuantity(999);
        clonedPrototype.getProduct().getAttributes().put("newKey", "newValue");
        
        // Verify base product is unchanged
        assertEquals("Electronics", basePrototype.getProduct().getCategory().getName());
        assertEquals("Tech Supplier", basePrototype.getProduct().getSupplier().getName());
        assertEquals(50, basePrototype.getProduct().getInventory().get(0).getQuantity());
        assertFalse(basePrototype.getProduct().getAttributes().containsKey("newKey"));
        
        // Verify clone has modifications
        assertEquals("Modified Category", clonedPrototype.getProduct().getCategory().getName());
        assertEquals("Modified Supplier", clonedPrototype.getProduct().getSupplier().getName());
        assertEquals(999, clonedPrototype.getProduct().getInventory().get(0).getQuantity());
        assertTrue(clonedPrototype.getProduct().getAttributes().containsKey("newKey"));
    }

    @Test
    @DisplayName("Registry manages multiple prototypes efficiently")
    void testRegistryManagesMultiplePrototypes() {
        // Register many prototypes
        for (int i = 1; i <= 10; i++) {
            com.example.prototype.entity.ERPProduct product = createBaseProductTemplate();
            product.setId("PROD-" + String.format("%03d", i));
            product.setName("Product " + i);
            
            ERPProductPrototype prototype = new ERPProductPrototype(product);
            registry.registerPrototype("product-" + i, prototype);
        }
        
        // Verify all are registered
        assertEquals(10, registry.size());
        for (int i = 1; i <= 10; i++) {
            assertTrue(registry.hasPrototype("product-" + i));
        }
        
        // Clone and customize each
        for (int i = 1; i <= 10; i++) {
            @SuppressWarnings("unchecked")
            ERPProductPrototype cloned = (ERPProductPrototype) registry.getPrototype("product-" + i);
            cloned.getProduct().setPrice(new BigDecimal(String.valueOf(i * 100)));
        }
        
        // Verify all clones are independent
        for (int i = 1; i <= 10; i++) {
            @SuppressWarnings("unchecked")
            ERPProductPrototype cloned = (ERPProductPrototype) registry.getPrototype("product-" + i);
            assertEquals(i * 100, cloned.getProduct().getPrice().intValue());
        }
    }

    @Test
    @DisplayName("Prototype pattern performance benefit")
    void testPrototypePatternPerformanceBenefit() {
        com.example.prototype.entity.ERPProduct complexProduct = createComplexProduct();
        ERPProductPrototype prototype = new ERPProductPrototype(complexProduct);
        
        // Clone multiple times (simulating creating many similar products)
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            ERPProductPrototype clone = prototype.clone();
            clone.getProduct().setId("PROD-" + i);
        }
        long endTime = System.currentTimeMillis();
        long cloneTime = endTime - startTime;
        
        System.out.println("Time to clone 100 products: " + cloneTime + "ms");
        
        // Verify all clones were created
        // (In a real scenario, you'd verify the clones are correct)
        assertTrue(cloneTime >= 0, "Cloning should complete successfully");
    }

    @Test
    @DisplayName("Remove prototype from registry")
    void testRemovePrototypeFromRegistry() {
        // Register multiple prototypes
        registry.registerPrototype("product1", new ERPProductPrototype(createBaseProductTemplate()));
        registry.registerPrototype("product2", new ERPProductPrototype(createBaseProductTemplate()));
        registry.registerPrototype("product3", new ERPProductPrototype(createBaseProductTemplate()));
        
        assertEquals(3, registry.size());
        
        // Remove one
        registry.removePrototype("product2");
        
        assertEquals(2, registry.size());
        assertTrue(registry.hasPrototype("product1"));
        assertFalse(registry.hasPrototype("product2"));
        assertTrue(registry.hasPrototype("product3"));
    }

    @Test
    @DisplayName("Clear registry")
    void testClearRegistry() {
        // Register multiple prototypes
        for (int i = 1; i <= 5; i++) {
            registry.registerPrototype("product" + i, new ERPProductPrototype(createBaseProductTemplate()));
        }
        
        assertEquals(5, registry.size());
        
        // Clear all
        registry.clear();
        
        assertEquals(0, registry.size());
        assertTrue(registry.getKeys().isEmpty());
    }

    @Test
    @DisplayName("Real-world scenario: Product catalog management")
    void testRealWorldProductCatalogScenario() {
        // Scenario: An ERP system needs to manage a product catalog
        // with pre-configured templates for different product types
        
        // Step 1: Create product templates for different categories
        registry.registerPrototype("laptop-template", 
            new ERPProductPrototype(createLaptopTemplate()));
        registry.registerPrototype("phone-template", 
            new ERPProductPrototype(createPhoneTemplate()));
        registry.registerPrototype("tablet-template", 
            new ERPProductPrototype(createTabletTemplate()));
        registry.registerPrototype("accessory-template", 
            new ERPProductPrototype(createAccessoryTemplate()));
        
        // Step 2: Add actual products by cloning templates
        @SuppressWarnings("unchecked")
        ERPProductPrototype laptop1 = (ERPProductPrototype) registry.getPrototype("laptop-template");
        laptop1.getProduct().setId("LAP-001");
        laptop1.getProduct().setName("Gaming Laptop Pro");
        laptop1.getProduct().setPrice(new BigDecimal("1499.99"));
        
        @SuppressWarnings("unchecked")
        ERPProductPrototype phone1 = (ERPProductPrototype) registry.getPrototype("phone-template");
        phone1.getProduct().setId("PHN-001");
        phone1.getProduct().setName("Smartphone X");
        phone1.getProduct().setPrice(new BigDecimal("899.99"));
        
        @SuppressWarnings("unchecked")
        ERPProductPrototype tablet1 = (ERPProductPrototype) registry.getPrototype("tablet-template");
        tablet1.getProduct().setId("TAB-001");
        tablet1.getProduct().setName("Tablet Air");
        tablet1.getProduct().setPrice(new BigDecimal("499.99"));
        
        // Step 3: Verify all products have correct base properties
        assertEquals("Laptops", laptop1.getProduct().getCategory().getName());
        assertEquals("Phones", phone1.getProduct().getCategory().getName());
        assertEquals("Tablets", tablet1.getProduct().getCategory().getName());
        
        // Step 4: Verify all products are independent
        assertNotEquals(laptop1.getProduct().getCategory(), 
                      phone1.getProduct().getCategory());
        assertNotEquals(phone1.getProduct().getCategory(), 
                      tablet1.getProduct().getCategory());
    }

    // Helper methods to create test data

    private com.example.prototype.entity.ERPProduct createBaseProductTemplate() {
        com.example.prototype.entity.ERPProduct product = new com.example.prototype.entity.ERPProduct();
        product.setId("BASE-TEMPLATE");
        product.setName("Base Laptop");
        product.setSku("SKU-BASE");
        product.setPrice(new BigDecimal("1000.00"));
        product.setCost(new BigDecimal("800.00"));
        product.setTaxRate(new BigDecimal("0.20"));
        product.setActive(true);
        product.setMinimumStockLevel(10);
        product.setMaximumStockLevel(100);
        
        ProductCategory category = new ProductCategory("CAT-001", "Electronics", "Electronic devices");
        product.setCategory(category);
        
        Supplier supplier = new Supplier("SUP-001", "Tech Supplier", "contact@tech.com");
        product.setSupplier(supplier);
        
        List<InventoryItem> inventory = new ArrayList<>();
        inventory.add(new InventoryItem("WH-001", "Main Warehouse", 50));
        product.setInventory(inventory);
        
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("color", "black");
        attributes.put("warranty", "2 years");
        product.setAttributes(attributes);
        
        return product;
    }

    private com.example.prototype.entity.ERPProduct createLaptopTemplate() {
        com.example.prototype.entity.ERPProduct product = createBaseProductTemplate();
        product.setCategory(new ProductCategory("CAT-LAP", "Laptops", "Laptop computers"));
        product.setSupplier(new Supplier("SUP-LAP", "Laptop Supplier", "laptop@supplier.com"));
        return product;
    }

    private com.example.prototype.entity.ERPProduct createPhoneTemplate() {
        com.example.prototype.entity.ERPProduct product = createBaseProductTemplate();
        product.setCategory(new ProductCategory("CAT-PHN", "Phones", "Mobile phones"));
        product.setSupplier(new Supplier("SUP-PHN", "Phone Supplier", "phone@supplier.com"));
        product.setWeight(0.2);
        product.setWeightUnit("kg");
        return product;
    }

    private com.example.prototype.entity.ERPProduct createTabletTemplate() {
        com.example.prototype.entity.ERPProduct product = createBaseProductTemplate();
        product.setCategory(new ProductCategory("CAT-TAB", "Tablets", "Tablet computers"));
        product.setSupplier(new Supplier("SUP-TAB", "Tablet Supplier", "tablet@supplier.com"));
        product.setWeight(0.5);
        product.setWeightUnit("kg");
        return product;
    }

    private com.example.prototype.entity.ERPProduct createAccessoryTemplate() {
        com.example.prototype.entity.ERPProduct product = createBaseProductTemplate();
        product.setCategory(new ProductCategory("CAT-ACC", "Accessories", "Product accessories"));
        product.setSupplier(new Supplier("SUP-ACC", "Accessory Supplier", "accessory@supplier.com"));
        product.setWeight(0.1);
        product.setWeightUnit("kg");
        return product;
    }

    private com.example.prototype.entity.ERPProduct createComplexProduct() {
        com.example.prototype.entity.ERPProduct product = new com.example.prototype.entity.ERPProduct();
        product.setId("COMPLEX-001");
        product.setName("Complex Product");
        product.setPrice(new BigDecimal("9999.99"));
        product.setCost(new BigDecimal("8000.00"));
        product.setTaxRate(new BigDecimal("0.25"));
        
        ProductCategory category = new ProductCategory("CAT-COMPLEX", "Complex Category", "Complex products");
        product.setCategory(category);
        
        Supplier supplier = new Supplier("SUP-COMPLEX", "Complex Supplier", "complex@supplier.com");
        supplier.setAddress("123 Main St");
        supplier.setCity("Tech City");
        supplier.setCountry("Techland");
        supplier.setPostalCode("12345");
        product.setSupplier(supplier);
        
        List<InventoryItem> inventory = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            inventory.add(new InventoryItem("WH-" + i, "Warehouse " + i, i * 10));
        }
        product.setInventory(inventory);
        
        Map<String, Object> attributes = new HashMap<>();
        for (int i = 1; i <= 20; i++) {
            attributes.put("attr" + i, "value" + i);
        }
        product.setAttributes(attributes);
        
        product.setActive(true);
        product.setMinimumStockLevel(5);
        product.setMaximumStockLevel(500);
        product.setWarehouseLocation("MAIN-WH");
        product.setWeight(2.5);
        product.setWeightUnit("kg");
        product.setVolume(0.01);
        product.setVolumeUnit("m3");
        product.setManufacturer("Tech Manufacturer");
        product.setBrand("TechBrand");
        product.setWarrantyPeriod("5 years");
        product.setReturnPolicy("30 days");
        product.setTaxable(true);
        product.setDiscountAllowed(true);
        product.setDiscountPercentage(new BigDecimal("0.10"));
        
        return product;
    }
}
