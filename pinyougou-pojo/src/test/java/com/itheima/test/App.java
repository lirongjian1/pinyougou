package com.itheima.test;

import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {

        Student p1 = new Student(1, "李");
        Student p2 = new Student(1, "李");
        System.out.println(p1);
        System.out.println(p1.hashCode());
        System.out.println(p2);
        System.out.println(p2.hashCode());

        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        System.out.println(map);
    }
}
