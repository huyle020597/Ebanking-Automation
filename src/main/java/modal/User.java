package modal;

public class User {
    String userId;
    String password;
    String bankAccount;

    public User(String userId, String password, String bankAccount) {
        this.userId = userId;
        this.password = password;
        this.bankAccount = bankAccount;
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


}
