public interface Direction {
    int getX();
    int getY();
    int getCheckValX(int increment, int gridSize);
    int getCheckValY(int increment, int gridSize);
    Direction getOpp();
}
