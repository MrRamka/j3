package simpleDb.entities;

public class DataInt implements Comparable<DataInt>{

    private int index;
    private int data;

    public DataInt(int index, int data) {
        this.index = index;
        this.data = data;
    }

    public int getIndex() {
        return index;
    }

    public int getData() {
        return data;
    }

    @Override
    public int compareTo(DataInt o) {
        return data - o.getData();
    }
}
