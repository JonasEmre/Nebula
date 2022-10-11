package nebula.screen

import ktx.ashley.entity
import ktx.ashley.with
import nebula.Nebula
import nebula.ecs.component.*

class GameScreen(game: Nebula) : NebulaScreen(game) {

    override fun show() {
        engine.entity {
            /* Aynı Unity'deki gibi component sistemi ekleniyor. with{} ile eklediğimiz componentlar
             * daha sonra liste elemanına ulaşmak gibi ulaşılabilir oluyor. */
            with<TransformComponent> {
                position.set(8f, 3.5f, 1f)
            }
            with<MoveComponent>()
            with<GraphicComponent>()
            with<PlayerComponent>()
            with<FacingComponent>()
        }
    }


    override fun render(delta: Float) {
        /* Engine yani ashley core sistemi update edilmesi gerekiyor.
           Bu sayede Ashley, tüm entityler ile ilgili işlerini yapabiliyor.
           engine.update() diğer sistemlerdeki processEntity fonksiyonlarını tek tek çalıştırıyor.
           Dolayısıyla ana ekranımızda şu an update etmemiz gerekiyor.*/
        engine.update(delta)
    }
}