/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.entity.Entity;

/**
 *
 * @author 05200203
 */
public class BolaDeFogoControl extends Component {

    private PhysicsComponent physics;
    private Entity player;
    double x, y;

    @Override

    public void onUpdate(double tpf) {
    }

    @Override
    public void onAdded() {
        player = FXGL.getApp().getGameWorld().getSingleton(CoelhoType.PLAYER).get();
        x = -813 + player.getX();
        y = player.getY()/2;
        physics.setOnPhysicsInitialized(() -> {
            physics.setVelocityX(x);
            physics.setVelocityY(y);
        });

    }

}
  