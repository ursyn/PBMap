package io.github.t3r1jj.pbmap.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import androidx.annotation.Nullable;
import androidx.core.util.ObjectsCompat;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import io.github.t3r1jj.pbmap.R;
import io.github.t3r1jj.pbmap.model.map.Coordinate;
import io.github.t3r1jj.pbmap.model.map.Place;

public class SearchSuggestion {
    private final String placeId;
    private final String mapPath;
    private String mapId;
    private Coordinate coordinate;

    SearchSuggestion(@NotNull String placeId, @NotNull String mapPath) {
        this.placeId = Objects.requireNonNull(placeId);
        this.mapPath = Objects.requireNonNull(mapPath);
    }

    /**
     * @param searchIntent with non-null data and extra mandatory non-null string with {@link SearchManager#EXTRA_DATA_KEY} key
     */
    public SearchSuggestion(@NotNull Intent searchIntent) {
        this.placeId = Objects.requireNonNull(searchIntent.getDataString());
        this.mapPath = Objects.requireNonNull(searchIntent.getStringExtra(SearchManager.EXTRA_DATA_KEY));
    }

    String getMapId() {
        return mapId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getMapPath() {
        return mapPath;
    }

    void setMapId(String mapId) {
        this.mapId = mapId;
    }

    @Nullable
    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setLocationCoordinate(Location location) {
        this.coordinate = new Coordinate(location.getLatitude(), location.getLongitude(), location.getAltitude());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchSuggestion that = (SearchSuggestion) o;

        return placeId.equals(that.placeId)
                && ObjectsCompat.equals(mapId, that.mapId)
                && ObjectsCompat.equals(coordinate, that.coordinate);
    }

    @Override
    public int hashCode() {
        return ObjectsCompat.hash(placeId, mapId, coordinate);
    }

    public String getName(Context context) {
        String translatedName = getNameRes(context);
        if (translatedName == null) {
            return placeId.toUpperCase().replace('_', ' ').trim();
        }
        return translatedName.replace("\n", " ").trim();
    }

    private String getNameRes(Context context) {
        int resId = getNameResId(context);
        if (resId == 0) {
            return null;
        }
        return context.getString(resId);
    }

    int getNameResId(Context context) {
        String packageName = context.getPackageName();
        return context.getResources().getIdentifier(Place.getResIdString(placeId, Place.NAME_POSTFIX), "string", packageName);
    }

    String getMapName(Context context) {
        if (mapId == null) {
            return context.getString(R.string.map);
        }
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(Place.getResIdString(mapId, Place.NAME_POSTFIX), "string", packageName);
        if (resId == 0) {
            return mapId.toUpperCase().replace('_', ' ').trim();
        }
        return context.getString(resId).replace("\n", " ").trim();
    }
}
