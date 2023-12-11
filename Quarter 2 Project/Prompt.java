import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Prompt extends JFrame implements ActionListener {
    private JLabel fileDesc;
    private JTextField fileNameField;
    private JComboBox<String> fileSelector;
    private JButton button;
    private Screen parent;

    public Prompt(Screen parent) {
        setLayout(null);
        this.parent = parent;
        fileNameField = new JTextField();
        button = new JButton();
        fileDesc = new JLabel("File name:");
        setSize(300, 200);
        fileNameField.setBounds(25, 50, 250, 50);
        add(fileNameField);
        button.setBounds(100, 100, 100, 25);
        add(button);
        fileDesc.setBounds(25, 25, 250, 25);
        
        fileSelector = new JComboBox<>();
        add(fileSelector);
        fileSelector.setBounds(25, 50, 250, 50);
        
        add(fileDesc);
        button.addActionListener(this);
        setAlwaysOnTop(true);
    }
    
    public void saveFile() {
        fileDesc.setText("File Name: ");;
        button.setText("save");
        button.setVisible(true);
        fileSelector.setVisible(false);
        fileNameField.setVisible(true);
        fileNameField.setText("");
        setVisible(true);
    }
    
    public void loadFile() {
        fileDesc.setText("File Name: ");;
        fileSelector.setVisible(true);
        DefaultComboBoxModel<String> m = new DefaultComboBoxModel<>();
        for (File f : (new File("saves")).listFiles()) {
            m.addElement(f.getName());
        }
        fileSelector.setModel(m);
        button.setVisible(true);
        fileNameField.setVisible(false);
        button.setText("load");
        fileNameField.setText("");
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            if (button.getText().equals("save")) {
                saveToFile();
            } else if (button.getText().equals("load")) {
                loadFromFile();
            }
            setVisible(false);
            button.setText("");
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    new FileInputStream("saves/" + fileSelector.getSelectedItem()));
            Object o = inputStream.readObject();
            inputStream.close();
            parent.loadNewMap((HashTable<Square, TileObject>) o);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    new FileOutputStream("saves/" + fileNameField.getText()));
            outputStream.writeObject(parent.getTileMap());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void error(String msg) {
        fileSelector.setVisible(false);
        fileDesc.setVisible(true);
        fileDesc.setText(msg);
        fileNameField.setVisible(false);
        button.setVisible(false);
        setVisible(true);
    }
}
