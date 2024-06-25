package jp.groupsession.v2.ntp.ntp120;

import java.sql.Connection;
import java.sql.SQLException;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;

/**
 * <br>[機  能] 日報 マスタメンテナンス画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp120Biz {
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
    public Ntp120Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp120ParamModel
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp120ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        paramMdl.setDspMod(GSConstNippou.DSP_MOD_MASTA);

        /**-----------各マスタの件数と最終更新日を取得----------- */
        Ntp120Dao dao = new Ntp120Dao(con);
        Ntp120DataModel dataMdl = null;

        //商品マスタ
        dataMdl = dao.getShohinData();
        if (dataMdl != null) {
            paramMdl.setNtp120ShohinCnt(dataMdl.getCount());
            if (dataMdl.getLastEdate() != null) {
                paramMdl.setNtp120ShohinDay(UDateUtil.getSlashYYMD(dataMdl.getLastEdate()));
            }
            dataMdl = null;
        }

        //業種マスタ
        dataMdl = dao.getGyoushuData();
        if (dataMdl != null) {
            paramMdl.setNtp120GyoushuCnt(dataMdl.getCount());
            if (dataMdl.getLastEdate() != null) {
                paramMdl.setNtp120GyoushuDay(UDateUtil.getSlashYYMD(dataMdl.getLastEdate()));
            }
            dataMdl = null;
        }

        //プロセスマスタ
        dataMdl = dao.getProcessData();
        if (dataMdl != null) {
            paramMdl.setNtp120ProcessCnt(dataMdl.getCount());
            if (dataMdl.getLastEdate() != null) {
                paramMdl.setNtp120ProcessDay(UDateUtil.getSlashYYMD(dataMdl.getLastEdate()));
            }
            dataMdl = null;
        }

        //活動分類
        dataMdl = dao.getKtBunruiData();
        if (dataMdl != null) {
            paramMdl.setNtp120KtBunruiCnt(dataMdl.getCount());
            if (dataMdl.getLastEdate() != null) {
                paramMdl.setNtp120KtBunruiDay(UDateUtil.getSlashYYMD(dataMdl.getLastEdate()));
            }
            dataMdl = null;
        }

        //活動方法
        dataMdl = dao.getKtHouhouData();
        if (dataMdl != null) {
            paramMdl.setNtp120KtHouhouCnt(dataMdl.getCount());
            if (dataMdl.getLastEdate() != null) {
                paramMdl.setNtp120KtHouhouDay(UDateUtil.getSlashYYMD(dataMdl.getLastEdate()));
            }
            dataMdl = null;
        }

        //コンタクト
        dataMdl = dao.getContactData();
        if (dataMdl != null) {
            paramMdl.setNtp120ContactCnt(dataMdl.getCount());
            if (dataMdl.getLastEdate() != null) {
                paramMdl.setNtp120ContactDay(UDateUtil.getSlashYYMD(dataMdl.getLastEdate()));
            }
            dataMdl = null;
        }

        //目標
        dataMdl = dao.getTargetData();
        if (dataMdl != null) {
            paramMdl.setNtp120TargetCnt(dataMdl.getCount());
            if (dataMdl.getLastEdate() != null) {
                paramMdl.setNtp120TargetDay(UDateUtil.getSlashYYMD(dataMdl.getLastEdate()));
            }
            dataMdl = null;
        }
    }
}
