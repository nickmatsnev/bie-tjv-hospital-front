package cvut.fit.matsnnik.front.models;

public class DoctorRegistrationModel {
    private int did;
    private String name;
    private String surname;
    private String dType;
    private String password;

    public DoctorRegistrationModel() {
    }

    public DoctorRegistrationModel(int did, String name, String surname, String dType, String password) {
        this.did = did;
        this.name = name;
        this.surname = surname;
        this.dType = dType;
        this.password = password;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getdType() {
        return dType;
    }

    public void setdType(String dType) {
        this.dType = dType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
