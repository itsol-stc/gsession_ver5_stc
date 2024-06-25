package jp.groupsession.v2.prj.prj090;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.dao.PrjUserConfDao;
import jp.groupsession.v2.prj.model.PrjUserConfModel;

/**
 * <br>[機  能] プロジェクト管理 個人設定 表示件数設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj090Biz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Prj090Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj090ParamModel
     * @param usid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Prj090ParamModel paramMdl, int usid) throws SQLException {

        //個人設定情報を取得する
        PrjUserConfDao pucDao = new PrjUserConfDao(con__);
        PrjUserConfModel pucMdl = pucDao.select(usid);

        if (pucMdl != null) {
            paramMdl.setPrj090prjViewCnt(pucMdl.getPucPrjCnt());
            paramMdl.setPrj090todoViewCnt(pucMdl.getPucTodoCnt());
            paramMdl.setPrj090SchDsp(pucMdl.getPucSchKbn());
        }

        //表示件数コンボをセット
        paramMdl.setCntList(PrjCommonBiz.getDspCntLavel());

    }

    /**
     * <br>[機  能] 表示件数の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Prj090ParamModel
     * @param usid ユーザSID
     * @throws SQLException SQL実行時例外
     * @return addEditFlg 登録モード(0:登録 1:編集)
     */
    public int update(Prj090ParamModel paramMdl, int usid) throws SQLException {

        UDate now = new UDate();
        int addEditFlg = 1;

        PrjUserConfModel pucMdl = new PrjUserConfModel();
        pucMdl.setUsrSid(usid);
        pucMdl.setPucPrjCnt(paramMdl.getPrj090prjViewCnt());
        pucMdl.setPucTodoCnt(paramMdl.getPrj090todoViewCnt());
        pucMdl.setPucSchKbn(paramMdl.getPrj090SchDsp());
        pucMdl.setPucEuid(usid);
        pucMdl.setPucEdate(now);

        PrjUserConfDao pucDao = new PrjUserConfDao(con__);
        int updateCnt = pucDao.update(pucMdl);

        //更新件数が0件の場合、プロジェクト個人設定の新規登録を行う
        if (updateCnt == 0) {
            pucMdl.setPucAuid(usid);
            pucMdl.setPucAdate(now);
            pucMdl.setPucTodoDate(GSConstProject.DATE_THE_PAST);
            pucMdl.setPucTodoProject(GSConstProject.PROJECT_ALL);
            pucMdl.setPucTodoStatus(GSConstProject.STATUS_YOTEI_AND_MIKAN);
            pucMdl.setPucPrjProject(GSConstProject.PRJ_MEMBER_NOT_END);
            pucMdl.setPucMainDate(GSConstProject.DATE_THE_PAST);
            pucMdl.setPucMainStatus(GSConstProject.STATUS_YOTEI_AND_MIKAN);
            pucMdl.setPucMainMember(GSConstProject.MEMBER_ALL);
            pucMdl.setPucDefDsp(GSConstProject.DSP_TODO);
            pucMdl.setPucSchKbn(GSConstProject.DSP_TODO_SHOW);
            pucMdl.setPucTodoDsp(GSConstProject.DSP_TODO_EASY);
            pucDao.insert(pucMdl);
            addEditFlg = 0;
        }
        pucDao.updatePrjSch(pucMdl);

        return addEditFlg;
    }

}