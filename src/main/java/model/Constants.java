package model;

public class Constants {
    public static final double INTERNAL_TRANSACTION_FEE = 1100;
    public static final double EXTERNAL_TRANSACTION_FEE = 3300;
    public static final double WITHDRAW_FEE = 3000;
    public static final String USER_URL = "http://14.176.232.213:8080/EBankingWebsite/faces/bank.xhtml#";
    public static final String ADMIN_URL = "http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml";
    public static final String YOPMAIL_URL = "https://yopmail.com";

    //Test data , user account

    public static final User USER_1 = new User("huyle020597","Maddie123@","huyle020597@yopmail.com");

    public static final User USER_2 = new User("huyle0205971","Maddie123@","huyle0205971@yopmail.com");

    //Test data - External Bank Account
    public static final String EXTERNAL_ACCOUNT_NUMBER = "10001111";
    public static final String EXTERNAL_NAME = "Nguyen Van A";

    //Test data , admin account
    public  static final User ADMIN = new User("1","admin",null);

}
