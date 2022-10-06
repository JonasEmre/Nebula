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
import nebula.ecs.system.PlayerAnimationSystem
import nebula.ecs.system.PlayerInputSystem
import nebula.ecs.system.RenderSystem
import nebula.screen.GameScreen
import nebula.screen.NebulaScreen

private val log = logger<Nebula>()
const val UNIT_SCALE = 1f / 16f

class Nebula : KtxGame<NebulaScreen>() {
    val gameViewport: Viewport = FitViewport(16f, 9f)
    val batch: Batch by lazy { SpriteBatch() }
    private val playerShipTexture: Texture by lazy { Texture(Gdx.files.internal("graphics/player_ship.png")) }

    val engine: Engine by lazy { PooledEngine().apply {
        addSystem(PlayerInputSystem())
        addSystem(PlayerAnimationSystem(playerShipTexture))
        addSystem(RenderSystem(batch, gameViewport))
    } }

    override fun create() {
        Gdx.app.logLevel = LOG_DEBUG
        log.debug { "İlk Kuruluş" }
        addScreen(GameScreen(this))
        setScreen<GameScreen>()
    }

    override fun dispose() {
        batch.dispose()
        playerShipTexture.dispose()
    }
}