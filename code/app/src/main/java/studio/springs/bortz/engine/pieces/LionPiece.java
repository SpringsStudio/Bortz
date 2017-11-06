package studio.springs.bortz.engine.pieces;

import studio.springs.bortz.engine.Position;

public class LionPiece extends GamePiece {
    LionPiece() {
        type = PieceType.LION;
    }
    @Override
    public boolean canMove(Position vector) {
        return Math.abs(vector.x) <= 1 && Math.abs(vector.y) <= 1;
    }
}
