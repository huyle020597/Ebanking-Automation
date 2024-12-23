package modal;

import java.util.Date;
import java.util.Objects;

public class Transaction {
    String mainAccount;
    Date transferDate;
    double transferAmount;

    public Transaction(String mainAccount, Date transferDate, double transferAmount) {
        this.mainAccount = mainAccount;
        this.transferDate = transferDate;
        this.transferAmount = transferAmount;
    }

    public String getMainAccount() {
        return mainAccount;
    }

    public void setMainAccount(String mainAccount) {
        this.mainAccount = mainAccount;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(transferAmount, that.transferAmount) == 0 && Objects.equals(mainAccount, that.mainAccount) && Objects.equals(transferDate, that.transferDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainAccount, transferDate, transferAmount);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "mainAccount='" + mainAccount + '\'' +
                ", transferDate=" + transferDate +
                ", transferAmount=" + transferAmount +
                '}';
    }
}
