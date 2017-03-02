package io.github.t3r1jj.pbmap.view.routing;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.qozix.tileview.geom.CoordinateTranslater;
import com.qozix.tileview.paths.CompositePathView;

import java.util.ArrayList;
import java.util.List;

import io.github.t3r1jj.pbmap.R;
import io.github.t3r1jj.pbmap.model.map.Coordinate;
import io.github.t3r1jj.pbmap.model.map.route.Edge;
import io.github.t3r1jj.pbmap.model.map.route.RouteGraph;
import io.github.t3r1jj.pbmap.view.MapView;

public class Route implements RemovableView {
    private List<CompositePathView.DrawablePath> drawablePaths = new ArrayList<>();

    public Route(MapView mapView, RouteGraph routeGraph) {
        Paint paint = getPaint(Color.GREEN, mapView.getContext().getResources().getDimension(R.dimen.route_stroke_width));
        CoordinateTranslater coordinateTranslater = mapView.getCoordinateTranslater();

        for (Edge edge : routeGraph.getPaths()) {
            CompositePathView.DrawablePath drawablePath = new CompositePathView.DrawablePath();
            List<double[]> positions = new ArrayList<>();
            positions.add(new double[]{edge.getStart().lng, edge.getStart().lat});
            positions.add(new double[]{edge.getEnd().lng, edge.getEnd().lat});
            drawablePath.path = coordinateTranslater.pathFromPositions(positions, false);
            drawablePath.paint = paint;
            drawablePaths.add(drawablePath);
        }
    }

    public Route(MapView mapView, List<Coordinate> coordinates) {
        if (coordinates.isEmpty()) {
            return;
        }
        Resources resources = mapView.getContext().getResources();
        Paint paint = getPaint(resources.getColor(R.color.route), resources.getDimension(R.dimen.route_stroke_width));
        CoordinateTranslater coordinateTranslater = mapView.getCoordinateTranslater();

        CompositePathView.DrawablePath drawablePath = new CompositePathView.DrawablePath();
        List<double[]> positions = new ArrayList<>();
        for (Coordinate coordinate : coordinates) {
            positions.add(new double[]{coordinate.lng, coordinate.lat});
        }
        drawablePath.path = coordinateTranslater.pathFromPositions(positions, false);
        drawablePath.paint = paint;
        drawablePaths.add(drawablePath);
    }

    @NonNull
    private Paint getPaint(int color, float strokeWidth) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    @Override
    public void removeFromMap(MapView pbMapView) {
        CompositePathView compositePathView = pbMapView.getCompositePathView();
        for (CompositePathView.DrawablePath drawablePath : drawablePaths) {
            compositePathView.removePath(drawablePath);
        }
    }

    @Override
    public void addToMap(MapView pbMapView) {
        CompositePathView compositePathView = pbMapView.getCompositePathView();
        for (CompositePathView.DrawablePath drawablePath : drawablePaths) {
            compositePathView.addPath(drawablePath);
        }
    }
}
