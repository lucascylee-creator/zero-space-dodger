import zero.Color;
import zero.GameApp;
import zero.Key;
import zero.ui.Button;

public class Main extends GameApp {
    private static final int WIN_SCORE = 20;

    private Player alien = new Player(300, 340);
    private boolean playing = false;
    private boolean won = false;
    private double debrisX = 200;
    private double debrisY = 0;
    private double debrisSpeed = 180;
    private int score = 0;
    private int lastScore = 0;
    private double seed = 12345;

    @Override
    public void setup() {
        Button restartButton = new Button("Restart");
        restartButton.onClick(() -> restart());
        controls(restartButton);
    }

    private double nextRandom() {
        seed = (seed * 9301 + 49297) % 233280;
        return seed / 233280.0;
    }

    private void resetGame() {
        alien.reset(width() / 2, height() - 60);
        debrisX = 50 + nextRandom() * (width() - 100);
        debrisY = 0;
        debrisSpeed = 180;
        score = 0;
        won = false;
    }

    private void restart() {
        resetGame();
        lastScore = 0;
        playing = true;
    }

    @Override
    public void update(double seconds) {
        nextRandom();
        if (!playing) {
            if (keyPressed(Key.SPACE) || keyPressed(Key.ENTER)) {
                restart();
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
            debrisX = 30 + nextRandom() * (width() - 60);
            score++;
            debrisSpeed += 5;
            if (score >= WIN_SCORE) {
                won = true;
                lastScore = score;
                playing = false;
                return;
            }
        }

        double dx = alien.getX() - debrisX;
        double dy = alien.getY() - debrisY;
        if (Math.sqrt(dx * dx + dy * dy) < 30) {
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
            if (won) {
                textCentered("YOU WIN!", width() / 2, height() / 2 - 60);
            } else {
                textCentered("SPACE DODGER", width() / 2, height() / 2 - 60);
            }
            fill(Color.WHITE);
            textSize(16);
            textCentered("Use LEFT / RIGHT arrows. Dodge " + WIN_SCORE + " pieces of debris to win!", width() / 2, height() / 2 - 10);
            textCentered("Press SPACE or click Restart to play", width() / 2, height() / 2 + 20);
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
        text("Score: " + score + " / " + WIN_SCORE, 20, 30);
    }

    public static void main(String[] args) {
        launch(args);
    }
}