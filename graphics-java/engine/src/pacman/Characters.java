/* Purpose:
   Handles the creation and movement of the characters.
   if the ghost collides with a wall, it will change direction, since
   it's not meant to be a game.
*/

/* package pacman; */

import java.util.List;
import java.util.Random;
import java.awt.Color;

public class Characters {
    public enum Direction {
        Right,
        Up,
        Left,
        Down
    }

    public static class Ghost {
        private float x;
        private float y;
        private Direction direction;
        private Color color;

        public Ghost(float x, float y, Color color) {
            this.x = x;
            this.y = y;
            this.direction = Direction.Up;
            this.color = color;
        }

        public void draw() {
            Fill.scanline((int) x - 7, (int) y - 7, (int) x + 7, (int) y + 7, color);
            Circles.fillCircle((int) x, (int) y - 5, 7, color);

            // eyes
            Circles.fillCircle((int) x - 3, (int) y - 5, 2, Color.WHITE);
            Circles.fillCircle((int) x + 3, (int) y - 5, 2, Color.WHITE);
            Fill.scanline((int) x - 4, (int) y - 6, (int) x - 3, (int) y - 4, Color.BLUE);
            Fill.scanline((int) x + 3, (int) y - 6, (int) x + 4, (int) y - 4, Color.BLUE);
        }

        public void update(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public void updateColor(Color color) {
            this.color = color;
        }

        public void moveGhost() {
            switch (direction) {
                case Up:
                    x = x + 0;
                    y = y + 3;
                    break;
                case Down:
                    x = x + 0;
                    y = y - 3;
                    break;
                case Left:
                    x = x - 3;
                    y = y + 0;
                    break;
                case Right:
                    x = x + 3;
                    y = y + 0;
                    break;
            }

            if (wallCollision(x, y)) {
                changeDirection();
            }
        }

        private void changeDirection() {
            Random rng = new Random();
            Direction newDirection;
            do {
                newDirection = Direction.values()[rng.nextInt(4)];
            } while (newDirection == direction);
            direction = newDirection;
        }

        private boolean wallCollision(float x, float y) {
            List<Walls> walls = List.of(Walls.getWalls());
            for (Walls wall : walls) {
                if (x >= wall.x1 && x <= wall.x2 && y >= wall.y1 && y <= wall.y2) { // collision
                    if (direction == Direction.Up) { // move ghost away from wall
                        y = (float) wall.y2 + 2;
                    } else if (direction == Direction.Down) {
                        y = (float) wall.y1 - 2;
                    } else if (direction == Direction.Left) {
                        x = (float) wall.x2 + 2;
                    } else if (direction == Direction.Right) {
                        x = (float) wall.x1 - 2;
                    }
                    return true;
                }
            }
            return false;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }
    }

    public static class Pacman {
        public static float x;
        public static float y;
        private static Direction direction;
        private float radius;

        public Pacman(float x, float y, Direction direction, float radius) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.radius = radius;
        }

        public void movePacman() {
            switch (direction) {
                case Up:
                    x = x + 0;
                    y = y - 1;
                    break;
                case Down:
                    x = x + 0;
                    y = y + 1;
                    break;
                case Left:
                    x = x - 1;
                    y = y + 0;
                    break;
                case Right:
                    x = x + 1;
                    y = y + 0;
                    break;
            }

            if (wallCollision(x, y)) {
                changeDirection();
            }

            if (x <= 1 && (y >= 290 && y <= 310)) {
                x = 599;
            }
        }

        private void changeDirection() {
            Random rng = new Random();
            Direction newDirection;
            do {
                newDirection = Direction.values()[rng.nextInt(4)];
            } while (newDirection == direction);
            direction = newDirection;
        }

        public static void userChangeDirection(Direction direction) {
            Pacman.direction = direction;
        }

        public void draw(float mounth) {
            Circles.drawCircleMidPoint((int) x, (int) y, (int) radius, Color.YELLOW);
            Fill.floodFill((int) x, (int) y, Color.BLACK, Color.YELLOW);
            switch (direction) {
                case Up:
                    Figures.fillTriangle((int) (x - mounth), (int) (y - radius), (int) (x + mounth), (int) (y - radius), (int) x, (int) y, Color.BLACK);
                    break;
                case Down:
                    // add +1 because triangle doesn't fill all the circle
                    Figures.fillTriangle((int) (x - mounth), (int) (y + radius + 1), (int) (x + mounth), (int) (y + radius + 1), (int) x, (int) y, Color.BLACK);
                    break;
                case Left:
                    Figures.fillTriangle((int) (x - radius), (int) (y + mounth), (int) (x - radius), (int) (y - mounth), (int) x, (int) y, Color.BLACK);
                    break;
                case Right:
                    Figures.fillTriangle((int) (x + radius), (int) (y + mounth), (int) (x + radius), (int) (y - mounth), (int) x, (int) y, Color.BLACK);
                    break;
            }
        }

        public static float getX() {
            return x;
        }

        public static float getY() {
            return y;
        }

        private boolean wallCollision(float x, float y) {
            List<Walls> walls = List.of(Walls.getWalls());
            for (Walls wall : walls) {
                if (x >= wall.x1 && x <= wall.x2 && y >= wall.y1 && y <= wall.y2) {
                    if (direction == Direction.Up) {
                        y = y + 2;
                    } else if (direction == Direction.Down) {
                        y = y - 2;
                    } else if (direction == Direction.Left) {
                        x = x + 2;
                    } else if (direction == Direction.Right) {
                        x = x - 2;
                    }
                    return true;
                }
            }
            return false;
        }

        public void handleDeath(float increment) {
            float[][] vertices = new float[2][3];
            vertices[0] = new float[] { x - 5, y - 5, 0 };
            vertices[1] = new float[] { x + 5, y + 5, 0 };
            resizePacman(vertices, Color.YELLOW);
        }

        // Resize the pacman when it dies
        private void resizePacman(float[][] vertices, Color color) {
            float radius = (vertices[1][0] - vertices[0][0]) / 2;
            // Fill.circle(x, y, radius, color);
            Circles.drawCircleMidPoint((int) x, (int) y, (int) radius, color);
            Fill.floodFill((int) x, (int) y, Color.BLACK, color);
        }

    }
}
