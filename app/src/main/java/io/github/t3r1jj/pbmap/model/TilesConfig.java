package io.github.t3r1jj.pbmap.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "tiles_config")
class TilesConfig {
    @Attribute
    String path;
    @Attribute
    float zoom;
    @Attribute
    int width;
    @Attribute
    int height;
}
