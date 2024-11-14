import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Screen extends JFrame {
    private CardLayout cardLayout;
    private ArrayList<Account> accounts;
    private StartMenu startMenu;
    private AccessMenu accessMenu;
    private EditAccountInfoMenu editInfoMenu;
    private Font font = new Font("Verdana", Font.PLAIN, 20);

    public Screen() {
        super("Bank");
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        accounts = new ArrayList<>();
        accounts.add(new Account("Jennifer", 999.99, 1234));
        accounts.add(new Account("Jose", 500.01, 4321));
        accounts.add(new Account("John", 1234.56, 1000));
        accounts.add(new Account("Jenna", 65.43, 9999));
        accounts.add(new Account("Johann", 56789.01, 1029));

        startMenu = new StartMenu();
        add(startMenu, "start menu");

        accessMenu = new AccessMenu();
        add(accessMenu, "access menu");

        editInfoMenu = new EditAccountInfoMenu();
        add(editInfoMenu, "edit info menu");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        cardLayout.show(startMenu.getParent(), "start menu");
    }

    private class StartMenu extends JPanel implements ActionListener {
        private JTextField nameField;
        private JTextField pinField;
        private JLabel nameLabel;
        private JLabel pinLabel;
        private JButton loginButton;
        private JLabel alertText;

        public StartMenu() {
            setLayout(null);

            setBackground(Color.LIGHT_GRAY);

            nameLabel = new JLabel("Account name:");
            nameLabel.setFont(font);
            add(nameLabel);
            nameField = new JTextField();
            nameField.setFont(font);
            add(nameField);
            pinLabel = new JLabel("Account pin:");
            pinLabel.setFont(font);
            add(pinLabel);
            pinField = new JTextField();
            pinField.setFont(font);
            add(pinField);
            loginButton = new JButton("Login to account");
            loginButton.addActionListener(this);
            loginButton.setFont(font);
            add(loginButton);
            alertText = new JLabel();
            alertText.setForeground(Color.RED);
            alertText.setFont(font);
            add(alertText);

            nameLabel.setBounds(100, 50, 1000, 50);
            nameField.setBounds(100, 100, getWidth() - 200, 50);
            pinLabel.setBounds(100, 200, 1000, 50);
            pinField.setBounds(100, 250, getWidth() - 200, 50);
            loginButton.setBounds(100, 350, getWidth() - 200, 100);
            alertText.setBounds(100, 500, 1000, 50);

            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    nameLabel.setBounds(100, 50, 1000, 50);
                    nameField.setBounds(100, 100, getWidth() - 200, 50);
                    pinLabel.setBounds(100, 200, 1000, 50);
                    pinField.setBounds(100, 250, getWidth() - 200, 50);
                    loginButton.setBounds(100, 350, getWidth() - 200, 100);
                    alertText.setBounds(100, 500, 1000, 50);
                }
            });
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                String name = nameField.getText();
                int pin;
                try {
                    pin = Integer.parseInt(pinField.getText());
                    if (pin > 9999 || pin < 0) {
                        alertText.setText("Invalid pin");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    alertText.setText("Invalid pin");
                    return;
                }

                for (Account account : accounts) {
                    if (account.setAccess(pin, name)) {
                        accessMenu.setAccount(account);
                        pinField.setText("");
                        nameField.setText("");
                        alertText.setText("");
                        cardLayout.show(accessMenu.getParent(), "access menu");
                        return;
                    }
                }

                alertText.setText("Invalid login information");
            }
        }
    }

    private class AccessMenu extends JPanel implements ActionListener {
        private Account accessedAccount;

        private JLabel nameLabel;
        private JLabel balanceLabel;
        private JLabel amountLabel;
        private JTextField amountField;
        private JButton depositButton;
        private JButton withdrawButton;
        private JButton logoutButton;
        private JButton editInfoButton;
        private JLabel alertText;

        public AccessMenu() {
            setLayout(null);

            setBackground(Color.LIGHT_GRAY);

            nameLabel = new JLabel();
            nameLabel.setFont(font);
            add(nameLabel);
            balanceLabel = new JLabel();
            balanceLabel.setFont(font);
            add(balanceLabel);
            amountLabel = new JLabel("Amount to deposit/withdraw: ");
            amountLabel.setFont(font);
            add(amountLabel);
            amountField = new JTextField();
            amountField.setFont(font);
            add(amountField);
            depositButton = new JButton("Deposit");
            depositButton.setFont(font);
            depositButton.addActionListener(this);
            add(depositButton);
            withdrawButton = new JButton("Withdraw");
            withdrawButton.setFont(font);
            withdrawButton.addActionListener(this);
            add(withdrawButton);
            logoutButton = new JButton("Logout");
            logoutButton.setFont(font);
            logoutButton.addActionListener(this);
            add(logoutButton);
            editInfoButton = new JButton("Edit account info");
            editInfoButton.setFont(font);
            editInfoButton.addActionListener(this);
            add(editInfoButton);
            alertText = new JLabel();
            alertText.setForeground(Color.RED);
            alertText.setFont(font);
            add(alertText);

            nameLabel.setBounds(100, 50, 1000, 50);
            balanceLabel.setBounds(100, 100, 1000, 50);
            amountLabel.setBounds(100, 200, 1000, 50);
            amountField.setBounds(100, 250, getWidth() - 200, 50);
            depositButton.setBounds(100, 350, getWidth() / 2 - 125, 100);
            withdrawButton.setBounds(getWidth() / 2 + 25, 350, getWidth() / 2 - 150, 100);
            logoutButton.setBounds(100, 500, getWidth() / 2 - 125, 100);
            editInfoButton.setBounds(getWidth() / 2 + 25, 500, getWidth() / 2 - 150, 100);
            alertText.setBounds(100, 650, 1000, 50);

            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    nameLabel.setBounds(100, 50, 1000, 50);
                    balanceLabel.setBounds(100, 100, 1000, 50);
                    amountLabel.setBounds(100, 200, 1000, 50);
                    amountField.setBounds(100, 250, getWidth() - 200, 50);
                    depositButton.setBounds(100, 350, getWidth() / 2 - 125, 100);
                    withdrawButton.setBounds(getWidth() / 2 + 25, 350, getWidth() / 2 - 150, 100);
                    logoutButton.setBounds(100, 500, getWidth() / 2 - 125, 100);
                    editInfoButton.setBounds(getWidth() / 2 + 25, 500, getWidth() / 2 - 150, 100);
                    alertText.setBounds(100, 650, 1000, 50);
                }
            });
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == depositButton) {
                double amount;
                try {
                    amount = ((int) (Double.parseDouble(amountField.getText()) * 100)) / 100.0;
                    if (amount < 0) {
                        alertText.setForeground(Color.RED);
                        alertText.setText("Invalid amount to deposit");
                        return;
                    } else if (amount > 1000000) {
                        alertText.setForeground(Color.RED);
                        alertText.setText("Invalid amount to deposit - cannot deposit over $1,000,000 at once");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    alertText.setForeground(Color.RED);
                    alertText.setText("Invalid amount to deposit");
                    return;
                }

                if (accessedAccount.deposit(amount)) {
                    alertText.setForeground(Color.GREEN);
                    alertText.setText(String.format("Successfully deposited $%,.2f", amount));
                    amountField.setText("");
                    updateAccountInfo();
                } else {
                    alertText.setForeground(Color.RED);
                    alertText.setText("Failed to deposit");
                }
            } else if (e.getSource() == withdrawButton) {
                double amount;
                try {
                    amount = ((int) (Double.parseDouble(amountField.getText()) * 100)) / 100.0;
                    if (amount < 0) {
                        alertText.setForeground(Color.RED);
                        alertText.setText("Invalid amount to withdraw");
                        return;
                    } else if (amount > accessedAccount.getBalance()) {
                        alertText.setForeground(Color.RED);
                        alertText.setText("Invalid amount to withdraw - too little in account");
                        return;
                    } else if (amount > 1000000) {
                        alertText.setForeground(Color.RED);
                        alertText.setText("Invalid amount to withdraw - cannot withdraw over $1,000,000 at once");
                        return;                        
                    }
                } catch (NumberFormatException ex) {
                    alertText.setForeground(Color.RED);
                    alertText.setText("Invalid amount to withdraw");
                    return;
                }

                if (accessedAccount.withdraw(amount)) {
                    alertText.setForeground(Color.GREEN);
                    alertText.setText(String.format("Successfully withdrew $%,.2f", amount));
                    amountField.setText("");
                    updateAccountInfo();
                } else {
                    alertText.setForeground(Color.RED);
                    alertText.setText("Failed to withdraw");
                }
            } else if (e.getSource() == logoutButton) {
                accessedAccount.resetAccess();
                alertText.setText("");
                amountField.setText("");
                cardLayout.show(startMenu.getParent(), "start menu");
            } else if (e.getSource() == editInfoButton) {
                alertText.setText("");
                editInfoMenu.setAccount(accessedAccount);
                cardLayout.show(editInfoMenu.getParent(), "edit info menu");
            }
        }

        public void setAccount(Account account) {
            accessedAccount = account;
            updateAccountInfo();
        }

        public void updateAccountInfo() {
            nameLabel.setText("Name: " + accessedAccount.getName());
            balanceLabel.setText(String.format("Balance: $%,.2f", accessedAccount.getBalance()));
        }
    }

    private class EditAccountInfoMenu extends JPanel implements ActionListener {
        private Account accessedAccount;

        private JLabel nameLabel;
        private JTextField nameField;
        private JLabel pinLabel;
        private JTextField pinField;
        private JButton applyEditsButton;
        private JButton returnButton;
        private JLabel alertText;

        public EditAccountInfoMenu() {
            setLayout(null);

            setBackground(Color.LIGHT_GRAY);

            nameLabel = new JLabel("Name:");
            nameLabel.setFont(font);
            add(nameLabel);
            nameField = new JTextField();
            nameField.setFont(font);
            add(nameField);
            pinLabel = new JLabel("Account pin:");
            pinLabel.setFont(font);
            add(pinLabel);
            pinField = new JTextField();
            pinField.setFont(font);
            add(pinField);
            applyEditsButton = new JButton("Apply edits");
            applyEditsButton.setFont(font);
            applyEditsButton.addActionListener(this);
            add(applyEditsButton);
            returnButton = new JButton("Return");
            returnButton.setFont(font);
            returnButton.addActionListener(this);
            add(returnButton);
            alertText = new JLabel();
            alertText.setFont(font);
            add(alertText);

            nameLabel.setBounds(100, 50, 1000, 50);
            nameField.setBounds(100, 100, getWidth() - 200, 50);
            pinLabel.setBounds(100, 200, 1000, 50);
            pinField.setBounds(100, 250, getWidth() - 200, 50);
            applyEditsButton.setBounds(100, 350, getWidth() / 2 - 125, 100);
            returnButton.setBounds(getWidth() / 2 + 25, 350, getWidth() / 2 - 150, 100);
            alertText.setBounds(100, 500, 1000, 50);
            
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    nameLabel.setBounds(100, 50, 1000, 50);
                    nameField.setBounds(100, 100, getWidth() - 200, 50);
                    pinLabel.setBounds(100, 200, 1000, 50);
                    pinField.setBounds(100, 250, getWidth() - 200, 50);
                    applyEditsButton.setBounds(100, 350, getWidth() / 2 - 125, 100);
                    returnButton.setBounds(getWidth() / 2 + 25, 350, getWidth() / 2 - 150, 100);
                    alertText.setBounds(100, 500, 1000, 50);
                }
            });
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == applyEditsButton) {
                String name = nameField.getText();
                if (name.length() < 3) {
                    alertText.setForeground(Color.RED);
                    alertText.setText("Invalid name - too short");
                    return;
                } else if (name.length() > 50) {
                    alertText.setForeground(Color.RED);
                    alertText.setText("Invalid name - too long");
                    return;
                } else if (!name.matches("[A-Za-z ]{" + name.length() + "}")) {
                    alertText.setForeground(Color.RED);
                    alertText.setText("Invlid name - invalid character(s) in name");
                    return;
                }
                int pin;
                try {
                    pin = Integer.parseInt(pinField.getText());
                    if (pin > 9999 || pin < 0) {
                        alertText.setForeground(Color.RED);
                        alertText.setText("Invalid pin");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    alertText.setForeground(Color.RED);
                    alertText.setText("Invalid pin");
                    return;
                }

                accessedAccount.setName(name);
                accessedAccount.setPin(pin);
                alertText.setForeground(Color.GREEN);
                alertText.setText("Successfully edited account information");
            } else if (e.getSource() == returnButton) {
                alertText.setText("");
                accessMenu.updateAccountInfo();
                cardLayout.show(accessMenu.getParent(), "access menu");
            }
        }

        public void setAccount(Account account) {
            accessedAccount = account;
            nameField.setText(account.getName());
            pinField.setText("" + account.getPin());
        }
    }
}
