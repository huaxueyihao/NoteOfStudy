package chapter12;

public class BasicType {

    public static void main(String[] args) {
//        long a = 2;
//        addInt(a);
//        addLong(a);
//        System.out.println(a);

        String a = "a";
        addStr(a);
        System.out.println(a);
    }

    private static void addStr(String a) {
        a = a + "b";
        System.out.println(a);
    }

    private static void addLong(long var) {
        var++;
        System.out.println(var);
    }

    private static void addInt(int var) {
        var++;
        System.out.println(var);
    }
}
