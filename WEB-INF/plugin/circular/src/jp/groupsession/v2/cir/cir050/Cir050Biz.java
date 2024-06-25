package jp.groupsession.v2.cir.cir050;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.dao.CirUserDao;
import jp.groupsession.v2.cir.model.CirUserModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 回覧板 基本設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir050Biz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(RequestModel reqMdl, Cir050ParamModel paramMdl, Connection con)
    throws SQLException {
        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //表示件数を取得
        CirUserDao cirDao = new CirUserDao(con);
        CirUserModel result = cirDao.getCirUserInfo(sessionUsrSid);

        if (result != null && result.getUsrSid() >= 0) {
            paramMdl.setCir050ViewCnt(result.getCurMaxDsp());
            //paramMdl.setCir050smlNtf(result.getCurSmlNtf());
            //自動リロード時間
            paramMdl.setCir050ReloadTime(NullDefault.getString(
                    paramMdl.getCir050ReloadTime(), String.valueOf(result.getCurReload())));
        } else {
            paramMdl.setCir050ReloadTime(String.valueOf(GSConstCircular.AUTO_RELOAD_10MIN));
        }

        //自動リロード時間コンボ
        paramMdl.setCir050TimeLabelList(__getTimeLabel(reqMdl));
    }

    /**
     * 表示件数コンボを生成します
     * @return List (in LabelValueBean)  表示件数コンボ
     */
    public List<LabelValueBean> getDspCntLabel() {
        List <LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        for (int cnt = 10; cnt <= 50; cnt += 10) {
            labelList.add(
                    new LabelValueBean(String.valueOf(cnt), String.valueOf(cnt)));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 表示件数の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void updateDspCount(RequestModel reqMdl, Cir050ParamModel paramMdl, Connection con)
    throws SQLException {
        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        UDate nowDate = new UDate();

        CirUserModel cirMdl = new CirUserModel();
        cirMdl.setUsrSid(sessionUsrSid);
        cirMdl.setCurMaxDsp(paramMdl.getCir050ViewCnt());
        //cirMdl.setCurSmlNtf(paramMdl.getCir050smlNtf());
        cirMdl.setCurReload(NullDefault.getInt(
                paramMdl.getCir050ReloadTime(), GSConstCircular.AUTO_RELOAD_10MIN));
        cirMdl.setCurEuid(sessionUsrSid);
        cirMdl.setCurEdate(nowDate);


        CirUserDao cirDao = new CirUserDao(con);
        int updateCnt = cirDao.updateDspCount(cirMdl);

        //更新件数が0件の場合、回覧板個人設定の新規登録を行う
        if (updateCnt == 0) {
            cirMdl.setCurAuid(sessionUsrSid);
            cirMdl.setCurAdate(nowDate);

//            cirMdl.setCurMemoKbn(GSConstCircular.CIR_INIT_MEMO_CHANGE_NO);
//            cirMdl.setCurMemoDay(GSConstCircular.CIR_INIT_MEMO_ONEWEEK);
//            cirMdl.setCurKouKbn(GSConstCircular.CIR_INIT_SAKI_PUBLIC);
//            cirMdl.setCurInitKbn(GSConstCircular.CIR_INIT_KBN_NOSET);

            cirDao.insertCirUser(cirMdl);
        }
    }

    /**
     * <br>[機  能] 自動リロード時間コンボの表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return labelList ラベルリスト
     */
    private List <LabelValueBean> __getTimeLabel(RequestModel reqMdl) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textHun = gsMsg.getMessage("cmn.minute");
        String textNoReload = gsMsg.getMessage("cmn.without.reloading");

        List <LabelValueBean> labelList = new ArrayList <LabelValueBean>();
        labelList.add(new LabelValueBean("1" + textHun, "60000"));
        labelList.add(new LabelValueBean("3" + textHun, "180000"));
        labelList.add(new LabelValueBean("5" + textHun, "300000"));
        labelList.add(new LabelValueBean("10" + textHun, "600000"));
        labelList.add(new LabelValueBean("20" + textHun, "1200000"));
        labelList.add(new LabelValueBean("30" + textHun, "1800000"));
        labelList.add(new LabelValueBean("40" + textHun, "2400000"));
        labelList.add(new LabelValueBean("50" + textHun, "3000000"));
        labelList.add(new LabelValueBean("60" + textHun, "3600000"));
        labelList.add(new LabelValueBean(textNoReload, "0"));
        return labelList;
    }
}
