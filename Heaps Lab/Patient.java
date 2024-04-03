import java.util.Date;

public class Patient implements Comparable<Patient> {
    private String name;
    private String illness;
    private String doctorNote;
    private Priority priority;
    private Age ageGroup;
    private Date timestamp;

    public Patient(String _name, String _illness, Priority _priority, Age _ageGroup) {
        name = _name;
        illness = _illness;
        priority = _priority;
        ageGroup = _ageGroup;
        timestamp = new Date();
    }

    @Override
    public int compareTo(Patient o) {
        int comp = priority.compareTo(o.priority);
        if(comp != 0) {
            return comp;
        }
        comp = ageGroup.compareTo(o.ageGroup);
        if(comp != 0) {
            return comp;
        }
        return timestamp.compareTo(o.timestamp);
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nIllness: " + illness + "\nPriority: " + priority + "\nAge: " + ageGroup;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Patient p) {
            return p.name.equals(name);
        }
        return false;
    }
    
    public String doctorNote() {
        return doctorNote;
    }

    public void setDoctorNote(String docNote) {
        doctorNote = docNote;
    }

}
