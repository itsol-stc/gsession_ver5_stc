package jp.groupsession.v2.enq.enq220;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.enq210.Enq210Biz;
import jp.groupsession.v2.enq.enq210.Enq210Form;
import jp.groupsession.v2.enq.enq210.Enq210QueModel;
import jp.groupsession.v2.enq.enq210.Enq210QueSubModel;

/**
 * <br>[機  能] アンケート 設問詳細登録画面のビジネスクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq220Biz {

    /** 並び替え 上へ */
    protected static final int SORTTYPE_UP_ = 0;
    /** 並び替え 下へ */
    protected static final int SORTTYPE_DOWN_ = 1;

    /** 単一選択 初期表示件数 */
    private static final int INIT_SINGLE_COUNT = 3;
    /** 複数選択 初期表示件数 */
    private static final int INIT_MULTIPLE_COUNT = 5;
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "enq210";

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException 設問情報の読み込みに失敗
     * @throws InvocationTargetException 設問 選択肢の読み込みに失敗
     * @throws IllegalAccessException 設問 選択肢の読み込みに失敗
     * @throws IOException 設問情報の読み込みに失敗
     * @throws NoSuchMethodException 日時設定時例外
     */
    public void setInitData(Enq220ParamModel paramMdl,
                            RequestModel reqMdl,
                            Connection con,
                            String tempDir)
    throws SQLException, IOToolsException,
        InvocationTargetException, IllegalAccessException, IOException, NoSuchMethodException {

        EnqCommonBiz enqBiz = new EnqCommonBiz();
        UDate nowDate = new UDate();
        UDate nextDate = nowDate.cloneUDate();
        nextDate.addDay(7);

        //発信者名称を設定
        Enq210Biz biz210 = new Enq210Biz();
        paramMdl.setEnq220ViewSender(
                biz210.getSenderName(con, reqMdl, paramMdl.getEnq210Send()));

        //回答期限(表示用)を設定
        if (paramMdl.getEnq210AnsDate() != null
                && paramMdl.getEnq210AnsDate().length() == 10) {
            paramMdl.setEnq220ViewAnsDate(enqBiz.getStrDate(
                    reqMdl,
                    NullDefault.getInt(paramMdl.getEnq210AnsDate().substring(0, 4), -1),
                    NullDefault.getInt(paramMdl.getEnq210AnsDate().substring(5, 7), -1),
                    NullDefault.getInt(paramMdl.getEnq210AnsDate().substring(8, 10), -1)));

        } else {
            paramMdl.setEnq220ViewAnsDate("");
        }
        //公開期限 開始(表示用)を設定
        if (paramMdl.getEnq210AnsPubFrDate() != null
                && paramMdl.getEnq210AnsPubFrDate().length() == 10) {
            paramMdl.setEnq220ViewAnsPubDateFrom(enqBiz.getStrDate(
                    reqMdl,
                    NullDefault.getInt(paramMdl.getEnq210AnsPubFrDate().substring(0, 4), -1),
                    NullDefault.getInt(paramMdl.getEnq210AnsPubFrDate().substring(5, 7), -1),
                    NullDefault.getInt(paramMdl.getEnq210AnsPubFrDate().substring(8, 10), -1)));
        } else {
            paramMdl.setEnq220ViewAnsPubDateFrom("");
        }
        //公開期限 終了(表示用)を設定
        if (paramMdl.getEnq210ToKbn() == Enq210Form.TO_DATE_NOLIMIT) {
            paramMdl.setEnq220ViewPubDateTo(null);
        } else {
            if (paramMdl.getEnq210AnsPubFrDate() != null
                    && paramMdl.getEnq210ToDate().length() == 10) {
                paramMdl.setEnq220ViewPubDateTo(enqBiz.getStrDate(
                        reqMdl,
                        NullDefault.getInt(paramMdl.getEnq210ToDate().substring(0, 4), -1),
                        NullDefault.getInt(paramMdl.getEnq210ToDate().substring(5, 7), -1),
                        NullDefault.getInt(paramMdl.getEnq210ToDate().substring(8, 10), -1)));
            } else {
                paramMdl.setEnq220ViewPubDateTo("");
            }
        }
        //公開開始日(表示用)を設定
        if (paramMdl.getEnq220ViewPubDateFrom() != null
                && paramMdl.getEnq220ViewPubDateFrom().length() == 10) {
            paramMdl.setEnq220ViewPubDateFrom(enqBiz.getStrDate(
                    reqMdl,
                    NullDefault.getInt(paramMdl.getEnq210FrDate().substring(0, 4), -1),
                    NullDefault.getInt(paramMdl.getEnq210FrDate().substring(5, 7), -1),
                    NullDefault.getInt(paramMdl.getEnq210FrDate().substring(8, 10), -1)));
        } else {
            paramMdl.setEnq220ViewPubDateFrom("");
        }

        //注意事項(表示用)を設定
        paramMdl.setEnq220ViewDesc(
                StringUtilHtml.removeIllegalTag(paramMdl.getEnq210Desc()));

        if (paramMdl.getEnq220initFlg() != 1) {
            GSTemporaryPathModel tempPath = getQueEditTempPath(reqMdl);

            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            temp.deleteTempPath(tempPath);
            temp.createTempDir(tempPath);


            List<Enq210QueModel> queList = biz210.readQueList(reqMdl);

            if (paramMdl.getEnq220editMode() == GSConstEnquete.EDITMODE_EDIT) {
                int queIndex = paramMdl.getEnq210editQueIndex();
                Enq210QueModel queData = null;
                for (Enq210QueModel editQueData : queList) {
                    if (editQueData.getEnq210queIndex() == queIndex) {
                        queData = editQueData;
                    }
                }

                int queKbn = queData.getEnq210QueKbn();
                paramMdl.setEnq210queType(queKbn);

                //設問連番
                paramMdl.setEnq220Seq(queData.getEnq210Seq());
                //設問番号
                paramMdl.setEnq220QueNo(queData.getEnq210QueNo());
                //設問
                paramMdl.setEnq220Question(queData.getEnq210Question());
                //設問種類区分
                paramMdl.setEnq210queType(queKbn);
                //必須フラグ
                paramMdl.setEnq220Require(queData.getEnq210Require());
                //その他入力有無
                paramMdl.setEnq220Other(String.valueOf(queData.getEnq210OtherFlg()));
                //説明
                paramMdl.setEnq220QueDesc(queData.getEnq210QueDesc());
                //添付区分
                paramMdl.setEnq220AttachKbn(String.valueOf(queData.getEnq210AttachKbn()));
    //            /** 添付ファイルSID */
    //            queData.setEnq210AttachSid(paramMdl.get);
    //            /** 添付名 */
    //            queData.setEnq210AttachName(paramMdl.get);
                //添付位置
                paramMdl.setEnq220AttachPos(String.valueOf(queData.getEnq210AttachPos()));
                //URL
                paramMdl.setEnq220Url(queData.getEnq210Url());
                //表示名
                paramMdl.setEnq220TempDspName(queData.getEnq210DspName());

                //初期値フラグ
                int initFlg = queData.getEnq210initFlg();
                paramMdl.setEnq220DefKbn(initFlg);
                if (initFlg == Enq210QueModel.INITFLG_SELECT) {
                    if (queKbn == GSConstEnquete.SYURUI_TEXT
                    || queKbn == GSConstEnquete.SYURUI_TEXTAREA) {
                        //初期値 テキスト
                        paramMdl.setEnq220DefTxt(queData.getEnq210initTxt());
                    } else if (queKbn == GSConstEnquete.SYURUI_INTEGER) {
                        //初期値 数値
                        paramMdl.setEnq220DefNum(
                                enqBiz.processNumber(queData.getEnq210initTxt()));
                    } else if (queKbn == GSConstEnquete.SYURUI_DAY) {
                        //初期値 日付
                        UDate date = queData.getEnq210initDate();
                        if (date != null) {
                            paramMdl.setEnq220DefDateYear(date.getYear());
                            paramMdl.setEnq220DefDateMonth(date.getMonth());
                            paramMdl.setEnq220DefDateDay(date.getIntDay());
                        }
                    }
                } else {
                     if (queKbn == GSConstEnquete.SYURUI_DAY) {
                         paramMdl.setEnq220DefDateYear(nowDate.getYear());
                         paramMdl.setEnq220DefDateMonth(nowDate.getMonth());
                         paramMdl.setEnq220DefDateDay(nowDate.getIntDay());
                    }
                }
                //範囲フラグ
                int rangeFlg = queData.getEnq210rangeFlg();
                paramMdl.setEnq220RngKbn(rangeFlg);
                if (rangeFlg == Enq210QueModel.RANGEFLG_SELECT) {
                    if (queKbn == GSConstEnquete.SYURUI_INTEGER) {
                        //範囲 数値
                        paramMdl.setEnq220RngStrNum(
                                enqBiz.processNumber(queData.getEnq210rangeTxtFr()));
                        paramMdl.setEnq220RngEndNum(
                                enqBiz.processNumber(queData.getEnq210rangeTxtTo()));
                    } else if (queKbn == GSConstEnquete.SYURUI_DAY) {
                        //範囲 日付
                        UDate date = queData.getEnq210rangeDateFr();
                        if (date != null) {
                            paramMdl.setEnq220RngStrDateYear(date.getYear());
                            paramMdl.setEnq220RngStrDateMonth(date.getMonth());
                            paramMdl.setEnq220RngStrDateDay(date.getIntDay());
                        }
                        date = queData.getEnq210rangeDateTo();
                        if (date != null) {
                            paramMdl.setEnq220RngEndDateYear(date.getYear());
                            paramMdl.setEnq220RngEndDateMonth(date.getMonth());
                            paramMdl.setEnq220RngEndDateDay(date.getIntDay());
                        }
                    }
                } else {
                    if (queKbn == GSConstEnquete.SYURUI_DAY) {
                        paramMdl.setEnq220RngStrDateYear(nowDate.getYear());
                        paramMdl.setEnq220RngStrDateMonth(nowDate.getMonth());
                        paramMdl.setEnq220RngStrDateDay(nowDate.getIntDay());
                        paramMdl.setEnq220RngEndDateYear(nextDate.getYear());
                        paramMdl.setEnq220RngEndDateMonth(nextDate.getMonth());
                        paramMdl.setEnq220RngEndDateDay(nextDate.getIntDay());
                    }
                }

                //単位
                if (queKbn == GSConstEnquete.SYURUI_INTEGER) {
                    paramMdl.setEnq220UnitNum(queData.getEnq210unitNum());
                }

                //枠線位置
                paramMdl.setEnq220LinePos(queData.getEnq210LinePos());

                paramMdl.setEnq220queId(queData.getEnq210Id());

                //選択肢を設定
                List<Enq210QueSubModel> queSubList = queData.getQueSubList();
                if (queSubList != null && !queSubList.isEmpty()) {
                    List<Enq220SubForm> subList = new ArrayList<Enq220SubForm>();
                    for (Enq210QueSubModel queSubMdl : queSubList) {
                        Enq220SubForm subForm = new Enq220SubForm();
                        BeanUtils.copyProperties(subForm, queSubMdl);
                        subList.add(subForm);
                    }
                    paramMdl.setSubListForm(subList);
                }
                //テンポラリディレクトリのコピー
                IOTools.copyDir(
                        new File(getQueTempPath(paramMdl.getEnq220queId(), reqMdl).getTempPath()),
                        new File(getQueEditTempPath(reqMdl).getTempPath()));

            } else {
                // 新規モード
                paramMdl.setEnq220Seq(-1);
                paramMdl.setEnq220Require("1");
                paramMdl.setEnq220AttachKbn(String.valueOf(GSConstEnquete.TEMP_OFF));
                paramMdl.setEnq220AttachPos(String.valueOf(GSConstEnquete.TEMP_POS_BOTTOM));
                paramMdl.setEnq220DefKbn(GSConstEnquete.INIT_OFF);
                paramMdl.setEnq220RngKbn(GSConstEnquete.RNG_OFF);

                paramMdl.setEnq220queId((new UDate()).getTimeStamp());

                //その他有無設定
                paramMdl.setEnq220Other(String.valueOf(GSConstEnquete.OTHER_OFF));
                //初期値を設定
                paramMdl.setEnq220DefDateYear(nowDate.getYear());
                paramMdl.setEnq220DefDateMonth(nowDate.getMonth());
                paramMdl.setEnq220DefDateDay(nowDate.getIntDay());
                //入力範囲開始を設定
                paramMdl.setEnq220RngStrDateYear(nowDate.getYear());
                paramMdl.setEnq220RngStrDateMonth(nowDate.getMonth());
                paramMdl.setEnq220RngStrDateDay(nowDate.getIntDay());
                //入力範囲終了を設定
                paramMdl.setEnq220RngEndDateYear(nextDate.getYear());
                paramMdl.setEnq220RngEndDateMonth(nextDate.getMonth());
                paramMdl.setEnq220RngEndDateDay(nextDate.getIntDay());

                //選択肢を設定
                int queType = paramMdl.getEnq210queType();
                if (queType == GSConstEnquete.SYURUI_SINGLE
                || queType == GSConstEnquete.SYURUI_MULTIPLE) {
                    List<Enq220SubForm> subList = new ArrayList<Enq220SubForm>();
                    int dspCnt = __getDspCount(queType);
                    for (int enqIndex = 0; enqIndex < dspCnt; enqIndex++) {
                        Enq220SubForm subForm = new Enq220SubForm();
                        subForm.setEnqIndex(enqIndex);
                        subList.add(subForm);
                    }
                    paramMdl.setSubListForm(subList);
                }

            }

            paramMdl.setEnq220initFlg(1);
        }

        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        if (paramMdl.getEnq220DefDate() == null) {
            dateBiz.setDateParam(paramMdl, "enq220DefDate", "enq220DefDateYear",
                    "enq220DefDateMonth", "enq220DefDateDay", null);
        }

        if (paramMdl.getEnq220RngStrDate() == null) {
            dateBiz.setDateParam(paramMdl, "enq220RngStrDate", "enq220RngStrDateYear",
                    "enq220RngStrDateMonth", "enq220RngStrDateDay", null);
        }

        if (paramMdl.getEnq220RngEndDate() == null) {
            dateBiz.setDateParam(paramMdl, "enq220RngEndDate", "enq220RngEndDateYear",
                    "enq220RngEndDateMonth", "enq220RngEndDateDay", null);
        }

        //設問番号(自動)
        paramMdl.setEnq220autoQueNo(__getAutoQueNo(reqMdl, paramMdl));

        // 表示モードの設定
        __setDspMode(paramMdl, reqMdl);

        //添付ファイル名の設定
        String queTempDir = getQueEditTempDir(reqMdl);
        CommonBiz cmnBiz = new CommonBiz();
        List<LabelValueBean> fileList = cmnBiz.getTempFileLabelList(queTempDir);
        if (fileList != null && !fileList.isEmpty()) {
            paramMdl.setEnq220fileName(fileList.get(0).getLabel());
        } else {
            paramMdl.setEnq220fileName(null);
        }
        paramMdl.setEnq220AttachKbn(
                String.valueOf(getQueAttachKbn(paramMdl.getEnq220fileName())));

        //添付ファイル名(設問情報)の設定
        biz210.setTempFileName(paramMdl, reqMdl);
    }

    /**
     * <br>[機  能] 設問情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエストモデル
     * @throws IOToolsException 設問情報の設定に失敗
     * @throws InvocationTargetException 設問 選択肢の読み込みに失敗
     * @throws IllegalAccessException 設問 選択肢の読み込みに失敗
     * @throws IOException  設問情報の設定に失敗
     */
    public void setQuestionData(Enq220ParamModel paramMdl,
                                            RequestModel reqMdl)
    throws IOToolsException, InvocationTargetException, IllegalAccessException, IOException {

        EnqCommonBiz enqBiz = new EnqCommonBiz();
        Enq210Biz biz210 = new Enq210Biz();
        List<Enq210QueModel> queList = biz210.readQueList(reqMdl);

        int queIndex = 0;
        Enq210QueModel queData = new Enq210QueModel();
        if (paramMdl.getEnq220editMode() == GSConstEnquete.EDITMODE_EDIT) {
            queIndex = paramMdl.getEnq210editQueIndex();
            for (Enq210QueModel editQueData : queList) {
                if (editQueData.getEnq210queIndex() == queIndex) {
                    queData = editQueData;
                }
            }
        } else {
            queIndex = queList.size();
            //ID
            queData.setEnq210Id(paramMdl.getEnq220queId());
            //インデックス
            queData.setEnq210queIndex(queIndex);
            //表示順
            queData.setEnq210DspOrder(queList.size() + 1);
            queList.add(queData);
        }

        //設問番号
        queData.setEnq210QueNo(paramMdl.getEnq220QueNo());
        //設問番号(自動)
        String autoQueNo = __getAutoQueNo(reqMdl, paramMdl);
        if (!StringUtil.isNullZeroString(autoQueNo)) {
            queData.setEnq210AutoQueNo(Integer.parseInt(autoQueNo));

            //設問番号が自動採番の場合、かつ設問番号が未設定の場合
            if (StringUtil.isNullZeroString(queData.getEnq210QueNo())) {
                queData.setEnq210QueNo(autoQueNo);
            }
        } else {
            queData.setEnq210AutoQueNo(0);
        }
        //設問連番
        queData.setEnq210Seq(paramMdl.getEnq220Seq());
        //設問
        queData.setEnq210Question(paramMdl.getEnq220Question());
        //設問種類区分
        queData.setEnq210QueKbn(paramMdl.getEnq210queType());
        //設問種類名称
        queData.setEnq210SyuruiName(
                Enq210Biz.getDspQueType(reqMdl, paramMdl.getEnq210queType()));
        //必須フラグ
        queData.setEnq210Require(paramMdl.getEnq220Require());
        //その他入力有無
        queData.setEnq210OtherFlg(NullDefault.getInt(paramMdl.getEnq220Other(), 0));
        //説明
        queData.setEnq210QueDesc(StringUtilHtml.removeIllegalTag(paramMdl.getEnq220QueDesc()));
        queData.setEnq210QueDescText(
                StringUtilHtml.deleteHtmlTagAndScriptStyleBlock(paramMdl.getEnq220QueDesc()));
        //添付区分
        queData.setEnq210AttachKbn(NullDefault.getInt(paramMdl.getEnq220AttachKbn(), 0));
//        /** 添付ファイルSID */
//        queData.setEnq210AttachSid(paramMdl.get);
        /** 添付名 */
        queData.setEnq210AttachName(paramMdl.getEnq220fileName());
        //添付位置
        queData.setEnq210AttachPos(NullDefault.getInt(paramMdl.getEnq220AttachPos(), 0));
        //URL
        queData.setEnq210Url(paramMdl.getEnq220Url());
        //表示名
        queData.setEnq210DspName(paramMdl.getEnq220TempDspName());

        // 設問区分
        int queKbn = queData.getEnq210QueKbn();
        // 初期値フラグ
        int initFlg = paramMdl.getEnq220DefKbn();
        queData.setEnq210initFlg(initFlg);
        // 初期値の判定
        if (initFlg == Enq210QueModel.INITFLG_SELECT) {

            // テキスト
            if (queKbn == GSConstEnquete.SYURUI_TEXT
             || queKbn == GSConstEnquete.SYURUI_TEXTAREA) {
                queData.setEnq210initTxt(paramMdl.getEnq220DefTxt());
            // 数値
            } else if (queKbn == GSConstEnquete.SYURUI_INTEGER) {
                queData.setEnq210initTxt(paramMdl.getEnq220DefNum());
            // 日付
            } else if (queKbn == GSConstEnquete.SYURUI_DAY) {
                UDate date = new UDate();
                date.setZeroHhMmSs();
                date.setDate(paramMdl.getEnq220DefDateYear(),
                             paramMdl.getEnq220DefDateMonth(),
                             paramMdl.getEnq220DefDateDay());
                queData.setEnq210initDate(date);
                // 表示用
                queData.setEnq210initDspDate(enqBiz.getStrDate(reqMdl, date));
            }
        } else {
            queData.setEnq210initDate(null);
            queData.setEnq210initDspDate(null);
            queData.setEnq210initTxt(null);
        }

        //範囲フラグ
        int rangeFlg = paramMdl.getEnq220RngKbn();
        queData.setEnq210rangeFlg(rangeFlg);
        if (rangeFlg == Enq210QueModel.RANGEFLG_SELECT) {
            if (queKbn == GSConstEnquete.SYURUI_INTEGER) {
                //範囲 数値
                queData.setEnq210rangeTxtFr(paramMdl.getEnq220RngStrNum());
                queData.setEnq210rangeTxtTo(paramMdl.getEnq220RngEndNum());
            } else if (queKbn == GSConstEnquete.SYURUI_DAY) {
                //範囲 日付
                UDate frDate = new UDate();
                frDate.setZeroHhMmSs();
                frDate.setDate(paramMdl.getEnq220RngStrDateYear(),
                                    paramMdl.getEnq220RngStrDateMonth(),
                                    paramMdl.getEnq220RngStrDateDay());
                queData.setEnq210rangeDateFr(frDate);
                queData.setEnq210rangeTxtFrDsp(enqBiz.getStrDate(reqMdl, frDate));

                UDate toDate = new UDate();
                toDate.setZeroHhMmSs();
                toDate.setDate(paramMdl.getEnq220RngEndDateYear(),
                                    paramMdl.getEnq220RngEndDateMonth(),
                                    paramMdl.getEnq220RngEndDateDay());
                queData.setEnq210rangeDateTo(toDate);
                queData.setEnq210rangeTxtToDsp(enqBiz.getStrDate(reqMdl, toDate));

            }
        } else {
            queData.setEnq210rangeTxtFr(null);
            queData.setEnq210rangeTxtTo(null);
            queData.setEnq210rangeDateFr(null);
            queData.setEnq210rangeDateTo(null);

            //日付、範囲 表示 初期化
            queData.setEnq210rangeTxtFrDsp(null);
            queData.setEnq210rangeTxtToDsp(null);
        }

        //単位
        if (queKbn == GSConstEnquete.SYURUI_INTEGER) {
            queData.setEnq210unitNum(paramMdl.getEnq220UnitNum());
        } else {
            queData.setEnq210unitNum(null);
        }
        //枠線位置
        queData.setEnq210LinePos(paramMdl.getEnq220LinePos());

        //選択肢
        List<Enq220SubForm> subList = paramMdl.getSubListToList();
        if (subList != null && !subList.isEmpty()) {
            List<Enq210QueSubModel> queSubList = new ArrayList<Enq210QueSubModel>();
            for (Enq220SubForm subForm : subList) {
                Enq210QueSubModel queSubMdl = new Enq210QueSubModel();
                BeanUtils.copyProperties(queSubMdl, subForm);
                queSubList.add(queSubMdl);
            }
            queData.setQueSubList(queSubList);
        }
        GSTemporaryPathModel tempMdl = getQueTempPath(paramMdl.getEnq220queId(), reqMdl);
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();
        tempUtil.deleteTempPath(tempMdl);
        tempUtil.createTempDir(tempMdl);
        //テンポラリディレクトリのコピー
        IOTools.copyDir(
                new File(getQueEditTempPath(reqMdl).getTempPath()),
                new File(tempMdl.getTempPath()));

        queList.set(queIndex, queData);
        biz210.saveQueList(reqMdl, queList);
    }

    /**
     * <br>[機  能] 表示モードの設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramModel パラメータモデル
     * @param reqMdl リクエスト情報
     */
    private void __setDspMode(Enq220ParamModel paramModel, RequestModel reqMdl) {

        int queKbn = paramModel.getEnq210queType();
        paramModel.setEnq220SyuruiName(
                Enq210Biz.getDspQueType(reqMdl, queKbn));

        switch (queKbn) {

            // 単一選択
            // 複数選択
            case(GSConstEnquete.SYURUI_SINGLE):
            case(GSConstEnquete.SYURUI_MULTIPLE):
                paramModel.setEnq220DspMode(GSConstEnquete.DSP_MODE_CHOICE);
                break;

            // テキスト入力
            case(GSConstEnquete.SYURUI_TEXT):
                paramModel.setEnq220DspMode(GSConstEnquete.DSP_MODE_TEXT);
                break;

            // テキスト入力（複数行）
            case(GSConstEnquete.SYURUI_TEXTAREA):
                paramModel.setEnq220DspMode(GSConstEnquete.DSP_MODE_TEXT);
                break;

            // 数値入力
            case(GSConstEnquete.SYURUI_INTEGER):
                paramModel.setEnq220DspMode(GSConstEnquete.DSP_MODE_INTEGER);
                break;

            // 日付入力
            case(GSConstEnquete.SYURUI_DAY):
                paramModel.setEnq220DspMode(GSConstEnquete.DSP_MODE_DAY);
                break;

            // コメント
            default:
                paramModel.setEnq220DspMode(GSConstEnquete.DSP_MODE_COMMENT);
        }
    }

    /**
     * <br>[機  能] 設問 添付ファイルの保存先ディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param queId 設問ID
     * @param reqMdl リクエスト情報
     * @return 設問情報一覧の保存先ディレクトリパス
     */
    public GSTemporaryPathModel getQueTempPath(String queId, RequestModel reqMdl) {
        return GSTemporaryPathModel.getInstance(reqMdl,
                GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID,
                GSConstEnquete.ENQ_QUESTION,
                queId);

    }
    /**
     * <br>[機  能] 設問 添付ファイルの保存先ディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param queId 設問ID
     * @param reqMdl リクエスト情報
     * @param tempDir テンポラリディレクトリパス
     * @return 設問情報一覧の保存先ディレクトリパス
     */
    public String getQueTempDir(String queId, RequestModel reqMdl,
                                            String tempDir) {
        //$GSTEMPDIR/enquete/セッションID/queData/設問ID/セッションID/
        //設問IDは設問情報読込時の日時(yyyyMMddHHmmssSSS) + 設問の連番(設問情報の連番ではなく、設問情報の登録順)
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String queTempDir = temp.getTempPath(reqMdl,
                GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID,
                GSConstEnquete.ENQ_QUESTION,
                queId);

        return queTempDir;
    }

    /**
     * <br>[機  能] 設問編集時 添付ファイルの保存先ディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return 設問情報一覧の保存先ディレクトリパス
     */
    public String getQueEditTempDir(RequestModel reqMdl) {
        //$GSTEMPDIR/enquete/セッションID/queData/設問ID/セッションID/
        //設問IDは設問情報読込時の日時(yyyyMMddHHmmssSSS) + 設問の連番(設問情報の連番ではなく、設問情報の登録順)
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String queTempDir = temp.getTempPath(reqMdl,
                GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID,
                GSConstEnquete.ENQ_QUESTION,
                GSConstEnquete.ENQ_QUESTION_EDIT);

        return queTempDir;
    }
    /**
     * <br>[機  能] 設問 添付ファイルの保存先ディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return 設問情報一覧の保存先ディレクトリパス
     */
    public GSTemporaryPathModel getQueEditTempPath(RequestModel reqMdl) {
        return GSTemporaryPathModel.getInstance(reqMdl,
                GSConstEnquete.PLUGIN_ID_ENQUETE, TEMP_DIRECTORY_ID,
                GSConstEnquete.ENQ_QUESTION,
                GSConstEnquete.ENQ_QUESTION_EDIT);

    }

    /**
     * <br>[機  能] 選択肢の並び替えを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param sortType 上へ or 下へ
     */
    public void sortChoice(Enq220ParamModel paramMdl, int sortType) {

        int sortIdx = paramMdl.getEnq220selectRow();
        List<Enq220SubForm> subList = paramMdl.getSubListToList();

        if ((sortType == SORTTYPE_UP_ && sortIdx <= 0)
        || (sortType == SORTTYPE_DOWN_ && sortIdx + 1 >= subList.size())) {
            return;
        }

        List<Enq220SubForm> newSubList = new ArrayList<Enq220SubForm>();
        for (int idx = 0; idx < subList.size(); idx++) {
            if (subList.get(idx).getEnqIndex() == sortIdx) {
                if (sortType == SORTTYPE_UP_) {
                    newSubList.add(idx - 1, subList.get(idx));
                    newSubList.get(idx - 1).setEnqIndex(
                            newSubList.get(idx).getEnqIndex() - 1);
                    newSubList.get(idx).setEnqIndex(
                            newSubList.get(idx).getEnqIndex() + 1);

                    paramMdl.setEnq220selectRow(sortIdx - 1);
                } else if (sortType == SORTTYPE_DOWN_) {
                    newSubList.add(subList.get(idx + 1));
                    newSubList.add(subList.get(idx));
                    newSubList.get(idx).setEnqIndex(
                            newSubList.get(idx).getEnqIndex() - 1);
                    newSubList.get(idx + 1).setEnqIndex(
                            newSubList.get(idx).getEnqIndex() + 1);

                    paramMdl.setEnq220selectRow(sortIdx + 1);
                    idx++;
                }
            } else {
                newSubList.add(subList.get(idx));
            }
        }

        paramMdl.setSubListForm(newSubList);
    }

    /**
     * <br>[機  能] 選択肢の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     */
    public void deleteChoice(Enq220ParamModel paramMdl) {

        int delIdx = paramMdl.getEnq220deleteRow();
        List<Enq220SubForm> subList = paramMdl.getSubListToList();

        boolean deleteFlg = false;
        for (int idx = 0; idx < subList.size(); idx++) {
            if (subList.get(idx).getEnqIndex() == delIdx) {
                subList.remove(idx);
                deleteFlg = true;
                idx--;
            } else if (deleteFlg) {
                subList.get(idx).setEnqIndex(
                        subList.get(idx).getEnqIndex() - 1);
            }
        }

        paramMdl.setSubListForm(subList);
    }

    /**
     * <br>[機  能] 設問添付ファイル名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param queId 設問ID
     * @param reqMdl リクエスト情報
     * @param tempDir テンポラリディレクトリパス
     * @return 添付ファイル名
     * @throws IOToolsException 添付ファイル情報の取得に失敗
     */
    public String getQueTempFileName(String queId, RequestModel reqMdl, String tempDir)
    throws IOToolsException {
        String fileName = null;

        String queTempDir = getQueTempDir(queId, reqMdl, tempDir);
        CommonBiz cmnBiz = new CommonBiz();
        List<LabelValueBean> fileList = cmnBiz.getTempFileLabelList(queTempDir);
        if (fileList != null && !fileList.isEmpty()) {
            fileName = fileList.get(0).getLabel();
        }
        return fileName;
    }
    /**
     * <br>[機  能] 設問添付ファイル名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return 添付ファイル名
     * @throws IOToolsException 添付ファイル情報の取得に失敗
     */
    public String getQueEditTempFileName(RequestModel reqMdl)
    throws IOToolsException {
        String fileName = null;

        String queTempDir = getQueEditTempDir(reqMdl);
        CommonBiz cmnBiz = new CommonBiz();
        List<LabelValueBean> fileList = cmnBiz.getTempFileLabelList(queTempDir);
        if (fileList != null && !fileList.isEmpty()) {
            fileName = fileList.get(0).getLabel();
        }
        return fileName;
    }

    /**
     * <br>[機  能] 自動採番時の設問連番を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータ情報
     * @return 自動採番時の設問連番
     * @throws IOToolsException 設問情報の設定に失敗
     * @throws InvocationTargetException 設問 選択肢の読み込みに失敗
     * @throws IllegalAccessException 設問 選択肢の読み込みに失敗
     */
    private String __getAutoQueNo(RequestModel reqMdl, Enq220ParamModel paramMdl)
    throws IOToolsException, InvocationTargetException, IllegalAccessException {
        if (paramMdl.getEnq210queType() == GSConstEnquete.SYURUI_COMMENT) {
            return null;
        }

        Enq210Biz biz210 = new Enq210Biz();
        List<Enq210QueModel> queList = biz210.readQueList(reqMdl);
        int autoQueNo = 1;
        if (queList != null && !queList.isEmpty()) {
            for (Enq210QueModel queData : queList) {
                if (paramMdl.getEnq220editMode() == GSConstEnquete.EDITMODE_EDIT) {
                    int queIndex = paramMdl.getEnq210editQueIndex();
                    if (queData.getEnq210queIndex() == queIndex) {
                        autoQueNo = queData.getEnq210AutoQueNo();
                        break;
                    }
                } else {
                    if (queData.getEnq210AutoQueNo() > autoQueNo) {
                        autoQueNo = queData.getEnq210AutoQueNo();
                    }
                }
            }

            if (paramMdl.getEnq220editMode() != GSConstEnquete.EDITMODE_EDIT) {
                autoQueNo++;
            }
        }

        return String.valueOf(autoQueNo);
    }

    /**
     * <br>[機  能] 指定したファイル名から添付区分を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param fileName ファイル名
     * @return 添付区分
     */
    public int getQueAttachKbn(String fileName) {
        if (StringUtil.isNullZeroString(fileName)) {
            return GSConstEnquete.EQM_ATTACH_KBN_NONE;
        }

        //指定したファイルが画像ファイルかを判定
        if (Cmn110Biz.isExtensionOk(fileName)) {
            return GSConstEnquete.EQM_ATTACH_KBN_IMAGE;
        }

        return GSConstEnquete.EQM_ATTACH_KBN_FILE;
    }

    /**
     * <br>[機  能] 選択肢の初期表示件数を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param queType 設問種類
     * @return 選択肢の初期表示件数
     */
    private int __getDspCount(int queType) {

        int ret = 0;
        if (queType == GSConstEnquete.SYURUI_SINGLE) {
            ret = INIT_SINGLE_COUNT;
        } else if (queType == GSConstEnquete.SYURUI_MULTIPLE) {
            ret = INIT_MULTIPLE_COUNT;
        }

        return ret;
    }
}
