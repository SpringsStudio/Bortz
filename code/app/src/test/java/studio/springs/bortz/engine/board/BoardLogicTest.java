package studio.springs.bortz.engine.board;

import org.junit.jupiter.api.function.Executable;

import studio.springs.bortz.engine.pieces.PieceType;
import studio.springs.bortz.engine.utils.IllegalMoveException;
import studio.springs.bortz.engine.utils.Position;

import static org.junit.jupiter.api.Assertions.*;

class BoardLogicTest {
    GameBoard board;
    BoardLogic logic;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        board = BoardLogic.prepareBoard();
        logic = new BoardLogic(board);
    }

    // Test illegal moves
    @org.junit.jupiter.api.Test
    void takeOwnPiece(){
        assertThrows(IllegalMoveException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
               logic.movePiece(new Position(2,0), new Position(1,0));
            }
        });
    }
    @org.junit.jupiter.api.Test
    void movePieceWrongWay(){
        assertThrows(IllegalMoveException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                logic.movePiece(new Position(0,0), new Position(0,1));
            }
        });
    }
    @org.junit.jupiter.api.Test
    void movePieceOutOfBounds(){
        assertThrows(IllegalMoveException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                logic.movePiece(new Position(2,0), new Position(3,0));
            }
        });
    }
    @org.junit.jupiter.api.Test
    void moveOpponentsPiece(){
        assertThrows(IllegalMoveException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                logic.movePiece(new Position(0,3), new Position(0,2));
            }
        });
    }
    @org.junit.jupiter.api.Test
    void dropNotCapturedPiece(){
        assertThrows(IllegalMoveException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                logic.dropPiece(new Position(0,1),PieceType.CHICK);
            }
        });
    }
}