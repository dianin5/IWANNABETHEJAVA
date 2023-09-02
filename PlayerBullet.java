package IWANNABETHEJAVA;

import java.awt.*;
import javax.swing.*;

/**
 * 플레이어의 총알을 나타내는 클래스입니다.
 */
public class PlayerBullet {
    public boolean shootSound;
    private Image bulletImage;
    private int bulletCx;
    private int bulletCy;
    private int bulletWidth;
    private int bulletHeight;
    private int bulletSpeed = 5;
    private boolean bulletReady;
    private ImageIcon bulletSrc;

    /**
     * PlayerBullet 클래스의 새 인스턴스를 생성합니다.
     *
     * @param x 총알의 초기 X 좌표
     * @param y 총알의 초기 Y 좌표
     */
    public PlayerBullet(int x, int y) {
        bulletCx = x;
        bulletCy = y;
        bulletReady = true;
        load();
    }

    /**
     * 총알 이미지를 로드합니다.
     */
    public void load() {
        bulletSrc = new ImageIcon("src/IWANNABETHEJAVA/image/Player/playerBullet_2.png");
        bulletImage = bulletSrc.getImage();
        bulletWidth = bulletImage.getWidth(null);
        bulletHeight = bulletImage.getHeight(null);
    }

    /**
     * 총알의 X 좌표를 반환합니다.
     *
     * @return 총알의 X 좌표
     */
    public int getX() {
        return bulletCx + 30;
    }

    /**
     * 총알의 Y 좌표를 반환합니다.
     *
     * @return 총알의 Y 좌표
     */
    public int getY() {
        return bulletCy + 9;
    }

    /**
     * 총알의 너비를 반환합니다.
     *
     * @return 총알의 너비
     */
    public int getWidth() {
        return bulletWidth;
    }

    /**
     * 총알의 높이를 반환합니다.
     *
     * @return 총알의 높이
     */
    public int getHeight() {
        return bulletHeight;
    }

    /**
     * 총알의 이미지를 반환합니다.
     *
     * @return 총알 이미지
     */
    public Image getImage() {
        return bulletImage;
    }

    /**
     * 총알이 준비 상태인지 여부를 반환합니다.
     *
     * @return 준비 상태인지 여부
     */
    public boolean isReady() {
        return bulletReady;
    }

    /**
     * 총알의 준비 상태를 설정합니다.
     *
     * @param bulletReady 준비 상태
     */
    public void setReady(boolean bulletReady) {
        this.bulletReady = bulletReady;
    }

    /**
     * 총알을 이동시킵니다.
     */
    public void move() {
        bulletCx += bulletSpeed;
        if (bulletCx > Game.SCREEN_WIDTH) {
            bulletReady = false;
        }
    }
}