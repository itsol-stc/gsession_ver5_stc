package jp.groupsession.v2.ntp.ntp083;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.biz.NtpUsedDataBiz;
import jp.groupsession.v2.ntp.dao.NtpDataDao;

/**
 * <br>[機  能] 日報 手動データ削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp083Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp083Biz.class);
    /** DBコネクション */
    public Connection con__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Ntp083Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 日報データを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp083ParamModel
     * @param umodel ユーザモデル
     * @throws SQLException SQL実行エラー
     */
    public void deleteNippou(Ntp083ParamModel paramMdl,
            BaseUserModel umodel) throws SQLException {
        log__.debug("日報データ 削除開始");

        //削除基準日の作成
        UDate bdate = new UDate();
        int year = paramMdl.getNtp083DelYear();
        int month = paramMdl.getNtp083DelMonth();
        bdate.addYear(-year);
        bdate.addMonth(-month);

        //日報情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        NtpDataDao dao = new NtpDataDao(con__);
        ArrayList<String> nipSidList = dao.getDeleteNipSid(bdate);
        List<Integer> sidList = new ArrayList<Integer>();
        for (String nipSid : nipSidList) {
            sidList.add(Integer.parseInt(nipSid));
        }
        NtpUsedDataBiz usedDataBiz = new NtpUsedDataBiz(con__);
        usedDataBiz.insertNtpDataSize(sidList, false);

        //削除実行
        NtpCommonBiz biz = new NtpCommonBiz(con__, reqMdl__);
        //日報確認データ削除
        biz.deleteOldNippouChk(con__, bdate);
        //日報コメントデータ削除
        biz.deleteOldNippouCmt(con__, bdate);
        //日報いいねデータ削除
        biz.deleteOldNippouGood(con__, bdate);
        //日報データ削除
        biz.deleteOldNippou(con__, bdate);

        log__.debug("日報データ 削除完了");
    }
}
