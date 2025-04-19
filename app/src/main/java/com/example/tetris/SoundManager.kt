package com.example.tetris

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool

/**
 * 効果音・BGM管理クラス（Android用）
 */
object SoundManager {
    private var bgmPlayer: MediaPlayer? = null
    private var soundPool: SoundPool? = null
    private var seLineClear: Int = 0
    private var seGameOver: Int = 0
    private var seFix: Int = 0

    /**
     * サウンドの初期化（ActivityやApplicationのonCreate等で呼ぶ）
     */
    fun init(context: Context) {
        soundPool = SoundPool.Builder().setMaxStreams(5).build()
        // seLineClear = soundPool!!.load(context, R.raw.line_clear, 1)
        // seGameOver = soundPool!!.load(context, R.raw.game_over, 1)
        // seFix = soundPool!!.load(context, R.raw.fix, 1)
        // bgmPlayer = MediaPlayer.create(context, R.raw.bgm)
        // bgmPlayer?.isLooping = true
    }

    /** 効果音：ライン消去 */
    fun playLineClear() { soundPool?.play(seLineClear, 1f, 1f, 1, 0, 1f) }
    /** 効果音：ゲームオーバー */
    fun playGameOver() { soundPool?.play(seGameOver, 1f, 1f, 1, 0, 1f) }
    /** 効果音：テトリミノ固定 */
    fun playFix() { soundPool?.play(seFix, 1f, 1f, 1, 0, 1f) }
    /** BGM再生 */
    fun startBgm() { bgmPlayer?.start() }
    /** BGM停止 */
    fun stopBgm() { bgmPlayer?.pause() }
    /** サウンド解放（Activity終了時などに呼ぶ） */
    fun release() {
        soundPool?.release()
        soundPool = null
        bgmPlayer?.release()
        bgmPlayer = null
    }
}
