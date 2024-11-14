import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen extends JFrame implements ActionListener {
    private MinHeap<Patient> patients;
    private CustomListModel<Patient, String> patientListModel;
    private JList<String> patientList;

    private PlaceholderTextField patientName;
    private PlaceholderTextField patientIll;
    private JComboBox<Priority> patientPriority;
    private JComboBox<Age> patientAge;

    private JButton addPatient;
    private JButton searchPatient;
    private JButton updatePatient;

    private JButton switchViews;

    private ArrayList<Patient> dischargedPatients;
    private CustomListModel<Patient, String> dischargedPatientsListModel;
    private JList<String> dischargedPatientList;

    private JButton dischargePatient;
    private JLabel topPatient;
    private PlaceholderTextField note;

    private JComponent[] doctorComps;
    private JComponent[] nurseComps;

    public Screen() {
        setLayout(null);

        patients = new MinHeap<>();
        patientListModel = new CustomListModel<>(patients, e -> {
            return "<html>" + e.toString().replace("\n", "<br>") + "</html>";
        });
        patientList = new JList<>(patientListModel);
        patientList.setBounds(50, 150, 200, 500);
        add(patientList);
        patientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        patientName = new PlaceholderTextField("Patient Name");
        patientName.setBounds(300, 50, 200, 50);
        add(patientName);

        patientIll = new PlaceholderTextField("Patient Illness");
        patientIll.setBounds(300, 150, 200, 50);
        add(patientIll);

        patientPriority = new JComboBox<>(new Priority[] { Priority.HIGH, Priority.MEDIUM, Priority.LOW });
        patientPriority.setBounds(300, 250, 200, 50);
        add(patientPriority);

        patientAge = new JComboBox<>(new Age[] { Age.ADULT, Age.CHILD });
        patientAge.setBounds(300, 350, 200, 50);
        add(patientAge);

        addPatient = new JButton("Add patient");
        addPatient.setBounds(300, 450, 200, 50);
        add(addPatient);
        addPatient.addActionListener(this);

        searchPatient = new JButton("Search for patient");
        searchPatient.setBounds(300, 550, 200, 50);
        add(searchPatient);
        searchPatient.addActionListener(this);

        updatePatient = new JButton("Update patient");
        updatePatient.setBounds(300, 650, 200, 50);
        add(updatePatient);
        updatePatient.addActionListener(this);

        nurseComps = new JComponent[] { patientList, patientName, patientIll, patientPriority, patientAge, addPatient,
                searchPatient, updatePatient };

        switchViews = new JButton("Doctor View");
        switchViews.setBounds(50, 50, 200, 50);
        add(switchViews);
        switchViews.addActionListener(this);

        dischargedPatients = new ArrayList<>();
        dischargedPatientsListModel = new CustomListModel<>(dischargedPatients, e -> {
            return "<html>" + e.toString().replace("\n", "<br>") + "<br>Doctor's Note: " + e.doctorNote() + "</html>";
        });
        dischargedPatientList = new JList<>(dischargedPatientsListModel);
        dischargedPatientList.setBounds(50, 150, 200, 500);
        add(dischargedPatientList);

        topPatient = new JLabel();
        topPatient.setBounds(300, 50, 200, 100);
        add(topPatient);

        note = new PlaceholderTextField("Doctor's Note");
        note.setBounds(300, 200, 200, 50);
        add(note);

        dischargePatient = new JButton("Discharge patient");
        dischargePatient.setBounds(300, 300, 200, 50);
        add(dischargePatient);
        dischargePatient.addActionListener(this);

        doctorComps = new JComponent[] { dischargePatient, note, topPatient, dischargedPatientList };
        for (JComponent j : doctorComps) {
            j.setVisible(false);
        }

        setSize(600, 750);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPatient) {
            patients.add(new Patient(patientName.getText(),
                    patientIll.getText(),
                    (Priority) patientPriority.getSelectedItem(),
                    (Age) patientAge.getSelectedItem()));

            patientListModel.update();

            topPatient.setText(patientListModel.convert(patients.peek()));
        } else if (e.getSource() == searchPatient) {
            int index = patientListModel.getIndex(new Patient(patientName.getText(),
                    patientIll.getText(),
                    (Priority) patientPriority.getSelectedItem(),
                    (Age) patientAge.getSelectedItem()));
            patientList.setSelectedIndex(index);
        } else if (e.getSource() == updatePatient) {
            if (patientList.getSelectedValue() != null) {
                patients.remove(patientListModel.getBackingElementAt(patientList.getSelectedIndex()));
                patients.add(new Patient(patientName.getText(),
                        patientIll.getText(),
                        (Priority) patientPriority.getSelectedItem(),
                        (Age) patientAge.getSelectedItem()));

                patientListModel.update();
            }
        } else if (e.getSource() == switchViews) {
            for (JComponent j : doctorComps) {
                j.setVisible(switchViews.getText().equals("Doctor View"));
            }
            for (JComponent j : nurseComps) {
                j.setVisible(switchViews.getText().equals("Nurse View"));
            }
            switchViews.setText(switchViews.getText().equals("Doctor View") ? "Nurse View" : "Doctor View");
        } else if (e.getSource() == dischargePatient) {
            Patient p = patients.poll();
            p.setDoctorNote(note.getText());
            dischargedPatients.add(p);
            patientListModel.update();
            dischargedPatientsListModel.update();
            if (patients.isEmpty()) {
                topPatient.setText("No more patients!");
            } else {
                topPatient.setText(patientListModel.convert(patients.peek()));
            }

        }
    }
}