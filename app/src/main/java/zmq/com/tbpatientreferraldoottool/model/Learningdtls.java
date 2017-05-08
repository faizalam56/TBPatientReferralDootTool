package zmq.com.tbpatientreferraldoottool.model;

/**
 * Created by zmq162 on 23/2/17.
 */
public class Learningdtls {

    private String learningAnalytics;

    @Override
    public String toString() {
        return "LearningAnalytics"+learningAnalytics;
    }

    public String getLearningAnalytics() {
        return learningAnalytics;
    }

    public void setLearningAnalytics(String learningAnalytics) {
        this.learningAnalytics = learningAnalytics;
    }
}
