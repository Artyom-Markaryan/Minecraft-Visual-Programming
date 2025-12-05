package io.github.artyom.exceptions;

public class MissingCodeLocationException extends Exception {
    public MissingCodeLocationException() {
        super("Tu dois définir l'emplacement de ton code afin de pouvoir le compiler!");
    }
}
