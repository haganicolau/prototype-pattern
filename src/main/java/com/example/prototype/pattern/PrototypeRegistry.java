package com.example.prototype.pattern;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * PrototypeRegistry - Manages a registry of prototype instances.
 * This class allows you to register prototype objects by key and retrieve
 * cloned copies of them. This is useful for managing pre-configured
 * product templates in an ERP system.
 * 
 * The registry is thread-safe for concurrent access.
 */
public class PrototypeRegistry {
    
    private final Map<String, Prototype<?>> registry;

    /**
     * Creates a new empty prototype registry.
     */
    public PrototypeRegistry() {
        this.registry = new HashMap<>();
    }

    /**
     * Registers a prototype with a given key.
     * 
     * @param key The key to associate with the prototype
     * @param prototype The prototype to register
     * @throws IllegalArgumentException if key or prototype is null
     * @throws IllegalStateException if a prototype with the same key already exists
     */
    public synchronized void registerPrototype(String key, Prototype<?> prototype) {
        Objects.requireNonNull(key, "Key cannot be null");
        Objects.requireNonNull(prototype, "Prototype cannot be null");
        
        if (registry.containsKey(key)) {
            throw new IllegalStateException(
                "Prototype with key '" + key + "' already exists in registry"
            );
        }
        
        registry.put(key, prototype);
    }

    /**
     * Retrieves a prototype by key.
     * This method returns the registered prototype instance.
     *
     * @param key The key of the prototype to retrieve
     * @return The registered prototype
     * @throws IllegalArgumentException if key is null
     * @throws IllegalStateException if no prototype is registered with the given key
     */
    public synchronized Prototype<?> getPrototype(String key) {
        Objects.requireNonNull(key, "Key cannot be null");
        
        Prototype<?> prototype = registry.get(key);
        if (prototype == null) {
            throw new IllegalStateException(
                "No prototype registered with key '" + key + "'"
            );
        }
        
        return prototype;
    }

    /**
     * Removes a prototype from the registry.
     * 
     * @param key The key of the prototype to remove
     * @throws IllegalArgumentException if key is null
     */
    public synchronized void removePrototype(String key) {
        Objects.requireNonNull(key, "Key cannot be null");
        registry.remove(key);
    }

    /**
     * Checks if a prototype is registered with the given key.
     * 
     * @param key The key to check
     * @return true if a prototype is registered with the key, false otherwise
     * @throws IllegalArgumentException if key is null
     */
    public synchronized boolean hasPrototype(String key) {
        Objects.requireNonNull(key, "Key cannot be null");
        return registry.containsKey(key);
    }

    /**
     * Returns the number of prototypes registered in the registry.
     * 
     * @return The number of registered prototypes
     */
    public synchronized int size() {
        return registry.size();
    }

    /**
     * Clears all prototypes from the registry.
     */
    public synchronized void clear() {
        registry.clear();
    }

    /**
     * Returns all registered keys.
     * 
     * @return A set of all registered keys
     */
    public synchronized java.util.Set<String> getKeys() {
        return new java.util.HashSet<>(registry.keySet());
    }
}
