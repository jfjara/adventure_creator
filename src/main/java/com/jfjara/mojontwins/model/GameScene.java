package com.jfjara.mojontwins.model;

import com.jfjara.mojontwins.messages.MessageQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameScene {

    private String text;
    private GameScene north;
    private GameScene south;
    private GameScene east;
    private GameScene west;

    private GameScene up;

    private GameScene down;

    private GameScene in;

    private GameScene out;

    private List<GameObject> objects = new ArrayList<>();

    public GameScene(String text, GameScene north, GameScene south, GameScene east, GameScene west, GameScene up, GameScene down) {
        this.text = text;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.up = up;
        this.down = down;
    }

    public void addObject(GameObject gameObject) {
        objects.add(gameObject);
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public GameScene getNorth() {
        return north;
    }

    public void setNorth(GameScene north) {
        this.north = north;
        if (north.getSouth() == null)
            north.setSouth(this);
    }

    public GameScene getSouth() {
        return south;
    }

    public void setSouth(GameScene south) {
        this.south = south;
        if (south.getNorth() == null)
            south.setNorth(this);
    }

    public GameScene getEast() {
        return east;
    }

    public void setEast(GameScene east) {
        this.east = east;
        if (east.getWest() == null)
            east.setWest(this);
    }

    public GameScene getWest() {
        return west;
    }

    public void setWest(GameScene west) {
        this.west = west;
        if (west.getEast() == null)
            west.setEast(this);
    }

    public GameScene getUp() {
        return up;
    }

    public void setUp(GameScene up) {
        this.up = up;
        if (up.getDown() == null) {
            up.setDown(this);
        }
    }

    public GameScene getDown() {
        return down;
    }

    public void setDown(GameScene down) {
        this.down = down;
        if (down.getUp() == null) {
            down.setUp(this);
        }
    }

    public void removeObject(GameObject objectFound) {
        objects.remove(objectFound);
    }

    public void printObjects() {
        List<GameObject> insideObjects = objects.stream()
                .filter(o -> o.isCanOpen() && o.isOpened())
                .flatMap(o -> o.getObjectsInside().stream())
                .collect(Collectors.toList());

        List<GameObject> temp = Stream.concat(objects.stream(), insideObjects.stream()).collect(Collectors.toList());

        temp.forEach(o -> MessageQueue.getInstance().enqueue(o.getName()));
    }

    public GameScene getIn() {
        return in;
    }

    public void setIn(GameScene in) {
        this.in = in;
        if (in.getOut() == null) {
            in.setOut(this);
        }
    }

    public GameScene getOut() {
        return out;
    }

    public void setOut(GameScene out) {
        this.out = out;
        if (out.getIn() == null) {
            out.setIn(this);
        }
    }

    public void setObjects(List<GameObject> objects) {
        this.objects = objects;
    }
}
