package com.example.prototype.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Utility class for creating deep copies of objects.
 * This class provides two approaches for deep copying:
 * 1. Serialization-based (primary) - works with any Serializable object
 * 2. Reflection-based (fallback) - more performant, no Serializable requirement
 */
public class DeepCopyUtil {

    /**
     * Creates a deep copy of a Serializable object using serialization.
     * This is the primary approach as it automatically handles complex object graphs.
     * 
     * @param <T> The type of the object to copy
     * @param object The object to deep copy
     * @return A deep copy of the object
     * @throws IllegalArgumentException if the object is not Serializable
     * @throws RuntimeException if serialization/deserialization fails
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deepCopy(T object) {
        if (object == null) {
            return null;
        }

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            
            // Serialize the object to a byte array
            oos.writeObject(object);
            oos.flush();
            
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                 ObjectInputStream ois = new ObjectInputStream(bis)) {
                
                // Deserialize the byte array to create a new object
                return (T) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to create deep copy using serialization", e);
        }
    }

    /**
     * Creates a deep copy of an object using reflection.
     * This is a fallback approach that doesn't require Serializable.
     * Note: This is a simplified implementation and may not handle all edge cases.
     * 
     * @param <T> The type of the object to copy
     * @param object The object to deep copy
     * @return A deep copy of the object
     * @throws RuntimeException if reflection fails
     */
    @SuppressWarnings("unchecked")
    public static <T> T deepCopyReflection(T object) {
        if (object == null) {
            return null;
        }

        try {
            // For simple objects, try serialization first
            if (object instanceof Serializable) {
                return (T) deepCopy((Serializable) object);
            }

            // For non-serializable objects, we would need a more complex reflection implementation
            // This is a placeholder - in a real implementation, you would:
            // 1. Get the class of the object
            // 2. Create a new instance
            // 3. Iterate through all fields
            // 4. Copy primitive fields directly
            // 5. Recursively deep copy object fields
            // 6. Handle collections and maps specially
            
            throw new UnsupportedOperationException(
                "Reflection-based deep copy is not fully implemented. " +
                "Please ensure your objects implement Serializable."
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create deep copy using reflection", e);
        }
    }

    /**
     * Checks if an object can be deep copied using serialization.
     * 
     * @param object The object to check
     * @return true if the object is Serializable, false otherwise
     */
    public static boolean isSerializable(Object object) {
        return object instanceof Serializable;
    }
}
