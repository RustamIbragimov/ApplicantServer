package model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ribra on 11/11/2015.
 */
public class Statistics {
    private static List<Person> list = new LinkedList<Person>();
    private static Statistics instance = new Statistics();

    private Statistics() {
        build();
    }

    public static Statistics getInstance() {
        return instance;
    }

    public void build() {
        list = JDBCDriver.getInstance().getAll();
    }

    public int getAmount(String place) {
        int n = 0;
        for (Person x : list) {
            if (x.getPlace().contains(place)) {
                n++;
            }
        }
        return n;
    }

}
