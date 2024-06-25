package jp.groupsession.v2.prj.prj130;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.dao.PrjPrjdataTmpDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] プロジェクト管理 プロジェクトテンプレート画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj130Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj130Biz.class);

    /** ヘルプモード 個人テンプレート*/
    public static final String HELP_MODE_TMP_KOJIN = "0";
    /** ヘルプモード 共有テンプレート*/
    public static final String HELP_MODE_TMP_KYOYU = "1";
    /** ヘルプモード テンプレート選択*/
    public static final String HELP_MODE_TMP_SELECT = "2";

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Prj130Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Prj130ParamMdl
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void setTmpList(Prj130ParamModel paramMdl, int userSid) throws SQLException {

        PrjPrjdataTmpDao dataTmpDao = new PrjPrjdataTmpDao(con__);

        int tmpMode = paramMdl.getPrjTmpMode();

        log__.debug("個人= " + GSConstProject.MODE_TMP_KOJIN
                + "　共有＝" + GSConstProject.MODE_TMP_KYOYU
                + "　選択＝" + GSConstProject.MODE_TMP_SELECT);
        //テンプレート表示モード = 個人テンプレート
        if (tmpMode == GSConstProject.MODE_TMP_KOJIN) {

            log__.debug("個人テンプレートモード");
            paramMdl.setPrj130cmdMode(HELP_MODE_TMP_KOJIN);
            paramMdl.setPrj130TmpKojinList(
                    dataTmpDao.selectTemlateList(
                            GSConstProject.PRT_KBN_KOJIN, userSid));

        //テンプレート表示モード = 共有テンプレート
        } else if (tmpMode == GSConstProject.MODE_TMP_KYOYU) {

            log__.debug("共有テンプレートモード");
            paramMdl.setPrj130cmdMode(HELP_MODE_TMP_KYOYU);
            paramMdl.setPrj130TmpKyoyuList(
                    dataTmpDao.selectTemlateList(
                            GSConstProject.PRT_KBN_KYOYU, userSid));

        //テンプレート表示モード = テンプレート選択
        } else if (tmpMode == GSConstProject.MODE_TMP_SELECT) {

            log__.debug("テンプレート選択モード");
            paramMdl.setPrj130cmdMode(HELP_MODE_TMP_SELECT);
            paramMdl.setPrj130TmpKojinList(
                    dataTmpDao.selectTemlateList(
                            GSConstProject.PRT_KBN_KOJIN, userSid));

            paramMdl.setPrj130TmpKyoyuList(
                    dataTmpDao.selectTemlateList(
                            GSConstProject.PRT_KBN_KYOYU, userSid));
        }
    }
}