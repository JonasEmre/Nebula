package nebula.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Pool.Poolable
import ktx.ashley.mapperFor

class TransformComponent : Component, Poolable, Comparable<TransformComponent> {

    val position = Vector3()
    val size = Vector2(1f, 1f)
    var rotationDeg = 0f

    // Component sınıflarında reset nedenini bilmediğim bir şekilde resetlenme istiyor.
    override fun reset() {
        position.set(Vector3.Zero)
        size.set(1f, 1f)
        rotationDeg = 0f
    }

    /* Entity'nin kıyaslamasını yapacak fonksiyon burada. Eğer z ekseninde herhangi bir farklılık yok ise
    y eksenindeki farklılığa bakacak ve onu döndürecektir. Bu sayede y ekseninde yukarı olan entity;
    render sırasında diğer objenin arkasındaymış gibi gözükecektir.*/
    override fun compareTo(other: TransformComponent): Int {
        val zDifference: Float = position.z - other.position.z
        return (if (zDifference == 0f) position.y - other.position.y else zDifference).toInt()
    }

    companion object {
        val mapper = mapperFor<TransformComponent>()
    }
}