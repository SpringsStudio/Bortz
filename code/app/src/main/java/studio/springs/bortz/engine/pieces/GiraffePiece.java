package studio.springs.bortz.engine.pieces;

import studio.springs.bortz.engine.Position;

public class GiraffePiece extends GamePiece {
    GiraffePiece() {
        type = PieceType.GIRAFFE;
    }
    @Override
    public boolean canMove(Position vector) {
        return (Math.abs(vector.x) + Math.abs(vector.y)) == 1;
    }
}
