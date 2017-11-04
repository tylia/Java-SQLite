package com.sqlite;

public class Main {
    public static void main(String[] args) {
        Data data = new Data();
        if (!data.open()) {
            System.out.println("Can't open data");
            return;
        }
//  Test your code here

        data.close();
    }
}
