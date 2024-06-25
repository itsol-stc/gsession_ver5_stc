package jp.groupsession.v2.ntp.ntp260;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpTargetDao;
import jp.groupsession.v2.ntp.dao.NtpTemplateDao;
import jp.groupsession.v2.ntp.dao.NtpTmpMemberDao;
import jp.groupsession.v2.ntp.dao.NtpTmpTargetDao;
import jp.groupsession.v2.ntp.model.NtpLabelValueModel;
import jp.groupsession.v2.ntp.model.NtpTargetModel;
import jp.groupsession.v2.ntp.model.NtpTemplateModel;
import jp.groupsession.v2.ntp.model.NtpTmpMemberModel;
import jp.groupsession.v2.ntp.model.NtpTmpTargetModel;

/**
 * <br>[機  能] 日報 テンプレート情報ポップアップのビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 */
public class Ntp260Biz {

    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Ntp260Biz(Connection con, RequestModel reqMdl) {
        reqMdl__ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp260ParamModel
     * @param userMdl セッションユーザモデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp260ParamModel paramMdl,
            BaseUserModel userMdl,
            Connection con) throws SQLException {

        //テンプレート情報取得
        NtpTemplateDao nttDao = new NtpTemplateDao(con);
        NtpTemplateModel nttMdl = nttDao.select(paramMdl.getNtp260NttSid());
        paramMdl.setNtp260TemplateName(nttMdl.getNttName());
        paramMdl.setNtp260TemplateDetail(
                StringUtilHtml.transToHTmlPlusAmparsant(nttMdl.getNttDetail()));



        //項目名取得
        List<String> itemList = new ArrayList<String>();
        //案件
        if (nttMdl.getNttAnken() == GSConstNippou.ITEM_USE) {
            itemList.add("案件");
        }

        //企業・顧客
        if (nttMdl.getNttComp() == GSConstNippou.ITEM_USE) {
            itemList.add("企業・顧客");
        }

        //活動分類/方法
        if (nttMdl.getNttKatudo() == GSConstNippou.ITEM_USE) {
            itemList.add("活動分類・方法");
        }

        //見込み度
        if (nttMdl.getNttMikomi() == GSConstNippou.ITEM_USE) {
            itemList.add("見込み度");
        }

        //次のアクション
        if (nttMdl.getNttAction() == GSConstNippou.ITEM_USE) {
            itemList.add("次のアクション");
        }

        //添付ファイル
        if (nttMdl.getNttTemp() == GSConstNippou.ITEM_USE) {
            itemList.add("添付ファイル");
        }

        if (!itemList.isEmpty()) {
            paramMdl.setNtp260ItemList(itemList);
        }

        //テンプレート目標取得
        NtpTmpTargetDao nptDao = new NtpTmpTargetDao(con);
        ArrayList<NtpTmpTargetModel> nptList = null;
        nptList = nptDao.select(paramMdl.getNtp260NttSid());
        if (!nptList.isEmpty()) {
            List<Integer> targetList = new ArrayList<Integer>();
            for (NtpTmpTargetModel nptMdl : nptList) {
                targetList.add(nptMdl.getNtgSid());
            }
            NtpTargetDao trgDao = new NtpTargetDao(con);
            List<NtpTargetModel> npgList = null;
            npgList = trgDao.getNtpTargetList(targetList);

            if (!npgList.isEmpty()) {
                paramMdl.setNtp260TargetList(npgList);
            }
        }

        //テンプレートメンバーを取得
        NtpTmpMemberDao npmDao = new NtpTmpMemberDao(con);
        ArrayList<NtpTmpMemberModel> npmList = null;
        npmList = npmDao.select(paramMdl.getNtp260NttSid());
        if (!npmList.isEmpty()) {
            List<String> memList = new ArrayList<String>();
            for (NtpTmpMemberModel nsmMdl : npmList) {

                if (nsmMdl.getGrpSid() != -1) {
                    memList.add("G" + String.valueOf(nsmMdl.getGrpSid()));
                } else if (nsmMdl.getUsrSid() != -1) {
                    memList.add(String.valueOf(nsmMdl.getUsrSid()));
                }
            }

            if (!memList.isEmpty()) {
                paramMdl.setNtp260GpUsrList(__getMemberList(memList, con));
            }
        }
    }


    /**
     * <br>[機  能] メンバー一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param memList 取得するユーザSID・グループSID
     * @param con コネクション
     * @return メンバー一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<NtpLabelValueModel> __getMemberList(
            List<String> memList, Connection con)
    throws SQLException {

        ArrayList<NtpLabelValueModel> ret = new ArrayList<NtpLabelValueModel>();
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<String> usrSids = new ArrayList<String>();

        //ユーザSIDとグループSIDを分離
        if (!memList.isEmpty()) {
            for (int i = 0; i < memList.size(); i++) {
                String str = NullDefault.getString(memList.get(i), "-1");

                if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    grpSids.add(new Integer(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    usrSids.add(str);
                }
            }
        }

        NtpLabelValueModel lavelBean = null;

        //グループ情報取得
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
        lavelBean = null;
        for (GroupModel gmodel : glist) {
            lavelBean = new NtpLabelValueModel(gmodel.getGroupName(),
                                        "G" + String.valueOf(gmodel.getGroupSid()), "0");
            ret.add(lavelBean);
        }

        //ユーザ情報取得
        UserBiz userBiz = new UserBiz();
        List<CmnUsrmInfModel> ulist
                = userBiz.getUserList(con,
                                        (String[]) usrSids.toArray(new String[usrSids.size()]));
        for (CmnUsrmInfModel umodel : ulist) {
            lavelBean = new NtpLabelValueModel(umodel, "0");
            ret.add(lavelBean);
        }
        return ret;
    }
}