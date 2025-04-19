# テトリスKotlinプロジェクト 開発経緯まとめ

## 1. 開発開始・設計方針
- テトリスの基本仕様（7種テトリミノ、ライン消去、スコア管理、ゲームオーバー等）を整理
- クラス設計（Board, Tetromino, TetrominoFactory, GameManager, Renderer, InputHandler, SoundManager, Config）を決定
- 最初はPython設計、その後Kotlin/Androidアプリ化へ方針転換

## 2. コアクラス実装
- Board.kt: 盤面管理（配置・ライン消去・ゲームオーバー判定）
- Tetromino.kt: テトリミノ形状・回転・種類管理
- TetrominoFactory.kt: テトリミノ生成（ランダム・7バッグ対応）
- GameManager.kt: ゲーム進行・スコア・レベル・ライン数・ハイスコア管理
- InputHandler.kt: 入力アクションenum化、GameManager連携
- Renderer.kt: Android Canvas用の盤面・テトリミノ・スコア描画
- SoundManager.kt: 効果音・BGM管理（現状サウンド再生は一時的にオフ）
- Config.kt: SharedPreferencesによる設定管理（BGM/SE有効・盤面サイズ・音量）

## 3. サウンド/BGM
- 効果音・BGMファイルは `res/raw/` 配下に配置する設計
- サウンドファイル未設置時もエラーにならないよう再生機能は一時的にオフ

## 4. Android Studio対応・ビルド手順
- 既存KotlinファイルをAndroid Studio新規プロジェクト（Empty Activity）に移植
- `app/src/main/java/com/example/tetris/` 配下に全ファイル配置
- `MainActivity.kt` と `TetrisView.kt` でゲームループ・描画・入力を統合

## 5. 今後の拡張・注意点
- Activity/ViewのUI拡張、タッチ操作、スコア保存、設定画面、リーダーボード等
- サウンド/BGMファイルの設置・有効化
- READMEやこのドキュメントを新プロジェクトにも必ずコピー

---

このドキュメントは、WindSurf/Cascadeでのやり取りや設計意図を新しいAndroid Studioプロジェクトに引き継ぐための履歴まとめです。

- 設計・実装方針の再確認や、AIサポートの文脈共有にご活用ください。
