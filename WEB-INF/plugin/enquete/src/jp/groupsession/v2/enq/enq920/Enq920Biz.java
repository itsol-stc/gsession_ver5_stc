package jp.groupsession.v2.enq.enq920;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.dao.EnqTypeListDao;
import jp.groupsession.v2.enq.model.EnqTypeListModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 管理者設定 アンケート種類設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq920Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq920Biz.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public Enq920Biz() {
    }

    /**
     * <br>[機  能] アンケート種類を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq920Model パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Enq920ParamModel enq920Model,
                            RequestModel reqMdl,
                            Connection con) throws SQLException {

        log__.debug("アンケート種類の設定処理");

        ArrayList<EnqTypeListModel> dspList = new ArrayList<EnqTypeListModel>();
        List<EnqTypeListModel> typeList = new ArrayList<EnqTypeListModel>();
        EnqTypeListModel dspMdl = null;

        // アンケート種類を取得
        EnqTypeListDao dao = new EnqTypeListDao(con);
        typeList = dao.select();
        if (typeList == null || typeList.isEmpty()) {
            return;
        }

        // アンケート種類をセット
        for (EnqTypeListModel bean : typeList) {
            dspMdl = new EnqTypeListModel();
            dspMdl.setEtpSid(bean.getEtpSid());
            dspMdl.setEtpDspSeq(bean.getEtpDspSeq());
            dspMdl.setEtpName(bean.getEtpName());
            dspMdl.setEmnCnt(bean.getEmnCnt());
            dspMdl.setEmnOpenEnd(__getStrDate(reqMdl, bean.getEmnOpenEnd()));
            dspMdl.setEmnResEnd(__getStrDate(reqMdl, bean.getEmnResEnd()));
            dspList.add(dspMdl);
        }
        enq920Model.setEnq920TypeListForm(dspList);
    }

    /**
     * <br>[機  能] アンケート種類一覧を表示順でソートする
     * <br>[解  説]
     * <br>[備  考]
     * @param enq920Model パラメータモデル
     * @throws Exception SQL実行例外
     */
    public void setSortTypeList(Enq920ParamModel enq920Model) throws Exception {

        log__.debug("アンケート種類一覧のソート処理");

        // 表示順でソートする
        EnqCommonBiz enqBiz = new EnqCommonBiz();
        ArrayList<EnqTypeListModel> list =
                enqBiz.getSortTypeList(enq920Model.getEnq920TypeListToList());
        enq920Model.setEnq920TypeListForm(list);
    }

    /**
     * <br>[機  能] yyyy/mm/ddの日付から、yyyy年mm月dd日に変換する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param strDate yyyy/mm/ddの日付
     * @return yyyy年mm月dd日
     */
    private String __getStrDate(RequestModel reqMdl, String strDate) {

        EnqCommonBiz enqBiz = new EnqCommonBiz();
        String ret = "";

        ArrayList<String> list = new ArrayList<String>();
        list = StringUtil.split("/", strDate);
        String[] date = (String[]) list.toArray(new String[0]);
        if (date.length == 3) {
            ret = enqBiz.getStrDate(reqMdl, date[0], date[1], date[2]);
        }

        return ret;
    }

}
