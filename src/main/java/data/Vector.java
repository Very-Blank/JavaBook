package data;

// Horrible code but this is how java does it.
public class Vector<T extends Object> {
    private int len;
    private Object[] array;

    public Vector(){
        this.len = 0;
        this.array = new Object[8];
    }

    public Vector(int capacity){
        if(capacity <= 0){
            throw new RuntimeException("Capacity can't be less than or equal to zero");
        }

        this.len = 0;
        this.array = new Object[capacity];
    }

    public void add(T value){
        if (len + 1 < array.length) {
            array[len] = (Object)value;
            len += 1;
        } else{
            Object[] newArray = new Object[this.array.length * 2];
            for (int i = 0; i < array.length; i++) {
                newArray[i] = array[i];
            }

            array = newArray;
            array[len] = (Object)value;
            len += 1;
        }
    }

    @SuppressWarnings("unchecked")
    public T get(int i){
        if (0 <= i && i < len) {
            return (T)array[i];
        } else{
            throw new RuntimeException("Tried to access outside the array");
        }
    }

    public void set(int i, T value){
        if (0 <= i && i < len) {
            array[i] = (Object)value;
        } else{
            throw new RuntimeException("Tried to access outside the array");
        }
    }

    public void remove(int i){
        if (0 <= i && i < len) {
            len -= 1;

            for (int j = i; j < len; j++) {
                array[j] = array[j + 1];
            }
        } else{
            throw new RuntimeException("Tried to remove outside the array");
        }
    }

    public int size(){
        return len;
    }
}
