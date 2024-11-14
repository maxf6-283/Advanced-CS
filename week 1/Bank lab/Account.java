public class Account {
    private String name;
    private long balance;
    private int pin;
    private boolean access;

    public Account(String name, double balance, int pin) {
        this.name = name;
        this.balance = Math.round(balance * 100);
        this.pin = pin;
        access = false;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return access ? balance/100.0 : 0.0;
    }

    public boolean getAccess() {
        return access;
    }

    public boolean setAccess(int inPin, String inName) {
        if(pin == inPin && name.equals(inName)) {
            access = true;
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if(access) {
            if(balance >= amount * 100) {
                balance -= amount * 100;
                return true;
            }
        }
        return false;
    }

    public boolean deposit(double amount) {
        if(access) {
            balance += amount * 100;
            return true;
        }
        return false;
    }

    public void resetAccess() {
        access = false;
    }

    public int getPin() {
        return access ? pin : 0;
    }

    public boolean setName(String name) {
        if (access) {
            this.name = name;
            return true;
        }
        return false;
    }

    public boolean setPin(int pin) {
        if (access) {
            this.pin = pin;
            return true;
        }
        return false;
    }
}