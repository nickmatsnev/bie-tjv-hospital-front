package cvut.fit.matsnnik.front.models;

public class PatientModel {
    private int pid, age;
    private String name, surname, email, password;

    public PatientModel() {
    }

    public PatientModel(int pid, int age, String name, String surname, String email, String password) {
        this.pid = pid;
        this.age = age;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
