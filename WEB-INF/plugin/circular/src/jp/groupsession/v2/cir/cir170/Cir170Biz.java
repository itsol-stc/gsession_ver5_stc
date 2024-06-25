package jp.groupsession.v2.cir.cir170;

import jp.groupsession.v2.cir.biz.CirCommonBiz;

/**
 * <br>[機  能] 回覧板 アカウントインポート画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir170Biz {

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param tempDir テンポラリディレクトリパス
     * @throws Exception 実行例外
     */
    public void getInitData(Cir170ParamModel paramMdl, String tempDir)
    throws Exception {

        CirCommonBiz sBiz = new CirCommonBiz();

        paramMdl.setCir170InitFlg(1);

        //取込みファイルコンボを設定
        paramMdl.setCir170FileLabelList(sBiz.getFileCombo(tempDir));
    }
}
