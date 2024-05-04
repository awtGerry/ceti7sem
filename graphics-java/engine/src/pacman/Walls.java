import java.awt.Color;

public class Walls {
    public double x1, y1, x2, y2;

    public Walls(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public static Walls[] wallsCords() {
        Walls[] walls = new Walls[54];

        for (int i = 0; i < walls.length; i++) {
            walls[i] = new Walls(0, 0, 0, 0);
        }

        ghostRectangle(walls);
        innerObstaclesBottom(walls);
        innerObstaclesTop(walls);
        outside(walls);

        return walls;
    }

    public static Walls[] getWalls() {
        return wallsCords();
    }

    public static void drawWalls() {
        Walls[] walls = getWalls();

        for (Walls wall : walls) {
            Fill.scanline((int) wall.x1, (int) wall.y1, (int) wall.x2, (int) wall.y2, Color.BLUE);
        }
    }

    private static void ghostRectangle(Walls[] walls) {
        walls[0] = new Walls(220, Pacman.WIDTH-270, 380, Pacman.WIDTH-260); // Left to right wall (bottom)
        walls[1] = new Walls(220, Pacman.WIDTH-350, 230, Pacman.WIDTH-260); // Left to right wall (left)
        walls[2] = new Walls(370, Pacman.WIDTH-350, 380, Pacman.WIDTH-260); // Left to right wall (right)
    }

    private static void innerObstaclesBottom(Walls[] walls) {
        /* LEFT BOTTOM OBSTACLES */
        walls[3] = new Walls(0, Pacman.HEIGHT-110, 50, Pacman.HEIGHT-100); // Left bottom obstacle

        walls[4] = new Walls(100, Pacman.HEIGHT-160, 110, Pacman.HEIGHT-100); // L shape

        walls[5] = new Walls(60, Pacman.HEIGHT-160, 110, Pacman.HEIGHT-150); // L shape

        walls[6] = new Walls(50, 550, 250, 560); // Left middle obstacle
        walls[7] = new Walls(160, Pacman.HEIGHT-110, 170, Pacman.HEIGHT-50); // Left middle obstacle

        walls[8] = new Walls(160, Pacman.HEIGHT-270, 170, Pacman.HEIGHT-200); // Left middle obstacle

        walls[9] = new Walls(160, Pacman.HEIGHT-160, 240, Pacman.HEIGHT-150); // left small wall

        /* MID BOTTOM OBSTACLES */
        walls[10] = new Walls(300, Pacman.HEIGHT-210, 310, Pacman.HEIGHT-150); // T shape (top)
        walls[11] = new Walls(300, Pacman.HEIGHT-210, 380, Pacman.HEIGHT-200); // T shape (top)
        walls[12] = new Walls(220, Pacman.HEIGHT-210, 300, Pacman.HEIGHT-200); // T shape (top)

        walls[13] = new Walls(300, Pacman.HEIGHT-110, 310, Pacman.HEIGHT-50); // T shape (bottom)
        walls[14] = new Walls(300, Pacman.HEIGHT-110, 380, Pacman.HEIGHT-100); // T shape (bottom)
        walls[15] = new Walls(220, Pacman.HEIGHT-110, 300, Pacman.HEIGHT-100); // T shape (bottom)

        /* RIGHT BOTTOM OBSTACLES */
        walls[16] = new Walls(Pacman.WIDTH - 110, Pacman.HEIGHT-160, Pacman.WIDTH - 100, Pacman.WIDTH-100); // Right middle obstacle
        walls[17] = new Walls(Pacman.WIDTH - 110, Pacman.HEIGHT-160, Pacman.WIDTH - 60, Pacman.HEIGHT-150); // Right middle obstacle
        walls[18] = new Walls(Pacman.WIDTH - 50, Pacman.HEIGHT-110, Pacman.WIDTH, Pacman.HEIGHT-100); // Right bottom obstacle

        walls[19] = new Walls(Pacman.WIDTH - 250, Pacman.HEIGHT-60, Pacman.WIDTH - 50, Pacman.HEIGHT-50); // Right middle obstacle
        walls[20] = new Walls(Pacman.WIDTH - 170, Pacman.HEIGHT-110, Pacman.WIDTH - 160, Pacman.HEIGHT-50); // Right middle obstacle

        walls[21] = new Walls(430, Pacman.HEIGHT-270, 440, Pacman.HEIGHT-200); // Right middle obstacle

        walls[22] = new Walls(360, Pacman.HEIGHT-160, 440, Pacman.HEIGHT-150); // right small wall

    }

    private static void innerObstaclesTop(Walls[] walls) {
        walls[23] = new Walls(300, Pacman.HEIGHT-600, 310, Pacman.HEIGHT-500); // Top T with outer wall

        walls[24] = new Walls(230, Pacman.HEIGHT-460, 370, Pacman.HEIGHT-450); // Top T
        walls[25] = new Walls(300, Pacman.HEIGHT-460, 310, Pacman.HEIGHT-400); // Top T

        /* LEFT */
        walls[26] = new Walls(160, Pacman.HEIGHT-460, 170, Pacman.HEIGHT-340); // Mid inverted T
        walls[27] = new Walls(160, Pacman.HEIGHT-410, 240, Pacman.HEIGHT-400); // Mid inverted T

        walls[28] = new Walls(60, Pacman.HEIGHT-460, 110, Pacman.HEIGHT-450); // Little left wall

        walls[29] = new Walls(60, Pacman.HEIGHT-540, 110, Pacman.HEIGHT-500); // Rectangle 1
        walls[30] = new Walls(160, Pacman.HEIGHT-540, 240, Pacman.HEIGHT-500); // Rectangle 2

        /* RIGHT */
        walls[31] = new Walls(430, Pacman.HEIGHT-460, 440, Pacman.HEIGHT-340); // Mid inverted T
        walls[32] = new Walls(360, Pacman.HEIGHT-410, 440, Pacman.HEIGHT-400); // Mid inverted T

        walls[33] = new Walls(Pacman.WIDTH - 100, Pacman.HEIGHT-460, Pacman.WIDTH - 60, Pacman.HEIGHT-450); // Little right wall

        walls[34] = new Walls(Pacman.WIDTH - 100, Pacman.HEIGHT-540, Pacman.WIDTH - 60, Pacman.HEIGHT-500); // Rectangle 1
        walls[35] = new Walls(Pacman.WIDTH - 240, Pacman.HEIGHT-540, Pacman.WIDTH - 160, Pacman.HEIGHT-500); // Rectangle 2
    }

    private static void outside(Walls[] walls) {
        walls[36] = new Walls(0, 0, 600, 0 + 10); // Bottom wall
        walls[37] = new Walls(0, 600 - 10, 600, 600); // Top wall

        walls[38] = new Walls(0, 600 / 1.5, 10, 600); // Left top wall
        walls[39] = new Walls(0, 0, 10, 600 / 3); // Left bottom wall
        walls[40] = new Walls(600 - 10, 600 / 1.5, 600, 600); // Right top wall
        walls[41] = new Walls(600 - 10, 0, 600, 600 / 3); // Right bottom wall

        walls[42] = new Walls(0, 600 / 3, 600 / 6, (600 / 3) + 10); // Left to mid wall (bottom)
        walls[43] = new Walls(600 / 6, 600 / 3, (600 / 6) + 10, 600 / 2.3); // Left going up wall (bottom)
        walls[44] = new Walls(0, 600 / 2.3, (600 / 6) + 10, (600 / 2.3) + 10); // Left back to 0 wall (bottom)

        walls[45] = new Walls(0, 600 / 1.5, 600 / 6, (600 / 1.5) + 10); // Left to mid wall (top)
        walls[46] = new Walls(600 / 6, 600 / 1.7, (600 / 6) + 10, (600 / 1.5) + 10); // Left going down wall (top)
        walls[47] = new Walls(0, 600 / 1.7, (600 / 6) + 10, (600 / 1.7) + 10); // Left back to 0 wall (top)

        walls[48] = new Walls(600 - (600 / 6), 600 / 3, 600, (600 / 3) + 10); // Right to mid wall (bottom)
        walls[49] = new Walls(600 - (600 / 6 + 10), 600 / 3, 600 - (600 / 6), 600 / 2.3); // Right going up wall (bottom)
        walls[50] = new Walls(600 - (600 / 6 + 10), 600 / 2.3, 600, (600 / 2.3) + 10); // Right back to 0 wall (bottom)

        walls[51] = new Walls(600 - (600 / 6), 600 / 1.5, 600, (600 / 1.5) + 10); // Right to mid wall (top)
        walls[52] = new Walls(600 - (600 / 6 + 10), 600 / 1.7, 600 - (600 / 6), (600 / 1.5) + 10); // Right going down wall (top)
        walls[53] = new Walls(600 - (600 / 6 + 10), 600 / 1.7, 600, (600 / 1.7) + 10); // Right back to 0 wall (top)
    }

}
