package com.jfjara.mojontwins.actions;

public class ActionParser {

    public static GameActionsEnum parse(String allText) {
        String[] words = allText.split(" ");
        return switch (words[0].toUpperCase()) {
            case "N", "NORTE" -> GameActionsEnum.N;
            case "S", "SUR" -> GameActionsEnum.S;
            case "E", "ESTE" -> GameActionsEnum.E;
            case "O", "OESTE" -> GameActionsEnum.W;
            case "SUBIR" -> GameActionsEnum.SUBIR;
            case "BAJAR" -> GameActionsEnum.BAJAR;
            case "ENTRAR" -> GameActionsEnum.ENTRAR;
            case "SALIR" -> GameActionsEnum.SALIR;
            case "ABRIR" -> GameActionsEnum.ABRIR;
            case "CERRAR" -> GameActionsEnum.CERRAR;
            case "FIN", "FINALIZAR" -> GameActionsEnum.END_GAME;
            case "LEER" -> GameActionsEnum.LEER;
            case "DEJAR", "SOLTAR" -> GameActionsEnum.DEJAR;
            case "VER" -> GameActionsEnum.VER;
            case "COGER", "AGARRAR", "RECOGER" -> GameActionsEnum.COGER;
            case "INVENTARIO", "LISTAR" -> GameActionsEnum.INVENTARIO;
            case "OBSERVAR", "MIRAR", "ANALIZAR" -> GameActionsEnum.OBSERVAR;
            case "APAGAR" -> GameActionsEnum.APAGAR;
            case "ENCENDER" -> GameActionsEnum.ENCENDER;
            case "TIRAR" -> GameActionsEnum.TIRAR;
            case "EMPUJAR" -> GameActionsEnum.EMPUJAR;
            default -> GameActionsEnum.UNKNOWN;
        };
    }
}
