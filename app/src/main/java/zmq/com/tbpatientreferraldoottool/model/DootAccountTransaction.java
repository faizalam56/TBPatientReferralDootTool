package zmq.com.tbpatientreferraldoottool.model;

/**
 * Created by zmq162 on 21/11/16.
 */
public class DootAccountTransaction {
    private String totalAmount;
    private String paidAmount;
    private String dueAmount;

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }
}
