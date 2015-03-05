package com.polarbirds.darkness.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.polarbirds.darkness.DarknessGame;
import com.polarbirds.darkness.Debug;
import com.polarbirds.darkness.graphics.RenderableObject;

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
    TextField loadingText;

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

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.fontColor = Color.WHITE;
        continueButton = new TextButton("Continue", style);
        continueButton.setVisible(loaded);
        continueButton.setPosition(0, Gdx.graphics.getHeight()/2, Align.center);


        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.WHITE;

        loadingText = new TextField("Loading: 0", textFieldStyle);
        loadingText.setAlignment(Align.center);

        table.add(loadingText).center();
        table.row();
        table.add(continueButton).center();


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
        stage.dispose();
    }

    @Override
    public void dispose() {
        game = null;
    }
}
