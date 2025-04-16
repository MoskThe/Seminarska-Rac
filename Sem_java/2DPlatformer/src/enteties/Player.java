package enteties;
import static utilz.Const.Directions.*;
import static utilz.Const.PlayerConst.*;

import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import static utilz.HelpMethods.canMoveHere;
import static utilz.HelpMethods.MoveNextToWall;
import static utilz.HelpMethods.MovePlayerToFloor;
import static utilz.HelpMethods.PlayerOnFloor;

import utilz.Const;
import utilz.LoadSave;

public class Player extends Entity{
    public boolean left, up ,down, right, jump, dash;

    private BufferedImage[][] ani;
    private int aniTick, aniIndex, aniSpeed = 20;
    public boolean playerMoving = false;
    private int action = IDLE;
    private float speed = 1.0f * Const.GameStats.SCALE;

    //For jumping and falling
    private float airSpeed = 0f;
    private float gravity = 0.05f * Const.GameStats.SCALE;
    private float jumpSpeed = -3.0f *Const.GameStats.SCALE;
    private float fallSpeedHit = 0.25f *Const.GameStats.SCALE;
    public boolean inAir = false;

    private float xOffSet = 7*Const.GameStats.SCALE;
    private float yOffSet = 4*Const.GameStats.SCALE;

    //dash
    private float dashTotal = 6.0f;
    private float dashAmountX = 0;
    private float dashAmountY = 0;
    private boolean dashCooldown = true;

    //death
    public boolean died = false;

    private int[][] lvlData;

    public Player(float x, float y, int width, int height) {
        super(x,y, width, height);
        loadAnimations();
        innitRect(x, y, 15*Const.GameStats.SCALE, 27*Const.GameStats.SCALE);
    }

    public void update() {
        if(hitBox.y + hitBox.height == Const.GameStats.PANEL_HEIGHT - 1) died = true;
        updateFrame();
        //updateHitbox();

        setAniamtion();
        updatePos();
    }

    public void render(Graphics g, int xLvlOffset){
        g.drawImage(ani[action][aniIndex], (int)(hitBox.x - xOffSet - xLvlOffset), (int)(hitBox.y - yOffSet), (int)(32*Const.GameStats.SCALE), (int)(32*Const.GameStats.SCALE), null);
        //drawHitbox(g);
    }


    private void setAniamtion() {
        int startAni = action;


        if(playerMoving) 
            action = RUNNING;
        else action = IDLE;

        if(inAir) {
            if(airSpeed<0) action = JUMP;
            else action = FALL; 
        }

        if(startAni != action){
            aniTick = 0;
            aniIndex = 0;
        }
    }

    private void updatePos() {

        playerMoving = false;
        
        if(jump) {
            jump();
        }
        if(dash && dashCooldown) {
            dash();
        }

        if(!left && !right && !inAir) return;

        float xSpeed = 0;

        if(left)
            xSpeed  -= speed;
        if(right)
            xSpeed += speed;
        
        if(!inAir) {
            dashAmountX = 0;
            if(!PlayerOnFloor(hitBox, lvlData)){
                inAir = true;
            }
        }

            if(inAir) {
                if(canMoveHere(hitBox.x, hitBox.y + airSpeed + dashAmountY, hitBox.width, hitBox.height, lvlData)) {
                    hitBox.y += airSpeed;
                    hitBox.y += dashAmountY;
                    airSpeed += gravity;
                    updateXPos(xSpeed, dashAmountX);
                }
                else {
                    hitBox.y = MovePlayerToFloor(hitBox, airSpeed);
                    dashAmountX = 0;
                    if(airSpeed > 0) {
                        inAir = false;
                        dashCooldown = true;
                        airSpeed = 0;
                    }
                    else {
                        airSpeed = fallSpeedHit;
                    }
                    updateXPos(xSpeed, dashAmountX);
                }
            }
            else updateXPos(xSpeed, dashAmountX);
            
            airSpeed += dashAmountY;

            playerMoving = true;

            if(dashAmountX < 0) dashAmountX += 0.15;
            else if(dashAmountX > 0) dashAmountX -= 0.15;
            else dashAmountX = 0;

        //if(canMoveHere(hitBox.x+xSpeed, hitBox.y+ySpeed, hitBox.width, hitBox.height, lvlData)) {
        //    hitBox.x += xSpeed;
        //    hitBox.y += ySpeed;
        //    playerMoving = true;
        //}
    }

    private void dash() {
        if(inAir) {
            if(left) dashAmountX -= dashTotal;
            if(right) dashAmountX += dashTotal;
            if(up) { 
                airSpeed = 0;
                airSpeed -= dashTotal;
            }
            if(down) { 
                airSpeed = 0;
                airSpeed += dashTotal;
            }
            dashCooldown = false;
        }
    }

    private void jump() {
        if(inAir)return;
        else{
            inAir = true;
            airSpeed = jumpSpeed;
        }
    }

    private void updateXPos(float xSpeed, float dashAmountX) {
        if(canMoveHere(hitBox.x + xSpeed + dashAmountX, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
            hitBox.x += xSpeed;
            hitBox.x += dashAmountX;
        }
        else {
            hitBox.x = MoveNextToWall(hitBox, xSpeed + dashAmountX);
        }
    }
    private void loadAnimations() {
        BufferedImage img = LoadSave.LoadImg(LoadSave.PLAYER_ATLAS);
        ani = new BufferedImage[6][8];
        for(int i = 0; i< ani.length; i++)
            for(int j = 0; j< ani[i].length; j++) 
                ani[i][j] = img.getSubimage(32*j, i*32, 32, 32);
    }
    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if(!PlayerOnFloor(hitBox, lvlData)) 
            inAir = true;
    }
    private void updateFrame() {
        aniTick++;
        if(aniTick == aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteNum(action)){
                aniIndex = 0;
            }
        }
    }
}
