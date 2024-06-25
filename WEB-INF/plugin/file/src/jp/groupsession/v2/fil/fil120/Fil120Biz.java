package jp.groupsession.v2.fil.fil120;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileUconfDao;
import jp.groupsession.v2.fil.model.FileUconfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 個人設定 表示設定画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil120Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil120Biz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Fil120Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil120ParamModel
     * @param buMdl セッションユーザモデル
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Fil120ParamModel paramMdl, BaseUserModel buMdl)
    throws SQLException {

        log__.debug("fil120Biz Start");

        //画面表示設定
        __setDspData(paramMdl);

        //初期表示の場合、DB登録値を表示する。
        if (StringUtil.isNullZeroString(paramMdl.getFil120MainSort())) {
            __setData(paramMdl, buMdl.getUsrsid());
        }
    }

    /**
     * <br>[機  能] DBから個人設定を取得し、設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil120ParamModel
     * @param usrSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    private void __setData(Fil120ParamModel paramMdl, int usrSid) throws SQLException {

        FileUconfDao uconfDao = new FileUconfDao(con__);
        FileUconfModel uconf = uconfDao.select(usrSid);

        if (uconf == null) {
            __setDefault(paramMdl);
            return;
        }

        paramMdl.setFil120MainSort(String.valueOf(uconf.getFucMainOkini()));
        paramMdl.setFil120MainCall(String.valueOf(uconf.getFucMainCall()));
        paramMdl.setFil120RirekiCnt(String.valueOf(uconf.getFucRirekiCnt()));
        paramMdl.setFil120Call(String.valueOf(uconf.getFucCall()));
    }

    /**
     * <br>[機  能] 各入力項目に初期値を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil120ParamModel
     */
    private void __setDefault(Fil120ParamModel paramMdl) {

        paramMdl.setFil120MainSort(String.valueOf(GSConstFile.MAIN_OKINI_DSP_ON));
        paramMdl.setFil120MainCall(String.valueOf(GSConstFile.MAIN_CALL_DSP_CNT));
        paramMdl.setFil120RirekiCnt(String.valueOf(GSConstFile.RIREKI_COUNT_DEFAULT));
        paramMdl.setFil120Call(String.valueOf(GSConstFile.CALL_DSP_CNT));
    }

    /**
     * <br>[機  能] 画面表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil120ParamModel
     */
    private void __setDspData(Fil120ParamModel paramMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        String textNumber = gsMsg.getMessage("cmn.number");
        String textHyojisinai = gsMsg.getMessage("cmn.dont.show");

        //履歴表示件数コンボ
        ArrayList<LabelValueBean> rirekiCntLabelList = new ArrayList<LabelValueBean>();
        LabelValueBean label = null;
        for (int i = 10; i <= 50; i = i + 10) {
            label = new LabelValueBean(String.valueOf(i + textNumber), String.valueOf(i));
            rirekiCntLabelList.add(label);
        }
        paramMdl.setFil120RirekiCntLblList(rirekiCntLabelList);

        //更新通知表示件数コンボ
        ArrayList<LabelValueBean> callLabelList = new ArrayList<LabelValueBean>();
        LabelValueBean lbl = null;
        callLabelList.add(new LabelValueBean(textHyojisinai, "0"));
        callLabelList.add(new LabelValueBean("3" + textNumber, "3"));
        for (int i = 5; i <= 30; i = i + 5) {
            lbl = new LabelValueBean(String.valueOf(i + textNumber), String.valueOf(i));
            callLabelList.add(lbl);
        }
        paramMdl.setFil120CallLblList(callLabelList);

    }
}
