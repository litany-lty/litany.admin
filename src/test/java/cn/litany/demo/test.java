package cn.litany.demo;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        String fname="C:\\textfiles\\db\\query\\query.txt";
        String[] items= fname.split("\\\\");
        System.out.println(Arrays.toString(items));
    }
}
