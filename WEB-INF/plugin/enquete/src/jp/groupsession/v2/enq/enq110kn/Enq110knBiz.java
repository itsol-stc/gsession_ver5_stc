package jp.groupsession.v2.enq.enq110kn;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.biz.EnqUsedDataBiz;
import jp.groupsession.v2.enq.dao.EnqAnsMainDao;
import jp.groupsession.v2.enq.dao.EnqAnsSubDao;
import jp.groupsession.v2.enq.dao.EnqMainDao;
import jp.groupsession.v2.enq.dao.EnqQueMainDao;
import jp.groupsession.v2.enq.dao.EnqQuestionDataDao;
import jp.groupsession.v2.enq.dao.EnqTypeDao;
import jp.groupsession.v2.enq.enq110.Enq110Biz;
import jp.groupsession.v2.enq.enq110.Enq110Const;
import jp.groupsession.v2.enq.enq110.Enq110ParamModel;
import jp.groupsession.v2.enq.model.EnqAnsMainModel;
import jp.groupsession.v2.enq.model.EnqAnsSubModel;
import jp.groupsession.v2.enq.model.EnqMainListModel;
import jp.groupsession.v2.enq.model.EnqMainModel;
import jp.groupsession.v2.enq.model.EnqQueMainModel;
import jp.groupsession.v2.enq.model.EnqQuestionDataModel;
import jp.groupsession.v2.enq.model.EnqSubListModel;
import jp.groupsession.v2.enq.model.EnqTypeModel;

