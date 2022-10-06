package nebula.ecs.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import ktx.ashley.allOf
import ktx.ashley.get
import nebula.ecs.component.*

class PlayerAnimationSystem(
    playerTexture: Texture
) : IteratingSystem(
    allOf(PlayerComponent::class, TransformComponent::class, GraphicComponent::class).get()
),
    EntityListener {

    private var lastDirection = FacingDirection.DEFAULT

    private val defaultRegion = TextureRegion(
        playerTexture, ((playerTexture.width / 3) * 1),
        0, playerTexture.width / 3, playerTexture.height
    )
    private val leftRegion = TextureRegion(
        playerTexture, 0, 0, playerTexture.width / 3, playerTexture.height
    )
    private val rightRegion = TextureRegion(
        playerTexture, ((playerTexture.width / 3) * 2), 0, playerTexture.width / 3, playerTexture.height
    )

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        engine.addEntityListener(family, this)
    }

    override fun removedFromEngine(engine: Engine) {
        super.removedFromEngine(engine)
        engine.removeEntityListener(this)
    }

    override fun entityAdded(entity: Entity) {
        /* Daha önce ana ekranda yaptığımız, geminin sprite region belirtmelerini zaten GraphicComponent
        * sınıfında yapıyoruz. Dolayısıyla burada sadece region belirtiyoruz.*/
        entity[GraphicComponent.mapper]?.setSpriteRegion(defaultRegion)
    }

    override fun entityRemoved(entity: Entity?) = Unit

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val facing = entity[FacingComponent.mapper]
        require(facing != null) { "(AnimationSystem)Entity FacingComponent'a sahip değil, entity=$entity" }
        val graphic = entity[GraphicComponent.mapper]
        require(graphic != null) { "(AnimationSystem)Entity GraphicComponent'a sahip değil, entity=$entity" }

        if (facing.direction == lastDirection && graphic.sprite.texture != null) {
            // Direction doğru tarafa bakıyor ve grafik texture sahibi, bir şey yapmaya gerek yok.
            return
        }

        lastDirection = facing.direction
        val currentRegion = when (facing.direction) {
            FacingDirection.RIGHT -> rightRegion
            FacingDirection.LEFT -> leftRegion
            else -> defaultRegion
        }
        graphic.setSpriteRegion(currentRegion)
    }
}