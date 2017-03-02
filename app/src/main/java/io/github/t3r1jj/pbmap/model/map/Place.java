package io.github.t3r1jj.pbmap.model.map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.github.t3r1jj.pbmap.view.PlaceView;

public abstract class Place {
    //TODO: Change name to id and base name on it with res/values.xml, same with description
    @Attribute
    protected String name;
    @Attribute(name = "logo_path", required = false)
    protected String logoPath;
    @ElementList
    protected List<Coordinate> coordinates;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public ImageView createLogo(Context context) {
        if (logoPath != null) {
            try {
                InputStream inputStream = context.getAssets().open(logoPath);
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                ImageView logo = new ImageView(context);
                logo.setImageDrawable(drawable);
                return logo;
            } catch (IllegalArgumentException | IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", shape=" + coordinates +
                '}';
    }

    public Coordinate getCenter() {
        Coordinate center = new Coordinate();
        for (Coordinate coordinate : coordinates) {
            center.lng += coordinate.lng;
            center.lat += coordinate.lat;
            center.alt += coordinate.alt;
        }
        int size = coordinates.size();
        center.lng /= size;
        center.lat /= size;
        center.alt /= size;
        return center;
    }

    abstract public PlaceView createView(Context context);

}
