package jp.groupsession.v2.fil.fil210;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileAconfDao;
import jp.groupsession.v2.fil.dao.FileCrtConfDao;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.fil.model.FileCrtConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 基本設定画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil210Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil210Biz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Fil210Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210ParamModel
     * @param req リクエスト
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Fil210ParamModel paramMdl, HttpServletRequest req)
    throws SQLException {

        log__.debug("fil210Biz Start");

        //DB登録値を設定する。
        if (paramMdl.getFil210CrtKbn() == null) {
            __setData(paramMdl);
        }

        //画面表示項目を設定する。
        __setDsp(paramMdl, req);


    }

    /**
     * <br>[機  能] 画面表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210ParamModel
     * @param req リクエスト
     * @throws SQLException SQL実行例外
     */
    private void __setDsp(Fil210ParamModel paramMdl, HttpServletRequest req) throws SQLException {

        //ファイルのサイズ制限コンボ
        __setFileSizeCombo(paramMdl);

        //削除したファイルを保存する期間コンボ
        __setSaveDaysCombo(paramMdl, req);
    }

    /**
     * <br>[機  能] DBからデータを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210ParamModel
     * @throws SQLException SQL実行例外
     */
    private void __setData(Fil210ParamModel paramMdl) throws SQLException {

        FileAconfDao aconfDao = new FileAconfDao(con__);
        FileAconfModel aconf = aconfDao.select();
        if (aconf == null) {
            __setDefaut(paramMdl);
            return;
        }

        paramMdl.setFil210CrtKbn(String.valueOf(aconf.getFacCrtKbn()));
        paramMdl.setFil210FileSize(String.valueOf(aconf.getFacFileSize()));
        paramMdl.setFil210SaveDays(String.valueOf(aconf.getFacSaveDays()));
        paramMdl.setFil210LockKbn(String.valueOf(aconf.getFacLockKbn()));
        paramMdl.setFil210VerKbn(String.valueOf(aconf.getFacVerKbn()));

        int usrKbn = 0;

        if (aconf.getFacCrtKbn() == GSConstFile.CREATE_CABINET_PERMIT_GROUP) {
            usrKbn = GSConstFile.USER_KBN_GROUP;
        } else if (aconf.getFacCrtKbn() == GSConstFile.CREATE_CABINET_PERMIT_USER) {
            usrKbn = GSConstFile.USER_KBN_USER;
        } else {
            return;
        }

        FileCrtConfDao crtConfDao = new FileCrtConfDao(con__);
        List <FileCrtConfModel> crtConfList = crtConfDao.select(usrKbn);

        if (crtConfList == null || crtConfList.size() < 1) {
            return;
        }

        ArrayList<String> userSidList = new ArrayList<String>();
        for (FileCrtConfModel model : crtConfList) {
            userSidList.add(String.valueOf(model.getUsrSid()));
        }
        if (usrKbn == GSConstFile.USER_KBN_USER) {
            paramMdl.setFil210SvUsers(
                    (String[]) userSidList.toArray(new String[userSidList.size()]));
        } else {
            paramMdl.setFil210SvGroups(
                    (String[]) userSidList.toArray(new String[userSidList.size()]));
        }

    }

    /**
     * <br>[機  能] ファイルのサイズ制限コンボを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210ParamModel
     */
    private void __setDefaut(Fil210ParamModel paramMdl) {

        paramMdl.setFil210CrtKbn(String.valueOf(GSConstFile.CREATE_CABINET_PERMIT_ADMIN));
        paramMdl.setFil210FileSize(String.valueOf(GSConstFile.FILE_SIZE_DEFAULT));
        paramMdl.setFil210SaveDays(String.valueOf(GSConstFile.FILE_SAVE_DAYS_NO));
        paramMdl.setFil210LockKbn(String.valueOf(GSConstFile.LOCK_KBN_OFF));
        paramMdl.setFil210VerKbn(String.valueOf(GSConstFile.VERSION_KBN_OFF));
    }

    /**
     * <br>[機  能] ファイルのサイズ制限コンボを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210ParamModel
     */
    private void __setFileSizeCombo(Fil210ParamModel paramMdl) {

        ArrayList<LabelValueBean> fileSizeLabelList = new ArrayList<LabelValueBean>();
        LabelValueBean label = null;
        for (int i = 10; i <= 100; i = i + 10) {
            label = new LabelValueBean(String.valueOf(i + "MB"), String.valueOf(i));
            fileSizeLabelList.add(label);
        }

        paramMdl.setFil210FileSizeList(fileSizeLabelList);
    }

    /**
     * <br>[機  能] 削除ファイル保存期間コンボを設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil210ParamModel
     * @param req リクエスト
     */
    private void __setSaveDaysCombo(Fil210ParamModel paramMdl, HttpServletRequest req) {

        GsMessage gsMsg = new GsMessage();
        String textHozonsinai = gsMsg.getMessage(req, "main.man160.4");
        String textDay = gsMsg.getMessage(req, "cmn.days2");

        ArrayList<LabelValueBean> saveDaysList = new ArrayList<LabelValueBean>();
        LabelValueBean label = null;

        label = new LabelValueBean(textHozonsinai, String.valueOf(-1));
        saveDaysList.add(label);

        for (int i = 1; i <= 10; i++) {
            label = new LabelValueBean(String.valueOf(i + textDay), String.valueOf(i));
            saveDaysList.add(label);
        }

        paramMdl.setFil210SaveDaysList(saveDaysList);
    }
}
