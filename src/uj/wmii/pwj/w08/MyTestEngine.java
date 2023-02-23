package uj.wmii.pwj.w08;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyTestEngine {

    private final String className;

    public static void main(String[] args) {
        drawBanner();
        if (args.length < 1) {
            System.out.println("Please specify test class name");
            System.exit(-1);
        }
        printTestsInfo();
        String className = args[0].trim();
        System.out.printf("Testing class: %s\n", className);
        MyTestEngine engine = new MyTestEngine(className);
        engine.runTests();
    }

    public MyTestEngine(String className) {
        this.className = className;
    }

    public void runTests() {
        final Object unit = getObject(className);
        List<Method> testMethods = getTestMethods(unit);
        int successCount = 0;
        int failCount = 0;
        int errorCount = 0;
        for (Method m: testMethods) {
            TestResult2 result = launchSingleMethod(m, unit);
            if (result == TestResult2.PASS) successCount++;
            else if(result==TestResult2.FAIL) failCount++;
            else errorCount++;
        }
        System.out.printf("Engine launched %d tests.\n", testMethods.size());
        System.out.printf("%d of them passed, %d failed, %d threw error.\n", successCount, failCount, errorCount);
    }

    private TestResult2 launchSingleMethod(Method m, Object unit) {
        try {
            if(m.getAnnotation(MyTestInvert.class) != null) {
                String inputString = m.getAnnotation(MyTestInvert.class).input();
                String expectedString = m.getAnnotation(MyTestInvert.class).expectedResult();
                String resultString = (String) m.invoke(unit, inputString);
                if (resultString.equals(expectedString)) {
                    System.out.println("Tested method: " + m.getName() + " test successful.");
                    return TestResult2.PASS;
                } else {
                    System.out.println("Tested method: " + m.getName() + " test fail.");
                    System.out.println("Expected value: " + expectedString);
                    System.out.println("Result value: " + resultString);
                    return TestResult2.FAIL;
                }
            }
            else{ // MyTestCalculator
                int expectedResult = m.getAnnotation(MyTestCalculator.class).expectedResult();
                int result = (int) m.invoke(unit,m.getAnnotation(MyTestCalculator.class).a(),
                        m.getAnnotation(MyTestCalculator.class).b(), m.getAnnotation(MyTestCalculator.class).operation());
                if(result==expectedResult){
                    System.out.println("Tested method: " + m.getName() + " test successful.");
                    return TestResult2.PASS;
                }
                else{
                    System.out.println("Tested method: " + m.getName() + " test fail.");
                    System.out.println("Expected value: " + expectedResult);
                    System.out.println("Result value: " + result);
                    return TestResult2.FAIL;
                }
            }
        } catch (ReflectiveOperationException e) {
            System.out.println("Tested method: " + m.getName() + " - ERROR");
            System.out.println("Read details in stacktrace.");
            e.printStackTrace();
            return TestResult2.ERROR;
        }
    }

    private static List<Method> getTestMethods(Object unit) {
        Method[] methods = unit.getClass().getDeclaredMethods();
        return Arrays.stream(methods).filter(
                m -> (m.getAnnotation(MyTestInvert.class) != null) || (m.getAnnotation(MyTestCalculator.class) != null))
                .collect(Collectors.toList());
    }

    private static Object getObject(String className) {
        try {
            Class<?> unitClass = Class.forName(className);
            return unitClass.getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return new Object();
        }
    }

    private static void drawBanner(){
        System.out.println(
                """
                        
                        ███╗   ███╗██╗   ██╗    ████████╗███████╗███████╗████████╗    ███████╗███╗   ██╗ ██████╗ ██╗███╗   ██╗███████╗
                        ███╗   ███╗██╗   ██╗    ████████╗███████╗███████╗████████╗    ███████╗███╗   ██╗ ██████╗ ██╗███╗   ██╗███████╗
                        ████╗ ████║╚██╗ ██╔╝    ╚══██╔══╝██╔════╝██╔════╝╚══██╔══╝    ██╔════╝████╗  ██║██╔════╝ ██║████╗  ██║██╔════╝
                        ██╔████╔██║ ╚████╔╝        ██║   █████╗  ███████╗   ██║       █████╗  ██╔██╗ ██║██║  ███╗██║██╔██╗ ██║█████╗ \s
                        ██║╚██╔╝██║  ╚██╔╝         ██║   ██╔══╝  ╚════██║   ██║       ██╔══╝  ██║╚██╗██║██║   ██║██║██║╚██╗██║██╔══╝ \s
                        ██║ ╚═╝ ██║   ██║          ██║   ███████╗███████║   ██║       ███████╗██║ ╚████║╚██████╔╝██║██║ ╚████║███████╗
                        ╚═╝     ╚═╝   ╚═╝          ╚═╝   ╚══════╝╚══════╝   ╚═╝       ╚══════╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝╚═╝  ╚═══╝╚══════╝
                                                                                                                                     \s""");
    }

    private static void printTestsInfo(){
        System.out.println("Information about tests:");
        System.out.println("There are several tests of 2 methods: invertString - which inverts String, and calculate - " +
                "calculates the value, given integer a, integer b, and operation to perform on them.");
        System.out.println("Test suite contains both successful tests and tests which fail, to demonstrate the work of test engine.");
    }

}
