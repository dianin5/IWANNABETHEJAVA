package IWANNABETHEJAVA;

import javax.swing.*;
import java.awt.*;

/**
 * 게임 종료 화면을 나타내는 패널 클래스입니다.
 */
public class EndingScreen extends JPanel {
    private static Image endingImage;
    private static ImageIcon endingIcon;
    private static int endingWidth;
    private static int endingHeight;
    private static final int endingX = 0;
    private static final int endingY = 0;

    static {
        load();
    }

    /**
     * 게임 종료 화면을 불러오고 초기화합니다.
     */
    private static void load() {
        endingIcon = new ImageIcon("src/IWANNABETHEJAVA/image/Screen/Ending.png");
        endingImage = endingIcon.getImage();
        endingWidth = endingImage.getWidth(null);
        endingHeight = endingImage.getHeight(null);
    }

    /**
     * 게임 종료 화면을 그립니다.
     *
     * @param g 그래픽 객체
     */
    public void doDrawingEndingScreen(Graphics g) {
        g.drawImage(endingImage, endingX, endingY, this);
    }
    /**
     * 게임 종료 화면의 너비를 반환합니다.
     *
     * @return 게임 종료 화면의 너비
     */
    public int getWidth() {
        return endingWidth;
    }
    /**
     * 게임 종료 화면의 높이를 반환합니다.
     *
     * @return 게임 종료 화면의 높이
     */
    public int getHeight() {
        return endingHeight;
    }

    /**
     * 게임 종료 화면의 이미지를 반환합니다.
     *
     * @return 게임 종료 화면의 이미지
     */
    public static Image getImage() {
        return endingImage;
    }
    /**
     * 게임 종료 화면의 X 좌표를 반환합니다.
     *
     * @return 게임 종료 화면의 X 좌표
     */
    public int getX() {
        return endingX;
    }
    /**
     * 게임 종료 화면의 Y 좌표를 반환합니다.
     *
     * @return 게임 종료 화면의 Y 좌표
     */
    public int getY() {
        return endingY;
    }
}