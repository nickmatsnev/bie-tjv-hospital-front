package cvut.fit.matsnnik.front.models;

public class DoctorLoginModel {
    private int did;
    private String password;

    public DoctorLoginModel(int did, String password) {
        this.did = did;
        this.password = password;
    }

    public DoctorLoginModel() {
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
