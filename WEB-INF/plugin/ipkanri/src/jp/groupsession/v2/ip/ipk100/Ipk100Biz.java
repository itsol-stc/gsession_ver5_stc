package jp.groupsession.v2.ip.ipk100;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.ip.IpkBiz;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.dao.IpkAddDao;
import jp.groupsession.v2.ip.dao.IpkSpecMstDao;
import jp.groupsession.v2.ip.model.IpkAddModel;
import jp.groupsession.v2.ip.model.IpkSpecMstModel;

/**
 * <br>[機  能] IP管理 スペックマスタメンテナンス 登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk100Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk100Biz.class);

    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Ipk100ParamModel paramMdl, Connection con)
    throws SQLException {

        IpkSpecMstDao dao = new IpkSpecMstDao(con);
        int ismSid = NullDefault.getInt(paramMdl.getIsmSid(), 0);
        ArrayList<IpkSpecMstModel> specModelList = new ArrayList<IpkSpecMstModel>();
        IpkBiz ipkBiz = new IpkBiz();

        //変更モードの場合
        if (paramMdl.getEditMode().equals(IpkConst.IPK_SPECM_HENKOU)) {
            //変更するスペック情報を取得する。
            int specLevel = 0;
            ArrayList<IpkSpecMstModel> specInfList =
                dao.selectSpecInf(ismSid);
            for (IpkSpecMstModel model : specInfList) {
                paramMdl.setIpk100name(NullDefault.getString(model.getIpk100name(), ""));
                paramMdl.setIpk100biko(NullDefault.getString(model.getIpk100biko(), ""));
                specLevel = model.getIpk100level() + 1;
            }

            //表示順ラジオボタンを設定する
            IpkSpecMstModel specInfModel = dao.selectLevel(
                    specLevel, NullDefault.getInt(paramMdl.getSpecKbn(), 0));
            paramMdl.setIpk100specLevel(String.valueOf(specInfModel.getIsmSid()));
            paramMdl.setIpk100svSpecLevel(String.valueOf(specInfModel.getIsmSid()));

            //表示順一覧を設定する。
            specModelList = dao.select(NullDefault.getInt(paramMdl.getSpecKbn(), 0), ismSid);
            paramMdl.setIpk100specMstModelList(ipkBiz.transToHtmlModelList(specModelList));

            //スクロールの初期位置
            int num = 1;
            for (IpkSpecMstModel mdl : specModelList) {
                if (paramMdl.getIpk100specLevel().equals(String.valueOf(mdl.getIsmSid()))) {
                    paramMdl.setIpk100scroll(String.valueOf(num * 66 - 99));
                }
                num++;
            }
        } else {
            //表示順一覧を設定する。
            specModelList = dao.select(NullDefault.getInt(paramMdl.getSpecKbn(), 0));
            paramMdl.setIpk100specMstModelList(ipkBiz.transToHtmlModelList(specModelList));
        }
        if (specModelList.size() > 0) {
            paramMdl.setExistFlg(IpkConst.IPK_SPEC_EXIST);
        } else {
            paramMdl.setExistFlg(IpkConst.IPK_SPEC_NOT_EXIST);
        }
    }
    /**
     * <br>[機  能] 再表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitDataAg(Ipk100ParamModel paramMdl, Connection con)
    throws SQLException {

        IpkSpecMstDao dao = new IpkSpecMstDao(con);
        int ismSid = NullDefault.getInt(paramMdl.getIsmSid(), 0);
        ArrayList<IpkSpecMstModel> specModel = null;
        IpkBiz ipkBiz = new IpkBiz();
        //変更モードの場合
        if (paramMdl.getEditMode().equals(IpkConst.IPK_SPECM_HENKOU)) {
            //表示順一覧を設定する。
            specModel = dao.select(NullDefault.getInt(paramMdl.getSpecKbn(), 0), ismSid);
            paramMdl.setIpk100specMstModelList(ipkBiz.transToHtmlModelList(specModel));
        } else {
            //表示順一覧を設定する。
            specModel = dao.select(NullDefault.getInt(paramMdl.getSpecKbn(), 0));
            paramMdl.setIpk100specMstModelList(ipkBiz.transToHtmlModelList(specModel));
        }
    }

    /**
     * <br>[機  能] 削除処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param ismSid スペックSID
     * @param con コネクション
     * @param sessionUsrSid ユーザSID
     * @param specKbn スペック区分
     * @throws SQLException SQL実行例外
     */
    public void deleteData(int ismSid,
            Connection con, int sessionUsrSid, int specKbn)
    throws SQLException {

        //コミットフラグ
        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {
            IpkSpecMstDao dao = new IpkSpecMstDao(con);
            //削除処理を行う。
            dao.delete(ismSid);

            //削除したスペックマスタの項目をIPK_ADDから消去する。
            IpkAddDao ipkAddDao = new IpkAddDao(con);
            IpkAddModel model = new IpkAddModel();
            //現在日時を取得する。
            UDate now = new UDate();
            model.setIadEdate(now);
            model.setIadEuid(sessionUsrSid);
            ipkAddDao.updateSpec(ismSid, specKbn, model);

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
}