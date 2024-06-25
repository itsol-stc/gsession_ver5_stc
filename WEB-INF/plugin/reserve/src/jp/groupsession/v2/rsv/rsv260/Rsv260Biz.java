package jp.groupsession.v2.rsv.rsv260;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 グループ・施設一括登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv260Biz extends AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv260Biz.class);
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
    public Rsv260Biz(RequestModel reqMdl, Connection con) {
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
     * @param paramMdl Rsv260ParamModel
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void setInitData(Rsv260ParamModel paramMdl, String tempDir)
        throws SQLException, IOToolsException {

        //施設区分コンボ生成
        ArrayList<LabelValueBean> grpKbnList = _getGroupKbnComboList(con_);
        paramMdl.setRsv260SisetuLabelList(grpKbnList);
        if (paramMdl.getRsv260SelectedSisetuKbn() < 0) {
            LabelValueBean lbl = grpKbnList.get(0);
            paramMdl.setRsv260SelectedSisetuKbn(Integer.parseInt(lbl.getValue()));
        }

        if (paramMdl.getRsv260GrpAdmKbn() < 0) {
            //権限設定デフォルト値
            paramMdl.setRsv260GrpAdmKbn(GSConstReserve.RSG_ADM_KBN_OK);
        }

        //サンプルファイルダウンロード区分
        paramMdl.setRsv260RskSid(paramMdl.getRsv260SelectedSisetuKbn());

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        //画面に表示するファイルのリストを作成
        ArrayList<LabelValueBean> fileLblList = new ArrayList<LabelValueBean>();

        if (fileList != null) {

            log__.debug("ファイルの数×２(オブジェクトと本体) = " + fileList.size());

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                String[] value = fileName.split(GSConstCommon.ENDSTR_OBJFILE);

                //表示用リストへ追加
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                fileLblList.add(new LabelValueBean(fMdl.getFileName(), value[0]));
                log__.debug("ファイル名 = " + fMdl.getFileName());
                log__.debug("保存ファイル名 = " + fMdl.getSaveFileName());
            }
        }
        paramMdl.setRsv260FileLabelList(fileLblList);
    }

    /**
     * <br>[機  能] ログメッセージ作成
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sidList SIDリスト
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @return メッセージ
     * @throws SQLException SQL実行時例外
     */
    public String getLogMessage(String[] sidList, Connection con,
            RequestModel reqMdl) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = "[" + gsMsg.getMessage("reserve.53") + "]";

        CmnUsrmInfDao usrmdao = new CmnUsrmInfDao(con);
        CmnGroupmDao groupmDao = new CmnGroupmDao(con);
        boolean multiFlg = false;
        for (String sid : sidList) {
            if (sid.substring(0, 1).equals("G")) {
                CmnGroupmModel mdl = groupmDao.select(Integer.parseInt(sid.substring(1)));
                if (multiFlg) {
                    msg += "\r\n";
                } else {
                    multiFlg = true;
                }
                msg += mdl.getGrpName();
            } else {
                CmnUsrmInfModel mdl = usrmdao.select(Integer.parseInt(sid));
                if (multiFlg) {
                    msg += "\r\n";
                } else {
                    multiFlg = true;
                }
                msg += mdl.getUsiSei() + " " + mdl.getUsiMei();
            }
        }
        return msg;

    }
}