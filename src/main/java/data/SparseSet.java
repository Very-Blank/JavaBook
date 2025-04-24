package data;
import java.util.ArrayList;

/**
 * Sparse set implementation for efficient management of objects with integer IDs.
 * Maintains two parallel structures: sparse array for ID mapping and dense array for storage.
 * 
 * @author aapeli.saarelainen.76@gmail.com
 * @param <T> type of managed objects, must extend Data<T>
 */
public class SparseSet<T extends Data<T>> {
    private Vector<Integer> sparse;
    private ArrayList<T> dense;

    /** Initializes empty sparse set with default vector and array list */
    public SparseSet() {
        this.sparse = new Vector<Integer>();
        this.dense = new ArrayList<T>();
    }

    /**
     * Retrieves object by its ID using sparse-dense mapping.
     * @param ID object identifier to look up
     * @return requested object
     * @throws DataException if ID is invalid or doesn't exist
     * <pre name="test">
     *   SparseSet<User> set = new SparseSet<>();
     *   User user = new User(0, "Test", "test@example.com", "+358", "0401234567", new ArrayList<>(), LocalDate.now());
     *   int id = set.setValueAt(user);
     *   set.get(id).getID() === 0;
     *   boolean thrown = false;
     *   try { set.get(999); } 
     *   catch (DataException e) { thrown = true; }
     *   thrown === true;
     * </pre>
     */
    public T get(int ID) throws DataException {
        if (0 <= ID && ID < sparse.size()) {
            int denseID = sparse.get(ID);
            if (0 <= denseID && denseID < this.dense.size()) {
                return dense.get(denseID);
            } else {
                throw new DataException("ID didn't exist");
            }
        } else {
            throw new DataException("ID didn't exist");
        }
    }

    /** @return reference to dense array containing actual objects */
    public ArrayList<T> getDense() {
        return dense;
    }

    /**
     * Adds or updates object in the set. Generates new ID if needed.
     * @param value object to add/update
     * @return ID assigned to the object
     * <pre name="test">
     *   SparseSet<User> set = new SparseSet<>();
     *   User user1 = new User(-1, "A", "a@test", "+358", "0401111111", new ArrayList<>(), LocalDate.now());
     *   int id1 = set.setValueAt(user1);
     *   id1 === 0;
     *   User user2 = new User(-1, "B", "b@test", "+358", "0402222222", new ArrayList<>(), LocalDate.now());
     *   int id2 = set.setValueAt(user2);
     *   id2 === 1;
     *   set.dense.size() === 2;
     *   user1.setID(0);
     *   user1.setName("Updated");
     *   set.setValueAt(user1);
     *   set.dense.get(0).getName() === "Updated";
     * </pre>
     */
    public int setValueAt(T value) {
        T copy = value.copy();
        int ID = value.getID();
        if (0 <= ID && ID < this.sparse.size()) {
            int denseID = this.sparse.get(ID);

            if (denseID != -1) {
                copy.setID(ID);
                this.dense.set(denseID, copy);
                return ID;
            }
        }

        copy.setID(this.sparse.size());
        this.dense.add(copy);
        this.sparse.add(this.dense.size() - 1);

        return this.sparse.size() - 1;
    }

    /**
     * Removes object from the set and maintains structure consistency.
     * @param ID identifier of object to remove
     * @throws DataException if ID is invalid or doesn't exist
     * <pre name="test">
     *   SparseSet<User> set = new SparseSet<>();
     *   User user = new User(-1, "Test", "test@example.com", "+358", "0401234567", new ArrayList<>(), LocalDate.now());
     *   int id = set.setValueAt(user);
     *   set.removeValueAt(id);
     *   set.dense.size() === 0;
     *   boolean thrown = false;
     *   try { set.removeValueAt(id); }
     *   catch (DataException e) { thrown = true; }
     *   thrown === true;
     * </pre>
     */
    public void removeValueAt(int ID) throws DataException {
        if (0 <= ID && ID < sparse.size()) {
            int denseID = sparse.get(ID);

            if (denseID != -1) {
                sparse.set(ID, -1);
                dense.remove(denseID);

                for (int i = denseID; i < dense.size(); i++) {
                    sparse.set(dense.get(i).getID(), i);
                }
            } else {
                throw new DataException("ID didn't exist");
            }
        } else {
            throw new DataException("ID didn't exist");
        }
    }
}
