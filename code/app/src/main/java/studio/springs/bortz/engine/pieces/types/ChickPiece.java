package studio.springs.bortz.engine.pieces.types;

import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.pieces.PieceType;
import studio.springs.bortz.engine.utils.Position;

public class ChickPiece extends GamePiece {
    public ChickPiece() {
        type = PieceType.CHICK;
    }
    @Override
    public boolean canMove(Position vector) {
        switch(getColor()){
            case WHITE:
                return vector.x == 0 && vector.y == 1;
            case BLACK:
                return vector.x == 0 && vector.y == -1;
            default:
                return false;
        }
    }
}
