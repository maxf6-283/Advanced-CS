import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen extends JFrame implements ActionListener, ListSelectionListener {

    private JScrollPane accountScrollPane;
    private JList<Account> accountList;
    private DefaultListModel<Account> accountListModel;
    private BinaryTree<Account> accounts;

    private boolean adminView;
    private JButton switchViews;
    private JComponent[] adminComponents;
    private JComponent[] customerComponents;

    private PlaceholderTextField accountFirstName;
    private PlaceholderTextField accountLastName;
    private PlaceholderTextField accountPin;
    private PlaceholderTextField accountBalance;
    private JButton createAccount;
    private JLabel createPasses;

    private PlaceholderTextField accFName;
    private PlaceholderTextField accLName;
    private JButton search;
    private JLabel searchPasses;
    private JLabel searchInfo;
    private JButton delete;

    private PlaceholderTextField loginFName;
    private PlaceholderTextField loginLName;
    private PlaceholderTextField loginPin;
    private JButton login;
    private JLabel loginPasses;

    private JLabel accInfo;
    private PlaceholderTextField amount;
    private JButton withdraw;
    private JButton deposit;
    private JButton logout;

    public Screen() {
        super("Account manager");
        setLayout(null);
        setSize(810, 630);
        setResizable(false);

        accounts = new BinaryTree<>();
        try {
            Scanner nameScanner = new Scanner(new FileInputStream("names.txt"));
            while (nameScanner.hasNext()) {
                String name = nameScanner.nextLine();
                String fName = name.split(",")[0];
                String lName = name.split(",")[1];
                int pin = (int) (Math.random() * 10000);
                double bal = Math.random() * 100_000;
                accounts.add(new Account(fName, lName, pin, bal));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        accountListModel = new DefaultListModel<>();
        updateAccountList();
        accountList = new JList<>(accountListModel);
        accountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        accountList.addListSelectionListener(this);
        accountScrollPane = new JScrollPane(accountList);
        accountScrollPane.setBounds(50, 50, 200, 500);

        accountFirstName = new PlaceholderTextField("First Name");
        accountLastName = new PlaceholderTextField("Last Name");
        accountPin = new PlaceholderTextField("PIN");
        accountBalance = new PlaceholderTextField("Balance");
        createAccount = new JButton("Create Account");
        createPasses = new JLabel();
        accountFirstName.setBounds(300, 50, 200, 25);
        accountLastName.setBounds(300, 75, 200, 25);
        accountPin.setBounds(300, 100, 200, 25);
        accountBalance.setBounds(300, 125, 200, 25);
        createAccount.setBounds(300, 160, 200, 50);
        createPasses.setBounds(300, 210, 200, 25);
        createAccount.addActionListener(this);

        accFName = new PlaceholderTextField("First Name");
        accLName = new PlaceholderTextField("Last Name");
        search = new JButton("Search");
        searchPasses = new JLabel();
        searchInfo = new JLabel();
        delete = new JButton("Delete Account");
        accFName.setBounds(300, 290, 200, 25);
        accLName.setBounds(300, 315, 200, 25);
        search.setBounds(300, 350, 200, 50);
        searchPasses.setBounds(300, 400, 200, 25);
        searchInfo.setBounds(300, 425, 200, 70);
        delete.setBounds(300, 500, 200, 50);
        setInfoText("");
        search.addActionListener(this);
        delete.addActionListener(this);
        delete.setEnabled(false);

        // --- customer components ---

        loginFName = new PlaceholderTextField("First name");
        loginLName = new PlaceholderTextField("Last name");
        loginPin = new PlaceholderTextField("PIN");
        login = new JButton("Log in");
        loginPasses = new JLabel();
        loginFName.setBounds(50, 50, 200, 25);
        loginLName.setBounds(50, 75, 200, 25);
        loginPin.setBounds(50, 100, 200, 25);
        login.setBounds(50, 135, 200, 50);
        loginPasses.setBounds(50, 185, 200, 25);
        login.addActionListener(this);

        accInfo = new JLabel();
        setCustomerInfoText("");
        amount = new PlaceholderTextField("Amount");
        deposit = new JButton("Deposit");
        withdraw = new JButton("Withdraw");
        logout = new JButton("Log out");
        accInfo.setBounds(300, 50, 200, 70);
        logout.setBounds(300, 130, 200, 70);
        
        
        // --- switch ---

        adminView = true;
        switchViews = new JButton("Switch to customer view");
        switchViews.setBounds(550, 50, 200, 50);
        switchViews.addActionListener(this);
        add(switchViews);

        adminComponents = new JComponent[] { search, delete, searchPasses, searchInfo, accFName, accLName,
                accountBalance, accountFirstName, accountLastName, accountScrollPane, accountPin, createAccount,
                createPasses };
        customerComponents = new JComponent[] { loginFName, loginLName, loginPin, login, loginPasses, accInfo, amount,
                deposit, withdraw, logout };

        for (JComponent j : adminComponents) {
            add(j);
        }
        for (JComponent j : customerComponents) {
            add(j);
            j.setVisible(false);
        }
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void updateAccountList() {
        accountListModel.clear();
        for (Account a : accounts.toArray()) {
            accountListModel.addElement(a);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createAccount) {
            String fName = accountFirstName.getText();
            String lName = accountLastName.getText();
            int pin = Integer.parseInt(accountPin.getText());
            double bal = Double.parseDouble(accountBalance.getText());
            accounts.resetPasses();
            accounts.add(new Account(lName, fName, pin, bal));
            createPasses.setText("Passes: " + accounts.passes());
            updateAccountList();
        } else if (e.getSource() == search) {
            String fName = accFName.getText();
            String lName = accLName.getText();
            Account acc = new Account(lName, fName, -1, -1);
            accounts.resetPasses();
            if (accounts.contains(acc)) {
                accountList.setSelectedValue(acc, true);
            }
            searchPasses.setText("Passes: " + accounts.passes());
        } else if (e.getSource() == delete) {
            accounts.remove(accountList.getSelectedValue());
            updateAccountList();
        } else if (e.getSource() == switchViews) {
            if (adminView) {
                adminView = false;
                switchViews.setText("Switch to admin view");
                for (JComponent j : adminComponents) {
                    j.setVisible(false);
                }
                for (JComponent j : customerComponents) {
                    j.setVisible(true);
                }
            } else {
                adminView = true;
                switchViews.setText("Switch to customer view");
                for (JComponent j : adminComponents) {
                    j.setVisible(true);
                }
                for (JComponent j : customerComponents) {
                    j.setVisible(false);
                }
            }
        }
    }

    private void setInfoText(String text) {
        searchInfo.setText(
                "<html><div style='background-color:white' width=\"200\" height=\"70\">" + text + "</div></html>");
    }

    private void setCustomerInfoText(String text) {
        accInfo.setText(
                "<html><div style='background-color:white' width=\"200\" height=\"70\">" + text + "</div></html>");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == accountList) {
            Account acc = accountList.getSelectedValue();
            delete.setEnabled(acc != null);
            if (acc != null) {
                setInfoText("First Name: " + acc.getFirstName() + "<br>" +
                        "Last name: " + acc.getLastName() + "<br>" +
                        "PIN: " + acc.pin() + "<br>" +
                        "Balance: " + acc.getBalance());
            } else {
                setInfoText("");
            }

        }
    }

}
