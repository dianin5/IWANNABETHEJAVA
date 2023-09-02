package IWANNABETHEJAVA;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
/**
 * BossBullet 클래스는 게임에서 보스의 탄환을 나타냅니다.
 */
public class BossBullet {
    private Image bulletImage;   // 탄환 이미지
    private double bulletCx;     // 탄환 중심의 x 좌표
    private double bulletCy;     // 탄환 중심의 y 좌표
    private int bulletWidth;     // 탄환의 너비
    private int bulletHeight;    // 탄환의 높이
    private boolean bulletReady; // 탄환의 준비 상태
    private int bulletType;      // 탄환의 종류
    private int bulletSpeed;     // 탄환의 이동 속도
    private int timing = 0;      // 타이밍 변수
    private int boomTiming = 0;  // 폭발 타이밍 변수
    private int bulletSpeedX;    // 탄환의 x축 이동 속도
    private int bulletSpeedY;    // 탄환의 y축 이동 속도
    private int randQuadrant;    // 랜덤한 사분면
    private Player tempPlayer;   // 플레이어 객체
    private Boss tempBoss;       // 보스 객체

    /**
     * 주어진 위치와 탄환 종류에 따라 BossBullet 객체를 생성합니다.
     *
     * @param x          탄환 중심의 x 좌표
     * @param y          탄환 중심의 y 좌표
     * @param type       탄환 종류
     * @param tempPlayer 플레이어 객체
     * @param tempBoss   보스 객체
     */
    public BossBullet(double x, double y, int type, Player tempPlayer, Boss tempBoss) {
        this.tempPlayer = tempPlayer;
        this.tempBoss = tempBoss;
        checkBullet(x, y, type);
        bulletReady = true;
        bulletType = type;
        checkSprite(type);
        load();
    }
    /**
     * 탄환 이미지를 로드하고, 탄환의 너비와 높이를 설정합니다.
     */
    public void load() {
        bulletWidth = bulletImage.getWidth(null);
        bulletHeight = bulletImage.getHeight(null);
    }
    /**
     * 탄환의 종류에 따라 위치를 설정합니다.
     *
     * @param x    탄환의 X 좌표
     * @param y    탄환의 Y 좌표
     * @param type 탄환의 종류 (0, 1, 2, 3, 4, 5, 6, 999 중 하나)
     */
    private void checkBullet(double x, double y, int type) {
        if (type == 0 || type == 1 || type == 4 || type == 5) {
            bulletCx = x;
            bulletCy = y;
        }
        else if (type == 2) {
            bulletCx = tempPlayer.getX();
            bulletCy = -Game.SCREEN_HEIGHT;
        }
        else if (type == 3) {
            bulletCx = x;
            bulletCy = y;
            bulletSpeed = 10;
            Random rand = new Random();
            bulletSpeedX = rand.nextInt(bulletSpeed - 3) + 1; // 양수 값을 경계로 설정
            bulletSpeedY = rand.nextInt(bulletSpeed - 1) + 1; // 양수 값을 경계로 설정
            randQuadrant = rand.nextInt(4) + 1;
        }
        if (type == 6) {
            Random rand = new Random();
            int boundX = (int) (tempBoss.getX() + tempBoss.getWidth());
            int boundY = (int) (tempBoss.getY() + tempBoss.getHeight());
            bulletCx = rand.nextInt(boundX > 0 ? boundX : 1) + tempBoss.getX();
            bulletCy = rand.nextInt(boundY > 0 ? boundY : 1) + tempBoss.getY();
        }

        else if (type == 999) {
            Random rand = new Random();
            bulletCx = rand.nextInt(tempBoss.getWidth()) + x;
            bulletCy = rand.nextInt(tempBoss.getHeight()) + y;
        }

    }

