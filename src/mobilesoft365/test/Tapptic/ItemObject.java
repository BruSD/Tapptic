package mobilesoft365.test.Tapptic;

import android.graphics.drawable.Drawable;

/**
 * Created with IntelliJ IDEA.
 * User: MediumMG
 * Date: 30.05.13
 * Time: 20:44
 * To change this template use File | Settings | File Templates.
 */
public class ItemObject {

    private String name;
    private String text;
    private Drawable image = null;

    public ItemObject(String itemName, String itemText, Drawable itemImage) {
        this.name = itemName;
        this.text = itemText;
        this.image = itemImage;
    }

    public String getName() {
        return this.name;
    }

    public String getText() {
        return this.text;
    }

    public Drawable getImage() {
        return this.image;
    }

}
