package me.zhouzhuo810.magpiex.ui.widget.map.entity;

import java.util.List;

/**
 * 地图数据
 */
public class MapEntity {

    public int mapWidth;//地图宽度
    public int mapHigh;//地图高度
    private List<MapTextRectEntity> mapData;

    public MapEntity(int mapHigh, int mapWidth, List<MapTextRectEntity> mapData) {
        this.mapData = mapData;
        this.mapHigh = mapHigh;
        this.mapWidth = mapWidth;
    }

    public List<MapTextRectEntity> getMapData() {
        return mapData;
    }

    public void setMapData(List<MapTextRectEntity> mapData) {
        this.mapData = mapData;
    }

    public int getMapHigh() {
        return mapHigh;
    }

    public void setMapHigh(int mapHigh) {
        this.mapHigh = mapHigh;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public void clear() {
        mapWidth = 0;
        mapHigh = 0;
        mapData.clear();
    }
}
