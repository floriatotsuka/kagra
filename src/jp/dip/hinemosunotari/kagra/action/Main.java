package jp.dip.hinemosunotari.kagra.action;

import jp.dip.hinemosunotari.kagra.watcher.FileWatcher;

public class Main {

	public static void main(String[] args) throws InterruptedException {


		String watchPath = "C:\\pleiades\\sandbox";;	// 監視対象ディレクトリ
		String endKeyFile = "end.txt";					// 作成されると処理を終了するファイル名(処理終了の動機ファイル)

		System.out.println("Main #main処理を開始します。");

		FileWatcher watcher = new FileWatcher();

		watcher.setWatchPath(watchPath);
		watcher.setWatchTextName(endKeyFile);

		Thread thread = new Thread(watcher);

		thread.start();

		thread.join();

		System.out.println("Main #main処理を終了します。");

	}

}
