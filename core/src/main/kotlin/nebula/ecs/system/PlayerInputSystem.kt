package nebula.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import ktx.ashley.allOf
import ktx.ashley.get
import nebula.ecs.component.FacingComponent
import nebula.ecs.component.FacingDirection
import nebula.ecs.component.PlayerComponent
import nebula.ecs.component.TransformComponent

class PlayerInputSystem :
    IteratingSystem(allOf(PlayerComponent::class, TransformComponent::class, FacingComponent::class).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val facing = entity[FacingComponent.mapper]
        require(facing != null) { "Entity FacingComponent'a sahip değil, entity=$entity" }
        val transform = entity[FacingComponent.mapper]
        require(transform != null) { "Entity TransformComponent'a sahip değil, entity=$entity" }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            facing.direction = FacingDirection.LEFT
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            facing.direction = FacingDirection.RIGHT
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
            facing.direction = FacingDirection.DEFAULT
        }
    }
}