package com.example.prototype.pattern;

import com.example.prototype.entity.InventoryItem;
import com.example.prototype.entity.ProductCategory;
import com.example.prototype.entity.Supplier;
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
 * Unit tests for ERPProductPrototype class.
 * Tests the Prototype pattern implementation for deep copying ERPProduct entities.
 */
@DisplayName("ERPProductPrototype Tests")
public class ERPProductPrototypeTest {

    private com.example.prototype.entity.ERPProduct originalProduct;

    @BeforeEach
    void setUp() {
        originalProduct = createSampleProduct();
    }

    @Test
    @DisplayName("Clone should create a new instance")
    void testCloneCreatesNewInstance() {
        ERPProductPrototype prototype = new ERPProductPrototype(originalProduct);
        ERPProductPrototype clonedPrototype = prototype.clone();

        assertNotSame(
            prototype.getProduct(),
            clonedPrototype.getProduct(),
            "Cloned product should be a different instance"
        );
    }

    @Test
    @DisplayName("Clone should create a deep copy with same values")
    void testCloneCreatesDeepCopyWithSameValues() {
        ERPProductPrototype prototype = new ERPProductPrototype(originalProduct);
        ERPProductPrototype clonedPrototype = prototype.clone();

        com.example.prototype.entity.ERPProduct original = prototype.getProduct();
        com.example.prototype.entity.ERPProduct cloned = clonedPrototype.getProduct();

        assertEquals(original.getId(), cloned.getId());
        assertEquals(original.getName(), cloned.getName());
        assertEquals(original.getSku(), cloned.getSku());
        assertEquals(original.getPrice(), cloned.getPrice());
        assertEquals(original.getCost(), cloned.getCost());
        assertEquals(original.getTaxRate(), cloned.getTaxRate());
    }

    @Test
    @DisplayName("Clone should create independent copy - nested objects")
    void testCloneCreatesIndependentCopy_NestedObjects() {
        ERPProductPrototype prototype = new ERPProductPrototype(originalProduct);
        ERPProductPrototype clonedPrototype = prototype.clone();

        com.example.prototype.entity.ERPProduct original = prototype.getProduct();
        com.example.prototype.entity.ERPProduct cloned = clonedPrototype.getProduct();

        assertNotSame(
            original.getCategory(),
            cloned.getCategory(),
            "Category should be a different instance"
        );
        assertNotSame(
            original.getSupplier(),
            cloned.getSupplier(),
            "Supplier should be a different instance"
        );
    }

    @Test
    @DisplayName("Clone should create independent copy - collections")
    void testCloneCreatesIndependentCopy_Collections() {
        ERPProductPrototype prototype = new ERPProductPrototype(originalProduct);
        ERPProductPrototype clonedPrototype = prototype.clone();

        com.example.prototype.entity.ERPProduct original = prototype.getProduct();
        com.example.prototype.entity.ERPProduct cloned = clonedPrototype.getProduct();

        assertNotSame(
            original.getInventory(),
            cloned.getInventory(),
            "Inventory list should be a different instance"
        );
        assertNotSame(
            original.getAttributes(),
            cloned.getAttributes(),
            "Attributes map should be a different instance"
        );
    }

    @Test
    @DisplayName("Clone should create independent copy - list elements")
    void testCloneCreatesIndependentCopy_ListElements() {
        ERPProductPrototype prototype = new ERPProductPrototype(originalProduct);
        ERPProductPrototype clonedPrototype = prototype.clone();

        com.example.prototype.entity.ERPProduct original = prototype.getProduct();
        com.example.prototype.entity.ERPProduct cloned = clonedPrototype.getProduct();

        List<InventoryItem> originalInventory = original.getInventory();
        List<InventoryItem> clonedInventory = cloned.getInventory();

        assertEquals(originalInventory.size(), clonedInventory.size());
        
        if (!originalInventory.isEmpty()) {
            assertNotSame(
                originalInventory.get(0),
                clonedInventory.get(0),
                "Inventory items should be different instances"
            );
        }
    }

