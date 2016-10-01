package jp.dip.hinemosunotari.kagra.watcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryCrawler {

	private List<File> filelist = new ArrayList<File>();

	public List<File> getFilelist() {
		return filelist;
	}

	public void setFilelist(List<File> filelist) {
		this.filelist = filelist;
	}

	/**
	 * 監視フォルダ以下のファイルリストを走査する。
	 *
	 * @param targetPath		監視フォルダのパス
	 * @return readfilelist		走査したファイルリスト
	 */
	public DirectoryCrawler readFileList(File targetPath) {
		List<File> readfilelist = new ArrayList<File>();
		File[] files = targetPath.listFiles();

		readfilelist = getFilelist();
		if (files == null){
			return null;
		}

		for (File file : files) {
			if (!file.exists()){
				continue;
			}else if (file.isDirectory()){
				// 走査対象がディレクトリであった場合
				readfilelist.add(file);
				readFileList(file);
			}else if(file.isFile()){
				// 走査対象がファイルであった場合
				readfilelist.add(file);
			}else{
				// Directory及び読み込み不能であった場合
			}
		}

		setFilelist(readfilelist);
		return this;
	}

	/**
	 * 監視フォルダ以下のファイルリストを画面出力する。
	 *
	 */
	public void show() {

		// ファイル情報を表示する
		if (getFilelist().size() <= 0) {
			return;
		} else {
			for (File file : getFilelist()) {
				if (file.isFile()) {
					// ファイルだった時の処理
					System.out.println("[F] " + file.getName());
				} else if (file.isDirectory()) {
					// ディレクトリだった時の処理
					System.out.println("[D] " + file.getName());
				} else {
					System.out.println("[?] " + file.getName());
				}
			}
		}
	}

	/**
	 * 監視フォルダ以下のファイルリストをPath型のArrayListで返す。
	 *
	 * @return filelist		監視フォルダ以下のファイルリスト
	 */
	public List<File> toPath() {

		// ファイル情報を表示する
		if (null == getFilelist()) {
			return (null);
		} else {
			return getFilelist();
		}
	}

}
