package data;

/**
 * A generic dynamic array implementation similar to Java's Vector.
 * Provides basic operations for adding, accessing, and removing elements.
 * Automatically resizes when the capacity is exceeded.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 * @param <T> the type of elements stored in this vector
 */
public class Vector<T extends Object> {
    private int len;
    private Object[] array;

    /**
     * Constructs an empty vector with an initial capacity of 8.
     * <pre name="test">
     *   Vector<Integer> vec = new Vector<>();
     *   vec.size() === 0;
     *   for (int i = 0; i < 8; i++) {
     *     vec.add(i);
     *   }
     *   vec.size() === 8;
     *   vec.add(8); // Should trigger resize
     *   vec.size() === 9;
     * </pre>
     */
    public Vector() {
        this.len = 0;
        this.array = new Object[8];
    }

    /**
     * Constructs an empty vector with the specified initial capacity.
     * 
     * @param capacity the initial capacity of the vector
     * @throws RuntimeException if capacity is less than or equal to zero
     * <pre name="test">
     *   Vector<Integer> vec = new Vector<>(5);
     *   vec.size() === 0;
     *   boolean thrown = false;
     *   try {
     *     new Vector<Integer>(0);
     *   } catch (RuntimeException e) {
     *     thrown = true;
     *   }
     *   thrown === true;
     *   thrown = false;
     *   try {
     *     new Vector<Integer>(-3);
     *   } catch (RuntimeException e) {
     *     thrown = true;
     *   }
     *   thrown === true;
     * </pre>
     */
    public Vector(int capacity) {
        if (capacity <= 0) {
            throw new RuntimeException("Capacity can't be less than or equal to zero");
        }

        this.len = 0;
        this.array = new Object[capacity];
    }

    /**
     * Appends the specified element to the end of the vector. If the vector's
     * capacity is exceeded, the internal array is doubled in size.
     * 
     * @param value the element to be added
     * <pre name="test">
     *   Vector<Integer> vec = new Vector<>(2);
     *   vec.add(1);
     *   vec.add(2);
     *   vec.size() === 2;
     *   vec.add(3); // Trigger resize
     *   vec.size() === 3;
     *   vec.get(0) === 1;
     *   vec.get(1) === 2;
     *   vec.get(2) === 3;
     * </pre>
     */
    public void add(T value) {
        if (len + 1 < array.length) {
            array[len] = (Object) value;
            len += 1;
        } else {
            Object[] newArray = new Object[this.array.length * 2];
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
            }

            array = newArray;
            array[len] = (Object) value;
            len += 1;
        }
    }

    /**
     * Returns the element at the specified position in this vector.
     * 
     * @param i index of the element to return
     * @return the element at the specified position
     * @throws RuntimeException if the index is out of bounds
     * <pre name="test">
     *   Vector<String> vec = new Vector<>();
     *   vec.add("a");
     *   vec.add("b");
     *   vec.get(0) === "a";
     *   vec.get(1) === "b";
     *   boolean thrown = false;
     *   try {
     *     vec.get(2);
     *   } catch (RuntimeException e) {
     *     thrown = true;
     *   }
     *   thrown === true;
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public T get(int i) {
        if (0 <= i && i < len) {
            return (T) array[i];
        } else {
            throw new RuntimeException("Tried to access outside the array");
        }
    }

    /**
     * Replaces the element at the specified position in this vector with the specified element.
     * 
     * @param i index of the element to replace
     * @param value new element to be stored at the specified position
     * @throws RuntimeException if the index is out of bounds
     * <pre name="test">
     *   Vector<Integer> vec = new Vector<>();
     *   vec.add(1);
     *   vec.set(0, 10);
     *   vec.get(0) === 10;
     *   boolean thrown = false;
     *   try {
     *     vec.set(1, 20);
     *   } catch (RuntimeException e) {
     *     thrown = true;
     *   }
     *   thrown === true;
     * </pre>
     */
    public void set(int i, T value) {
        if (0 <= i && i < len) {
            array[i] = (Object) value;
        } else {
            throw new RuntimeException("Tried to access outside the array");
        }
    }

    /**
     * Removes the element at the specified position in this vector. Shifts any subsequent
     * elements to the left (subtracts one from their indices).
     * 
     * @param i index of the element to be removed
     * @throws RuntimeException if the index is out of bounds
     * <pre name="test">
     *   Vector<Integer> vec = new Vector<>();
     *   vec.add(1);
     *   vec.add(2);
     *   vec.add(3);
     *   vec.remove(1);
     *   vec.size() === 2;
     *   vec.get(0) === 1;
     *   vec.get(1) === 3;
     *   boolean thrown = false;
     *   try {
     *     vec.remove(2);
     *   } catch (RuntimeException e) {
     *     thrown = true;
     *   }
     *   thrown === true;
     * </pre>
     */
    public void remove(int i) {
        if (0 <= i && i < len) {
            len -= 1;

            for (int j = i; j < len; j++) {
                array[j] = array[j + 1];
            }
        } else {
            throw new RuntimeException("Tried to remove outside the array");
        }
    }

    /**
     * Returns the number of elements in this vector.
     * 
     * @return the number of elements
     * <pre name="test">
     *   Vector<Integer> vec = new Vector<>();
     *   vec.size() === 0;
     *   vec.add(5);
     *   vec.size() === 1;
     *   vec.remove(0);
     *   vec.size() === 0;
     * </pre>
     */
    public int size() {
        return len;
    }
}
