package studio.springs.bortz.ai;

import java.util.ArrayList;
import java.util.List;

import studio.springs.bortz.engine.GameMove;
import studio.springs.bortz.engine.board.BoardLogic;
import studio.springs.bortz.engine.board.GameBoard;
import studio.springs.bortz.engine.pieces.GamePiece;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.utils.Position;

public abstract class Ai {
    public abstract GameMove calculateMove();
    private GameBoard board;
    private BoardLogic logic;
    private PieceColor aiColor;

    public Ai(BoardLogic logic, GameBoard board, PieceColor aiColor){
        this.board = board;
        this.aiColor = aiColor;
        this.logic = logic;
    }

    List<GameMove> possbleMoves(){
        List<GameMove> moves = new ArrayList<>();
        Position range = new Position(1,1);
        List<Position> boardPositions = Position.listPositions(new Position(0,0), Position.Subtract(board.getSize(),range));

        for(Position pos : boardPositions){
            GamePiece piece = board.getPiece(pos);
            if(piece != null && piece.getColor() == aiColor){
                System.out.println("Found piece: " + piece.getType().name());
                for(Position pos2 : Position.listPositions(Position.Subtract(pos,range),Position.Add(pos,range))){
                    if(logic.canMovePiece(pos,pos2)){
                        moves.add(new GameMove(piece.getType(),pos, GameMove.MoveType.SIMPLE_MOVEMENT,pos2));
                    }
                }
            }
        }
        return moves;
    }

}
