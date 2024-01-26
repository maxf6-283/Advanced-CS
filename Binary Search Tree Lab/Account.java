public class Account implements Comparable<Account> {
    private String lastName;
    private String firstName;
    private int pin;
    private double balance;

    public Account (String lName, String fName, int pin, double bal) {
        lastName = lName;
        firstName = fName;
        this.pin = pin;
        balance = bal;
    }

    @Override
    public int compareTo(Account o) {
        if(lastName.compareTo(o.lastName) == 0) {
            return firstName.compareToIgnoreCase(o.firstName);
        }
        return lastName.compareToIgnoreCase(o.lastName);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public int pin() {
        return pin;
    }

    public double balance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Account a) {
            return compareTo(a) == 0;
        }
        return false;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBalance() {
        return String.format("%.2f", balance);
    }
}