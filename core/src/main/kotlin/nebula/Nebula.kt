package nebula

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.app.KtxGame
import ktx.log.logger
import nebula.ecs.system.*
import nebula.screen.GameScreen
import nebula.screen.NebulaScreen

private val log = logger<Nebula>()
const val V_WIDTH = 16f
const val V_HEIGTH = 9f

class Nebula : KtxGame<NebulaScreen>() {
    val gameViewport: Viewport = FitViewport(V_WIDTH, V_HEIGTH)
    val batch: Batch by lazy { SpriteBatch() }
    private val playerShipTexture: Texture by lazy { Texture(Gdx.files.internal("graphics/player_ship.png")) }

    val engine: Engine by lazy {
        PooledEngine().apply {
            addSystem(PlayerInputSystem())
            addSystem(MoveSystem())
            addSystem(DamageSystem())
            addSystem(PlayerAnimationSystem(playerShipTexture))
            addSystem(RenderSystem(batch, gameViewport))
            addSystem(RemoveSystem())
        }
    }

    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG
        addScreen(GameScreen(this))
        setScreen<GameScreen>()
    }

    override fun dispose() {
        batch.dispose()
        playerShipTexture.dispose()
        log.debug { "Rendercalls:${(batch as SpriteBatch).renderCalls}" }
    }
}