package jp.groupsession.v2.rsv.rsv150;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.rsv.model.RsvUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 個人設定 表示設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv150Biz extends AbstractReserveBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv150Biz.class);

    /** コネクション */
    protected Connection con_ = null;
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public Rsv150Biz(Connection con, RequestModel reqMdl) {
        con_ = con;
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv150ParamModel
     * @param userSid ログインユーザSID
     * @throws SQLException 例外
     */
    public void initDsp(
            Rsv150ParamModel paramMdl, int userSid) throws SQLException {
        //施設グループコンボを取得
        paramMdl.setRsv150sisetuLabelList(_getGroupComboListDef(con_, reqMdl_));

        //自動リロード時間のコンボを取得
        paramMdl.setRsv150TimeLabelList(__getTimeLabel());

        //コンボデータセット
        setRsv150CombData(paramMdl);

        //表示件数コンボを取得
        paramMdl.setRsv150DspCntList(__getDspComboList());

        //もし、初期表示フラグがoffならば、
        if (paramMdl.getRsv150initDspFlg() == 0) {
            //チェックボックスをチェック
            paramMdl.setRsv150initDspFlg(1);
            paramMdl.setRsv150DispItem1(String.valueOf(GSConstReserve.KOJN_SETTEI_DSP_OK));
            paramMdl.setRsv150DispItem2(String.valueOf(GSConstReserve.KOJN_SETTEI_DSP_OK));
            paramMdl.setRsv150ReloadTime(String.valueOf(GSConstReserve.AUTO_RELOAD_10MIN));

            int hourFr = GSConstReserve.DEFAULT_START_HOUR;
            int hourTo = GSConstReserve.DEFAULT_END_HOUR;
            int dspCnt = GSConstReserve.RSV_DEFAULT_DSP;

            //管理者設定取得
            RsvAdmConfDao admconfDao = new RsvAdmConfDao(con_);
            RsvAdmConfModel admMdl = admconfDao.select();

            //個人設定取得
            RsvUserModel rsvUsr = _isRsvUser(reqMdl_, con_);

            if (admMdl != null) {
                //日間表示時間帯設定 表示区分
                paramMdl.setRsv150DateKbn(admMdl.getRacDtmKbn());
            }

            //もし、モデルがNULLじゃなければ、
            if (rsvUsr != null) {
                log__.debug("モデルから表示項目値を取得します");
                //モデルから表示項目値を取得
                paramMdl.setRsv150SelectedGrpSid(rsvUsr.getRsgSid());
                paramMdl.setRsv150DispItem1(String.valueOf(rsvUsr.getRsuDit1()));
                paramMdl.setRsv150DispItem2(String.valueOf(rsvUsr.getRsuDit2()));

                //自動リロード時間
                paramMdl.setRsv150ReloadTime(String.valueOf(rsvUsr.getRsuReload()));

                //施設画像表示区分
                paramMdl.setRsv150ImgDspKbn(rsvUsr.getRsuImgDsp());

                //初期表示画面
                paramMdl.setRsv150DefDsp(rsvUsr.getRsuIniDsp());


                //日間表示時間帯区分 = 管理者強制 の場合、管理者設定の表示時間を使用する
                if (admMdl != null && admMdl.getRacDtmKbn() == GSConstReserve.RAC_DTMKBN_ADM) {
                    hourFr = admMdl.getRacDtmFr();
                    hourTo = admMdl.getRacDtmTo();
                } else {
                    //個人設定がされている場合は表示時間を取得
                    hourFr = rsvUsr.getRsuDtmFr();
                    hourTo = rsvUsr.getRsuDtmTo();
                }

                dspCnt = rsvUsr.getRsuMaxDsp();
                if (dspCnt == 0) {
                    dspCnt = GSConstReserve.RSV_DEFAULT_DSP;
                }
            } else if (admMdl != null){
                //個人設定が存在しない場合は管理者設定の表示時間を取得
                hourFr = admMdl.getRacDtmFr();
                hourTo = admMdl.getRacDtmTo();
            }
            paramMdl.setRsv150SelectedFromSid(String.valueOf(hourFr));
            paramMdl.setRsv150SelectedToSid(String.valueOf(hourTo));
            paramMdl.setRsv150ViewCnt(dspCnt);
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
        GsMessage gsMsg = new GsMessage(reqMdl_);
        String strMinute = gsMsg.getMessage("cmn.minute");
        labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.1minute"), "60000"));
        labelList.add(new LabelValueBean("3" + strMinute, "180000"));
        labelList.add(new LabelValueBean("5" + strMinute, "300000"));
        labelList.add(new LabelValueBean("10" + strMinute, "600000"));
        labelList.add(new LabelValueBean("20" + strMinute, "1200000"));
        labelList.add(new LabelValueBean("30" + strMinute, "1800000"));
        labelList.add(new LabelValueBean("40" + strMinute, "2400000"));
        labelList.add(new LabelValueBean("50" + strMinute, "3000000"));
        labelList.add(new LabelValueBean("60" + strMinute, "3600000"));
        labelList.add(new LabelValueBean(gsMsg.getMessage("cmn.without.reloading"), "0"));
        return labelList;
    }
    /**
     * <br>[機  能] コンボデータをセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv160ParamModel
     */
    public void setRsv150CombData(Rsv150ParamModel paramMdl) {
        ArrayList<LabelValueBean> rsv150ourLabelList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl_);
        for (int i = 0; i < 24; i++) {
            rsv150ourLabelList.add(
                  new LabelValueBean(i + gsMsg.getMessage("cmn.hour"), Integer.toString(i)));
        }
        paramMdl.setRsv150ourLabelList(rsv150ourLabelList);
    }

    /**
     * <br>[機  能] 表示件数コンボリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return ret グループコンボリスト
     */
    private ArrayList<LabelValueBean> __getDspComboList() {

        log__.debug("表示件数コンボリストを取得");

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        for (int cnt = GSConstReserve.RSV_DEFAULT_DSP;
            cnt <= GSConstReserve.RSV_MAX_DSP;
            cnt += 10) {

            labelList.add(
                    new LabelValueBean(String.valueOf(cnt), String.valueOf(cnt)));
        }
        return labelList;
    }
}
