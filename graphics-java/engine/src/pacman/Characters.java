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
            Fill.scanline(x - 7, y - 7, x + 7, y + 7, color);
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
                    y = y + 2;
                    break;
                case Down:
                    x = x + 0;
                    y = y - 2;
                    break;
                case Left:
                    x = x - 2;
                    y = y + 0;
                    break;
                case Right:
                    x = x + 2;
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
            List<Wall> walls = getWalls();
            for (Wall wall : walls) {
                if (x >= wall.x1 && x <= wall.x2 && y >= wall.y1 && y <= wall.y2) {
                    if (direction == Direction.Up) {
                        y -= 5;
                    }
                    if (direction == Direction.Down) {
                        y += 5;
                    }
                    if (direction == Direction.Left) {
                        x += 5;
                    }
                    if (direction == Direction.Right) {
                        x -= 5;
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
}
