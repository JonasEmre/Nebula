package nebula.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import ktx.ashley.allOf
import ktx.ashley.get
import nebula.V_HEIGTH
import nebula.V_WIDTH
import nebula.ecs.component.*
import kotlin.math.max
import kotlin.math.min

private const val UPDATE_RATE = 1 / 25f
private const val HOR_ACCELERATION = 16.5f
private const val VER_ACCELERATION = 2.25f
private const val MAX_VER_PLAYER_NEG_SPEED = 0.75f
private const val MAX_VER_PLAYER_POS_SPEED = 5f
private const val MAX_HOR_SPEED = 5.5f


class MoveSystem : IteratingSystem(
    allOf(TransformComponent::class, MoveComponent::class).exclude(RemoveComponent::class.java).get()
) {
    // Sanırım FixedUpdate() mantığını kendimiz yazıyoruz burada.
    private var accumulator = 0f

    override fun update(deltaTime: Float) {
        accumulator += deltaTime
        while (accumulator >= UPDATE_RATE) {
            accumulator -= deltaTime
            super.update(UPDATE_RATE)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        require(transform != null) { "Entity TransformComponent'a sahip değil, entity=$entity" }
        val move = entity[MoveComponent.mapper]
        require(move != null) { "Entity MoveComponent'a sahip değil, entity=$entity" }

        val player = entity[PlayerComponent.mapper]
        if (player != null) {
            // Player movement
            entity[FacingComponent.mapper]?.let {facing->
                movePlayer(transform, move, player, facing, deltaTime)
            }
        } else {
            // Other entity movement
            moveEntity(transform, move, deltaTime)
        }
    }

    private fun movePlayer(
        transform: TransformComponent,
        move: MoveComponent,
        playerComponent: PlayerComponent,
        facingComponent: FacingComponent,
        deltaTime: Float
    ) {

        // Update horizantal speed
        move.speed.x = when(facingComponent.direction) {
            FacingDirection.LEFT -> min(0f, move.speed.x - HOR_ACCELERATION * deltaTime)
            FacingDirection.RIGHT -> max(0f, move.speed.x + HOR_ACCELERATION * deltaTime)
            else -> 0f
        }
        move.speed.x = MathUtils.clamp(move.speed.x, -MAX_HOR_SPEED, MAX_HOR_SPEED)

        //Update vertical speed
        move.speed.y = MathUtils.clamp(
            move.speed.y - VER_ACCELERATION * deltaTime,
            -MAX_VER_PLAYER_NEG_SPEED,
            MAX_VER_PLAYER_POS_SPEED
        )

        moveEntity(transform, move, deltaTime)
    }

    private fun moveEntity(transform: TransformComponent, move: MoveComponent, deltaTime: Float) {
        /* Ekranın köşesi ile size arasındaki farkı çıkarıyoruz ki böyle ekran dışına çıkmayalım
        *  Bu fonksiyon sayesinde yürüme mekanizması devreye girecek, clamp() ise limitleri define ediyor.*/
        transform.position.x = MathUtils.clamp(
            transform.position.x + move.speed.x * deltaTime,
            0f, V_WIDTH - transform.size.x
        )
        transform.position.y = MathUtils.clamp(
            transform.position.y + move.speed.y * deltaTime,
            1f, V_HEIGTH + 1f - transform.size.y
        )
    }
}