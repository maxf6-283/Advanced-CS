import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Screen extends JFrame implements ActionListener, DocumentListener {
    private ArrayList<MVHS> people;
    private JList<MVHS> peopleList;
    private PeopleListModel peopleListModel;

    private JButton filterStudents;
    private JButton filterTeachers;
    private JButton filterAdmins;
    private JButton filterAll;
    private JTextField searchBar;
    private JLabel searchBarLabel;
    private JButton removePerson;
    private JButton addPerson;
    private AddPersonWindow addPersonWindow;

    public Screen() {
        super("MVHS database");

        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        people = new ArrayList<>();

        setBackground(Color.WHITE);

        people.add(new Student("Jordan U"));
        people.add(new Student("Justin D"));
        people.add(new Teacher("Mr. North"));
        people.add(new Teacher("Mr. South"));
        people.add(new Admin("Ms. East"));
        people.add(new Admin("Ms. West"));

        peopleListModel = new PeopleListModel();
        peopleList = new JList<>(peopleListModel);
        peopleList.setBounds(50, 50, 200, 200);
        add(peopleList);

        filterStudents = new JButton("Show only students");
        filterTeachers = new JButton("Show only teachers");
        filterAdmins = new JButton("Show only admins");
        filterAll = new JButton("Show everyone");
        filterStudents.setBounds(300, 150, 200, 50);
        filterTeachers.setBounds(300, 250, 200, 50);
        filterAdmins.setBounds(300, 350, 200, 50);
        filterAll.setBounds(300, 450, 200, 50);
        add(filterStudents);
        add(filterTeachers);
        add(filterAdmins);
        add(filterAll);
        filterStudents.addActionListener(this);
        filterTeachers.addActionListener(this);
        filterAdmins.addActionListener(this);
        filterAll.addActionListener(this);
        searchBar = new JTextField();
        searchBarLabel = new JLabel("Search for a name:");
        searchBar.setBounds(300, 75, 200, 50);
        searchBarLabel.setBounds(300, 50, 1000, 25);
        add(searchBar);
        add(searchBarLabel);
        searchBar.getDocument().addDocumentListener(this);

        removePerson = new JButton("Remove selected");
        addPerson = new JButton("Add person");
        removePerson.setBounds(50, 300, 200, 50);
        addPerson.setBounds(50, 400, 200, 50);
        add(removePerson);
        add(addPerson);
        removePerson.addActionListener(this);
        addPerson.addActionListener(this);

        addPersonWindow = new AddPersonWindow();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class PeopleListModel extends AbstractListModel<MVHS> {
        private List<MVHS> activeArray = people;
        private Predicate<? super MVHS> lastFilter = (e -> true);
        private Predicate<? super MVHS> lastFilter2 = (e -> true);

        @Override
        public int getSize() {
            return activeArray.size();
        }

        @Override
        public MVHS getElementAt(int index) {
            return activeArray.get(index);
        }

        public void filter(Predicate<? super MVHS> predicate) {
            lastFilter = predicate;
            int oldSize = activeArray.size();
            activeArray = people.stream().filter(e -> predicate.test(e) && lastFilter2.test(e)).toList();
            if (activeArray.size() > oldSize) {
                fireIntervalAdded(this, 0, activeArray.size() - oldSize);
            } else if (activeArray.size() < oldSize) {
                fireIntervalRemoved(this, 0, oldSize - activeArray.size());
            }
            fireContentsChanged(this, 0, activeArray.size() - 1);
        }

        public void filter2(Predicate<? super MVHS> predicate) {
            lastFilter2 = predicate;
            int oldSize = activeArray.size();
            activeArray = people.stream().filter(e -> lastFilter.test(e) && predicate.test(e)).toList();
            if (activeArray.size() > oldSize) {
                fireIntervalAdded(this, 0, activeArray.size() - oldSize);
            } else if (activeArray.size() < oldSize) {
                fireIntervalRemoved(this, 0, oldSize - activeArray.size());
            }
            fireContentsChanged(this, 0, activeArray.size() - 1);
        }

        public void update() {
            filter(e -> lastFilter.test(e) && lastFilter2.test(e));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == filterStudents) {
            peopleListModel.filter(person -> person.getRole().equals("student"));
        } else if (e.getSource() == filterTeachers) {
            peopleListModel.filter(person -> person.getRole().equals("teacher"));
        } else if (e.getSource() == filterAdmins) {
            peopleListModel.filter(person -> person.getRole().equals("admin"));
        } else if (e.getSource() == filterAll) {
            peopleListModel.filter(person -> true);
        } else if (e.getSource() == removePerson) {
            people.removeAll(peopleList.getSelectedValuesList());
            peopleListModel.update();
        } else if (e.getSource() == addPerson) {
            addPersonWindow.activate();
        } else if (e.getSource() == addPersonWindow.add) {
            addPersonWindow.addPerson();
        }
        peopleList.setSelectedValue(null, false);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (e.getDocument() == searchBar.getDocument()) {
            peopleListModel
                    .filter2(person -> person.getName().toLowerCase().contains(searchBar.getText().toLowerCase()));
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (e.getDocument() == searchBar.getDocument()) {
            peopleListModel
                    .filter2(person -> person.getName().toLowerCase().contains(searchBar.getText().toLowerCase()));
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (e.getDocument() == searchBar.getDocument()) {
            peopleListModel
                    .filter2(person -> person.getName().toLowerCase().contains(searchBar.getText().toLowerCase()));
        }
    }

    private class AddPersonWindow extends JFrame {
        private JTextField nameField;
        private JLabel nameLabel;
        private JComboBox<String> role;
        private JButton add;

        public AddPersonWindow() {
            super("add person");
            setLayout(null);

            role = new JComboBox<>(new String[] { "--select a role--", "student", "teacher", "admin" });
            role.setBounds(50, 50, 200, 50);
            add(role);
            role.addActionListener(Screen.this);

            nameField = new JTextField();
            nameLabel = new JLabel("Name: ");
            nameField.setBounds(50, 150, 200, 25);
            nameLabel.setBounds(50, 125, 200, 25);
            add(nameField);
            add(nameLabel);

            add = new JButton("Add person");
            add.setBounds(300, 50, 150, 150);
            add(add);
            add.addActionListener(Screen.this);

        }

        public void activate() {
            setVisible(true);
            setAlwaysOnTop(true);

            setSize(500, 300);
            setResizable(false);
        }

        public void addPerson() {
            if (nameField.getText().length() < 1) {
                return;
            }
            switch (role.getSelectedIndex()) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    people.add(new Student(nameField.getText()));
                }
                case 2 -> {
                    people.add(new Teacher(nameField.getText()));
                }
                case 3 -> {
                    people.add(new Admin(nameField.getText()));
                }
            }

            deactivate();
            peopleListModel.update();
        }

        public void deactivate() {
            setVisible(false);
            nameField.setText("");
            role.setSelectedIndex(0);
        }
    }
}
