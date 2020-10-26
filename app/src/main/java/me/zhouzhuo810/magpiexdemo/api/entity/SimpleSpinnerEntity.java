package me.zhouzhuo810.magpiexdemo.api.entity;

import me.zhouzhuo810.magpiex.ui.widget.intef.ISpinnerData;

public class SimpleSpinnerEntity implements ISpinnerData {
    
    private String id;
    private String name;
    
    public SimpleSpinnerEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public CharSequence getSpItemName() {
        return name;
    }
}
