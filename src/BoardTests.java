
public class BoardTests {

    public static void main(String[] args) {
        Game g = new Game();
        for (int y = 0; y <= 2; y++) {
            for (int x = 0; x <= 2; x++) {
                if (x == 1) g.placements[y][x] = STATES.O;
                else g.placements[y][x] = STATES.X;
            }
        }

        //g.clear();

        for (int y = 0; y <= 2; y++) {
            for (int x = 0; x <= 2; x++) {
                System.out.print(g.placements[y][x]);
            }
            System.out.print("\n");
        }




    }
}
