import javafx.application.Application;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import static javafx.scene.paint.Color.BLACK;

public class Game extends Application {
    public Game(){}

    private Group root = new Group();
    private Group board = new Group();
    private Group moves = new Group();

    private static final int WINDOW_X = 500;
    private static final int WINDOW_Y = 500;
    private static final int SQUARE_SIDE = 100;
    private static final int PADDING = 100;

    public STATES turn;

    public STATES[][] placements = new STATES[3][3];

    //game methods
    public void clear() {
        for (int y = 0; y <= 2; y++) {
            for (int x = 0; x <= 2; x++) {
                placements[y][x] = STATES.EMPTY;
            }
        }
    }

    public void changeTurn() {
        if (turn == STATES.X) turn = STATES.O;
        else if (turn == STATES.O) turn = STATES.X;
    }

    public void setBoard() {
        Rectangle r = new Rectangle();
        r.setX(PADDING);
        r.setY(PADDING);
        r.setWidth(3*SQUARE_SIDE);
        r.setHeight(3*SQUARE_SIDE);
        r.setFill(null);
        r.setStroke(BLACK);
        r.setStrokeWidth(2);
        Line y1 = new Line(PADDING+SQUARE_SIDE,PADDING, PADDING+SQUARE_SIDE,PADDING+3*SQUARE_SIDE);
        y1.setStrokeWidth(2);
        Line y2 = new Line(PADDING+2*SQUARE_SIDE,PADDING, 2*PADDING+SQUARE_SIDE,PADDING+3*SQUARE_SIDE);
        y2.setStrokeWidth(2);
        Line x1 = new Line(PADDING,PADDING+SQUARE_SIDE, PADDING+3*SQUARE_SIDE,PADDING+SQUARE_SIDE);
        x1.setStrokeWidth(2);
        Line x2 = new Line(PADDING,PADDING+2*SQUARE_SIDE, PADDING+3*SQUARE_SIDE,2*PADDING+SQUARE_SIDE);
        x2.setStrokeWidth(2);

        board.getChildren().addAll(r,y1,y2,x1,x2);
    }

    public void playTurn() {

    }

    //game rules (logic)



    @Override
    public void start(Stage stage) {
        stage.setTitle("Game");
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);

        root.getChildren().add(board);



        setBoard();

        stage.setScene(scene);
        stage.show();
    }
}
