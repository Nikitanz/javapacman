public class DirectionUp implements Direction{
    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return -1;
    }

    @Override
    public int getCheckValX(int increment, int gridSize) {
        return 0;
    }

    @Override
    public int getCheckValY(int increment, int gridSize) {
        return -increment;
    }

    @Override
    public Direction getOpp() {
        return new DirectionDown();
    }
}
