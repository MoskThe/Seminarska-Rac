import javax.imageio.ImageIO;
import javax.swing.JPanel;

import enteties.Player;
import utilz.Const;

import static utilz.Const.PlayerConst.*;
import static utilz.Const.Directions.*;

import java.awt.Dimension;
import java.awt.Graphics;



public class Panel extends JPanel{
    private Game game;
    
   public Panel(Game game) {
        this.game = game;

        MouseInput mouseInput = new MouseInput(game);
        setPanelSize();
        addKeyListener(new KeyInput(game));
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        game.render(g);
    }

    private void setPanelSize() {
        Dimension panelSize = new Dimension(Const.GameStats.PANEL_WIDTH, Const.GameStats.PANEL_HEIGHT);
        System.out.println(Const.GameStats.PANEL_WIDTH + " : " + Const.GameStats.PANEL_HEIGHT);
        setPreferredSize(panelSize);
        setMinimumSize(panelSize);
        setMaximumSize(panelSize);
    }
}