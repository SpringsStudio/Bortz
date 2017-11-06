package studio.springs.bortz.engine.pieces;

import studio.springs.bortz.engine.Position;

public abstract class GamePiece {
    private PieceColor color;
    protected PieceType type;

    public abstract boolean canMove(Position vector);
    public PieceType getType() {return type;}
    public PieceColor getColor() {return color; }
    void setColor(PieceColor color) {this.color = color; }
    public static PieceType promotionOf(PieceType type){
        switch (type){
            case CHICK:
                return PieceType.CHICKEN;
            default:
                return null;
        }
    }
    public GamePiece promote(){
        return GamePieceFactory.createPiece(promotionOf(type),color);
    }
}
