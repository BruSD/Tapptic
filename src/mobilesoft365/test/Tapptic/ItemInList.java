package mobilesoft365.test.Tapptic;

import android.graphics.drawable.Drawable;

/**
 * Created with IntelliJ IDEA.
 * User: misa
 * Date: 30.05.13
 * Time: 16:37
 * To change this template use File | Settings | File Templates.
 */
public class ItemInList {

    private String name;
    private Drawable image = null;

    public ItemInList(String itemName, Drawable itemImage) {
        this.name = itemName;
        this.image = itemImage;
    }

    public String getName() {
        return this.name;
    }

    public Drawable getImage() {
        return this.image;
    }
}
