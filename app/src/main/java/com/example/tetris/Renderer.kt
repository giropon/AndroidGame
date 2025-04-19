package com.example.tetris

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

/**
 * 描画処理クラス（Android Canvas用雛形）
 */
class Renderer {
    private val offsetY: Int = 20
    private val paint = Paint()

    // テトリミノごとの色（0は空白）
    private val colors = mapOf(
        0 to Color.LTGRAY,
        1 to Color.CYAN, // I
        2 to Color.YELLOW, // O
        3 to Color.GREEN, // S
        4 to Color.RED, // Z
        5 to Color.BLUE, // J
        6 to Color.rgb(255, 140, 0), // L
        7 to Color.MAGENTA // T
    )

    private val paint = Paint()

    // テトリミノごとの色（0は空白）
    private val colors = mapOf(
        0 to Color.LTGRAY,
        1 to Color.CYAN, // I
        2 to Color.YELLOW, // O
        3 to Color.GREEN, // S
        4 to Color.RED, // Z
        5 to Color.BLUE, // J
        6 to Color.rgb(255, 140, 0), // L
        7 to Color.MAGENTA // T
    )

    /**
     * 盤面を描画
     */
    fun renderBoard(canvas: Canvas, board: Board) {
        // 横幅いっぱいに拡大
        val margin = 10
        val cellSize = (canvas.width - margin * 2) / board.width
        val offsetX = margin
        for (y in 0 until board.height) {
            for (x in 0 until board.width) {
                val value = board.getState()[y][x]
                paint.color = colors[value] ?: Color.DKGRAY
                canvas.drawRect(
                    (offsetX + x * cellSize).toFloat(),
                    (offsetY + y * cellSize).toFloat(),
                    (offsetX + (x + 1) * cellSize).toFloat(),
                    (offsetY + (y + 1) * cellSize).toFloat(),
                    paint
                )
            }
        }
    }

    /**
     * 操作中テトリミノを描画
     */
    fun renderTetromino(canvas: Canvas, tetromino: Tetromino) {
        // 横幅いっぱいに拡大
        val margin = 10
        val boardWidth = 10
        val cellSize = (canvas.width - margin * 2) / boardWidth
        val offsetX = margin
        for (dy in tetromino.shape.indices) {
            for (dx in tetromino.shape[dy].indices) {
                val cell = tetromino.shape[dy][dx]
                if (cell != 0) {
                    paint.color = colors[cell] ?: Color.DKGRAY
                    val x = tetromino.x + dx
                    val y = tetromino.y + dy
                    canvas.drawRect(
                        (offsetX + x * cellSize).toFloat(),
                        (offsetY + y * cellSize).toFloat(),
                        (offsetX + (x + 1) * cellSize).toFloat(),
                        (offsetY + (y + 1) * cellSize).toFloat(),
                        paint
                    )
                }
            }
        }
    }

    /**
     * 次のテトリミノを描画
     */
    fun renderNextTetromino(canvas: Canvas, tetromino: Tetromino) {
        val startX = offsetX + (cellSize * (10 + 2)) // 盤面右側に表示
        val startY = offsetY
        for (dy in tetromino.shape.indices) {
            for (dx in tetromino.shape[dy].indices) {
                val cell = tetromino.shape[dy][dx]
                if (cell != 0) {
                    paint.color = colors[cell] ?: Color.DKGRAY
                    canvas.drawRect(
                        (startX + dx * cellSize).toFloat(),
                        (startY + dy * cellSize).toFloat(),
                        (startX + (dx + 1) * cellSize).toFloat(),
                        (startY + (dy + 1) * cellSize).toFloat(),
                        paint
                    )
                }
            }
        }
    }

    /**
     * スコア・レベルを描画
     */
    fun renderScore(canvas: Canvas, score: Int, level: Int) {
        paint.color = Color.BLACK
        paint.textSize = 48f
        canvas.drawText("SCORE: $score", offsetX.toFloat(), (offsetY + cellSize * 22).toFloat(), paint)
        canvas.drawText("LEVEL: $level", offsetX.toFloat(), (offsetY + cellSize * 23).toFloat(), paint)
    }
}
