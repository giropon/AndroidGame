package com.example.tetris

/**
 * ゲーム全体の状態と進行を管理するクラス
 */
class GameManager(val boardWidth: Int = 10, val boardHeight: Int = 20) {
    var score: Int = 0
    var level: Int = 1
    var linesCleared: Int = 0
    private var highScore: Int = 0
    val board = Board(boardWidth, boardHeight)
    var currentTetromino: Tetromino = TetrominoFactory.createRandomTetromino(boardWidth)
    var nextTetromino: Tetromino = TetrominoFactory.createRandomTetromino(boardWidth)
    var gameOver: Boolean = false

    /**
     * ゲーム開始時の初期化
     */
    fun startGame() {
        for (y in 0 until board.height) {
    for (x in 0 until board.width) {
        board.getState()[y][x] = 0
    }
}
        resetScoreAndLevel()
        gameOver = false
        currentTetromino = TetrominoFactory.createRandomTetromino(boardWidth)
        nextTetromino = TetrominoFactory.createRandomTetromino(boardWidth)
    }

    /**
     * スコア・レベル・ライン数をリセット
     */
    fun resetScoreAndLevel() {
        score = 0
        level = 1
        linesCleared = 0
    }

    /**
     * 1フレーム分の進行処理（テトリミノ落下など）
     */
    fun update() {
        if (gameOver) return
        if (canMove(currentTetromino, 0, 1)) {
            currentTetromino.y += 1
        } else {
            // 固定
            board.placeTetromino(currentTetromino, currentTetromino.x, currentTetromino.y)
            // テトリミノ固定効果音（現在オフ）
            if (Config.seEnabled && false) SoundManager.playFix()
            val cleared = board.clearLines()
            if (cleared > 0) {
                score += calcScore(cleared)
                linesCleared += cleared
                level = 1 + linesCleared / 10
                if (score > highScore) highScore = score
                // ライン消去効果音（現在オフ）
                if (Config.seEnabled && false) SoundManager.playLineClear()
            }
            spawnNextTetromino()
            if (!board.canPlace(currentTetromino, currentTetromino.x, currentTetromino.y)) {
                gameOver = true
                // ゲームオーバー効果音（現在オフ）
                if (Config.seEnabled && false) SoundManager.playGameOver()
            }
        }
    }

    /**
     * テトリミノを左に移動
     */
    fun moveLeft() {
        if (canMove(currentTetromino, -1, 0)) currentTetromino.x -= 1
    }

    /**
     * テトリミノを右に移動
     */
    fun moveRight() {
        if (canMove(currentTetromino, 1, 0)) currentTetromino.x += 1
    }

    /**
     * テトリミノを下にソフトドロップ
     */
    fun softDrop() {
        if (canMove(currentTetromino, 0, 1)) currentTetromino.y += 1
    }

    /**
     * テトリミノを即座にハードドロップ
     */
    fun hardDrop() {
        while (canMove(currentTetromino, 0, 1)) {
            currentTetromino.y += 1
        }
        update()
    }

    /**
     * テトリミノを右回転
     */
    fun rotateRight() {
        currentTetromino.rotateRight()
        if (!board.canPlace(currentTetromino, currentTetromino.x, currentTetromino.y)) {
            // 回転できなければ元に戻す
            currentTetromino.rotateLeft()
        }
    }

    /**
     * テトリミノを左回転
     */
    fun rotateLeft() {
        currentTetromino.rotateLeft()
        if (!board.canPlace(currentTetromino, currentTetromino.x, currentTetromino.y)) {
            // 回転できなければ元に戻す
            currentTetromino.rotateRight()
        }
    }

    /**
     * テトリミノが指定方向に動けるか判定
     */
    private fun canMove(tetromino: Tetromino, dx: Int, dy: Int): Boolean {
        val newX = tetromino.x + dx
        val newY = tetromino.y + dy
        return board.canPlace(tetromino, newX, newY)
    }

    /**
     * 次のテトリミノを出現させる
     */
    private fun spawnNextTetromino() {
        currentTetromino = nextTetromino
        nextTetromino = TetrominoFactory.createRandomTetromino(boardWidth)
        currentTetromino.x = (boardWidth - currentTetromino.shape[0].size) / 2
        currentTetromino.y = 0
    }

    /**
     * スコア計算（消したライン数に応じて加算）
     */
    private fun calcScore(lines: Int): Int {
        return when(lines) {
            1 -> 100 * level
            2 -> 300 * level
            3 -> 500 * level
            4 -> 800 * level
            else -> 0
        }
    }

    /**
     * レベルに応じた落下速度（ミリ秒）を返す（例: レベルが上がるほど速くなる）
     */
    fun getFallSpeedMillis(): Long {
        // レベル1: 1000ms, レベル2: 900ms, ..., 最小100ms
        return (1000L - (level - 1) * 100L).coerceAtLeast(100L)
    }

    /**
     * 現在のテトリミノが落下できる最下段の位置にあるゴーストテトリミノを返す
     */
    fun getGhostTetromino(): Tetromino {
        val ghost = Tetromino(currentTetromino.type, currentTetromino.x, currentTetromino.y)
        ghost.shape = currentTetromino.shape.map { it.copyOf() }.toTypedArray()
        while (board.canPlace(ghost, ghost.x, ghost.y + 1)) {
            ghost.y += 1
        }
        return ghost
    }
}

