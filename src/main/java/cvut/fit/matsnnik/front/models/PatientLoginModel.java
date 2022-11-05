package cvut.fit.matsnnik.front.models;

public class PatientLoginModel {
    private String email;
    private String password;

    public PatientLoginModel() {
    }

    public PatientLoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
