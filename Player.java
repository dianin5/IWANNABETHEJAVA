package IWANNABETHEJAVA;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * 플레이어를 나타내는 클래스입니다.
 */
public class Player {
    private Image playerImage;
    private int playerWidth;
    private int playerHeight;
    private int playerCx = 100;
    private int playerCy = 200;
    private int px, py;
    private int speed = 4;
    public boolean conflict;
    public static int conflictCount = 0;
    int tempX, tempY;
    Vector<PlayerBullet> bullets;
    HashMap<String, Integer> spriteState;

    /**
     * Player 클래스의 새 인스턴스를 생성합니다.
     */
    Player() {
        load();
        conflict = false;
    }

    /**
     * 스프라이트 상태를 확인하고 플레이어 이미지를 변경합니다.
     */
    public void checkSprite() {
        if (spriteState.get("STAY") != null && spriteState.get("STAY") == 1) {
            playerImage = new ImageIcon("src/IWANNABETHEJAVA/image/Player/player_0.png").getImage();
        } else if (spriteState.get("DOWN") != null && spriteState.get("DOWN") == 1) {
            playerImage = new ImageIcon("src/IWANNABETHEJAVA/image/Player/player_1.png").getImage();
        } else if (spriteState.get("UP") != null && spriteState.get("UP") == 1) {
            playerImage = new ImageIcon("src/IWANNABETHEJAVA/image/Player/player_2.png").getImage();
        }
    }

    /**
     * 플레이어의 죽음을 처리하고 이미지를 변경합니다.
     *
     * @param count 죽음 카운트
     */
    public void dead(int count) {
        if (count == 0) {
            playerImage = new ImageIcon("src/IWANNABETHEJAVA/image/Player/playerDead.png").getImage();
        } else if (count == 10) {
            playerImage = new ImageIcon("src/IWANNABETHEJAVA/image/Player/playerDead.png").getImage();
        } else if (count == 20) {
            playerImage = new ImageIcon("src/IWANNABETHEJAVA/image/Player/playerDead.png").getImage();
        } else if (count == 30) {
            playerImage = new ImageIcon("src/IWANNABETHEJAVA/image/Player/playerDead.png").getImage();
        } else if (count == 40) {
            playerImage = new ImageIcon("src/IWANNABETHEJAVA/image/Player/playerDead.png").getImage();
        }
    }

    /**
     * 이미지를 로드하고 스프라이트 상태와 총알을 초기화합니다.
     */
    public void load() {
        spriteState = new HashMap<>();
        spriteState.put("STAY", 1);
        spriteState.put("DOWN", 0);
        spriteState.put("UP", 0);

        checkSprite();

        bullets = new Vector<>();
        playerWidth = playerImage.getWidth(null);
        playerHeight = playerImage.getHeight(null);
    }

    /**
     * 플레이어를 움직입니다.
     */
    public void move() {
        playerCx = playerCx + px;
        playerCy = playerCy + py;
    }

    /**
     * 플레이어의 X 좌표를 반환합니다. 경계를 벗어나는 경우 조정합니다.
     *
     * @return 플레이어의 X 좌표
     */
    public int getX() {
        if (playerCx < 0) {
            playerCx = 0;
        }
        if (playerCx > 950) {
            playerCx = 950;
        }
        return playerCx;
    }

    /**
     * 플레이어의 Y 좌표를 반환합니다. 경계를 벗어나는 경우 조정합니다.
     *
     * @return 플레이어의 Y 좌표
     */
    public int getY() {
        if (playerCy < -3) {
            playerCy = 0;
        }
        if (playerCy > 540) {
            playerCy = 540;
        }
        return playerCy;
    }

    /**
     * 플레이어의 너비를 반환합니다.
     *
     * @return 플레이어의 너비
     */
    public int getWidth() {
        return playerWidth;
    }

    /**
     * 플레이어의 높이를 반환합니다.
     *
     * @return 플레이어의 높이
     */
    public int getHeight() {
        return playerHeight;
    }

    /**
     * 플레이어의 이미지를 반환합니다.
     *
     * @return 플레이어 이미지
     */
    public Image getImage() {
        return playerImage;
    }

    /**
     * 플레이어의 총알 목록을 반환합니다.
     *
     * @return 총알 목록
     */
    public Vector<PlayerBullet> getBullet() {
        return bullets;
    }

    /**
     * 키가 눌렸을 때 호출되는 메서드입니다.
     *
     * @param e 키 이벤트
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!conflict) {
            if (key == KeyEvent.VK_LEFT) {
                px = -speed;
            } else if (key == KeyEvent.VK_RIGHT) {
                px = speed;
            } else if (key == KeyEvent.VK_UP) {
                py = -speed;
                spriteState.put("STAY", 0);
                spriteState.put("UP", 1);
                spriteState.put("DOWN", 0);
                checkSprite();
            } else if (key == KeyEvent.VK_DOWN) {
                py = speed;
                spriteState.put("STAY", 0);
                spriteState.put("UP", 0);
                spriteState.put("DOWN", 1);
                checkSprite();
            } else if (key == KeyEvent.VK_SPACE) {
                tempX = playerCx;
                tempY = playerCy;
                bullets.add(new PlayerBullet(tempX, tempY));
            }
        } else {
            px = 0;
            py = 0;
        }
    }

    /**
     * 키가 놓였을 때 호출되는 메서드입니다.
     *
     * @param e 키 이벤트
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (!conflict) {
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                px = 0;
            } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                py = 0;
                spriteState.put("STAY", 1);
                spriteState.put("UP", 0);
                spriteState.put("DOWN", 0);
                checkSprite();
            }
        } else {
            px = 0;
            py = 0;
        }
    }
}