package io.github.artyom.exceptions;

public class TooCloseToWorldBorderException extends Exception {
    public TooCloseToWorldBorderException() {
        super("Impossible de placer ce bloc de code si proche de la bordure du monde!");
    }
}
