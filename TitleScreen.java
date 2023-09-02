package IWANNABETHEJAVA;

import javax.swing.*;
import java.awt.*;

/**
 * TitleScreen은 게임의 타이틀 화면을 나타내는 패널입니다.
 */
public class TitleScreen extends JPanel {
    private Image titleImage;

    /**
     * TitleScreen 객체를 생성하고 타이틀 이미지를 로드합니다.
     */
    public TitleScreen() {
        loadImage();
    }

    /**
     * 타이틀 이미지를 로드합니다.
     */
    private void loadImage() {
        ImageIcon titleIcon = new ImageIcon("src/IWANNABETHEJAVA/image/screen/titlescreen.jpg");
        titleImage = titleIcon.getImage();
    }

    /**
     * 패널을 그립니다.
     *
     * @param g 그래픽 컨텍스트
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTitleImage(g);
    }

    /**
     * 타이틀 이미지를 그립니다.
     *
     * @param g 그래픽 컨텍스트
     */
    private void drawTitleImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(titleImage, 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * 패널의 기본 크기를 타이틀 이미지의 크기로 설정합니다.
     *
     * @return 기본 크기
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(titleImage.getWidth(this), titleImage.getHeight(this));
    }
}