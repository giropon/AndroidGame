package com.example.tetris

/**
 * 入力アクションの種類
 */
enum class InputAction {
    LEFT, RIGHT, ROTATE_LEFT, ROTATE_RIGHT, SOFT_DROP, HARD_DROP
}

/**
 * 入力処理クラス
 * ユーザーの入力をGameManagerに伝える
 */
class InputHandler(private val gameManager: GameManager) {
    /**
     * 入力アクションを処理する
     */
    fun onInput(action: InputAction) {
        when(action) {
            InputAction.LEFT -> gameManager.moveLeft()
            InputAction.RIGHT -> gameManager.moveRight()
            InputAction.ROTATE_LEFT -> gameManager.rotateLeft()
            InputAction.ROTATE_RIGHT -> gameManager.rotateRight()
            InputAction.SOFT_DROP -> gameManager.softDrop()
            InputAction.HARD_DROP -> gameManager.hardDrop()
        }
    }
}
