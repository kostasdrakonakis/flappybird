/*
 * Copyright 2018 Konstantinos Drakonakis.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kostasdrakonakis.flappybird

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import java.util.*

class FlappyBird : ApplicationAdapter() {

    private lateinit var batch: SpriteBatch
    private lateinit var background: Texture
    private lateinit var gameOver: Texture
    private lateinit var birds: Array<Texture>
    private lateinit var topTubeRectangles: Array<Rectangle?>
    private lateinit var bottomTubeRectangles: Array<Rectangle?>
    private lateinit var birdCircle: Circle
    private lateinit var font: BitmapFont
    private lateinit var topTube: Texture
    private lateinit var bottomTube: Texture
    private lateinit var random: Random

    private var flapState = 0
    private var birdY: Float = 0f
    private var velocity: Float = 0f
    private var score: Int = 0
    private var scoringTube: Int = 0
    private var gameState: Int = 0
    private val numberOfTubes: Int = 4
    private var gdxHeight: Int = 0
    private var gdxWidth: Int = 0
    private var topTubeWidth: Int = 0
    private var bottomTubeWidth: Int = 0
    private var topTubeHeight: Int = 0
    private var bottomTubeHeight: Int = 0

    private val tubeX = FloatArray(numberOfTubes)
    private val tubeOffset = FloatArray(numberOfTubes)
    private var distanceBetweenTubes: Float = 0.toFloat()

    override fun create() {
        batch = SpriteBatch()
        background = Texture("bg.png")
        gameOver = Texture("gameover.png")
        birdCircle = Circle()
        font = BitmapFont()
        font.color = Color.WHITE
        font.data.setScale(10f)

        birds = arrayOf(Texture("bird.png"), Texture("bird2.png"))

        gdxHeight = Gdx.graphics.height
        gdxWidth = Gdx.graphics.width

        topTube = Texture("toptube.png")
        bottomTube = Texture("bottomtube.png")
        random = Random()
        distanceBetweenTubes = gdxWidth * 3f / 4f
        topTubeRectangles = arrayOfNulls(numberOfTubes)
        bottomTubeRectangles = arrayOfNulls(numberOfTubes)

        topTubeWidth = topTube.width
        topTubeHeight = topTube.height
        bottomTubeWidth = bottomTube.width
        bottomTubeHeight = bottomTube.height

        startGame()
    }

    override fun render() {
        batch.begin()
        batch.draw(background, 0f, 0f, gdxWidth.toFloat(), gdxHeight.toFloat())

        if (gameState == 1) {

            if (tubeX[scoringTube] < gdxWidth / 2) {
                score++
                if (scoringTube < numberOfTubes - 1) {
                    scoringTube++
                } else {
                    scoringTube = 0
                }
            }

            if (Gdx.input.justTouched()) {
                velocity = -30f
            }

            for (i in 0 until numberOfTubes) {

                if (tubeX[i] < -topTubeWidth) {
                    tubeX[i] += numberOfTubes * distanceBetweenTubes
                    tubeOffset[i] = (random.nextFloat() - 0.5f) * (gdxHeight.toFloat() - GAP - 200f)
                } else {
                    tubeX[i] = tubeX[i] - TUBE_VELOCITY
                }

                batch.draw(topTube, tubeX[i], gdxHeight / 2f + GAP / 2 + tubeOffset[i])
                batch.draw(bottomTube,
                        tubeX[i],
                        gdxHeight / 2f - GAP / 2 - bottomTubeHeight.toFloat() + tubeOffset[i])

                topTubeRectangles[i] = Rectangle(tubeX[i],
                        gdxHeight / 2f + GAP / 2 + tubeOffset[i],
                        topTubeWidth.toFloat(),
                        topTubeHeight.toFloat())

                bottomTubeRectangles[i] = Rectangle(tubeX[i],
                        gdxHeight / 2f - GAP / 2 - bottomTubeHeight.toFloat() + tubeOffset[i],
                        bottomTubeWidth.toFloat(),
                        bottomTubeHeight.toFloat())
            }

            if (birdY > 0) {
                velocity += GRAVITY
                birdY -= velocity
            } else {
                gameState = 2
            }

        } else if (gameState == 0) {
            if (Gdx.input.justTouched()) {
                gameState = 1
            }
        } else if (gameState == 2) {
            batch.draw(gameOver,
                    gdxWidth / 2f - gameOver.width / 2f,
                    gdxHeight / 2f - gameOver.height / 2f)

            if (Gdx.input.justTouched()) {
                gameState = 1
                startGame()
                score = 0
                scoringTube = 0
                velocity = 0f
            }
        }

        flapState = if (flapState == 0) 1 else 0

        batch.draw(birds[flapState], gdxWidth / 2f - birds[flapState].width / 2f, birdY)
        font.draw(batch, score.toString(), 100f, 200f)
        birdCircle.set(gdxWidth / 2f,
                birdY + birds[flapState].height / 2f,
                birds[flapState].width / 2f)

        for (i in 0 until numberOfTubes) {
            if (Intersector.overlaps(birdCircle, topTubeRectangles[i])
                    || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) gameState = 2
        }

        batch.end()
    }

    private fun startGame() {
        birdY = gdxHeight / 2f - birds[0].height / 2f

        for (i in 0 until numberOfTubes) {
            tubeOffset[i] = (random.nextFloat() - 0.5f) * (gdxHeight.toFloat() - GAP - 200f)
            tubeX[i] = gdxWidth / 2f - topTubeWidth / 2f + gdxWidth.toFloat() + i * distanceBetweenTubes
            topTubeRectangles[i] = Rectangle()
            bottomTubeRectangles[i] = Rectangle()
        }
    }

    companion object {
        private const val GRAVITY = 2f
        private const val TUBE_VELOCITY = 4f
        private const val GAP = 800f
    }
}
