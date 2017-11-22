package studio.springs.bortz.engine;

import studio.springs.bortz.ai.Ai;
import studio.springs.bortz.ai.MinimaxAi;
import studio.springs.bortz.engine.pieces.PieceColor;
import studio.springs.bortz.engine.utils.GameMove;
import studio.springs.bortz.engine.utils.IllegalMoveException;

public class AiGameClient extends GameClient {
    Ai ai;
    public AiGameClient(){
        this.ai = new MinimaxAi(logic, board, PieceColor.BLACK, 4);
    }
    public void startAiMove(){
        ai.start();
    }
    public void waitForAi() throws InterruptedException, IllegalMoveException{
        ai.join();
        GameMove aiMove = ai.getLastMove();
        if (aiMove != null) {
            // ------------------ DEBUGGING INFO ---------------------------------------------------------- //
            System.out.print("Calculated move. Type: " + aiMove.movement.name() + ". Piece: " + aiMove.piece.name());
            if (aiMove.origin != null)
                System.out.print(". From: " + aiMove.origin.x + "," + aiMove.origin.y);
            System.out.println(". To: " + aiMove.destination.x + "," + aiMove.destination.y);
            // -------------------------------------------------------------------------------------------- //
            performMove(aiMove);
        }
        else
            System.out.println("Warning: Ai did not find a possible move."); // <--- Here as well
    }
    public PieceColor getAiColor(){return ai.getAiColor();}
}
