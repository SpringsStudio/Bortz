package studio.springs.bortz.engine.pieces;

import studio.springs.bortz.engine.Position;

public class ElephantPiece extends GamePiece {
    @Override
    public boolean canMove(Position vector) {
       return Math.abs(vector.x) <= 1 && Math.abs(vector.y) <= 1 && (Math.abs(vector.x) + Math.abs(vector.y)) == 2;
    }

    @Override
    public PieceType getType() {
        return PieceType.ELEPHANT;
    }
}
