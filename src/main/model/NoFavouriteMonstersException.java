package model;

// Called when the Monster Group contains no favourite monsters
public class NoFavouriteMonstersException extends Exception {
    public NoFavouriteMonstersException() {
        super("There are no favourite monsters");
    }
}
