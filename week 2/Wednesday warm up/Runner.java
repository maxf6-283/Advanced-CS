public class Runner {
    public static void main(String[] args) {
        Student stu = new Student("John");
        System.out.println(stu.saying());
        Profile pro = (Profile) stu;
        System.out.println(pro.saying());
    }
}
