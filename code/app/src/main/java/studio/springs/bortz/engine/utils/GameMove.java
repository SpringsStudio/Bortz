package studio.springs.bortz.engine.utils;

import studio.springs.bortz.engine.pieces.PieceType;

public class GameMove {
    public enum MoveType {
        SIMPLE_MOVEMENT,
        DROP
    }

    final public PieceType piece;
    final public Position origin;
    final public MoveType movement;
    final public Position destination;

    public GameMove(PieceType piece, Position origin, MoveType movement, Position destination){
        this.piece = piece;
        this.origin = origin;
        this.movement = movement;
        this.destination = destination;
    }
}
