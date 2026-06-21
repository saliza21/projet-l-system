package model;

public class BranchState {
    private final int x;
    private final int y;
    private final double angle;

    public BranchState(int x, int y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }
}
