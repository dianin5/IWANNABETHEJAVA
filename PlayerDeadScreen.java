package IWANNABETHEJAVA;

import java.awt.*;
import javax.swing.*;

/**
 * 게임에서 플레이어가 사망했을 때 표시되는 화면을 나타내는 클래스입니다.
 */
public class PlayerDeadScreen extends JPanel {
    private static Image deadScreenImage;
    private static ImageIcon deadScreenSrc;
    private static int deadScreenWidth;
    private static int deadScreenHeight;
    private static final int deadScreenX = 0;
    private static final int deadScreenY = 0;

    static {
        load();
    }

    /**
     * 이미지를 로드합니다.
     */
    private static void load() {
        deadScreenSrc = new ImageIcon("src/IWANNABETHEJAVA/image/Screen/GameOverScreen.png");
        deadScreenImage = deadScreenSrc.getImage();
        deadScreenWidth = deadScreenImage.getWidth(null);
        deadScreenHeight = deadScreenImage.getHeight(null);

    }

    /**
     * 사망 화면을 그립니다.
     *
     * @param g 그래픽스 객체
     */
    public void doDrawingDeadScreen(Graphics g) {
        Graphics2D graphicBoss = (Graphics2D) g;
        graphicBoss.drawImage(getImage(), getX(), getY(), this);
    }

    /**
     * 사망 화면의 너비를 반환합니다.
     *
     * @return 사망 화면의 너비
     */
    public int getWidth() {
        return deadScreenWidth;
    }

    /**
     * 사망 화면의 높이를 반환합니다.
     *
     * @return 사망 화면의 높이
     */
    public int getHeight() {
        return deadScreenHeight;
    }

    /**
     * 사망 화면의 이미지를 반환합니다.
     *
     * @return 사망 화면 이미지
     */
    public static Image getImage() {
        return deadScreenImage;
    }

    /**
     * 사망 화면의 X 좌표를 반환합니다.
     *
     * @return 사망 화면의 X 좌표
     */
    public int getX() {
        return deadScreenX;
    }

    /**
     * 사망 화면의 Y 좌표를 반환합니다.
     *
     * @return 사망 화면의 Y 좌표
     */
    public int getY() {
        return deadScreenY;
    }
}