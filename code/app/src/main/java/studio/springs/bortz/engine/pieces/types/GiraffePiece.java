package studio.springs.bortz.engine.pieces.types;

import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.pieces.PieceType;
import studio.springs.bortz.engine.utils.Position;

public class GiraffePiece extends GamePiece {
    public GiraffePiece() {
        type = PieceType.GIRAFFE;
    }
    @Override
    public boolean canMove(Position vector) {
        return (Math.abs(vector.x) + Math.abs(vector.y)) == 1;
    }
}
