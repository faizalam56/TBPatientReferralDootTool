package zmq.com.tbpatientreferraldoottool.model;

import java.io.Serializable;

/**
 * Created by zmq162 on 10/11/16.
 */
public class ScreenRegiDtls implements Serializable{
    private String patientName;
    private String patientAddress;
    private String patientPhoneNo;
    private String patientAge;
    private String patientSex;
    private String patientVillage;
    private String patientVillageId;
    private String patientBlock;
    private String patientBlockId;
    private String patientDmc;
    private String patientDmcId;
    private String patientDistrict;
    private String patientState;
    private String patientCountry;
    private String patientQuestionsId;
    private String patientScreenResponse;
    private String screenDateTime;
    private String patientTestStatus;
    private String patientTestResult;
    private int patientScreenAverage;
    private String checkDataFromLocalServer;
    private String longitude;
    private String latitude;

    @Override
    public String toString() {
        return "answer"+ getPatientScreenResponse();
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getPatientVillage() {
        return patientVillage;
    }

    public void setPatientVillage(String patientVillage) {
        this.patientVillage = patientVillage;
    }

    public String getPatientBlock() {
        return patientBlock;
    }

    public void setPatientBlock(String patientBlock) {
        this.patientBlock = patientBlock;
    }

    public String getPatientDmc() {
        return patientDmc;
    }

    public void setPatientDmc(String patientDmc) {
        this.patientDmc = patientDmc;
    }

    public String getPatientDistrict() {
        return patientDistrict;
    }

    public void setPatientDistrict(String patientDistrict) {
        this.patientDistrict = patientDistrict;
    }

    public String getPatientCountry() {
        return patientCountry;
    }

    public void setPatientCountry(String patientCountry) {
        this.patientCountry = patientCountry;
    }



    public String getPatientState() {
        return patientState;
    }

    public void setPatientState(String patientState) {
        this.patientState = patientState;
    }

    public String getPatientVillageId() {
        return patientVillageId;
    }

    public void setPatientVillageId(String patientVillageId) {
        this.patientVillageId = patientVillageId;
    }

    public String getPatientBlockId() {
        return patientBlockId;
    }

    public void setPatientBlockId(String patientBlockId) {
        this.patientBlockId = patientBlockId;
    }

    public String getPatientDmcId() {
        return patientDmcId;
    }

    public void setPatientDmcId(String patientDmcId) {
        this.patientDmcId = patientDmcId;
    }

    public String getScreenDateTime() {
        return screenDateTime;
    }

    public void setScreenDateTime(String screenDateTime) {
        this.screenDateTime = screenDateTime;
    }

    public String getPatientPhoneNo() {
        return patientPhoneNo;
    }

    public void setPatientPhoneNo(String patientPhoneNo) {
        this.patientPhoneNo = patientPhoneNo;
    }

    public String getPatientScreenResponse() {
        return patientScreenResponse;
    }

    public void setPatientScreenResponse(String patientScreenResponse) {
        this.patientScreenResponse = patientScreenResponse;
    }

    public String getPatientTestStatus() {
        return patientTestStatus;
    }

    public void setPatientTestStatus(String patientTestStatus) {
        this.patientTestStatus = patientTestStatus;
    }

    public String getPatientTestResult() {
        return patientTestResult;
    }

    public void setPatientTestResult(String patientTestResult) {
        this.patientTestResult = patientTestResult;
    }

    public String getPatientQuestionsId() {
        return patientQuestionsId;
    }

    public void setPatientQuestionsId(String patientQuestionsId) {
        this.patientQuestionsId = patientQuestionsId;
    }

    public int getPatientScreenAverage() {
        return patientScreenAverage;
    }

    public void setPatientScreenAverage(int patientScreenAverage) {
        this.patientScreenAverage = patientScreenAverage;
    }

    public String getCheckDataFromLocalServer() {
        return checkDataFromLocalServer;
    }

    public void setCheckDataFromLocalServer(String checkDataFromLocalServer) {
        this.checkDataFromLocalServer = checkDataFromLocalServer;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
