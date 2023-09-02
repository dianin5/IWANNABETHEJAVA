package IWANNABETHEJAVA;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

/**
 * 사운드 클래스는 효과음 또는 배경 음악을 나타냅니다.
 */
public class Sound {
    private AudioInputStream ais;
    private Clip clip;
    private FloatControl gainControl;
    private boolean loop;

    /**
     * 지정된 오디오 파일과 반복 설정으로 Sound 객체를 생성합니다.
     *
     * @param fileName 오디오 파일의 이름(확장자 제외)
     * @param loop     사운드를 반복해서 재생해야 하는 경우 true, 그렇지 않은 경우 false
     */
    public Sound(String fileName, boolean loop) {
        try {
            File file = new File("src/IWANNABETHEJAVA/sound/" + fileName + ".wav");
            ais = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(ais);

            gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if (fileName.equals("Boss/nooo")) {
                gainControl.setValue(-10.0f);
            } else if (fileName.equals("Boss/burning")) {
                gainControl.setValue(-30.0f);
            } else {
                gainControl.setValue(-40.0f);
            }

            this.loop = loop;
        } catch (Exception e) {
            // 예외 처리
            System.err.println("오디오 파일을 로드하는 중 오류 발생: " + fileName);
            e.printStackTrace();
        }
    }

    /**
     * 사운드를 재생합니다.
     */
    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    /**
     * 사운드를 정지합니다.
     */
    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}