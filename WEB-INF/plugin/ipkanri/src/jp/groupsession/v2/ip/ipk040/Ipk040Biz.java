package jp.groupsession.v2.ip.ipk040;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.IpkAdminInfo;
import jp.groupsession.v2.ip.IpkBiz;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.IpkUsedDataBiz;
import jp.groupsession.v2.ip.dao.IpkAddAdmDao;
import jp.groupsession.v2.ip.dao.IpkAddDao;
import jp.groupsession.v2.ip.dao.IpkBinDao;
import jp.groupsession.v2.ip.dao.IpkNetAdmDao;
import jp.groupsession.v2.ip.dao.IpkNetDao;
import jp.groupsession.v2.ip.dao.IpkSpecMstDao;
import jp.groupsession.v2.ip.ipk050.Ipk050Biz;
import jp.groupsession.v2.ip.model.IpkAddAdmModel;
import jp.groupsession.v2.ip.model.IpkAddModel;
import jp.groupsession.v2.ip.model.IpkBinModel;
import jp.groupsession.v2.ip.model.IpkNetModel;

/**
 * <br>[機  能] IP管理 IPアドレス一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk040Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk040Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException 実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setInitData(Ipk040ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException, TempFileException {

        try {
            //ネットワーク情報を取得する
            IpkNetDao ipkNetDao = new IpkNetDao(con);
            IpkBiz ipkBiz = new IpkBiz();
            int netSid = NullDefault.getInt(paramMdl.getNetSid(), -2);
            ArrayList<IpkNetModel> ret = ipkNetDao.selectNetwork(netSid);
            //フォームにセットする。
            for (IpkNetModel ipkModel : ret) {
                paramMdl.setNetName(String.valueOf(ipkModel.getNetName()));
                paramMdl.setNetNetad(ipkModel.getNetNetad());
                paramMdl.setNetSabnet(ipkModel.getNetSabnet());
                //コメントをhtml表示用に変換
                paramMdl.setNetMsg(NullDefault.getString(
                StringUtilHtml.transToHTmlPlusAmparsant(
                                     ipkModel.getNetMsg()), ""));
            }

            //ネットワーク添付ファイル一覧を設定(公開)
            ArrayList <IpkBinModel> koukaiBinList = ipkBiz.getTempDataList(
                    con, netSid, IpkConst.NETWORK_TOUROKU, IpkConst.IPK_TEMP_DSP_KOUKAI, reqMdl);
            paramMdl.setKoukaiBinFileInfList(koukaiBinList);

            //ネットワーク添付ファイル一覧を設定(非公開)
            ArrayList <IpkBinModel> hikoukaiBinList = ipkBiz.getTempDataList(
                    con, netSid, IpkConst.NETWORK_TOUROKU, IpkConst.IPK_TEMP_DSP_HIKOUKAI, reqMdl);
            paramMdl.setHikoukaiBinFileInfList(hikoukaiBinList);

            //添付ファイル存在フラグを設定する。
            if (!koukaiBinList.isEmpty() || !hikoukaiBinList.isEmpty()) {
                paramMdl.setTempExist(IpkConst.IPK_TEMP_EXIST);
            }

            //セッションユーザSIDを取得する。
            BaseUserModel usModel = reqMdl.getSmodel();
            int sessionUsrSid = usModel.getUsrsid();

            //セッションユーザがGS管理者か、全ネットワーク管理者であるかを判定
            IpkAdminInfo ipkKanrisyaKubun = new IpkAdminInfo();
            boolean allAdmin = ipkKanrisyaKubun.isGsIpAdm(reqMdl, con);

            //ネットワーク管理者であるか判定
            IpkNetAdmDao ipkNetAdmDao = new IpkNetAdmDao(con);
            int netAdmCount = ipkNetAdmDao.countUsrAdmNet(
                    NullDefault.getInt(paramMdl.getNetSid(), 0), sessionUsrSid);
            boolean netAdmFlg = IpkConst.KANRI_KENGEN_NASI;
            if (netAdmCount > 0) {
                netAdmFlg = IpkConst.KANRI_KENGEN_ARI;
            }

            if (allAdmin || netAdmFlg) {
                paramMdl.setIadNetAdmFlg(IpkConst.KANRI_KENGEN_ARI);
            }

            //IPアドレス一覧データ取得
            //IPアドレス使用中の登録台数を取得する。
            IpkAddDao ipkAddDao = new IpkAddDao(con);
            String iadCountUse = ipkAddDao.countIadUse(
                    NullDefault.getInt(paramMdl.getNetSid(), -2));

            //IPアドレス未使用の登録台数取得
            String iadCountNotUse = ipkAddDao.countIadNotUse(
                    NullDefault.getInt(paramMdl.getNetSid(), -2));

            //１ページ表示件数
            int limit = NullDefault.getInt(paramMdl.getIadLimit(), -2);

            //全データ件数
            int maxCount = 0;
            if (paramMdl.getUsedKbn().equals(IpkConst.USEDKBN_SIYOU)) {
                maxCount = NullDefault.getInt(iadCountUse, -2);

            } else if (paramMdl.getUsedKbn().equals(IpkConst.USEDKBN_MISIYOU)) {
                maxCount = NullDefault.getInt(iadCountNotUse, -2);

            } else {
                maxCount = NullDefault.getInt(iadCountUse, -2)
                + NullDefault.getInt(iadCountNotUse, -2);
            }

            //現在ページ、スタート行
            int nowPage = NullDefault.getInt(paramMdl.getIadPageNum(), -2);

            log__.debug("現在ページ = " + nowPage);

            //表示件数が全表示の場合
            if (String.valueOf(limit).equals(IpkConst.IPAD_LIMIT_ALL)) {
                limit = maxCount;
            }
            //ページあふれ制御
            int maxPageNum = PageUtil.getPageCount(maxCount, limit);
            paramMdl.setMaxPageNum(String.valueOf(maxPageNum));
            //現在ページより、全ページ数が大きい場合
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

            //一覧情報をセットする。
            IpkAddModel ipkAddModel = new IpkAddModel();
            ipkAddModel.setNetSid(NullDefault.getInt(paramMdl.getNetSid(), -2));
            ipkAddModel.setIadUseKbn(
                    NullDefault.getString(paramMdl.getUsedKbn(), IpkConst.USEDKBN_SIYOU));
            ipkAddModel.setIadOffset(offset);
            ipkAddModel.setIadLimit(limit);
            ipkAddModel.setSortKey(NullDefault.getInt(paramMdl.getSortKey(), -2));
            ipkAddModel.setOrderKey(NullDefault.getInt(paramMdl.getOrderKey(), -2));

            //ページング
            paramMdl.setIadPageNum(String.valueOf(nowPage));
            paramMdl.setIadPage1(String.valueOf(nowPage));
            paramMdl.setIadPage2(String.valueOf(nowPage));
            paramMdl.setIadPageLabel(
                PageUtil.createPageOptions(maxCount, limit));

            //IPアドレス一覧情報を取得する
            ArrayList<IpkAddModel> resList = ipkAddDao.select(ipkAddModel, true);
            ArrayList<IpkAddModel> newList = new ArrayList<IpkAddModel>();
            IpkAddAdmDao ipkAddAdmDao = new IpkAddAdmDao(con);
            IpkSpecMstDao ipkSpecMstDao = new IpkSpecMstDao(con);
            String cpuName = null;
            String memoryName = null;
            String hdName = null;
            for (IpkAddModel mdl : resList) {
                ipkAddModel = new IpkAddModel();
                ipkAddModel.setIadSid(mdl.getIadSid());
                ipkAddModel.setIadName(mdl.getIadName());
                //CPUを設定する。
                if (mdl.getIadCpu() == 0) {
                    cpuName = "---";
                } else {
                    cpuName = ipkSpecMstDao.selectName(mdl.getIadCpu());
                }
                //メモリを設定する。
                if (mdl.getIadMemory() == 0) {
                    memoryName = "---";
                } else {
                    memoryName = ipkSpecMstDao.selectName(mdl.getIadMemory());
                }
                //HDを設定する。
                if (mdl.getIadHd() == 0) {
                    hdName = "---";
                } else {
                    hdName = ipkSpecMstDao.selectName(mdl.getIadHd());
                }
                ipkAddModel.setIadCpuName(StringUtilHtml.transToHTmlPlusAmparsant(cpuName));
                ipkAddModel.setIadMemoryName(StringUtilHtml.transToHTmlPlusAmparsant(memoryName));
                ipkAddModel.setIadHdName(StringUtilHtml.transToHTmlPlusAmparsant(hdName));

                //IPアドレスを"."で区切る。
                String ipad = Ipk050Biz.getDecFormat(mdl.getIadIpad(), IpkConst.IPAD_FORMAT);
                int a = Integer.parseInt(ipad.substring(0, 3));
                int b = Integer.parseInt(ipad.substring(3, 6));
                int c = Integer.parseInt(ipad.substring(6, 9));
                int d = Integer.parseInt(ipad.substring(9, 12));
                ipkAddModel.setIadIpadDsp(a + "." + b + "." + c + "." + d);
                ipkAddModel.setIadUseKbn(mdl.getIadUseKbn());
                //コメントを表示文字数を制限する。
                ipkAddModel.setIadMsg(NullDefault.getString(
                        ipkBiz.getShortMsg(mdl.getIadMsg(), IpkConst.IPK_MSG_NUM_IPAD), ""));

                //セッションユーザの使用者になっているIPアドレスSIDリストを取得する。
                ArrayList<String> iadSessionUserAdmList =
                    ipkKanrisyaKubun.getUsrAdminIpadSid(reqMdl, con);

                //各IPアドレスの使用者の名前をセットする。
                //IPアドレス使用者のユーザ姓名と対応するIPアドレスSIDを取得する。
                ArrayList<Integer> iadSidList = new ArrayList<Integer>();
                for (int i = 0; i < resList.size(); i++) {
                    iadSidList.add(resList.get(i).getIadSid());
                }
                //IPアドレス使用者のユーザ情報のリストを取得する。
                ArrayList <IpkAddAdmModel> adminUserList
                = ipkAddAdmDao.selectIadAdminUsr(iadSidList);

                ArrayList<IpkAddAdmModel> convUsrList =
                    new ArrayList<IpkAddAdmModel>();

                for (IpkAddAdmModel admModel : adminUserList) {
                    if (admModel.getIadSid() == mdl.getIadSid()) {
                        convUsrList.add(admModel);
                    }
                }
                ipkAddModel.setUserSeiMei(convUsrList);

                //IPアドレスの使用権限があるか判定する。
                if (allAdmin || netAdmFlg) {
                    ipkAddModel.setIadAdmFlg(IpkConst.KANRI_KENGEN_ARI);
                } else {
                    for (String usrAdmIadSid : iadSessionUserAdmList) {
                        if (ipkAddModel.getIadSid() == NullDefault.getInt(usrAdmIadSid, 0)) {
                            ipkAddModel.setIadAdmFlg(IpkConst.KANRI_KENGEN_ARI);
                            break;
                        }
                        ipkAddModel.setIadAdmFlg(IpkConst.KANRI_KENGEN_NASI);
                    }
                }
                newList.add(ipkAddModel);
            }
            paramMdl.setIpkAddList(newList);
            paramMdl.setIadCountUse(iadCountUse);
            paramMdl.setIadCountNotUse(iadCountNotUse);
            paramMdl.setMaxCount(String.valueOf(maxCount));
            paramMdl.setIadCount(String.valueOf(
                    NullDefault.getInt(iadCountUse, -2)
                    + NullDefault.getInt(iadCountNotUse, -2)));

            String dspFlg = ipkAddAdmDao.countUsrAdmNet(
                    NullDefault.getInt(paramMdl.getNetSid(), 0), sessionUsrSid);
            if (allAdmin) {
                paramMdl.setDspFlg(IpkConst.BUTTON_DSP);
            } else {
                paramMdl.setDspFlg(dspFlg);
            }
            if (NullDefault.getInt(dspFlg, 0) > 0 || netAdmFlg || allAdmin) {
                paramMdl.setCheckBoxDspFlg(IpkConst.CHECKBOX_DSP);
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * <br>[機  能] IPアドレス情報を一括削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException 実行例外
     */
    public void deleteIpad(Ipk040ParamModel paramMdl, Connection con, int sessionUsrSid)
    throws SQLException {

        //コミットフラグ
        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {
            IpkAddDao ipkAddDao = new IpkAddDao(con);
            IpkAddAdmDao ipkAddAdmDao = new IpkAddAdmDao(con);
            IpkBinDao ipkBinDao = new IpkBinDao(con);
            UDate now = new UDate();
            int netSid = NullDefault.getInt(paramMdl.getNetSid(), 0);

            //ネットワーク情報のデータ使用量を登録(削除前のデータ使用量を減算)
            IpkUsedDataBiz usedDataBiz = new IpkUsedDataBiz(con);
            usedDataBiz.insertNetworkSize(netSid, false);

            //一括削除チェックボックスのチェックされている項目を取得する。
            String[] deleteCheck = paramMdl.getDeleteCheck();
            if (deleteCheck != null) {
                //IPアドレスSIDをintに変換
                ArrayList<Integer> delIadSids = new ArrayList<Integer>();
                for (int i = 0; i < deleteCheck.length; i++) {
                    delIadSids.add(Integer.parseInt(deleteCheck[i]));
                }
                //IPアドレス情報の削除
                ipkAddDao.deleteArrayIpad(delIadSids);
                //IPアドレス使用者情報の削除
                ipkAddAdmDao.deleteHukusuu(delIadSids);
                //バイナリー情報の論理削除、添付情報の削除を行う
                ipkBinDao.removeIadBinDataList(delIadSids, sessionUsrSid, now);
                ipkBinDao.deleteIadTempList(netSid, delIadSids);
            }

            //ネットワーク情報のデータ使用量を登録(削除後のデータ使用量を登録)
            usedDataBiz.insertNetworkSize(netSid, false);

            commitFlg = true;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
                log__.debug("コミット完了");
            } else {
                con.rollback();
            }
        }
    }

    /**
     * <br>[機  能] 次ページ表示する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void setNextPage(Ipk040ParamModel paramMdl) {

        int pageNum = NullDefault.getInt(paramMdl.getIadPageNum(), -2);
        int maxPageNum = NullDefault.getInt(paramMdl.getMaxPageNum(), -2);
        if (maxPageNum > pageNum) {
            //現在ページ数に1を加える。
            paramMdl.setIadPageNum(String.valueOf(pageNum + 1));
            paramMdl.setDeleteAllCheck(null);
            paramMdl.setDeleteCheck(null);
        }
    }

    /**
     * <br>[機  能] 前のページ表示する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void setBeforePage(Ipk040ParamModel paramMdl) {

        int pageNum = NullDefault.getInt(paramMdl.getIadPageNum(), -2);
        if (pageNum > 1) {
            //現在ページ数から1を引く
            paramMdl.setIadPageNum(String.valueOf(pageNum - 1));
            paramMdl.setDeleteAllCheck(null);
            paramMdl.setDeleteCheck(null);
        }
    }

    /**
     * <br>[機  能] フォーム情報から検索モデルにセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return ScheduleListSearchModel 検索条件モデル
     * @throws SQLException 実行例外
     */
    public IpkAddModel setIpkSearchModel(Ipk040ParamModel paramMdl, Connection con)
        throws SQLException {

        IpkAddModel searchMdl = new IpkAddModel();
        try {
            searchMdl.setSortKey(NullDefault.getInt(paramMdl.getSortKey(), 0));
            searchMdl.setOrderKey(NullDefault.getInt(paramMdl.getOrderKey(), 0));
            searchMdl.setNetSid(NullDefault.getInt(paramMdl.getNetSid(), -2));
            searchMdl.setIadUseKbn(
                    NullDefault.getString(paramMdl.getUsedKbn(), IpkConst.USEDKBN_ALL));
            IpkNetDao ipkNetDao = new IpkNetDao(con);
            ArrayList<IpkNetModel> ipkNetList
            = ipkNetDao.selectNetwork(NullDefault.getInt(paramMdl.getNetSid(), -2));
            for (IpkNetModel model : ipkNetList) {
                searchMdl.setNetNetad(model.getNetNetad());
                searchMdl.setNetSabnet(model.getNetSabnet());
                searchMdl.setNetName(model.getNetName());
            }
        } catch (SQLException e) {
            throw e;
        }
        return searchMdl;
    }

    /**
     * <br>[機  能] ページコンボ変更時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void setPageCmb(Ipk040ParamModel paramMdl) {

        String pageNum = NullDefault.getString(paramMdl.getIadPage1(), IpkConst.IPAD_PAGE_NUM);
        paramMdl.setIadPageNum(pageNum);
        paramMdl.setDeleteCheck(null);
        paramMdl.setDeleteAllCheck(null);
    }

    /**
     * <br>[機  能] 一括削除のテキストボックス値にnullをセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void setDeleteCheckNull(Ipk040ParamModel paramMdl) {

        paramMdl.setDeleteCheck(null);
        paramMdl.setDeleteAllCheck(null);
    }
}