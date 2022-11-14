package cvut.fit.matsnnik.front.models;

import java.util.Objects;

public class SessionModel {
    private Long plannedStart;
    private Long plannedEnd;
    private String name;
    private Long doctor;
    private Long patient;

    public SessionModel() {
    }

    public SessionModel(Long plannedStart, Long plannedEnd, String name, Long doctor, Long patient) {
        this.plannedStart = plannedStart;
        this.plannedEnd = plannedEnd;
        this.name = name;
        this.doctor = doctor;
        this.patient = patient;
    }

    public Long getPlannedStart() {
        return plannedStart;
    }

    public void setPlannedStart(Long plannedStart) {
        this.plannedStart = plannedStart;
    }

    public Long getPlannedEnd() {
        return plannedEnd;
    }

    public void setPlannedEnd(Long plannedEnd) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionModel that = (SessionModel) o;
        return Objects.equals(plannedStart, that.plannedStart) && Objects.equals(plannedEnd, that.plannedEnd) && Objects.equals(name, that.name) && Objects.equals(doctor, that.doctor) && Objects.equals(patient, that.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plannedStart, plannedEnd, name, doctor, patient);
    }

}
