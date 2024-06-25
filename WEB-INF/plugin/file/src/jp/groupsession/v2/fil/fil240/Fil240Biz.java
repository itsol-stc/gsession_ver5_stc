package jp.groupsession.v2.fil.fil240;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileCallDataDao;
import jp.groupsession.v2.fil.fil010.FileLinkSimpleModel;

/**
 * 更新通知一覧で使用するビジネスロジッククラス
 * @author JTS
 */
public class Fil240Biz {

    /** Logging インスタンス */
//    private static Log log__ = LogFactory.getLog(Fil240Biz.class);
    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Fil240Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl Fil240ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Fil240ParamModel paramMdl,
            Connection con) throws SQLException {

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //更新通知情報を取得
        paramMdl.setCallList(getCallInfoList(sessionUsrSid, paramMdl));
    }

    /**
     * 更新通知一覧を取得します
     * @param userSid ユーザSID
     * @param paramMdl Fil240ParamModel
     * @return 更新通知一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<FileLinkSimpleModel> getCallInfoList(
            int userSid, Fil240ParamModel paramMdl)
    throws SQLException {

        CommonBiz  commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con__, reqMdl__.getSmodel(),
                                                    GSConstFile.PLUGIN_ID_FILE);

        ArrayList<FileLinkSimpleModel> ret = new ArrayList<FileLinkSimpleModel>();
        FileCallDataDao dao = new FileCallDataDao(con__);
        Fil240Dao fil240Dao = new Fil240Dao(con__);

        //全データ件数
        int maxCount = dao.getUpdateCallDataCnt(userSid, isAdmin);
        if (maxCount == 0) {
            return null;
        }

        int limit = GSConstFile.CALL_LIST_DSP_CNT;

        //現在ページ、スタート行
        int nowPage = paramMdl.getFil240PageNum();
        int offset = PageUtil.getRowNumber(nowPage, limit);
        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < offset) {
            nowPage = maxPageNum;
            offset = maxPageStartRow;
        }

        //更新通知情報を取得
        ArrayList<Fil240Model> updateList =
                fil240Dao.getUpdateCallData(userSid, isAdmin, offset, limit);

        //ページング
        paramMdl.setFil240PageNum(nowPage);
        paramMdl.setFil240Slt_page1(nowPage);
        paramMdl.setFil240Slt_page2(nowPage);
        paramMdl.setFil240PageLabel(PageUtil.createPageOptions(maxCount, limit));

        FilCommonBiz filCmnBiz = new FilCommonBiz(reqMdl__, con__);
        FileLinkSimpleModel dspModel = null;
        String path = "";
        for (Fil240Model bean : updateList) {
            path = filCmnBiz.getDirctoryPath(bean.getFdrSid());
            dspModel = new FileLinkSimpleModel();
            dspModel.setDirectoryFullPathName(path);
            dspModel.setDirectoryKbn(bean.getFdrKbn());
            dspModel.setDirectoryName(bean.getFdrName());
            dspModel.setCabinetSid(bean.getFcbSid());
            dspModel.setDirectorySid(bean.getFdrSid());
            dspModel.setFfrUpCmt(StringUtilHtml.transToHTmlPlusAmparsant(bean.getFfrUpCmt()));
            dspModel.setDirectoryUpdateStr(
                    UDateUtil.getSlashYYMD(bean.getFdrEdate())
                    + " "
                    + UDateUtil.getSeparateHMS(bean.getFdrEdate()));
            dspModel.setFcbMark(bean.getFcbMark());
            dspModel.setEditUsrName(bean.getEditUsrName());
            dspModel.setBinSid(bean.getBinSid());
            dspModel.setEditUsrJkbn(bean.getEditUsrJkbn());
            dspModel.setEditUsrUkoFlg(bean.getEditUsrUkoFlg());
            ret.add(dspModel);
        }
        return ret;
    }
}
