/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.util.Duration;
import com.almasb.fxgl.extra.entity.components.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author 05200203
 */
public class CoelhoFactory implements EntityFactory {

    @Spawns("gatilho")
    public Entity newgatinho(SpawnData data) {
        return Entities.builder()
                .type(CoelhoType.GATILHO)
                .bbox(new HitBox(BoundingShape.box(3, 700)))
                .with(new CollidableComponent(true))
                .build();
    }
    
    @Spawns("brokenL")
    public Entity newBrokenLarge(SpawnData data) {
        return Entities.builder()
                .type(CoelhoType.BROKENL)
                .from(data)
                .viewFromTexture("/Map/BrokenL.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("hitbox")
    public Entity newHitbox(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
        return Entities.builder()
                .type(CoelhoType.HITBOX)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(4, 20)))
                .with(new CollidableComponent(true))
                .with(physics, new ExpireCleanComponent(Duration.seconds(0.3)))
                .build();
    }

    @Spawns("forca")
    public Entity newForca(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
        return Entities.builder()
                .type(CoelhoType.FORCA)
                .from(data)
                .viewFromTexture("/Map/forca.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .with(physics)
                .build();
    }

    @Spawns("end")
    public Entity newEnd(SpawnData data) {
        return Entities.builder()
                .type(CoelhoType.END)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("life")
    public Entity newPowerUp(SpawnData data) {
        return Entities.builder()
                .type(CoelhoType.LIFE)
                .from(data)
                .viewFromTexture("/Map/Vida_Icon.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("broken")
    public Entity newBroken(SpawnData data) {

        return Entities.builder()
                .type(CoelhoType.BROKEN)
                .from(data)
                .viewFromTexture("/Map/Broken.png")
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("lightbandit")
    public Entity newEnemy(SpawnData data) {

        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        return Entities.builder()
                .type(CoelhoType.LIGHTBANDIT)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(24, 44)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new LightBanditControl())
                .build();
    }

    @Spawns("knight")
    public Entity newKnight(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        return Entities.builder()
                .type(CoelhoType.KNIGHT)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(17, 36)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new KnightControl())
                .build();
    }

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return Entities.builder()
                .type(CoelhoType.PLATAFORMA)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        return Entities.builder()
                .type(CoelhoType.PLAYER)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(25, 27)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new PlayerControl())
                .build();
    }

    @Spawns("coin")
    public Entity newCoin(SpawnData data) {
        return Entities.builder()
                .type(CoelhoType.COIN)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(80 / 5, 16)))
                .viewFromAnimatedTexture("/Map/Coin.png", 5, Duration.seconds(0.5), true)
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("door")
    public Entity newDoor(SpawnData data) {
        return Entities.builder()
                .type(CoelhoType.DOOR)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("hitboxenemy")
    public Entity newHitboxLB(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
        return Entities.builder()
                .type(CoelhoType.HITBOXENEMY)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(5, 20)))
                .with(new CollidableComponent(true))
                .with(physics, new ExpireCleanComponent(Duration.seconds(0.03)))
                .build();
    }


    @Spawns("finalboss")
    public Entity newBoss(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
        return Entities.builder()
                .type(CoelhoType.FINALBOSS)
                .from(data)
                .bbox(new HitBox(BoundingShape.box(100, 175)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new FinalBossControl())
                .build();
    }

    @Spawns("boladefogo")
    public Entity newBolaDeFogo(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
        return Entities.builder()
                .type(CoelhoType.BOLADEFOGO)
                .from(data)
                .viewFromNodeWithBBox(new Circle(30, Color.CRIMSON))
                .with(new CollidableComponent(true))
                .with(physics, new ExpireCleanComponent(Duration.seconds(3)))
                .with(new BolaDeFogoControl())
                .build();
    }

}
