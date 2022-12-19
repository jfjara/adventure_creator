package com.jfjara.mojontwins.actions;

import com.jfjara.mojontwins.messages.MessageQueue;
import com.jfjara.mojontwins.model.GameObject;
import com.jfjara.mojontwins.model.GamePlayer;

import java.util.List;

public class ActionExecutor {

    public static boolean execute(GameActionsEnum action, GamePlayer player, List<String> objects) {
        switch (action) {
            case N -> {
                boolean success = player.goTo(GameActionsEnum.N);
                if (!success) System.out.println("No puedes ir al Norte");
                return success;
            }
            case S -> {
                boolean success = player.goTo(GameActionsEnum.S);
                if (!success) System.out.println("No puedes ir al Sur");
                return success;
            }
            case E ->  {
                boolean success = player.goTo(GameActionsEnum.E);
                if (!success) System.out.println("No puedes ir al Este");
                return success;
            }
            case W ->  {
                boolean success = player.goTo(GameActionsEnum.W);
                if (!success) System.out.println("No puedes ir al Oeste");
                return success;
            }
            case SUBIR -> {
                boolean success = player.goTo(GameActionsEnum.SUBIR);
                if (!success) System.out.println("No puedes subir a ninguna parte");
                return success;
            }
            case BAJAR -> {
                boolean success = player.goTo(GameActionsEnum.BAJAR);
                if (!success) System.out.println("No puedes bajar a ninguna parte");
                return success;
            }
            case ENTRAR -> {
                boolean success = player.goTo(GameActionsEnum.ENTRAR);
                if (!success) System.out.println("No puedes entrar en ninguna parte");
                return success;
            }
            case SALIR -> {
                boolean success = player.goTo(GameActionsEnum.SALIR);
                if (!success) System.out.println("No puedes salir a ninguna parte");
                return success;
            }
            case ABRIR -> {
                return openObject(player, objects);
            }
            case CERRAR -> {
                return closeObject(player, objects);
            }
            case DEJAR -> {
                return leaveObject(player, objects);
            }
            case LEER -> {
                return readObject(player, objects);
            }
            case VER -> {
                return true;
            }
            case INVENTARIO -> {
                return showInventary(player);
            }
            case APAGAR -> {
                return turnOffObject(player, objects);
            }
            case ENCENDER -> {
                return turnOnObject(player, objects);
            }
            case OBSERVAR -> {
                return examObject(player, objects);
            }
            case COGER -> {
                return catchObject(player, objects);
            }
            case END_GAME -> {
                return false;
            }
            case UNKNOWN -> {
                MessageQueue.getInstance().enqueue("No se a que te refieres");
                return false;
            }
        };
        return false;
    }

    private static boolean openObject(GamePlayer player, List<String> objects) {
        GameObject objectFound = player.searchObjectInAllSites(objects.get(0));
        if (objectFound != null) {
            if (objectFound.isCanOpen()) {
                if (objectFound.isOpened()) {
                    MessageQueue.getInstance().enqueue("Ya esta abierto");
                } else {
                    MessageQueue.getInstance().enqueue("Hecho.");
                    objectFound.setOpened(true);
                }
            } else {
                MessageQueue.getInstance().enqueue("No puedes abrir eso");
            }
        } else {
            MessageQueue.getInstance().enqueue("No encuentras lo que quieres abrir");
        }
        return false;
    }

    private static boolean closeObject(GamePlayer player, List<String> objects) {
        GameObject objectFound = player.searchObjectInAllSites(objects.get(0));
        if (objectFound != null) {
            if (objectFound.isCanOpen()) {
                if (!objectFound.isOpened()) {
                    MessageQueue.getInstance().enqueue("Ya esta cerrado");
                } else {
                    MessageQueue.getInstance().enqueue("Hecho.");
                    objectFound.setOpened(false);
                }
            } else {
                MessageQueue.getInstance().enqueue("No puedes cerrar eso");
            }
        } else {
            MessageQueue.getInstance().enqueue("No encuentras lo que quieres cerrar");
        }
        return false;
    }

