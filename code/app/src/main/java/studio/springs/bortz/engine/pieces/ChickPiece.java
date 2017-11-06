package studio.springs.bortz.engine.pieces;

import studio.springs.bortz.engine.Position;

public class ChickPiece extends GamePiece {
    ChickPiece() {
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
