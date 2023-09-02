package IWANNABETHEJAVA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
/**
 * 게임 화면을 나타내는 JPanel 클래스입니다.
 */
public class InGameScreen extends JPanel implements ActionListener{
    public int playerDamage = 100;
    public Timer timer;
    private final int DELAY = 10;
    public int deadCnt=0;
    public BossEmergence enemyAppearance = null;
    public Player player=null;
    public Boss boss=null;
    public BossPattern pattern;
    public PlayerDeadScreen deadTitle;
    public EndingScreen endingScreen;
    public Sound inGameScreenAppearanceMusic;
    public Sound inGameScreenBossMusic;
    public Sound playerConflictMusic;
    public Sound playerDeadScreenMusic;
    public Sound playerShootMusic;
    /**
     * InGameScreen 클래스의 생성자입니다.
     */
    InGameScreen() {
        setBackground(Color.black);
        player = new Player();
        enemyAppearance = new BossEmergence();
        inGameScreenAppearanceMusic = new Sound("Boss/BossAppearance",false);
        inGameScreenAppearanceMusic.play();
        timer = new Timer(DELAY, this); //DELAY (actionperformance) 마다 같은 동작 을 반복한다.
        timer.start();

        endingScreen = new EndingScreen();
    }
    /**
     * JPanel을 그리는 메서드입니다.
     *
     * @param g Graphics 객체
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }
    /**
     * 게임 요소를 그리는 메서드입니다.
     *
     * @param g Graphics2D 객체
     */
    private void doDrawing(Graphics g) {
        Graphics2D graphic = (Graphics2D) g;

        //draw Player
        doDrawingPlayer(graphic);

        //draw Appearance Boss
        if(BossEmergence.enemyEmergenceCheck){ //보스 등장 씬

            doDrawingAppearanceBoss(graphic);
        }

        //draw Boss
        if(Boss.bossAppear){

            doDrawingBoss(graphic);
            doDrawingBossBar(graphic);
        }

        //draw Boss Bullet
        if(Boss.bossAppear) {
            Vector<BossBullet> Enemybullets = pattern.getBullet();
            for(BossBullet Enemybullet:Enemybullets) {
                graphic.drawImage(Enemybullet.getImage(), (int)Enemybullet.getX(), (int)Enemybullet.getY(), this);
        }
 }

        //draw player Bullet
        Vector<PlayerBullet> bullets = player.getBullet();
        for(PlayerBullet bullet:bullets) {
            graphic.drawImage(bullet.getImage(),
                    bullet.getX(),
                    bullet.getY(),
                    this);
        }

        //draw deadScreen
        if(player.conflict == true) {
            deadTitle = new PlayerDeadScreen();
            deadTitle.doDrawingDeadScreen(graphic);
        }
        //draw EndingScreen
        if (Boss.isBossDead) {
            endingScreen.doDrawingEndingScreen(graphic);
        }
    }
    /**
     * Appearance Boss를 그리는 메서드입니다.
     *
     * @param g Graphics 객체
     */
    public void doDrawingAppearanceBoss(Graphics g){
        g.drawImage(enemyAppearance.getImage(),enemyAppearance.getX(),enemyAppearance.getY(),this);
    }
    /**
     * Boss를 그리는 메서드입니다.
     *
     * @param g Graphics 객체
     */
    public void doDrawingBoss(Graphics g) {

        g.drawImage(boss.getImage(),(int)boss.getX(),(int)boss.getY(),this);
    }