/**
 * <br>[機  能] アンケート 回答確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq110knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq110knBiz.class);
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /** コネクション */
    private Connection con__ = null;

    /**
     * <p>デフォルトコンストラクタ
     */
    public Enq110knBiz() {
    }

    /**
     * <p>コンストラクタ
     * @param reqMdl リクエストモデル
     * @param con コネクション
     */
    public Enq110knBiz(RequestModel reqMdl, Connection con) {
        reqMdl__ = reqMdl;
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110knModel パラメータモデル
     * @param dspMode 表示モード
     * @throws Exception 例外
     */
    public void setInitData(Enq110knParamModel enq110knModel, int dspMode) throws Exception {

        log__.debug("初期表示情報取得");

        // 表示モードの判定
        if (dspMode == GSConstEnquete.EAM_STS_KBN_YES) {
            // アンケート回答済
            enq110knModel.setEnq110DspMode(GSConstEnquete.EAM_STS_KBN_YES);
            __setEnqData(enq110knModel);
        } else {
            // アンケート未回答
            enq110knModel.setEnq110DspMode(GSConstEnquete.EAM_STS_KBN_NO);
            __setEnqInputData(enq110knModel);
        }

    }

    /**
     * <br>[機  能] 更新処理
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110knModel パラメータモデル
     * @return true:更新成功、false:更新失敗
     * @throws SQLException SQL実行例外
     */
    public boolean doCommit(Enq110knParamModel enq110knModel) throws SQLException {

        log__.debug("更新処理");

        boolean commitFlg = false;
        List<EnqMainListModel> queList = enq110knModel.getEnq110QueListToList();

        // 回答アンケートのチェック
        if (queList.isEmpty()) {
            return false;
        }

        // セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        // 更新処理
        try {
            con__.setAutoCommit(false);

            //アンケート情報のデータ使用量を登録(編集対象のデータ使用量を減算)
            long emnSid = enq110knModel.getAnsEnqSid();
            EnqUsedDataBiz usedDataBiz = new EnqUsedDataBiz(con__);
            usedDataBiz.insertEnqDataSize(Arrays.asList(emnSid), false);

            // 回答_基本を更新
            EnqAnsMainDao mdao = new EnqAnsMainDao(con__);
            int count = mdao.updateAnsMain(
                    __getInsertAnsMainModel(enq110knModel.getAnsEnqSid(), sessionUsrSid));
            if (count < 1) {
                return false;
            }

            // 回答_サブを更新する
            // --回答_サブを削除
            EnqAnsSubDao sdao = new EnqAnsSubDao(con__);
            sdao.deleteAnsSub(enq110knModel.getAnsEnqSid(), sessionUsrSid);

            // --回答_サブを登録
            for (EnqMainListModel bean : queList) {
                // コメントは除外
                if (bean.getEqmQueKbn() == GSConstEnquete.SYURUI_COMMENT) {
                    continue;
                }
                // 登録
                __enqAnsSubTouroku(sdao, bean, sessionUsrSid);
            }

            //アンケート情報のデータ使用量を登録
            usedDataBiz.insertEnqDataSize(Arrays.asList(emnSid), true);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("回答アンケートの更新に失敗しました。" + e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
            con__.setAutoCommit(true);
        }

        return commitFlg;
    }

    /**
     * <br>[機  能] アンケート情報設定
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110knModel パラメータモデル
     * @throws Exception 例外
     */
    private void __setEnqData(Enq110knParamModel enq110knModel) throws Exception {

        // アンケート基本情報設定
        __setEnqMainData(enq110knModel);

        // アンケート設問情報設定（回答済）
        __setEnqQuestionDbData(enq110knModel);
    }

    /**
     * <br>[機  能] アンケートの入力情報設定
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110knModel パラメータモデル
     * @throws Exception 例外
     */
    private void __setEnqInputData(Enq110knParamModel enq110knModel) throws Exception {

        // アンケート基本情報設定
        __setEnqMainData(enq110knModel);

        // アンケート設問情報設定
        __setEnqQuestionData(enq110knModel);
    }

    /**
     * <br>[機  能] アンケート基本情報の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110knModel パラメータモデル
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイル操作時に発生する例外
     * @throws Exception 例外
     */
    private void __setEnqMainData(Enq110knParamModel enq110knModel)
        throws SQLException, TempFileException, Exception {

        log__.debug("アンケート基本情報の設定処理");

        EnqMainModel model = new EnqMainModel();
        EnqMainDao dao = new EnqMainDao(con__);

        // アンケート基本情報の取得
        model = dao.select(enq110knModel.getAnsEnqSid());

        // 取得値を画面値にセット
        enq110knModel.setEnq110TypeName(__getTypeName(model.getEtpSid()));
        enq110knModel.setEnq110Title(model.getEmnTitle());
        enq110knModel.setEnq110PriKbn(model.getEmnPriKbn());
        enq110knModel.setEnq110Desc(NullDefault.getString(model.getEmnDesc(), ""));
        enq110knModel.setEnq110Desc(StringUtilHtml.removeIllegalTag(enq110knModel.getEnq110Desc()));
        enq110knModel.setEnq110AttachKbn(model.getEmnAttachKbn());
        enq110knModel.setEnq110AttachPos(model.getEmnAttachPos());
        if (model.getEmnAttachKbn() == GSConstEnquete.TEMP_FILE
         || model.getEmnAttachKbn() == GSConstEnquete.TEMP_IMAGE) {
            // バイナリ情報取得
            CmnBinfModel wkBinMdl = __getTempFileMdl(model.getEmnAttachId());
            if (wkBinMdl != null) {
                CommonBiz cmnBiz = new CommonBiz();
                enq110knModel.setEnq110AttachId(model.getEmnAttachId());
                enq110knModel.setEnq110AttachName(wkBinMdl.getBinFileName());
                enq110knModel.setEnq110AttachSize(
                        cmnBiz.getByteSizeString(wkBinMdl.getBinFileSize()));
            }
        }

        Enq110Biz biz110 = new Enq110Biz(reqMdl__, con__);
        enq110knModel.setEnq110OpenStr(biz110.createViewDateStr(reqMdl__, model.getEmnOpenStr()));
        enq110knModel.setEnq110OpenEnd(biz110.createViewDateStr(reqMdl__, model.getEmnOpenEnd()));
        enq110knModel.setEnq110ResEnd(biz110.createViewDateStr(reqMdl__, model.getEmnResEnd()));
        enq110knModel.setEnq110AnsPubStr(
                biz110.createViewDateStr(reqMdl__, model.getEmnAnsPubStr()));
        enq110knModel.setEnq110Anony(model.getEmnAnony());
        enq110knModel.setEnq110AnsOpen(model.getEmnAnsOpen());
        __setSendName(enq110knModel, (int) model.getEmnSendGrp(), (int) model.getEmnSendUsr());

    }

    /**
     * <br>[機  能] アンケート回答済情報の設定処理
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110knModel パラメータモデル
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイル操作時に発生する例外
     * @throws Exception 例外
     */
    private void __setEnqQuestionDbData(Enq110knParamModel enq110knModel)
            throws SQLException, TempFileException, Exception {

        log__.debug("アンケート回答済情報の設定処理");

        ArrayList<EnqMainListModel> mMdl = new ArrayList<EnqMainListModel>();
        EnqMainListModel mList = new EnqMainListModel();
        int usrSid = -1;

        // ユーザ情報取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        usrSid = NullDefault.getInt(enq110knModel.getEnq110answer(), usModel.getUsrsid());

        // 設問・回答情報取得処理
        EnqQuestionDataDao dao = new EnqQuestionDataDao(con__);
        ArrayList<EnqQuestionDataModel> list =
                dao.selectAnswered(enq110knModel.getAnsEnqSid(), usrSid);

        int wkEqmSeq = -9;
        boolean queSetFlg = false;

        // 設問・回答欄の作成処理
        for (EnqQuestionDataModel bean : list) {

            // 設問・回答情報をセットする
            if (queSetFlg && bean.getEqmSeq() != wkEqmSeq) {
                mMdl.add(mList);
                mList = new EnqMainListModel();
                queSetFlg = false;
            }
            mList = __getEnqAnsedList(bean, mList, queSetFlg);
            queSetFlg = true;

            // 設問連番の保持
            wkEqmSeq = bean.getEqmSeq();
        }
        if (queSetFlg) {
            mMdl.add(mList);
        }
        enq110knModel.setEnq110knQueListForm(mMdl);
    }

    /**
     * <br>[機  能] 設問情報取得処理
     * <br>[解  説]
     * <br>[備  考]
     * @param bean アンケートの設問・回答モデル
     * @param argList 設問・回答情報
     * @param flg 設問情報セットフラグ
     * @return 設問モデル
     * @throws Exception 例外
     */
    private EnqMainListModel __getEnqAnsedList(EnqQuestionDataModel bean,
                                               EnqMainListModel argList,
                                               boolean flg) throws Exception {

        EnqCommonBiz enqBiz = new EnqCommonBiz();
        EnqMainListModel mList = argList;

        // DBで取得したデータをセット
        if (!flg) {
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
                // バイナリ情報取得
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

            // 回答値をセット
            mList.setEqmAnsText(bean.getEasAnsTxt());
            mList.setEqmAnsTextarea(NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(bean.getEasAnsTxt()), ""));
            if (!StringUtil.isNullZeroString(bean.getEasAns())) {
                mList.setEqmAnsNum(String.valueOf(bean.getEasAnsNum()));
            }
            if (bean.getEasAnsDat() != null) {
                mList.setEqmSelectAnsYear(bean.getEasAnsDat().getYear());
                mList.setEqmSelectAnsMonth(bean.getEasAnsDat().getMonth());
                mList.setEqmSelectAnsDay(bean.getEasAnsDat().getIntDay());
                mList.setEqmSelectAnsDate(enqBiz.getStrDate(reqMdl__,
                                                            bean.getEasAnsDat().getYear(),
                                                            bean.getEasAnsDat().getMonth(),
                                                            bean.getEasAnsDat().getIntDay()));
            }
        }

        // 選択系設問の、回答値を画面にセットする
        if (bean.getEqmQueKbn() == GSConstEnquete.SYURUI_SINGLE
         && bean.getEasAnsNum() == GSConstEnquete.CHOICE_INIT_ON) {
            mList.setEqmSelRbn(String.valueOf(bean.getEqsSeq()));
            mList.setEqmSelOther(NullDefault.getString(
                    StringUtilHtml.transToHTmlPlusAmparsant(bean.getEasAns()), ""));
            mList.setEqmSelRbnName(bean.getEasAns());
        } else if (bean.getEqmQueKbn() == GSConstEnquete.SYURUI_MULTIPLE
                && bean.getEasAnsNum() == GSConstEnquete.CHOICE_INIT_ON) {
            ArrayList<LabelValueBean> wk = mList.getEqmSelChkName();
            LabelValueBean label = new LabelValueBean();
            if (wk == null) {
                wk = new ArrayList<LabelValueBean>();
            }
            if (bean.getEqsSeq() == GSConstEnquete.CHOICE_KBN_OTHER) {
                mList.setEqmSelOther(NullDefault.getString(
                        StringUtilHtml.transToHTmlPlusAmparsant(bean.getEasAns()), ""));
            }
            label.setLabel(bean.getEasAns());
            label.setValue(String.valueOf(bean.getEqsSeq()));
            wk.add(label);
            mList.setEqmSelChkName(wk);
        }

        return mList;
    }

    /**
     * <br>[機  能] アンケート設問情報の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param enq110knModel パラメータモデル
     * @throws SQLException SQL実行例外
     * @throws TempFileException 添付ファイル操作時に発生する例外
     * @throws Exception 例外
     */
    private void __setEnqQuestionData(Enq110knParamModel enq110knModel)
        throws SQLException, TempFileException, Exception {

        log__.debug("アンケート設問情報の設定処理");

        ArrayList<EnqMainListModel> mMdl = new ArrayList<EnqMainListModel>();
        EnqMainListModel mList = null;
        EnqMainListModel wkMdl = null;
        int index = 0;

        // 入力した回答データ
        List<EnqMainListModel> hidList = enq110knModel.getEnq110QueListToList();

        // 設問情報取得処理
        EnqQueMainDao dao = new EnqQueMainDao(con__);
        ArrayList<EnqQueMainModel> queMdl = dao.select(enq110knModel.getAnsEnqSid());

        // 回答欄の作成処理
        for (EnqQueMainModel bean : queMdl) {
            mList = new EnqMainListModel();
            wkMdl = hidList.get(index);
            mList = __getEnqMainList(bean, wkMdl);

            mMdl.add(mList);
            index++;
        }

        enq110knModel.setEnq110knQueListForm(mMdl);
    }

    /**
     * <br>[機  能] 設問情報取得処理
     * <br>[解  説]
     * <br>[備  考]
     * @param bean アンケート回答時の設問モデル
     * @param inputMdl 入力データのモデル
     * @return 設問モデル
     * @throws Exception 例外
     */
    private EnqMainListModel __getEnqMainList(EnqQueMainModel bean, EnqMainListModel inputMdl)
            throws Exception {

        EnqCommonBiz enqBiz = new EnqCommonBiz();
        EnqMainListModel mList = new EnqMainListModel();

        // DBで取得したデータをセット
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
        mList.setEqmUnitNum(inputMdl.getEqmUnitNum());

        // 最小、最大値のセット
        mList.setEqmRngStrNum(inputMdl.getEqmRngStrNum());
        mList.setEqmRngEndNum(inputMdl.getEqmRngEndNum());
        mList.setEqmRngStrDat(inputMdl.getEqmRngStrDat());
        mList.setEqmRngEndDat(inputMdl.getEqmRngEndDat());

        // 前画面で入力したパラメータをセット
        mList.setEqmAnsText(inputMdl.getEqmAnsText());
        mList.setEqmAnsTextarea(NullDefault.getString(
                StringUtilHtml.transToHTmlPlusAmparsant(inputMdl.getEqmAnsTextarea()), ""));
        mList.setEqmAnsNum(inputMdl.getEqmAnsNum());
        mList.setEqmSelectAnsYear(inputMdl.getEqmSelectAnsYear());
        mList.setEqmSelectAnsMonth(inputMdl.getEqmSelectAnsMonth());
        mList.setEqmSelectAnsDay(inputMdl.getEqmSelectAnsDay());
        mList.setEqmSelectAnsDate(enqBiz.getStrDate(reqMdl__,
                                                    inputMdl.getEqmSelectAnsYear(),
                                                    inputMdl.getEqmSelectAnsMonth(),
                                                    inputMdl.getEqmSelectAnsDay()));
        mList.setEqmSelRbn(inputMdl.getEqmSelRbn());
        if (inputMdl.getEqmSelChk() != null && inputMdl.getEqmSelChk().length > 0) {
            mList.setEqmSelChk(inputMdl.getEqmSelChk());
        }
        mList.setEqmSelOther(NullDefault.getString(
                StringUtilHtml.transToHTmlPlusAmparsant(inputMdl.getEqmSelOther()), ""));

        // 選択系設問の、回答値を画面にセットする
        String wkRbn = NullDefault.getString(inputMdl.getEqmSelRbn(), "");
        if (!StringUtil.isNullZeroStringSpace(wkRbn)
         || wkRbn.equals(String.valueOf(GSConstEnquete.CHOICE_KBN_OTHER))) {

            for (EnqSubListModel smdl : inputMdl.getEqmSubListToList()) {
                if (inputMdl.getEqmSelRbn().equals(String.valueOf(smdl.getEqsSeq()))) {
                    mList.setEqmSelRbnName(smdl.getEqsDspName());
                    break;
                }
            }

        } else if (inputMdl.getEqmSelChk() != null && inputMdl.getEqmSelChk().length > 0) {

            ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
            LabelValueBean label = null;
            String[] checked = inputMdl.getEqmSelChk();
            for (EnqSubListModel smdl : inputMdl.getEqmSubListToList()) {
                if (Arrays.asList(checked).contains(String.valueOf(smdl.getEqsSeq()))) {
                    label = new LabelValueBean();
                    label.setLabel(smdl.getEqsDspName());
                    label.setValue(String.valueOf(smdl.getEqsSeq()));
                    labelList.add(label);
                }
            }
            mList.setEqmSelChkName(labelList);
        }

        return mList;
    }

    /**
     * <br>[機  能] 回答アンケート更新処理
     * <br>[解  説]
     * <br>[備  考]
     * @param sdao EnqAnsSubDaoクラス
     * @param mdl アンケートのモデル
     * @param usrSid セッションユーザSID
     * @return true:更新成功、false:更新失敗
     * @throws SQLException SQL実行例外
     */
    private boolean __enqAnsSubTouroku(EnqAnsSubDao sdao, EnqMainListModel mdl, int usrSid)
            throws SQLException {

        log__.debug("回答アンケート更新処理");

        boolean ret = false;

        int queKbn = mdl.getEqmQueKbn();

        // 単一
        if (queKbn == GSConstEnquete.SYURUI_SINGLE) {

            int selRbnVal = NullDefault.getInt(mdl.getEqmSelRbn(), -9);
            for (EnqSubListModel bean : mdl.getEqmSubListToList()) {

                // その他
                if (selRbnVal == GSConstEnquete.CHOICE_KBN_OTHER && bean.getEqsSeq() == selRbnVal) {
                    sdao.insertAnsSub(queKbn,
                                      __getInsertAnsSubModel(mdl, usrSid, bean.getEqsSeq(),
                                                             mdl.getEqmSelOther(),
                                                             GSConstEnquete.CHOICE_INIT_ON));
                // 選択した選択肢
                } else if (bean.getEqsSeq() == selRbnVal) {
                    sdao.insertAnsSub(queKbn,
                                      __getInsertAnsSubModel(mdl, usrSid, bean.getEqsSeq(),
                                                             bean.getEqsDspName(),
                                                             GSConstEnquete.CHOICE_INIT_ON));
                // 選択していない選択肢
                } else {
                    sdao.insertAnsSub(queKbn,
                                      __getInsertAnsSubModel(mdl, usrSid, bean.getEqsSeq(), null,
                                                             GSConstEnquete.CHOICE_INIT_OFF));
                }
            }

        // 複数
        } else if (queKbn == GSConstEnquete.SYURUI_MULTIPLE) {

            List<String> selChkVal = null;
            if (mdl.getEqmSelChk() != null && mdl.getEqmSelChk().length > 0) {
                selChkVal = new ArrayList<String>(Arrays.asList(mdl.getEqmSelChk()));
            }

            for (EnqSubListModel bean : mdl.getEqmSubListToList()) {
                // 選択していない選択肢
                if (selChkVal == null || !selChkVal.contains(String.valueOf(bean.getEqsSeq()))) {
                    sdao.insertAnsSub(queKbn,
                                      __getInsertAnsSubModel(mdl, usrSid, bean.getEqsSeq(), null,
                                                             GSConstEnquete.CHOICE_INIT_OFF));
                // 選択 その他
                } else if (bean.getEqsSeq() == GSConstEnquete.CHOICE_KBN_OTHER) {
                    sdao.insertAnsSub(queKbn,
                                      __getInsertAnsSubModel(mdl, usrSid, bean.getEqsSeq(),
                                                             mdl.getEqmSelOther(),
                                                             GSConstEnquete.CHOICE_INIT_ON));
                // 選択
                } else {
                    sdao.insertAnsSub(queKbn,
                                      __getInsertAnsSubModel(mdl, usrSid, bean.getEqsSeq(),
                                                             bean.getEqsDspName(),
                                                             GSConstEnquete.CHOICE_INIT_ON));
                }
            }

        // 選択系以外
        } else {
            sdao.insertAnsSub(queKbn, __getInsertAnsSubModel(mdl, usrSid));
        }

        return ret;
    }

    /**
     * <br>[機  能] 更新用の回答_基本モデル取得処理
     * <br>[解  説]
     * <br>[備  考]
     * @param enqSid アンケートSID
     * @param usrSid セッションユーザSID
     * @return 回答_サブモデル
     */
    private EnqAnsMainModel __getInsertAnsMainModel(long enqSid, int usrSid) {

        log__.debug("更新用の回答_基本モデル取得処理");

        UDate now = new UDate();
        EnqAnsMainModel ansMdl = new EnqAnsMainModel();
        ansMdl.setEmnSid(enqSid);
        ansMdl.setUsrSid(usrSid);
        ansMdl.setEamStsKbn(GSConstEnquete.ANS_KBN_ANSWERED);
        ansMdl.setEqmAnsDate(now);
        ansMdl.setEamEuid(usrSid);
        ansMdl.setEamEdate(now);

        return ansMdl;
    }

    /**
     * <br>[機  能] 更新用の回答_サブモデル取得処理
     * <br>[解  説]
     * <br>[備  考]
     * @param mdl アンケートのモデル
     * @param usrSid セッションユーザSID
     * @return 回答_サブモデル
     */
    private EnqAnsSubModel __getInsertAnsSubModel(EnqMainListModel mdl, int usrSid) {

        log__.debug("設問区分<選択系>以外の、更新用回答_サブモデル取得処理");
        return __getInsertAnsSubModel(mdl, usrSid, 0, "", -1);
    }

    /**
     * <br>[機  能] 更新用の回答_サブモデル取得処理
     * <br>[解  説]
     * <br>[備  考]
     * @param mdl アンケートのモデル
     * @param eqsSeq 設問サブ連番
     * @param dspName 選択肢の表示名
     * @param usrSid セッションユーザSID
     * @param choiceKbn 選択区分
     * @return 回答_サブモデル
     */
    private EnqAnsSubModel __getInsertAnsSubModel(EnqMainListModel mdl,
                                                  int usrSid,
                                                  int eqsSeq,
                                                  String dspName,
                                                  int choiceKbn) {

        log__.debug("更新用の回答_サブモデル取得処理");

        UDate now = new UDate();
        EnqAnsSubModel ansMdl = new EnqAnsSubModel();

        ansMdl.setEmnSid(mdl.getEmnSid());
        ansMdl.setUsrSid(usrSid);
        ansMdl.setEqmSeq(mdl.getEqmSeq());
        ansMdl.setEqsSeq(eqsSeq);
        ansMdl.setEqmAuid(usrSid);
        ansMdl.setEqmAdate(now);
        ansMdl.setEqmEuid(usrSid);
        ansMdl.setEqmEdate(now);

        int queKbn = mdl.getEqmQueKbn();
        switch (queKbn) {
            // 単一、複数
            case GSConstEnquete.SYURUI_SINGLE:
            case GSConstEnquete.SYURUI_MULTIPLE:
                ansMdl.setEasAnsNum(new BigDecimal(choiceKbn));
                ansMdl.setEasAns(dspName);
                break;

            // テキスト
            case GSConstEnquete.SYURUI_TEXT:
                ansMdl.setEasAnsTxt(mdl.getEqmAnsText());
                ansMdl.setEasAns(mdl.getEqmAnsText());
                break;

            // テキストエリア
            case GSConstEnquete.SYURUI_TEXTAREA:
                ansMdl.setEasAnsTxt(mdl.getEqmAnsTextarea());
                ansMdl.setEasAns(mdl.getEqmAnsTextarea());
                break;

            // 数値
            case GSConstEnquete.SYURUI_INTEGER:
                if (!StringUtil.isNullZeroString(mdl.getEqmAnsNum())) {
                    ansMdl.setEasAnsNum(new BigDecimal(mdl.getEqmAnsNum()));
                } else {
                    ansMdl.setEasAnsNum(null);
                }
                ansMdl.setEasAns(mdl.getEqmAnsNum());
                break;

            // 日付
            case GSConstEnquete.SYURUI_DAY:
                if (mdl.getEqmSelectAnsYear() != -1) {
                    UDate date = new UDate();
                    date.setDate(mdl.getEqmSelectAnsYear(), mdl.getEqmSelectAnsMonth(),
                                 mdl.getEqmSelectAnsDay());
                    ansMdl.setEasAnsDat(date);
                    ansMdl.setEasAns(date.getDateString("/"));
                }

            default:
                break;
        }
        return ansMdl;
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
            model = dao.selectUserNameAndJtkbn(usrSid);
            name = model.getUsiSei() + " " + model.getUsiMei();
            sendSid = usrSid;

            delFlg = model.getUsrJkbn() == GSConst.JTKBN_DELETE;
            paramMdl.setEnq110SendUkoFlg(model.getUsrUkoFlg());
        }
        paramMdl.setEnq110SendName(name);
        paramMdl.setEnq110SendSid(sendSid);
        paramMdl.setEnq110SendKbn(sendKbn);
        paramMdl.setEnq110SendNameDelFlg(delFlg);
    }

    /**
     * <br>[機  能] 添付ファイルデータ取得
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

        // 該当する添付ファイルを参照できるかチェックする
        if (!enqBiz.canUseTempFile(con, enqSid, binSid)) {
            return false;
        }

        return true;
    }
}
