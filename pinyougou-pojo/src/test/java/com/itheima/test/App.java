package com.itheima.test;

public class App {
    public static void main(String[] args) {

        Student p1 = new Student(1, "李");
        Student p2 = new Student(1, "李");
        System.out.println(p1);
        System.out.println(p1.hashCode());
        System.out.println(p2);
        System.out.println(p2.hashCode());
    }
}
