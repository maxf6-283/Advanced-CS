import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
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

    public Screen() {
        super("Account manager");
        setLayout(null);
        setSize(800, 630);
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
        add(accountScrollPane);

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
        createAccount.setBounds(300, 175, 200, 50);
        createPasses.setBounds(300, 225, 200, 25);
        add(accountFirstName);
        add(accountLastName);
        add(accountBalance);
        add(accountPin);
        add(createAccount);
        add(createPasses);
        createAccount.addActionListener(this);

        accFName = new PlaceholderTextField("First Name");
        accLName = new PlaceholderTextField("Last Name");
        search = new JButton("Search");
        searchPasses = new JLabel();
        searchInfo = new JLabel();
        accFName.setBounds(300, 275, 200, 25);
        accLName.setBounds(300, 300, 200, 25);
        search.setBounds(300, 350, 200, 50);
        searchPasses.setBounds(300, 400, 200, 25);
        searchInfo.setBounds(300, 450, 200, 100);
        setInfoText("");
        add(accFName);
        add(accLName);
        add(search);
        add(searchPasses);
        add(searchInfo);
        search.addActionListener(this);

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
            if(accounts.contains(acc)) {
                accountList.setSelectedValue(acc, true);
            }
            searchPasses.setText("Passes: " + accounts.passes());
        }
    }

    private void setInfoText(String text) {
        searchInfo.setText("<html><div style='background-color:white' width=\"200\" height=\"100\">" + text + "</div></html>");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(e.getSource() == accountList) {
            Account acc = accountList.getSelectedValue();
            setInfoText("First Name: " + acc.getFirstName() + "<br>" +
                                "Last name: " + acc.getLastName() + "<br>" + 
                                "PIN: " + acc.pin() + "<br>" + 
                                "Balance: " + acc.getBalance());

        }
    }

}
