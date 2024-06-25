package jp.groupsession.v2.anp.anp150kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.dao.AnpPriConfDao;
import jp.groupsession.v2.anp.model.AnpPriConfModel;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <br>[機  能] 安否確認 管理者設定 緊急連絡先一括設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp150knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp150knBiz.class);

    /**
     * <br>[機  能] 初期表示情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param anp150knModel パラメータモデル
     * @param con DBコネクション
     * @throws Exception 実行例外
     */
    public void setInitData(Anp150knParamModel anp150knModel, Connection con)
                throws Exception {
        log__.debug("初期表示");

        //グループ・ユーザ指定の場合
        if (anp150knModel.getAnp150TargetKbn() == Anp150knParamModel.TAISYO_SELECT) {
            __setInitDataSelect(con, anp150knModel);
        } else {
            //全てのユーザの場合
            __setInitDataAll(con, anp150knModel);
        }
    }

    /**
     * <br>[機  能] 指定グループ・ユーザ選択時の初期データを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param anp150knModel パラメータモデル
     * @throws SQLException SQL実行例外
     */
    private void __setInitDataSelect(Connection con, Anp150knParamModel anp150knModel)
            throws SQLException {

        AnpiCommonBiz anpiBiz = new AnpiCommonBiz(con);
        CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con);
        //チェックするユーザ一覧
        ArrayList<Integer> usrSids = new ArrayList<Integer>();
        //表示ユーザデータリスト
        ArrayList<UsrLabelValueBean> strSettingList = new ArrayList<UsrLabelValueBean>();
        //表示除外ユーザデータリスト
        ArrayList<UsrLabelValueBean> strCutSettingList = new ArrayList<UsrLabelValueBean>();

        //分離用リスト
        ArrayList<Integer> svGrpSidList = new ArrayList<Integer>();
        ArrayList<Integer> svUsrSidList = new ArrayList<Integer>();
        //ユーザSIDとグループSIDを分離する
        for (String sid : anp150knModel.getAnp150TargetList()) {
            sid = NullDefault.getString(sid, "-1");
            if (sid.equals("-1")) {
                continue;
            }
            if (sid.contains(new String("G").subSequence(0, 1))) {
                //グループ
                svGrpSidList.add(new Integer(sid.substring(1, sid.length())));
            } else {
                //ユーザ
                svUsrSidList.add(new Integer(sid));
            }
        }


        int okUsrNum = 0;
        //除外が必要な場合 有効データ件数チェックをする
        if ((anp150knModel.getAnp150PassKbn() == 1)
                && (!anp150knModel.getAnp150SelectMail().equals("0"))) {

            //有効データと除外データを分ける
            //分離したリストからユーザSIDのリストを生成する。

            //対象ユーザSID(重複SID不可)
            HashSet<Integer> svTargetUsrSids = new HashSet<Integer>();
            //選択されたグループSIDから所属しているユーザSIDを取得
            if (svGrpSidList != null && !(svGrpSidList.size() < 1)) {
                ArrayList<Integer> usrSidList = anpiBiz.getUsrSidList(svGrpSidList);
                svTargetUsrSids.addAll(usrSidList);
            }
            if (svUsrSidList != null && !(svUsrSidList.size() < 1)) {
                svTargetUsrSids.addAll(svUsrSidList);
            }
            usrSids.addAll(svTargetUsrSids);


            //SIDリストからユーザ情報を取得する。
            ArrayList<CmnUsrmInfModel> selectUsrmInfList = usrmInfDao.getUserList(usrSids);

            ArrayList<CmnUsrmInfModel> okUsrList = new ArrayList<CmnUsrmInfModel>();
            ArrayList<CmnUsrmInfModel> cutUsrList = new ArrayList<CmnUsrmInfModel>();

            //ユーザ情報を元にアドレスが設定されている、されていないSIDに分離
            for (CmnUsrmInfModel usrMdl : selectUsrmInfList) {
                if (usrMdl.getUsrSid() < 100) {
                    continue;
                }
                if (anp150knModel.getAnp150SelectMail().equals("1")) {
                    if (StringUtil.isNullZeroString(usrMdl.getUsiMail1())) {
                        cutUsrList.add(usrMdl);
                    } else {
                        okUsrList.add(usrMdl);
                    }
                } else if (anp150knModel.getAnp150SelectMail().equals("2")) {
                    if (StringUtil.isNullZeroString(usrMdl.getUsiMail2())) {
                        cutUsrList.add(usrMdl);
                    } else {
                        okUsrList.add(usrMdl);
                    }
                } else if (anp150knModel.getAnp150SelectMail().equals("3")) {
                    if (StringUtil.isNullZeroString(usrMdl.getUsiMail3())) {
                        cutUsrList.add(usrMdl);
                    } else {
                        okUsrList.add(usrMdl);
                    }
                }
            }

            //設定されているリストの人数
            okUsrNum = okUsrList.size();
            //設定されている表示用ユーザリストを生成
            if (svGrpSidList != null && !(svGrpSidList.size() < 1)) {
                UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
                ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(svGrpSidList);
                for (GroupModel grpMdl : glist) {
                    strSettingList.add(
                            new UsrLabelValueBean(grpMdl.getGroupName(),
                                    UserGroupSelectBiz.GROUP_PREFIX
                                    + String.valueOf(grpMdl.getGroupSid())));
                }
            }

            //選択されたユーザSIDからユーザ情報を取得
            if (svUsrSidList != null && !(svUsrSidList.size() < 1)) {
                //対象ユーザ情報を取得する
                ArrayList<CmnUsrmInfModel> usrmInfList =
                        __getOkUsrList(anp150knModel, usrmInfDao.getUserList(svUsrSidList));
                for (CmnUsrmInfModel usrMdl : usrmInfList) {
                    strSettingList.add(
                            new UsrLabelValueBean(usrMdl));
                }
            }

            //----------------------除外用リストがあった場合のみ
            //設定されていないリストの人数をフォームにセットする
            if (cutUsrList != null && !(cutUsrList.size() < 1)) {
                anp150knModel.setAnp150knCutUsrNum(cutUsrList.size());
                //設定されていない表示用ユーザリストを生成＆セットする
                for (CmnUsrmInfModel usrMdl : cutUsrList) {
                    strCutSettingList.add(
                            new UsrLabelValueBean(usrMdl));
                }
                anp150knModel.setAnp150knCutUsrList(strCutSettingList);
            }

        } else {
            //除外が不必要な場合 現行の処理

            //選択されたグループSIDからグループ情報を取得
            if (svGrpSidList != null && !(svGrpSidList.size() < 1)) {
                UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
                ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(svGrpSidList);
                for (GroupModel grpMdl : glist) {
                    strSettingList.add(
                            new UsrLabelValueBean(grpMdl.getGroupName(),
                                    UserGroupSelectBiz.GROUP_PREFIX
                                    + String.valueOf(grpMdl.getGroupSid())));

                }
            }

            //選択されたユーザSIDからユーザ情報を取得
            if (svUsrSidList != null && !(svUsrSidList.size() < 1)) {
                //対象ユーザ情報を取得する
                ArrayList<CmnUsrmInfModel> usrmInfList = usrmInfDao.getUserList(svUsrSidList);
                for (CmnUsrmInfModel usrMdl : usrmInfList) {
                    strSettingList.add(
                            new UsrLabelValueBean(usrMdl));
                }
            }

            //対象ユーザ数カウント用(重複SIDを除去)
            HashSet<Integer> cntUsrSidList = new HashSet<Integer>();
            //選択グループからユーザ情報を取得
            if (svGrpSidList != null) {
                for (int grpSid: svGrpSidList) {
                    List<CmnUsrmInfModel> belongList =
                       anpiBiz.getBelongUserList(con, Integer.valueOf(grpSid), null, -1, false);
                    for (CmnUsrmInfModel usrMdl : belongList) {
                        cntUsrSidList.add(usrMdl.getUsrSid());
                    }
                }
            }
            //選択ユーザの追加
            if (svUsrSidList != null) {
                cntUsrSidList.addAll(svUsrSidList);
            }

            okUsrNum = cntUsrSidList.size();
        }

        //表示用リスト
        anp150knModel.setAnp150knOkUsrList(strSettingList);
        //対象ユーザ数
        anp150knModel.setAnp150knOkUsrNum(okUsrNum);
    }

    /**
     * <br>[機  能] 全ユーザ選択時の初期データを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param anp150knModel パラメータモデル
     * @throws SQLException SQL実行例外
     */
    private void __setInitDataAll(Connection con, Anp150knParamModel anp150knModel)
            throws SQLException {

        CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con);
        //チェックするユーザ一覧
        ArrayList<Integer> usrSids = new ArrayList<Integer>();
        //表示ユーザデータリスト
        ArrayList<UsrLabelValueBean> strSettingList = new ArrayList<UsrLabelValueBean>();

        //全てのユーザ GS管理者、システムメール含む
        ArrayList<CmnUsrmInfModel> usrmInfList = usrmInfDao.getUserList(usrSids);
        ArrayList<CmnUsrmInfModel> escUsrmInfList = new ArrayList<CmnUsrmInfModel>();
        for (CmnUsrmInfModel mdl : usrmInfList) {
            //GS管理者、システムメール除外
            if (mdl.getUsrSid() < 100) {
                continue;
            }
            escUsrmInfList.add(mdl);
        }

        //有効データ件数チェック
        if ((anp150knModel.getAnp150PassKbn() == 1)
                && (!anp150knModel.getAnp150SelectMail().equals("0"))) {
            ArrayList<CmnUsrmInfModel> cutUsrmInfList =
                    __getCutUsrList(anp150knModel, escUsrmInfList);
            for (CmnUsrmInfModel usrMdl : cutUsrmInfList) {
                strSettingList.add(
                        new UsrLabelValueBean(usrMdl));
            }
            anp150knModel.setAnp150knCutUsrList(strSettingList);
            anp150knModel.setAnp150knCutUsrNum(strSettingList.size());
            anp150knModel.setAnp150knOkUsrNum(escUsrmInfList.size() - strSettingList.size());
        } else {
            anp150knModel.setAnp150knOkUsrNum(escUsrmInfList.size());
        }
    }

    /**
     * <br>[機  能] 除外ユーザリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param anp150knModel パラメータモデル
     * @param usrmInfList ユーザリスト
     * @return 除外ユーザリスト
     */
    private ArrayList<CmnUsrmInfModel> __getCutUsrList(
            Anp150knParamModel anp150knModel,
            ArrayList<CmnUsrmInfModel> usrmInfList) {
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();

            //除外ユーザリストを作成
            for (CmnUsrmInfModel usrMdl : usrmInfList) {
                if (usrMdl.getUsrSid() < 100) {
                    continue;
                }
                if (anp150knModel.getAnp150SelectMail().equals("1")) {
                    if (StringUtil.isNullZeroString(usrMdl.getUsiMail1())) {
                        ret.add(usrMdl);
                    }
                } else if (anp150knModel.getAnp150SelectMail().equals("2")) {
                    if (StringUtil.isNullZeroString(usrMdl.getUsiMail2())) {
                        ret.add(usrMdl);
                    }
                } else if (anp150knModel.getAnp150SelectMail().equals("3")) {
                    if (StringUtil.isNullZeroString(usrMdl.getUsiMail3())) {
                        ret.add(usrMdl);
                    }
                }
            }
        return ret;
    }

    /**
     * <br>[機  能] 有効ユーザリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param anp150knModel パラメータモデル
     * @param usrmInfList ユーザリスト
     * @return 除外ユーザリスト
     */
    private ArrayList<CmnUsrmInfModel> __getOkUsrList(
            Anp150knParamModel anp150knModel,
            ArrayList<CmnUsrmInfModel> usrmInfList) {
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();

            //除外ユーザリストを作成
            for (CmnUsrmInfModel usrMdl : usrmInfList) {
                if (usrMdl.getUsrSid() < 100) {
                    continue;
                }
                if (anp150knModel.getAnp150SelectMail().equals("1")) {
                    if (!StringUtil.isNullZeroString(usrMdl.getUsiMail1())) {
                        ret.add(usrMdl);
                    }
                } else if (anp150knModel.getAnp150SelectMail().equals("2")) {
                    if (!StringUtil.isNullZeroString(usrMdl.getUsiMail2())) {
                        ret.add(usrMdl);
                    }
                } else if (anp150knModel.getAnp150SelectMail().equals("3")) {
                    if (!StringUtil.isNullZeroString(usrMdl.getUsiMail3())) {
                        ret.add(usrMdl);
                    }
                }
            }
        return ret;
    }

    /**
     * <br>[機  能] 緊急連絡先一括設定の更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param anp150knModel パラメータモデル
     * @param sessionUsrSid ユーザSID
     * @throws SQLException SQL実行例外
     * @return 更新ユーザ数
     */
    public int updatePriMail(Connection con,
            Anp150knParamModel anp150knModel,
            int sessionUsrSid) throws SQLException {

        int ret = 0;
        AnpiCommonBiz anpiBiz = new AnpiCommonBiz(con);

        CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con);
        ArrayList<Integer> targetUsrSids = new ArrayList<Integer>();
        ArrayList<CmnUsrmInfModel> targetUsrmInfList = null;
        //指定グループ・ユーザ
        if (anp150knModel.getAnp150TargetKbn() == Anp150knParamModel.TAISYO_SELECT) {
            ArrayList<Integer> svGrpSidList = new ArrayList<Integer>();
            ArrayList<Integer> svUsrSidList = new ArrayList<Integer>();
            //ユーザSIDとグループSIDを分離する
            for (String sid : anp150knModel.getAnp150TargetList()) {
                sid = NullDefault.getString(sid, "-1");
                if (sid.equals("-1")) {
                    continue;
                }
                if (sid.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    svGrpSidList.add(new Integer(sid.substring(1, sid.length())));
                } else {
                    //ユーザ
                    svUsrSidList.add(new Integer(sid));
                }
            }

            //対象ユーザSID(重複SID不可)
            HashSet<Integer> svTargetUsrSids = new HashSet<Integer>();

            //選択されたグループSIDから所属しているユーザSIDを取得
            if (svGrpSidList != null && !(svGrpSidList.size() < 1)) {
                ArrayList<Integer> usrSidList = anpiBiz.getUsrSidList(svGrpSidList);
                svTargetUsrSids.addAll(usrSidList);
            }

            if (svUsrSidList != null && !(svUsrSidList.size() < 1)) {
                svTargetUsrSids.addAll(svUsrSidList);
            }
            targetUsrSids.addAll(svTargetUsrSids);
        }

        //全ユーザリストの取得
        //ユーザ情報一覧  全取得の場合GS管理者、システムメール含む
        ArrayList<CmnUsrmInfModel> usrmInfList = usrmInfDao.getUserList(targetUsrSids);

        //コンボで選択したメールアドレス(１・２・３)が設定されていないユーザは除外
        targetUsrmInfList = __getTargetUsr(anp150knModel, usrmInfList);

        //更新処理
        for (CmnUsrmInfModel usrMdl : targetUsrmInfList) {
            AnpPriConfDao pDao = new AnpPriConfDao(con);

            AnpPriConfModel mdl = anpiBiz.getAnpPriConfModel(con, usrMdl.getUsrSid());
            AnpPriConfModel udateMdl = new AnpPriConfModel();

            String mail = null;
            if (anp150knModel.getAnp150SelectMail().equals("1")) {
                mail = usrMdl.getUsiMail1();
            } else if (anp150knModel.getAnp150SelectMail().equals("2")) {
                mail = usrMdl.getUsiMail2();
            } else if (anp150knModel.getAnp150SelectMail().equals("3")) {
                mail = usrMdl.getUsiMail3();
            } else if (anp150knModel.getAnp150SelectMail().equals("0")) {
                mail = anp150knModel.getAnp150OtherMail();
            }

            udateMdl.setAppMailadr(mail);
            udateMdl.setAppTelno(NullDefault.getString(mdl.getAppTelno(), ""));
            udateMdl.setAppEuid(sessionUsrSid);
            udateMdl.setUsrSid(usrMdl.getUsrSid());

            //上書きする
            if (anp150knModel.getAnp150UpdateFlg() == 1) {
                pDao.doUpdateAnp050kn(udateMdl);
                ret++;
            } else {
                //上書きしない
                if (StringUtil.isNullZeroString(mdl.getAppMailadr())) {
                    //未設定だった場合更新
                    pDao.doUpdateAnp050kn(udateMdl);
                    ret++;
                }
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] メールコンボボックスで指定したアドレスが設定されているユーザリストを生成する。
     *                     その他のメールを設定した場合は全ユーザを返す。
     * <br>[解  説]
     * <br>[備  考] GS管理者、システムメールは除外。
     * @param anp150knModel パラメータモデル
     * @param usrmInfList 指定したユーザ情報一覧
     * @return ユーザ情報リスト
     */
    private ArrayList<CmnUsrmInfModel> __getTargetUsr(Anp150knParamModel anp150knModel,
            ArrayList<CmnUsrmInfModel> usrmInfList
            ) {

        ArrayList<CmnUsrmInfModel> ret =
                new ArrayList<CmnUsrmInfModel>();

        //メールアドレス登録していないユーザには設定しない
        if ((anp150knModel.getAnp150PassKbn() == 1)
                && (!anp150knModel.getAnp150SelectMail().equals("0"))) {
            for (CmnUsrmInfModel model : usrmInfList) {
                if (model.getUsrSid() < 100) {
                    continue;
                }
                int selectmail = Integer.valueOf(anp150knModel.getAnp150SelectMail());
                switch (selectmail) {
                case 1:
                    if (!StringUtil.isNullZeroString(model.getUsiMail1())) {
                        ret.add(model);
                    }
                    break;
                case 2:
                    if (!StringUtil.isNullZeroString(model.getUsiMail2())) {
                        ret.add(model);
                    }
                    break;
                case 3:
                    if (!StringUtil.isNullZeroString(model.getUsiMail3())) {
                        ret.add(model);
                    }
                    break;
                default:
                }
            }
        } else {
            for (CmnUsrmInfModel model : usrmInfList) {
                if (model.getUsrSid() < 100) {
                    continue;
                }
                ret.add(model);
            }
        }

        return ret;
    }
}