    private static boolean leaveObject(GamePlayer player, List<String> objects) {
        GameObject objectFound = player.searchObjectInventary(objects.get(0));
        if (objectFound != null) {
            MessageQueue.getInstance().enqueue("Has dejado en el suelo: " + objectFound.getName());
            player.getCurrentScene().addObject(objectFound);
            player.getObjectsRecollected().remove(objectFound);
        } else {
            MessageQueue.getInstance().enqueue("No encuentras lo que quieres dejar");
        }
        return false;
    }

    private static boolean readObject(GamePlayer player, List<String> objects) {
        GameObject objectFound = player.searchObjectInventary(objects.get(0));
        if (objectFound != null && objectFound.isCanRead()) {
            MessageQueue.getInstance().enqueue("<<" + objectFound.getReadMessage() + ">>");
        } else {
            MessageQueue.getInstance().enqueue("No puedes hacer eso");
        }
        return false;
    }

    private static boolean showInventary(GamePlayer player) {
        if (player.getObjectsRecollected().isEmpty()) {
            MessageQueue.getInstance().enqueue("No llevas nada");
        } else {
            MessageQueue.getInstance().enqueue("Llevas los siguientes objetos:");
            player.getObjectsRecollected().forEach(o -> MessageQueue.getInstance().enqueue(o.getName()));
        }
        return false;
    }

    private static boolean turnOffObject(GamePlayer player, List<String> objects) {
        GameObject objectFound = player.searchObjectInAllSites(objects.get(0));
        if (objectFound != null && objectFound.isCanTurnOn()) {
            if (objectFound.isTurnOn()) {
                MessageQueue.getInstance().enqueue("Hecho.");
                objectFound.setTurnOn(false);
            } else {
                MessageQueue.getInstance().enqueue("Ya estaba apagado");
            }
        } else {
            MessageQueue.getInstance().enqueue("No encuentras lo que quieres apagar");
        }
        return false;
    }

    private static boolean turnOnObject(GamePlayer player, List<String> objects) {
        GameObject objectFound = player.searchObjectInAllSites(objects.get(0));
        if (objectFound != null && objectFound.isCanTurnOn()) {
            if (!objectFound.isTurnOn()) {
                String message = "Hecho. ";
                message += objectFound.getMessageTurnOn() != null ? objectFound.getMessageTurnOn() : "";
                MessageQueue.getInstance().enqueue(message);
                objectFound.setTurnOn(true);
            } else {
                MessageQueue.getInstance().enqueue("Ya estaba encendido");
            }
        } else {
            MessageQueue.getInstance().enqueue("No encuentras lo que quieres encender");
        }
        return false;
    }

    private static boolean examObject(GamePlayer player, List<String> objects) {
        if (!objects.isEmpty()) {
            GameObject objectFound = player.searchObjectInAllSites(objects.get(0));
            if (objectFound != null) {
                MessageQueue.getInstance().enqueue(objectFound.getDescription());
                if (objectFound.isCanOpen() && objectFound.isOpened() && !objectFound.getObjectsInside().isEmpty()) {
                    MessageQueue.getInstance().enqueue("Dentro de " + objectFound.getName() + " puedes ver:");
                    objectFound.getObjectsInside().forEach(o -> MessageQueue.getInstance().enqueue(o.getName()));
                }
                return false;
            }
        }
        MessageQueue.getInstance().enqueue("No encuentras lo que quieres observar");
        return false;
    }

    private static boolean catchObject(GamePlayer player, List<String> objects) {
        if (objects == null || objects.isEmpty()) {
            MessageQueue.getInstance().enqueue("Que quieres coger?");
            return false;
        }
        GameObject objectFound = player.searchObjectInScene(objects.get(0));
        if (objectFound != null && objectFound.isCanPickUp()) {
            player.addObject(objectFound);
            player.getCurrentScene().removeObject(objectFound);
            MessageQueue.getInstance().enqueue("Hecho");
        } else {
            if (objectFound != null && !objectFound.isCanPickUp() && objectFound.getMessageCannotPickUp() != null)
                MessageQueue.getInstance().enqueue(objectFound.getMessageCannotPickUp());
            else
                MessageQueue.getInstance().enqueue("No puedes hacer eso");
        }
        return false;
    }

}
