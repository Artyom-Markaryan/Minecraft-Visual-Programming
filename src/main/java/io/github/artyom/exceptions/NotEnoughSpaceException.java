package io.github.artyom.exceptions;

public class NotEnoughSpaceException extends Exception {
    public NotEnoughSpaceException() {
        super("Il n'y a pas assez d'espace ici pour placer ce bloc de code!");
    }
}
