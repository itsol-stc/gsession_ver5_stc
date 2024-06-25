package jp.groupsession.v2.ntp.ntp094;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpPriConfDao;
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.model.NtpPriConfModel;

/**
 * <br>[機  能] 日報 日報一覧表示設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp094Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp094Biz.class);
    /** DBコネクション */
    public Connection con__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Ntp094Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp094ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Ntp094ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        //DBより設定情報を取得。なければデフォルト値とする。
        NtpCommonBiz biz = new NtpCommonBiz(con__, reqMdl__);
        NtpPriConfModel pconf = biz.getNtpPriConfModel(con, umodel.getUsrsid());

        //件数
        if (paramMdl.getNtp094DefLine() <= 0) {
            paramMdl.setNtp094DefLine(pconf.getNprDspList());
        }

        //自動リロード時間
        paramMdl.setNtp094ReloadTime(NullDefault.getString(
                paramMdl.getNtp094ReloadTime(), String.valueOf(pconf.getNprAutoReload())));
        paramMdl.setNtp094TimeLabelList(__getTimeLabel());

        //表示位置
        if (paramMdl.getNtp094InitFlg() == GSConstNippou.INIT_FLG) {
            paramMdl.setNtp094Position(pconf.getNprDspPosition());
        } else {
            paramMdl.setNtp094Position(paramMdl.getNtp094Position());
        }

        //ソート1
        paramMdl.setNtp094SortKey1(pconf.getNprSortKey1());
        paramMdl.setNtp094SortOrder1(pconf.getNprSortOrder1());
        //ソート2
        paramMdl.setNtp094SortKey2(pconf.getNprSortKey2());
        paramMdl.setNtp094SortOrder2(pconf.getNprSortOrder2());

        //デフォルトグループ
        if (pconf.getNprDspMygroup() != 0) {
            paramMdl.setNtp094DefGroup(GSConstNippou.MY_GROUP_STRING
                    + String.valueOf(pconf.getNprDspMygroup()));
        } else {
            paramMdl.setNtp094DefGroup(String.valueOf(pconf.getNprDspGroup()));
        }

        //スケジュール表示
        if (paramMdl.getNtp094InitFlg() == GSConstNippou.INIT_FLG) {
            paramMdl.setNtp094SchKbn(pconf.getNprSchKbn());
        } else {
            paramMdl.setNtp094SchKbn(paramMdl.getNtp094SchKbn());
        }

        //表示情報をFormにセットする
        setDspData(paramMdl, con, umodel.getUsrsid());

        paramMdl.setNtp094InitFlg(GSConstNippou.NOT_INIT_FLG);

    }

    /**
     * <br>[機  能] 表示情報をFormにセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp094ParamModel
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws SQLException SQL実行エラー
     */
    public void setDspData(
            Ntp094ParamModel paramMdl, Connection con, int userSid) throws SQLException {

        //グループラベルの作成
        NtpCommonBiz cBiz = new NtpCommonBiz(con__, reqMdl__);
        List<NtpLabelValueModel> glabel = cBiz.getGroupLabelForNippou(userSid, con, false);
        paramMdl.setNtp094GroupLabel(glabel);

        //グループ存在チェック
        GroupBiz gpBiz = new GroupBiz();
        int gsid = gpBiz.getDefaultGroupSid(userSid, con);
        boolean gpFlg = false;
        for (NtpLabelValueModel lbvMdl : glabel) {
            if (lbvMdl.getValue().equals(paramMdl.getNtp094DefGroup())) {
                gpFlg = true;
            }
        }

        if (!gpFlg) {
            paramMdl.setNtp094DefGroup(String.valueOf(gsid));
        }
    }

    /**
     * <br>[機  能] 設定された個人設定情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp094ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setPconfSetting(Ntp094ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        //DBより設定情報を取得。なければデフォルト値とする。
        NtpCommonBiz biz = new NtpCommonBiz(con__, reqMdl__);
        NtpPriConfModel pconf = biz.getNtpPriConfModel(con, umodel.getUsrsid());

        //件数
        pconf.setNprDspList(paramMdl.getNtp094DefLine());
        pconf.setNprAutoReload(NullDefault.getInt(
                paramMdl.getNtp094ReloadTime(), GSConstNippou.AUTO_RELOAD_10MIN));
        pconf.setNprDspPosition(paramMdl.getNtp094Position());
        pconf.setNprEuid(umodel.getUsrsid());
        pconf.setNprEdate(new UDate());
        //ソート1
        pconf.setNprSortKey1(paramMdl.getNtp094SortKey1());
        pconf.setNprSortOrder1(paramMdl.getNtp094SortOrder1());
        //ソート2
        pconf.setNprSortKey2(paramMdl.getNtp094SortKey2());
        pconf.setNprSortOrder2(paramMdl.getNtp094SortOrder2());
        //デフォルトグループ
        if (NtpCommonBiz.isMyGroupSid(paramMdl.getNtp094DefGroup())) {
            //マイグループをデフォルト表示とした場合
            GroupBiz gpBiz = new GroupBiz();
            int gsid = gpBiz.getDefaultGroupSid(umodel.getUsrsid(), con);
            pconf.setNprDspGroup(gsid);
            pconf.setNprDspMygroup(NtpCommonBiz.getDspGroupSid(paramMdl.getNtp094DefGroup()));
        } else {
            pconf.setNprDspGroup(Integer.parseInt(paramMdl.getNtp094DefGroup()));
            pconf.setNprDspMygroup(0);
        }
        //スケジュール表示区分
        pconf.setNprSchKbn(paramMdl.getNtp094SchKbn());

        boolean commitFlg = false;
        try {
            NtpPriConfDao dao = new NtpPriConfDao(con);
            int count = dao.updateDsp(pconf);
            if (count <= 0) {
                //レコードがない場合は作成
                dao.insert(pconf);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
    }

    /**
     * <br>[機  能] 自動リロード時間コンボの表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @return labelList ラベルリスト
     */
    private List <LabelValueBean> __getTimeLabel() {
        List <LabelValueBean> labelList = new ArrayList <LabelValueBean>();
        labelList.add(new LabelValueBean("1分", "60000"));
        labelList.add(new LabelValueBean("3分", "180000"));
        labelList.add(new LabelValueBean("5分", "300000"));
        labelList.add(new LabelValueBean("10分", "600000"));
        labelList.add(new LabelValueBean("20分", "1200000"));
        labelList.add(new LabelValueBean("30分", "1800000"));
        labelList.add(new LabelValueBean("40分", "2400000"));
        labelList.add(new LabelValueBean("50分", "3000000"));
        labelList.add(new LabelValueBean("60分", "3600000"));
        labelList.add(new LabelValueBean("リロードしない", "0"));
        return labelList;
    }
}
