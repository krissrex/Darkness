package com.polarbirds.darkness.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.polarbirds.darkness.DarknessGame;
import com.polarbirds.darkness.Debug;
import com.polarbirds.darkness.asset.Assets;

/**
 * Created by Kristian Rekstad on 05.03.2015.
 */
public class LoadingScreen implements Screen{

    DarknessGame game;
    boolean loaded;
    boolean continueClicked;
    boolean nextScreen;

    BitmapFont font;
    Stage stage;
    TextButton continueButton;
    Label loadingText;
    Label motdText;
    Texture background;

    SpriteBatch spriteBatch;

    int w, h;

    public LoadingScreen(DarknessGame game){
        this.game = game;
        loaded = false;
        font = new BitmapFont();
    }

    @Override
    public void show() {
        continueClicked = false;
        nextScreen = false;

        stage = new Stage();

        background = DarknessGame.ASSET_MANAGER.get(Assets.texture.background, Texture.class);
        spriteBatch = new SpriteBatch();
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();


        DarknessGame.INPUT_MULTIPLEXER.addProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setWidth(Gdx.graphics.getWidth());

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.fontColor = Color.WHITE;
        continueButton = new TextButton("Continue", style);
        continueButton.setStyle(style);
        continueButton.setVisible(loaded);
        continueButton.setPosition(0, Gdx.graphics.getHeight()/2, Align.center);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        loadingText = new Label("loading: 0", labelStyle);
        loadingText.setAlignment(Align.center);

        motdText = new Label("\nNote: The game is not finished yet.\n" +
                "Controlls are WASDJK, Shift, Esc, right mouse.\nPhysics, enemies and firing is not implemented.", labelStyle);

        //motdText.setPosition(30, 100);

        table.add(loadingText).center();
        table.row();
        table.add(continueButton).center();
        table.row();
        table.add(motdText);


        stage.addActor(table);

        if (Debug.DEBUG){
            stage.setDebugAll(true);
        }

    }

    @Override
    public void render(float delta) {
        if (nextScreen){
            game.setScreen(game.gameScreen);
            return;
        }

        spriteBatch.begin();
        spriteBatch.draw(background, 0f, 0f, w, h);
        spriteBatch.end();

        loaded = DarknessGame.ASSET_MANAGER.update(); // Load assets

        continueButton.setVisible(loaded); // Only show button on loading done

        if (loaded){
            loadingText.setText("Loading complete!");
        } else {
            loadingText.setText("Progress: " + Math.round(DarknessGame.ASSET_MANAGER.getProgress() * 100));
        }

        stage.act(delta); // Update stage

        if (continueButton.isChecked() && !continueClicked){ // Button is clicked the first time
            continueClicked = true;
            SequenceAction sequenceAction = new SequenceAction();

            AlphaAction fadeAction = new AlphaAction();
            fadeAction.setAlpha(0);
            fadeAction.setDuration(1);
            fadeAction.setInterpolation(Interpolation.fade);

            RunnableAction runnableAction = new RunnableAction();
            runnableAction.setRunnable(new Runnable() {
                @Override
                public void run() {
                    nextScreen = true;
                }
            });

            sequenceAction.addAction(fadeAction);
            sequenceAction.addAction(runnableAction);

            stage.addAction(sequenceAction);
        }

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        DarknessGame.INPUT_MULTIPLEXER.removeProcessor(stage);
        stage.dispose();
        background.dispose();
        spriteBatch.dispose();
    }

    @Override
    public void dispose() {
        game = null;
    }
}
