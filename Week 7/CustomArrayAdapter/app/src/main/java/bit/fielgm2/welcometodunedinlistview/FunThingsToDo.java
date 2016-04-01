package bit.fielgm2.welcometodunedinlistview;

import android.graphics.drawable.Drawable;

/**
 * Created by Greg on 1/04/2016.
 */
public class FunThingsToDo
{
    private String funThingToDO;
    private String image;

    public FunThingsToDo(String funThingToDO, String image)
    {
        this.funThingToDO = funThingToDO;
        this.image = image;
    }

    public String getFunThingToDO() {
        return funThingToDO;
    }

    public void setFunThingToDO(String funThingToDO) {
        this.funThingToDO = funThingToDO;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
