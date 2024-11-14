import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Screen extends JFrame {
    private ArrayList<Pair<Student, Schedule>> students;
    private File saveFile;

    private CardLayout cardLayout;

    private StudentScreen studentScreen;
    private StudentListScreen studentListScreen;

    public Screen() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveToFile();
                System.exit(1);
            }
        });

        students = new ArrayList<>();
        saveFile = new File("students.json");
        readFromFile();

        studentScreen = new StudentScreen();
        studentListScreen = new StudentListScreen();

        add(studentScreen, "studentScreen");
        add(studentListScreen, "studentListScreen");

        cardLayout.show(studentListScreen.getParent(), "studentListScreen");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class StudentListScreen extends JPanel implements DocumentListener, ActionListener, ListSelectionListener {

        private CustomListModel<Pair<Student, Schedule>, Student> studentList;

        private JList<Student> studentJList;
        private JTextField searchBar;
        private JButton selectButton;

        public StudentListScreen() {
            setLayout(null);
            studentList = new CustomListModel<Pair<Student, Schedule>, Student>(students, e -> e.getKey());
            studentList.filters.add(e -> true);

            studentJList = new JList<>(studentList);
            studentJList.setBounds(50, 125, 200, 200);
            add(studentJList);
            studentJList.addListSelectionListener(this);
            studentJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            searchBar = new JTextField();
            searchBar.setBounds(50, 50, 200, 50);
            add(searchBar);
            searchBar.getDocument().addDocumentListener(this);

            selectButton = new JButton("Open schedule");
            selectButton.setBounds(50, 350, 200, 50);
            add(selectButton);
            selectButton.addActionListener(this);
            selectButton.setForeground(Color.GRAY);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == selectButton) {
                if (studentJList.getSelectedValue() != null) {
                    studentScreen
                            .setStudent(students.stream().filter(e1 -> e1.getKey() == studentJList.getSelectedValue())
                                    .findFirst().orElseThrow());
                    cardLayout.show(studentScreen.getParent(), "studentScreen");
                }
            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (e.getDocument() == searchBar.getDocument()) {
                search();
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (e.getDocument() == searchBar.getDocument()) {
                search();
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if (e.getDocument() == searchBar.getDocument()) {
                search();
            }
        }

        private void search() {
            String text = searchBar.getText();
            studentList.filters.set(0, e -> e.getKey().toString().toLowerCase().contains(text.toLowerCase()));
            studentList.update();
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(studentJList.getSelectedValue() != null) {
                selectButton.setForeground(Color.BLACK);
            } else {
                selectButton.setForeground(Color.GRAY);
            }
        }
    }

    private class StudentScreen extends JPanel implements ActionListener, ListSelectionListener, DocumentListener {
        private Pair<Student, Schedule> shownStudent;

        private CustomListModel<Pair<Integer, String>, Pair<Integer, String>> periodListModel;

        private JLabel studentName;
        private JList<Pair<Integer, String>> periodList;
        private JButton deletePeriodButton;
        private JLabel periodNameLabel;
        private JTextField periodNameField;
        private JLabel periodLabel;
        private JTextField periodField;
        private JButton addPeriodButton;
        private JButton backButton;

        public StudentScreen() {
            setLayout(null);
            periodListModel = new CustomListModel<>(new ArrayList<>());

            studentName = new JLabel();
            studentName.setBounds(50, 25, 200, 25);
            add(studentName);

            periodList = new JList<>(periodListModel);
            periodList.setBounds(50, 50, 200, 200);
            add(periodList);
            periodList.addListSelectionListener(this);

            deletePeriodButton = new JButton("Delete Period");
            deletePeriodButton.setForeground(Color.GRAY);
            deletePeriodButton.setBounds(50, 275, 200, 50);
            add(deletePeriodButton);
            deletePeriodButton.addActionListener(this);

            periodNameLabel = new JLabel("Period Name:");
            periodNameLabel.setBounds(50, 325, 200, 25);
            add(periodNameLabel);

            periodNameField = new JTextField();
            periodNameField.setBounds(50, 350, 200, 50);
            add(periodNameField);
            periodNameField.getDocument().addDocumentListener(this);

            periodLabel = new JLabel("Period:");
            periodLabel.setBounds(50, 400, 200, 25);
            add(periodLabel);

            periodField = new JTextField();
            periodField.setBounds(50, 425, 200, 50);
            add(periodField);
            periodField.getDocument().addDocumentListener(this);

            addPeriodButton = new JButton("Add period");
            addPeriodButton.setForeground(Color.GRAY);
            addPeriodButton.setBounds(50, 500, 200, 50);
            add(addPeriodButton);
            addPeriodButton.addActionListener(this);

            backButton = new JButton("Back");
            backButton.setBounds(50, 575, 200, 50);
            add(backButton);
            backButton.addActionListener(this);
        }

        public void setStudent(Pair<Student, Schedule> student) {
            shownStudent = student;
            studentName.setText(student.getKey().toString());
            periodListModel = new CustomListModel<>(student.getValue().mySchedule());
            periodList.setModel(periodListModel);
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (periodList.getSelectedValue() == null) {
                deletePeriodButton.setForeground(Color.GRAY);
            } else {
                if(periodList.getSelectedValuesList().size() > 1) {
                    deletePeriodButton.setText("Delete Periods");
                } else {
                    deletePeriodButton.setText("Delete Period");
                }
                deletePeriodButton.setForeground(Color.BLACK);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == deletePeriodButton) {
                if (periodList.getSelectedValue() == null) {
                    deletePeriodButton.setSelected(false);
                } else {
                    periodList.getSelectedValuesList().stream()
                            .forEach(e1 -> shownStudent.getValue().mySchedule().remove(e1));
                    periodListModel.update();
                }
            } else if (e.getSource() == addPeriodButton) {
                if (canEnterPeriodInfo()) {
                    int period = Integer.parseInt(periodField.getText());
                    String periodName = periodNameField.getText();

                    if (periodList.getSelectedValue() != null) {
                        shownStudent.getValue().mySchedule().add(periodList.getSelectedIndex(),
                                new Pair<>(period, periodName));
                        periodListModel.update();
                    } else {
                        shownStudent.getValue().mySchedule().add(new Pair<>(period, periodName));
                        periodListModel.update();
                    }
                } else {
                    addPeriodButton.setSelected(false);
                }
            } else if (e.getSource() == backButton) {
                cardLayout.show(studentListScreen.getParent(), "studentListScreen");
            }
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (canEnterPeriodInfo()) {
                addPeriodButton.setForeground(Color.BLACK);
            } else {
                addPeriodButton.setForeground(Color.GRAY);
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (canEnterPeriodInfo()) {
                addPeriodButton.setForeground(Color.BLACK);
            } else {
                addPeriodButton.setForeground(Color.GRAY);
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if (canEnterPeriodInfo()) {
                addPeriodButton.setForeground(Color.BLACK);
            } else {
                addPeriodButton.setForeground(Color.GRAY);
            }
        }

        private boolean canEnterPeriodInfo() {
            boolean validPeriod = periodField.getText().matches("[0-9]+");
            boolean validName = periodNameField.getText().matches("[0-9a-zA-z .,]+");

            return validPeriod && validName;
        }

    }

    private void saveToFile() {
        String toSave = "{";
        for (Pair<Student, Schedule> pair : students) {
            toSave += "\n\t\"" + pair.getKey().toString() + "\": ";
            String toAdd = "{\n\t\t"
                    + pair.getValue().toString().replaceAll("([0-9]) ?: ?([A-Za-z 0-9.]+)", "\"$1\": \"$2\",")
                            .replace("\n", "\n\t\t");
            toAdd = toAdd.substring(0, toAdd.length() - 1);
            toSave += toAdd;
            toSave += "\n\t},";
        }
        toSave = toSave.substring(0, toSave.length() - 1);
        toSave += "\n}";

        FileWriter writer;
        try {
            writer = new FileWriter(saveFile);
            writer.write(toSave);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void readFromFile() {
        FileReader reader;
        try {
            reader = new FileReader(saveFile);

            while ((char) reader.read() != '{') {
            }
            boolean readingStudents = true;
            while (readingStudents) {
                char character = (char) reader.read();
                if (character == '}') {
                    readingStudents = false;
                } else if (character == '"') {
                    Schedule schedule = new Schedule();
                    Student student;
                    String studentName = "";
                    char stuChar = (char) reader.read();
                    while (stuChar != '"') {
                        studentName += stuChar;
                        stuChar = (char) reader.read();
                    }
                    student = new Student(studentName);
                    while ((char) reader.read() != ':') {
                    }
                    while ((char) reader.read() != '{') {
                    }
                    boolean readSchedule = true;
                    while (readSchedule) {
                        char perChar = (char) reader.read();
                        if (perChar == '}') {
                            readSchedule = false;
                        } else if ("0123456789".contains("" + perChar)) {
                            int period = (int) perChar - 48;
                            while ((char) reader.read() != ':') {
                            }
                            while ((char) reader.read() != '"') {
                            }
                            String className = "";
                            perChar = (char) reader.read();
                            while (perChar != '"') {
                                className += perChar;
                                perChar = (char) reader.read();
                            }

                            schedule.addClass(period, className);
                        }
                    }

                    students.add(new Pair<>(student, schedule));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
