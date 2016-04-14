package bit.fielgm2.lastfmwebservice;

/**
 * Created by Greg on 14/04/2016.
 */
public class Artists
{
    private String name;
    private int listnerCount;

    public Artists(String name, int listnerCount)
    {
        this.name = name;
        this.listnerCount = listnerCount;
    }

    public String toString()
    {
        return String.format("%1$-20s %2$s", name, listnerCount);
    }

    public String getName() {
        return name;
    }

    public int getListnerCount() {
        return listnerCount;
    }
}
