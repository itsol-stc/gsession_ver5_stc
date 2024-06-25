package jp.groupsession.v2.ip.ipk100kn;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.ip.IpkBiz;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.dao.IpkSpecMstDao;
import jp.groupsession.v2.ip.model.IpkSpecMstModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] IP管理 スペックマスタメンテナンス 登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk100knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk100knBiz.class);

    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Ipk100knParamModel paramMdl, Connection con)
            throws SQLException {

        try {
            //名前をhtml表示用に変換
            paramMdl.setIpk100knName(NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(
                            paramMdl.getIpk100name()), ""));
            //備考をhtml表示用に変換
            paramMdl.setIpk100knBiko(NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(
                            paramMdl.getIpk100biko()), ""));

            //表示順一覧を設定する。
            IpkSpecMstDao dao = new IpkSpecMstDao(con);
            int ismSid = NullDefault.getInt(paramMdl.getIsmSid(), 0);
            ArrayList<IpkSpecMstModel> specModelList =
                dao.select(NullDefault.getInt(paramMdl.getSpecKbn(), 0), ismSid);
            IpkBiz ipkBiz = new IpkBiz();
            paramMdl.setIpk100specMstModelList(ipkBiz.transToHtmlModelList(specModelList));

            if (paramMdl.getEditMode().equals(IpkConst.IPK_SPECM_HENKOU)) {
                //スクロールの初期位置
                int num = 1;
                for (IpkSpecMstModel mdl : specModelList) {
                    if (paramMdl.getIpk100specLevel().equals(String.valueOf(mdl.getIsmSid()))) {
                        paramMdl.setIpk100knScroll(String.valueOf(num * 31 - 93));
                    }
                    num++;
                }
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * <br>[機  能] スペックマスタへの登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行例外
     */
    public void setSpecData(Ipk100knParamModel paramMdl,
            Connection con, int sessionUsrSid, MlCountMtController cntCon)
    throws SQLException {

        //コミットフラグ
        boolean commitFlg = false;
        con.setAutoCommit(false);

        try {
            IpkSpecMstModel model = new IpkSpecMstModel();
            IpkSpecMstDao dao = new IpkSpecMstDao(con);
            ArrayList <IpkSpecMstModel> speclist
                = dao.select(NullDefault.getInt(paramMdl.getSpecKbn(), 0));

            int hyoujiNum = IpkConst.IPK_MAX_LEVEL;
            int specLevel = NullDefault.getInt(paramMdl.getIpk100specLevel(), 0);

            //表示順が一番上の場合
            if (specLevel == 0) {
                //登録処理を行う。
                __insertUpdateSpecData(paramMdl, con, hyoujiNum, sessionUsrSid, cntCon);
                hyoujiNum = hyoujiNum - 1;
            }
            for (IpkSpecMstModel mdl : speclist) {
                //変更時は変更するISMSIDを飛ばす。
                if (paramMdl.getEditMode().equals(IpkConst.IPK_SPECM_HENKOU)
                        && mdl.getIsmSid() == NullDefault.getInt(paramMdl.getIsmSid(), 0)) {
                    continue;
                }
                //表示順を更新する。
                model = new IpkSpecMstModel();
                model.setIpk100level(hyoujiNum);
                model.setIsmSid(mdl.getIsmSid());
                dao.updateLevel(model);
                hyoujiNum = hyoujiNum - 1;

                if (specLevel == mdl.getIsmSid()) {
                    //登録処理を行う。
                    __insertUpdateSpecData(paramMdl, con, hyoujiNum, sessionUsrSid, cntCon);
                    hyoujiNum = hyoujiNum - 1;
                }

            }
            commitFlg = true;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
                log__.debug("コミット完了");
            } else {
                con.rollback();
            }
        }
    }
    /**
     * <br>[機  能] スペックマスタへの登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param hyoujiNum 表示順
     * @param sessionUsrSid セッションユーザSID
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行例外
     */
    public void __insertUpdateSpecData(Ipk100knParamModel paramMdl,
            Connection con, int hyoujiNum,
            int sessionUsrSid, MlCountMtController cntCon)
    throws SQLException {

        try {
            IpkSpecMstModel model = new IpkSpecMstModel();
            IpkSpecMstDao dao = new IpkSpecMstDao(con);
            String editMode = paramMdl.getEditMode();

            //登録用モデルを設定する。
            model.setIpk100name(paramMdl.getIpk100name());
            model.setIpk100level(hyoujiNum);
            model.setIpk100biko(paramMdl.getIpk100biko());
            model.setUsrSid(sessionUsrSid);
            model.setSpecKbn(NullDefault.getInt(paramMdl.getSpecKbn(), -1));
            //現在日時を取得する。
            UDate now = new UDate();
            model.setNow(now);

            if (editMode.equals(IpkConst.IPK_SPECM_TUIKA)) {
                //新規登録時
                int ismSid = (int) cntCon.getSaibanNumber(
                IpkConst.PLUGIN_ID_IPKANRI, IpkConst.SPEC, sessionUsrSid);
                model.setIsmSid(ismSid);
                dao.insert(model);
            } else if (editMode.equals(IpkConst.IPK_SPECM_HENKOU)) {
                //更新時
                model.setIsmSid(NullDefault.getInt(paramMdl.getIsmSid(), 0));
                dao.update(model);
            }

        } catch (SQLException e) {
            throw e;
        }
    }
}