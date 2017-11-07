package studio.springs.bortz.engine.pieces.types;

import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.pieces.PieceType;
import studio.springs.bortz.engine.utils.Position;

public class LionPiece extends GamePiece {
    public LionPiece() {
        type = PieceType.LION;
    }
    @Override
    public boolean canMove(Position vector) {
        return Math.abs(vector.x) <= 1 && Math.abs(vector.y) <= 1;
    }
}
