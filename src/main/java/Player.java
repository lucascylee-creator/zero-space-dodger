public class Player {
    private double x;
    private double y;
    private double speed = 250;

    public Player(double startX, double startY) {
        x = startX;
        y = startY;
    }

    public void update(int direction, double seconds, double maxX) {
        x += direction * speed * seconds;
        if (x < 20) x = 20;
        if (x > maxX - 20) x = maxX - 20;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public void reset(double startX, double startY) {
        x = startX;
        y = startY;
    }
}