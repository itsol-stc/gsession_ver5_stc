package jp.groupsession.v2.ip.ipk070;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.IpkBiz;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.dao.IpkAddAdmDao;
import jp.groupsession.v2.ip.dao.IpkAddDao;
import jp.groupsession.v2.ip.dao.IpkNetDao;
import jp.groupsession.v2.ip.dao.IpkSpecMstDao;
import jp.groupsession.v2.ip.ipk050.Ipk050Biz;
import jp.groupsession.v2.ip.model.IpkAddAdmModel;
import jp.groupsession.v2.ip.model.IpkAddModel;
import jp.groupsession.v2.ip.model.IpkNetModel;
import jp.groupsession.v2.ip.model.IpkSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] IP管理 詳細検索画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk070Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk070Biz.class);

    /**
     * <br>[機  能] 初期表示の設定をする。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Ipk070ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException {

        try {
            //ラベルリスト設定
            __setGroupUserCombo(paramMdl, con, reqMdl);

            GsMessage gsMsg = new GsMessage(reqMdl);
            String textNetAd = gsMsg.getMessage("ipk.2");
            String textSabnet = gsMsg.getMessage("ipk.3");
            String textIpAd = gsMsg.getMessage("ipk.6");
            String textMachine = gsMsg.getMessage("ipk.7");
            String textUsedKbn = gsMsg.getMessage("ipk.11");
            String textMsg = gsMsg.getMessage("cmn.comment");
            String textMemory = gsMsg.getMessage("cmn.memory");
            String textAllNetwork = gsMsg.getMessage("ipk.ipk070.2");

            ArrayList < LabelValueBean > sortKeyList = new  ArrayList <LabelValueBean>();
            sortKeyList.add(new LabelValueBean(textNetAd, "4"));
            sortKeyList.add(new LabelValueBean(textSabnet, "5"));
            sortKeyList.add(new LabelValueBean(textIpAd, "0"));
            sortKeyList.add(new LabelValueBean(textMachine, "1"));
            sortKeyList.add(new LabelValueBean(textUsedKbn, "2"));
            sortKeyList.add(new LabelValueBean(textMsg, "3"));
            sortKeyList.add(new LabelValueBean("CPU", "6"));
            sortKeyList.add(new LabelValueBean(textMemory, "7"));
            sortKeyList.add(new LabelValueBean("HD", "8"));
            paramMdl.setIpk070SortKeyLabelList(sortKeyList);

            //非公開ネットワークSIDリストを取得する。
            IpkBiz ipkBiz = new IpkBiz();
            ArrayList<Integer> notDspNetSidList = ipkBiz.getNotDspNetList(con, reqMdl);

            //ネットワーク名を設定する。
            IpkNetDao ipkNetDao = new IpkNetDao(con);
            List<LabelValueBean> netNameList = new  ArrayList <LabelValueBean>();
            ArrayList<IpkNetModel> netInfList = ipkNetDao.getDspNetInf(notDspNetSidList);
            netNameList.add(new LabelValueBean(textAllNetwork, "0"));
            for (IpkNetModel model : netInfList) {
                netNameList.add(new LabelValueBean(
                                model.getNetName(), String.valueOf(model.getNetSid())));
            }
            paramMdl.setIpk070NetNameLabel(netNameList);
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * <br>[機  能] 再表示の設定をする。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void setInitDataAg(Ipk070ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException {

        try {

            GsMessage gsMsg = new GsMessage(reqMdl);
            String textNetAd = gsMsg.getMessage("ipk.2");
            String textSabnet = gsMsg.getMessage("ipk.3");
            String textIpAd = gsMsg.getMessage("ipk.6");
            String textMachine = gsMsg.getMessage("ipk.7");
            String textUsedKbn = gsMsg.getMessage("ipk.11");
            String textMsg = gsMsg.getMessage("cmn.comment");
            String textMemory = gsMsg.getMessage("cmn.memory");
            String textAllNetwork = gsMsg.getMessage("ipk.ipk070.2");

            //ラベルリスト設定
            __setGroupUserCombo(paramMdl, con, reqMdl);
            ArrayList < LabelValueBean > sortKeyList = new  ArrayList <LabelValueBean>();
            sortKeyList.add(new LabelValueBean(textNetAd, "4"));
            sortKeyList.add(new LabelValueBean(textSabnet, "5"));
            sortKeyList.add(new LabelValueBean(textIpAd, "0"));
            sortKeyList.add(new LabelValueBean(textMachine, "1"));
            sortKeyList.add(new LabelValueBean(textUsedKbn, "2"));
            sortKeyList.add(new LabelValueBean(textMsg, "3"));
            sortKeyList.add(new LabelValueBean("CPU", "6"));
            sortKeyList.add(new LabelValueBean(textMemory, "7"));
            sortKeyList.add(new LabelValueBean("HD", "8"));
            paramMdl.setIpk070SortKeyLabelList(sortKeyList);

            //非公開ネットワークSIDリストを取得する。
            IpkBiz ipkBiz = new IpkBiz();
            ArrayList<Integer> notDspNetSidList = ipkBiz.getNotDspNetList(con, reqMdl);

            //ネットワーク名を設定する。
            IpkNetDao ipkNetDao = new IpkNetDao(con);
            List<LabelValueBean> netNameList = new ArrayList< LabelValueBean >();
            ArrayList<IpkNetModel> netInfList = ipkNetDao.getDspNetInf(notDspNetSidList);
            netNameList.add(new LabelValueBean(textAllNetwork, "0"));
            for (IpkNetModel model : netInfList) {
                netNameList.add(new LabelValueBean(
                                model.getNetName(), String.valueOf(model.getNetSid())));
            }
            paramMdl.setIpk070NetNameLabel(netNameList);
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * 検索条件部分のグループ、ユーザコンボを生成する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    private void __setGroupUserCombo(
            Ipk070ParamModel paramMdl,
            Connection con,
            RequestModel reqMdl)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        GroupBiz gpBiz = new GroupBiz();
        List<LabelValueBean> groupLabelList = gpBiz.getGroupCombLabelList(con, true, gsMsg);

        //表示グループ・ユーザ
        int dspGpSid = NullDefault.getInt(paramMdl.getIpk070SltGroup(), -1);

        //グループコンボのラベルを取得する。
        paramMdl.setIpk070GroupLabel(groupLabelList);
        //ユーザコンボ
        UserBiz uBiz = new UserBiz();
        List<UsrLabelValueBean> userLabel = uBiz.getUserLabelListNoSysUser(con, gsMsg, dspGpSid);
        paramMdl.setIpk070UserLabel(userLabel);
    }

    /**
     * <br>[機  能] 検索を実行する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void setSearchData(Ipk070ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException {

        try {


            IpkAddDao ipkAddDao = new IpkAddDao(con);
            IpkSearchModel model = __setSearchModel(paramMdl, con, reqMdl);
            IpkBiz ipkBiz = new IpkBiz();

            //検索結果を取得する。
            ArrayList<IpkAddModel> ipkAddModelList = new ArrayList<IpkAddModel>();
            ipkAddModelList = ipkAddDao.selectSearchResult(model, true);
            IpkAddAdmDao ipkAddAdmDao = new IpkAddAdmDao(con);
            ArrayList<IpkSearchModel> dspList = new ArrayList<IpkSearchModel>();
            for (IpkAddModel mdl : ipkAddModelList) {
                model = new IpkSearchModel();
                //IPアドレスを"."で区切る。
                String ipad = Ipk050Biz.getDecFormat(mdl.getIadIpad(), IpkConst.IPAD_FORMAT);
                int a = Integer.parseInt(ipad.substring(0, 3));
                int b = Integer.parseInt(ipad.substring(3, 6));
                int c = Integer.parseInt(ipad.substring(6, 9));
                int d = Integer.parseInt(ipad.substring(9, 12));
                model.setIpad(a + "." + b + "." + c + "." + d);
                model.setIadName(mdl.getIadName());
                model.setUseKbn(mdl.getIadUseKbn());

                //コメントの表示文字数を制限する。
                model.setIadMsg(ipkBiz.getShortMsg(mdl.getIadMsg(), IpkConst.IPK_MSG_NUM_SEARCH));
                model.setNetad(mdl.getNetNetad());
                model.setSubnet(mdl.getNetSabnet());
                model.setNetSid(mdl.getNetSid());
                model.setIadSid(mdl.getIadSid());
                __setSpecName(con, mdl, model);

                //ネットワーク管理者情報をセットする。
                ArrayList<Integer> iadSidList = new ArrayList<Integer>();
                iadSidList.add(mdl.getIadSid());

                ArrayList <IpkAddAdmModel> adminUserList
                    = ipkAddAdmDao.selectIadAdminUsr(iadSidList);

                model.setIadAdmList(adminUserList);
                dspList.add(model);
            }
            paramMdl.setSearchModelList(dspList);
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * <br>[機  能] 検索モデルを設定する(検索ボタンクリック時)
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return model IpkSearchModel
     * @throws SQLException SQL実行時例外
     */
    public IpkSearchModel __setSearchModel(
            Ipk070ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException {

        IpkAddDao ipkAddDao = new IpkAddDao(con);
        //検索モデルを設定する。
        IpkSearchModel model = new IpkSearchModel();
        model.setNetSid(NullDefault.getInt(paramMdl.getIpk070SvSltNet(), 0));
        model.setAdmGrpSid((NullDefault.getInt(paramMdl.getIpk070SvGrpSid(), -1)));
        model.setAdmUsrSid((NullDefault.getInt(paramMdl.getIpk070SvUsrSid(), -1)));

        //キーワードを配列にセットする。
        String[] keywordList = NullDefault.getString(
                paramMdl.getIpk070SvKeyWord(), "").replaceAll("　", " ").split(" ");
        model.setKeywordList(keywordList);

        model.setKeywordKbn(NullDefault.getString(paramMdl.getIpk070SvKeyWordkbn(), ""));

        model.setSearchTarget(paramMdl.getIpk070SvSearchTarget());

        model.setSortKey1(NullDefault.getInt(
                paramMdl.getIpk070SvSearchSortKey1(), IpkConst.IPK_SORT_IPAD));
        model.setSortKey2(NullDefault.getInt(
                paramMdl.getIpk070SvSearchSortKey2(), IpkConst.IPK_SORT_USEDKBN));
        model.setOrderKey1(NullDefault.getInt(
                paramMdl.getIpk070SvSearchOrderKey1(), IpkConst.IPK_ORDER_ASC));
        model.setOrderKey2(NullDefault.getInt(
                paramMdl.getIpk070SvSearchOrderKey2(), IpkConst.IPK_ORDER_ASC));

        //公開するネットワークを設定する。
        IpkBiz ipkBiz = new IpkBiz();
        model.setNotDspNetSidList(ipkBiz.getNotDspNetList(con, reqMdl));

        //検索結果一覧の件数を取得する。
        int maxCount = ipkAddDao.countSearchResult(model);
        //１ページ表示件数
        int limit = IpkConst.SEARCH_LIMIT;

        //現在ページ、スタート行
        int nowPage = NullDefault.getInt(paramMdl.getIpk070PageNow(), 1);
        log__.debug("現在ページ数 = " + nowPage);
        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        paramMdl.setIpk070MaxPageNum(String.valueOf(maxPageNum));
        //検索ボタンクリック時は1ページ目を表示する。
        if (NullDefault.getString(paramMdl.getCMD(), "").equals("doSearch")) {
            nowPage = 1;
            paramMdl.setIpk070PageNow("1");
        }
        //現在ページより、全ページ数が小さい場合
        if (maxPageNum < nowPage) {
            nowPage = maxPageNum;
        }
        //結果セット取得開始行設定
        int offset = PageUtil.getRowNumber(nowPage, limit);

        //ページ開始行数
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);

        if (maxPageStartRow < offset) {
            nowPage = maxPageNum;
            offset = maxPageStartRow;
        }
        model.setOffset(offset);
        model.setLimit(limit);

        //ページング
        paramMdl.setIpk070Page1(String.valueOf(nowPage));
        paramMdl.setIpk070Page2(String.valueOf(nowPage));
        paramMdl.setIpk070MaxPageNum(String.valueOf(maxPageNum));
        paramMdl.setIpk070PageLabel(
            PageUtil.createPageOptions(maxCount, limit));

        return model;
    }

    /**
     * <br>[機  能] 次ページ表示する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void setNextPage(Ipk070ParamModel paramMdl) {

        int pageNum = NullDefault.getInt(paramMdl.getIpk070Page1(), -2);
        int maxPageNum = NullDefault.getInt(paramMdl.getIpk070MaxPageNum(), -2);
        if (maxPageNum > pageNum) {
            //現在ページ数に1を加える。
            paramMdl.setIpk070PageNow(String.valueOf(pageNum + 1));
        }
    }

    /**
     * <br>[機  能] 前のページ表示する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void setBeforePage(Ipk070ParamModel paramMdl) {

        int pageNum = NullDefault.getInt(paramMdl.getIpk070Page1(), -2);
        if (pageNum > 1) {
            //現在ページ数から1を引く
            paramMdl.setIpk070PageNow(String.valueOf(pageNum - 1));
        }
    }

    /**
     * <br>[機  能] IPアドレス一覧から遷移時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void setIpk040Search(Ipk070ParamModel paramMdl) {

        paramMdl.setIpk070SltNet(paramMdl.getNetSid());
    }

    /**
     * <br>[機  能] ページコンボ変更時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void setPageCmb(Ipk070ParamModel paramMdl) {

        String pageNum = NullDefault.getString(paramMdl.getIpk070Page1(), IpkConst.IPAD_PAGE_NUM);
        paramMdl.setIpk070PageNow(pageNum);
    }

    /**
     * <br>[機  能] 検索結果件数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return int 検索結果件数
     * @throws SQLException SQL実行例外
     */
    public int getSearchResult(
            Ipk070ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException {

        int count = 0;
        try {
            IpkAddDao ipkAddDao = new IpkAddDao(con);
            IpkSearchModel model = __setSearchModel(paramMdl, con, reqMdl);

            //公開するネットワークを設定する。
            IpkBiz ipkBiz = new IpkBiz();
            model.setNotDspNetSidList(ipkBiz.getNotDspNetList(con, reqMdl));

            //検索結果を取得する。
            count = ipkAddDao.countSearchResult(model);

        } catch (SQLException e) {
            throw e;
        }
        return count;
    }

    /**
     * <br>[機  能] CPU,メモリ,HDの名前を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mdl IpkAddModel
     * @param model IpkSearchModel
     * @throws SQLException SQL実行例外
     */
    public void __setSpecName(Connection con, IpkAddModel mdl, IpkSearchModel model)
    throws SQLException {

        try {
            IpkSpecMstDao dao = new IpkSpecMstDao(con);
            //CPU名を設定する。
            if (mdl.getIadCpu() == IpkConst.SPEC_MITOUROKU) {
                model.setCpuName("---");
            } else {
                model.setCpuName(dao.selectName(mdl.getIadCpu()));
            }

            //メモリ名を設定する。
            if (mdl.getIadMemory() == IpkConst.SPEC_MITOUROKU) {
                model.setMemoryName("---");
            } else {
                model.setMemoryName(dao.selectName(mdl.getIadMemory()));
            }

            //HD名を設定する。
            if (mdl.getIadHd() == IpkConst.SPEC_MITOUROKU) {
                model.setHdName("---");
            } else {
                model.setHdName(dao.selectName(mdl.getIadHd()));
            }
        } catch (SQLException e) {
            throw e;
        }
    }
}