import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import enteties.Player;
import utilz.Const;
import utilz.LoadSave;

public class Playing implements Statemethods {
        private Player player;
        private LevelHandler levelHandler;
        private Game game;
        private FileWriter writer;
        private BufferedWriter buffer;

        private long timeCheck = 0;
        private long timePlaying = 0l;

        private int xLvlOffset;
        private int leftBorder = (int) (0.3 *Const.GameStats.PANEL_WIDTH);
        private int rightBorder = (int) (0.7 *Const.GameStats.PANEL_WIDTH);
        private int lvlTileWide = levelHandler.GetLvlData()[0].length;
        private int maxTilesOffset = lvlTileWide - Const.GameStats.TILES_WIDTH;
        private int maxLvlOffset = maxTilesOffset * Const.GameStats.TILE_SIZE;
        
        private BufferedImage Background;

        //sounds
        AudioInputStream music;
        public Clip BitMusic;
        
        public Playing(Game game) throws IOException, UnsupportedAudioFileException, LineUnavailableException{
                this.game = game;
                initClasses();

                Background = LoadSave.LoadImg(LoadSave.PLAYING_BACKGROUND);

                music = AudioSystem.getAudioInputStream(new File("2DPlatformer/src/assets/Music.wav"));
                BitMusic = AudioSystem.getClip();
                BitMusic.open(music);
                //try {
                //writer = new FileWriter("2DPlatformer/src/HighScore.txt");
                //buffer = new BufferedWriter(writer);
                //}catch (IOException e){

                //

        }

        public void initClasses() throws IOException, UnsupportedAudioFileException, LineUnavailableException{
                levelHandler = new LevelHandler(game);
                player = new Player(100, 300, (int)(32*Const.GameStats.SCALE), (int)(32*Const.GameStats.SCALE));
                player.loadLvlData(levelHandler.GetLevel().lvlData);
        }

        @Override
        public void update() {
                updateTime();

                if(player.died) {
                        resetPlaying();
                        player.died = false;
                }

                if(player.getHitbox().x + player.getHitbox().width > levelHandler.GetLvlData()[0].length * Const.GameStats.TILE_SIZE - Const.GameStats.TILE_SIZE - 2) winGame();

                levelHandler.update();
                player.update();
                checkBorder();
        }
        private void updateTime() {
                if(timeCheck == 0) timeCheck = System.nanoTime();
                timePlaying = System.nanoTime() - timeCheck;
                System.out.println(timePlaying);
        }

        private void resetPlaying() {
                player.getHitbox().x = 100;
                player.getHitbox().y = 300;
                xLvlOffset = 0;

                player.inAir = true;
                player.left = false;
                player.right = false;
                player.up = false;
                player.down = false;
        }
        private void resetTimer() {
                timePlaying = 0;
                timeCheck = 0;
        }
        private void winGame() {
                
                double timeInSec = (double)(timePlaying)/1000000000;
                int sec = (int)timeInSec;
                int sec00 = (int)(timeInSec*100 - sec*100);

                System.out.println("" + sec + "." + sec00 + " sec");
                
                resetPlaying();
                resetTimer();

                BitMusic.setMicrosecondPosition(0);
                BitMusic.stop();
                game.getMenu().MenuSong.loop(Clip.LOOP_CONTINUOUSLY);
                Gamestate.state = Gamestate.MENU;
        }

        private void checkBorder() {
                int playerX = (int) player.getHitbox().x;
                int diff = playerX - xLvlOffset;
                if(diff > rightBorder)
                        xLvlOffset += diff - rightBorder;
                else if(diff < leftBorder)
                        xLvlOffset += diff - leftBorder;

                if(xLvlOffset > maxLvlOffset) xLvlOffset = maxLvlOffset;
                if(xLvlOffset < 0) xLvlOffset = 0;
        }

        @Override
        public void draw(Graphics g) {
                g.drawImage(Background,0, 0, Const.GameStats.PANEL_WIDTH, Const.GameStats.PANEL_HEIGHT, null);

                levelHandler.draw(g, xLvlOffset);
                player.render(g, xLvlOffset);
        }
        public Player rePlayer() {
                return player;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
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
                switch (e.getKeyCode()) {
                        case KeyEvent.VK_W: player.up = true;break;
                        case KeyEvent.VK_A: player.left = true;break;
                        case KeyEvent.VK_S: player.down = true;break;
                        case KeyEvent.VK_D: player.right = true;break;
                        case KeyEvent.VK_SPACE: player.jump = true;break;
                        case KeyEvent.VK_SHIFT: player.dash = true;break;
                        case KeyEvent.VK_ESCAPE: resetPlaying(); resetTimer(); BitMusic.setMicrosecondPosition(0); BitMusic.stop();game.getMenu().MenuSong.loop(Clip.LOOP_CONTINUOUSLY);;Gamestate.state = Gamestate.MENU;break;
                }
        }

        @Override
        public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                        case KeyEvent.VK_W: player.up = false;break;
                        case KeyEvent.VK_A: player.left = false;break;
                        case KeyEvent.VK_S: player.down = false;break;
                        case KeyEvent.VK_D: player.right = false;break;
                        case KeyEvent.VK_SPACE: player.jump = false;break;
                        case KeyEvent.VK_SHIFT: player.dash = false;break;
                }
        }
}
