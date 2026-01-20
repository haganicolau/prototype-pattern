package com.example.prototype.pattern;

/**
 * Prototype interface - Defines the contract for cloning objects.
 * This is the core of the Prototype design pattern (GoF).
 * 
 * @param <T> The type of object to clone
 */
public interface Prototype<T> {
    
    /**
     * Creates and returns a copy of this prototype.
     * The returned object should be a deep copy to ensure
     * independence from the original.
     * 
     * @return A new instance that is a copy of this prototype
     */
    T clone();
}
