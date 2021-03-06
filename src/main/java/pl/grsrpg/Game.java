package pl.grsrpg;

import lombok.Getter;
import org.fusesource.jansi.AnsiConsole;
import pl.grsrpg.config.Config;
import pl.grsrpg.board.IBoard;
import pl.grsrpg.board.Board;
import pl.grsrpg.utils.IOUtils;

import java.io.File;
import java.io.IOException;

@Getter
public class Game {
    private static Config config;
    private static Game game;
    private IBoard board;

    public static Config getConfig() {
        return config;
    }

    public static Game getGame() {
        return game;
    }

    public IBoard getBoard() {
        return board;
    }

    private Game() {
        if (!loadGame()) {
            this.board = new Board();
            this.board.startGame();
        }
    }

    public static void main(String[] args) {
        AnsiConsole.systemInstall(); //Windows ANSI ESC Codes Support
        loadConfig();
        game = new Game();
        game.startGameLoop();
        AnsiConsole.systemUninstall();
    }

    private static void loadConfig() {
        File config = IOUtils.openFile("config/config.yml", "config.yml");
        try {
            Game.config = IOUtils.getMapper().readValue(config, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean loadGame() {
        File save = new File(IOUtils.getDataPath() + "/data/save.yml");
        if (save.exists()) {
            System.out.print("Found previous game save, load it?[Y/n] ");
            String choice = IOUtils.getScanner().next();
            if ("n".equals(choice))
                return false;
            try {
                this.board = IOUtils.getMapper().readValue(save, Board.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private void startGameLoop() {
        this.board.gameLoop();
    }
}
