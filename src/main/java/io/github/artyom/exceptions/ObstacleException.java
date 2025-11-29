package io.github.artyom.exceptions;

public class ObstacleException extends Exception {
    public ObstacleException() {
        super("Impossible de pousser cette ligne de blocs de code, il y a un obstacle sur le chemin!");
    }
}
