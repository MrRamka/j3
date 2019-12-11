package simpleDb.main;

import simpleDb.entities.DataInt;
import simpleDb.entities.DataString;
import simpleDb.tree.RedBlackTree;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DataReader {

    private RedBlackTree<DataString> nameTree = new RedBlackTree<>();
    private RedBlackTree<DataString> surnameTree = new RedBlackTree<>();
    private RedBlackTree<DataInt> ageTree = new RedBlackTree<>();
    private RedBlackTree<DataString> roleTree = new RedBlackTree<>();
    private RedBlackTree<DataInt> genderTree = new RedBlackTree<>();
    public final static String USER_ROLE = "User";
    public final static String ADMIN_ROLE = "Admin";
    public final static int MALE = 1;
    public final static int FEMALE = 0;
    private static String DATA_PATH;
    private static String CSV_LINE_SEPARATOR = ",";
    private static String LINE_BREAK = "\n";
    private final int LINE_BREAK_SIZE = LINE_BREAK.length();
    private List<Integer> indexList = new ArrayList<>();
    private BufferedReader bufferedReader;

    public DataReader(String dataPath) throws IOException {
        this.DATA_PATH = dataPath;
        FileInputStream fileInputStream = new FileInputStream(new File(dataPath));
        bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
//        initialRead();

    }

    private void initialRead() throws IOException {
        String inputLine;
        int last_index = 0;
        while ((inputLine = bufferedReader.readLine()) != null) {
            String[] data = inputLine.split(",");
            nameTree.insert(new DataString(last_index, data[0]));
            surnameTree.insert(new DataString(last_index, data[1]));
            ageTree.insert(new DataInt(last_index, Integer.parseInt(data[2])));
            roleTree.insert(new DataString(last_index, data[3]));
            genderTree.insert(new DataInt(last_index, Integer.parseInt(data[4])));
            indexList.add(last_index);
            last_index += inputLine.length() + LINE_BREAK_SIZE;
        }
    }

    public List<Integer> getIndexList() {
        return indexList;
    }

    public List<List<String>> getUsersByRole(String role) throws IOException {
        if (role.equals(USER_ROLE)) {
            return getUsersIndexDataString(roleTree.getGreaterThan(new DataString(1, ADMIN_ROLE), roleTree.size()));
        } else if (role.equals(ADMIN_ROLE)) {
            return getUsersBesidesIndexDataString(roleTree.getGreaterThan(new DataString(1, ADMIN_ROLE), roleTree.size()));
        }
        throw new IllegalArgumentException("Unknown user Role. Use class constants");
    }

    public List<List<String>> getUsersByGender(int gender) throws IOException {
        if (gender == MALE) {
            return getUsersIndexDataInt(genderTree.getGreaterThan(new DataInt(1, 0), roleTree.size()));
        } else if (gender == FEMALE) {
            return getUsersBesidesIndexDataInt(genderTree.getGreaterThan(new DataInt(1, 0), roleTree.size()));
        }
        throw new IllegalArgumentException("Unknown user Gender. Use class constants");
    }

    public List<List<String>> getUsersGreaterThan(int age) throws IOException {
        return getUsersIndexDataInt(ageTree.getGreaterThan(new DataInt(1, age), roleTree.size()));
    }

    public List<List<String>> getUsersLessThan(int age) throws IOException {
        return getUsersBesidesIndexDataInt(ageTree.getGreaterThan(new DataInt(1, age), roleTree.size()));
    }

    public List<List<String>> getUsersGreaterThanName(String name) throws IOException {
        return getUsersBesidesIndexDataString(nameTree.getGreaterThan(new DataString(1, name), roleTree.size()));
    }

    public List<List<String>> getUsersLessThanName(String name) throws IOException {
        return getUsersBesidesIndexDataString(nameTree.getGreaterThan(new DataString(1, name), roleTree.size()));
    }

    private List<List<String>> getUsersBesidesIndexDataString(List<DataString> dataStrings) throws IOException {
        List<Integer> indexListGreaterThan = new ArrayList<>();
        for (DataString dataInt : dataStrings) {
            indexListGreaterThan.add(dataInt.getIndex());
        }
        List<Integer> indexListLessThan = new ArrayList<>();
        for (Integer integer : indexList) {
            if (!indexListGreaterThan.contains(integer))
                indexListLessThan.add(integer);
        }
        return getUsersByIndex(indexListLessThan);
    }

    private List<List<String>> getUsersBesidesIndexDataInt(List<DataInt> dataInts) throws IOException {
        List<Integer> indexListGreaterThan = new ArrayList<>();
        for (DataInt dataInt : dataInts) {
            indexListGreaterThan.add(dataInt.getIndex());
        }
        List<Integer> indexListLessThan = new ArrayList<>();
        for (Integer integer : indexList) {
            if (!indexListGreaterThan.contains(integer))
                indexListLessThan.add(integer);
        }
        return getUsersByIndex(indexListLessThan);
    }

    private List<List<String>> getUsersIndexDataString(List<DataString> dataStrings) throws IOException {
        List<Integer> indexListGreaterThan = new ArrayList<>();
        for (DataString dataString : dataStrings) {
            indexListGreaterThan.add(dataString.getIndex());
        }
        return getUsersByIndex(indexListGreaterThan);
    }

    private List<List<String>> getUsersIndexDataInt(List<DataInt> dataInts) throws IOException {
        List<Integer> indexListGreaterThan = new ArrayList<>();
        for (DataInt dataInt : dataInts) {
            indexListGreaterThan.add(dataInt.getIndex());
        }
        return getUsersByIndex(indexListGreaterThan);
    }

    private List<List<String>> getUsersByIndex(List<Integer> index) throws IOException {
        List<List<String>> usersList = new ArrayList<>();
        index.sort(Comparator.comparingInt(o -> o));
        FileInputStream fileInputStream = new FileInputStream(new File(DATA_PATH));
        bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        int cursor = 0;
        String inputLine;
        for (int anIndex : index) {
            bufferedReader.skip(anIndex - cursor);
            inputLine = bufferedReader.readLine();
            cursor += anIndex - cursor + inputLine.length() + LINE_BREAK_SIZE;
            String[] data = inputLine.split(CSV_LINE_SEPARATOR);
            usersList.add(new ArrayList<>(Arrays.asList(data)));
        }
        return usersList;
    }

    private List<String> getUserByIndex(int index) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(DATA_PATH));
        bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        bufferedReader.skip(index);
        String[] data = bufferedReader.readLine().split(CSV_LINE_SEPARATOR);
        return new ArrayList<>(Arrays.asList(data));
    }

    public static void setCsvLineSeparator(String csvLineSeparator) {
        CSV_LINE_SEPARATOR = csvLineSeparator;
    }

    public static void setLineBreak(String lineBreak) {
        LINE_BREAK = lineBreak;
    }
}
