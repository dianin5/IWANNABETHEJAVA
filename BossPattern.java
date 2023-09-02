package IWANNABETHEJAVA;

import java.util.Vector;

/**
 * BossPateern 클래스는 보스 패턴에 관련된 클래스입니다.
 */
public class BossPattern extends Thread {
    public static boolean turnEnding = false;
    private Boss tempBoss = null;
    private Player tempPlayer = null;

    public Sound flagSound;
    public Sound hammerSound;

    public Sound fireballSound;
    public Sound burningSound;
    public Sound boomSound;
    public Sound noooSound;
    public Sound recoverSound;
    public Sound inGameScreenBossMusic;
    public Sound endingScreenMusic;

    Vector<BossBullet> enemyBullets;


    /**
     * 지정된 보스, 플레이어, 게임 내 보스 음악을 가지고 BossPattern 객체를 생성합니다.
     *
     * @param boss                    보스 객체
     * @param player                    플레이어 객체
     * @param inGameScreenBossMusic 게임 내 보스 음악
     */
    BossPattern(Boss boss, Player player, Sound inGameScreenBossMusic) {

        this.inGameScreenBossMusic = inGameScreenBossMusic;
        this.tempBoss = boss;
        this.tempPlayer = player;
        this.hammerSound = new Sound("hammer", true);
        this.burningSound = new Sound("Boss/burning", true);
        this.fireballSound = new Sound("Boss/fireball", true);
        enemyBullets = new Vector<>();
    }
    /**
     * 보스의 탄환을 가져옵니다.
     *
     * @return 보스 탄환의 벡터
     */
    public Vector<BossBullet> getBullet() {

        return enemyBullets;
    }

    /**
     * 보스의 체력이 0이 되면 출력됩니다.
     */