    /**
     * Player를 그리는 메서드입니다.
     *
     * @param graphic Graphics2D 객체
     */
    private void doDrawingPlayer(Graphics2D graphic) {
        graphic.drawImage(player.getImage(), player.getX(), player.getY(), this);
    }
    /**
     * Boss의 체력 바를 그리는 메서드입니다.
     *
     * @param g Graphics 객체
     */
    public void doDrawingBossBar(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(0,0,boss.getHp(),20);
    }
    /**
     * Boss를 생성하는 메서드입니다.
     */
    public void createBoss(){
        inGameScreenAppearanceMusic.stop();
        inGameScreenAppearanceMusic = null;
        inGameScreenBossMusic = new Sound("Boss/InGameScreen_Boss",false);
        inGameScreenBossMusic.play();

        enemyAppearance = null;
        boss = new Boss();
        pattern = new BossPattern(boss,player,inGameScreenBossMusic);
        pattern.start();

    }
    /**
     * Timer의 action이 수행될 때 호출되는 메서드입니다.
     *
     * @param e ActionEvent 객체
     */
    public void actionPerformed(ActionEvent e){

        if(!BossEmergence.enemyEmergenceCheck && !Boss.bossAppear && !Boss.isBossDead){

            createBoss();
        }
        if(player.conflict == true) {
            deadCnt++;
            Player.conflictCount++;
            player.dead(deadCnt);
            if(Player.conflictCount == 1) {
                inGameScreenBossMusic.stop();
                inGameScreenBossMusic = null;
                playerConflictMusic = new Sound("Player/playerConflict",false);
                playerConflictMusic.play();
                playerDeadScreenMusic = new Sound("GameOver",false);
                playerDeadScreenMusic.play();
            }
        }
        movePlayerStep();
        moveBulletStep();
        if(Boss.bossAppear) {
            moveEnemyBulletStep();
        }
        if(BossEmergence.enemyEmergenceCheck) {
            moveEnemyAppearanceStep();
        }
        repaint();
    }
    /**
     * Player의 이동을 처리하는 메서드입니다.
     */
    private void movePlayerStep() {
        player.move();
        if(Boss.bossAppear) {
            if((player.getX()+25)>=boss.getX() && (player.getX()+25)<=boss.getWidth()+boss.getX()) {
                if((player.getY()+9)>=boss.getY() && (player.getY()+9)<=boss.getHeight()+boss.getY()) {
                    if(!player.conflict)
                        player.conflict = true;
                }
            }
        }
    }
    /**
     * Player의 Bullet 이동을 처리하는 메서드입니다.
     */
    private void moveBulletStep() {
        Vector<PlayerBullet> bullets = player.getBullet(); //플레이어의 미사일 정보를 벡터에 저장.
        for (int i = 0; i < bullets.size(); i++) {
            PlayerBullet bullet = bullets.get(i);
            if(bullet.shootSound) {
                playerShootMusic = new Sound("Player/playerShoot",false);
                playerShootMusic.play();
                bullet.shootSound = false;
                playerShootMusic = null;
            }
            if (bullet.isReady()){ //벡터의 각 인덱스가 미사일 준비상태라면,

                bullet.move(); // . 미사일을 이동시킨다
                if(Boss.bossAppear){

                if(bullet.getX()>=boss.getX() && bullet.getX()<=boss.getWidth()+boss.getX()){ //만약 플레이어 미사일이 보스의 위치와 동일하다면,

                if(bullet.getY()>=boss.getY() && bullet.getY()<=boss.getHeight()+boss.getY()) {
                    movebossBarStep(); //보스의 체력 바를 줄인다.
                    bullets.remove(i); // . 그리고 삭제한다
                }
 }
 }
            }
            else
            {
                bullets.remove(i);
            }
        }
    }
    /**
     * emergence Boss의 이동을 처리하는 메서드입니다.
     */
    private void moveEnemyAppearanceStep() {
        enemyAppearance.move();
    }
    /**
     * Boss의 미사일 이동을 처리하는 메서드입니다.
     */
    private void moveEnemyBulletStep() {
        Vector<BossBullet> Enemybullets = pattern.getBullet();
        for (int i = 0; i < Enemybullets.size(); i++) {
            BossBullet enemyBullet = Enemybullets.get(i);
            if(enemyBullet.isReady()) {
                enemyBullet.move();
                if(player.getX()>=enemyBullet.getX() && player.getX()<=enemyBullet.getWidth()+enemyBullet.getX()) {
                    if(player.getY()>=enemyBullet.getY() &&
                            player.getY()<=enemyBullet.getHeight()+enemyBullet.getY()) {
                        if(!player.conflict)
                            player.conflict = true;
                    }
                }
            }
            else {
                        Enemybullets.remove(i);
            }
        }
    }
    /**
     * Boss의 체력 바를 감소시키는 메서드입니다.
     */
    private void movebossBarStep() {
        if(Boss.bossAppear) {
            boss.hp=boss.hp-playerDamage;
            if(boss.hp <= 0) {
                Boss.isBossDead = true;
            }
        }
    }
}