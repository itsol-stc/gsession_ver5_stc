package jp.groupsession.v2.ntp.ntp087;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpTemplateDao;
import jp.groupsession.v2.ntp.dao.NtpTemplateSortDao;
import jp.groupsession.v2.ntp.dao.NtpTmpMemberDao;
import jp.groupsession.v2.ntp.dao.NtpTmpTargetDao;
import jp.groupsession.v2.ntp.model.NtpTargetModel;
import jp.groupsession.v2.ntp.model.NtpTemplateModel;
import jp.groupsession.v2.ntp.model.NtpTemplateSortModel;
import jp.groupsession.v2.ntp.model.NtpTmpMemberModel;
import jp.groupsession.v2.ntp.model.NtpTmpTargetModel;
import jp.groupsession.v2.ntp.ntp230.Ntp230Dao;

/**
 * <br>[機  能] 日報 テンプレート登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp087Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp087Biz.class);
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
    public Ntp087Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp087ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Ntp087ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        int sessionUsrSid = umodel.getUsrsid();

        if (paramMdl.getNtp087InitFlg().equals(String.valueOf(GSConstNippou.INIT_FLG))) {

            if (paramMdl.getNtp087ProcMode() == GSConstNippou.CMD_EDIT) {
            //編集
                //テンプレート情報取得
                NtpTemplateDao nttDao = new NtpTemplateDao(con);
                NtpTemplateModel nttMdl = nttDao.select(paramMdl.getNtp086NttSid());
                if (nttMdl != null) {

                    //テンプレー名
                    paramMdl.setNtp087TemplateName(nttMdl.getNttName());

                    //テンプレート内容(初期値)
                    paramMdl.setNtp087Detail(nttMdl.getNttDetail());

                    ArrayList<String> itemList = new ArrayList<String>();

                    //案件
                    if (nttMdl.getNttAnken() == GSConstNippou.ITEM_USE) {
                        itemList.add(String.valueOf(GSConstNippou.ITEM_ANKEN));
                    }

                    //企業・顧客
                    if (nttMdl.getNttComp() == GSConstNippou.ITEM_USE) {
                        itemList.add(String.valueOf(GSConstNippou.ITEM_COMPANY));
                    }

                    //活動分類/方法
                    if (nttMdl.getNttKatudo() == GSConstNippou.ITEM_USE) {
                        itemList.add(String.valueOf(GSConstNippou.ITEM_KATUDO));
                    }

                    //見込み度
                    if (nttMdl.getNttMikomi() == GSConstNippou.ITEM_USE) {
                        itemList.add(String.valueOf(GSConstNippou.ITEM_MIKOMIDO));
                    }

                    //次のアクション
                    if (nttMdl.getNttAction() == GSConstNippou.ITEM_USE) {
                        itemList.add(String.valueOf(GSConstNippou.ITEM_ACTION));
                    }

                    //添付ファイル
                    if (nttMdl.getNttTemp() == GSConstNippou.ITEM_USE) {
                        itemList.add(String.valueOf(GSConstNippou.ITEM_TEMP));
                    }

                    if (!itemList.isEmpty()) {
                        paramMdl.setNtp087DspItem(itemList.toArray(new String[itemList.size()]));
                    }

                    //テンプレート目標取得
                    NtpTmpTargetDao nptDao = new NtpTmpTargetDao(con);
                    ArrayList<NtpTmpTargetModel> nptList = null;
                    nptList = nptDao.select(paramMdl.getNtp086NttSid());
                    if (!nptList.isEmpty()) {
                        ArrayList<String> targetList = new ArrayList<String>();
                        for (NtpTmpTargetModel nptMdl : nptList) {
                            targetList.add(String.valueOf(nptMdl.getNtgSid()));
                        }
                        paramMdl.setNtp087DspTarget(
                                targetList.toArray(new String[targetList.size()]));
                    }

                    //テンプレートメンバーを取得
                    NtpTmpMemberDao npmDao = new NtpTmpMemberDao(con);
                    ArrayList<NtpTmpMemberModel> npmList = null;
                    npmList = npmDao.select(paramMdl.getNtp086NttSid());
                    if (!npmList.isEmpty()) {
                        ArrayList<String> memList = new ArrayList<String>();
                        for (NtpTmpMemberModel nsmMdl : npmList) {

                            if (nsmMdl.getGrpSid() != -1) {
                                memList.add("G" + String.valueOf(nsmMdl.getGrpSid()));
                            } else if (nsmMdl.getUsrSid() != -1) {
                                memList.add(String.valueOf(nsmMdl.getUsrSid()));
                            }

                        }
                        paramMdl.setNtp087memberSid(memList.toArray(new String[memList.size()]));
                    }
                }

            } else {

                //項目初期値設定
                paramMdl.setNtp087DspItem(__getDefultItem());

            }

        }

        //目標一覧取得
        Ntp230Dao ntp230dao = new Ntp230Dao(con);
        List<NtpTargetModel> targetList = ntp230dao.getTargetList();
        if (!targetList.isEmpty()) {
            paramMdl.setNtp087TargetList(targetList);
        }

        paramMdl.setNtp087InitFlg(String.valueOf(GSConstNippou.NOT_INIT_FLG));
    }

    /**
     * <br>[機  能] 設定されたテンプレート情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp087ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行エラー
     */
    public void setTemplate(Ntp087ParamModel paramMdl,
            BaseUserModel umodel, Connection con, MlCountMtController cntCon)
                                                        throws SQLException {

        int usrSid = umodel.getUsrsid();
        NtpTemplateDao targetDao = new NtpTemplateDao(con);
        NtpTmpMemberDao tmpDao = new NtpTmpMemberDao(con);
        NtpTmpTargetDao tmpTrgDao = new NtpTmpTargetDao(con);
        NtpTemplateModel targetMdl = __createNtpTemplate(usrSid);
        ArrayList<NtpTmpMemberModel> tmpMdlList = new ArrayList<NtpTmpMemberModel>();

        //テンプレート名
        targetMdl.setNttName(paramMdl.getNtp087TemplateName());

        //表示項目取得
        ArrayList<Integer> selItmList = new ArrayList<Integer>();
        if (paramMdl.getNtp087DspItem() != null && paramMdl.getNtp087DspItem().length > 0) {
            for (String itm : paramMdl.getNtp087DspItem()) {
                selItmList.add(Integer.valueOf(itm));
            }
        }

        //案件
        if (selItmList.indexOf(GSConstNippou.ITEM_ANKEN) != -1) {
            targetMdl.setNttAnken(GSConstNippou.ITEM_USE);
        } else {
            targetMdl.setNttAnken(GSConstNippou.ITEM_NOT_USE);
        }

        //企業・顧客
        if (selItmList.indexOf(GSConstNippou.ITEM_COMPANY) != -1) {
            targetMdl.setNttComp(GSConstNippou.ITEM_USE);
        } else {
            targetMdl.setNttComp(GSConstNippou.ITEM_NOT_USE);
        }

        //活動分類/方法
        if (selItmList.indexOf(GSConstNippou.ITEM_KATUDO) != -1) {
            targetMdl.setNttKatudo(GSConstNippou.ITEM_USE);
        } else {
            targetMdl.setNttKatudo(GSConstNippou.ITEM_NOT_USE);
        }

        //見込み度
        if (selItmList.indexOf(GSConstNippou.ITEM_MIKOMIDO) != -1) {
            targetMdl.setNttMikomi(GSConstNippou.ITEM_USE);
        } else {
            targetMdl.setNttMikomi(GSConstNippou.ITEM_NOT_USE);
        }

        //次のアクション
        if (selItmList.indexOf(GSConstNippou.ITEM_ACTION) != -1) {
            targetMdl.setNttAction(GSConstNippou.ITEM_USE);
        } else {
            targetMdl.setNttAction(GSConstNippou.ITEM_NOT_USE);
        }

        //添付ファイル
        if (selItmList.indexOf(GSConstNippou.ITEM_TEMP) != -1) {
            targetMdl.setNttTemp(GSConstNippou.ITEM_USE);
        } else {
            targetMdl.setNttTemp(GSConstNippou.ITEM_NOT_USE);
        }

        //テンプレート内容
        if (!StringUtil.isNullZeroStringSpace(paramMdl.getNtp087Detail())) {
            targetMdl.setNttDetail(paramMdl.getNtp087Detail());
        }

        if (paramMdl.getNtp087ProcMode().equals(GSConstNippou.CMD_ADD)) {
            //追加モード
            //SID採番
            int nttSid = (int) cntCon.getSaibanNumber(GSConstNippou.SBNSID_NIPPOU,
                GSConstNippou.SBNSID_SUB_TEMPLATE, usrSid);
            targetMdl.setNttSid(nttSid);
            targetDao.insert(targetMdl);

            //ソートテーブルに追加
            NtpTemplateSortDao sortDao = new NtpTemplateSortDao(con);
            NtpTemplateSortModel sortMdl = new NtpTemplateSortModel();
            sortMdl.setNttSid(nttSid);
            sortMdl.setNpsSort(sortDao.getMaxSort());
            sortDao.insert(sortMdl);

            //グループ・ユーザ
            tmpMdlList
                = __getNtpTmpMemMdl(paramMdl.getNtp087memberSid(), con, nttSid);
            if (!tmpMdlList.isEmpty()) {
                for (NtpTmpMemberModel nsmMdl : tmpMdlList) {
                    tmpDao.insert(nsmMdl);
                }
            }

            //目標
            if (paramMdl.getNtp087DspTarget() != null
                    && paramMdl.getNtp087DspTarget().length > 0) {
                for (String trgSid : paramMdl.getNtp087DspTarget()) {
                    tmpTrgDao.insert(nttSid, Integer.valueOf(trgSid));
                }
            }

        } else {
            //変更モード
            targetMdl.setNttSid(paramMdl.getNtp086NttSid());
            targetDao.update(targetMdl);

            //グループ・ユーザ
            tmpDao.delete(paramMdl.getNtp086NttSid());
            tmpMdlList
                = __getNtpTmpMemMdl(paramMdl.getNtp087memberSid(), con, paramMdl.getNtp086NttSid());
            if (!tmpMdlList.isEmpty()) {
                for (NtpTmpMemberModel nsmMdl : tmpMdlList) {
                    tmpDao.insert(nsmMdl);
                }
            }

            //目標
            tmpTrgDao.delete(paramMdl.getNtp086NttSid());
            if (paramMdl.getNtp087DspTarget() != null
                    && paramMdl.getNtp087DspTarget().length > 0) {
                for (String trgSid : paramMdl.getNtp087DspTarget()) {
                    tmpTrgDao.insert(paramMdl.getNtp086NttSid(), Integer.valueOf(trgSid));
                }
            }
        }

    }

    /**
     * <br>[機  能] メンバー一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param left グループ ユーザ SID
     * @param con コネクション
     * @param nttSid テンプレートSID
     * @return メンバー一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<NtpTmpMemberModel> __getNtpTmpMemMdl(
            String[] left, Connection con, int nttSid)
            throws SQLException {

        ArrayList<NtpTmpMemberModel> ret = new ArrayList<NtpTmpMemberModel>();
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<String> usrSids = new ArrayList<String>();

        //ユーザSIDとグループSIDを分離
        if (left != null) {
            for (int i = 0; i < left.length; i++) {
                String str = NullDefault.getString(left[i], "-1");

                if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    grpSids.add(new Integer(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    usrSids.add(str);
                }
            }
        }

        //グループ情報取得
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
        for (GroupModel gmodel : glist) {
            NtpTmpMemberModel tmpMemMdl = new NtpTmpMemberModel();
            tmpMemMdl.setNtmTmpSid(nttSid);
            tmpMemMdl.setUsrSid(-1);
            tmpMemMdl.setGrpSid(gmodel.getGroupSid());
            ret.add(tmpMemMdl);
        }

        //ユーザ情報取得
        UserBiz userBiz = new UserBiz();
        ArrayList<BaseUserModel> ulist
                = userBiz.getBaseUserList(con,
                                        (String[]) usrSids.toArray(new String[usrSids.size()]));
        for (BaseUserModel umodel : ulist) {
            NtpTmpMemberModel tmpMemMdl = new NtpTmpMemberModel();
            tmpMemMdl.setNtmTmpSid(nttSid);
            tmpMemMdl.setUsrSid(umodel.getUsrsid());
            tmpMemMdl.setGrpSid(-1);
            ret.add(tmpMemMdl);
        }

        return ret;
    }

    /**
     * <br>[機  能] 設定項目のデフォルト値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return String[] デフォルト設定項目配列
     */
    private static String[] __getDefultItem() {
        String[] itms = GSConstNippou.ITEM_ALL;
        return itms;
    }

    /**
     * <br>[機  能] テンプレート情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return NtpContactModel
     */
    private NtpTemplateModel __createNtpTemplate(int usrSid) {

        UDate nowDate = new UDate();
        NtpTemplateModel mdl = new NtpTemplateModel();
        mdl.setNttAuid(usrSid);
        mdl.setNttAdate(nowDate);
        mdl.setNttEuid(usrSid);
        mdl.setNttEdate(nowDate);
        return mdl;
    }
}