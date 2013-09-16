package com.fls.main.entitys;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.fls.main.art.Sprites;
import com.fls.main.core.Level;
import com.fls.main.core.Stats;
import com.fls.main.core.achive.Achievment;
import com.fls.main.sound.Audio;

public class Player extends Entity {

    private int dir = 0, climbDir = -1;;
    private boolean walking = false, jumping = false, climbing = false, readSign = false, falling = false;
    private int xImg = 0, yImg = 0, frames = 0;
    private int jumpCount = 0, jumpDelay = 0, shootDelay = 0, fallTime = 0;
    public int pickups = 0;
    private double yAim = 0, aimAngle = 0;
    public BufferedImage[][] sheet = Sprites.entitys;

    public Player() {
        speed = 0.5;
        w = 30;
        h = 30;
    }

    public void render(Graphics g) {
        if (walking) {
            xImg = 0;
            yImg = 0;
            frames++;
        } else {
            xImg = 0;
            yImg = 1;
            frames++;
        }

        if (jumping || falling) {
            xImg = 0;
            yImg = 2;
            frames = 0;
        }

        if (againstWall) {
            xImg = 4;
            yImg = 0;
            frames = 0;
            if (jumping) {
                xImg = 5;
            }
        }

        if (climbing) {
            if (climbDir == 1) {
                yImg = 3;
                frames++;
            } else if (climbDir == -1) {
                yImg = 3;
                --frames;
            } else {
                xImg = 0;
            }
        }
        
        if(climbing && againstWall){
            
        }

        g.drawImage(sheet[xImg + (frames / 10 % 4)][yImg], (int) x, (int) y, null);
    }

    public void tick(boolean up, boolean down, boolean left, boolean right, boolean jump, boolean shoot) {
        walking = false;
        if (dir == 1) sheet = Sprites.entitys;
        if (dir == -1) sheet = Sprites.entitys2;

        readSign = onGround & up;
        // yAim = 0;
        // aimAngle = -0.2;

        if (up) {
            yAim--;
            aimAngle -= 0.8;
        }
        else if (down) {
            yAim++;
            aimAngle += 0.8;
        }

        if (left) {
            if (!jumping) walking = true;
            dir = -1;
            xx -= speed;// * (walking ? Level.friction : 0.5);
        }
        if (right) {
            if (!jumping) walking = true;
            dir = 1;
            xx += speed;// * (walking ? Level.friction : 0.5);
        }

        if (canClimb) {
            if (up) {
                climbing = true;
                yy -= 0.25;
                climbDir = 1;
            }
            else if (down) {
                climbing = true;
                yy += 0.25;
                climbDir = -1;
            }
            else {
                yy = 0;
                climbDir = 0;
            }
        }

        if (shoot && shootDelay == 0) {
            shoot();
            shootDelay = 20;
        }

        if (jump && jumpDelay == 0 && jumpCount <= 4) {
            level.unlockAchive(Achievment.jump);
            Stats.instance.jumps++;
            if (jumpCount < 2 || (jumpCount <= 4 && againstWall)) Audio.jump.play();
            jumping = true;
            jumpDelay = 20;
            if (onGround) {
                yy -= 2 + Math.abs(xx) * 0.5;
            }
            if (jumpCount < 2) {
                jumpCount++;
                yy -= 2.5;
            }
            if (againstWall && jumpCount >= 1 && jumpCount <= 4) {
                jumpDelay = 20;
                if (dir == 1) xx -= (Math.abs(xx) + 5);
                else if (dir == -1) xx += (Math.abs(xx) + 5);
                yy -= 2;
                jumpCount++;

            }
        }

        tryMove(xx, yy);
        xx *= 0.7;
        if (!canClimb) {
            if (yy < 0 && up) {
                yy *= 0.992;
                yy += Level.gravity * 0.5;
            } else if (yy < 0 && againstWall) {
                yy *= 0.992;
                yy += 0.05 * 0.5;
            } else {
                yy *= Level.friction;
                yy += Level.gravity;
            }
        }

        if (onGround) {
            jumpCount = 0;
            jumping = false;
            climbing = false;
            falling = false;
            fallTime = 0;
        }
        if (!onGround && !jumping) {
            fallTime++;
            if (fallTime >= 20) falling = true;
        }
        if (jumpDelay > 0) jumpDelay--;
        if (shootDelay > 0) shootDelay--;

        if (againstWall) {
            walking = false;
        }

        if (y < 5) level.trans(0, -1);
        if (y > 360 - w + 10 - 5) level.trans(0, 1);
        if (x < 5) level.trans(-1, 0);
        if (x > 500 - h + 10 - 5) level.trans(1, 0);
    }

    private void shoot() {
        double pow = 3;
        double xx = x + w / 2.0 - 2.5 + dir * 7;
        double yy = y + h / 2.0 - 2.5 + yAim * 2;
        double xAim = Math.cos(aimAngle) * dir * pow;
        double yAim = Math.sin(aimAngle) * pow;
        double xxa = xx + xAim;
        double yya = yy + yAim;
        xx = x + w / 2.0 - 2.5;
        level.addEntity(new NStar(xx, yy, xxa, yya));
    }

    public void readSign(Sign sign) {
        if (sign.autoRead || readSign) {
            sign.autoRead = false;
            level.readSign(sign);
        }
    }

    public void die() {
        super.die();
        Audio.die.play();
        for (int i = 0; i < 32; i++) {
            level.addEntity(new PlayerGore(x + random.nextDouble() * w, y + random.nextDouble() * h));
        }
        Stats.instance.deaths++;
    }

    public void explode(Explosion e) {
        die();
    }
}
