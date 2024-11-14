import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen extends JFrame implements ActionListener {
    private JList<String> studentList;
    private CustomListModel<Profile, String> studentListModel;
    private TreeSet<Profile> students;
    private PlaceholderTextField nameText;
    private PlaceholderTextField idText;
    private JButton addButton;
    private JButton deleteButton;

    public Screen() {
        super("Student Database Manager");
        setLayout(null);

        students = new TreeSet<>();
        students.add(new Profile("John", 1234));
        students.add(new Profile("Jane", 4321));
        students.add(new Profile("Jeff", 1111));

        studentListModel = new CustomListModel<>(students, e -> e.toString());

        studentList = new JList<>(studentListModel);
        add(studentList);
        studentList.setBounds(0, 0, 400, 300);

        nameText = new PlaceholderTextField("Name...");
        nameText.setFont(new Font("Arial", Font.PLAIN, 20));
        nameText.setHorizontalAlignment(SwingConstants.LEFT);
        nameText.setBounds(25, 305, 150, 30);
        this.add(nameText);

        idText = new PlaceholderTextField("ID...");
        idText.setFont(new Font("Arial", Font.PLAIN, 20));
        idText.setHorizontalAlignment(SwingConstants.LEFT);
        idText.setBounds(205, 305, 150, 30);
        this.add(idText);

        addButton = new JButton();
        addButton.setFont(new Font("Arial", Font.BOLD, 20));
        addButton.setHorizontalAlignment(SwingConstants.CENTER);
        addButton.setBounds(55, 345, 100, 30);
        addButton.setText("add");
        this.add(addButton);
        addButton.addActionListener(this);

        deleteButton = new JButton();
        deleteButton.setFont(new Font("Arial", Font.BOLD, 20));
        deleteButton.setHorizontalAlignment(SwingConstants.CENTER);
        deleteButton.setBounds(231, 345, 100, 30);
        deleteButton.setText("delete");
        this.add(deleteButton);
        deleteButton.addActionListener(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    @Override
    public Dimension getPreferredSize() {
        // sets the size of the panel
        return new Dimension(400, 400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = nameText.getText();
            int id = Integer.parseInt(idText.getText());
            for(Profile p : students) {
                if(p.getId() == id) {
                    return;
                }
            }
            students.add(new Profile (name, id));
            studentListModel.update();
        } else if (e.getSource() == deleteButton) {
            String name = nameText.getText();
            int id = Integer.parseInt(idText.getText());
            students.remove(new Profile (name, id));
            studentListModel.update();
        }
    }

}
