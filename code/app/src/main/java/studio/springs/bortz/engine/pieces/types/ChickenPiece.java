package studio.springs.bortz.engine.pieces.types;

import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.pieces.PieceType;
import studio.springs.bortz.engine.utils.Position;

public class ChickenPiece extends GamePiece {
    public ChickenPiece(){
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
