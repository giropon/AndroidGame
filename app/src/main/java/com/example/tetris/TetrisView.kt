package com.example.tetris

import android.content.Context
import android.graphics.Canvas
import android.view.KeyEvent
import android.view.View
import android.os.Handler
import android.os.Looper

class TetrisView(context: Context) : View(context) {
    private val gameManager = GameManager(Config.boardWidth, Config.boardHeight)
    private val renderer = Renderer()
    private val inputHandler = InputHandler(gameManager)
    private val handler = Handler(Looper.getMainLooper())
    private var running = true

    init {
        isFocusable = true
        isFocusableInTouchMode = true
        gameManager.startGame()
        startGameLoop()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        renderer.renderBoard(canvas, gameManager.board)
        renderer.renderTetromino(canvas, gameManager.currentTetromino)
        renderer.renderNextTetromino(canvas, gameManager.nextTetromino)
        renderer.renderScore(canvas, gameManager.score, gameManager.level)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_LEFT -> inputHandler.onInput(InputAction.LEFT)
            KeyEvent.KEYCODE_DPAD_RIGHT -> inputHandler.onInput(InputAction.RIGHT)
            KeyEvent.KEYCODE_DPAD_UP -> inputHandler.onInput(InputAction.ROTATE_RIGHT)
            KeyEvent.KEYCODE_DPAD_DOWN -> inputHandler.onInput(InputAction.SOFT_DROP)
            KeyEvent.KEYCODE_SPACE, KeyEvent.KEYCODE_ENTER -> inputHandler.onInput(InputAction.HARD_DROP)
            else -> return super.onKeyDown(keyCode, event)
        }
        invalidate()
        return true
    }

    private fun startGameLoop() {
        handler.post(object : Runnable {
            override fun run() {
                if (running && !gameManager.gameOver) {
                    gameManager.update()
                    invalidate()
                    handler.postDelayed(this, gameManager.getFallSpeedMillis())
                }
            }
        })
    }
}