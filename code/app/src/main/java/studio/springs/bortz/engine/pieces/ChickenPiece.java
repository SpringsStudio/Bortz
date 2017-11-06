package studio.springs.bortz.engine.pieces;

import studio.springs.bortz.engine.Position;

public class ChickenPiece extends GamePiece {
    ChickenPiece(){
        type = PieceType.CHICKEN;
    }
    @Override
    public boolean canMove(Position vector) {
        boolean bounds = Math.abs(vector.x) <= 1 && Math.abs(vector.y) <= 1;
        switch(getColor()){
            case WHITE:
                return bounds && (vector.x == 0 || vector.y > -1);
            case BLACK:
                return bounds && (vector.x == 0 || vector.y < 1);
            default:
                return false;
        }
    }
}
