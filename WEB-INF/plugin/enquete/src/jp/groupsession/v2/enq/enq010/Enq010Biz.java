package jp.groupsession.v2.enq.enq010;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.dao.EnqAnsMainDao;
import jp.groupsession.v2.enq.dao.EnqMainDao;
import jp.groupsession.v2.enq.dao.EnqMenuListDao;
import jp.groupsession.v2.enq.dao.EnqTypeDao;
import jp.groupsession.v2.enq.model.EnqAdmConfModel;
import jp.groupsession.v2.enq.model.EnqMenuListModel;
import jp.groupsession.v2.enq.model.EnqPriConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アンケート一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq010Biz {
    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param cmd コマンド
     * @throws SQLException 実行例外
     * @throws Exception 実行例外
     */
    public void setInitData(Enq010ParamModel paramMdl,
            RequestModel reqMdl,
            Connection con,
            String cmd)
                    throws SQLException, Exception {

        if (paramMdl.getEnq010initFlg() != 1) {
            paramMdl.setEnq010type(-1);
            paramMdl.setEnq010keywordSimple(null);
            paramMdl.setEnq010keyword(null);
            paramMdl.setEnq010keywordType(0);
            paramMdl.setEnq010sendGroup(-1);
            paramMdl.setEnq010sendUser(-1);
            paramMdl.setEnq010sendInput(0);
            paramMdl.setEnq010sendInputText(null);
            paramMdl.setEnq010sortKey(Enq010Const.SORTKEY_OPEN);
            paramMdl.setEnq010order(Enq010Const.ORDER_DESC);

            UDate now = new UDate();
            paramMdl.setEnq010makeDateKbn(Enq010Const.DATE_NON);
            paramMdl.setEnq010makeDateFromYear(now.getYear());
            paramMdl.setEnq010makeDateFromMonth(now.getMonth());
            paramMdl.setEnq010makeDateFromDay(now.getIntDay());
            paramMdl.setEnq010makeDateToYear(now.getYear());
            paramMdl.setEnq010makeDateToMonth(now.getMonth());
            paramMdl.setEnq010makeDateToDay(now.getIntDay());
            
            paramMdl.setEnq010pubDateKbn(Enq010Const.DATE_NON);
            paramMdl.setEnq010pubDateFromYear(now.getYear());
            paramMdl.setEnq010pubDateFromMonth(now.getMonth());
            paramMdl.setEnq010pubDateFromDay(now.getIntDay());
            paramMdl.setEnq010pubDateToYear(now.getYear());
            paramMdl.setEnq010pubDateToMonth(now.getMonth());
            paramMdl.setEnq010pubDateToDay(now.getIntDay());
            paramMdl.setEnq010ansDateKbn(Enq010Const.DATE_NON);
            paramMdl.setEnq010ansDateFromYear(now.getYear());
            paramMdl.setEnq010ansDateFromMonth(now.getMonth());
            paramMdl.setEnq010ansDateFromDay(now.getIntDay());
            paramMdl.setEnq010ansDateToYear(now.getYear());
            paramMdl.setEnq010ansDateToMonth(now.getMonth());
            paramMdl.setEnq010ansDateToDay(now.getIntDay());
            
            paramMdl.setEnq010resPubDateKbn(Enq010Const.DATE_NON);
            paramMdl.setEnq010resPubDateFromYear(now.getYear());
            paramMdl.setEnq010resPubDateFromMonth(now.getMonth());
            paramMdl.setEnq010resPubDateFromDay(now.getIntDay());
            paramMdl.setEnq010resPubDateToYear(now.getYear());
            paramMdl.setEnq010resPubDateToMonth(now.getMonth());
            paramMdl.setEnq010resPubDateToDay(now.getIntDay());
            
            paramMdl.setEnq010priority(
                    new int[] {GSConstEnquete.JUUYOU_0,
                            GSConstEnquete.JUUYOU_1,
                            GSConstEnquete.JUUYOU_2});

            if (cmd.equals("")) {
                // セッションユーザ情報を取得
                BaseUserModel usModel = reqMdl.getSmodel();
                int sessionUsrSid = usModel.getUsrsid();

                EnqCommonBiz enqBiz = new EnqCommonBiz();
                EnqAdmConfModel amdl = enqBiz.getAdmConfData(con, sessionUsrSid);
                int folderNo = 0;
                int canAnswer = 0;

                if (amdl.getEacListCntUse() == GSConstEnquete.ENQ_DISP_USER) {
                    EnqPriConfModel pconf = enqBiz.getPriConfData(con, sessionUsrSid);
                    if (pconf != null) {
                        folderNo = pconf.getEpcListFolder();
                        canAnswer = pconf.getEpcCanAnswer();
                    }
                } else {
                    folderNo = amdl.getEacFolderSelect();
                    canAnswer = amdl.getEacCanAnswer();
                }

                if (folderNo < 10) {
                    paramMdl.setEnq010folder(0);
                    paramMdl.setEnq010subFolder(folderNo);
                } else {
                    paramMdl.setEnq010folder(folderNo / 10);
                    paramMdl.setEnq010subFolder(folderNo % 10);
                }

                paramMdl.setEnq010pageTop(1);
                paramMdl.setEnq010pageBottom(1);
                _setSearchParamSimple(paramMdl);
                if (canAnswer == 1) {
                    paramMdl.setEnq010statusAnsOverSimple(String.valueOf(canAnswer));
                    paramMdl.setEnq010svStatusAnsOverSimple(String.valueOf(canAnswer));
                }
            }
            int[] status = null;
            if (paramMdl.getEnq010folder() == Enq010Const.FOLDER_RECEIVE) {
                if (paramMdl.getEnq010subFolder() == Enq010Const.SUBFOLDER_UNANS) {
                    status = new int[] {Enq010Const.STATUS_NOTANS};
                } else if (paramMdl.getEnq010subFolder() == Enq010Const.SUBFOLDER_REPLIED) {
                    status = new int[] {Enq010Const.STATUS_ANS};
                } else {
                    status = new int[]  {Enq010Const.STATUS_NOTANS,
                            Enq010Const.STATUS_ANS};
                }
            } else if (paramMdl.getEnq010folder() == Enq010Const.FOLDER_SEND) {
                if (paramMdl.getEnq010subFolder() == Enq010Const.SUBFOLDER_NOT_PUBLIC) {
                    status = new int[] {Enq010Const.STATUS_NOTPUB};
                } else if (paramMdl.getEnq010subFolder() == Enq010Const.SUBFOLDER_PUBLIC) {
                    status = new int[] {Enq010Const.STATUS_PUB};
                } else if (paramMdl.getEnq010subFolder() == Enq010Const.SUBFOLDER_COMP_ANS) {
                    status = new int[] {Enq010Const.STATUS_ANSEXIT};
                } else if (paramMdl.getEnq010subFolder() == Enq010Const.SUBFOLDER_COMP_PUB) {
                    status = new int[] {Enq010Const.STATUS_PUBEXIT};
                } else {
                    status = new int[] {Enq010Const.STATUS_NOTPUB,
                            Enq010Const.STATUS_PUB,
                            Enq010Const.STATUS_ANSEXIT,
                            Enq010Const.STATUS_PUBEXIT};
                }
            }

            if (!cmd.equals("")) {
                /** 匿名 状態 期限切れ(検索条件保持) */
                paramMdl.setEnq010statusAnsOver(new int[] {Enq010Const.PUBLIC_ANSFLG_NG,
                        Enq010Const.PUBLIC_ANSFLG_OK});
                /** 匿名 状態 期限切れ(検索条件保持) */
                paramMdl.setEnq010statusAnsOverSimple(
                        String.valueOf(Enq010Const.SEARCH_ANSFLGOK_NOTONLY));

                paramMdl.setEnq010status(status);

                _setSearchParam(paramMdl);
            }

            paramMdl.setEnq010initFlg(1);
        }
        
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        if (paramMdl.getEnq010makeDateFrom() == null) {
            dateBiz.setDateParam(paramMdl, "enq010makeDateFrom", "enq010makeDateFromYear",
                    "enq010makeDateFromMonth", "enq010makeDateFromDay", null);
        }
        
        if (paramMdl.getEnq010makeDateTo() == null) {
            dateBiz.setDateParam(paramMdl, "enq010makeDateTo", "enq010makeDateToYear",
                    "enq010makeDateToMonth", "enq010makeDateToDay", null);
        }

        if (paramMdl.getEnq010ansDateFrom() == null) {
            dateBiz.setDateParam(paramMdl, "enq010ansDateFrom",
                    "enq010ansDateFromYear", "enq010ansDateFromMonth",
                    "enq010ansDateFromDay", null);
        }
        
        if (paramMdl.getEnq010ansDateTo() == null) {
            dateBiz.setDateParam(paramMdl, "enq010ansDateTo",
                    "enq010ansDateToYear", "enq010ansDateToMonth",
                    "enq010ansDateToDay", null);
        }
        
        if (paramMdl.getEnq010resPubDateFrom() == null) {
            dateBiz.setDateParam(paramMdl, "enq010resPubDateFrom",
                    "enq010resPubDateFromYear", "enq010resPubDateFromMonth",
                    "enq010resPubDateFromDay", null);
        }
        
        if (paramMdl.getEnq010resPubDateTo() == null) {
            dateBiz.setDateParam(paramMdl, "enq010resPubDateTo",
                    "enq010resPubDateToYear", "enq010resPubDateToMonth",
                    "enq010resPubDateToDay", null);
        }
        
        //管理者フラグ、個人設定フラグを設定
        paramMdl.setEnq010adminUser(EnqCommonBiz.isGsEnqAdmin(reqMdl, con));
        EnqCommonBiz enqBiz = new EnqCommonBiz();
        paramMdl.setEnq010psnFlg(
                enqBiz.checkPriPerm(reqMdl, con, GSConstEnquete.DSP_ID_800));

        //アンケート作成対象者フラグを設定
        paramMdl.setEnq010crtUser(enqBiz.isEnqCrtUser(con, reqMdl));

        // 未回答件数を取得
        int userSid = reqMdl.getSmodel().getUsrsid();
        EnqAnsMainDao dao = new EnqAnsMainDao(con);
        paramMdl.setEnq010UnansCount(
                dao.count(userSid, GSConstEnquete.ANS_KBN_UNANSWERED));

        //未公開件数を取得
        Enq010Dao dao010 = new Enq010Dao(con);
        paramMdl.setEnq010notPublicCount(
                dao010.getEnqSendCount(userSid, Enq010Const.SUBFOLDER_NOT_PUBLIC));

        //公開件数を取得
        paramMdl.setEnq010publicCount(
                dao010.getEnqSendCount(userSid, Enq010Const.SUBFOLDER_PUBLIC));

        //草稿件数を取得
        paramMdl.setEnq010draftCount(
                dao010.getEnqDraftCount(userSid));

        //アンケート種類を設定
        GsMessage gsMsg = new GsMessage(reqMdl);
        List<LabelValueBean> enqTypeList = new ArrayList<LabelValueBean>();
        enqTypeList.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"),  "-1"));
        EnqTypeDao enqTypeDao = new EnqTypeDao(con);
        enqTypeList.addAll(enqTypeDao.getEnqTypeList());
        paramMdl.setEnqTypeList(enqTypeList);

        //発信者 グループを設定
        GroupBiz grpBiz = new GroupBiz();
        paramMdl.setEnqSendGroupList(
                grpBiz.getGroupCombLabelList(con, true, gsMsg));

        //発信者 ユーザを設定
        UserBiz usrBiz = new UserBiz();
        paramMdl.setEnqSendUserList(
                usrBiz.getNormalUserLabelList(con, paramMdl.getEnq010sendGroup(),
                        null, true, gsMsg));

        //アンケート情報一覧を取得する
        Enq010SearchModel searchMdl = __createSearchModel(con, paramMdl, reqMdl);

        int searchCnt = dao010.getEnqueteCount(searchMdl, reqMdl);

        //ページ調整
        int pageMaxCnt = enqBiz.getMaxListCnt(con, reqMdl.getSmodel().getUsrsid());
        searchMdl.setMaxCount(pageMaxCnt);
        int maxPage = searchCnt / pageMaxCnt;
        if ((searchCnt % pageMaxCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getEnq010pageTop();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setEnq010pageTop(page);
        paramMdl.setEnq010pageBottom(page);

        //ページコンボ設定
        if (maxPage > 1) {
            paramMdl.setPageList(PageUtil.createPageOptions(searchCnt, pageMaxCnt));
        }

        searchMdl.setPage(paramMdl.getEnq010pageTop());
        List<Enq010EnqueteModel> enqList = dao010.getEnqueteList(searchMdl, reqMdl);
        for (Enq010EnqueteModel enqMdl : enqList) {
            enqMdl.setAnswerPath(__createAnswerUrl(reqMdl, enqMdl.getEnqSid()));
        }
        paramMdl.setEnq010EnqueteList(enqList);

        // 左メニューのテンプレート一覧取得
        EnqMenuListDao menuDao = new EnqMenuListDao(con);
        List<EnqMenuListModel> templateList
        = menuDao.selectMenuList(GSConstEnquete.DATA_KBN_TEMPLATE);
        if (templateList != null && !templateList.isEmpty()) {
            String tempName = null;
            for (int templateIdx = 0; templateIdx < templateList.size(); templateIdx++) {
                String wk = __getTrimRangeString(templateList.get(templateIdx).getEmnTitle(),
                        GSConstEnquete.MENU_RANGE_TEMPLAGE_NAME);
                tempName = StringUtilHtml.transToHTmlPlusAmparsant(NullDefault.getString(wk, ""));
                templateList.get(templateIdx).setViewEmnTitle(tempName);
            }
        }
        paramMdl.setEnq010TemplateList(templateList);
    }

    /**
     * <br>[機  能] 入力された検索条件を検索条件保持パラメータへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    protected void _setSearchParam(Enq010ParamModel paramMdl) {
        /** キーワード(簡易検索)(検索条件保持) */
        paramMdl.setEnq010svKeywordSimple(null);

        /** 種類(検索条件保持) */
        paramMdl.setEnq010svType(paramMdl.getEnq010type());
        /** キーワード(検索条件保持) */
        paramMdl.setEnq010svKeyword(paramMdl.getEnq010keyword());
        /** キーワード 種別(検索条件保持) */
        paramMdl.setEnq010svKeywordType(paramMdl.getEnq010keywordType());
        /** 発信者 グループ(検索条件保持) */
        paramMdl.setEnq010svSendGroup(paramMdl.getEnq010sendGroup());
        /** 発信者 ユーザ(検索条件保持) */
        paramMdl.setEnq010svSendUser(paramMdl.getEnq010sendUser());
        /** 発信者 入力(検索条件保持) */
        paramMdl.setEnq010svSendInput(paramMdl.getEnq010sendInput());
        /** 発信者 テキスト(検索条件保持) */
        paramMdl.setEnq010svSendInputText(paramMdl.getEnq010sendInputText());
        /** 作成日 指定なし(検索条件保持) */
        paramMdl.setEnq010svMakeDateKbn(paramMdl.getEnq010makeDateKbn());
        /** 作成日 開始 年(検索条件保持) */
        paramMdl.setEnq010svMakeDateFromYear(paramMdl.getEnq010makeDateFromYear());
        /** 作成日 開始 月(検索条件保持) */
        paramMdl.setEnq010svMakeDateFromMonth(paramMdl.getEnq010makeDateFromMonth());
        /** 作成日 開始 日(検索条件保持) */
        paramMdl.setEnq010svMakeDateFromDay(paramMdl.getEnq010makeDateFromDay());
        /** 作成日 終了 年(検索条件保持) */
        paramMdl.setEnq010svMakeDateToYear(paramMdl.getEnq010makeDateToYear());
        /** 作成日 終了 月(検索条件保持) */
        paramMdl.setEnq010svMakeDateToMonth(paramMdl.getEnq010makeDateToMonth());
        /** 作成日 終了 日(検索条件保持) */
        paramMdl.setEnq010svMakeDateToDay(paramMdl.getEnq010makeDateToDay());
        /** 作成日 開始 年月日(検索条件保持) */
        paramMdl.setEnq010svMakeDateFrom(paramMdl.getEnq010makeDateFrom());
        /** 作成日 終了 年月日(検索条件保持) */
        paramMdl.setEnq010svMakeDateTo(paramMdl.getEnq010makeDateTo());
        /** 公開期間 指定なし(検索条件保持) */
        paramMdl.setEnq010svPubDateKbn(paramMdl.getEnq010pubDateKbn());
        /** 公開期間 開始 年(検索条件保持) */
        paramMdl.setEnq010svPubDateFromYear(paramMdl.getEnq010pubDateFromYear());
        /** 公開期間 開始 月(検索条件保持) */
        paramMdl.setEnq010svPubDateFromMonth(paramMdl.getEnq010pubDateFromMonth());
        /** 公開期間 開始 日(検索条件保持) */
        paramMdl.setEnq010svPubDateFromDay(paramMdl.getEnq010pubDateFromDay());
        /** 公開期間 終了 年(検索条件保持) */
        paramMdl.setEnq010svPubDateToYear(paramMdl.getEnq010pubDateToYear());
        /** 公開期間 終了 月(検索条件保持) */
        paramMdl.setEnq010svPubDateToMonth(paramMdl.getEnq010pubDateToMonth());
        /** 公開期間 終了 日(検索条件保持) */
        paramMdl.setEnq010svPubDateToDay(paramMdl.getEnq010pubDateToDay());
        /** 回答期限 指定なし(検索条件保持) */
        paramMdl.setEnq010svAnsDateKbn(paramMdl.getEnq010ansDateKbn());
        /** 回答期限 開始 年(検索条件保持) */
        paramMdl.setEnq010svAnsDateFromYear(paramMdl.getEnq010ansDateFromYear());
        /** 回答期限 開始 月(検索条件保持) */
        paramMdl.setEnq010svAnsDateFromMonth(paramMdl.getEnq010ansDateFromMonth());
        /** 回答期限 開始 日(検索条件保持) */
        paramMdl.setEnq010svAnsDateFromDay(paramMdl.getEnq010ansDateFromDay());
        /** 回答期限 終了 年(検索条件保持) */
        paramMdl.setEnq010svAnsDateToYear(paramMdl.getEnq010ansDateToYear());
        /** 回答期限 終了 月(検索条件保持) */
        paramMdl.setEnq010svAnsDateToMonth(paramMdl.getEnq010ansDateToMonth());
        /** 回答期限 終了 日(検索条件保持) */
        paramMdl.setEnq010svAnsDateToDay(paramMdl.getEnq010ansDateToDay());
        /** 回答期限 開始 年月日(検索条件保持) */
        paramMdl.setEnq010svAnsDateFrom(paramMdl.getEnq010ansDateFrom());
        /** 回答期限 終了 年月日(検索条件保持) */
        paramMdl.setEnq010svAnsDateTo(paramMdl.getEnq010ansDateTo());
        /** 結果公開期間 指定なし(検索条件保持) */
        paramMdl.setEnq010svResPubDateKbn(paramMdl.getEnq010resPubDateKbn());
        /** 結果公開期間 開始 年(検索条件保持) */
        paramMdl.setEnq010svResPubDateFromYear(paramMdl.getEnq010resPubDateFromYear());
        /** 結果公開期間 開始 月(検索条件保持) */
        paramMdl.setEnq010svResPubDateFromMonth(paramMdl.getEnq010resPubDateFromMonth());
        /** 結果公開期間 開始 日(検索条件保持) */
        paramMdl.setEnq010svResPubDateFromDay(paramMdl.getEnq010resPubDateFromDay());
        /** 結果公開期間 終了 年(検索条件保持) */
        paramMdl.setEnq010svResPubDateToYear(paramMdl.getEnq010resPubDateToYear());
        /** 結果公開期間 終了 月(検索条件保持) */
        paramMdl.setEnq010svResPubDateToMonth(paramMdl.getEnq010resPubDateToMonth());
        /** 結果公開期間 終了 日(検索条件保持) */
        paramMdl.setEnq010svResPubDateToDay(paramMdl.getEnq010resPubDateToDay());
        /** 結果公開期間 開始 年月日(検索条件保持) */
        paramMdl.setEnq010svResPubDateFrom(paramMdl.getEnq010resPubDateFrom());
        /** 結果公開期間 終了 年月日(検索条件保持) */
        paramMdl.setEnq010svResPubDateTo(paramMdl.getEnq010resPubDateTo());

        /** 重要度(検索条件保持) */
        paramMdl.setEnq010svPriority(paramMdl.getEnq010priority());
        /** 状態(検索条件保持) */
        paramMdl.setEnq010svStatus(paramMdl.getEnq010status());
        /** 匿名 匿名(検索条件保持) */
        paramMdl.setEnq010svAnony(paramMdl.getEnq010anony());

        /** 匿名 状態 期限切れ(検索条件保持) */
        paramMdl.setEnq010svStatusAnsOver(paramMdl.getEnq010statusAnsOver());

        /** 匿名 状態 期限切れ(検索条件保持) */
        paramMdl.setEnq010svStatusAnsOverSimple(null);

        /** 匿名 状態 期限切れ(検索条件保持) */
        int[] statusAO = paramMdl.getEnq010svStatusAnsOver();
        boolean simple_ansover_flg = false;
        if (statusAO != null && statusAO.length > 0) {
            simple_ansover_flg = true;
            for (int aof : statusAO) {
                if (aof == Enq010Const.PUBLIC_ANSFLG_NG) {
                    simple_ansover_flg = false;
                }
            }
        }
        if (simple_ansover_flg) {
            paramMdl.setEnq010statusAnsOverSimple(
                    String.valueOf(Enq010Const.SEARCH_ANSFLGOK_ONLY));
        } else {
            paramMdl.setEnq010statusAnsOverSimple(
                    String.valueOf(Enq010Const.SEARCH_ANSFLGOK_NOTONLY));
        }

    }

    /**
     * <br>[機  能] 入力された検索条件を検索条件保持パラメータへ設定する
     * <br>[解  説] 簡易検索時
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    protected void _setSearchParamSimple(Enq010ParamModel paramMdl) {
        /** キーワード(簡易検索)(検索条件保持) */
        paramMdl.setEnq010svKeywordSimple(paramMdl.getEnq010keywordSimple());

        /** 種類(検索条件保持) */
        paramMdl.setEnq010svType(0);
        /** キーワード(検索条件保持) */
        paramMdl.setEnq010svKeyword(null);
        /** キーワード 種別(検索条件保持) */
        paramMdl.setEnq010svKeywordType(0);
        /** 発信者 グループ(検索条件保持) */
        paramMdl.setEnq010svSendGroup(0);
        /** 発信者 ユーザ(検索条件保持) */
        paramMdl.setEnq010svSendUser(0);
        /** 発信者 入力(検索条件保持) */
        paramMdl.setEnq010svSendInput(0);
        /** 発信者 テキスト(検索条件保持) */
        paramMdl.setEnq010svSendInputText(null);
        /** 作成日 指定なし(検索条件保持) */
        paramMdl.setEnq010svMakeDateKbn(0);
        /** 作成日 開始 年(検索条件保持) */
        paramMdl.setEnq010svMakeDateFromYear(0);
        /** 作成日 開始 月(検索条件保持) */
        paramMdl.setEnq010svMakeDateFromMonth(0);
        /** 作成日 開始 日(検索条件保持) */
        paramMdl.setEnq010svMakeDateFromDay(0);
        /** 作成日 終了 年(検索条件保持) */
        paramMdl.setEnq010svMakeDateToYear(0);
        /** 作成日 終了 月(検索条件保持) */
        paramMdl.setEnq010svMakeDateToMonth(0);
        /** 作成日 終了 日(検索条件保持) */
        paramMdl.setEnq010svMakeDateToDay(0);
        /** 公開期間 指定なし(検索条件保持) */
        paramMdl.setEnq010svPubDateKbn(0);
        /** 公開期間 開始 年(検索条件保持) */
        paramMdl.setEnq010svPubDateFromYear(0);
        /** 公開期間 開始 月(検索条件保持) */
        paramMdl.setEnq010svPubDateFromMonth(0);
        /** 公開期間 開始 日(検索条件保持) */
        paramMdl.setEnq010svPubDateFromDay(0);
        /** 公開期間 終了 年(検索条件保持) */
        paramMdl.setEnq010svPubDateToYear(0);
        /** 公開期間 終了 月(検索条件保持) */
        paramMdl.setEnq010svPubDateToMonth(0);
        /** 公開期間 終了 日(検索条件保持) */
        paramMdl.setEnq010svPubDateToDay(0);
        /** 回答期限 指定なし(検索条件保持) */
        paramMdl.setEnq010svAnsDateKbn(0);
        /** 回答期限 開始 年(検索条件保持) */
        paramMdl.setEnq010svAnsDateFromYear(0);
        /** 回答期限 開始 月(検索条件保持) */
        paramMdl.setEnq010svAnsDateFromMonth(0);
        /** 回答期限 開始 日(検索条件保持) */
        paramMdl.setEnq010svAnsDateFromDay(0);
        /** 回答期限 終了 年(検索条件保持) */
        paramMdl.setEnq010svAnsDateToYear(0);
        /** 回答期限 終了 月(検索条件保持) */
        paramMdl.setEnq010svAnsDateToMonth(0);
        /** 回答期限 終了 日(検索条件保持) */
        paramMdl.setEnq010svAnsDateToDay(0);
        /** 結果公開期間 指定なし(検索条件保持) */
        paramMdl.setEnq010svResPubDateKbn(0);
        /** 結果公開期間 開始 年(検索条件保持) */
        paramMdl.setEnq010svResPubDateFromYear(0);
        /** 結果公開期間 開始 月(検索条件保持) */
        paramMdl.setEnq010svResPubDateFromMonth(0);
        /** 結果公開期間 開始 日(検索条件保持) */
        paramMdl.setEnq010svResPubDateFromDay(0);
        /** 結果公開期間 終了 年(検索条件保持) */
        paramMdl.setEnq010svResPubDateToYear(0);
        /** 結果公開期間 終了 月(検索条件保持) */
        paramMdl.setEnq010svResPubDateToMonth(0);
        /** 結果公開期間 終了 日(検索条件保持) */
        paramMdl.setEnq010svResPubDateToDay(0);
        /** 重要度(検索条件保持) */
        paramMdl.setEnq010svPriority(null);
        /** 状態(検索条件保持) */
        int[] status = null;
        if (paramMdl.getEnq010folder() == Enq010Const.FOLDER_RECEIVE) {
            if (paramMdl.getEnq010subFolder() == Enq010Const.SUBFOLDER_UNANS) {
                status = new int[] {Enq010Const.STATUS_NOTANS};
            } else if (paramMdl.getEnq010subFolder() == Enq010Const.SUBFOLDER_REPLIED) {
                status = new int[] {Enq010Const.STATUS_ANS};
            } else {
                status = new int[]  {Enq010Const.STATUS_NOTANS,
                        Enq010Const.STATUS_ANS};
            }
        } else if (paramMdl.getEnq010folder() == Enq010Const.FOLDER_SEND) {
            if (paramMdl.getEnq010subFolder() == Enq010Const.SUBFOLDER_NOT_PUBLIC) {
                status = new int[] {Enq010Const.STATUS_NOTPUB};
            } else if (paramMdl.getEnq010subFolder() == Enq010Const.SUBFOLDER_PUBLIC) {
                status = new int[] {Enq010Const.STATUS_PUB};
            } else if (paramMdl.getEnq010subFolder() == Enq010Const.SUBFOLDER_COMP_ANS) {
                status = new int[] {Enq010Const.STATUS_ANSEXIT};
            } else if (paramMdl.getEnq010subFolder() == Enq010Const.SUBFOLDER_COMP_PUB) {
                status = new int[] {Enq010Const.STATUS_PUBEXIT};
            } else {
                status = new int[] {Enq010Const.STATUS_NOTPUB,
                        Enq010Const.STATUS_PUB,
                        Enq010Const.STATUS_ANSEXIT,
                        Enq010Const.STATUS_PUBEXIT};
            }
        }
        paramMdl.setEnq010svStatus(status);
        /** 匿名 匿名(検索条件保持) */
        paramMdl.setEnq010svAnony(0);

        /** 匿名 状態 期限切れ(検索条件保持) */
        paramMdl.setEnq010svStatusAnsOver(null);

        /** 匿名 状態 期限切れ(検索条件保持) */
        paramMdl.setEnq010svStatusAnsOverSimple(paramMdl.getEnq010statusAnsOverSimple());
    }

    /**
     * <br>[機  能] 検索条件Modelを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @return 検索条件Model
     * @throws SQLException SQL実行時例外
     */
    private Enq010SearchModel __createSearchModel(Connection con, Enq010ParamModel paramMdl,
            RequestModel reqMdl)
                    throws SQLException {
        Enq010SearchModel searchMdl = new Enq010SearchModel();
        searchMdl.setPage(paramMdl.getEnq010pageTop());

        int folder = paramMdl.getEnq010folder();
        searchMdl.setFolder(folder);
        searchMdl.setSessionUserSid(reqMdl.getSmodel().getUsrsid());

        //ソートキー
        searchMdl.setSortKey(paramMdl.getEnq010sortKey());
        if (folder == Enq010Const.FOLDER_RECEIVE
                && searchMdl.getSortKey() == Enq010Const.SORTKEY_OPEN) {
            searchMdl.setSortKey(Enq010Const.SORTKEY_ANS_OPEN);
        }

        //並び順
        searchMdl.setOrder(paramMdl.getEnq010order());

        //種類
        searchMdl.setEnqType(paramMdl.getEnq010svType());

        //キーワード 種別
        //キーワード
        String[] keywordList = null;
        if (!StringUtil.isNullZeroString(paramMdl.getEnq010svKeywordSimple())) {
            searchMdl.setKeywordType(Enq010Const.KEYWORDKBN_AND);
            keywordList = new String[] {paramMdl.getEnq010svKeywordSimple()};
        } else if (!StringUtil.isNullZeroString(paramMdl.getEnq010svKeyword())) {
            searchMdl.setKeywordType(paramMdl.getEnq010svKeywordType());
            keywordList = paramMdl.getEnq010svKeyword().split(" ");
        }
        searchMdl.setKeyword(keywordList);

        //発信者 グループ
        searchMdl.setSenderGroup(paramMdl.getEnq010svSendGroup());
        //発信者 ユーザ
        searchMdl.setSenderUser(paramMdl.getEnq010svSendUser());
        //発信者 入力
        searchMdl.setSenderInput(paramMdl.getEnq010svSendInputText());

        if (folder == Enq010Const.FOLDER_SEND
                || folder == Enq010Const.FOLDER_DRAFT) {
            //作成日 開始
            searchMdl.setMakeDateFrom(
                    __createSearchDate(paramMdl.getEnq010svMakeDateKbn(),
                            paramMdl.getEnq010svMakeDateFromYear(),
                            paramMdl.getEnq010svMakeDateFromMonth(),
                            paramMdl.getEnq010svMakeDateFromDay()));
            //作成日 終了
            searchMdl.setMakeDateTo(
                    __createSearchDate(paramMdl.getEnq010svMakeDateKbn(),
                            paramMdl.getEnq010svMakeDateToYear(),
                            paramMdl.getEnq010svMakeDateToMonth(),
                            paramMdl.getEnq010svMakeDateToDay(), 1));
        }
        //結果公開期間 開始
        searchMdl.setResPubLimitDateFrom(
                __createSearchDate(paramMdl.getEnq010svResPubDateKbn(),
                        paramMdl.getEnq010svResPubDateFromYear(),
                        paramMdl.getEnq010svResPubDateFromMonth(),
                        paramMdl.getEnq010svResPubDateFromDay()));

        //結果公開期間 終了
        searchMdl.setResPubLimitDateTo(
                __createSearchDate(paramMdl.getEnq010svResPubDateKbn(),
                        paramMdl.getEnq010svResPubDateToYear(),
                        paramMdl.getEnq010svResPubDateToMonth(),
                        paramMdl.getEnq010svResPubDateToDay()));


        if (folder == Enq010Const.FOLDER_RECEIVE
                || folder == Enq010Const.FOLDER_SEND
                || folder == Enq010Const.FOLDER_DRAFT) {
            //公開期間 開始
            searchMdl.setPubLimitDateFrom(
                    __createSearchDate(paramMdl.getEnq010svPubDateKbn(),
                            paramMdl.getEnq010svPubDateFromYear(),
                            paramMdl.getEnq010svPubDateFromMonth(),
                            paramMdl.getEnq010svPubDateFromDay()));

            //公開期間 終了
            searchMdl.setPubLimitDateTo(
                    __createSearchDate(paramMdl.getEnq010svPubDateKbn(),
                            paramMdl.getEnq010svPubDateToYear(),
                            paramMdl.getEnq010svPubDateToMonth(),
                            paramMdl.getEnq010svPubDateToDay()));

            //回答期限 開始
            searchMdl.setAnsLimitDateFrom(
                    __createSearchDate(paramMdl.getEnq010svAnsDateKbn(),
                            paramMdl.getEnq010svAnsDateFromYear(),
                            paramMdl.getEnq010svAnsDateFromMonth(),
                            paramMdl.getEnq010svAnsDateFromDay()));
            //回答期限 終了
            searchMdl.setAnsLimitDateTo(
                    __createSearchDate(paramMdl.getEnq010svAnsDateKbn(),
                            paramMdl.getEnq010svAnsDateToYear(),
                            paramMdl.getEnq010svAnsDateToMonth(),
                            paramMdl.getEnq010svAnsDateToDay(), 1));
        }
        //重要度
        searchMdl.setPriority(paramMdl.getEnq010svPriority());
        //状態
        searchMdl.setStatus(paramMdl.getEnq010svStatus());
        //匿名
        searchMdl.setAnony(paramMdl.getEnq010svAnony());

        if (folder == Enq010Const.FOLDER_RECEIVE) {
            if (!StringUtil.isNullZeroString(paramMdl.getEnq010svStatusAnsOverSimple())) {
                searchMdl.setStatusAnsOver(new int[] {
                        Enq010Const.PUBLIC_ANSFLG_OK
                });
            } else {
                searchMdl.setStatusAnsOver(paramMdl.getEnq010svStatusAnsOver());
            }
        }
        //管理者フラグ
        searchMdl.setEnqAdminFlg(false);

        return searchMdl;
    }

    /**
     * <br>[機  能] 指定したアンケートが存在するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param emnSid アンケートSID
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行時例外
     */
    public boolean existEnquete(Connection con, long emnSid) throws SQLException {
        EnqMainDao enqMainDao = new EnqMainDao(con);
        return enqMainDao.select(emnSid) != null;
    }

    /**
     * <br>[機  能] 検索条件の日付をUDateへ変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param kbn 指定なし
     * @param year 年
     * @param month 月
     * @param day 日
     * @return UDate
     */
    private UDate __createSearchDate(int kbn, int year, int month, int day) {

        return __createSearchDate(kbn, year, month, day, 0);
    }

    /**
     * <br>[機  能] 検索条件の日付をUDateへ変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param kbn 指定なし
     * @param year 年
     * @param month 月
     * @param day 日
     * @param setKbn 時分秒のセット区分
     * @return UDate
     */
    private UDate __createSearchDate(int kbn, int year, int month, int day, int setKbn) {
        if (kbn != Enq010Const.DATE_USE) {
            return null;
        }

        UDate date = new UDate();
        date.setDate(year, month, day);
        if (setKbn == 1) {
            date.setMaxHhMmSs();
        } else {
            date.setZeroHhMmSs();
        }
        return date;
    }

    /**
     * <br>[機  能] 文字列を、指定した長さでカットし、末尾に三点リーダーを付加します
     * <br>[解  説]
     * <br>[備  考]
     * @param str 文字列
     * @param range カットする長さ
     * @return カットした文字列
     */
    private String __getTrimRangeString(String str, int range) {

        String ret = StringUtil.trimRengeString(str, range);
        if (str.length() > range) {
            ret += "…";
        }
        return ret;
    }

    /**
     * <br>[機  能] 回答画面URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param sid アンケートSID
     * @return 回答画面URL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    private String __createAnswerUrl(RequestModel reqMdl, long sid)
    throws UnsupportedEncodingException {

        String answerUrl = null;
        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        //スレッドのURLを作成
        try {

            String paramUrl = "/" + urlBiz.getContextPath(reqMdl);

            paramUrl += "/enquete/enq110" + ".do";
            paramUrl += "?ansEnqSid=" + sid;

            answerUrl = urlBiz.getAccessUrl(reqMdl, paramUrl);
        } catch (URISyntaxException e) {
            return null;
        }
        return answerUrl;
    }
}