    @Test
    @DisplayName("Clone should create independent copy - map entries")
    void testCloneCreatesIndependentCopy_MapEntries() {
        ERPProductPrototype prototype = new ERPProductPrototype(originalProduct);
        ERPProductPrototype clonedPrototype = prototype.clone();

        com.example.prototype.entity.ERPProduct original = prototype.getProduct();
        com.example.prototype.entity.ERPProduct cloned = clonedPrototype.getProduct();

        Map<String, Object> originalAttributes = original.getAttributes();
        Map<String, Object> clonedAttributes = cloned.getAttributes();

        assertEquals(originalAttributes.size(), clonedAttributes.size());
        
        // Verify map entries are independent
        originalAttributes.put("newKey", "newValue");
        assertFalse(clonedAttributes.containsKey("newKey"));
    }

    @Test
    @DisplayName("Modifications to clone should not affect original")
    void testModificationsToCloneDoNotAffectOriginal() {
        ERPProductPrototype prototype = new ERPProductPrototype(originalProduct);
        ERPProductPrototype clonedPrototype = prototype.clone();

        com.example.prototype.entity.ERPProduct original = prototype.getProduct();
        com.example.prototype.entity.ERPProduct cloned = clonedPrototype.getProduct();

        // Modify the clone
        cloned.setName("Modified Name");
        cloned.setPrice(new BigDecimal("999.99"));
        cloned.getCategory().setName("Modified Category");
        cloned.getInventory().clear();
        cloned.getAttributes().put("newKey", "newValue");

        // Verify original is unchanged
        assertEquals("Sample Product", original.getName());
        assertEquals(new BigDecimal("100.00"), original.getPrice());
        assertEquals("Electronics", original.getCategory().getName());
        assertFalse(original.getInventory().isEmpty());
        assertFalse(original.getAttributes().containsKey("newKey"));
    }

    @Test
    @DisplayName("Modifications to original should not affect clone")
    void testModificationsToOriginalDoNotAffectClone() {
        ERPProductPrototype prototype = new ERPProductPrototype(originalProduct);
        ERPProductPrototype clonedPrototype = prototype.clone();

        com.example.prototype.entity.ERPProduct original = prototype.getProduct();
        com.example.prototype.entity.ERPProduct cloned = clonedPrototype.getProduct();

        // Modify the original
        original.setName("Modified Name");
        original.setPrice(new BigDecimal("999.99"));
        original.getCategory().setName("Modified Category");
        original.getInventory().clear();
        original.getAttributes().put("newKey", "newValue");

        // Verify clone is unchanged
        assertEquals("Sample Product", cloned.getName());
        assertEquals(new BigDecimal("100.00"), cloned.getPrice());
        assertEquals("Electronics", cloned.getCategory().getName());
        assertFalse(cloned.getInventory().isEmpty());
        assertFalse(cloned.getAttributes().containsKey("newKey"));
    }

