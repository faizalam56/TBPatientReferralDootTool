package zmq.com.tbpatientreferraldoottool.model;

/**
 * Created by zmq162 on 18/11/16.
 */
public class DootAccount {
    private String dootAccountNo;
    private String dootAccountBalance;
    private  String dootAccountOpenDate;

    public String getDootAccountNo() {
        return dootAccountNo;
    }

    public void setDootAccountNo(String dootAccountNo) {
        this.dootAccountNo = dootAccountNo;
    }

    public String getDootAccountBalance() {
        return dootAccountBalance;
    }

    public void setDootAccountBalance(String dootAccountBalance) {
        this.dootAccountBalance = dootAccountBalance;
    }

    public String getDootAccountOpenDate() {
        return dootAccountOpenDate;
    }

    public void setDootAccountOpenDate(String dootAccountOpenDate) {
        this.dootAccountOpenDate = dootAccountOpenDate;
    }
}
