import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Runner {
    public static void main(String[] args) {
        List<String> list1 = new java.util.ArrayList<>();
        List<String> list2 = new LinkedList<>();

        TestList<String> testing = new TestList<>(list1, list2);
        for (int i = 0; i < 100; i++) {
            testing.add("Thing: " + i);
        }

        testing.subList(0, 10).clear();
        testing.subList(80, 90).clear();
        testing.subList(20, 30).clear();
        testing.subList(10, 11).clear();
        testing.subList(10, 10).clear();

        testing.remove(0);
        testing.remove(testing.size() - 1);
        testing.remove("Thing: " + 11);

        System.out.println(testing);


        //testAllFunctions(testing, stringArgs, intArgs, collArgs);
        // testing.subList(0, 5).clear();

        // testing.subList(0, 5).clear();

        // testing.clear();


        // testing.set(43, "Test");

        // for (String s : testing.subList(23, 35)) {
        //     System.out.println(s);
        // }

        // testing.subList(25, 30).remove(0);

        // testing.subList(25, 35).subList(1, 5).remove(0);

        // testing.subList(25, 35).subList(1, 5).add("New Thing");

        // System.out.println(testing);

    }

    private static void testAllFunctions(TestList<String> testList, String[] strInputs, int[] intInputs,
            Collection<?>[] collInputs) {
        Method[] methods = testList.getClass().getDeclaredMethods();

        for (Method method : methods) {
            System.out.println(method.getName());
            if(method.getName().equals("clear")) {
                
            }
            Class<?>[] params = method.getParameterTypes();
            Object[] args = new Object[params.length];

            for (int i = 0; i < params.length; i++) {
                if (params[i].equals(String.class) || params[i].equals(Object.class)) {
                    args[i] = strInputs[i % strInputs.length];
                } else if (params[i].equals(int.class)) {
                    args[i] = intInputs[i % intInputs.length];
                } else if (params[i].equals(Collection.class)) {
                    args[i] = collInputs[i % collInputs.length];
                } else {
                    System.out.println(params[i].getName());
                    throw new IllegalArgumentException();
                }
            }

            if (method.getReturnType().equals(ListIterator.class)) {
                try {
                    testListIteratorFunctions((ListIterator<String>) method.invoke(testList, args), strInputs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (method.getReturnType().equals(List.class)) {
                try {
                    testSubListFunctions((List<String>) method.invoke(testList, args), strInputs, intInputs, collInputs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                try {
                    method.invoke(testList, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void testListIteratorFunctions(ListIterator<String> listIterator, String[] strInputs) {
        Method[] methods = listIterator.getClass().getDeclaredMethods();

        for (Method method : methods) {
            System.out.println(method.getName());
            Class<?>[] params = method.getParameterTypes();
            Object[] args = new Object[params.length];

            for (int i = 0; i < params.length; i++) {
                if (params[i].equals(String.class) || params[i].equals(Object.class)) {
                    args[i] = strInputs[i % strInputs.length];
                } else {
                    System.out.println(params[i].getName());
                    throw new IllegalArgumentException();
                }
            }

            try {
                method.invoke(listIterator, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void testSubListFunctions(List<String> testList, String[] strInputs, int[] intInputs, Collection<?>[] collInputs) {
        Method[] methods = testList.getClass().getDeclaredMethods();

        for (Method method : methods) {
            System.out.println(method.getName());
            Class<?>[] params = method.getParameterTypes();
            Object[] args = new Object[params.length];

            for (int i = 0; i < params.length; i++) {
                if (params[i].equals(String.class) || params[i].equals(Object.class)) {
                    args[i] = strInputs[i % strInputs.length];
                } else if (params[i].equals(int.class)) {
                    args[i] = intInputs[i % intInputs.length];
                } else if (params[i].equals(Collection.class)) {
                    args[i] = collInputs[i % collInputs.length];
                } else {
                    System.out.println(params[i].getName());
                    throw new IllegalArgumentException();
                }
            }

            if (method.getReturnType().equals(ListIterator.class)) {
                try {
                    testListIteratorFunctions((ListIterator<String>) method.invoke(testList, args), strInputs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    method.invoke(testList, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}