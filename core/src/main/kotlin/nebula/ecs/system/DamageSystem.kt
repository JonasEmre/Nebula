package nebula.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.addComponent
import ktx.ashley.allOf
import ktx.ashley.get
import nebula.ecs.component.PlayerComponent
import nebula.ecs.component.RemoveComponent
import nebula.ecs.component.TransformComponent
import kotlin.math.max

private const val DAMAGE_AREA_HEIGHT = 2f
private const val DAMAGE_PER_SECOND = 25f
private const val EXPLOSION_DURATION = 0.9f

class DamageSystem : IteratingSystem(
    allOf(PlayerComponent::class, TransformComponent::class).exclude(RemoveComponent::class.java).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val transform = entity[TransformComponent.mapper]
        require(transform != null) { "Entity TransformComponent'a sahip değil, entity=$entity" }
        val player = entity[PlayerComponent.mapper]
        require(player != null) { "Entity PlayerComponent'a sahip değil, entity=$entity" }

        if (transform.position.y <= DAMAGE_AREA_HEIGHT) {
            var damage = DAMAGE_PER_SECOND * deltaTime
            if (player.shield > 0f) {
                val blockAmount = player.shield
                player.shield = max(0f, player.shield - damage)
                damage -= blockAmount
                if (damage <= 0f) {
                    // Tüm damage bloklandı demek
                    return
                }
            }
            player.life -= damage
            if (player.life <= 0f){
                // Player ölüor ve belli bir süre sonra kayboluyor.
                entity.addComponent<RemoveComponent>(engine){
                    delay = EXPLOSION_DURATION
                }
            }
        }
    }
}