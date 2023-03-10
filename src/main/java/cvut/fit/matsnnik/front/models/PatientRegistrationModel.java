package cvut.fit.matsnnik.front.models;

public class PatientRegistrationModel {
    private Integer pid, age;
    private String email, name, surname, password, rPassword;

    public PatientRegistrationModel() {
    }

    public PatientRegistrationModel(Integer pid, Integer age, String email, String name, String surname, String password, String rPassword) {
        this.pid = pid;
        this.age = age;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.rPassword = rPassword;
    }

    public String getrPassword() {
        return rPassword;
    }

    public void setrPassword(String rPassword) {
        this.rPassword = rPassword;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
