package bit.fielgm2.lastfmartistimageapp;

/**
 * Created by Greg on 14/04/2016.
 */
public class Artists
{
    private String name;
    private String imageUrl;
    private int listnerCount;

    public Artists(String name, String imageUrl, int listnerCount)
    {
        this.imageUrl = imageUrl;
        this.name = name;
        this.listnerCount = listnerCount;
    }
    public Artists(String name)
    {
        this.name = name;
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

    public String getImageUrl() {
        return imageUrl;
    }
}