    @Test
    @DisplayName("Constructor should throw exception for null product")
    void testConstructorThrowsExceptionForNullProduct() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new ERPProductPrototype(null),
            "Constructor should throw IllegalArgumentException for null product"
        );
    }

    @Test
    @DisplayName("Multiple clones should be independent of each other")
    void testMultipleClonesAreIndependent() {
        ERPProductPrototype prototype = new ERPProductPrototype(originalProduct);
        ERPProductPrototype clone1 = prototype.clone();
        ERPProductPrototype clone2 = prototype.clone();
        ERPProductPrototype clone3 = prototype.clone();

        com.example.prototype.entity.ERPProduct product1 = clone1.getProduct();
        com.example.prototype.entity.ERPProduct product2 = clone2.getProduct();
        com.example.prototype.entity.ERPProduct product3 = clone3.getProduct();

        // Modify each clone differently
        product1.setName("Clone 1");
        product2.setName("Clone 2");
        product3.setName("Clone 3");

        assertEquals("Clone 1", product1.getName());
        assertEquals("Clone 2", product2.getName());
        assertEquals("Clone 3", product3.getName());
    }

    @Test
    @DisplayName("Shallow clone should share the same product instance")
    void testShallowCloneSharesSameInstance() {
        ERPProductPrototype prototype = new ERPProductPrototype(originalProduct);
        ERPProductPrototype shallowClone = prototype.shallowClone();

        assertSame(
            prototype.getProduct(),
            shallowClone.getProduct(),
            "Shallow clone should share the same product instance"
        );
    }

    @Test
    @DisplayName("Equals should return true for equal products")
    void testEqualsForEqualProducts() {
        ERPProductPrototype prototype1 = new ERPProductPrototype(originalProduct);
        ERPProductPrototype prototype2 = new ERPProductPrototype(originalProduct);

        assertEquals(prototype1, prototype2);
        assertEquals(prototype1.hashCode(), prototype2.hashCode());
    }

    @Test
    @DisplayName("Equals should return false for different products")
    void testEqualsForDifferentProducts() {
        ERPProductPrototype prototype1 = new ERPProductPrototype(originalProduct);
        
        com.example.prototype.entity.ERPProduct differentProduct = createSampleProduct();
        differentProduct.setId("different-id");
        ERPProductPrototype prototype2 = new ERPProductPrototype(differentProduct);

        assertNotEquals(prototype1, prototype2);
    }

    @Test
    @DisplayName("Clone preserves all product fields")
    void testClonePreservesAllProductFields() {
        ERPProductPrototype prototype = new ERPProductPrototype(originalProduct);
        ERPProductPrototype clonedPrototype = prototype.clone();

        com.example.prototype.entity.ERPProduct original = prototype.getProduct();
        com.example.prototype.entity.ERPProduct cloned = clonedPrototype.getProduct();

        // Verify all important fields are preserved
        assertEquals(original.getId(), cloned.getId());
        assertEquals(original.getName(), cloned.getName());
        assertEquals(original.getDescription(), cloned.getDescription());
        assertEquals(original.getSku(), cloned.getSku());
        assertEquals(original.getBarcode(), cloned.getBarcode());
        assertEquals(original.getPrice(), cloned.getPrice());
        assertEquals(original.getCost(), cloned.getCost());
        assertEquals(original.getTaxRate(), cloned.getTaxRate());
        assertEquals(original.isActive(), cloned.isActive());
        assertEquals(original.getMinimumStockLevel(), cloned.getMinimumStockLevel());
        assertEquals(original.getMaximumStockLevel(), cloned.getMaximumStockLevel());
        assertEquals(original.getWarehouseLocation(), cloned.getWarehouseLocation());
        assertEquals(original.getWeight(), cloned.getWeight());
        assertEquals(original.getWeightUnit(), cloned.getWeightUnit());
    }

    /**
     * Helper method to create a sample ERPProduct for testing.
     */
    private com.example.prototype.entity.ERPProduct createSampleProduct() {
        com.example.prototype.entity.ERPProduct product = new com.example.prototype.entity.ERPProduct();
        product.setId("PROD-001");
        product.setName("Sample Product");
        product.setDescription("A sample product for testing");
        product.setSku("SKU-001");
        product.setBarcode("1234567890");
        product.setPrice(new BigDecimal("100.00"));
        product.setCost(new BigDecimal("75.00"));
        product.setTaxRate(new BigDecimal("0.20"));
        product.setActive(true);
        product.setMinimumStockLevel(10);
        product.setMaximumStockLevel(100);
        product.setWarehouseLocation("WH-001");
        product.setWeight(1.5);
        product.setWeightUnit("kg");
        product.setCreatedBy("test-user");
        product.setModifiedBy("test-user");

        // Create nested objects
        ProductCategory category = new ProductCategory("CAT-001", "Electronics", "Electronic devices");
        product.setCategory(category);

        Supplier supplier = new Supplier("SUP-001", "Tech Supplier", "contact@tech.com");
        product.setSupplier(supplier);

        // Create inventory items
        List<InventoryItem> inventory = new ArrayList<>();
        inventory.add(new InventoryItem("WH-001", "Main Warehouse", 50));
        inventory.add(new InventoryItem("WH-002", "Secondary Warehouse", 30));
        product.setInventory(inventory);

        // Create attributes
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("color", "black");
        attributes.put("warranty", "2 years");
        attributes.put("weight", 1.5);
        product.setAttributes(attributes);

        return product;
    }
}
