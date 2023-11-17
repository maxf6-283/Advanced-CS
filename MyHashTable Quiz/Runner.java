public class Runner {
    public static void main(String[] args) {
        School mvhs = new School("MVHS", 1);
        School lahs = new School("LAHS", 2);

        MyHashTable<School, String> students = new MyHashTable<>();
        students.put(mvhs, "Jose");
        students.put(mvhs, "Jen");
        students.put(lahs, "John");
        students.put(lahs, "Jane");

        System.out.println(students.keySet());

        System.out.println(students);

        students.remove(mvhs, "Jose");

        System.out.println(students);

        students.remove(lahs);

        System.out.println(students);
    }
}
