package com.isoterik.xgdx.x2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isoterik.xgdx.GameCamera;
import com.isoterik.xgdx.GameObject;
import com.isoterik.xgdx.XGdx;
import com.isoterik.xgdx.utils.GameWorldUnits;

/**
 * A game camera that wraps an {@link OrthographicCamera} for rendering in 2D space.
 *
 * @author isoteriksoftware
 */
public class GameCamera2d extends GameCamera {
    private SpriteBatch spriteBatch;

    private Color backgroundColor;

    /**
     * Creates a new instance given a viewport.
     * * <strong>Note:</strong> an {@link OrthographicCamera} will be created if it doesn't exist.
     * @param viewport the viewport
     */
    public GameCamera2d(Viewport viewport) {
        super(viewport);
        spriteBatch = new SpriteBatch();
        this.backgroundColor = new Color(1, 0, 0, 1);

        if (camera == null || !(camera instanceof  OrthographicCamera))
            camera = new OrthographicCamera(viewport.getWorldWidth(), viewport.getWorldHeight());

        getCamera().position.set(viewport.getWorldWidth() * .5f, viewport.getWorldHeight() * .5f, 0);
        viewport.setCamera(camera);
    }

    /**
     * Creates a new instance given an instance of {@link GameWorldUnits} for unit conversions. The viewport defaults to an {@link ExtendViewport}.
     * @param gameWorldUnits an instance of {@link GameWorldUnits}
     */
    public GameCamera2d(GameWorldUnits gameWorldUnits) {
        this(new ExtendViewport(gameWorldUnits.getWorldWidth(), gameWorldUnits.getWorldHeight(),
                new OrthographicCamera(gameWorldUnits.getWorldWidth(), gameWorldUnits.getWorldHeight())));
    }

    /**
     * Creates a new scene. The screen dimensions defaults to (1280 x 720) taking 100 pixels per meter for unit conversions.
     * The viewport defaults to an {@link ExtendViewport}.
     */
    public GameCamera2d() {
        this(new GameWorldUnits(XGdx.instance().defaultSettings.VIEWPORT_WIDTH, XGdx.instance().defaultSettings.VIEWPORT_HEIGHT,
                XGdx.instance().defaultSettings.PIXELS_PER_UNIT));
    }

    /**
     * Sets the sprite batch used for rendering
     * @param spriteBatch the sprite batch
     */
    public void setSpriteBatch(SpriteBatch spriteBatch)
    { this.spriteBatch = spriteBatch; }

    /**
     *
     * @return the sprite batch used for rendering
     */
    public SpriteBatch getSpriteBatch()
    { return spriteBatch; }

    /**
     * Sets the color used for clearing the scene every frame.
     * @param backgroundColor color used for clearing the scene every frame
     */
    public void setBackgroundColor(Color backgroundColor)
    { this.backgroundColor = backgroundColor; }

    /**
     *
     * @return color used for clearing the scene every frame
     */
    public Color getBackgroundColor()
    { return backgroundColor; }

    @Override
    public OrthographicCamera getCamera()
    { return (OrthographicCamera)camera; }

    @Override
    public void setup(Viewport viewport) {
        super.setup(viewport);
        camera = new OrthographicCamera(viewport.getWorldWidth(), viewport.getWorldHeight());
        camera.position.set(viewport.getWorldWidth() * .5f, viewport.getWorldHeight() * .5f, 0);
        viewport.setCamera(camera);
    }

    @Override
    public void attach() {
        if (spriteBatch == null)
            spriteBatch = new SpriteBatch();
    }

    @Override
    public void detach() {
        if (spriteBatch != null) {
            spriteBatch.dispose();
            spriteBatch = null;
        }
    }

    @Override
    public void destroy() {
        if (spriteBatch != null) {
            spriteBatch.dispose();
            spriteBatch = null;
        }
    }

    @Override
    public void preRender(Array<GameObject> gameObjects) {
        ScreenUtils.clear(backgroundColor);
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
    }

    @Override
    public void postRender(Array<GameObject> gameObjects) {
        spriteBatch.end();
    }
}





























