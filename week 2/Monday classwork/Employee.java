public class Employee extends People{

    private double salary;

    public Employee(String newName, int newAge, double newSalary){
        super(newName, newAge);
        salary = newSalary;
    }

    public String toString(){
        return super.toString() + "\tsalary: "+ salary;
    }

}