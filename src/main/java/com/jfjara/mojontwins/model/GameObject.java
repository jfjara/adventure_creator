package com.jfjara.mojontwins.model;

import java.util.LinkedList;
import java.util.List;

public class GameObject {

    private String name;
    //poner nombres sinonimos
    private List<String> synonymous = new LinkedList<>();
    private String description;
    private boolean canPickUp;
    private String messageCannotPickUp;

    private boolean canTurnOn;
    private boolean isTurnOn;
    private String messageTurnOn;

    private boolean canRead;
    private String readMessage;

    private boolean canOpen;
    private boolean isOpened;
    private List<GameObject> objectsInside = new LinkedList<>();

    public GameObject(String name, String description, boolean canPickUp, String messageCannotPickUp) {
        this.name = name;
        this.description = description;
        this.canPickUp = canPickUp;
        this.messageCannotPickUp = messageCannotPickUp;
        addSynonymous(new String[] { name });
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCanPickUp() {
        return canPickUp;
    }

    public void setCanPickUp(boolean canPickUp) {
        this.canPickUp = canPickUp;
    }

    public String getMessageCannotPickUp() {
        return messageCannotPickUp;
    }

    public void setMessageCannotPickUp(String messageCannotPickUp) {
        this.messageCannotPickUp = messageCannotPickUp;
    }

    public boolean isCanTurnOn() {
        return canTurnOn;
    }

    public void setCanTurnOn(boolean canTurnOn) {
        this.canTurnOn = canTurnOn;
    }

    public boolean isTurnOn() {
        return isTurnOn;
    }

    public void setTurnOn(boolean turnOn) {
        isTurnOn = turnOn;
    }

    public String getMessageTurnOn() {
        return messageTurnOn;
    }

    public void setMessageTurnOn(String messageTurnOn) {
        this.messageTurnOn = messageTurnOn;
    }

    public boolean isCanRead() {
        return canRead;
    }

    public void setCanRead(boolean canRead) {
        this.canRead = canRead;
    }

    public String getReadMessage() {
        return readMessage;
    }

    public void setReadMessage(String readMessage) {
        this.readMessage = readMessage;
    }

    public List<String> getSynonymous() {
        return synonymous;
    }

    public void addSynonymous(String... synonymous) {
        if (synonymous != null) {
            for (int i = 0; i < synonymous.length; i++) {
                this.synonymous.add(synonymous[i]);
            }
        }
    }

    public void setSynonymous(List<String> synonymous) {
        this.synonymous = synonymous;
    }

    public boolean isCanOpen() {
        return canOpen;
    }

    public void setCanOpen(boolean canOpen) {
        this.canOpen = canOpen;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public List<GameObject> getObjectsInside() {
        return objectsInside;
    }

    public void setObjectsInside(List<GameObject> objectsInside) {
        this.objectsInside = objectsInside;
    }

    public void addObjectInside(List<GameObject> objects) {
        objectsInside.addAll(objects);
    }
}
