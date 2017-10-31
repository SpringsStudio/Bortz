package studio.springs.bortz.engine.pieces;

import studio.springs.bortz.engine.Position;

public class GiraffePiece extends GamePiece {
    @Override
    public boolean canMove(Position vector) {
        return (Math.abs(vector.x) + Math.abs(vector.y)) == 1;
    }

    @Override
    public PieceType getType() {
        return PieceType.GIRAFFE;
    }
}
