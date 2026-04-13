import boundary.GameUI;
import control.GameManager;

public class App {
    public static void main(String[] args) throws Exception {
        GameManager gm = new GameManager(new GameUI());
        gm.startGame();
    }
}
