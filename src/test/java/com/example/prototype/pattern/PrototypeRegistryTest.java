package com.example.prototype.pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for PrototypeRegistry class.
 * Tests the registry functionality for managing prototype instances.
 */
@DisplayName("PrototypeRegistry Tests")
public class PrototypeRegistryTest {

    private PrototypeRegistry registry;
    private Prototype<String> testPrototype;

    @BeforeEach
    void setUp() {
        registry = new PrototypeRegistry();
        testPrototype = new Prototype<String>() {
            private final String value = "test-value";

            @Override
            public String clone() {
                return value;
            }
        };
    }

    @Test
    @DisplayName("Register prototype should succeed")
    void testRegisterPrototype() {
        registry.registerPrototype("test-key", testPrototype);
        
        assertTrue(registry.hasPrototype("test-key"));
        assertEquals(1, registry.size());
    }

    @Test
    @DisplayName("Register duplicate prototype should throw exception")
    void testRegisterDuplicatePrototype() {
        registry.registerPrototype("test-key", testPrototype);
        
        assertThrows(
            IllegalStateException.class,
            () -> registry.registerPrototype("test-key", testPrototype),
            "Should throw IllegalStateException for duplicate key"
        );
    }

    @Test
    @DisplayName("Get prototype should return clone")
    void testGetPrototype() {
        registry.registerPrototype("test-key", testPrototype);
        
        @SuppressWarnings("unchecked")
        Prototype<String> retrievedPrototype = (Prototype<String>) registry.getPrototype("test-key");
        
        assertNotNull(retrievedPrototype);
        assertEquals("test-value", retrievedPrototype.clone());
    }

    @Test
    @DisplayName("Get non-existent prototype should throw exception")
    void testGetNonExistentPrototype() {
        assertThrows(
            IllegalStateException.class,
            () -> registry.getPrototype("non-existent-key"),
            "Should throw IllegalStateException for non-existent key"
        );
    }

    @Test
    @DisplayName("Has prototype should return true for existing key")
    void testHasPrototypeForExistingKey() {
        registry.registerPrototype("test-key", testPrototype);
        
        assertTrue(registry.hasPrototype("test-key"));
    }

    @Test
    @DisplayName("Has prototype should return false for non-existing key")
    void testHasPrototypeForNonExistingKey() {
        assertFalse(registry.hasPrototype("non-existent-key"));
    }

    @Test
    @DisplayName("Remove prototype should succeed")
    void testRemovePrototype() {
        registry.registerPrototype("test-key", testPrototype);
        assertTrue(registry.hasPrototype("test-key"));
        
        registry.removePrototype("test-key");
        
        assertFalse(registry.hasPrototype("test-key"));
        assertEquals(0, registry.size());
    }

    @Test
    @DisplayName("Remove non-existent prototype should not throw exception")
    void testRemoveNonExistentPrototype() {
        assertDoesNotThrow(
            () -> registry.removePrototype("non-existent-key"),
            "Should not throw exception for non-existent key"
        );
    }

    @Test
    @DisplayName("Size should return correct count")
    void testSize() {
        assertEquals(0, registry.size());
        
        registry.registerPrototype("key1", testPrototype);
        assertEquals(1, registry.size());
        
        registry.registerPrototype("key2", testPrototype);
        assertEquals(2, registry.size());
        
        registry.registerPrototype("key3", testPrototype);
        assertEquals(3, registry.size());
    }

    @Test
    @DisplayName("Clear should remove all prototypes")
    void testClear() {
        registry.registerPrototype("key1", testPrototype);
        registry.registerPrototype("key2", testPrototype);
        registry.registerPrototype("key3", testPrototype);
        assertEquals(3, registry.size());
        
        registry.clear();
        
        assertEquals(0, registry.size());
        assertFalse(registry.hasPrototype("key1"));
        assertFalse(registry.hasPrototype("key2"));
        assertFalse(registry.hasPrototype("key3"));
    }

    @Test
    @DisplayName("Get keys should return all registered keys")
    void testGetKeys() {
        registry.registerPrototype("key1", testPrototype);
        registry.registerPrototype("key2", testPrototype);
        registry.registerPrototype("key3", testPrototype);
        
        var keys = registry.getKeys();
        
        assertEquals(3, keys.size());
        assertTrue(keys.contains("key1"));
        assertTrue(keys.contains("key2"));
        assertTrue(keys.contains("key3"));
    }

    @Test
    @DisplayName("Get keys should return empty set for empty registry")
    void testGetKeysForEmptyRegistry() {
        var keys = registry.getKeys();
        
        assertTrue(keys.isEmpty());
    }

    @Test
    @DisplayName("Multiple get operations should return independent clones")
    void testMultipleGetOperationsReturnIndependentClones() {
        registry.registerPrototype("test-key", testPrototype);
        
        @SuppressWarnings("unchecked")
        Prototype<String> clone1 = (Prototype<String>) registry.getPrototype("test-key");
        @SuppressWarnings("unchecked")
        Prototype<String> clone2 = (Prototype<String>) registry.getPrototype("test-key");
        @SuppressWarnings("unchecked")
        Prototype<String> clone3 = (Prototype<String>) registry.getPrototype("test-key");
        
        assertNotNull(clone1);
        assertNotNull(clone2);
        assertNotNull(clone3);
        
        // All clones should have the same value
        assertEquals("test-value", clone1.clone());
        assertEquals("test-value", clone2.clone());
        assertEquals("test-value", clone3.clone());
    }

    @Test
    @DisplayName("Registry should handle multiple different prototypes")
    void testRegistryHandlesMultipleDifferentPrototypes() {
        Prototype<String> prototype1 = new Prototype<String>() {
            @Override
            public String clone() {
                return "value1";
            }
        };
        
        Prototype<String> prototype2 = new Prototype<String>() {
            @Override
            public String clone() {
                return "value2";
            }
        };
        
        Prototype<String> prototype3 = new Prototype<String>() {
            @Override
            public String clone() {
                return "value3";
            }
        };
        
        registry.registerPrototype("key1", prototype1);
        registry.registerPrototype("key2", prototype2);
        registry.registerPrototype("key3", prototype3);
        
        @SuppressWarnings("unchecked")
        String value1 = ((Prototype<String>) registry.getPrototype("key1")).clone();
        @SuppressWarnings("unchecked")
        String value2 = ((Prototype<String>) registry.getPrototype("key2")).clone();
        @SuppressWarnings("unchecked")
        String value3 = ((Prototype<String>) registry.getPrototype("key3")).clone();
        
        assertEquals("value1", value1);
        assertEquals("value2", value2);
        assertEquals("value3", value3);
    }

    @Test
    @DisplayName("Registry should be initially empty")
    void testRegistryInitiallyEmpty() {
        assertEquals(0, registry.size());
        assertTrue(registry.getKeys().isEmpty());
        assertFalse(registry.hasPrototype("any-key"));
    }
}
