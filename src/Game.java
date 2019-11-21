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
import static javafx.scene.paint.Color.RED;


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

    double mouseX, mouseY; // where the mouse is clicked

    public STATES turn;
    public String turnString; //whos turn isit

    Text showturn = new Text(turnString() + "'s turn"); // show which player turn it is

    public STATES[][] placements = new STATES[3][3]; //array of the board

    public boolean gameOver;


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
        showturn.setText(turnString() + "'s turn");

    }

    public String stateToString(STATES state) {
        if (state == STATES.X) return "X";
        else if (state == STATES.O) return "O";
        else return "empty";
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
        return (placements[y][x] == STATES.EMPTY);
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

        //shows which player's turn it is
        showturn.setLayoutX((PADDING+1.5*SQUARE_SIDE));
        showturn.setLayoutY(PADDING/4);
        showturn.setFont(new Font(20));
        board.getChildren().add(showturn);

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
                showturn.setText(turnString() + "'s turn");

                checkState(); //output in console the state of the board according to placements array

                //when win condition
                if (!(checkWin().equals(""))){
                    System.out.println(checkWin() + " wins");
                    Line winline = new Line();
                    winline.setStartX(PADDING + SQUARE_SIDE/2 + SQUARE_SIDE * Character.getNumericValue(winstring.charAt(1)));
                    winline.setStartY(PADDING + SQUARE_SIDE/2 + SQUARE_SIDE * Character.getNumericValue(winstring.charAt(0)));
                    winline.setEndX(PADDING + SQUARE_SIDE/2 + SQUARE_SIDE * Character.getNumericValue(winstring.charAt(3)));
                    winline.setEndY(PADDING + SQUARE_SIDE/2 + SQUARE_SIDE * Character.getNumericValue(winstring.charAt(2)));
                    winline.setStrokeWidth(5);
                    winline.setStroke(RED);
                    moves.getChildren().add(winline);
                    showturn.setText(checkWin() + " wins");
                }
                else System.out.println("no winner yet");
            }
        });


    }

    //game rules (logic)
    //win conditions


    String winstring;

    public String checkWin() {
        String winner = "";
        //check horizontals
        for (int y=0; y <3; y++) {
            if (placements[y][0] == placements[y][1] && placements[y][1] == placements[y][2]) {
                winner = stateToString(placements[y][0]);
                winstring = y+"0"+y+"2";
                break;
            }
        }
        //check verticals
        for (int x =0;x<3;x++) {
            if (placements[0][x] == placements[1][x] && placements[1][x] == placements[2][x]) {
                winner = stateToString(placements[0][x]);
                winstring = "0"+x+"2"+x;
                break;
            }
        }
        //check diagonals
        if (placements[0][0]==placements[1][1] && placements[1][1] ==placements[2][2]) {
            winner = stateToString(placements[0][0]);
            winstring = "0022";
        }
        if (placements[0][2]==placements[1][1] && placements[1][1] ==placements[2][0]) {
            winner = stateToString(placements[0][2]);
            winstring = "0220";
        }

        if (winner.equals("empty")) return "";
        else return winner;
    }




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
