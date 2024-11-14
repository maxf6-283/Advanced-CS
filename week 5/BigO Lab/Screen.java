import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Screen extends JFrame implements ActionListener {
    private List<Student> studentList = new MyArrayList<>();

    private JScrollPane studentScrollPane;
    private JTextArea studentTextArea;
    private JTextField searchContents;
    private JButton searchBinaryButton;
    private JButton searchLinearButton;
    private JButton scrambleButton;
    private JButton sortBubbleButton;
    private JButton sortMergeButton;
    private JLabel resultField;

    private int passes;
    private boolean sorted;

    public Screen() {
        super("Student database");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 575);
        setResizable(false);

        studentTextArea = new JTextArea();
        studentTextArea.setEditable(false);
        readStudents();
        studentScrollPane = new JScrollPane(studentTextArea);
        studentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        studentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        studentScrollPane.setBounds(50, 50, 200, 200);
        add(studentScrollPane);

        searchContents = new JTextField();
        searchContents.setBounds(50, 275, 200, 50);
        add(searchContents);

        searchBinaryButton = new JButton("Binary Search");
        searchBinaryButton.setBounds(50, 350, 200, 50);
        add(searchBinaryButton);
        searchBinaryButton.addActionListener(this);

        searchLinearButton = new JButton("Linear Search");
        searchLinearButton.setBounds(50, 425, 200, 50);
        add(searchLinearButton);
        searchLinearButton.addActionListener(this);

        resultField = new JLabel();
        setResultText("");
        resultField.setBounds(300, 50, 200, 200);
        add(resultField);

        scrambleButton = new JButton("Scramble");
        scrambleButton.setBounds(300, 275, 200, 50);
        add(scrambleButton);
        scrambleButton.addActionListener(this);

        sortBubbleButton = new JButton("Bubble Sort");
        sortBubbleButton.setBounds(300, 350, 200, 50);
        add(sortBubbleButton);
        sortBubbleButton.addActionListener(this);

        sortMergeButton = new JButton("Merge Sort");
        sortMergeButton.setBounds(300, 425, 200, 50);
        add(sortMergeButton);
        sortMergeButton.addActionListener(this);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    private void setResultText(String text) {
        resultField.setText("<html><div style=\"width:200px;height:200px;background-color:white;\"><p><br><br>" + text
                + "<p></div></html>");
    }

    private void readStudents() {
        try {
            Scanner studentScanner = new Scanner(new File("students.txt"), Charset.defaultCharset());

            while (studentScanner.hasNextLine()) {
                Student studentToAdd = new Student(studentScanner.nextLine());
                if (studentList.size() > 0) {
                    int index = binarySearch(studentToAdd, 0, studentList.size());
                    studentList.add(index, studentToAdd);
                } else {
                    studentList.add(studentToAdd);
                }
            }

            sorted = true;
            showStudents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showStudents() {
        studentTextArea.setText("");
        for (Student student : studentList) {
            studentTextArea.append(student.toString() + "\n");
        }
    }

    private int binarySearch(Student target, int startIndex, int endIndex) {
        passes++;
        if (startIndex == endIndex - 1) {
            if (studentList.get(startIndex).lastName().toLowerCase().compareTo(target.lastName().toLowerCase()) > 0) {
                return startIndex;
            } else {
                return endIndex;
            }
        }
        int midIndex = (startIndex + endIndex) / 2;

        if (studentList.get(midIndex).lastName().toLowerCase().compareTo(target.lastName().toLowerCase()) > 0) {
            endIndex = midIndex;
            return binarySearch(target, startIndex, endIndex);
        } else {
            startIndex = midIndex;
            return binarySearch(target, startIndex, endIndex);
        }
    }

    private int linearSearch(Student target) {
        for (int i = 0; i < studentList.size(); i++) {
            passes++;
            if (sorted ? studentList.get(i).lastName().compareToIgnoreCase(target.lastName()) > 0
                    : studentList.get(i).lastName().equalsIgnoreCase(target.lastName())) {
                return i;
            }
        }

        return sorted ? studentList.size() : -1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchBinaryButton) {
            passes = 0;
            int index = binarySearch(new Student("_ " + searchContents.getText()), 0, studentList.size());
            boolean isInList = index > 0
                    ? studentList.get(index - 1).lastName().equalsIgnoreCase(searchContents.getText())
                    : false;
            setResultText("<h4>Searched (Binary)<br><br>Index: " + (isInList ? index - 1 : index) + "<br><br>Passes: "
                    + passes
                    + "<br><br>Found: <span style=\"color:"
                    + (isInList ? "green\">Yes</span><br><br>Student: " + studentList.get(index - 1)
                            : "red\">No</span>")
                    + "</h4>");
        } else if (e.getSource() == searchLinearButton) {
            passes = 0;
            int index = linearSearch(new Student("_ " + searchContents.getText()));
            boolean isInList = sorted ? (index > 0
                    ? studentList.get(index - 1).lastName().equalsIgnoreCase(searchContents.getText())
                    : false) : index != -1;
            setResultText("<h4>Searched (Linear)<br><br>Index: " + (isInList ? (sorted ? index - 1 : index) : index)
                    + "<br><br>Passes: "
                    + passes
                    + "<br><br>Found: <span style=\"color:"
                    + (isInList ? "green\">Yes</span><br><br>Student: " + studentList.get(sorted ? index - 1 : index)
                            : "red\">No</span>")
                    + "</h4>");
        } else if (e.getSource() == scrambleButton) {
            passes = 0;
            scramble();
            showStudents();
            setResultText("<h1>Scrambled<br><br>Passes: " + passes + "</h1>");
        } else if (e.getSource() == sortBubbleButton) {
            passes = 0;
            bubbleSort();
            showStudents();
            setResultText("<h1>Sorted (Bubble)<br><br>Passes: " + passes + "</h1>");
        } else if (e.getSource() == sortMergeButton) {
            passes = 0;
            mergeSort(0, studentList.size());
            showStudents();
            setResultText("<h1>Sorted (Merge)<br><br>Passes: " + passes + "</h1>");
        }
    }

    private void mergeSort(int startIndex, int endIndex) {
        if (endIndex - startIndex <= 1) {
            return;
        }

        int midIndex = (startIndex + endIndex) / 2;

        mergeSort(startIndex, midIndex);
        mergeSort(midIndex, endIndex);
        int firstI = startIndex;
        int secondI = midIndex;
        int i = 0;
        Student[] studArr = new Student[endIndex - startIndex];
        while (firstI < midIndex && secondI < endIndex) {
            passes++;
            if (studentList.get(firstI).lastName().compareToIgnoreCase(studentList.get(secondI).lastName()) < 0) {
                studArr[i] = studentList.get(firstI);
                firstI++;
                i++;
            } else {
                studArr[i] = studentList.get(secondI);
                secondI++;
                i++;
            }
        }
        for (; firstI < midIndex;) {
            passes++;
            studArr[i] = studentList.get(firstI);
            firstI++;
            i++;
        }
        for (; secondI < endIndex;) {
            passes++;
            studArr[i] = studentList.get(secondI);
            secondI++;
            i++;
        }

        for (i = 0; i < studArr.length; i++) {
            studentList.set(startIndex + i, studArr[i]);
        }
    }

    private void bubbleSort() {
        for (int i = studentList.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                passes++;
                if (studentList.get(j).lastName().compareToIgnoreCase(studentList.get(j + 1).lastName()) > 0) {
                    swap(j, j + 1);
                }
            }
        }

        sorted = true;
    }

    private void scramble() {
        sorted = false;

        for (int i = 0; i < studentList.size(); i++) {
            passes++;
            int randIndex = (int) (Math.random() * studentList.size());
            swap(i, randIndex);
        }
    }

    private void swap(int i, int j) {
        Student temp = studentList.get(i);
        studentList.set(i, studentList.get(j));
        studentList.set(j, temp);
    }
}
