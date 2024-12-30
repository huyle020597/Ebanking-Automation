package model;

public class User {
    private String userId;
    private String password;
    private String password2;
    private String emailAddress;
    private String bankAccount;
    private String fullName;
    private String phoneNumber;
    private String dob;
    private String city;
    private String cmnd;
    private String email;

    public User(String userId, String password, String emailAddress) {
        this.userId = userId;
        this.password = password;
        this.emailAddress = emailAddress;
    }

    public User(String userId, String password, String emailAddress, String bankAccount) {
        this.userId = userId;
        this.password = password;
        this.emailAddress = emailAddress;
        this.bankAccount = bankAccount;
    }

    public User(String userId, String password, String password2, String fullName, String phoneNumber,
                String dob, String city, String cmnd, String emailAddress) {
        this.userId = userId;
        this.password = password;
        this.password2 = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.city = city;
        this.cmnd = cmnd;
        this.emailAddress = emailAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }
}
