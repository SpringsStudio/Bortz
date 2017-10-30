package studio.springs.bortz.engine;

import studio.springs.bortz.engine.pieces.PieceColor;

public class GameState {
    public enum StateType{
        MOVE,
        BOARD_PIECE_SELECTED,
        CAPTURED_PIECE_SELECTED
    }
    private StateType type;
    private Object selection;
    private PieceColor playerColor;

    GameState(){
        changeState(StateType.MOVE, PieceColor.WHITE);
    }
    public StateType getType() {return type;}
    public PieceColor getPlayerColor() {return playerColor;}
    public Object getSelection() {return selection;}

    public void setSelection(Object selection){this.selection = selection;}
    public void changeState(StateType type, PieceColor playerColor){
       this.type = type;
       this.playerColor = playerColor;
    }
    public void changeState(StateType type){
        this.type = type;
    }
    public void changeState(PieceColor playerColor){
        this.playerColor = playerColor;
    }
}
