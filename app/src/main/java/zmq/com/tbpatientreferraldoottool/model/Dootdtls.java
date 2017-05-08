package zmq.com.tbpatientreferraldoottool.model;

/**
 * Created by zmq162 on 21/10/16.
 */
public class Dootdtls {
    private String dootId;
    private String dootName;
    private String villageId;
    private String villageName;
    private String blockId;
    private String blockName;
    private String districtName;
    private String stateName;
    private String countryName;
    private String loginKey;

    @Override
    public String toString() {
        return "id "+dootId +"  dootName "+dootName+" villageName "+villageName;
    }

    public String getDootId() {
        return dootId;
    }

    public void setDootId(String dootId) {
        this.dootId = dootId;
    }

    public String getDootName() {
        return dootName;
    }

    public void setDootName(String dootName) {
        this.dootName = dootName;
    }

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }
}
