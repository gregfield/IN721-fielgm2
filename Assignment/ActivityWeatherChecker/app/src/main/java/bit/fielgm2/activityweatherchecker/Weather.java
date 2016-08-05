package bit.fielgm2.activityweatherchecker;

/**
 * Created by Greg on 30/05/2016.
 */
public class Weather
{
    private double currentTemp;
    private double minTemp;
    private double maxTemp;
    private double humidity;
    private double pressure;

    //========================
    //Getters and Setters
    //========================
    public double getCurrentTemp(){return currentTemp;}
    public double getMinTemp(){return minTemp;}
    public double getMaxTemp(){return maxTemp;}
    public double getHumidity(){return humidity;}
    public double getPressure(){return pressure;}

    public void setCurrentTemp(double currentTemp){this.currentTemp = currentTemp;}
    public void setMinTemp(double minTemp){this.minTemp = minTemp;}
    public void setMaxTemp(double maxTemp){this.maxTemp = maxTemp;}
    public void setHumidity(double humidity){this.humidity = humidity;}
    public void setPressure(double pressure){this.pressure = pressure;}
}
