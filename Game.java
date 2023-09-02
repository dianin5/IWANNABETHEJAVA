package IWANNABETHEJAVA;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 * Game 클래스는 게임의 메인 창을 나타냅니다.
 */
public class Game extends JFrame {
    public final static int SCREEN_WIDTH = 1024; //화면 가로 크기
    public final static int SCREEN_HEIGHT = 768; //화면 세로 크기
    public static int ScreenState; // 0. 게임 상태 타이틀 화면 1. 인 게임 화면
    protected TitleScreen titlePanel;
    protected InGameScreen inGamePanel;
    public Sound titleScreenMusic;
    /**
     * Game 객체를 생성합니다.
     * 초기 게임 상태를 설정하고 UI를 초기화합니다.
     */
    public Game() {
        ScreenState = 0;
        initUI();
    }
    private void initUI() {
        setTitle("SUPER JAVA BROS."); //윈도우 창 제목
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT); //윈도우 창 사이즈
        setLocationRelativeTo(null); //윈도우 창 화면의 중간에 출력한다.
        setResizable(false); //출력 화면의 크기 조절 불가능하게 설정한다.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X 버튼을 누를 시 윈도우 창이 정상적으로 종료 될 수 있도록 한다.
        setVisible(true); //화면이 보이게 한다.
        addKeyListener(new TAdapter_title()); //키보드 이벤트
        checkScreenState(ScreenState);
    }



    /**
     * 현재 게임 상태를 확인하고 화면을 업데이트합니다.
     *
     * @param state 현재 게임 상태
     */
    public void checkScreenState(int state) {
        switch (state) {
            case 0 -> {
                getContentPane().removeAll(); // JPanel . 모든 를 삭제한다
                getContentPane().add(titlePanel = new TitleScreen()); //TitleScreen Panel . 객체 을 추가
                setVisible(true);
                revalidate();
                titleScreenMusic = new Sound("titlescreen", true);
                titleScreenMusic.play();
            }
            case 1 -> {
                getContentPane().removeAll(); // JPanel 현재의 모든 를 삭제한다.
                getContentPane().add(inGamePanel = new InGameScreen()); //InGameScreen Panel . 객체 를 추가
                setVisible(true);
                revalidate();
            }
        }
    }
    /**
     * 타이틀 화면용 키 어댑터 클래스입니다.
     */
    private class TAdapter_title extends KeyAdapter {
        /**
         * 키가 눌렸을 때 호출됩니다.
         *
         * @param e 키 이벤트
         */
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (ScreenState == 1) {//게임 화면
                inGamePanel.player.keyPressed(e);
                if (inGamePanel.player.conflict) {
                    if (key == KeyEvent.VK_R) {
                        // 게임 내 변수들 초기화
                        inGamePanel.playerDeadScreenMusic.stop();
                        inGamePanel.playerDeadScreenMusic = null;
                        inGamePanel.playerConflictMusic.stop();
                        inGamePanel.playerConflictMusic = null;
                        inGamePanel.player.conflict = false;
                        Player.conflictCount = 0;
                        inGamePanel.timer.stop();
                        inGamePanel.deadCnt = 0;
                        inGamePanel.player = null;
                        inGamePanel.boss = null;
                        inGamePanel.pattern = null;
                        inGamePanel.deadTitle = null;
                        Boss.bossAppear = false;
                        Boss.isBossDead = false;
                        Boss.isBossDrop = false;
                        BossEmergence.enemyEmergenceCheck = false;
                        inGamePanel = null;
                        ScreenState = 0;
                        checkScreenState(ScreenState);
                    }
                }
                if (BossEmergence.enemyEmergenceCheck) {//보스 등장 중
                    if (key == KeyEvent.VK_S) {//'S' 키를 누르면 보스 등장 씬 스킵
                        inGamePanel.createBoss();
                        BossEmergence.enemyEmergenceCheck = false;
                    }
                }
            }
                if (key == KeyEvent.VK_SPACE) {
                    if (ScreenState == 0) {//타이틀 화면

                        titleScreenMusic.stop();
                        titleScreenMusic = null;
                        ScreenState = 1; //게임 화면으로 전환
                        checkScreenState(ScreenState); //게임 화면으로 전환 처리
                    }
                }
                if (key == KeyEvent.VK_ESCAPE) {
                    if (ScreenState == 0) {
                        System.exit(0);
                    }
                    if (ScreenState == 1) {
                        ScreenState = 0;
                        checkScreenState(ScreenState);
                    }
                }
            }
            /**
             * 키가 눌렸다가 떼어질 때 호출됩니다.
             * @param e 키 이벤트
             */
            @Override
            public void keyReleased (KeyEvent e){
                if (ScreenState == 1) {
                    inGamePanel.player.keyReleased(e);
                }
            }

        }
    }
