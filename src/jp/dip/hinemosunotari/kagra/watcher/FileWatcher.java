package jp.dip.hinemosunotari.kagra.watcher;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Date;

public class FileWatcher implements Runnable {


	private String watchPath;
	private String watchTextName;

	public String getWatchPath() {
		return watchPath;
	}

	public void setWatchPath(String watchPath) {
		this.watchPath = watchPath;
	}

	public String getWatchTextName() {
		return watchTextName;
	}

	public void setWatchTextName(String watchTextName) {
		this.watchTextName = watchTextName;
	}

	@Override
	public void run() {
		Date date = new Date();
		Path dir = Paths.get(watchPath);

		WatchService watcher;
		try {
			watcher = FileSystems.getDefault().newWatchService();
			dir.register(watcher, new Kind[]{
					StandardWatchEventKinds.ENTRY_CREATE,	// 作成
					StandardWatchEventKinds.ENTRY_MODIFY,	// 変更
					StandardWatchEventKinds.ENTRY_DELETE,	// 削除
					StandardWatchEventKinds.OVERFLOW		// 特定不能時
					}
			);

			while (true) {
				WatchKey watchKey = watcher.take();

				Path name = null;
				for (WatchEvent<?> event : watchKey.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();

					name = (Path) event.context();
					Path child = dir.resolve(name);

					if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
						System.out.println(name.toString() + "が作成されました。 (" + date.toString() + " - " + child + ")");
						if (name != null && name.toString().equals(watchTextName)) {
							System.out.println("終了キーとなるファイルが作成されたので、クローズ処理を行います・・・");
							return;
						}
					} else if(kind == StandardWatchEventKinds.ENTRY_MODIFY){
						System.out.println(name.toString() + "が変更されました。 (" + date.toString() + " - " + child + ")");
					} else if(kind == StandardWatchEventKinds.ENTRY_DELETE){
						System.out.println(name.toString() + "が削除されました。 (" + date.toString() + " - " + child + ")");
					} else if(kind == StandardWatchEventKinds.OVERFLOW){
						continue;
					}
					watchKey.reset();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
