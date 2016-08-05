package bit.fielgm2.activityweatherchecker;

/**
 * Created by Greg on 2/06/2016.
 */
public class DatabaseRecord
{
    //all the relevant fields for the database records
    private int activityId;
    private String activityName;
    private double latitude;
    private double longitude;
    private String cityName;

    private String date;

    public DatabaseRecord(int activityId, String activityName, double latitude,double longitude,String cityName, String date)
    {
        this.activityId = activityId;
        this.activityName = activityName;
        this.latitude = latitude;
        this.longitude =longitude;
        this.cityName = cityName;
        this.date = date;
    }

    //getters
    public int getActivityId(){return activityId;}
    public String getDate() {return date;}
    public String getActivityName() {return activityName;}
    public double getLatitude() {return latitude;}
    public double getLongitude() {return longitude;}
    public String getCityName() {return cityName;}

    //setters
    public void setActivityId(int activityId){this.activityId = activityId;}
    public void setDate(String date) { this.date = date; }
    public void setActivityName(String activityName) {this.activityName = activityName; }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(double longitude) {this.longitude = longitude;}
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
