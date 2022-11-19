package cvut.fit.matsnnik.front.models;

import java.util.Objects;

public class SessionActualDTO {
    private Long plannedStart;
    private Long plannedEnd;
    private String name;
    private Long doctor;
    private String patient;

    public SessionActualDTO() {
    }

    public SessionActualDTO(Long plannedStart, Long plannedEnd, String name, Long doctor, String patient) {
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

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

}
