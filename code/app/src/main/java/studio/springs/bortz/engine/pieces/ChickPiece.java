package studio.springs.bortz.engine.pieces;

import studio.springs.bortz.engine.Position;

public class ChickPiece extends GamePiece {
    @Override
    public boolean canMove(Position vector) {
       switch(getColor()){
           case WHITE:
               return Math.abs(vector.x) == 0 && vector.y == -1;
           case BLACK:
               return Math.abs(vector.x) == 0 && vector.y == 1;
       }
       return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.CHICK;
    }
}
