package studio.springs.bortz.engine.pieces;

import studio.springs.bortz.engine.pieces.types.*;

public class GamePieceFactory {
    public static GamePiece createPiece(PieceType type, PieceColor color){
        if (type == null || color == null) return null;

        GamePiece piece;
        switch (type) {
            case LION: piece = new LionPiece();
                break;
            case GIRAFFE: piece = new GiraffePiece();
                break;
            case ELEPHANT: piece = new ElephantPiece();
                break;
            case CHICK: piece = new ChickPiece();
                break;
            case CHICKEN: piece = new ChickenPiece();
                break;
            default: return null;
        }
        piece.setColor(color);
        return piece;
    }
}
