package nebula.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.app.KtxScreen
import nebula.Nebula

abstract class NebulaScreen(
    private val game: Nebula,
    val batch: Batch = game.batch,
    val engine: Engine = game.engine,
    private val gameViewport: Viewport = game.gameViewport
) : KtxScreen {
    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
    }
}