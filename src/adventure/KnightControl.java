/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;

/**
 *
 * @author augus
 */
public class KnightControl extends Component {

    private int life = 3;
    private Entity player;
    private ViewComponent view;
    private PhysicsComponent physics;
    private final AnimatedTexture texture;
    private final AnimationChannel animIdle;
    private final AnimationChannel animWalk, animAttack, animHurt;
    boolean dead = false;

    public KnightControl() {
        animIdle = new AnimationChannel("/Enemy/Knight/knightidle.png", 4, 18, 35, Duration.seconds(0.5), 0, 3);
        animWalk = new AnimationChannel("/Enemy/Knight/knightwalk.png", 8, 19, 36, Duration.seconds(0.5), 0, 7);
        animAttack = new AnimationChannel("/Enemy/Knight/knightslash.png", 10, 42, 38, Duration.seconds(0.4), 0, 9);
        animHurt = new AnimationChannel("/Enemy/Knight/knightdeath.png", 2, 19, 36, Duration.seconds(0.5), 0, 1);
        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        view.getView().addNode(texture);
        texture.loopAnimationChannel(animIdle);
        getEntity().setScaleX(-1);
        player = FXGL.getApp().getGameWorld().getSingleton(CoelhoType.PLAYER).get();
    }

    @Override
    public void onUpdate(double tpf) {
        if (distanceToPlayer(player) <= 250) {
            if (distanceToPlayer(player) < 20) {
                stop();
            } else {
                if (player.getX() - 15 < getEntity().getX()) {
                    getEntity().setScaleX(-1);
                    physics.setVelocityX(-100);
                } else {
                    getEntity().setScaleX(1);
                    physics.setVelocityX(100);
                }
            }

        }
        if (physics.getVelocityX() == 0 && texture.getAnimationChannel() == animWalk) {
            texture.loopAnimationChannel(animIdle);
        } else if (physics.getVelocityX() > 0 || physics.getVelocityX() < 0) {
            if (texture.getAnimationChannel() == animIdle) {
                texture.loopAnimationChannel(animWalk);
            }
        }
        
    }

    private double distanceToPlayer(Entity player) {
        return player.getComponent(PositionComponent.class).distance(getEntity().getComponent(PositionComponent.class));
    }

    public void attacar() {
        if (player.getView().getScaleX() > 0) {
            texture.playAnimationChannel(animAttack);
            getEntity().setScaleX(1);
            texture.setOnCycleFinished(() -> {
                texture.loopAnimationChannel(animIdle);
            });
        } else if (player.getView().getScaleX() < 0) {
            texture.playAnimationChannel(animAttack);
            getEntity().setScaleX(-1);
            texture.setOnCycleFinished(() -> {
                texture.loopAnimationChannel(animIdle);
            });
        }
    }


    public void stop() {
        physics.setVelocityX(physics.getVelocityX() * 0);
    }

    public void damage() {
        texture.playAnimationChannel(animHurt);
        texture.setOnCycleFinished(() -> {
            texture.loopAnimationChannel(animIdle);
        });

    }

    public void setLife(int life) {
        this.life = life;
        System.out.println(this.life);
        if (this.life <= 0) {
            isDead();
        }

    }

    public boolean isDead() {
        return dead = true;
    }


}
