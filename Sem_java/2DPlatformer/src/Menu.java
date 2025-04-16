import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import utilz.Const;
import utilz.LoadSave;

public class Menu implements Statemethods{
    private Game game;

    BufferedImage background;
    Font myFont = new Font ("Courier New", 1, 50);
    Font Title = new Font ("Courier New", 1, 100);
    AudioInputStream MenuMusic;
    Clip MenuSong;

    public Menu(Game game) {
        this.game = game;

        background = LoadSave.LoadImg(LoadSave.PLAYING_BACKGROUND);

        try {
            MenuMusic = AudioSystem.getAudioInputStream(new File("2DPlatformer/src/assets/MenuSong.wav"));
            MenuSong = AudioSystem.getClip();
            MenuSong.open(MenuMusic);
        } catch (Exception e) {
            // TODO: handle exception
        }
        MenuSong.loop(Clip.LOOP_CONTINUOUSLY);
    }
    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(background, 0, 0,Const.GameStats.PANEL_WIDTH, Const.GameStats.PANEL_HEIGHT, null);

        g.setColor(Color.black);
        

        //myFont.
        g.setFont(Title);
        g.drawString("MENU", 40, 100);
        g.setFont (myFont);
        g.drawString("CONTROLS:", 40, 200);
        g.drawString("Move - w,a,s,d", 100, 260);
        g.drawString("Jump - space", 100, 320);
        g.drawString("Dash - shift (while in the air)", 100, 380);
        g.drawString("Return - escape", 100, 440);

        g.drawString("Reach the end to win the game", 40, 600);
        g.drawString("Press --ENTER-- to start the game", 40, 660);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            MenuSong.setFramePosition(0); MenuSong.stop(); game.getPlaying().BitMusic.loop(Clip.LOOP_CONTINUOUSLY); Gamestate.state = Gamestate.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    } 
}
