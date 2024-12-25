package model;

public class Constants {
    public static final double INTERNAL_TRANSACTION_FEE = 1100;
    public static final double EXTERNAL_TRANSACTION_FEE = 3300;
    public static final String USER_URL = "http://14.176.232.213:8080/EBankingWebsite/faces/bank.xhtml#";
    public static final String ADMIN_URL = "http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml";
    public static final String YOPMAIL_URL = "https://yopmail.com";

    //Test data , user account
    public static final String USER_ID_1 = "huyle020597";
    public static final String USER_PASSWORD_1 = "Maddie123@";
    public static final String EMAIL_1 = "huyle020597@yopmail.com";

    public static final User USER_1 = new User("huyle020597","Maddie123@","100001284","huyle020597@yopmail.com");


    public static final String USER_ID_2 = "huyle0205971";
    public static final String USER_PASSWORD_2 = "Maddie123@";
    public static final User USER_2 = new User("huyle0205971","Maddie123@","100001298","huyle0205971@yopmail.com");


    //Test data - External Bank Account
    public static final String EXTERNAL_ACCOUNT_NUMBER = "10001111";
    public static final String EXTERNAL_NAME = "Nguyen Van A";

    //Test data , admin account
    public static final String ADMIN_ID = "1";
    public static final String ADMIN_PASSWORD = "admin";


}
