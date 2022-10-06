package nebula.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Texture
import ktx.ashley.entity
import ktx.ashley.get
import ktx.ashley.with
import ktx.log.logger
import nebula.Nebula
import nebula.ecs.component.FacingComponent
import nebula.ecs.component.GraphicComponent
import nebula.ecs.component.PlayerComponent
import nebula.ecs.component.TransformComponent

private val log = logger<Nebula>()

class GameScreen(game: Nebula) : NebulaScreen(game) {


    private val player = engine.entity {
        /* Aynı Unity'deki gibi component sistemi ekleniyor. with{} ile eklediğimiz componentlar
         * daha sonra liste elemanına ulaşmak gibi ulaşılabilir oluyor. */
        with<TransformComponent> {
            position.set(8f, 3.5f, 1f)
        }
        with<GraphicComponent>()
        with<PlayerComponent>()
        with<FacingComponent>()
    }

    override fun show() {
        log.debug { "İlk ekran gösterimde." }
    }

    override fun render(delta: Float) {
        // Engine yani ashley core sistemi update edilmesi gerekiyor
        engine.update(delta)
    }
}