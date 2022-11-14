package cvut.fit.matsnnik.front.models;

public class SessionModelReg {
    private String plannedStart;
    private String plannedEnd;
    private String name;
    private Long doctor;
    private Long patient;

    public SessionModelReg() {
    }

    public SessionModelReg(String plannedStart, String plannedEnd, String name, Long doctor, Long patient) {
        this.plannedStart = plannedStart;
        this.plannedEnd = plannedEnd;
        this.name = name;
        this.doctor = doctor;
        this.patient = patient;
    }

    public String getPlannedStart() {
        return plannedStart;
    }

    public void setPlannedStart(String plannedStart) {
        this.plannedStart = plannedStart;
    }

    public String getPlannedEnd() {
        return plannedEnd;
    }

    public void setPlannedEnd(String plannedEnd) {
        this.plannedEnd = plannedEnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDoctor() {
        return doctor;
    }

    public void setDoctor(Long doctor) {
        this.doctor = doctor;
    }

    public Long getPatient() {
        return patient;
    }

    public void setPatient(Long patient) {
        this.patient = patient;
    }
}
