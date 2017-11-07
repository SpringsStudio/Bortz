package studio.springs.bortz.engine.pieces.types;

import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.pieces.PieceType;
import studio.springs.bortz.engine.utils.Position;

public class ElephantPiece extends GamePiece {
    public ElephantPiece() {
        type = PieceType.ELEPHANT;
    }
    @Override
    public boolean canMove(Position vector) {
        return Math.abs(vector.x) == 1 && Math.abs(vector.y) == 1;
    }
}
