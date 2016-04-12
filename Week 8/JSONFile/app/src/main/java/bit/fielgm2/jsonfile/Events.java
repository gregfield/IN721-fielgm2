package bit.fielgm2.jsonfile;

/**
 * Created by Greg on 12/04/2016.
 */
public class Events
{
    private String eventName;
    private String eventDescription;

    public Events(String name, String description)
    {
        eventName = name;
        eventDescription = description;
    }


    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }
}
