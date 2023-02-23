package uj.wmii.pwj.w08;

public class MyBeautifulTestSuite {

    @MyTestInvert(input = "Hello",expectedResult = "olleH")
    public static String invertWordHello(String inputString){
        return MyBeautifulMethodsToTest.invertString(inputString);
    }

    @MyTestInvert(input = "a", expectedResult = "a")
    public static String invertWordA(String inputString){
        return MyBeautifulMethodsToTest.invertString(inputString);
    }

    @MyTestInvert(input = "I love cats and dogs",expectedResult = "sgod dna stac evol I")
    public static String invertSentence(String inputString){
        return MyBeautifulMethodsToTest.invertString(inputString);
    }

    @MyTestInvert(input = "Hello",expectedResult = "olleH")
    public static String invertWordMistake(String inputString){
        return "hamburger";
    }

    public static String methodWithoutAnnotation(String inputString){
        throw new NullPointerException();
    }

    @MyTestInvert(input = "Cabbage",expectedResult = "egabbaC")
    public static String invertWordCabbage(String inputString){
        throw new NumberFormatException();
    }

    ///////////////////////////////////////////////////////////////////////


    @MyTestCalculator(a=1, b=1, operation=Operations.ADD, expectedResult=2)
    public static int add1And1(int a, int b, Operations operation){
        return MyBeautifulMethodsToTest.calculate(a,b,operation);
    }

    @MyTestCalculator(a=1, b=1, operation=Operations.SUBTRACT, expectedResult=0)
    public static int subtract1And1(int a, int b, Operations operation){
        return MyBeautifulMethodsToTest.calculate(a,b,operation);
    }

    @MyTestCalculator(a=10, b=15, operation=Operations.MULTIPLY, expectedResult=150)
    public static int multiply10And15(int a, int b, Operations operation){
        return MyBeautifulMethodsToTest.calculate(a,b,operation);
    }

    @MyTestCalculator(a=1, b=0, operation=Operations.DIVIDE, expectedResult=0)
    public static int divide1By0(int a, int b, Operations operation){
        return MyBeautifulMethodsToTest.calculate(a,b,operation);
    }

}
