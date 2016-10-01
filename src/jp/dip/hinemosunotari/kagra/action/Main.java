package jp.dip.hinemosunotari.kagra.action;

import java.io.File;

import jp.dip.hinemosunotari.kagra.watcher.DirectoryCrawler;
import jp.dip.hinemosunotari.kagra.watcher.FileWatcher;

public class Main {

	public static void main(String[] args) throws InterruptedException {


		String watchPath = "C:\\pleiades\\sandbox";;	// 監視対象ディレクトリ
		String endKeyFile = "end.txt";					// 作成されると処理を終了するファイル名(処理終了の動機ファイル)
		File file = new File(watchPath);

		System.out.println("Main #main処理を開始します。");

		FileWatcher fileWatcher = new FileWatcher();
		DirectoryCrawler directoryCrawler = new DirectoryCrawler();

		// 監視対象のファイルリストを表示する
		// TODO FileWatcherの監視リストに加える実装
		directoryCrawler.readFileList(file).show();

		// 監視対象のファイルを監視する
		fileWatcher.setWatchPath(watchPath);
		fileWatcher.setWatchTextName(endKeyFile);
		Thread thread = new Thread(fileWatcher);

		thread.start();

		thread.join();

		System.out.println("Main #main処理を終了します。");
	}

}
