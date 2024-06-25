package jp.groupsession.v2.tcd.tcd150;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] タイムカード CSVインポート画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd150Biz {

    /**
     * <br>[機  能] 初期表示画面情報を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param tempDir テンポラリディレクトリID
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 取込みファイル操作時例外
     */
    protected void _setInitData(Tcd150ParamModel paramMdl,
            RequestModel reqMdl, Connection con, String tempDir)
    throws SQLException, IOToolsException {

        BaseUserModel usModel = reqMdl.getSmodel();
        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        int usKbn = tcBiz.getUserKbn(con, usModel);
        paramMdl.setUsrKbn(String.valueOf(usKbn));
        int sessionUserSid = usModel.getUsrsid();

        //グループコンボ
        List<LabelValueBean> groupLabel = null;
        //ユーザコンボ
        List<UsrLabelValueBean> userLabel = null;
        groupLabel = tcBiz.getGroupLabelList(con, sessionUserSid, usKbn, reqMdl);

        //グループコンボの設定
        paramMdl.setTcd150GpLabelList(groupLabel);
        int dspGpSid = NullDefault.getInt(
                paramMdl.getTcd150SltGroup(), tcBiz.getInitGpSid(sessionUserSid, groupLabel, con));

        //ユーザ種別 = グループ管理者の場合、選択されたグループのグループ管理者かを確認
        if (usKbn == GSConstTimecard.USER_KBN_GRPADM) {
            boolean grpAdminUser = false;
            for (LabelValueBean grpLabel : groupLabel) {
                if (dspGpSid == Integer.parseInt(grpLabel.getValue())) {
                    grpAdminUser = true;
                    break;
                }
            }
            if (!grpAdminUser) {
                dspGpSid = Integer.parseInt(groupLabel.get(0).getValue());
            }
        }

        //グループコンボの初期値の設定
        paramMdl.setTcd150SltGroup(String.valueOf(dspGpSid));

        int dspUsrSid = NullDefault.getInt(
                paramMdl.getTcd150SltUser(), sessionUserSid);
        paramMdl.setTcd150SltUser(String.valueOf(dspUsrSid));

        //ユーザコンボの設定
        GsMessage gsMsg = new GsMessage(reqMdl);
        UserBiz usrBiz = new UserBiz();
        userLabel = usrBiz.getNormalAllUserLabelList(con, gsMsg, dspGpSid, null, true);
        paramMdl.setTcd150UsrLabelList(userLabel);

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        //画面に表示するファイルのリストを作成
        ArrayList<LabelValueBean> fileLblList = new ArrayList<LabelValueBean>();

        if (fileList != null) {
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
            }
        }
        paramMdl.setTcd150FileLabelList(fileLblList);
    }


    /**
     * <br>[機  能] 権限チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return 権限チェック結果 true:OK、false:NG
     * @throws SQLException
     */
    protected boolean _dispKengen(RequestModel reqMdl, Connection con)
            throws SQLException {

        BaseUserModel usModel = reqMdl.getSmodel();
        TimecardBiz tcBiz = new TimecardBiz(reqMdl);
        int userKbn = tcBiz.getUserKbn(con, usModel);
        boolean ret = false;
        TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(usModel.getUsrsid(), con);
        //ユーザ種別 = 一般ユーザの場合
        if (userKbn == GSConstTimecard.USER_KBN_NORMAL) {
            //基本設定を確認
            if (admConf.getTacLockFlg() == GSConstTimecard.UNLOCK_FLG
                    && admConf.getTacLockStrike() == GSConstTimecard.UNLOCK_FLG
                    && admConf.getTacLockLate() == GSConstTimecard.UNLOCK_FLG
                    && admConf.getTacLockHoliday() == GSConstTimecard.UNLOCK_FLG
                    && admConf.getTacLockTimezone() == GSConstTimecard.UNLOCK_FLG) {
                ret = true;
            }
        } else {
            ret = true;
        }
        return ret;
    }
}
