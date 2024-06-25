package jp.groupsession.v2.rsv.rsv270;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 施設予約 グループ・施設一括出力画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv270Biz extends AbstractReserveBiz {

    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     */
    public Rsv270Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 処理権限判定
     * <br>[解  説]
     * <br>[備  考]
     * @return true:処理実行可 false:処理実行不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isPossibleToProcess()
        throws SQLException {

        //管理者である
        return _isAdmin(reqMdl_, con_);
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv270ParamModel
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void setInitData(Rsv270ParamModel paramMdl)
        throws SQLException, IOToolsException {

        //施設区分コンボ生成
        ArrayList<LabelValueBean> grpKbnList = _getGroupKbnComboList(con_);
        paramMdl.setRsv270SisetuLabelList(grpKbnList);
        if (paramMdl.getRsv270SelectedSisetuKbn() < 0) {
            LabelValueBean lbl = grpKbnList.get(0);
            paramMdl.setRsv270SelectedSisetuKbn(Integer.parseInt(lbl.getValue()));
        }
    }
}