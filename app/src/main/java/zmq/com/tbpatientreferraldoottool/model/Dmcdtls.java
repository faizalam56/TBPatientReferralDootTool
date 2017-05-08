package zmq.com.tbpatientreferraldoottool.model;

/**
 * Created by zmq162 on 8/11/16.
 */
public class Dmcdtls {
    private String dmcId;
    private String dmcName;

    @Override
    public String toString() {
        return "dmcId"+dmcId+"dmcName"+dmcName;
    }

    public String getDmcId() {
        return dmcId;
    }

    public void setDmcId(String dmcId) {
        this.dmcId = dmcId;
    }

    public String getDmcName() {
        return dmcName;
    }

    public void setDmcName(String dmcName) {
        this.dmcName = dmcName;
    }
}
