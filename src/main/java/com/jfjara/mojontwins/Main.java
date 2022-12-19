package com.jfjara.mojontwins;

import com.jfjara.mojontwins.actions.ActionExecutor;
import com.jfjara.mojontwins.actions.ActionParser;
import com.jfjara.mojontwins.actions.GameActionsEnum;
import com.jfjara.mojontwins.actions.SemanticParser;
import com.jfjara.mojontwins.messages.MessageQueue;
import com.jfjara.mojontwins.model.GameObject;
import com.jfjara.mojontwins.model.GamePlayer;
import com.jfjara.mojontwins.model.GameScene;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GameScene scene1 = new GameScene("Estas en el salon de tu casa. Tienes la tele encendida con el youtube puesto. Salta un video del tuberviejuner. Te aburres.",
                null, null, null, null, null, null);
        GameScene scene2 = new GameScene("Llegas al pasillo. Al frente, la puerta de la calle. Al este, el baño. Ves las escaleras que suben a la planta de arriba", null, null, null, null, null, null);
        GameScene scene3 = new GameScene("Estas en la planta de arriba de la casa. Ves la escalera por donde has subido. Y poco mas que ver.", null, null, null, null, null, null);
        GameScene scene4 = new GameScene("El baño de la planta baja de tu casa. Es pequeño pero para cagar y mear te vale.", null, null, null, null, null, null);

        scene1.setNorth(scene2);

        GameObject nota = new GameObject("nota", "Es una nota escrita en un papel", true, null);
        nota.setCanRead(true);
        nota.setReadMessage("Te he dejado la comida en la basura, cerdo.");
        GameObject televisor = new GameObject("televisor", "Es la tele de tu salon. Marca Bluesky de las del Carreful.", false, "¿Donde vas Rambo? La tele pesa un huevo, no puedes cargar con ella");
        televisor.setCanTurnOn(true);
        televisor.setTurnOn(true);
        televisor.setMessageTurnOn("Sigue el tuberviejuner en la tele, menudo cansino.");
        televisor.addSynonymous(new String[] {"tele", "television"});


        GameObject pera = new GameObject("pera", "Es una pera un poco pocha", true, null);
        GameObject nevera = new GameObject("nevera", "Es una nevera de una puerta de color blanca, marca Churrifriss", false, null);
        nevera.addSynonymous(new String[] {"frigo", "frigorifico", "refrigerador"});
        nevera.setCanOpen(true);
        nevera.setOpened(false);
        nevera.addObjectInside(Arrays.asList(pera));

        scene4.addObject(nevera);
        scene1.addObject(televisor);
        scene1.addObject(new GameObject("llaves", "Son las llaves de tu casa. Tienen un bonito llavero con el logo de SEAT", true, null));

        GameObject escaleras = new GameObject("escaleras", "Son unas escaleras, muy practicas para subir. Hacen forma de U", false, "No puedes superman, estan agarradas al suelo y ademas son grandes y pesan");
        scene2.setEast(scene4);
        scene2.setUp(scene3);

        scene2.addObject(nota);

        scene2.addObject(escaleras);
        scene3.addObject(escaleras);

        GamePlayer player = new GamePlayer();
        player.setCurrentScene(scene1);

        Scanner sc = new Scanner(System.in);
        boolean endGame = false, changeScene = true;
        while (!endGame) {
            if (changeScene) {
                MessageQueue.getInstance().enqueue(player.getCurrentScene().getText());
                MessageQueue.getInstance().enqueue("Tambien puedes ver:");
                player.getCurrentScene().printObjects();
            }
            MessageQueue.getInstance().enqueue("Que haces ahora?");
            MessageQueue.getInstance().showMessages();
            String actionText = sc.nextLine();
            GameActionsEnum action = ActionParser.parse(SemanticParser.extractVerb(actionText));
            if (action == GameActionsEnum.END_GAME) {
                endGame = true;
                MessageQueue.getInstance().enqueue("OK. Bye!");
            } else {
                changeScene = ActionExecutor.execute(action, player, SemanticParser.extractObjects(actionText));
            }
            MessageQueue.getInstance().showMessages();
        }
    }
}
