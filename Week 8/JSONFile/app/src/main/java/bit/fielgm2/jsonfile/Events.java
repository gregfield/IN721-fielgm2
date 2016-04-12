package bit.fielgm2.jsonfile;

/**
 * Created by Greg on 12/04/2016.
 */
public class Events
{
    private String eventTitle;
    private String eventDescription;

    public Events(String title, String description)
    {
        eventTitle = title;
        eventDescription = description;
    }


    public String getEventTitle() {
        return eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }
}
