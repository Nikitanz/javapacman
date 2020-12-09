import java.util.HashSet;
import java.util.Set;

/* Ghost class conitrols the ghost. */
class Ghost extends Mover {
    /* Direction ghost is heading */
    Direction direction;

    /* Last ghost location*/
    int lastX;
    int lastY;

    /* Current ghost location */
    int x;
    int y;

    /* The pellet the ghost is on top of */
    int pelletX, pelletY;

    /* The pellet the ghost was last on top of */
    int lastPelletX, lastPelletY;

    /*Constructor places ghost and updates states*/
    public Ghost(int x, int y) {
        direction = new DirectionLeft();
        pelletX = x / gridSize - 1;
        pelletY = x / gridSize - 1;
        lastPelletX = pelletX;
        lastPelletY = pelletY;
        this.lastX = x;
        this.lastY = y;
        this.x = x;
        this.y = y;
    }

    /* update pellet status */
    public void updatePellet() {
        int tempX, tempY;
        tempX = x / gridSize - 1;
        tempY = y / gridSize - 1;
        if (tempX != pelletX || tempY != pelletY) {
            lastPelletX = pelletX;
            lastPelletY = pelletY;
            pelletX = tempX;
            pelletY = tempY;
        }

    }

    /* Determines if the location is one where the ghost has to make a decision*/
    public boolean isChoiceDest() {
        return x % gridSize == 0 && y % gridSize == 0;
    }

    /* Chooses a new direction randomly for the ghost to move */
    public Direction newDirection() {
        int random;
        Direction backwards = direction.getOpp();
        int newX = x, newY = y;
        int lookX = x, lookY = y;
        Set<String> set = new HashSet<String>();
        Direction newDirection = backwards;
        /* While we still haven't found a valid direction */
        while (newDirection.getClass().equals(backwards.getClass()) || !isValidDest(lookX, lookY)) {
            /* If we've tried every location, turn around and break the loop */
            if (set.size() == 3) {
                newDirection = backwards;
                break;
            }

            newX = x;
            newY = y;
            lookX = x;
            lookY = y;

            /* Randomly choose a direction */
            random = (int) (Math.random() * 4) + 1;
            if (random == 1) {
                newDirection = new DirectionLeft();
                newX -= increment;
                lookX -= increment;
            } else if (random == 2) {
                newDirection = new DirectionRight();
                newX += increment;
                lookX += gridSize;
            } else if (random == 3) {
                newDirection = new DirectionUp();
                newY -= increment;
                lookY -= increment;
            } else if (random == 4) {
                newDirection = new DirectionDown();
                newY += increment;
                lookY += gridSize;
            }
            if (!newDirection.getClass().equals(backwards.getClass())) {
                set.add(newDirection.getClass().getName());
            }
        }
        return newDirection;
    }

    /* Random move function for ghost */
    public void move() {
        lastX = x;
        lastY = y;

        /* If we can make a decision, pick a new direction randomly */
        if (isChoiceDest()) {
            direction = newDirection();
        }

        /* If that direction is valid, move that way */
        if (isValidDest(x + direction.getCheckValX(increment,gridSize), y)){
            x += direction.getX() * increment;
        }

        if (isValidDest(x, y + direction.getCheckValY(increment,gridSize))){
            y += direction.getY() * increment;
        }
    }
}