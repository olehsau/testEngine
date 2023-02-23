package uj.wmii.pwj.w08;

import java.awt.*;

public class MyBeautifulMethodsToTest {

    public static String invertString(String str){
        String result="";
        for (char c : str.toCharArray()){
            result = c + result;
        }
        return result;
    }

    public static int calculate(int a, int b, Operations operation){
        if (operation == Operations.ADD) return a+b;
        if (operation == Operations.SUBTRACT) return a-b;
        if (operation == Operations.MULTIPLY) return a*b;
        if (operation == Operations.DIVIDE) return a/b;
        else return 0;
    }

}
