package com.example.tetris

import android.content.Context
import android.content.SharedPreferences

/**
 * ゲーム設定管理クラス
 */
object Config {
    private const val PREF_NAME = "tetris_config"
    private const val KEY_BGM = "bgm_enabled"
    private const val KEY_SE = "se_enabled"
    private const val KEY_BOARD_WIDTH = "board_width"
    private const val KEY_BOARD_HEIGHT = "board_height"
    private const val KEY_VOLUME = "volume"

    var bgmEnabled: Boolean = true
    var seEnabled: Boolean = true
    var boardWidth: Int = 10
    var boardHeight: Int = 20
    var volume: Float = 1.0f

    fun load(context: Context) {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        bgmEnabled = pref.getBoolean(KEY_BGM, true)
        seEnabled = pref.getBoolean(KEY_SE, true)
        boardWidth = pref.getInt(KEY_BOARD_WIDTH, 10)
        boardHeight = pref.getInt(KEY_BOARD_HEIGHT, 20)
        volume = pref.getFloat(KEY_VOLUME, 1.0f)
    }

    fun save(context: Context) {
        val pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        pref.edit().apply {
            putBoolean(KEY_BGM, bgmEnabled)
            putBoolean(KEY_SE, seEnabled)
            putInt(KEY_BOARD_WIDTH, boardWidth)
            putInt(KEY_BOARD_HEIGHT, boardHeight)
            putFloat(KEY_VOLUME, volume)
            apply()
        }
    }
}
