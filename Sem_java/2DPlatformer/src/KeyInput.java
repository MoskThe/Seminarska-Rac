import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import enteties.Player;

import static utilz.Const.Directions.*;

public class KeyInput implements KeyListener{

    public Game game;
    public KeyInput(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state) {
            case MENU: 
                game.getMenu().keyPressed(e);
                break;
            case PLAYING: 
                game.getPlaying().keyPressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (Gamestate.state) {
            case MENU: 
                game.getMenu().keyReleased(e);
                break;
            case PLAYING: 
                game.getPlaying().keyReleased(e);
                break;
            default:
                break;
        }
    }
    
}
