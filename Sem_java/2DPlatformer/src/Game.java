import java.awt.Dimension;
import javax.swing.JPanel;

import enteties.Player;
import utilz.Const;

import java.awt.Graphics;

public class Game implements Runnable {
    private Window window;
    private Panel panel;
    private Thread gameThread;
    private final int FPS = 120;
    private final int UPS = 180;

    public Playing playing;
    public Menu menu;

    //file size info
    /*public static final int TILE_SIZE_DEF = 32;
    public static final float SCALE = 2.0f;
    public static final int TILES_WIDTH = 26;
    public static final int TILES_HEIGHT = 14;
    public static final int TILE_SIZE = (int)(TILE_SIZE_DEF * SCALE);
    //panel size
    public static final int PANEL_WIDTH = TILE_SIZE * TILES_WIDTH;
    public static final int PANEL_HEIGHT = TILE_SIZE * TILES_HEIGHT; */

    public Game() {
        menu = new Menu(this);
        playing = new Playing(this);

        this.panel = new Panel(this);
        this.window = new Window(panel);
        panel.requestFocus();

        startGameLoop();
    }
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void update() {
        switch (Gamestate.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
            default:
                break;
        }
    }
    public void render(Graphics g) {
        switch (Gamestate.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
            default:
                break;
        }
    }

    @Override
    public void run() {
        
        double timePerFrame = 1000000000.0 / FPS; // in nanoseconds
        double timePerTick = 1000000000.0 / UPS; // in nanoseconds
        long CurrentTime;

        long PreviousTime= System.nanoTime();

        int frames = 0;
        int updates = 0;
        long LastCheck = 0;

        double deltaT = 0; // Delta time
        double deltaF = 0;
        while(true) {

            CurrentTime = System.nanoTime();

            deltaT += (CurrentTime - PreviousTime) / timePerTick;
            deltaF += (CurrentTime - PreviousTime) / timePerFrame;
            PreviousTime = CurrentTime;
            if(deltaT >= 1) {
                update();
                updates++;
                deltaT--;
            }
            if(deltaF >= 1) {
                panel.repaint();
                frames++;
                deltaF--;
            }

            if(System.currentTimeMillis() - LastCheck >= 1000) {
                LastCheck = System.currentTimeMillis();
                //System.out.println("FPS: " + frames + " UPS" + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
    public Menu getMenu() {
        return menu;
    }
    public Playing getPlaying() {
        return playing;
    }
}