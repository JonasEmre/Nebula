package nebula.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import ktx.log.logger
import nebula.ecs.component.GraphicComponent
import nebula.ecs.component.TransformComponent

private val log = logger<RenderSystem>()

class RenderSystem(
    private val batch: Batch,
    private val gameViewPort: Viewport) : SortedIteratingSystem(
    allOf(TransformComponent::class, GraphicComponent::class).get(),
    compareBy { entity -> entity[TransformComponent.mapper] }
) {

    override fun update(deltaTime: Float) {
        /* Burada öncelikle forceSort() yaparak tüm grafiklerin sıralamasını yapıyoruz. Bu sayede
        kim daha önce veya arkada onu biliyoruz. batch.use kısmında ise super.update() çağırıyoruz.
        Bu ise processEntity() fonksiyonunu devreye sokuyor ki zaten orada sprite.run() ile
        grafiği çizdirme işlemini yapıyoruz. O yüzden sadece super.update() var. */
        forceSort()
        gameViewPort.apply()
        batch.use(gameViewPort.camera.combined) {
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        // Tüm componentları buraya sıralayıp, null olup olmadığına bakıyoruz. Bu yaklaşım daha temiz ve kolay.
        val transform = entity[TransformComponent.mapper]
        require(transform != null) { "Entity TransformComponent'a sahip değil, entity=$entity" }
        val graphic = entity[GraphicComponent.mapper]
        require(graphic != null) { "Entity GraphicComponent'a sahip değil, entity=$entity" }

        // Ola ki graphic null değeri almış ise direkt return atıyoruz.
        if (graphic.sprite.texture == null){
            log.error { "Entity grafiğe sahip değil, entity=$entity" }
            return
        }

        // graphic null olmayacağına göre; bu blok devreye girecektir.
        graphic.sprite.run {
            rotation = transform.rotationDeg
            setBounds(transform.position.x, transform.position.y, transform.size.x, transform.size.y)
            draw(batch)
        }
    }


}