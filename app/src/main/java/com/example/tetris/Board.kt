package com.example.tetris

/**
 * テトリスの盤面（グリッド）を管理するクラス
 */
class Board(val width: Int, val height: Int) {
    val grid: Array<IntArray> = Array(height) { IntArray(width) { 0 } }

    /**
     * 指定位置にテトリミノを置けるか判定
     * @param tetromino テトリミノ（shape: Array<Array<Int>>）
     * @param x 左上x座標
     * @param y 左上y座標
     * @return 置ける場合true
     */
    fun canPlace(tetromino: Tetromino, x: Int, y: Int): Boolean {
        for (dy in tetromino.shape.indices) {
            for (dx in tetromino.shape[dy].indices) {
                val cell = tetromino.shape[dy][dx]
                if (cell != 0) {
                    val nx = x + dx
                    val ny = y + dy
                    if (nx < 0 || nx >= width || ny < 0 || ny >= height) return false
                    if (grid[ny][nx] != 0) return false
                }
            }
        }
        return true
    }

    /**
     * テトリミノを盤面に固定
     */
    fun placeTetromino(tetromino: Tetromino, x: Int, y: Int) {
        for (dy in tetromino.shape.indices) {
            for (dx in tetromino.shape[dy].indices) {
                val cell = tetromino.shape[dy][dx]
                if (cell != 0) {
                    val nx = x + dx
                    val ny = y + dy
                    if (nx in 0 until width && ny in 0 until height) {
                        grid[ny][nx] = cell
                    }
                }
            }
        }
    }

    /**
     * 揃ったラインを消去し、消去数を返す
     */
    fun clearLines(): Int {
        val newGrid = mutableListOf<IntArray>()
        var linesCleared = 0
        for (row in grid) {
            if (row.all { it != 0 }) {
                linesCleared++
            } else {
                newGrid.add(row)
            }
        }
        while (newGrid.size < height) {
            newGrid.add(0, IntArray(width) { 0 })
        }
        for (i in 0 until height) {
            grid[i] = newGrid[i]
        }
        return linesCleared
    }

    /**
     * 盤面の状態を取得
     */
    fun getState(): Array<IntArray> {
        return Array(height) { grid[it].clone() }
    }

    /**
     * ゲームオーバー判定（最上段にブロックがあるか）
     */
    fun isGameOver(): Boolean {
        return grid[0].any { it != 0 }
    }
}

