# Prototype Pattern Implementation - GoF Design Pattern

## Overview

This project demonstrates the **Prototype Pattern** (Gang of Four Design Patterns) implemented in Java 17 with Maven. The Prototype pattern is used to create deep copies of complex objects without modifying the original classes.

## Problem Statement

In an ERP system, we have a large, complex entity class `ERPProduct` that cannot be modified. We need to create deep copies of this entity efficiently for various operations such as:
- Creating product variants
- Managing product catalogs
- Implementing product templates
- Bulk product creation

## Solution: Prototype Pattern

The Prototype pattern solves this problem by:
1. **Wrapping** the immutable entity in a prototype class
2. **Implementing** deep copy logic without modifying the original entity
3. **Providing** a clone method that creates independent copies
4. **Managing** prototypes in a registry for easy access

## Project Structure

```
glm/
├── pom.xml                                    # Maven configuration
├── README.md                                  # This file
├── plans/
│   └── prototype-pattern-implementation.md   # Detailed implementation plan
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           └── example/
    │               └── prototype/
    │                   ├── entity/
    │                   │   ├── ERPProduct.java           # Large, immutable entity
    │                   │   ├── ProductCategory.java      # Nested object
    │                   │   ├── Supplier.java            # Nested object
    │                   │   └── InventoryItem.java      # Nested object in list
    │                   ├── pattern/
    │                   │   ├── Prototype.java            # Prototype interface
    │                   │   ├── ERPProductPrototype.java  # Wrapper for ERPProduct
    │                   │   └── PrototypeRegistry.java    # Registry management
    │                   └── util/
    │                       └── DeepCopyUtil.java        # Deep copy utility
    └── test/
        └── java/
            └── com/
                └── example/
                    └── prototype/
                        ├── pattern/
                        │   ├── ERPProductPrototypeTest.java  # Unit tests
                        │   └── PrototypeRegistryTest.java    # Unit tests
                        └── integration/
                            └── PrototypePatternIntegrationTest.java  # Integration tests
```

## Key Components

### 1. ERPProduct Entity
- **Location**: [`src/main/java/com/example/prototype/entity/ERPProduct.java`](src/main/java/com/example/prototype/entity/ERPProduct.java)
- **Purpose**: Represents a complex product entity in an ERP system
- **Characteristics**: 
  - Large number of fields (30+ properties)
  - Contains nested objects (ProductCategory, Supplier)
  - Contains collections (List<InventoryItem>)
  - Contains maps (Map<String, Object>)
  - **Cannot be modified** (constraint)
  - Implements Serializable for deep copying

### 2. Prototype Interface
- **Location**: [`src/main/java/com/example/prototype/pattern/Prototype.java`](src/main/java/com/example/prototype/pattern/Prototype.java)
- **Purpose**: Defines the contract for cloning objects
- **Method**: `T clone()` - Creates and returns a copy of the prototype

### 3. ERPProductPrototype Wrapper
- **Location**: [`src/main/java/com/example/prototype/pattern/ERPProductPrototype.java`](src/main/java/com/example/prototype/pattern/ERPProductPrototype.java)
- **Purpose**: Wraps ERPProduct and implements Prototype interface
- **Key Features**:
  - Wraps ERPProduct without modifying it
  - Implements [`clone()`](src/main/java/com/example/prototype/pattern/ERPProductPrototype.java:30) method for deep copying
  - Uses [`DeepCopyUtil`](src/main/java/com/example/prototype/util/DeepCopyUtil.java) for actual copying
  - Provides [`shallowClone()`](src/main/java/com/example/prototype/pattern/ERPProductPrototype.java:57) for performance-critical scenarios

### 4. DeepCopyUtil
- **Location**: [`src/main/java/com/example/prototype/util/DeepCopyUtil.java`](src/main/java/com/example/prototype/util/DeepCopyUtil.java)
- **Purpose**: Handles the deep copy logic
- **Approaches**:
  1. **Serialization-based** (primary): Uses Java serialization/deserialization
  2. **Reflection-based** (fallback): Alternative approach (placeholder for future)
- **Benefits**:
  - Works with any Serializable object
  - Automatically handles complex object graphs
  - No modification to source classes required

### 5. PrototypeRegistry
- **Location**: [`src/main/java/com/example/prototype/pattern/PrototypeRegistry.java`](src/main/java/com/example/prototype/pattern/PrototypeRegistry.java)
- **Purpose**: Manages a registry of prototype instances
- **Features**:
  - Register prototypes with unique keys
  - Retrieve cloned copies by key
  - Thread-safe operations
  - Manage prototype lifecycle


## Benefits

1. **No Modification Required**: The original [`ERPProduct`](src/main/java/com/example/prototype/entity/ERPProduct.java) class remains unchanged
2. **Deep Copy Guarantee**: Nested objects, collections, and maps are properly copied
3. **Type Safety**: Generic [`Prototype<T>`](src/main/java/com/example/prototype/pattern/Prototype.java) interface ensures type safety
4. **Reusability**: [`PrototypeRegistry`](src/main/java/com/example/prototype/pattern/PrototypeRegistry.java) allows managing multiple templates
5. **Performance**: Cloning is faster than creating objects from scratch
6. **Flexibility**: Can switch between serialization and reflection approaches
7. **Independence**: Original and clone are completely independent
8. **Thread Safety**: Registry operations are synchronized for concurrent access


## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the Project

```bash
mvn clean install
```

### Run Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ERPProductPrototypeTest

# Run specific test method
mvn test -Dtest=ERPProductPrototypeTest#testCloneCreatesNewInstance
```

### Generate Coverage Report

```bash
mvn clean test jacoco:report
```


## Implementation Notes

### Deep Copy Strategy

The implementation uses **serialization-based deep copying** as the primary approach:

1. Serialize the object to a byte array
2. Deserialize the byte array to create a new object
3. This creates a completely independent copy

**Advantages**:
- Works with any Serializable object
- Automatically handles complex object graphs
- Minimal code required

**Disadvantages**:
- Performance overhead of serialization
- All objects in the graph must be Serializable

### Thread Safety

The [`PrototypeRegistry`](src/main/java/com/example/prototype/pattern/PrototypeRegistry.java) is thread-safe:
- All public methods are `synchronized`
- Prevents race conditions in concurrent access
- Ensures consistent state

### Serialization

All entity classes implement `Serializable`:
- [`ERPProduct`](src/main/java/com/example/prototype/entity/ERPProduct.java:23)
- [`ProductCategory`](src/main/java/com/example/prototype/entity/ProductCategory.java:11)
- [`Supplier`](src/main/java/com/example/prototype/entity/Supplier.java:11)
- [`InventoryItem`](src/main/java/com/example/prototype/entity/InventoryItem.java:11)

This enables the serialization-based deep copy mechanism.

## Future Enhancements

Potential improvements to consider:

1. **Performance Optimization**: Implement reflection-based deep copy for better performance
2. **Lazy Initialization**: Implement lazy loading for large objects
3. **Custom Serialization**: Implement custom serialization for better control
4. **Immutable Objects**: Make ERPProduct truly immutable for thread safety
5. **Cache Management**: Add LRU cache for frequently used prototypes
6. **Prototype Builders**: Add builder pattern for creating complex prototypes
7. **Validation**: Add validation logic for prototype registration

