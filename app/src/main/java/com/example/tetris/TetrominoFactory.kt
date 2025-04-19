package com.example.tetris

import kotlin.random.Random

/**
 * テトリミノ生成用ファクトリークラス
 */
object TetrominoFactory {
    /**
     * ランダムなテトリミノを生成
     * @param boardWidth 盤面の幅（初期x座標計算用）
     * @return Tetrominoインスタンス
     */
    fun createRandomTetromino(boardWidth: Int): Tetromino {
        val types = TetrominoType.values()
        val type = types[Random.nextInt(types.size)]
        val x = 3 // 盤面中央に配置（幅が10前提で中央付近）
        val y = 0
        return Tetromino(type, x, y)
    }

    /**
     * 7バッグ方式でテトリミノリストを生成（オプション）
     */
    fun createBag(): List<TetrominoType> {
        val bag = TetrominoType.values().toMutableList()
        bag.shuffle()
        return bag
    }
}
