package simpleDb.entities;

public class DataString implements Comparable<DataString>{

    private int index;
    private String data;

    public DataString(int index, String data) {
        this.index = index;
        this.data = data;
    }

    public int getIndex() {
        return index;
    }

    public String getData() {
        return data;
    }

    @Override
    public int compareTo(DataString o) {
        return data.compareTo(o.getData());
    }

    @Override
    public String toString() {
        return "DataString{" +
                "index=" + index +
                ", data='" + data + '\'' +
                '}';
    }
}
