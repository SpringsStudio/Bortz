package studio.springs.bortz.engine.pieces;

import studio.springs.bortz.engine.Position;

public class LionPiece extends GamePiece {
    @Override
    public boolean canMove(Position from, Position to) {
        return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.LION;
    }
}
