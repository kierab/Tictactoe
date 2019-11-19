import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.util.Random;

import static javafx.scene.paint.Color.BLACK;

public class Game extends Application {
    public Game(){}


    private Group root = new Group();
    private Group board = new Group();
    private Group moves = new Group();

    Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);

    private static final int WINDOW_X = 500;
    private static final int WINDOW_Y = 500;
    private static final int SQUARE_SIDE = 100;
    private static final int PADDING = 100;

    double placeX, placeY; // x and y of where the symbol is being placed
    double mouseX, mouseY; // where the mouse is clicked

    public STATES turn;
    public String turnString;

    public STATES[][] placements = new STATES[3][3];


    //game methods
    public void clear() {
        moves.getChildren().clear();
        for (int y = 0; y <= 2; y++) {
            for (int x = 0; x <= 2; x++) {
                placements[y][x] = STATES.EMPTY;
            }
        }
    }

    public void changeTurn() {
        if (turn == STATES.X) turn = STATES.O;
        else turn = STATES.X;
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

        Button restart = new Button("restart");
        restart.setOnAction(event -> newGame());
        restart.setLayoutX(PADDING+1.5*SQUARE_SIDE);
        restart.setLayoutY(PADDING/2);

        board.getChildren().addAll(r,y1,y2,x1,x2,restart);
    }

    public void newGame() {
        clear();
        randomTurn();
        setBoard();
        checkState();
    }

    public String turnString() {
        if (turn == STATES.X) return "X";
        else return "O";
    }

    public void randomTurn() {
        Random r = new Random();
        int rnd = r.nextInt(2);
        turn = (rnd == 1) ? STATES.X : STATES.O;

    }



    public boolean checkFree(int x, int y) {
        if (placements[y][x] != STATES.EMPTY) return false;
        else return true;
    }

    public void checkState() {
        System.out.print("\n");
        for (int y = 0; y <= 2; y++) {
            for (int x = 0; x <= 2; x++) {
                System.out.print(placements[y][x] + ", ");
            }
            System.out.print("\n");
        }
    }

    public void runGame() {
        clear();
        randomTurn();
        scene.setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
            int xplace = (int) ((mouseX - PADDING)/100);
            int yplace = (int) ((mouseY - PADDING)/100);
            //System.out.println(xplace + " and" + yplace);
            if (!(xplace > 3 || yplace > 3 || xplace < 0 || yplace < 0 ) && checkFree(xplace,yplace)) {
                placements[yplace][xplace] = turn;
                Text move = new Text(PADDING + xplace*SQUARE_SIDE + 40 ,PADDING+yplace*SQUARE_SIDE + 60,turnString());
                move.setFont(new Font(40));
                changeTurn();
                moves.getChildren().add(move);

                //toString
                checkState();
            }

        });

    }

    //game rules (logic)
    //win conditions
    //add small text on top saying whos turnitis



    @Override
    public void start(Stage stage) {
        stage.setTitle("nought and cross x");


        newGame();
        runGame();

        root.getChildren().add(board);
        root.getChildren().add(moves);
        stage.setScene(scene);
        stage.show();
    }
}
