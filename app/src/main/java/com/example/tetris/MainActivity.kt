package com.example.tetris

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var tetrisView: TetrisView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Config.load(this) // 設定の読み込み
        SoundManager.init(this) // サウンド初期化（必要なら）
        tetrisView = TetrisView(this)
        setContentView(tetrisView)
    }

    override fun onDestroy() {
        super.onDestroy()
        SoundManager.release()
    }
}