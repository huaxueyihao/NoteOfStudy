package com.book.study.chapter02;

import javax.swing.*;
import java.sql.SQLOutput;
import java.util.function.BinaryOperator;

public class MainClass {
    public static void main(String[] args) {

        BinaryOperator<Long> add = (x, y) -> x + y;


        Runnable helloWorld = () -> System.out.println("hello");

        JButton button = new JButton();
        button.addActionListener(evet-> System.out.println(evet.getActionCommand()));


        int[] a = {};

        for (int i : a) {

        }



    }
}
