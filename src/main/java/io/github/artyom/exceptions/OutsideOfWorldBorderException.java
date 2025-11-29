package io.github.artyom.exceptions;

public class OutsideOfWorldBorderException extends Exception {
    public OutsideOfWorldBorderException() {
        super("Impossible de pousser cette ligne de blocs de code à l'extérieur du monde!");
    }
}
