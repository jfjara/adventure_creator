package com.jfjara.mojontwins.model;

import com.jfjara.mojontwins.actions.GameActionsEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GamePlayer {

    private GameScene currentScene;
    private List<GameObject> objectsRecollected = new ArrayList<>();

    public GameScene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(GameScene currentScene) {
        this.currentScene = currentScene;
    }

    public void addObject(GameObject gameObject) {
        objectsRecollected.add(gameObject);
    }

    private String getRealName(String objectName, GameObject gameObject) {
        String nameFound = gameObject.getSynonymous().stream()
                .filter(o -> o.equalsIgnoreCase(objectName))
                .findFirst()
                .orElse(null);
        return nameFound != null ? gameObject.getName() : null;
    }
    public GameObject searchObjectInScene(String objectName) {

        List<GameObject> insideObjects = getCurrentScene().getObjects().stream()
                .filter(o -> o.isCanOpen() && o.isOpened())
                .flatMap(o -> o.getObjectsInside().stream())
                .collect(Collectors.toList());

        List<GameObject> temp = Stream.concat(getCurrentScene().getObjects().stream(), insideObjects.stream()).collect(Collectors.toList());

        return temp.stream()
                .filter(o -> o.getName().equalsIgnoreCase(getRealName(objectName, o)))
                .findFirst()
                .orElse(null);
    }

    public GameObject searchObjectInventary(String objectName) {
        return getObjectsRecollected().stream()
                .filter(o -> o.getName().equalsIgnoreCase(getRealName(objectName, o)))
                .findFirst()
                .orElse(null);
    }



    public GameObject searchObjectInAllSites(String objectName) {
        List<GameObject> temp = Stream.concat(getObjectsRecollected().stream(),
                currentScene.getObjects().stream())
                .collect(Collectors.toList());

        List<GameObject> insideObjects = temp.stream()
                .filter(o -> o.isCanOpen() && o.isOpened())
                .flatMap(o -> o.getObjectsInside().stream())
                .collect(Collectors.toList());

        temp = Stream.concat(temp.stream(), insideObjects.stream()).collect(Collectors.toList());


        return temp.stream()
                .filter(o -> {
                    String realName = getRealName(objectName, o);
                    if (o.getName().equalsIgnoreCase(realName)) {
                        return true;
                    }
                    return false;
                })
                .findFirst()
                .orElse(null);
    }

    public List<GameObject> getObjectsRecollected() {
        return objectsRecollected;
    }

    private boolean hasMoveScene(GameScene scene) {
        if (scene != null) {
            currentScene = scene;
            return true;
        }
        return false;
    }

    public boolean goTo(GameActionsEnum direction) {
        switch (direction) {
            case ENTRAR -> {
                return hasMoveScene(currentScene.getIn());
            }
            case SALIR -> {
                return hasMoveScene(currentScene.getOut());
            }
            case SUBIR -> {
                return hasMoveScene(currentScene.getUp());
            }
            case BAJAR -> {
                return hasMoveScene(currentScene.getDown());
            }
            case N -> {
                return hasMoveScene(currentScene.getNorth());
            }
            case S -> {
                return hasMoveScene(currentScene.getSouth());
            }
            case E -> {
                return hasMoveScene(currentScene.getEast());
            }
            case W -> {
                return hasMoveScene(currentScene.getWest());
            }
            default -> {
                return false;
            }
        }
    }
}
