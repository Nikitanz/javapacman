public class DirectionLeft implements Direction{
    @Override
    public int getX() {
        return -1;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getCheckValX(int increment, int gridSize) {
        return -increment;
    }

    @Override
    public int getCheckValY(int increment, int gridSize) {
        return 0;
    }

    @Override
    public Direction getOpp() {
        return new DirectionRight();
    }
}