        public void Burning() {
            for (int i = 0; ; i++) {
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    break;
                }
                if (i == 5) {
                    noooSound = new Sound("Boss/nooo", false);
                    noooSound.play();
                }
                if (i % 2 == 0) {
                    if (!tempPlayer.conflict) {
                        burningSound = new Sound("Boss/burning", false);
                        burningSound.play();
                    } else {
                        burningSound.stop();
                        burningSound = null;
                    }
                    endingScreenMusic = new Sound("Ending",false);
                    enemyBullets.add(new BossBullet(tempBoss.enemyCx, tempBoss.enemyCy, 999, tempPlayer, tempBoss));
                }
                tempBoss.enemyCy += 5;
                if (tempBoss.getY() >= Game.SCREEN_HEIGHT + 50) {
                    Boss.isBossDrop = true;
                    break;
                }
            }
        }
    /**
     * 보스가 아래로 떨어졌을 때 터지는 소리를 재생하는 메서드입니다.
     */
        public void Boom(){ //보스가 아래로 떨어졌을 때 터지는 , 애니메이션

            boomSound = new Sound("Boss/boom",false);
            boomSound.play();
            enemyBullets.add(new BossBullet(tempBoss.enemyCx,tempBoss.enemyCy,666,tempPlayer,tempBoss));
        }
    /**
     * 보스가 사망한 경우 폭발 애니메이션을 실행하는 메서드입니다.
     */
        public void checkDead() {
            if(Boss.isBossDead) {
                Burning();

            }
        }

    /**
     * Runnable 인터페이스를 구현한 클래스로, 보스 패턴을 실행하는 스레드입니다.
     */
    @Override
    public void run() {
        turnEnding = false;
        int patternNum;

        // 보스가 죽지 않을 때까지 반복합니다.
        while (!Boss.isBossDrop) {
            try {
                if(Boss.isBossDead) {
                    Burning();
                    inGameScreenBossMusic.stop();
                    endingScreenMusic.play();
                    turnEnding = true;
                    break;
                }
                // 3초간 대기합니다.
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                return;
            }

            // 턴이 끝났을 경우 다음 패턴으로 넘어갑니다.
            if (turnEnding) {
                continue;
            }

            // 1부터 10까지의 랜덤한 숫자를 생성합니다.
            patternNum = (int) (Math.random() * 10) + 1;

            // 생성된 숫자에 따라 해당하는 패턴을 실행합니다.
            switch (patternNum) {
                case 1:
                    Pattern_GotorhombusShape();
                    break;
                case 2:
                    Pattern_GotoCenter();
                    break;
                case 3:
                    Pattern_GotoUpDown(2);
                    break;
                case 4:
                    Pattern_ShootFireBall(10);
                    break;
                case 5:
                    Pattern_Dropflag();
                    break;
                case 6:
                    Pattern_RandomShootFireBall();
                    break;
                case 7:
                    Pattern_GotoRightUp(5);
                    break;
                case 8:
                    Pattern_GotoLeftRight(5);
                    break;
                case 9:
                    Pattern_Recover();
                    break;
                case 10:
                    Pattern_DropFireBall(600);
                    break;
                default:
                    break;
            }
        }
    }
    /**
     * 보스가 마름모 형태로 움직이는 패턴을 실행합니다.
     */
    public void Pattern_GotorhombusShape() {
        final int patternGoal = 2;
        int patternCount = 0;
        double speedX = 6.5;
        double speedY = 2.5;

        int passState = -1;
        while (true) {
            try {
                if(Boss.isBossDead) {
                    Burning();
                    inGameScreenBossMusic.stop();
                    endingScreenMusic = new Sound("Ending",false);
                    endingScreenMusic.play();
                    turnEnding = true;
                    break;
                }
                if (passState == -1) {
                    Thread.sleep(10);
                }
                else {
                    Thread.sleep(7);
                }
            } catch (InterruptedException e) {
                return;
            }
            if (passState == -1) {
                tempBoss.enemyCx += speedX;
                if (tempBoss.getX() >= tempBoss.rightTopX) {
                    passState = 0;
                }
            }
            if (passState == 0) {
                tempBoss.enemyCx -= speedX;
                tempBoss.enemyCy += speedY;
                if (tempBoss.enemyCx <= tempBoss.leftBottomX && tempBoss.enemyCy >= tempBoss.leftBottomY) {
                    tempBoss.enemyCx = tempBoss.leftBottomX;
                    tempBoss.enemyCy = tempBoss.leftBottomY;
                    if (!tempPlayer.conflict) {
                        hammerSound = new Sound("Boss/hammer", false);
                        hammerSound.play();
                    }
                    else {
                        hammerSound.stop();
                        hammerSound = null;
                    }
                    enemyBullets.add(new BossBullet(tempBoss.enemyCx, tempBoss.enemyCy, 0, tempPlayer, tempBoss));
                    passState = 1;
                }
            }
            if (passState == 1) {
                tempBoss.enemyCy -= speedY;
                if (tempBoss.enemyCy <= tempBoss.leftTopY) {
                    tempBoss.enemyCy = tempBoss.leftTopY;
                    if (!tempPlayer.conflict) {
                        hammerSound = new Sound("Boss/hammer", false);
                        hammerSound.play();
                    }
                    else {
                        hammerSound.stop();
                        hammerSound = null;
                    }
                    enemyBullets.add(new BossBullet(tempBoss.enemyCx, tempBoss.enemyCy, 0, tempPlayer, tempBoss));
                    passState = 2;
                }
            }

            if (passState == 2) {
                tempBoss.enemyCx += speedX;
                tempBoss.enemyCy += speedY;
                if (tempBoss.enemyCx >= tempBoss.rightBottomX && tempBoss.enemyCy >= tempBoss.rightBottomY) {
                    tempBoss.enemyCx = tempBoss.rightBottomX;
                    if (!tempPlayer.conflict) {
                        hammerSound = new Sound("Boss/hammer", false);
                        hammerSound.play();
                    }
                    else {
                        hammerSound.stop();
                        hammerSound = null;
                    }
                    enemyBullets.add(new BossBullet(tempBoss.enemyCx, tempBoss.enemyCy, 1,tempPlayer,tempBoss));
                    passState = 3;
                }
            }

            if (passState == 3) {
                tempBoss.enemyCx -= speedX;
                tempBoss.enemyCy -= speedY;
                if (tempBoss.enemyCx <= tempBoss.leftTopX && tempBoss.enemyCy <= tempBoss.leftTopY) {
                    tempBoss.enemyCx = tempBoss.leftTopX;
                    tempBoss.enemyCy = tempBoss.leftTopY;
                    if (!tempPlayer.conflict) {
                        hammerSound = new Sound("Boss/hammer", false);
                        hammerSound.play();
                    }
                    else {
                        hammerSound.stop();
                        hammerSound = null;
                    }
                    enemyBullets.add(new BossBullet(tempBoss.enemyCx, tempBoss.enemyCy, 1, tempPlayer, tempBoss));
                    passState = 4;
                }
            }

            if (passState == 4) {
                tempBoss.enemyCy += speedY;
                if (tempBoss.enemyCy >= tempBoss.rightTopY) {
                    tempBoss.enemyCy = tempBoss.rightTopY;
                    if (!tempPlayer.conflict) {
                        hammerSound = new Sound("Boss/hammer", false);
                        hammerSound.play();
                    }
                    else {
                        hammerSound.stop();
                        hammerSound = null;
                    }
                    enemyBullets.add(new BossBullet(tempBoss.enemyCx, tempBoss.enemyCy, 1, tempPlayer, tempBoss));
                    passState = 5;
                }
            }

            if (passState == 5) {
                tempBoss.enemyCx += speedX;
                tempBoss.enemyCy -= speedY;
                if (tempBoss.enemyCx >= tempBoss.rightBottomX && tempBoss.enemyCy <= tempBoss.rightBottomY) {
                    tempBoss.enemyCx = tempBoss.rightBottomX;
                    tempBoss.enemyCy = tempBoss.rightBottomY;
                    if (!tempPlayer.conflict) {
                        hammerSound = new Sound("Boss/hammer", false);
                        hammerSound.play();
                    }
                    else {
                        hammerSound.stop();
                        hammerSound = null;
                    }
                    enemyBullets.add(new BossBullet(tempBoss.enemyCx, tempBoss.enemyCy, 0, tempPlayer, tempBoss));
                    passState = 6;
                }
            }
            if (passState == 6) {
                tempBoss.enemyCx -= speedX;
                tempBoss.enemyCy += speedY;
                if (tempBoss.enemyCx <= tempBoss.leftBottomX && tempBoss.enemyCy >= tempBoss.leftBottomY) {
                    tempBoss.enemyCx = tempBoss.leftBottomX;
                    if (!tempPlayer.conflict) {
                        hammerSound = new Sound("Boss/hammer", false);
                        hammerSound.play();
                    }
                    else {
                        hammerSound.stop();
                        hammerSound = null;
                    }
                    enemyBullets.add(new BossBullet(tempBoss.enemyCx, tempBoss.enemyCy, 0, tempPlayer, tempBoss));
                    passState = 7;
                }
            }
            if (passState == 7) {
                tempBoss.enemyCy -= speedY;
                if (tempBoss.enemyCy <= tempBoss.leftTopY) {
                    tempBoss.enemyCy = tempBoss.leftTopY;
                    if (!tempPlayer.conflict) {
                        hammerSound = new Sound("Boss/hammer", false);
                        hammerSound.play();
                    }
                    else {
                        hammerSound.stop();
                        hammerSound = null;
                    }
                    enemyBullets.add(new BossBullet(tempBoss.enemyCx, tempBoss.enemyCy, 0, tempPlayer, tempBoss));
                    passState = 0;
                    patternCount++;
                }
            }
            if (patternCount >= patternGoal) {
                break;
            }
        }
    }

    /**
     * 보스가 중앙 오른쪽 위치로 움직이는 패턴을 실행합니다.
     */
    public void Pattern_GotoCenter() {
        tempBoss.enemyCx = tempBoss.centerX + 300;
        tempBoss.enemyCy = tempBoss.centerY;
        while (true) {
            try {
                if(Boss.isBossDead) {
                    Burning();
                    inGameScreenBossMusic.stop();
                    endingScreenMusic = new Sound("Ending",false);
                    endingScreenMusic.play();
                    turnEnding = true;
                    break;
                }
                Thread.sleep(10);
            } catch (InterruptedException e) {
                return;
            }
            tempBoss.enemyCx -= 2;
            if (tempBoss.getX() == tempBoss.centerX) {
                break;
            }
        }
    }
    /**
     * 보스가 위아래로 움직이는 패턴을 실행합니다.
     *
     * @param speed 보스의 움직임 속도
     */
    public void Pattern_GotoUpDown(int speed){
        int goUpCheck=0;
        int goDownCheck=0;
        while(!Boss.isBossDrop) {
            try {
                Thread.sleep(10);
            }
            catch(InterruptedException e) {
                return;
            }
            if(tempBoss.getY()>0 && tempBoss.getY()<300 && goUpCheck!=1 && goDownCheck!=1)
                tempBoss.enemyCy-=speed;
            if(tempBoss.getY()>=Game.SCREEN_HEIGHT - tempBoss.getHeight()) {
                goDownCheck=0;
                goUpCheck=1;
                Pattern_ShootFireBall(1);
            }
            if(tempBoss.getY()<=0) {
                goDownCheck=1;
                goUpCheck=0;
                Pattern_ShootFireBall(1);
            }
            if(goDownCheck==1 && goUpCheck==0) {
                tempBoss.enemyCy+=speed;
            }
            if(goUpCheck==1 && goDownCheck==0) {
                tempBoss.enemyCy-=speed;
            }
            if(tempBoss.getY() == 163) {
                Pattern_ShootFireBall(1);
            }
            checkDead();
            if(Boss.isBossDrop) {
                Boom();
                inGameScreenBossMusic.stop();
                endingScreenMusic = new Sound("Ending",false);
                endingScreenMusic.play();
                turnEnding = true;
            }
        }
    }
    /**
     * 깃발이 위에서 아래로 떨어지는 패턴을 실행합니다.
     */
    public void Pattern_Dropflag() {
        for(int i=0;i<=100;i++) {
            try {
                if(Boss.isBossDead) {
                    Burning();
                    inGameScreenBossMusic.stop();
                    endingScreenMusic = new Sound("Ending",false);
                    endingScreenMusic.play();
                    turnEnding = true;
                    break;
                }
                Thread.sleep(10);
            }
            catch(InterruptedException e) {
                return;
            }
            if(i==0 || i==50 || i==100) {
                if(!tempPlayer.conflict) {
                    flagSound = new Sound("Boss/flag",false);
                    flagSound.play();
                }
                else {
                    flagSound.stop();
                    flagSound = null;
                }
                enemyBullets.add(new BossBullet(tempBoss.enemyCx,tempBoss.enemyCy,2,tempPlayer,tempBoss));
            }
            else{
                flagSound = null;
            }
        }
    }
    /**
     * 파이어볼이 무작위로 흩어지는 패턴을 실행합니다.
     */
    public void Pattern_RandomShootFireBall() {
        int count=50;
        for(int i=0;i<=count;i++) {
            try {
                if(Boss.isBossDead) {
                    Burning();
                    inGameScreenBossMusic.stop();
                    endingScreenMusic = new Sound("Ending",false);
                    endingScreenMusic.play();
                    turnEnding = true;
                    break;
                }
                Thread.sleep(50);
            }
            catch(InterruptedException e) {
                return;
            }
            if(!tempPlayer.conflict) {
                this.fireballSound = new Sound("Boss/fireball", true);
                fireballSound = new Sound("Boss/fireball",false);
                fireballSound.play();
            }
            else {
                fireballSound.stop();
                fireballSound = null;
            }
            enemyBullets.add(new BossBullet(tempBoss.enemyCx,tempBoss.enemyCy,3,tempPlayer,tempBoss));
        }
    }
    /**
     * 보스가 오른쪽에서 왼쪽으로 파이어볼을 발사하는 패턴을 실행합니다.
     *
     * @param type 파이어볼 패턴의 종류
     */
    private void Pattern_ShootFireBall(int type) {
        if(type == 1) {
            for(int i=0;i<=10;i++) {
                if(Boss.isBossDead) {
                    Burning();
                    inGameScreenBossMusic.stop();
                    endingScreenMusic = new Sound("Ending",false);
                    endingScreenMusic.play();
                    turnEnding = true;
                    break;
                }
                if(!tempPlayer.conflict) {
                    this.fireballSound = new Sound("Boss/fireball", true);
                    fireballSound = new Sound("Boss/fireball",false);
                    fireballSound.play();
                    enemyBullets.add(new BossBullet(tempBoss.getX()+20,(i*70),5,tempPlayer,tempBoss));
                    break;
                }
                else {
                    this.fireballSound = new Sound("Boss/fireball", true);
                    fireballSound.stop();
                    fireballSound = null;
                    break;
                }
            }
        }
        else if(type == 2) {
            for(int i=0;i<=10;i++) {
                if(Boss.isBossDead) {
                    Burning();
                    inGameScreenBossMusic.stop();
                    endingScreenMusic = new Sound("Ending",false);
                    endingScreenMusic.play();
                    turnEnding = true;
                    break;
                }
                if(!tempPlayer.conflict)
                {
                    this.fireballSound = new Sound("Boss/fireball", true);
                    fireballSound = new Sound("Boss/fireball",false);
                    fireballSound.play();
                    enemyBullets.add(new BossBullet(tempBoss.getX()+20,25+(i*70),5,tempPlayer,tempBoss));
                    break;
                }
                else {
                    this.fireballSound = new Sound("Boss/fireball", true);
                    fireballSound.stop();
                    fireballSound = null;
                    break;
                }
            }
        }
    }
    /**
     * 보스가 우측 상단 위치로 움직이는 패턴을 실행합니다.
     *
     * @param speed 보스의 움직임 속도
     */
    public void Pattern_GotoRightUp(int speed){
        while(true) {
            try {
                if(Boss.isBossDead) {
                    Burning();
                    inGameScreenBossMusic.stop();
                    endingScreenMusic = new Sound("Ending",false);
                    endingScreenMusic.play();
                    turnEnding = true;
                    break;
                }
                Thread.sleep(10);
            }
            catch(InterruptedException e) {
                return;
            }
            tempBoss.enemyCy-=speed;
            if(tempBoss.getY()<=0) {
                break;
            }
        }
    }
    /**
     * 보스가 우측에서 좌측으로 움직이는 패턴을 실행합니다.
     *
     * @param speed 보스의 움직임 속도
     */
    public void Pattern_GotoLeftRight(int speed) {
        for(int i=0;;i++) {
            try {
                if(Boss.isBossDead) {
                    Burning();
                    inGameScreenBossMusic.stop();
                    endingScreenMusic = new Sound("Ending",false);
                    endingScreenMusic.play();
                    turnEnding = true;
                    break;
                }
                Thread.sleep(50);
            }
            catch(InterruptedException e) {
                return;
            }
            tempBoss.enemyCx-=speed;
            if(i%2==0) {
                if(!tempPlayer.conflict) {
                    this.fireballSound = new Sound("Boss/fireball", true);
                    fireballSound = new Sound("Boss/fireball",false);
                    fireballSound.play();
                }
                else {
                    this.fireballSound = new Sound("Boss/fireball", true);
                    fireballSound.stop();
                    fireballSound = null;
                }
                enemyBullets.add(new BossBullet(tempBoss.enemyCx,tempBoss.enemyCy,3,tempPlayer,tempBoss));
            }
            if(tempBoss.getX()<-220) {
                break;
            }
        }
    }
    /**
     * 상단에서 하단으로 파이어볼이 떨어지는 패턴을 실행합니다.
     *
     * @param timing 파이어볼 떨어지는 간격
     */
    public void Pattern_DropFireBall(int timing) {
        int goal = 6;
        for(int cnt = 0;cnt<goal;cnt++) {
            if(Boss.isBossDead) {
                Burning();
                inGameScreenBossMusic.stop();
                endingScreenMusic = new Sound("Ending",false);
                endingScreenMusic.play();
                turnEnding = true;
                break;
            }
            checkDead();
            try {
                Thread.sleep(timing);
            }
            catch(InterruptedException e){
                return;
            }
            if(cnt%2 == 1) {
                for(int i=0;i<=10;i++) {
                    if(!tempPlayer.conflict) {
                        this.fireballSound = new Sound("Boss/fireball", true);
                        fireballSound = new Sound("Boss/fireball",false);
                        fireballSound.play();
                    }
                    else {
                        this.fireballSound = new Sound("Boss/fireball", true);
                        fireballSound.stop();
                        fireballSound = null;
                        break;
                    }
                    enemyBullets.add(new BossBullet(0+(i*100),10,4,tempPlayer,tempBoss));
                }
            }
            else {
                for(int i=0;i<=10;i++) {
                    if(!tempPlayer.conflict) {
                        this.fireballSound = new Sound("Boss/fireball", true);
                        fireballSound = new Sound("Boss/fireball",false);
                        fireballSound.play();
                    }
                    else {
                        this.fireballSound = new Sound("Boss/fireball", true);
                        fireballSound.stop();
                        fireballSound = null;
                    }
                    enemyBullets.add(new BossBullet(40+(i*100),10,4,tempPlayer,tempBoss));
                }
            }
        }
    }
    /**
     * 보스의 체력 바가 점진적으로 회복되는 패턴을 실행합니다.
     */
    public void Pattern_Recover() {
        for(int i=0;;i++) {
            try {
                if(Boss.isBossDead) {
                    Burning();
                    inGameScreenBossMusic.stop();
                    endingScreenMusic = new Sound("Ending",false);
                    endingScreenMusic.play();
                    turnEnding = true;
                    break;
                }
                Thread.sleep(10);
            }
            catch(InterruptedException e) {
                return;
            }
            if(i%2==0) {
                enemyBullets.add(new BossBullet(tempBoss.getX(),tempBoss.getY(),6,tempPlayer,tempBoss));
            }
            if(i%10==0) {
                if(!tempPlayer.conflict) {
                    recoverSound = new Sound("Boss/recover",false);
                    recoverSound.play();
                }
                else {
                    recoverSound.stop();
                    recoverSound = null;
                }
            }
            if(i==800) {
                break;
            }
            tempBoss.hp+=1;
        }
    }
    }
