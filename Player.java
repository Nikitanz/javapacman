import java.util.HashSet;
import java.util.Set;

/* This is the pacman object */
class Player extends Mover {
    /* Direction is used in demoMode, currDirection and desiredDirection are used in non demoMode*/
    Direction direction;
    Direction currDirection;
    Direction desiredDirection;

    /* Keeps track of pellets eaten to determine end of game */
    int pelletsEaten;

    /* Last location */
    int lastX;
    int lastY;

    /* Current location */
    int x;
    int y;

    /* Which pellet the pacman is on top of */
    int pelletX;
    int pelletY;

    /* teleport is true when travelling through the teleport tunnels*/
    boolean teleport;

    /* Stopped is set when the pacman is not moving or has been killed */
    boolean stopped = false;

    /* Constructor places pacman in initial location and orientation */
    public Player(int x, int y) {

        teleport = false;
        pelletsEaten = 0;
        pelletX = x / gridSize - 1;
        pelletY = y / gridSize - 1;
        this.lastX = x;
        this.lastY = y;
        this.x = x;
        this.y = y;
        currDirection = new DirectionLeft();
        desiredDirection = new DirectionLeft();
    }

    /* This function is used for demoMode.  It is copied from the Ghost class.  See that for comments */
    public Direction newDirection() {
        int random;
        Direction backwards = new DirectionUp();
        int newX = x, newY = y;
        int lookX = x, lookY = y;
        Set<String> set = new HashSet<String>();
        backwards = direction.getOpp();
        Direction newDirection = backwards;
        while (newDirection.getClass().equals(backwards.getClass())  || !isValidDest(lookX, lookY)) {
            if (set.size() == 3) {
                newDirection = backwards;
                break;
            }
            newX = x;
            newY = y;
            lookX = x;
            lookY = y;
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

    /* This function is used for demoMode.  It is copied from the Ghost class.  See that for comments */
    public boolean isChoiceDest() {
        return x % gridSize == 0 && y % gridSize == 0;
    }

    /* This function is used for demoMode.  It is copied from the Ghost class.  See that for comments */
    public void demoMove() {
        lastX = x;
        lastY = y;
        if (isChoiceDest()) {
            direction = newDirection();
        }
        if (isValidDest(x + direction.getCheckValX(increment,gridSize), y)){
            x += direction.getX() * increment;
        } else if (direction instanceof DirectionLeft && y == 9 * gridSize && x < 2 * gridSize){
            x = max - gridSize * 1;
            teleport = true;
        } else if (direction instanceof DirectionRight && y == 9 * gridSize && x > max - gridSize * 2){
            x = 1 * gridSize;
            teleport = true;
        }

        if (isValidDest(x, y + direction.getCheckValX(increment,gridSize))){
            y += direction.getY() * increment;
        }
        currDirection = direction;
        frameCount++;
    }

    /* The move function moves the pacman for one frame in non demo mode */
    public void move() {
        int gridSize = 20;
        lastX = x;
        lastY = y;

        /* Try to turn in the direction input by the user */
        /*Can only turn if we're in center of a grid*/
        if (x % 20 == 0 && y % 20 == 0 ||
                /* Or if we're reversing*/
                (desiredDirection instanceof DirectionLeft  && currDirection instanceof DirectionRight) ||
                (desiredDirection instanceof DirectionRight && currDirection instanceof DirectionLeft) ||
                (desiredDirection instanceof DirectionUp    && currDirection instanceof DirectionDown) ||
                (desiredDirection instanceof DirectionDown  && currDirection instanceof DirectionUp)
        ) {
            if (isValidDest(x + desiredDirection.getCheckValX(increment,gridSize), y)){
                x += desiredDirection.getX() * increment;
            }

            if (isValidDest(x, y + desiredDirection.getCheckValY(increment,gridSize))){
                y += desiredDirection.getY() * increment;
            }
        }
        /* If we haven't moved, then move in the direction the pacman was headed anyway */
        if (lastX == x && lastY == y) {
            if (isValidDest(x + currDirection.getCheckValX(increment,gridSize), y)){
                x += currDirection.getX() * increment;
            } else if (currDirection instanceof DirectionLeft && y == 9 * gridSize && x < 2 * gridSize){
                x = max - gridSize * 1;
                teleport = true;
            } else if (currDirection instanceof DirectionRight && y == 9 * gridSize && x > max - gridSize * 2){
                x = 1 * gridSize;
                teleport = true;
            }

            if (isValidDest(x, y + currDirection.getCheckValY(increment,gridSize))){
                y += currDirection.getY() * increment;
            }
        }
        /* If we did change direction, update currDirection to reflect that */
        else {
            currDirection = desiredDirection;
        }

        /* If we didn't move at all, set the stopped flag */
        if (lastX == x && lastY == y)
            stopped = true;

            /* Otherwise, clear the stopped flag and increment the frameCount for animation purposes*/
        else {
            stopped = false;
            frameCount++;
        }
    }

    /* Update what pellet the pacman is on top of */
    public void updatePellet() {
        if (x % gridSize == 0 && y % gridSize == 0) {
            pelletX = x / gridSize - 1;
            pelletY = y / gridSize - 1;
        }
    }
}
