package com.example.tetris

/**
 * テトリミノの種類
 */
enum class TetrominoType {
    I, O, S, Z, J, L, T
}

/**
 * テトリミノクラス
 */
class Tetromino(
    val type: TetrominoType,
    var x: Int = 0,
    var y: Int = 0
) {
    var shape: Array<Array<Int>> = getInitialShape(type)
        private set

    /**
     * 右回転
     */
    fun rotateRight() {
        shape = Array(shape[0].size) { x -> Array(shape.size) { y -> shape[shape.size - y - 1][x] } }
    }

    /**
     * 左回転
     */
    fun rotateLeft() {
        shape = Array(shape[0].size) { x -> Array(shape.size) { y -> shape[y][shape[0].size - x - 1] } }
    }

    /**
     * 初期状態に戻す
     */
    fun reset() {
        shape = getInitialShape(type)
    }

    companion object {
        /**
         * 各テトリミノの初期形状を返す
         */
        fun getInitialShape(type: TetrominoType): Array<Array<Int>> = when(type) {
            TetrominoType.I -> arrayOf(
                arrayOf(0, 0, 0, 0),
                arrayOf(1, 1, 1, 1),
                arrayOf(0, 0, 0, 0),
                arrayOf(0, 0, 0, 0)
            )
            TetrominoType.O -> arrayOf(
                arrayOf(2, 2),
                arrayOf(2, 2)
            )
            TetrominoType.S -> arrayOf(
                arrayOf(0, 3, 3),
                arrayOf(3, 3, 0),
                arrayOf(0, 0, 0)
            )
            TetrominoType.Z -> arrayOf(
                arrayOf(4, 4, 0),
                arrayOf(0, 4, 4),
                arrayOf(0, 0, 0)
            )
            TetrominoType.J -> arrayOf(
                arrayOf(5, 0, 0),
                arrayOf(5, 5, 5),
                arrayOf(0, 0, 0)
            )
            TetrominoType.L -> arrayOf(
                arrayOf(0, 0, 6),
                arrayOf(6, 6, 6),
                arrayOf(0, 0, 0)
            )
            TetrominoType.T -> arrayOf(
                arrayOf(0, 7, 0),
                arrayOf(7, 7, 7),
                arrayOf(0, 0, 0)
            )
        }
    }
}
