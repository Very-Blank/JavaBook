package data;
import java.util.ArrayList;

public class SparseSet<T extends Data<T>>{
    private Vector<Integer> sparse;
    private ArrayList<T> dense;

    public SparseSet(){
        this.sparse = new Vector<Integer>();
        this.dense = new ArrayList<T>();
    }

    public T get(int ID) throws DataException {
        if(0 <= ID && ID < sparse.size()){
            int denseID = sparse.get(ID);
            if(0 <= denseID && denseID < this.dense.size()){
                return dense.get(denseID);
            } else{
                throw new DataException("ID didn't exist");
            }
        } else{
            throw new DataException("ID didn't exist");
        }
    }

    public ArrayList<T> getDense(){
        return dense;
    }

    public int setValueAt(T value){
        T copy = value.copy();
        int ID = value.getID();
        if(0 <= ID && ID < this.sparse.size()){
            int denseID = this.sparse.get(ID);

            if(denseID != -1){
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

    public void removeValueAt(T value) throws DataException {
        if(0 <= value.getID() && value.getID() < sparse.size()){
            int denseID = sparse.get(value.getID());

            if(denseID != -1){
                sparse.set(value.getID(), -1);
                dense.remove(denseID);

                //All values have shifted back one!
                //So we adjust them
                for(int i = denseID; i < dense.size(); i++){
                    sparse.set(dense.get(i).getID(), i);
                }
            } else{
                throw new DataException("ID didn't exist");
            }
        } else{
            throw new DataException("ID didn't exist");
        }
    }
}

