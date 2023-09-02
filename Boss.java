package IWANNABETHEJAVA;

import javax.swing.*;
import java.awt.*;
/**
 * Boss 클래스는 게임의 보스 캐릭터를 나타냅니다.
 */
public class Boss {
    private Image enemyImage;        // 보스 이미지
    private ImageIcon enemySrc;      // 보스 이미지 소스
    private int enemyWidth;          // 보스 이미지의 너비
    private int enemyHeight;         // 보스 이미지의 높이
    public static int defaultHP = 1000;    // 보스의 기본 체력
    public int hp = defaultHP;            // 보스의 현재 체력
    public final int leftTopX = 30;
    public final int leftTopY = 50;
    public final int leftBottomX = 30;
    public final int leftBottomY = 250;
    public final int rightTopX = 750;
    public final int rightTopY = 50;
    public final int rightBottomX = 750;
    public final int rightBottomY = 250;
    public final int centerX = 750;
    public final int centerY = 145;
    public int enemyCx = rightTopX-114;
    public int enemyCy = rightTopY;
    public static boolean bossAppear = false; // 보스 등장 여부
    public static boolean isBossDead = false; // 보스 사망 여부
    public static boolean isBossDrop = false; // 보스 아이템 드롭 여부

    /**
     * Boss 객체를 생성합니다.
     * 보스 등장 여부를 설정하고, 체력을 초기화하며 이미지를 로드합니다.
     */
    public Boss() {
        bossAppear = true;
        hp = defaultHP;
        load();
    }

    private void load() {
        loadImageDefault();
        enemyWidth = enemyImage.getWidth(null);
        enemyHeight = enemyImage.getHeight(null);
    }

    private void loadImageDefault() {
        enemySrc = new ImageIcon("src/IWANNABETHEJAVA/image/Boss/Boss.png");
        enemyImage = enemySrc.getImage();
    }
    /**
     * 보스 중심의 x 좌표를 반환합니다.
     *
     * @return 보스 중심의 x 좌표
     */
    public double getX() {
        return enemyCx;
    }
    /**
     * 보스 중심의 y 좌표를 반환합니다.
     *
     * @return 보스 중심의 y 좌표
     */
    public double getY() {
        return enemyCy;
    }
    /**
     * 보스 이미지의 너비를 반환합니다.
     *
     * @return 보스 이미지의 너비
     */
    public int getWidth() {
        return enemyWidth;
    }
    /**
     * 보스 이미지의 높이를 반환합니다.
     *
     * @return 보스 이미지의 높이
     */
    public int getHeight() {
        return enemyHeight;
    }
    /**
     * 보스 이미지를 반환합니다.
     *
     * @return 보스 이미지
     */
    public Image getImage() {
        return enemyImage;
    }
    /**
     * 보스의 현재 체력을 반환합니다.
     *
     * @return 보스의 현재 체력
     */
    public int getHp() {
        return hp;
    }
}