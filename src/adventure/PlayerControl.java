/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.extra.entity.state.StateComponent;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.time.LocalTimer;
import com.almasb.fxgl.extra.entity.state.State;

/**
 *
 * @author 05200203
 */
public class PlayerControl extends StateComponent {

    private int combo = 0;
    PositionComponent position;
    private ViewComponent view;
    private PhysicsComponent physics;
    CoelhoAdventure controle;
    private LocalTimer ComboTimer, jumptimer;

    private AnimatedTexture especial;
    private AnimatedTexture texture;
    private AnimationChannel animRun, animIdle, animJump, animJumpFall, animAttack1, animAttack2, animAttack3, animDeath;

    private final State IDLE = new State() {

        @Override
        protected void onEnter(State prevState) {
            texture.loopAnimationChannel(animIdle);
        }

        @Override
        protected void onUpdate(double tpf) {
            if (Math.abs(physics.getVelocityX()) > 0) {
                setState(RUN);
            }

            if (Math.abs(physics.getVelocityY()) > 0) {
                setState(FALL);
            }
        }
    };

    private final State RUN = new State() {

        @Override
        protected void onEnter(State prevState) {
            texture.loopAnimationChannel(animRun);
        }

        @Override
        protected void onUpdate(double tpf) {
            if (Math.abs(physics.getVelocityX()) == 0) {
                setState(IDLE);
                stop();
            }
            if (Math.abs(physics.getVelocityY()) > 0) {
                setState(FALL);
            }
        }
    };

    private final State JUMP = new State() {

        @Override
        protected void onEnter(State prevState) {
            physics.setVelocityY(-450);
            jumptimer.capture();
            texture.playAnimationChannel(animJump);
            controle.getAudioPlayer().playSound("Jump.wav");
        }

        @Override
        protected void onUpdate(double tpf) {

        }
    };

    private final State FALL = new State() {

        @Override
        protected void onEnter(State prevState) {
            texture.loopAnimationChannel(animJumpFall);
        }

        @Override
        protected void onUpdate(double tpf) {

            if (physics.getVelocityY() == 0) {
                setState(IDLE);
            }
        }
    };

    private final State Attack = new State() {

        @Override
        protected void onEnter(State prevState) {
            texture.playAnimationChannel(animAttack1);
            if (getEntity().getView().getScaleX() == 1) {

                controle.getGameWorld().spawn("hitbox", getEntity().getX() + 30, getEntity().getY() + 10);

            } else if (getEntity().getView().getScaleX() == -1) {

                controle.getGameWorld().spawn("hitbox", getEntity().getX() - 10, getEntity().getY());

            }
            ComboTimer.capture();

        }

        @Override
        protected void onUpdate(double tpf) {
            if (ComboTimer.elapsed(Duration.seconds(0.5))) {
                ComboTimer.capture();
                setState(IDLE);
            }
        }

    };

    private final State Attack2 = new State() {

        @Override
        protected void onEnter(State prevState) {
            texture.playAnimationChannel(animAttack3);
            if (getEntity().getView().getScaleX() == 1) {

                controle.getGameWorld().spawn("hitbox", getEntity().getX() + 30, getEntity().getY() + 10);

            } else if (getEntity().getView().getScaleX() == -1) {

                controle.getGameWorld().spawn("hitbox", getEntity().getX() - 10, getEntity().getY());

            }
            ComboTimer.capture();
        }

        @Override
        protected void onUpdate(double d) {
            if (ComboTimer.elapsed(Duration.seconds(0.5))) {
                ComboTimer.capture();
                setState(IDLE);

            }
        }

    };

    private final State Attack3 = new State() {
        @Override
        protected void onEnter(State prevState) {
            texture.playAnimationChannel(animAttack2);
            if (getEntity().getView().getScaleX() == 1) {

                controle.getGameWorld().spawn("hitbox", getEntity().getX() + 40, getEntity().getY() + 10);

            } else if (getEntity().getView().getScaleX() == -1) {

                controle.getGameWorld().spawn("hitbox", getEntity().getX() - 5, getEntity().getY());

            }
            ComboTimer.capture();
        }

        @Override
        protected void onUpdate(double d) {
            if (ComboTimer.elapsed(Duration.seconds(0.5))) {
                ComboTimer.capture();
                setState(IDLE);

            }
        }

        @Override
        protected void onExit() {
            combo = 0;
        }

    };

    PlayerControl() {
        animRun = new AnimationChannel("/Player/Run.png", 6, 29, 28, Duration.seconds(0.8), 0, 5);
        animIdle = new AnimationChannel("/Player/Idle.png", 4, 25, 27, Duration.seconds(0.8), 0, 3);
        animJump = new AnimationChannel("/Player/jump.png", 8, 28, 33, Duration.seconds(0.8), 0, 7);
        animJumpFall = new AnimationChannel("/Player/JumpFall.png", 2, 17, 31, Duration.seconds(0.2), 0, 1);
        animAttack1 = new AnimationChannel("/Player/Attack1Test.png", 6, 37, 36, Duration.seconds(0.5), 0, 5);
        animAttack2 = new AnimationChannel("/Player/Attack2.png", 6, 50, 26, Duration.seconds(0.5), 0, 5);
        animAttack3 = new AnimationChannel("/Player/Attack3.png", 4, 39, 29, Duration.seconds(0.5), 0, 3);
        animDeath = new AnimationChannel("/Player/hurt.png", 4, 25, 27, Duration.seconds(0.3), 0, 3);

        controle = new CoelhoAdventure();
        texture = new AnimatedTexture(animIdle);
        jumptimer = FXGL.newLocalTimer();
        texture.start(FXGL.getApp().getStateMachine().getPlayState());
        texture.setOnCycleFinished(() -> {
            if (texture.getAnimationChannel() == animJump) {
                setState(FALL);
            }
        });
    }

    @Override
    public void onAdded() {
        ComboTimer = FXGL.newLocalTimer();
        setState(IDLE);
        view.getView().addNode(texture);
    }

    public void left() {
        view.getView().setScaleX(-1);
        physics.setVelocityX(-150);
    }

    public void right() {
        view.getView().setScaleX(1);
        physics.setVelocityX(150);
    }

    public void jump() {
        if (jumptimer.elapsed(Duration.seconds(0.20))) {

            if (getState() == IDLE || getState() == RUN) {
                setState(JUMP);
            }
        }
    }

    public void attack() {
        if (getState() == IDLE || getState() == RUN) {
            combo++;
            if (combo == 1) {
                ComboTimer.capture();
                setState(Attack);
            }

            if (combo == 2) {
                ComboTimer.capture();
                setState(Attack2);
            }

            if (combo == 3) {
                ComboTimer.capture();
                setState(Attack3);
            }
        }

    }

    public void stop() {
        physics.setVelocityX(physics.getVelocityX() * 0);
    }

    public void damage() {
        texture.playAnimationChannel(animDeath);
        texture.setOnCycleFinished(() -> {
            setState(IDLE);
        });

    }

}
