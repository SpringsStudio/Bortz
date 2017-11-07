package studio.springs.bortz.engine.logic;

import studio.springs.bortz.engine.utils.Position;
import studio.springs.bortz.engine.pieces.PieceType;

public class GameMove {
    enum MoveType {
        SIMPLE_MOVEMENT,
        DROP
    }

    final public PieceType piece;
    final public Position origin;
    final public MoveType movement;
    final public Position destination;
    final public boolean promotion;

    public GameMove(PieceType piece, Position origin, MoveType movement, Position destination, boolean promotion){
        this.piece = piece;
        this.origin = origin;
        this.movement = movement;
        this.destination = destination;
        this.promotion = promotion;
    }
}
