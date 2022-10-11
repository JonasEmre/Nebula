package nebula.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import ktx.ashley.allOf
import ktx.ashley.get
import nebula.ecs.component.RemoveComponent

class RemoveSystem : IteratingSystem(
    allOf(RemoveComponent::class).get()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val remove = entity[RemoveComponent.mapper]
        require(remove != null) { "Entit RemoveComponent'a sahip değil, entity=$entity" }

        // Bu remove sisteminin tam olarak ne olduğunu anlamadım.
        remove.delay -= deltaTime
        if (remove.delay <= 0f) engine.removeEntity(entity)
    }
}