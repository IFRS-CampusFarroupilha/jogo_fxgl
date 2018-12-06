/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.time.LocalTimer;
import java.util.Random;
import javafx.util.Duration;

/**
 *
 * @author augus
 */
public class FinalBossControl extends Component {

    private int escudo = 0;
    private int life = 3;
    private Entity player;
    private ViewComponent view;
    private PhysicsComponent physics;
    private final AnimatedTexture texture;
    private final AnimationChannel idle;
    private final AnimationChannel attack, hurt;
    boolean dead = false;
    protected CoelhoAdventure control;
    private LocalTimer attacktime;
    protected Random rand;
    private Entity boladefogo;
    boolean primeiroAtk = true;
    int chance = 0;
    int bolas = 0;

    public FinalBossControl() {
        idle = new AnimationChannel("/Enemy/Final Boss/idleteste.png", 2, 100, 175, Duration.seconds(0.5), 0, 1);
        attack = new AnimationChannel("/Enemy/Final Boss/attack.png", 7, 100, 175, Duration.seconds(1), 0, 6);
        hurt = new AnimationChannel("/Enemy/Final Boss/hurt.png", 7, 100, 175, Duration.seconds(1), 0, 6);
        texture = new AnimatedTexture(idle);
    }

    @Override
    public void onAdded() {
        rand = new Random();
        control = new CoelhoAdventure();
        view.getView().addNode(texture);
        texture.loopAnimationChannel(idle);
        attacktime = FXGL.newLocalTimer();
        getEntity().setScaleX(-1);
        player = FXGL.getApp().getGameWorld().getSingleton(CoelhoType.PLAYER).get();
    }

    @Override
    public void onUpdate(double tpf) {
        if (attacktime.elapsed(Duration.seconds(1.0))) {
            int k;
            k = rand.nextInt(6);
            if (k == 3) {
                attack();
            }
            attacktime.capture();
        }
    }

    public void attacao() {
        if (life == 2) {
            boladefogo = control.getGameWorld().spawn("boladefogo", 800, 0);
            boladefogo = control.getGameWorld().spawn("boladefogo", 700, 0);
            boladefogo = control.getGameWorld().spawn("boladefogo", 600, 0);
            boladefogo = control.getGameWorld().spawn("boladefogo", 400, 0);
            boladefogo = control.getGameWorld().spawn("boladefogo", 300, 0);
            boladefogo = control.getGameWorld().spawn("boladefogo", 200, 0);
        } else if(life <= 1){
            boladefogo = control.getGameWorld().spawn("boladefogo", 800, 0);
            boladefogo = control.getGameWorld().spawn("boladefogo", 700, 0);
            boladefogo = control.getGameWorld().spawn("boladefogo", 600, 0);
            boladefogo = control.getGameWorld().spawn("boladefogo", 400, 0);
            boladefogo = control.getGameWorld().spawn("boladefogo", 300, 0);
            boladefogo = control.getGameWorld().spawn("boladefogo", 650, 0);
            boladefogo = control.getGameWorld().spawn("boladefogo", 450, 0);
            boladefogo = control.getGameWorld().spawn("boladefogo", 350, 0);
            boladefogo = control.getGameWorld().spawn("boladefogo", 250, 0);
            boladefogo = control.getGameWorld().spawn("boladefogo", 500, 0);
            boladefogo = control.getGameWorld().spawn("boladefogo", 550, 0);      
        }

    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        if (this.life <= 0) {
            isDead();
        } else {
            if (escudo == 0) {
                this.life = life;
                System.out.println(this.life);
                start();
                attacao();
            }
        }

    }

    public boolean isDead() {
        return dead = true;
    }

    public void start() {
        attacktime.capture();
        texture.playAnimationChannel(hurt);
        texture.setOnCycleFinished(() -> {
            texture.loopAnimationChannel(idle);
        });
        escudo = 1;
        for (int i = 0; i < 2; i++) {
            control.spawnenemies();
        }
    }

    public void Fattack() {
        FXGL.getApp().getMasterTimer().runAtInterval(() -> {
            if (bolas >= 10) {

            } else {
                boladefogo = control.getGameWorld().spawn("boladefogo", 838, 150);
                bolas++;

            }

        }, Duration.seconds(0.8));
    }

    public void attack() {
        texture.playAnimationChannel(attack);

        texture.setOnCycleFinished(() -> {
            texture.loopAnimationChannel(idle);
        });
        boladefogo = control.getGameWorld().spawn("boladefogo", 838, 150);
    }

    public void setEscudo(int escudo) {
        this.escudo = escudo;
    }

}
