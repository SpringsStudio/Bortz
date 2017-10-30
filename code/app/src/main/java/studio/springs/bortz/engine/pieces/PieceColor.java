package studio.springs.bortz.engine.pieces;

public enum PieceColor {
    WHITE, BLACK;
    public static PieceColor opposite(PieceColor color){
        return (color == WHITE) ? BLACK : WHITE;
    }
}
