package IWANNABETHEJAVA;

import javax.swing.*;
import java.awt.*;
/**
 * BossAppearance 클래스는 게임에서 보스의 출현을 나타냅니다.
 */
public class BossEmergence {
    private Image enemyEmergenceImage;   // 보스 출현 이미지
    private int enemyEmergenceCx;        // 보스 출현 중심의 x 좌표
    private int enemyEmergenceCy;        // 보스 출현 중심의 y 좌표
    private int enemyEmergenceWidth;     // 보스 출현 이미지의 너비
    private int enemyEmergenceHeight;    // 보스 출현 이미지의 높이
    public int timing = 0;                // 타이밍 변수
    public static boolean enemyEmergenceCheck = false;   // 보스 출현 상태 체크 변수

    /**
     * BossEmergence 객체를 생성합니다.
     * 보스 출현 이미지를 로드하고, 출현 위치를 설정합니다.
     */
     BossEmergence() {
        load();
         enemyEmergenceCx = (Game.SCREEN_WIDTH / 2) - (enemyEmergenceWidth / 2);
         enemyEmergenceCy = (Game.SCREEN_HEIGHT / 2) - (enemyEmergenceHeight / 2);
        enemyEmergenceCheck = true;

    }

    private void load() {
        loadImage();
        enemyEmergenceWidth = enemyEmergenceImage.getWidth(null);
        enemyEmergenceHeight = enemyEmergenceImage.getHeight(null);
    }

    private void loadImage() {
        ImageIcon enemyEmergenceSrc = new ImageIcon("src/IWANNABETHEJAVA/image/Boss/BossEmergence.jpg");
        enemyEmergenceImage = enemyEmergenceSrc.getImage();
    }

    /**
     * 보스 출현 중심의 x 좌표를 반환합니다.
     *
     * @return 보스 출현 중심의 x 좌표
     */
    public int getX() {

        return enemyEmergenceCx;
    }

    /**
     * 보스 출현 중심의 y 좌표를 반환합니다.
     *
     * @return 보스 출현 중심의 y 좌표
     */
    public int getY() {

        return enemyEmergenceCy;
    }

    /**
     * 보스 출현 이미지의 너비를 반환합니다.
     *
     * @return 보스 출현 이미지의 너비
     */
    public int getWidth() {

        return enemyEmergenceWidth;
    }

    /**
     * 보스 출현 이미지의 높이를 반환합니다.
     *
     * @return 보스 출현 이미지의 높이
     */
    public int getHeight() {
        return enemyEmergenceHeight;
    }

    /**
     * 보스 출현 이미지를 반환합니다.
     *
     * @return 보스 출현 이미지
     */
    public Image getImage() {

        return enemyEmergenceImage;
    }

    /**
     * 보스 출현을 이동시킵니다.
     * 보스 출현 위치를 업데이트하고, 출현 상태를 체크합니다.
     */
    protected void move() {
        timing++;
        enemyEmergenceCy -= 8;
        if(enemyEmergenceCy < 512){
            enemyEmergenceCy -= 8;
            if(enemyEmergenceCy<-1024){
                enemyEmergenceCheck=false;
            }
        }
        else{
            timing++;
            if(timing == 5){
                enemyEmergenceCy -=1;
                timing =0;
            }
        }

    }
}