package cvut.fit.matsnnik.front.models;

public class PatientDTO {
    private Integer pid;
    private String email;
    private String name;
    private String surname;
    private Integer age;
    private String password;

    public PatientDTO(Integer pid, String email, String name, String surname, Integer age, String password) {
        this.pid = pid;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.password = password;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
