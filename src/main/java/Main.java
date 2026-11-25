import zero.Color;
import zero.GameApp;
import zero.Key;

public class Main extends GameApp {
    private Player alien = new Player(300, 340);
    private boolean playing = false;
    private double debrisX = 200;
    private double debrisY = 0;
    private double debrisSpeed = 180;
    private int score = 0;
    private int lastScore = 0;

    private void resetGame() {
        alien.reset(width() / 2, height() - 60);
        debrisX = 50 + Math.random() * (width() - 100);
        debrisY = 0;
        debrisSpeed = 180;
        score = 0;
    }

    @Override
    public void update(double seconds) {
        if (!playing) {
            if (keyPressed(Key.SPACE) || keyPressed(Key.ENTER)) {
                resetGame();
                playing = true;
            }
            return;
        }

        int direction = 0;
        if (keyDown(Key.LEFT)) direction--;
        if (keyDown(Key.RIGHT)) direction++;
        alien.update(direction, seconds, width());

        debrisY += debrisSpeed * seconds;
        if (debrisY > height()) {
            debrisY = 0;
            debrisX = 30 + Math.random() * (width() - 60);
            score++;
            debrisSpeed += 5;
        }

        double dx = alien.getX() - debrisX;
        double dy = alien.getY() - debrisY;
        if (Math.hypot(dx, dy) < 30) {
            lastScore = score;
            playing = false;
        }
    }

    @Override
    public void draw() {
        if (!playing) {
            background(Color.rgb(15, 15, 35));
            fill(Color.rgb(255, 224, 145));
            textSize(32);
            textCentered("SPACE DODGER", width() / 2, height() / 2 - 60);
            fill(Color.WHITE);
            textSize(16);
            textCentered("Use LEFT / RIGHT arrows to dodge debris!", width() / 2, height() / 2 - 10);
            textCentered("Press SPACE to start", width() / 2, height() / 2 + 20);
            if (lastScore > 0) {
                textCentered("Last score: " + lastScore, width() / 2, height() / 2 + 60);
            }
            return;
        }

        background(Color.rgb(20, 24, 40));
        fill(Color.rgb(255, 80, 80));
        circle(debrisX, debrisY, 25);
        fill(Color.rgb(0, 255, 200));
        circle(alien.getX(), alien.getY(), 40);
        fill(Color.WHITE);
        textSize(18);
        text("Score: " + score, 20, 30);
    }

    public static void main(String[] args) {
        launch(args);
    }
}