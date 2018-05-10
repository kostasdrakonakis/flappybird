package com.kostasdrakonakis.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

    private static final float GRAVITY = 2;
    private static final float TUBE_VELOCITY = 4;
    private static final float GAP = 800;

    private SpriteBatch batch;
    private Texture background;

    private Texture gameOver;

    private Texture[] birds;
    private int flapState = 0;
    private float birdY = 0;
    private float velocity = 0;
    private Circle birdCircle;
    private int score = 0;
    private int scoringTube = 0;
    private BitmapFont font;

    private int gameState = 0;

    private Texture topTube;
    private Texture bottomTube;
    private Random randomGenerator;
    private int numberOfTubes = 4;
    private float[] tubeX = new float[numberOfTubes];
    private float[] tubeOffset = new float[numberOfTubes];
    private float distanceBetweenTubes;
    private Rectangle[] topTubeRectangles;
    private Rectangle[] bottomTubeRectangles;

    private int gdxHeight;
    private int gdxWidth;
    private int topTubeWidth;
    private int bottomTubeWidth;
    private int topTubeHeight;
    private int bottomTubeHeight;


    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("bg.png");
        gameOver = new Texture("gameover.png");
        birdCircle = new Circle();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(10);

        birds = new Texture[2];
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");

        gdxHeight = Gdx.graphics.getHeight();
        gdxWidth = Gdx.graphics.getWidth();


        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        randomGenerator = new Random();
        distanceBetweenTubes = gdxWidth * 3 / 4;
        topTubeRectangles = new Rectangle[numberOfTubes];
        bottomTubeRectangles = new Rectangle[numberOfTubes];

        topTubeWidth = topTube.getWidth();
        topTubeHeight = topTube.getHeight();
        bottomTubeWidth = bottomTube.getWidth();
        bottomTubeHeight = bottomTube.getHeight();

        startGame();
    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(background, 0, 0, gdxWidth, gdxHeight);

        if (gameState == 1) {

            if (tubeX[scoringTube] < gdxWidth / 2) {
                score++;
                if (scoringTube < numberOfTubes - 1) {
                    scoringTube++;
                } else {
                    scoringTube = 0;
                }
            }

            if (Gdx.input.justTouched()) {
                velocity = -30;
            }

            for (int i = 0; i < numberOfTubes; i++) {

                if (tubeX[i] < -topTubeWidth) {
                    tubeX[i] += numberOfTubes * distanceBetweenTubes;
                    tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (gdxHeight - GAP - 200);
                } else {
                    tubeX[i] = tubeX[i] - TUBE_VELOCITY;
                }

                batch.draw(topTube, tubeX[i], gdxHeight / 2 + GAP / 2 + tubeOffset[i]);
                batch.draw(bottomTube, tubeX[i], gdxHeight / 2 - GAP / 2 - bottomTubeHeight + tubeOffset[i]);

                topTubeRectangles[i] = new Rectangle(tubeX[i], gdxHeight / 2 + GAP / 2 + tubeOffset[i], topTubeWidth, topTubeHeight);
                bottomTubeRectangles[i] = new Rectangle(tubeX[i], gdxHeight / 2 - GAP / 2 - bottomTubeHeight + tubeOffset[i], bottomTubeWidth, bottomTubeHeight);
            }

            if (birdY > 0) {
                velocity = velocity + GRAVITY;
                birdY -= velocity;
            } else {
                gameState = 2;
            }

        } else if (gameState == 0) {
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        } else if (gameState == 2) {
            batch.draw(gameOver, gdxWidth / 2 - gameOver.getWidth() / 2, gdxHeight / 2 - gameOver.getHeight() / 2);
            if (Gdx.input.justTouched()) {
                gameState = 1;
                startGame();
                score = 0;
                scoringTube = 0;
                velocity = 0;
            }
        }

        flapState = flapState == 0 ? 1 : 0;

        batch.draw(birds[flapState], gdxWidth / 2 - birds[flapState].getWidth() / 2, birdY);
        font.draw(batch, String.valueOf(score), 100, 200);
        birdCircle.set(gdxWidth / 2, birdY + birds[flapState].getHeight() / 2, birds[flapState].getWidth() / 2);

        for (int i = 0; i < numberOfTubes; i++) {
            if (Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) {
                gameState = 2;
            }
        }

        batch.end();
    }

    private void startGame() {
        birdY = gdxHeight / 2 - birds[0].getHeight() / 2;

        for (int i = 0; i < numberOfTubes; i++) {
            tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (gdxHeight - GAP - 200);
            tubeX[i] = gdxWidth / 2 - topTubeWidth / 2 + gdxWidth + i * distanceBetweenTubes;
            topTubeRectangles[i] = new Rectangle();
            bottomTubeRectangles[i] = new Rectangle();
        }
    }
}