    /**
     *  탄환의 종류에 따라 스프라이트 경로를 설정하고, 이미지를 생성합니다.
     * @param type 탄환의 종류 (0, 1, 2, 3, 4, 5, 6, 999 중 하나)
     */
    private void checkSprite(int type) {
        // 탄환의 종류에 따라 스프라이트 경로를 설정하고, 이미지를 생성합니다.
        String spritePath = "";
        switch (type) {
            case 0:
            case 1:
                spritePath = "src/IWANNABETHEJAVA/image/Boss/BossBullethammer1.png";
                break;
            case 2:
                spritePath = "src/IWANNABETHEJAVA/image/Boss/BossBulletflag_1.png";
                break;
            case 3:
            case 4:
                spritePath = "src/IWANNABETHEJAVA/image/Boss/BossBulletFire2.png";
                break;
            case 5:
                spritePath = "src/IWANNABETHEJAVA/image/Boss/BossBulletFire1.png";
                break;
            case 6:
                spritePath = "src/IWANNABETHEJAVA/image/Boss/BossRecover_1.png";
                break;
            case 999:
                spritePath = "src/IWANNABETHEJAVA/image/Boss/BossDeadFire_1.png";
                break;

        }
        bulletImage = new ImageIcon(spritePath).getImage();
    }
    /**
     * 탄환의 x 좌표를 반환합니다.
     *
     * @return 탄환 중심의 x 좌표
     */
    public double getX() {
        return bulletCx;
    }
    /**
     * 탄환의 y 좌표를 반환합니다.
     *
     * @return 탄환 중심의 y 좌표
     */
    public double getY() {
        return bulletCy;
    }
    /**
     * 탄환의 너비를 반환합니다.
     *
     * @return 탄환의 너비
     */
    public int getWidth() {
        return bulletWidth;
    }
    /**
     * 탄환의 높이를 반환합니다.
     *
     * @return 탄환의 높이
     */
    public int getHeight() {
        return bulletHeight;
    }
    /**
     * 탄환의 이미지를 반환합니다.
     *
     * @return 탄환 이미지
     */
    public Image getImage() {
        return bulletImage;
    }
    /**
     * 탄환의 준비 상태를 반환합니다.
     *
     * @return 탄환의 준비 상태
     */
    public boolean isReady() {
        return bulletReady;
    }
    /**
     * 탄환을 이동시킵니다.
     */
    public void move() {
        // 탄환의 종류에 따라 다양한 이동 패턴을 구현합니다.
        switch (bulletType) {
            case 0:
                bulletSpeed = 3;
                bulletCx += bulletSpeed;
                if (bulletCx > Game.SCREEN_WIDTH) {
                    bulletReady = false;
                }
                break;
            case 1:
                bulletSpeed = 3;
                bulletCx -= bulletSpeed;
                if (bulletCx < 0) {
                    bulletReady = false;
                }
                break;
            case 2:
                bulletSpeed = 15;
                if (bulletCy == 0) {
                    bulletCy = 0;
                    timing++;
                    if (timing >= 5) {
                        spriteAnimation(bulletType, timing);
                        if (timing == 40) {
                            bulletReady = false;
                            timing = 0;
                        }
                    }
                } else {
                    bulletCy += bulletSpeed;
                }
                break;
            case 3:
                if (randQuadrant == 1) {
                    bulletCx += bulletSpeedX;
                    bulletCy += bulletSpeedY;
                }
                else if (randQuadrant == 2) {
                    bulletCx += bulletSpeedX;
                    bulletCy -= bulletSpeedY;
                }
                else if (randQuadrant == 3) {
                    bulletCx -= bulletSpeedX;
                    bulletCy += bulletSpeedY;
                }
                else if (randQuadrant == 4) {
                    bulletCx -= bulletSpeedX;
                    bulletCy -= bulletSpeedY;
                }
                if (bulletCx < 0 || bulletCy < 0 || bulletCx > Game.SCREEN_WIDTH || bulletCy > Game.SCREEN_HEIGHT) {
                    bulletReady = false;
                }
                break;
            case 4:
                bulletSpeed = 5;
                bulletCy += bulletSpeed;
                if (bulletCy > Game.SCREEN_HEIGHT) {
                    bulletReady = false;
                }
                break;
            case 5:
                bulletSpeed = 5;
                bulletCx -= bulletSpeed;
                if (bulletCx < 0) {
                    bulletReady = false;
                }
                break;
            case 6:
                timing++;
                bulletSpeed = 5;
                bulletCy -= bulletSpeed;
                spriteAnimation(bulletType, timing);
                if (timing == 5) {
                    timing = 0;
                }
                if (bulletCy < 0) {
                    bulletReady = false;
                }
                break;
            case 999:
                bulletSpeed = 5;
                bulletCy -= bulletSpeed;
                timing++;
                spriteAnimation(bulletType, timing);
                if (timing > 40) {
                    timing = 0;
                }
                if (bulletCy < 0) {
                    bulletReady = false;
                }
                break;

        }
    }

    /**
     * 탄환의 종류와 애니메이션 카운트에 따라 스프라이트 경로를 설정하고 이미지를 업데이트합니다.
     *
     * @param type 탄환의 종류 (2, 6, 999 중 하나)
     * @param cnt 애니메이션 카운트
     */
    private void spriteAnimation(int type, int cnt) {
        // 탄환의 종류와 애니메이션 카운트에 따라 스프라이트 경로를 설정하고 이미지를 업데이트합니다.
        String spritePath = "";
        switch (type) {
            case 2:
                if (cnt == 5) {
                    spritePath = "src/IWANNABETHEJAVA/image/Boss/BossBulletflag_1.png";
                }
                else if (cnt == 10) {
                    spritePath = "src/IWANNABETHEJAVA/image/Boss/BossBulletflag_1.png";
                }
                else if (cnt == 15) {
                    spritePath = "src/IWANNABETHEJAVA/image/Boss/BossBulletflag_1.png";
                }
                else if (cnt == 20) {
                    spritePath = "src/IWANNABETHEJAVA/image/Boss/BossBulletflag_1.png";
                }
                else if (cnt == 25) {
                    spritePath = "src/IWANNABETHEJAVA/image/Boss/BossBulletflag_1.png";
                }
                else if (cnt == 30) {
                    spritePath = "src/IWANNABETHEJAVA/image/Boss/BossBulletflag_1.png";
                }
                else if (cnt == 35) {
                    spritePath = "src/IWANNABETHEJAVA/image/Boss/BossBulletflag_1.png";
                }
                break;
            case 6:
                if (cnt == 0) {
                    spritePath = "src/IWANNABETHEJAVA/image/Boss/BossRecover_1.png";
                }
                else if (cnt == 2) {
                    spritePath = "src/IWANNABETHEJAVA/image/Boss/BossRecover_1.png";
                }
                else if (cnt == 4) {
                    spritePath = "src/IWANNABETHEJAVA/image/Boss/BossRecover_1.png";
                }
                break;
            case 999:
                if (cnt == 5) {
                    spritePath = "src/IWANNABETHEJAVA/image/Boss/BossDeadFire_1.png";
                }
                else if (cnt == 10) {
                    spritePath = "src/IWANNABETHEJAVA/image/Boss/BossDeadFire_2.png";
                }
                else if (cnt == 15) {
                    spritePath = "src/IWANNABETHEJAVA/image/Boss/BossDeadFire_3.png";
                }
                break;

        }
        bulletImage = new ImageIcon(spritePath).getImage();
    }
}