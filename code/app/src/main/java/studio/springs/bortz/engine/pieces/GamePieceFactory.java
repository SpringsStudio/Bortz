package studio.springs.bortz.engine.pieces;

public class GamePieceFactory {
    public GamePiece createPiece(PieceType type, PieceColor color){
        GamePiece piece;
        switch (type) {
            case LION: piece = new LionPiece();
                break;
            default: return null;
        }
        piece.setColor(color);
        return piece;
    }
}