package jp.groupsession.v2.fil.fil300;

public class FilErrlAdddataException extends Exception {
    /** 削除するファイルが1件もない場合 例外メッセージ*/
    public static final String INFO_NOFILE_ALL = "[仮登録ファイル]は削除されました。";

    /** 選択フォルダに削除するファイルがない場合 例外メッセージ*/
    public static final String INFO_NOFILE_SELDIR = "キャビネットまたはフォルダを選択してください";



    public FilErrlAdddataException(String reason) {
        super(reason);
    }

}
