package jp.groupsession.v2.zsk.zsk130;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchLabelValueModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;
import jp.groupsession.v2.zsk.dao.ZaiGpriConfDao;
import jp.groupsession.v2.zsk.maingrp.ZskMaingrpCommonBiz;
import jp.groupsession.v2.zsk.model.ZaiGpriConfModel;
import jp.groupsession.v2.zsk.model.ZskSortModel;

/**
 * <br>[機  能] 在席管理 個人設定 座席表メンバー表示設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk130Biz {

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     */
    public Zsk130Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Zsk130ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @param first 初回表示
     * @throws Exception SQL実行エラー
     */
    public void setInitData(Zsk130ParamModel paramMdl,
                             BaseUserModel umodel,
                             Connection con, boolean first) throws Exception {


        ZsjCommonBiz cmnBiz = new ZsjCommonBiz(reqMdl__);

        paramMdl.setZsk130SortConfAble(cmnBiz.canEditViewMember(con));


        //初回DB値取得
        if (first) {
            //
            ZskSortModel sortMdl = cmnBiz.getSortData(con, umodel.getUsrsid());
            paramMdl.setZsk130SortKey1(sortMdl.getSort1());
            paramMdl.setZsk130SortKey2(sortMdl.getSort2());
            paramMdl.setZsk130SortOrder1(sortMdl.getOrder1());
            paramMdl.setZsk130SortOrder2(sortMdl.getOrder2());

            //コンボ選択値取得
            ZskMaingrpCommonBiz biz = new ZskMaingrpCommonBiz();
            String groupCodeStr = biz.selectGroupSid(umodel.getUsrsid(), con);

            //コンボ選択値セット
            paramMdl.setZsk130mainDspGpSid(
                    NullDefault.getString(paramMdl.getZsk130mainDspGpSid(), groupCodeStr));
            //表示順・スケジュールデフォルト設定
            __setGpriInf(paramMdl, con, umodel.getUsrsid());
        }
        //表示順ラベル設定
        setLabelData(paramMdl);

        //メイン画面 グループ表示設定
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String name = gsMsg.getMessage("cmn.name");
        String number = gsMsg.getMessage("cmn.employee.staff.number");
        String post = gsMsg.getMessage("cmn.post");
        String birth = gsMsg.getMessage("cmn.birthday");
        String sortkey1 = gsMsg.getMessage("cmn.sortkey") + "1";
        String sortkey2 = gsMsg.getMessage("cmn.sortkey") + "2";

        String[] sortAllText = {name, number, post, birth,
                sortkey1, sortkey2};

        //ソートキーラベル
        ArrayList<LabelValueBean> sortLabel = new ArrayList<LabelValueBean>();
        for (int i = 0; i < sortAllText.length; i++) {
            String label = sortAllText[i];
            String value = Integer.toString(GSConstZaiseki.SORT_KEY_ALL[i]);
            sortLabel.add(new LabelValueBean(label, value));
        }
        paramMdl.setZsk130mainDspSortKeyLabel(sortLabel);

        //コンボセット グループ
        SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);
        List<SchLabelValueModel> groupLabel = scBiz.getGroupLabelForSchedule(
                umodel.getUsrsid(), con, false);
        paramMdl.setZsk130mainDspGpLabelList(groupLabel);



    }
    /**
     * <br>[機  能] 表示順の設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk130ParamModel
     * @param con コネクション
     * @param usrSid ユーザSID
     * @throws Exception SQL実行エラー
     */
    public void __setGpriInf(
            Zsk130ParamModel paramMdl, Connection con, int usrSid) throws Exception {

        ZaiGpriConfDao dao = new ZaiGpriConfDao(con);
        ZaiGpriConfModel model = dao.select(usrSid);

        if (paramMdl.getZsk130mainDspFlg() == -1 && model != null) {
            paramMdl.setZsk130mainDspSortKey1(NullDefault.getInt(
                    String.valueOf(model.getZgcSortKey1()), GSConstZaiseki.SORT_KEY_NAME));
            paramMdl.setZsk130mainDspSortKey2(NullDefault.getInt(
                    String.valueOf(model.getZgcSortKey2()), GSConstZaiseki.SORT_KEY_NAME));
            paramMdl.setZsk130mainDspSortOrder1(NullDefault.getInt(
                    String.valueOf(model.getZgcSortOrder1()), GSConst.ORDER_KEY_ASC));
            paramMdl.setZsk130mainDspSortOrder2(NullDefault.getInt(
                    String.valueOf(model.getZgcSortOrder2()), GSConst.ORDER_KEY_ASC));
            paramMdl.setZsk130mainDspSchViewDf(NullDefault.getInt(
                    String.valueOf(model.getZgcSchViewDf()), GSConstZaiseki.MAIN_SCH_DSP));
            paramMdl.setZsk130mainDspFlg(NullDefault.getInt(
                    String.valueOf(model.getZgcViewKbn()), GSConstZaiseki.MAINGRP_DSP));

        } else if (paramMdl.getZsk130mainDspFlg() == -1 && model == null) {
            paramMdl.setZsk130mainDspSortKey1(GSConstZaiseki.SORT_KEY_NAME);
            paramMdl.setZsk130mainDspSortKey2(GSConstZaiseki.SORT_KEY_NAME);
            paramMdl.setZsk130mainDspSortOrder1(GSConst.ORDER_KEY_ASC);
            paramMdl.setZsk130mainDspSortOrder2(GSConst.ORDER_KEY_ASC);
            paramMdl.setZsk130mainDspSchViewDf(GSConstZaiseki.MAIN_SCH_DSP);
            paramMdl.setZsk130mainDspFlg(GSConstZaiseki.MAINGRP_DSP);
        }
    }

    /**
     * <br>[機  能] コンボ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Zsk130ParamModel
     * @throws Exception SQL実行エラー
     */
    public void setLabelData(Zsk130ParamModel paramMdl) throws Exception {

        ArrayList<LabelValueBean> sortLabel = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String name = gsMsg.getMessage("cmn.name");
        String number = gsMsg.getMessage("cmn.employee.staff.number");
        String post = gsMsg.getMessage("cmn.post");
        String birth = gsMsg.getMessage("cmn.birthday");
        String zaiseki = gsMsg.getMessage("zsk.20");
        String comment = gsMsg.getMessage("zsk.23");
        String sortkey1 = gsMsg.getMessage("cmn.sortkey") + "1";
        String sortkey2 = gsMsg.getMessage("cmn.sortkey") + "2";
        String[] sortZskText = {name, number, post, birth, zaiseki, comment,
                sortkey1, sortkey2};

        for (int i = 0; i < sortZskText.length; i++) {
            String label = sortZskText[i];
            String value = Integer.toString(GSConstZaiseki.SORT_KEY_ZSK[i]);
            sortLabel.add(new LabelValueBean(label, value));
        }
        paramMdl.setZsk130SortKeyLabel(sortLabel);

    }

}