package jp.groupsession.v2.api.ntp.nippou.timeline;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.ntp.nippou.biz.ApiNippouSearchBiz;
import jp.groupsession.v2.api.ntp.nippou.model.ApiNippouDataModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NippouDao;
import jp.groupsession.v2.ntp.dao.NippouSearchDao;
import jp.groupsession.v2.ntp.model.AnkenPermitCheckModel;
import jp.groupsession.v2.ntp.model.NippouListSearchModel;
import jp.groupsession.v2.ntp.model.NippouSearchModel;
import jp.groupsession.v2.ntp.model.NtpDataModel;
import jp.groupsession.v2.ntp.model.NtpPriConfModel;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040CommentModel;
/**
 * <br>[機  能] WEBAPI 日報タイムラインアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouTimeLineAction extends AbstractApiAction {
    /** ロガークラス */
    private static Log log__ = LogFactory.getLog(ApiNippouTimeLineAction.class);
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        //日報プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_NIPPOU, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_NIPPOU));
            return null;
        }

        int sessionUsrSid = umodel.getUsrsid(); //セッションユーザSID

        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        ApiNippouTimeLineForm thisForm = (ApiNippouTimeLineForm) form;
        ActionErrors err = thisForm.validateCheck(con, req, sessionUsrSid);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        NtpCommonBiz biz = new NtpCommonBiz(con, getRequestModel(req));
        NtpPriConfModel pconf = biz.getNtpPriConfModel(con, sessionUsrSid);
        //１ページ表示件数
        int limit = pconf.getNprDspList();

        limit = NullDefault.getInt(thisForm.getMaxCnt(), limit);

        //日報検索情報を取得
        NippouListSearchModel searchMdl = new NippouListSearchModel();
        UDate date1 = new UDate();
        date1.setDate(
                1970,
                1,
                1);
        UDate date2 = new UDate();
        date2.setDate(
                9999,
                12,
                31);
        searchMdl.setNtpDateFrom(date1);
        searchMdl.setNtpDateTo(date2);



        //データが存在しない場合、グループが削除されていた場合はデフォルト所属グループを返す
        NtpCommonBiz scBiz = new NtpCommonBiz(con, getRequestModel(req));
        //デフォルト表示グループ
        String dfGpSidStr = scBiz.getDispDefaultGroupSidStr(con, sessionUsrSid);
        boolean myGrpFlg = false;


        //表示グループ
        String dspGpSidStr = NullDefault.getString(thisForm.getGrpSid(), dfGpSidStr);

        if (NtpCommonBiz.isMyGroupSid(dspGpSidStr)) {
            //マイグループSIDをセット
            myGrpFlg = true;
        }
        searchMdl.setNtpSltGroupSid(String.valueOf(NtpCommonBiz.getDspGroupSid(dspGpSidStr)));

        searchMdl.setMyGrpFlg(myGrpFlg);

        //検索モデルへ設定
        searchMdl.setUsrSid(NullDefault.getInt(thisForm.getUsrSid(), -1));

        //特例アクセス閲覧不可ユーザの取得
        NippouDao ntpDao = new NippouDao(con);
        List<Integer> notAccessUserList = null;
        int selectGrp = NullDefault.getInt(searchMdl.getNtpSltGroupSid(), -1);
        if (!searchMdl.isMyGrpFlg()) {
            notAccessUserList = ntpDao.getSpAccessUserList(selectGrp, sessionUsrSid, false, -1);
        } else {
            notAccessUserList = ntpDao.getSpAccessUserList(-1, sessionUsrSid, false, -1);
        }

        //全データ件数
        int maxCount = getNippouCnt(
                searchMdl,
                sessionUsrSid,
                con,
                notAccessUserList);

        log__.debug("getNtpeduleCn==>" + maxCount);
        //現在ページ、スタート行
        int nowPage = NullDefault.getInt(thisForm.getPage(), 1);
        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        if (nowPage > maxPageNum) {
            nowPage = maxPageNum;
        }
        searchMdl.setNtpOffset(nowPage);
        searchMdl.setNtpLimit(limit);
        int sort = Integer.parseInt(thisForm.getSort());

        List<ApiNippouDataModel> dataList =
                __getNtpList(searchMdl, sessionUsrSid, sort, true, req, con, notAccessUserList);

        resultSet.setAttribute("Count", Integer.toString(dataList.size()));
        resultSet.setAttribute("TotalCount", Integer.toString(maxCount));
        resultSet.setAttribute("Page", Integer.toString(nowPage));
        resultSet.setAttribute("MaxPage", Integer.toString(maxPageNum));
        for (ApiNippouDataModel dataModel__ : dataList) {
            Element result = new Element("Result");
            resultSet.addContent(result);
            result.addContent(_createElement("NipSid", dataModel__.getNipSid())); //日報SID
            CmnUsrmInfModel usrMdl = dataModel__.getUsrMdl();

            result.addContent(_createElement("UsrSid", dataModel__.getUsrSid())); //ユーザSID
            result.addContent(_createElement("UsrName", usrMdl.getUsiSei()
                    + " " + usrMdl.getUsiMei())); //ユーザ名
            result.addContent(_createElement("UsrUkoFlg", usrMdl.getUsrUkoFlg())); // ユーザ無効フラグ
            if (usrMdl.getBinSid() == 0) {
                //写真なし
                result.addContent(_createElement("UsrImgPath", ""));
            } else {
                if (usrMdl.getUsiPictKf() == 0) {
                    //写真あり 公開
                    result.addContent(_createElement("UsrImgPath",
                            "./common/cmn100.do?CMD=getImageFile&cmn100binSid="
                                    + usrMdl.getBinSid()));

                } else {
                    //写真あり 非公開
                    result.addContent(_createElement("UsrImgPath", "secret"));
                }
            }

            result.addContent(_createElement("AddUsrSid", dataModel__.getNipEuid())); //登録ユーザSID
            result.addContent(_createElement("AddUsrName", dataModel__.getAddUsrName())); //登録ユーザ名
            result.addContent(_createElement("AddUsrUkoFlg",
                                                 dataModel__.getAddUsrUkoFlg())); //登録ユーザ無効フラグ
            result.addContent(_createElement("NipDate",
                    UDateUtil.getSlashYYMD(dataModel__.getNipDate()))); //登録日
            result.addContent(_createElement("NipFrTime",
                    (StringUtil.toDecFormat(dataModel__.getNipFrTime().getIntHour(), "00")
                            + ":"
                            + StringUtil.toDecFormat(dataModel__.getNipFrTime().getIntMinute(),
                                    "00")))); //開始時間
            result.addContent(_createElement("NipToTime",
                    (StringUtil.toDecFormat(dataModel__.getNipToTime().getIntHour(), "00")
                            + ":"
                            + StringUtil.toDecFormat(dataModel__.getNipToTime().getIntMinute(),
                                    "00")))); //終了時間

            result.addContent(_createElement("NipTitle", dataModel__.getNipTitle())); //日報タイトル
            result.addContent(_createElement("NipTitleClo",
                    dataModel__.getNipTitleClo())); //タイトルカラー
            result.addContent(_createElement("NipMikomi", dataModel__.getNipMikomi())); //見込み度
            result.addContent(_createElement("NipSyokan", dataModel__.getNipDetail())); //所感
            result.addContent(_createElement("NanSid", dataModel__.getNanSid())); //案件SID
            result.addContent(_createElement("NanName", dataModel__.getNanName())); //案件名
            result.addContent(_createElement("AcoSid", dataModel__.getAcoSid())); //会社SID
            result.addContent(_createElement("AcoName", dataModel__.getAcoName())); //会社名
            result.addContent(_createElement("AbaSid", dataModel__.getAbaSid())); //拠点SID
            result.addContent(_createElement("AbaName", dataModel__.getAbaName())); //拠点名
            result.addContent(_createElement("MkbSid", dataModel__.getMkbSid())); //活動区分SID
            result.addContent(_createElement("MkbName", dataModel__.getKtBunrui())); //活動区分名
            result.addContent(_createElement("MkhSid", dataModel__.getMkhSid())); //活動方法SID
            result.addContent(_createElement("MkhName", dataModel__.getKtHouhou())); //活動方法名
            result.addContent(_createElement("IineCount", dataModel__.getIineCount())); //いいね回数
            result.addContent(_createElement("IineFlg", dataModel__.getIineFlg())); //いいねフラグ
            if (dataModel__.isAnkenViewable()) {
                result.addContent(_createElement("AnkenViewable", 1)); //案件公開
            } else {
                result.addContent(_createElement("AnkenViewable", 0)); //案件非公開
            }

            Element tempFileSet = new Element("TempFileSet");
            result.addContent(tempFileSet);
            tempFileSet.setAttribute("TotalCount", Integer.toString(dataModel__.getClips().size()));
            List<CmnBinfModel> binList = dataModel__.getClips();
            for (CmnBinfModel binModel : binList) {
                Element binFile = new Element("File");
                tempFileSet.addContent(binFile);
                binFile.addContent(_createElement("BinSid", binModel.getBinSid())); //バイナリーSID
                binFile.addContent(_createElement("BinFileName",
                        binModel.getBinFileName())); //ファイル名
            }



            Element commentSet = new Element("CommentSet");
            result.addContent(commentSet);
            commentSet.setAttribute("TotalCount",
                    Integer.toString(dataModel__.getComments().size()));

            for (Ntp040CommentModel commentModel: dataModel__.getComments()) {
                Element comment = new Element("Comment");
                commentSet.addContent(comment);
                comment.addContent(_createElement("AdmFlg", commentModel.getNtp040CommentDelFlg()));
                comment.addContent(_createElement("NpcSid",
                        commentModel.getNtp040CommentMdl().getNpcSid()));
                comment.addContent(_createElement("UsrSid",
                        commentModel.getNtp040CommentMdl().getUsrSid()));
                comment.addContent(_createElement("UsrName",
                        (commentModel.getNtp040UsrInfMdl().getUsiSei()
                                + " " + commentModel.getNtp040UsrInfMdl().getUsiMei())));
                comment.addContent(_createElement("UsrUkoFlg",
                        commentModel.getNtp040UsrInfMdl().getUsrUkoFlg()));
                comment.addContent(_createElement("NpcComment",
                        StringUtilHtml.transToText(commentModel
                                .getNtp040CommentMdl().getNpcComment())));
                if (commentModel.getNtp040UsrInfMdl().getBinSid() == 0) {
                    //写真なし
                    comment.addContent(_createElement("UsrImgPath", ""));
                } else {
                    if (commentModel.getNtp040UsrInfMdl().getUsiPictKf() == 0) {
                        //写真あり 公開
                        comment.addContent(_createElement("UsrImgPath",
                                "./common/cmn100.do?CMD=getImageFile&cmn100binSid="
                                        + commentModel.getNtp040UsrInfMdl().getBinSid()));

                    } else {
                        //写真あり 非公開
                        comment.addContent(_createElement("UsrImgPath", "secret"));
                    }
                }
            }
        }
        return doc;
    }
    /**
     * <br>指定ユーザの月間日報を取得します
     * @param searchMdl 検索条件
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @param notAccessUserList 特例アクセス閲覧不可ユーザ
     * @return int データ件数
     * @throws SQLException SQL実行時例外
     */
    public int getNippouCnt(
            NippouListSearchModel searchMdl,
            int sessionUsrSid,
            Connection con,
            List<Integer> notAccessUserList) throws SQLException {

        //DB日報件数
        int ret = 0;

        //日報情報を取得(指定ユーザ)
        NippouSearchDao ntpDao = new NippouSearchDao(con);
        ret = ntpDao.getCount(searchMdl, sessionUsrSid, notAccessUserList);
        return ret;
    }
    /**
     * <br>指定ユーザの月間日報を取得します
     * @param searchMdl 検索条件
     * @param sessionUsrSid セッションユーザSID
     * @param sort ソート区分
     * @param offset オフセット有無
     * @param req リクエスト
     * @param con コネクション
     * @param notAccessUserList 特例アクセス閲覧不可ユーザ
     * @return ArrayList グループ>指定ユーザの順に格納
     * @throws SQLException SQL実行時例外
     */
    private List<ApiNippouDataModel> __getNtpList(
            NippouListSearchModel searchMdl,
            int sessionUsrSid,
            int sort,
            boolean offset,
            HttpServletRequest req,
            Connection con,
            List<Integer> notAccessUserList) throws SQLException {

        //DB日報情報
        ArrayList < NtpDataModel > ntpDataList = new ArrayList<NtpDataModel>();
        RequestModel reqMdl = getRequestModel(req);
        NtpCommonBiz ntpCmnBiz = new NtpCommonBiz(con, reqMdl);
        AnkenPermitCheckModel napMdl = new AnkenPermitCheckModel(reqMdl,
                ntpCmnBiz.isNippouAdmin(con, reqMdl));


        //指定ユーザ日報
        NippouSearchDao ntpSearchDao = new NippouSearchDao(con);
        ArrayList<NippouSearchModel> searchList
        = ntpSearchDao.getNippouDataAll(searchMdl, sessionUsrSid, sort, notAccessUserList, napMdl);

        ntpDataList = new ArrayList<NtpDataModel>(searchList);
        ApiNippouSearchBiz searchBiz = new ApiNippouSearchBiz(getRequestModel(req), con);
        return searchBiz.getReports(ntpDataList);
    }

}
