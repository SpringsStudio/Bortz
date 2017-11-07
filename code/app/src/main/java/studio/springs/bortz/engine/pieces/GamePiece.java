package studio.springs.bortz.engine.pieces;

import studio.springs.bortz.engine.utils.BiMap;
import studio.springs.bortz.engine.utils.Position;

public abstract class GamePiece {
    private PieceColor color;
    protected PieceType type;
    private static BiMap<PieceType,PieceType> promotions = new BiMap<>(); static {
        promotions.put(PieceType.CHICK, PieceType.CHICKEN);
    }


    public abstract boolean canMove(Position vector);
    public PieceType getType() {return type;}
    public PieceColor getColor() {return color; }
    void setColor(PieceColor color) {this.color = color; }

    public GamePiece swapColor(){
        return GamePieceFactory.createPiece(type,PieceColor.opposite(color));
    }

    public GamePiece promote(){
        return GamePieceFactory.createPiece(promotions.get(type),color);
    }

    public GamePiece demote(){
        return GamePieceFactory.createPiece(promotions.getKey(type),color);
    }

    @Override
    public int hashCode(){
        return (type.name() + color.name()).hashCode();
    }

    @Override
    public boolean equals(Object o){
        return this.hashCode() == o.hashCode();
    }
}
