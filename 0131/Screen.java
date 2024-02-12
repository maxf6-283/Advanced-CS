import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class Screen extends JPanel implements ActionListener {
    private BST<Student> tree;
    private JButton addButton;
    private JButton clear;
    private JButton delete;
    private JButton balance;
    private JButton search;
    private PlaceholderTextField fNameText;
    private PlaceholderTextField lNameText;
    private PlaceholderTextField IDText;
    private PlaceholderTextField schoolText;
    private JLabel studentInfo;
    private JLabel studentList;

    public Screen() {
        setLayout(null);
        tree = new BST<>();
        tree.add(new Student("Julia", "Johnson", 1111, "MVHS"));
        tree.add(new Student("Jack", "Thompson", 1234, "LAHS"));
        tree.add(new Student("Thomas", "Jenkins", 4321, "MVHS"));
        // int [] nums = new int[] {10, 5, 15, 1, 9, 11, 19};
        // for(int each : nums) {
        // tree.add(each);
        // }

        fNameText = new PlaceholderTextField("First Name");
        fNameText.setBounds(25, 350, 150, 30);
        this.add(fNameText);

        lNameText = new PlaceholderTextField("Last Name");
        lNameText.setBounds(175, 350, 150, 30);
        this.add(lNameText);

        IDText = new PlaceholderTextField("ID");
        IDText.setBounds(25, 390, 150, 30);
        this.add(IDText);

        schoolText = new PlaceholderTextField("School");
        schoolText.setBounds(175, 390, 150, 30);
        this.add(schoolText);

        addButton = new JButton("Add");
        addButton.setBounds(350, 350, 100, 30);
        addButton.addActionListener(this);
        this.add(addButton);

        delete = new JButton("Delete");
        delete.setBounds(450, 350, 100, 30);
        delete.addActionListener(this);
        this.add(delete);

        search = new JButton("Search");
        search.setBounds(350, 390, 100, 30);
        search.addActionListener(this);
        this.add(search);

        clear = new JButton("Clear");
        clear.setBounds(400, 25, 100, 30);
        clear.addActionListener(this);
        this.add(clear);

        balance = new JButton("Balance");
        balance.setBounds(400, 75, 100, 30);
        balance.addActionListener(this);
        this.add(balance);

        studentInfo = new JLabel();
        studentInfo.setBounds(100, 420, 200, 70);
        this.add(studentInfo);

        studentList = new JLabel();
        studentList.setBounds(600, 50, 200, 70);
        setStudentListText();
        this.add(studentList);
    }

    private void setStudentListText() {
        studentList.setText("<html>" + tree.toString().replace(",", "<br>") + "</html>");
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 550);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        tree.draw(g, 200, 20, 200);
        g.setColor(Color.BLACK);
        g.drawString("Height: " + tree.getHeight(), 25, 25);
        g.drawString("Level: " + tree.getLevel(), 25, 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String firstName = fNameText.getText();
            String lastName = lNameText.getText();
            int ID = Integer.parseInt(IDText.getText());
            String school = schoolText.getText();

            tree.add(new Student(firstName, lastName, ID, school));

            fNameText.clear();
            lNameText.clear();
            IDText.clear();
            schoolText.clear();
        } else if (e.getSource() == clear) {
            tree.clear();
        } else if (e.getSource() == delete) {
            String firstName = fNameText.getText();
            String lastName = lNameText.getText();

            tree.remove(new Student(firstName, lastName, 0, ""));

            fNameText.clear();
            lNameText.clear();
        } else if (e.getSource() == balance) {
            tree.balance();
        } else if (e.getSource() == search) {
            String firstName = fNameText.getText();
            String lastName = lNameText.getText();

            Student s = tree.get(new Student(firstName, lastName, 0, ""));

            studentInfo.setText("<html>" + s.toString() + "<br> school: " + s.school() + "<br> id: " + s.id() + "</html>");

            fNameText.clear();
            lNameText.clear();
        }
        setStudentListText();
        repaint();
    }
}
