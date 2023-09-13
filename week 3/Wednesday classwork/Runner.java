import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) {
        ArrayList<Pair<Employee, Company>> pairList = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        Company johnDeere = new Company("John Deere", 102.89);
        Company westCoastProducts = new Company("West Coast Products", 23.54);
        Company qualcomm = new Company("Qualcomm", 164.26);

        ArrayList<Company> companies = new ArrayList<>(List.of(johnDeere, westCoastProducts, qualcomm));

        Employee milind = new Employee("Milind");
        Employee jane = new Employee("Jane");
        Employee john = new Employee("John");
        Employee klaus = new Employee("Klaus");
        Employee gene = new Employee("Gene");

        pairList.add(new Pair<>(milind, johnDeere));
        pairList.add(new Pair<>(jane, westCoastProducts));
        pairList.add(new Pair<>(john, westCoastProducts));
        pairList.add(new Pair<>(klaus, johnDeere));
        pairList.add(new Pair<>(gene, qualcomm));

        boolean loop = true;
        while (loop) {
            System.out.println("Select an option: ");
            System.out.println("1.) Display all employees");
            System.out.println("2.) Enter a company name to change the stock price of");
            System.out.println("3.) Add a new company");
            System.out.println("4.) Select a user and replace their company with another company");
            System.out.println("5.) Quit");
            int ans = sc.nextInt();
            sc.nextLine();
            switch (ans) {
                case 1 -> {
                    System.out.println();
                    for (Pair<Employee, Company> pair : pairList) {
                        System.out.println(pair);
                    }
                    System.out.println();
                }
                case 2 -> {
                    System.out.print("\nEnter a company name: ");
                    String companyName = sc.nextLine();
                    boolean found = false;
                    CompanyLoop: for (Company company : companies) {
                        if (company.getName().equalsIgnoreCase(companyName)) {
                            found = true;
                            System.out.print("Enter the new stock price: ");
                            double stockPrice = sc.nextDouble();
                            sc.nextLine();
                            company.updateStockPrice(stockPrice);
                            System.out.println();
                            break CompanyLoop;
                        }
                    }
                    if (!found) {
                        System.out.println("No company found by that name\n");
                    }
                }
                case 3 -> {
                    System.out.print("\nWhat company would you like to add: ");
                    String newComp = sc.nextLine();
                    System.out.print("What is the stock price of this company: ");
                    double stockPrice = sc.nextDouble();
                    sc.nextLine();
                    companies.add(new Company(newComp, stockPrice));
                    System.out.println();
                }
                case 4 -> {
                    System.out.print("\nEnter the name of the user: ");
                    String userName = sc.nextLine();
                    boolean found = false;
                    EmployeeLoop: for (Pair<Employee, Company> pair : pairList) {
                        if (pair.getKey().getName().equalsIgnoreCase(userName)) {
                            found = true;
                            System.out.print("Enter the name of the new company for the user: ");
                            String companyName = sc.nextLine();
                            boolean companyFound = false;
                            CompanyLoop: for (Company company : companies) {
                                if (company.getName().equalsIgnoreCase(companyName)) {
                                    companyFound = true;
                                    pair.setValue(company);
                                    System.out.println();
                                    break CompanyLoop;
                                }
                            }
                            if (!companyFound) {
                                System.out.println("No company found by that name\n");
                            }
                            break EmployeeLoop;
                        }
                    }
                    if (!found) {
                        System.out.println("No employee found by that name\n");
                    }
                }
                case 5 -> {
                    loop = false;
                }
                default -> {
                    System.out.println("\nInvalid response, answer again\n");
                }
            }
        }

        sc.close();


    }
}
