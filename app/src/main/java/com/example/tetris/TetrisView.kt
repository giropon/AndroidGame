package com.example.tetris

import android.content.Context
import android.graphics.Canvas
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.os.Handler
import android.os.Looper
import android.graphics.RectF

class TetrisView(context: Context) : View(context) {
    private val gameManager = GameManager(Config.boardWidth, Config.boardHeight)
    private val renderer = Renderer()
    private val inputHandler = InputHandler(gameManager)
    private val handler = Handler(Looper.getMainLooper())
    private var running = true

    // タッチ用ボタン領域
    private var leftButtonRect: RectF? = null
    private var rightButtonRect: RectF? = null
    private var rotateButtonRect: RectF? = null
    private var downButtonRect: RectF? = null
    private var hardDropButtonRect: RectF? = null

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 画面下部にボタン領域を設定
        val buttonHeight = h * 0.15f
        val buttonWidth = w / 5f
        val y = h - buttonHeight
        leftButtonRect = RectF(0f, y, buttonWidth, h.toFloat())
        rightButtonRect = RectF(buttonWidth * 4, y, w.toFloat(), h.toFloat())
        rotateButtonRect = RectF(buttonWidth * 2, y, buttonWidth * 3, h.toFloat())
        downButtonRect = RectF(buttonWidth, y, buttonWidth * 2, h.toFloat())
        hardDropButtonRect = RectF(buttonWidth * 3, y, buttonWidth * 4, h.toFloat())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x
            val y = event.y
            when {
                leftButtonRect?.contains(x, y) == true -> inputHandler.onInput(InputAction.LEFT)
                rightButtonRect?.contains(x, y) == true -> inputHandler.onInput(InputAction.RIGHT)
                rotateButtonRect?.contains(x, y) == true -> inputHandler.onInput(InputAction.ROTATE_RIGHT)
                downButtonRect?.contains(x, y) == true -> inputHandler.onInput(InputAction.SOFT_DROP)
                hardDropButtonRect?.contains(x, y) == true -> inputHandler.onInput(InputAction.HARD_DROP)
                else -> return false
            }
            invalidate()
            return true
        }
        return super.onTouchEvent(event)
    }

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
        // 簡易ボタン描画
        val paint = android.graphics.Paint()
        paint.color = 0x33000000
        paint.style = android.graphics.Paint.Style.FILL
        leftButtonRect?.let { canvas.drawRect(it, paint) }
        rightButtonRect?.let { canvas.drawRect(it, paint) }
        rotateButtonRect?.let { canvas.drawRect(it, paint) }
        downButtonRect?.let { canvas.drawRect(it, paint) }
        hardDropButtonRect?.let { canvas.drawRect(it, paint) }
        // ラベル
        paint.color = 0xFF000000.toInt()
        paint.textSize = (height * 0.045).toFloat()
        paint.textAlign = android.graphics.Paint.Align.CENTER
        leftButtonRect?.let { canvas.drawText("←", it.centerX(), it.centerY() + paint.textSize/2, paint) }
        rightButtonRect?.let { canvas.drawText("→", it.centerX(), it.centerY() + paint.textSize/2, paint) }
        rotateButtonRect?.let { canvas.drawText("⟳", it.centerX(), it.centerY() + paint.textSize/2, paint) }
        downButtonRect?.let { canvas.drawText("↓", it.centerX(), it.centerY() + paint.textSize/2, paint) }
        hardDropButtonRect?.let { canvas.drawText("⇓", it.centerX(), it.centerY() + paint.textSize/2, paint) }
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