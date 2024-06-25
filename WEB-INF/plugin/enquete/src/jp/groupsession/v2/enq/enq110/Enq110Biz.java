package jp.groupsession.v2.enq.enq110;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.dao.EnqAnsMainDao;
import jp.groupsession.v2.enq.dao.EnqAnsSubDao;
import jp.groupsession.v2.enq.dao.EnqMainDao;
import jp.groupsession.v2.enq.dao.EnqQuestionDataDao;
import jp.groupsession.v2.enq.dao.EnqTypeDao;
import jp.groupsession.v2.enq.enq210.Enq210Biz;
import jp.groupsession.v2.enq.enq210.Enq210Form;
import jp.groupsession.v2.enq.enq210.Enq210QueModel;
import jp.groupsession.v2.enq.enq210.Enq210QueSubModel;
import jp.groupsession.v2.enq.model.EnqAnsMainModel;
import jp.groupsession.v2.enq.model.EnqAnsSubModel;
import jp.groupsession.v2.enq.model.EnqMainListModel;
import jp.groupsession.v2.enq.model.EnqMainModel;
import jp.groupsession.v2.enq.model.EnqQuestionDataModel;
import jp.groupsession.v2.enq.model.EnqSubListModel;
import jp.groupsession.v2.enq.model.EnqTypeModel;

