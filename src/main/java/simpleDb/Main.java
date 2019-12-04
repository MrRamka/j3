package simpleDb;

import simpleDb.main.DataReader;

import java.io.*;
import java.util.*;

import static simpleDb.main.DataReader.*;

public class Main {
    private static final String DATA_PATH = "D:\\MAVEN_PROJECTS\\j3\\src\\main\\java\\simpleDb\\data.csv";
    private static final String INDEX_NUMBERS_PATH = "D:\\MAVEN_PROJECTS\\j3\\src\\main\\java\\simpleDb\\index_numbers.txt";

    public static void main(String[] args) {

        DataReader dataReader;
        try {
            dataReader = new DataReader(DATA_PATH);
            for (List l : dataReader.getUsersByRole(USER_ROLE))
                System.out.println(l);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
