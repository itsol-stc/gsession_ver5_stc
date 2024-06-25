package jp.groupsession.v2.ntp.ntp081;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpAdmConfDao;
import jp.groupsession.v2.ntp.dao.NtpColMsgDao;
import jp.groupsession.v2.ntp.dao.NtpMikomidoMsgDao;
import jp.groupsession.v2.ntp.model.NtpAdmConfModel;
import jp.groupsession.v2.ntp.model.NtpColMsgModel;
import jp.groupsession.v2.ntp.model.NtpMikomidoMsgModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報 基本設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp081Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp081Biz.class);
    /** リクエスモデル */
    public RequestModel reqMdl_ = null;
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
    public Ntp081Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp081ParamModel
     * @param con コネクション
     * @throws Exception SQL実行エラー
     */
    public void setInitData(Ntp081ParamModel paramMdl, Connection con) throws Exception {
        log__.debug("setInitData START");

        //日報管理者設定の値を取得
        NtpAdmConfDao adminDao = new NtpAdmConfDao(con);
        NtpAdmConfModel adminModel = adminDao.select();
        if (adminModel != null) {
            if (paramMdl.getNtp081KyoyuFlg() == -1) {
                paramMdl.setNtp081KyoyuFlg(adminModel.getNacCrange());
            }
            if (paramMdl.getNtp081HourDivision() == -1) {
                paramMdl.setNtp081HourDivision(adminModel.getNacHourDiv());
            }
            if (paramMdl.getNtp081KakuteiFlg() == -1) {
                paramMdl.setNtp081KakuteiFlg(adminModel.getNacKakuteiFlg());
            }
        }

        //日報タイトルカラーコメントの値を取得
        NtpColMsgDao colDao = new NtpColMsgDao(con);
        List <NtpColMsgModel> colList = colDao.select();
        for (NtpColMsgModel colModel : colList) {
            if (colModel.getNcmId() == GSConstNippou.BGCOLOR_BLUE) {
                paramMdl.setNtp081ColCmt1(NullDefault.getString(
                        paramMdl.getNtp081ColCmt1(), colModel.getNcmMsg()));
            } else if (colModel.getNcmId() == GSConstNippou.BGCOLOR_RED) {
                paramMdl.setNtp081ColCmt2(NullDefault.getString(
                        paramMdl.getNtp081ColCmt2(), colModel.getNcmMsg()));
            } else if (colModel.getNcmId() == GSConstNippou.BGCOLOR_GREEN) {
                paramMdl.setNtp081ColCmt3(NullDefault.getString(
                        paramMdl.getNtp081ColCmt3(), colModel.getNcmMsg()));
            } else if (colModel.getNcmId() == GSConstNippou.BGCOLOR_ORANGE) {
                paramMdl.setNtp081ColCmt4(NullDefault.getString(
                        paramMdl.getNtp081ColCmt4(), colModel.getNcmMsg()));
            } else if (colModel.getNcmId() == GSConstNippou.BGCOLOR_BLACK) {
                paramMdl.setNtp081ColCmt5(NullDefault.getString(
                        paramMdl.getNtp081ColCmt5(), colModel.getNcmMsg()));
            }
        }

        //日報見込み度コメントの値を取得
        NtpMikomidoMsgDao mikomidoDao = new NtpMikomidoMsgDao(con);
        ArrayList <NtpMikomidoMsgModel> mikomidoList = mikomidoDao.select();
        for (NtpMikomidoMsgModel mikomidoModel : mikomidoList) {
            if (mikomidoModel.getNmmId() == GSConstNippou.MIKOMI_10) {
                paramMdl.setNtp081MikomidoCmt1(NullDefault.getString(
                        paramMdl.getNtp081MikomidoCmt1(), mikomidoModel.getNmmMsg()));
            } else if (mikomidoModel.getNmmId() == GSConstNippou.MIKOMI_30) {
                paramMdl.setNtp081MikomidoCmt2(NullDefault.getString(
                        paramMdl.getNtp081MikomidoCmt2(), mikomidoModel.getNmmMsg()));
            } else if (mikomidoModel.getNmmId() == GSConstNippou.MIKOMI_50) {
                paramMdl.setNtp081MikomidoCmt3(NullDefault.getString(
                        paramMdl.getNtp081MikomidoCmt3(), mikomidoModel.getNmmMsg()));
            } else if (mikomidoModel.getNmmId() == GSConstNippou.MIKOMI_70) {
                paramMdl.setNtp081MikomidoCmt4(NullDefault.getString(
                        paramMdl.getNtp081MikomidoCmt4(), mikomidoModel.getNmmMsg()));
            } else if (mikomidoModel.getNmmId() == GSConstNippou.MIKOMI_100) {
                paramMdl.setNtp081MikomidoCmt5(NullDefault.getString(
                        paramMdl.getNtp081MikomidoCmt5(), mikomidoModel.getNmmMsg()));
            }
        }

        //初期値セット
        if (paramMdl.getNtp081KyoyuFlg() == -1) {
            paramMdl.setNtp081KyoyuFlg(GSConstNippou.CRANGE_SHARE_ALL);
        }
        if (paramMdl.getNtp081HourDivision() == -1) {
            paramMdl.setNtp081HourDivision(GSConstNippou.DF_HOUR_DIVISION);
        }
        if (paramMdl.getNtp081KakuteiFlg() == -1) {
            paramMdl.setNtp081KakuteiFlg(GSConstNippou.KAKUTEI_INPUT_REQUIRED);
        }
    }

    /**
     * <br>[機  能] 設定された管理者設定情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp081ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setAconfSetting(Ntp081ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        boolean commitFlg = false;
        try {
            //管理者設定の更新用モデル取得
            NtpAdmConfModel adminModel = __getNtpAdmConfModel(paramMdl, umodel, con);

            //管理者設定更新
            NtpAdmConfDao dao = new NtpAdmConfDao(con);
            int count = dao.update(adminModel);
            if (count <= 0) {
                //レコードがない場合は作成
                dao.insert(adminModel);
            }

            //
            for (int i = GSConstNippou.BGCOLOR_BLUE; i <= GSConstNippou.BGCOLOR_BLACK; i++) {
                //タイトルカラーコメントの更新用モデル取得
                NtpColMsgModel colModel = __getNtpColMsgModel(paramMdl, umodel, con, i);
                //タイトルカラーコメント更新
                NtpColMsgDao colDao = new NtpColMsgDao(con);
                count = colDao.update(colModel);
                if (count <= 0) {
                    //レコードがない場合は作成
                    colDao.insert(colModel);
                }
            }

            for (int j = GSConstNippou.MIKOMI_10; j <= GSConstNippou.MIKOMI_100; j++) {
                //見込み度コメントの更新用モデル取得
                NtpMikomidoMsgModel mikomidoModel
                   = __getNtpMikomidoMsgModel(paramMdl, umodel, con, j);
                //見込み度コメント更新
                NtpMikomidoMsgDao mikomidoDao = new NtpMikomidoMsgDao(con);
                count = mikomidoDao.update(mikomidoModel);
                if (count <= 0) {
                    //レコードがない場合は作成
                    mikomidoDao.insert(mikomidoModel);
                }
            }

            commitFlg = true;
        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
    }

    /**
     * <br>[機  能] 管理者設定情報の更新用Modelを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp081ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @return 管理者設定情報の更新用Model
     */
    public NtpAdmConfModel __getNtpAdmConfModel(
            Ntp081ParamModel paramMdl, BaseUserModel umodel, Connection con)
    throws SQLException {

        NtpCommonBiz ncbiz = new NtpCommonBiz(con, reqMdl_);

        //管理者設定より設定情報を取得。なければデフォルト値とする。
        NtpAdmConfModel model = ncbiz.getAdminConfiModel(con);

        //デフォルト値をセットする。
        if (model == null) {
            model = new NtpAdmConfModel();
            model.setNacAtdelFlg(GSConstNippou.AUTO_DELETE_OFF);
            model.setNacAtdelY(-1);
            model.setNacAtdelM(-1);
            model.setNacAuid(umodel.getUsrsid());
            model.setNacAdate(new UDate());
        }

        //更新する値をセットする。
        model.setNacCrange(paramMdl.getNtp081KyoyuFlg());
        model.setNacHourDiv(paramMdl.getNtp081HourDivision());
        model.setNacKakuteiFlg(paramMdl.getNtp081KakuteiFlg());
        model.setNacEuid(umodel.getUsrsid());
        model.setNacEdate(new UDate());

        return model;
    }

    /**
     * <br>[機  能] 管理者設定情報の更新用Modelを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp081ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @param bgColor カラーコード
     * @throws SQLException SQL実行エラー
     * @return 管理者設定情報の更新用Model
     */
    public NtpColMsgModel __getNtpColMsgModel(
            Ntp081ParamModel paramMdl, BaseUserModel umodel, Connection con, int bgColor)
    throws SQLException {

        //管理者設定より設定情報を取得。なければデフォルト値とする。
        NtpColMsgDao dao = new NtpColMsgDao(con);
        NtpColMsgModel model = dao.select(bgColor);

        //デフォルト値をセットする。
        if (model == null) {
            model = new NtpColMsgModel();
            model.setNcmId(bgColor);
            model.setNcmAuid(umodel.getUsrsid());
            model.setNcmAdate(new UDate());
        }

        //更新する値をセットする。
        if (bgColor == GSConstNippou.BGCOLOR_BLUE) {
            model.setNcmMsg(paramMdl.getNtp081ColCmt1());
        } else if (bgColor == GSConstNippou.BGCOLOR_RED) {
            model.setNcmMsg(paramMdl.getNtp081ColCmt2());
        } else if (bgColor == GSConstNippou.BGCOLOR_GREEN) {
            model.setNcmMsg(paramMdl.getNtp081ColCmt3());
        } else if (bgColor == GSConstNippou.BGCOLOR_ORANGE) {
            model.setNcmMsg(paramMdl.getNtp081ColCmt4());
        } else if (bgColor == GSConstNippou.BGCOLOR_BLACK) {
            model.setNcmMsg(paramMdl.getNtp081ColCmt5());
        }
        model.setNcmEuid(umodel.getUsrsid());
        model.setNcmEdate(new UDate());

        return model;
    }

    /**
     * <br>[機  能] 管理者設定情報の更新用Modelを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp081ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @param mikomido 見込み度
     * @throws SQLException SQL実行エラー
     * @return 管理者設定情報の更新用Model
     */
    public NtpMikomidoMsgModel __getNtpMikomidoMsgModel(
            Ntp081ParamModel paramMdl, BaseUserModel umodel, Connection con, int mikomido)
    throws SQLException {

        //管理者設定より設定情報を取得。なければデフォルト値とする。
        NtpMikomidoMsgDao dao = new NtpMikomidoMsgDao(con);
        NtpMikomidoMsgModel model = dao.select(mikomido);

        //デフォルト値をセットする。
        if (model == null) {
            model = new NtpMikomidoMsgModel();
            model.setNmmId(mikomido);
            model.setNmmAuid(umodel.getUsrsid());
            model.setNmmAdate(new UDate());
        }

        //更新する値をセットする。
        if (mikomido == GSConstNippou.MIKOMI_10) {
            model.setNmmMsg(paramMdl.getNtp081MikomidoCmt1());
        } else if (mikomido == GSConstNippou.MIKOMI_30) {
            model.setNmmMsg(paramMdl.getNtp081MikomidoCmt2());
        } else if (mikomido == GSConstNippou.MIKOMI_50) {
            model.setNmmMsg(paramMdl.getNtp081MikomidoCmt3());
        } else if (mikomido == GSConstNippou.MIKOMI_70) {
            model.setNmmMsg(paramMdl.getNtp081MikomidoCmt4());
        } else if (mikomido == GSConstNippou.MIKOMI_100) {
            model.setNmmMsg(paramMdl.getNtp081MikomidoCmt5());
        }
        model.setNmmEuid(umodel.getUsrsid());
        model.setNmmEdate(new UDate());

        return model;
    }
}