/**
 * <br>[機  能] アンケート 回答画面のビジネスクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq110Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq110Biz.class);
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /** コネクション */
    private Connection con__ = null;
    /** アプリケーションルートパス */
    private String appRoot__ = null;
    /** テンポラリディレクトリパス */
    private String tempDir__ = null;

    /**
     * <p>デフォルトコンストラクタ
     */
    public Enq110Biz() {
    }

    /**
     * <p>コンストラクタ
     * @param reqMdl リクエストモデル
     * @param con コネクション
     */
    public Enq110Biz(RequestModel reqMdl, Connection con) {
        reqMdl__ = reqMdl;
        con__ = con;
    }

    /**
     * <p>コンストラクタ
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param appRoot アプリケーションルートパス
     * @param tempDir テンポラリディレクトリ
     */
    public Enq110Biz(RequestModel reqMdl, Connection con, String appRoot, String tempDir) {
        reqMdl__ = reqMdl;
        con__ = con;
        appRoot__ = appRoot;
        tempDir__ = tempDir;
    }

    /**
     * <br>[機  能] アンケート回答可能フラグを取得する
     * <br>[解  説]
     * <br>[備  考] 回答区分は、Enq110Constで定義した値を使用しています。
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param enqSid アンケートSID
     * @return 回答区分
     * @throws Exception 実行例外
     */
    public int canAnsEnquete(RequestModel reqMdl, Connection con, long enqSid)
            throws Exception {

        log__.debug("アンケート回答可能権限取得処理");

        int ret = -1;
        try {
            // セッションユーザ情報を取得する
            BaseUserModel usModel = reqMdl.getSmodel();
            int sessionUsrSid = usModel.getUsrsid();

            // アンケートに回答可能かどうか判別
            EnqAnsMainModel mdl = new EnqAnsMainModel();
            EnqAnsMainDao dao = new EnqAnsMainDao(con);
            mdl = dao.select(enqSid, sessionUsrSid);

            // 回答権限無し
            if (mdl == null) {
                return Enq110Const.ANS_KBN_WITHOUT_AUTHORITY;
            }

            UDate now = new UDate();
            now.setZeroHhMmSs();
            EnqMainDao emDao = new EnqMainDao(con);
            EnqMainModel enqMdl = emDao.select(enqSid);
            int diffKigen = now.compareDateYMD(enqMdl.getEmnResEnd());

            // 回答フラグのチェック
            if (mdl.getEamStsKbn() == GSConstEnquete.EAM_STS_KBN_YES) {
                if (diffKigen < 0) {
                    // 回答済み、期限切れ
                    ret = Enq110Const.ANS_KBN_ANSED_NOT_CURRENT;
                } else {
                    // 回答済み、期限内
                    ret = Enq110Const.ANS_KBN_ANSED_CURRENT;
                }
            } else {
                if (diffKigen < 0) {
                    // 未回答、期限切れ
                    ret = Enq110Const.ANS_KBN_UNANS_NOT_CURRENT;
                } else {
                    // 未回答、期限内
                    ret = Enq110Const.ANS_KBN_UNANS_CURRENT;
                }
            }

        } catch (Exception e) {
            log__.error("アンケート回答_基本の取得に失敗しました。" + e);
            throw e;
        }
        return ret;
    }

    /**
     * <br>[機  能] アンケートを編集可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param editMode 編集モードフラグ
     * @param enqSid アンケートSID
     * @return true: 編集可能 false: 編集不可
     * @throws SQLException SQL実行時例外
     */
    public boolean canEditEnquete(Connection con, int editMode, long enqSid)
    throws SQLException {

        if (editMode != GSConstEnquete.EDITMODE_EDIT) {
            return true;
        }

        EnqMainDao enqMainDao = new EnqMainDao(con);
        return enqMainDao.select(enqSid) != null;
    }

    /**
     * <br>[機  能] アンケート登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110Model パラメータモデル
     * @param mtCon 採番コントローラ
     * @throws Exception 例外
     */
    public void doCommit(Enq110ParamModel enq110Model,
                         MlCountMtController mtCon) throws Exception {

        log__.debug("アンケート登録処理");

        boolean commit = false;
        con__.setAutoCommit(false);

        try {

            Enq210Biz biz210 = new Enq210Biz();
            // 草稿の場合、配信に切り替える
            if (enq110Model.getEnq210editMode() == Enq210Form.EDITMODE_DRAFT) {
                enq110Model.setEnq210editMode(Enq210Form.EDITMODE_NORMAL);
            }
            biz210.entryEnqueteData(enq110Model, reqMdl__, con__, mtCon, appRoot__, tempDir__);

            con__.commit();

        } catch (SQLException e) {

        } finally {
            if (!commit) {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110Model パラメータモデル
     * @throws Exception 例外
     */
    public void setInitData(Enq110ParamModel enq110Model) throws Exception {

        log__.debug("初期表示情報取得");

        EnqCommonBiz enqBiz = new EnqCommonBiz();

        // 年月日コンボの取得
        enq110Model.setEnq110YearLabel(enqBiz.getYearLabels(reqMdl__));
        enq110Model.setEnq110MonthLabel(enqBiz.getMonthLabels(reqMdl__));
        enq110Model.setEnq110DayLabel(enqBiz.getDayLabels(reqMdl__));

        // 遷移元画面の判定
        if (enq110Model.getEnq110DspMode() == Enq110Const.DSP_MODE_PREVIEW) {
            // アンケート作成画面から(Enq210)
            __setEnqInputData(enq110Model);
        } else {
            // 一覧画面から(Enq010)
            __setEnqData(enq110Model);
        }

    }

    /**
     * <br>[機  能] 入力値をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110Model パラメータモデル
     * @param inputModel 入力データ
     * @throws Exception 例外
     */
    public void setInputData(Enq110ParamModel enq110Model, List<EnqMainListModel> inputModel)
    throws Exception {

        log__.debug("入力値をセットする");

        List<EnqMainListModel> wkList = enq110Model.getEnq110QueListToList();
        List<EnqMainListModel> resultList = new ArrayList<EnqMainListModel>();
        EnqMainListModel wkQueMdl = null;
        int index = 0;

        if (wkList.size() != inputModel.size()) {
            return;
        }

        // 入力データをセットする
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        for (EnqMainListModel bean : inputModel) {
            wkQueMdl = new EnqMainListModel();
            wkQueMdl = wkList.get(index);
            wkQueMdl.setEqmAnsText(bean.getEqmAnsText());
            wkQueMdl.setEqmAnsTextarea(bean.getEqmAnsTextarea());
            wkQueMdl.setEqmAnsNum(bean.getEqmAnsNum());
            wkQueMdl.setEqmSelectAnsYear(bean.getEqmSelectAnsYear());
            wkQueMdl.setEqmSelectAnsMonth(bean.getEqmSelectAnsMonth());
            wkQueMdl.setEqmSelectAnsDay(bean.getEqmSelectAnsDay());
            dateBiz.setDateParam(wkQueMdl, "eqmSelectAnsDateInput",
                "eqmSelectAnsYear", "eqmSelectAnsMonth", "eqmSelectAnsDay", null);
            wkQueMdl.setEqmSelRbn(bean.getEqmSelRbn());
            wkQueMdl.setEqmSelChk(bean.getEqmSelChk());
            wkQueMdl.setEqmSelOther(bean.getEqmSelOther());
            resultList.add(wkQueMdl);
            index++;
        }
        enq110Model.setEnq110QueListForm(resultList);
    }

    /**
     * <br>[機  能] アンケートの入力情報設定
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110Model パラメータモデル
     * @throws Exception 例外
     */
    private void __setEnqInputData(Enq110ParamModel enq110Model) throws Exception {

        // アンケート基本情報設定（プレビュー）
        __setEnqMainInputData(enq110Model);

        // アンケート設問情報設定(プレビュー）
        __setEnqQuestionInputData(enq110Model);
    }

    /**
     * <br>[機  能] アンケート情報設定
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110Model パラメータモデル
     * @throws Exception 例外
     */
    private void __setEnqData(Enq110ParamModel enq110Model) throws Exception {

        // アンケート基本情報設定
        __setEnqMainData(enq110Model);

        // アンケート設問情報設定
        if (enq110Model.getEnq110InitMode() == Enq110Const.ANS_KBN_ANSED_CURRENT) {
            // 回答済
            __setEnqQuestionEditData(enq110Model);
        } else {
            // 未回答
            __setEnqQuestionData(enq110Model);
        }

    }

    /**
     * <br>[機  能] アンケート基本情報の設定（プレビュー）
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110Model パラメータモデル
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイル操作時に発生する例外
     * @throws Exception 例外
     */
    private void __setEnqMainInputData(Enq110ParamModel enq110Model)
        throws SQLException, TempFileException, Exception {

        log__.debug("アンケート基本情報の設定処理（プレビュー）");

        EnqCommonBiz enqBiz = new EnqCommonBiz();

        // 取得値を画面値にセット
        enq110Model.setEnq110TypeName(__getTypeName(enq110Model.getEnq210Syurui()));
        enq110Model.setEnq110Title(enq110Model.getEnq210Title());
        enq110Model.setEnq110PriKbn(enq110Model.getEnq210Juuyou());
        enq110Model.setEnq110Desc(
                StringUtilHtml.removeIllegalTag(enq110Model.getEnq210Desc()));
        enq110Model.setEnq110Desc(StringUtilHtml.removeIllegalTag(enq110Model.getEnq110Desc()));
        enq110Model.setEnq210Desc(enq110Model.getEnq110Desc());
        enq110Model.setEnq110AttachKbn(enq110Model.getEnq210AttachKbn());
        enq110Model.setEnq110AttachPos(enq110Model.getEnq210AttachPos());
        if (enq110Model.getEnq210AttachKbn() == GSConstEnquete.TEMP_FILE
         || enq110Model.getEnq210AttachKbn() == GSConstEnquete.TEMP_IMAGE) {
            Enq210Biz biz210 = new Enq210Biz();
            String enqTempDir = biz210.getEnqTempDir(reqMdl__);
            CommonBiz cmnBiz = new CommonBiz();
            List<LabelValueBean> fileList = cmnBiz.getTempFileLabelList(enqTempDir);
            if (fileList != null && !fileList.isEmpty()) {
                enq110Model.setEnq110AttachId(fileList.get(0).getValue());
                enq110Model.setEnq110AttachName(fileList.get(0).getLabel());
                enq110Model.setEnq110AttachSize(
                        cmnBiz.getByteSizeString(cmnBiz.getTempFileSize(enqTempDir)));
            }
        }
        enq110Model.setEnq110OpenStr(enqBiz.getStrDate(reqMdl__,
                                                       enq110Model.getEnq210FrYear(),
                                                       enq110Model.getEnq210FrMonth(),
                                                       enq110Model.getEnq210FrDay()));

        if (enq110Model.getEnq210ToKbn() == Enq210Form.TO_DATE_NOLIMIT) {
            enq110Model.setEnq110OpenEnd(null);
        } else {
            enq110Model.setEnq110OpenEnd(enqBiz.getStrDate(reqMdl__,
                                                           enq110Model.getEnq210ToYear(),
                                                           enq110Model.getEnq210ToMonth(),
                                                           enq110Model.getEnq210ToDay()));
        }

        enq110Model.setEnq110ResEnd(enqBiz.getStrDate(reqMdl__,
                                                      enq110Model.getEnq210AnsYear(),
                                                      enq110Model.getEnq210AnsMonth(),
                                                      enq110Model.getEnq210AnsDay()));
        enq110Model.setEnq110AnsPubStr(enqBiz.getStrDate(reqMdl__,
                                                    enq110Model.getEnq210AnsPubFrYear(),
                                                    enq110Model.getEnq210AnsPubFrMonth(),
                                                    enq110Model.getEnq210AnsPubFrDay()));
        enq110Model.setEnq110Anony(enq110Model.getEnq210Anony());
        enq110Model.setEnq110AnsOpen(enq110Model.getEnq210AnsOpen());

        String sendId = NullDefault.getString(enq110Model.getEnq210Send(), "");
        Enq210Biz biz210 = new Enq210Biz();
        enq110Model.setEnq110SendName(
                biz210.getSenderName(con__, reqMdl__, sendId));
        if (sendId.startsWith("G")) {
            enq110Model.setEnq110SendKbn(Enq110Const.SENDER_KBN_GROUP);
            sendId = sendId.substring(1);
        } else {
            enq110Model.setEnq110SendKbn(Enq110Const.SENDER_KBN_USER);
        }

        if (ValidateUtil.isNumber(sendId)) {
            enq110Model.setEnq110SendSid(Long.parseLong(sendId));
        }
    }

    /**
     * <br>[機  能] アンケート設問情報設定(プレビュー）
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110Model パラメータモデル
     * @throws SQLException SQL実行例外
     * @throws Exception 実行例外
     * @throws IOToolsException 設問情報一覧の読み込みに失敗
     */
    private void __setEnqQuestionInputData(Enq110ParamModel enq110Model)
            throws SQLException, IOToolsException, Exception {

        List<EnqMainListModel> list = new ArrayList<EnqMainListModel>();
        EnqMainListModel mainMdl = null;

        // 設問情報の入力値を取得
        Enq210Biz biz210 = new Enq210Biz();
        List<Enq210QueModel> queList = biz210.readQueList(reqMdl__);

        // 取得した入力値を、画面にセット
        for (Enq210QueModel bean : queList) {
            // main
            mainMdl = new EnqMainListModel();
            mainMdl = __getInputEnqMainList(bean);

            // main add
            list.add(mainMdl);
        }
        enq110Model.setEnq110QueListForm(list);
    }

    /**
     * <br>[機  能] 設問の入力情報設定処理
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 入力値のモデル
     * @return 設問表示用モデル
     * @throws Exception 実行例外
     */
    private EnqMainListModel __getInputEnqMainList(Enq210QueModel bean) throws Exception {

        EnqCommonBiz enqBiz = new EnqCommonBiz();

        EnqMainListModel mainMdl = new EnqMainListModel();
        mainMdl.setEmnSid(bean.getEnq210Sid());
        mainMdl.setEqmSeq(bean.getEnq210Seq());
        mainMdl.setEqmDspSec(bean.getEnq210DspOrder());
        mainMdl.setEqmQueSec(bean.getEnq210QueNo());
        mainMdl.setEqmQuestion(bean.getEnq210Question());
        mainMdl.setEqmQueKbn(bean.getEnq210QueKbn());
        mainMdl.setEqmRequire(bean.getEnq210Require());
        mainMdl.setEqmOther(bean.getEnq210OtherFlg());
        mainMdl.setEqmDesc(
                StringUtilHtml.removeIllegalTag(bean.getEnq210QueDesc()));
        mainMdl.setEqmAttachKbn(bean.getEnq210AttachKbn());
        if (bean.getEnq210AttachKbn() == GSConstEnquete.TEMP_FILE
         || bean.getEnq210AttachKbn() == GSConstEnquete.TEMP_IMAGE) {
            mainMdl.setEqmAttachDir(bean.getEnq210Id());
            Enq210Biz biz210 = new Enq210Biz();
            String tempDir = biz210.getQueSaveDir(reqMdl__)
                           + bean.getEnq210Id();
            CommonBiz cmnBiz = new CommonBiz();
            List<LabelValueBean> fileList = cmnBiz.getTempFileLabelList(tempDir);
            if (fileList != null && !fileList.isEmpty()) {
                mainMdl.setEqmAttachId(fileList.get(0).getValue());
                mainMdl.setEqmAttachName(fileList.get(0).getLabel());
                mainMdl.setEqmAttachSize(
                        cmnBiz.getByteSizeString(cmnBiz.getTempFileSize(tempDir)));
            }
        } else {
            mainMdl.setEqmUrl(bean.getEnq210Url());
            mainMdl.setEqmDspName(bean.getEnq210AttachName());
        }
        mainMdl.setEqmAttachPos(bean.getEnq210AttachPos());
        mainMdl.setEqmUnitNum(bean.getEnq210unitNum());
        mainMdl.setEqmLineKbn(bean.getEnq210LinePos());

        // 初期値のセット
        mainMdl.setEqmAnsText(bean.getEnq210initTxt());
        mainMdl.setEqmAnsTextarea(bean.getEnq210initTxt());
        if (bean.getEnq210initTxt() != null) {
            mainMdl.setEqmAnsNum(enqBiz.processNumber(bean.getEnq210initTxt()));
        } else {
            mainMdl.setEqmAnsNum(bean.getEnq210initTxt());
        }
        if (bean.getEnq210initDate() != null) {
            mainMdl.setEqmSelectAnsYear(bean.getEnq210initDate().getYear());
            mainMdl.setEqmSelectAnsMonth(bean.getEnq210initDate().getMonth());
            mainMdl.setEqmSelectAnsDay(bean.getEnq210initDate().getIntDay());
            mainMdl.setEqmSelectAnsDate(enqBiz.getStrDate(reqMdl__,
                                        bean.getEnq210initDate().getYear(),
                                        bean.getEnq210initDate().getMonth(),
                                        bean.getEnq210initDate().getIntDay()));
        }

        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        dateBiz.setDateParam(mainMdl, "eqmSelectAnsDateInput",
                "eqmSelectAnsYear", "eqmSelectAnsMonth", "eqmSelectAnsDay", null);

        // 入力範囲のセット
        if (bean.getEnq210rangeTxtFr() != null) {
            mainMdl.setEqmRngStrNum(
                    enqBiz.processNumber(bean.getEnq210rangeTxtFr()));
        } else {
            mainMdl.setEqmRngStrNum(bean.getEnq210rangeTxtFr());
        }
        if (bean.getEnq210rangeTxtTo() != null) {
            mainMdl.setEqmRngEndNum(
                    enqBiz.processNumber(bean.getEnq210rangeTxtTo()));
        } else {
            mainMdl.setEqmRngEndNum(bean.getEnq210rangeTxtTo());
        }


        if (bean.getEnq210rangeDateFr() != null) {
            mainMdl.setEqmRngStrDat(enqBiz.getStrDate(reqMdl__, bean.getEnq210rangeDateFr()));
        }
        if (bean.getEnq210rangeDateTo() != null) {
            mainMdl.setEqmRngEndDat(enqBiz.getStrDate(reqMdl__, bean.getEnq210rangeDateTo()));
        }

        // 選択系の選択肢をセット
        if ((bean.getQueSubList() != null && bean.getQueSubList().size() > 0)
         && (bean.getEnq210QueKbn() == GSConstEnquete.SYURUI_SINGLE
         || bean.getEnq210QueKbn() == GSConstEnquete.SYURUI_MULTIPLE)) {
            List<EnqSubListModel> slist = new ArrayList<EnqSubListModel>();
            EnqSubListModel subMdl = null;
            int index = 1;
            List<String> wkChkInitVal = new ArrayList<String>();
            // 選択肢のセット
            for (Enq210QueSubModel sbean : bean.getQueSubList()) {
                // 初期値のセット
                if (sbean.getEnqDefFlg() != null) {
                    if (bean.getEnq210QueKbn() == GSConstEnquete.SYURUI_SINGLE) {
                        mainMdl.setEqmSelRbn(String.valueOf(index));
                    } else {
                        wkChkInitVal.add(String.valueOf(index));
                    }
                }
                subMdl = new EnqSubListModel();
                subMdl.setEmnSid(bean.getEnq210Sid());
                subMdl.setEqmSeq(bean.getEnq210Seq());
                subMdl.setEqsSeq(index);
                subMdl.setEqsDspSec(sbean.getEnqDspSec());
                subMdl.setEqsDspName(sbean.getEnqDspName());
                slist.add(subMdl);
                index++;
            }
            mainMdl.setEqmSelChk((String[]) wkChkInitVal.toArray(new String[wkChkInitVal.size()]));

            // その他のセット
            if (bean.getEnq210OtherFlg() != GSConstEnquete.CHOICE_INIT_OFF) {
                subMdl = new EnqSubListModel();
                subMdl.setEmnSid(bean.getEnq210Sid());
                subMdl.setEqmSeq(bean.getEnq210Seq());
                subMdl.setEqsSeq(GSConstEnquete.CHOICE_KBN_OTHER);
                subMdl.setEqsDspSec(slist.size() + 1);
                slist.add(subMdl);
            }
            mainMdl.setEqmSubListForm(slist);
        }

        return mainMdl;
    }

    /**
     * <br>[機  能] アンケート基本情報の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110Model パラメータモデル
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイル操作時に発生する例外
     * @throws Exception 例外
     */
    private void __setEnqMainData(Enq110ParamModel enq110Model)
        throws SQLException, TempFileException, Exception {

        log__.debug("アンケート基本情報の設定処理");

        EnqMainModel model = new EnqMainModel();
        EnqMainDao dao = new EnqMainDao(con__);

        // アンケート基本情報の取得
        model = dao.select(enq110Model.getAnsEnqSid());

        // 取得値を画面値にセット
        enq110Model.setEnq110TypeName(__getTypeName(model.getEtpSid()));
        enq110Model.setEnq110Title(model.getEmnTitle());
        enq110Model.setEnq110PriKbn(model.getEmnPriKbn());
        enq110Model.setEnq110Desc(NullDefault.getString(model.getEmnDesc(), ""));
        enq110Model.setEnq110Desc(StringUtilHtml.removeIllegalTag(enq110Model.getEnq110Desc()));
        enq110Model.setEnq110AttachKbn(model.getEmnAttachKbn());
        enq110Model.setEnq110AttachPos(model.getEmnAttachPos());
        if (model.getEmnAttachKbn() == GSConstEnquete.TEMP_FILE
         || model.getEmnAttachKbn() == GSConstEnquete.TEMP_IMAGE) {
            CmnBinfModel wkBinMdl = __getTempFileMdl(model.getEmnAttachId());
            if (wkBinMdl != null) {
                CommonBiz cmnBiz = new CommonBiz();
                enq110Model.setEnq110AttachId(model.getEmnAttachId());
                enq110Model.setEnq110AttachName(wkBinMdl.getBinFileName());
                enq110Model.setEnq110AttachSize(
                        cmnBiz.getByteSizeString(wkBinMdl.getBinFileSize()));
            }
        }
        enq110Model.setEnq110OpenStr(createViewDateStr(reqMdl__, model.getEmnOpenStr()));
        enq110Model.setEnq110OpenEnd(createViewDateStr(reqMdl__, model.getEmnOpenEnd()));
        enq110Model.setEnq110ResEnd(createViewDateStr(reqMdl__, model.getEmnResEnd()));
        enq110Model.setEnq110AnsPubStr(createViewDateStr(reqMdl__, model.getEmnAnsPubStr()));
        enq110Model.setEnq110Anony(model.getEmnAnony());
        enq110Model.setEnq110AnsOpen(model.getEmnAnsOpen());
        __setSendName(enq110Model, (int) model.getEmnSendGrp(), (int) model.getEmnSendUsr());
        if (StringUtil.isNullZeroString(enq110Model.getEnq110queDate())) {
            enq110Model.setEnq110queDate(String.valueOf(model.getEmnEdate().getTime()));
        }
    }

    /**
     * <br>[機  能] 指定した日付の画面表示用文字列を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param date 日付
     * @return 日付文字列
     */
    public String createViewDateStr(RequestModel reqMdl, UDate date) {
        String dateStr = "";
        if (date != null) {
            EnqCommonBiz enqBiz = new EnqCommonBiz();
            dateStr = enqBiz.getStrDate(reqMdl, date);
            dateStr += "(" + date.getStrWeekJ(reqMdl) + ")";
        }

        return dateStr;
    }

    /**
     * <br>[機  能] 回答済アンケート設問情報の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110Model パラメータモデル
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイル操作時に発生する例外
     * @throws Exception 例外
     */
    private void __setEnqQuestionEditData(Enq110ParamModel enq110Model)
        throws SQLException, TempFileException, Exception {

        log__.debug("アンケート回答済情報の設定処理");

        ArrayList<EnqMainListModel> mMdl = new ArrayList<EnqMainListModel>();
        ArrayList<EnqSubListModel> sMdl = new ArrayList<EnqSubListModel>();
        EnqMainListModel mList = null;
        EnqSubListModel sList = null;
        EnqAnsSubModel wkAnsMdl = null;

        int wkEqmSeq = -9;              // 設問連番保持値
        boolean subQueAddFlg = false;   // 選択肢の情報をモデルにセットするフラグ

        // セッションユーザ取得
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();

        // 設問情報取得処理
        ArrayList<EnqQuestionDataModel> queMdl = __getEnqQuestionData(enq110Model.getAnsEnqSid());
        if (queMdl == null || queMdl.size() < 1) {
            return;
        }

        // 回答情報取得処理
        ArrayList<EnqAnsSubModel> ansMdl =
                __getEnqAnsedData(enq110Model.getAnsEnqSid(), sessionUsrSid);
        if (ansMdl == null) {
            return;
        }

        // 回答欄の作成処理
        for (EnqQuestionDataModel bean : queMdl) {

            // 設問と同じ回答データ取得
            if (bean.getEqmQueKbn() != GSConstEnquete.SYURUI_COMMENT) {
                for (int i = 0; i < ansMdl.size(); i++) {
                    if (bean.getEqmSeq() == ansMdl.get(i).getEqmSeq()
                     && bean.getEqsSeq() == ansMdl.get(i).getEqsSeq()) {
                        wkAnsMdl = ansMdl.get(i);
                        break;
                    }
                }
            }

            // 設問情報設定処理
            if (bean.getEqmSeq() != wkEqmSeq) {
                // 選択肢の情報をセットする
                if (subQueAddFlg) {
                    mList.setEqmSubListForm(sMdl);
                    subQueAddFlg = false;
                    sMdl = new ArrayList<EnqSubListModel>();
                }
                // 設問情報を表示用モデルにセットする
                if (wkEqmSeq != -9) {
                    mMdl.add(mList);
                }

                // 設問情報セット
                mList = new EnqMainListModel();
                mList = __getEnqAnsedList(bean, wkAnsMdl);
            }
            // 設問_選択肢の情報セット
            if (bean.getEqmQueKbn() == GSConstEnquete.SYURUI_SINGLE
             || bean.getEqmQueKbn() == GSConstEnquete.SYURUI_MULTIPLE) {
                sList = new EnqSubListModel();
                sList = __getEnqSubAnsedList(bean, mList, wkAnsMdl);
                sMdl.add(sList);
                subQueAddFlg = true;
            }
            // 設問連番の保持
            wkEqmSeq = bean.getEqmSeq();
        }

        // 設問_選択肢の情報をセットする
        if (subQueAddFlg) {
            mList.setEqmSubListForm(sMdl);
        }

        mMdl.add(mList);
        enq110Model.setEnq110QueListForm(mMdl);
    }

    /**
     * <br>[機  能] 未回答アンケート設問情報の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110Model パラメータモデル
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイル操作時に発生する例外
     * @throws Exception 例外
     */
    private void __setEnqQuestionData(Enq110ParamModel enq110Model)
        throws SQLException, TempFileException, Exception {

        log__.debug("アンケート設問情報の設定処理");

        ArrayList<EnqMainListModel> mMdl = new ArrayList<EnqMainListModel>();
        ArrayList<EnqSubListModel> sMdl = new ArrayList<EnqSubListModel>();
        EnqMainListModel mList = null;
        EnqSubListModel sList = null;

        int wkEqmSeq = -9;              // 設問連番保持値
        boolean subQueAddFlg = false;   // 選択肢の情報をモデルにセットするフラグ

        // 設問情報取得処理
        ArrayList<EnqQuestionDataModel> queMdl = __getEnqQuestionData(enq110Model.getAnsEnqSid());
        if (queMdl == null || queMdl.size() < 1) {
            return;
        }

        // 回答欄の作成処理
        for (EnqQuestionDataModel bean : queMdl) {

            // 設問情報設定処理
            if (bean.getEqmSeq() != wkEqmSeq) {
                // 選択肢の情報をセットする
                if (subQueAddFlg) {
                    mList.setEqmSubListForm(sMdl);
                    subQueAddFlg = false;
                    sMdl = new ArrayList<EnqSubListModel>();
                }
                // 設問情報を表示用モデルにセットする
                if (wkEqmSeq != -9) {
                    mMdl.add(mList);
                }

                // 設問情報セット
                mList = new EnqMainListModel();
                mList = __getEnqMainList(bean);
            }
            // 設問_選択肢の情報セット
            if (bean.getEqmQueKbn() == GSConstEnquete.SYURUI_SINGLE
             || bean.getEqmQueKbn() == GSConstEnquete.SYURUI_MULTIPLE) {
                sList = new EnqSubListModel();
                sList = __getEnqSubList(bean, mList);
                sMdl.add(sList);
                subQueAddFlg = true;
            }
            // 設問連番の保持
            wkEqmSeq = bean.getEqmSeq();
        }

        // 設問_選択肢の情報をセットする
        if (subQueAddFlg) {
            mList.setEqmSubListForm(sMdl);
        }

        mMdl.add(mList);
        enq110Model.setEnq110QueListForm(mMdl);
    }

    /**
     * <br>[機  能] 回答済アンケートの、設問情報取得処理
     * <br>[解  説]
     * <br>[備  考]
     * @param bean アンケートの設問・回答モデル
     * @param ansbean 回答データ
     * @return 設問モデル
     * @throws Exception 例外
     */
    private EnqMainListModel __getEnqAnsedList(EnqQuestionDataModel bean,
                                               EnqAnsSubModel ansbean) throws Exception {

        // 設問情報をセットする
        EnqMainListModel mList = new EnqMainListModel();
        mList = __setQueMainList(bean);

        // 回答欄の設定
        if (bean.getEqmQueKbn() == GSConstEnquete.SYURUI_DAY
         && ansbean.getEasAnsDat() != null) {
            mList.setEqmSelectAnsYear(ansbean.getEasAnsDat().getYear());
            mList.setEqmSelectAnsMonth(ansbean.getEasAnsDat().getMonth());
            mList.setEqmSelectAnsDay(ansbean.getEasAnsDat().getIntDay());
            DateTimePickerBiz dateBiz = new DateTimePickerBiz();
            dateBiz.setDateParam(mList, "eqmSelectAnsDateInput",
                    "eqmSelectAnsYear", "eqmSelectAnsMonth", "eqmSelectAnsDay", null);
        } else if (bean.getEqmQueKbn() == GSConstEnquete.SYURUI_TEXT) {
            mList.setEqmAnsText(ansbean.getEasAns());
        } else if (bean.getEqmQueKbn() == GSConstEnquete.SYURUI_TEXTAREA) {
            mList.setEqmAnsTextarea(ansbean.getEasAns());
        } else if (bean.getEqmQueKbn() == GSConstEnquete.SYURUI_INTEGER) {
            mList.setEqmAnsNum(ansbean.getEasAns());
        }

        return mList;
    }

    /**
     * <br>[機  能] 設問情報取得処理
     * <br>[解  説]
     * <br>[備  考]
     * @param bean アンケート回答時の設問モデル
     * @param mList 設問情報モデル
     * @param ansbean 回答情報
     * @return 選択肢モデル
     * @throws Exception 例外
     */
    private EnqSubListModel __getEnqSubAnsedList(EnqQuestionDataModel bean,
                                                 EnqMainListModel mList,
                                                 EnqAnsSubModel ansbean)
            throws Exception {

        // 設問情報サブをセットする
        EnqSubListModel sList = new EnqSubListModel();
        sList = __setQueSubList(bean);

        // その他の入力値セット
        if (mList.getEqmOther() != GSConstEnquete.OTHER_OFF) {
            mList.setEqmSelOther(ansbean.getEasAns());
        }
        // 初期値のセット
        // 単一選択
        if (bean.getEqmQueKbn() == GSConstEnquete.SYURUI_SINGLE
                && ansbean.getEasAnsNum() != null
                && ansbean.getEasAnsNum().intValue() > 0) {
            mList.setEqmSelRbn(String.valueOf(bean.getEqsSeq()));

        // 複数選択
        } else if (bean.getEqmQueKbn() == GSConstEnquete.SYURUI_MULTIPLE
                && ansbean.getEasAnsNum() != null
                && ansbean.getEasAnsNum().intValue() > 0) {
            int size = 0;
            if (mList.getEqmSelChk() != null && mList.getEqmSelChk().length > 0) {
                size = mList.getEqmSelChk().length;
            }
            String[] wk = new String[size + 1];
            if (mList.getEqmSelChk() != null && mList.getEqmSelChk().length > 0) {
                System.arraycopy(mList.getEqmSelChk(), 0, wk, 0, size);
            }
            wk[size] = String.valueOf(bean.getEqsSeq());
            mList.setEqmSelChk(wk);
        }

        return sList;
    }

    /**
     * <br>[機  能] 未回答アンケートの、設問情報取得処理
     * <br>[解  説]
     * <br>[備  考]
     * @param bean アンケート回答時の設問モデル
     * @return 設問モデル
     * @throws Exception 例外
     */
    private EnqMainListModel __getEnqMainList(EnqQuestionDataModel bean) throws Exception {

        // 設問情報をセットする
        EnqMainListModel mList = new EnqMainListModel();
        mList = __setQueMainList(bean);

        // 回答欄の設定
        if (bean.getEqmQueKbn() == GSConstEnquete.SYURUI_DAY
         && bean.getEqsDefDat() != null) {
            UDate initDate = bean.getEqsDefDat();
            mList.setEqmSelectAnsYear(initDate.getYear());
            mList.setEqmSelectAnsMonth(initDate.getMonth());
            mList.setEqmSelectAnsDay(initDate.getIntDay());
            DateTimePickerBiz dateBiz = new DateTimePickerBiz();
            dateBiz.setDateParam(mList, "eqmSelectAnsDateInput",
                    "eqmSelectAnsYear", "eqmSelectAnsMonth", "eqmSelectAnsDay", null);
        } else if (bean.getEqmQueKbn() == GSConstEnquete.SYURUI_TEXT) {
            mList.setEqmAnsText(bean.getEqsDefTxt());
        } else if (bean.getEqmQueKbn() == GSConstEnquete.SYURUI_TEXTAREA) {
            mList.setEqmAnsTextarea(bean.getEqsDefTxt());
        } else {
            mList.setEqmAnsNum(bean.getEqsDef());
        }
        return mList;
    }

    /**
     * <br>[機  能] 設問情報取得処理
     * <br>[解  説]
     * <br>[備  考]
     * @param bean アンケート回答時の設問モデル
     * @param mList 設問情報モデル
     * @return 選択肢モデル
     * @throws Exception 例外
     */
    private EnqSubListModel __getEnqSubList(EnqQuestionDataModel bean, EnqMainListModel mList)
            throws Exception {

        // 設問情報サブをセットする
        EnqSubListModel sList = new EnqSubListModel();
        sList = __setQueSubList(bean);

        // 選択系設問の、初期値セット
        if (bean.getEqmQueKbn() == GSConstEnquete.SYURUI_SINGLE && bean.getEqsDefNum() == 1) {
            mList.setEqmSelRbn(String.valueOf(bean.getEqsSeq()));
        } else if (bean.getEqmQueKbn() == GSConstEnquete.SYURUI_MULTIPLE
                && bean.getEqsDefNum() == 1) {
            int size = 0;
            if (mList.getEqmSelChk() != null && mList.getEqmSelChk().length > 0) {
                size = mList.getEqmSelChk().length;
            }
            String[] wk = new String[size + 1];
            if (mList.getEqmSelChk() != null && mList.getEqmSelChk().length > 0) {
                System.arraycopy(mList.getEqmSelChk(), 0, wk, 0, size);
            }
            wk[size] = String.valueOf(bean.getEqsSeq());
            mList.setEqmSelChk(wk);
        }

        return sList;
    }

    /**
     * <br>[機  能] メインの設問情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 設問情報
     * @return 設問情報
     * @throws Exception 例外
     */
    private EnqMainListModel __setQueMainList(EnqQuestionDataModel bean) throws Exception {

        EnqCommonBiz enqBiz = new EnqCommonBiz();

        EnqMainListModel mList = new EnqMainListModel();
        mList.setEmnSid(bean.getEmnSid());
        mList.setEqmSeq(bean.getEqmSeq());
        mList.setEqmDspSec(bean.getEqmDspSec());
        mList.setEqmQueSec(bean.getEqmQueSec());
        mList.setEqmQuestion(bean.getEqmQuestion());
        mList.setEqmQueKbn(bean.getEqmQueKbn());
        mList.setEqmRequire(String.valueOf(bean.getEqmRequire()));
        mList.setEqmOther(bean.getEqmOther());
        mList.setEqmDesc(
                StringUtilHtml.removeIllegalTag(bean.getEqmDesc()));
        mList.setEqmAttachKbn(bean.getEqmAttachKbn());
        if (bean.getEqmAttachKbn() == GSConstEnquete.TEMP_FILE
         || bean.getEqmAttachKbn() == GSConstEnquete.TEMP_IMAGE) {
            CmnBinfModel wkBinMdl = __getTempFileMdl(bean.getEqmAttachId());
            if (wkBinMdl != null) {
                CommonBiz cmnBiz = new CommonBiz();
                mList.setEqmAttachId(bean.getEqmAttachId());
                mList.setEqmAttachName(wkBinMdl.getBinFileName());
                mList.setEqmAttachSize(cmnBiz.getByteSizeString(wkBinMdl.getBinFileSize()));
            }
        }
        mList.setEqmAttachPos(bean.getEqmAttachPos());
        mList.setEqmLineKbn(bean.getEqmLineKbn());
        mList.setEqmGrfKbn(bean.getEqmGrfKbn());
        mList.setEqmDefTxt(bean.getEqsDefTxt());
        if (bean.getEqsDefNum() == 0) {
            mList.setEqmDefNum(String.valueOf(bean.getEqsDefNum()));
        } else {
            mList.setEqmDefNum(enqBiz.processNumber(String.valueOf(bean.getEqsDefNum())));
        }
        mList.setEqmDefDat(bean.getEqsDefDat());
        mList.setEqmDef(bean.getEqsDef());
        if (bean.getEqsRngStrNum() != null) {
            mList.setEqmRngStrNum(enqBiz.processNumber(bean.getEqsRngStrNum()));
        } else {
            mList.setEqmRngStrNum(bean.getEqsRngStrNum());
        }
        if (bean.getEqsRngEndNum() != null) {
            mList.setEqmRngEndNum(enqBiz.processNumber(bean.getEqsRngEndNum()));
        } else {
            mList.setEqmRngEndNum(bean.getEqsRngEndNum());
        }
        mList.setEqmRngStrDat(enqBiz.getStrDate(reqMdl__, bean.getEqsRngStrDat()));
        mList.setEqmRngEndDat(enqBiz.getStrDate(reqMdl__, bean.getEqsRngEndDat()));
        mList.setEqmUnitNum(bean.getEqsUnitNum());

        return mList;
    }

    /**
     * <br>[機  能] サブの設問情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 設問情報
     * @return 設問情報
     * @throws Exception 例外
     */
    private EnqSubListModel __setQueSubList(EnqQuestionDataModel bean) throws Exception {

        EnqSubListModel sList = new EnqSubListModel();
        sList = new EnqSubListModel();
        sList.setEmnSid(bean.getEmnSid());
        sList.setEqmSeq(bean.getEqmSeq());
        sList.setEqsSeq(bean.getEqsSeq());
        sList.setEqsDspSec(bean.getEqsDspSec());
        sList.setEqsDspName(bean.getEqsDspName());
        sList.setEqsDefTxt(bean.getEqsDefTxt());
        sList.setEqsDefNum(String.valueOf(bean.getEqsDefNum()));
        sList.setEqsDefDat(bean.getEqsDefDat());
        sList.setEqsDef(bean.getEqsDef());

        return sList;
    }

    /**
     * <br>[機  能] アンケート種類名の取得
     * <br>[解  説]
     * <br>[備  考]
     * @param enqTypeSid アンケート種類SID
     * @return アンケート種類名
     * @throws SQLException SQL実行例外
     */
    private String __getTypeName(long enqTypeSid) throws SQLException {

        log__.debug("アンケート種類名の取得処理");

        String ret = null;
        EnqTypeModel model = new EnqTypeModel();

        EnqTypeDao dao = new EnqTypeDao(con__);
        model = dao.select(enqTypeSid);
        if (model == null) {
            ret = "";
        } else {
            ret = model.getEtpName();
        }
        return ret;
    }

    /**
     * <br>[機  能] 発信者名称を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param grpSid グループSID
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    private void __setSendName(Enq110ParamModel paramMdl, int grpSid, int usrSid)
    throws SQLException {

        log__.debug("発信者名称取得処理");
        String name = null;
        long sendSid = 0;
        int sendKbn = Enq110Const.SENDER_KBN_USER;
        boolean delFlg = false;

        // 発信者名称取得
        if (grpSid > 0) {
            CmnGroupmModel model = new CmnGroupmModel();
            CmnGroupmDao dao = new CmnGroupmDao(con__);
            model = dao.selectGroup(grpSid);
            name = model.getGrpName();
            sendKbn = Enq110Const.SENDER_KBN_GROUP;
            sendSid = grpSid;
            delFlg = model.getGrpJkbn() == GSConst.JTKBN_DELETE;
        } else {
            CmnUsrmInfModel model = new CmnUsrmInfModel();
            CmnUsrmInfDao dao = new CmnUsrmInfDao(con__);
            model = dao.select(usrSid);
            name = model.getUsiSei() + " " + model.getUsiMei();
            sendSid = usrSid;

            CmnUsrmModel usrmModel = new CmnUsrmModel();
            CmnUsrmDao usrmDao = new CmnUsrmDao(con__);
            usrmModel = usrmDao.select(usrSid);
            delFlg = usrmModel.getUsrJkbn() == GSConst.JTKBN_DELETE;
            paramMdl.setEnq110SendUkoFlg(usrmModel.getUsrUkoFlg());
        }
        paramMdl.setEnq110SendName(name);
        paramMdl.setEnq110SendSid(sendSid);
        paramMdl.setEnq110SendKbn(sendKbn);
        paramMdl.setEnq110SendNameDelFlg(delFlg);
    }

    /**
     * <br>[機  能] アンケート設問情報取得処理
     * <br>[解  説]
     * <br>[備  考]
     * @param enqSid アンケートSID
     * @return アンケート設問_基本情報
     * @throws SQLException SQL実行例外
     */
    private ArrayList<EnqQuestionDataModel> __getEnqQuestionData(long enqSid) throws SQLException {

        log__.debug("アンケート設問情報取得処理");

        ArrayList<EnqQuestionDataModel> model = new ArrayList<EnqQuestionDataModel>();
        EnqQuestionDataDao dao = new EnqQuestionDataDao(con__);
        model = dao.select(enqSid);

        return model;
    }

    /**
     * <br>[機  能] アンケート回答情報取得処理
     * <br>[解  説]
     * <br>[備  考]
     * @param enqSid アンケートSID
     * @param usrSid ユーザSID
     * @return アンケート設問_基本情報
     * @throws SQLException SQL実行例外
     */
    private ArrayList<EnqAnsSubModel> __getEnqAnsedData(long enqSid, int usrSid)
            throws SQLException {

        log__.debug("アンケート設問情報取得処理");

        ArrayList<EnqAnsSubModel> model = new ArrayList<EnqAnsSubModel>();
        EnqAnsSubDao dao = new EnqAnsSubDao(con__);
        model = dao.select(enqSid, usrSid);

        return model;
    }

    /**
     * <br>[機  能] 添付ファイル名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param binSid バイナリSID
     * @return 添付ファイル名
     * @throws TempFileException 添付ファイル操作時に発生する例外
     */
    private CmnBinfModel __getTempFileMdl(String binSid) throws TempFileException {

        log__.debug("添付ファイル名取得処理");

        if (binSid == null || binSid.equals("")) {
            return null;
        }

        CmnBinfModel model = new CmnBinfModel();
        CmnBinfDao dao = new CmnBinfDao(con__);
        model = dao.getBinInfo(NullDefault.getLong(binSid, -1));
        return model;
    }

    /**
     * <br>[機  能] アンケートの添付ファイルが、ダウンロード可能かチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param enqSid アンケートSID
     * @param binSid バイナリSID
     * @return true:参照可能、false:参照不可
     * @throws SQLException SQL実行例外
     */
    public boolean canDownloadEnqBinData(RequestModel reqMdl, Connection con,
                                         long enqSid, long binSid) throws SQLException {

        log__.debug("アンケートの添付ファイルが、ダウンロード可能かチェックする処理");
        EnqCommonBiz enqBiz = new EnqCommonBiz();

        // セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        // セッションユーザが、該当する添付ファイルを参照できるかチェックする
        if (!enqBiz.canUseTempFile(con, enqSid, binSid, sessionUsrSid)) {
            return false;
        }

        return true;
    }

    /**
     * <br>[機  能] アンケート 設問情報の状態を確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @return 確認結果
     * @throws SQLException SQL実行時例外
     */
    public int checkEnqQueData(Connection con, Enq110ParamModel paramMdl)
    throws SQLException {
        String queDate = NullDefault.getString(paramMdl.getEnq110queDate(), "");
        EnqMainDao dao = new EnqMainDao(con);
        EnqMainModel model = dao.select(paramMdl.getAnsEnqSid());
        String enqEdate = null;

        if (model == null || model.getEmnEdate() == null) {
            return Enq110Const.ENQ_QUE_NODATA;
        }


        enqEdate = String.valueOf(model.getEmnEdate().getTime());
        if (enqEdate != null && enqEdate.equals(queDate)) {
            return Enq110Const.ENQ_QUE_OK;
        }

        return Enq110Const.ENQ_QUE_CHANGED;
    }
}
