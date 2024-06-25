package jp.groupsession.v2.zsk.zsk010;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrInoutDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrInoutModel;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;
import jp.groupsession.v2.zsk.dao.ZaiIndexDao;
import jp.groupsession.v2.zsk.dao.ZaiInfoDao;
import jp.groupsession.v2.zsk.dao.ZaiPriConfDao;
import jp.groupsession.v2.zsk.model.ZaiIndexModel;
import jp.groupsession.v2.zsk.model.ZaiInfoModel;
import jp.groupsession.v2.zsk.model.ZaiInfoPlusModel;
import jp.groupsession.v2.zsk.model.ZaiPriConfModel;
import jp.groupsession.v2.zsk.model.ZskSortModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 在席管理 在席状況画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk010Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;


    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Zsk010Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
   }

    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl Zsk010ParamModel
     * @param con コネクション
     * @param domain ドメイン
     * @return Sch010Form アクションフォーム
     * @throws IOException 画像ファイル読み込み時例外
     * @throws IOToolsException 画像ファイル読み込み時例外
     * @throws FileNotFoundException 画像ファイル読み込み時例外
     * @throws SQLException SQL実行時例外
     * @throws UnsupportedEncodingException エンコード例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public Zsk010ParamModel getInitData(
            Zsk010ParamModel paramMdl,
            Connection con,
            String domain)
    throws
    IOException,
    IOToolsException,
    FileNotFoundException,
    UnsupportedEncodingException,
    SQLException,
    TempFileException {

        //セッション情報を取得
        HttpSession session = reqMdl__.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
        paramMdl.setAdminKbn(String.valueOf(GSConst.USER_NOT_ADMIN));
        CommonBiz commonBiz = new CommonBiz();
        boolean adminUser = commonBiz.isPluginAdmin(con, usModel, GSConstZaiseki.PLUGIN_ID_ZAISEKI);
        if (adminUser) {
            paramMdl.setAdminKbn(String.valueOf(GSConst.USER_ADMIN));
        }

        //表示座席表コンボ
        paramMdl.setZifLabelList(__getZasekiHyoLabel(sessionUsrSid, con));
        int dspZifSid = -1;
        int reloadTime = GSConstZaiseki.AUTO_RELOAD_10MIN;

        //個人設定
        ZaiPriConfDao priDao = new ZaiPriConfDao(con);
        ZaiPriConfModel priMdl = priDao.select(sessionUsrSid);

        //表示する座席表、自動リロード時間
        if (priMdl == null) {
            List<LabelValueBean> labelList = paramMdl.getZifLabelList();
            LabelValueBean labelBean = labelList.get(0);
            dspZifSid = NullDefault.getInt(
                    paramMdl.getSelectZifSid(), Integer.parseInt(labelBean.getValue()));
        } else if (priMdl.getZifSid() == GSConstZaiseki.ZASEKI_NOT_SELECT) {
            List<LabelValueBean> labelList = paramMdl.getZifLabelList();
            LabelValueBean labelBean = labelList.get(0);
            dspZifSid = NullDefault.getInt(
                    paramMdl.getSelectZifSid(), Integer.parseInt(labelBean.getValue()));
            reloadTime = priMdl.getZpcReload();
        } else {
            dspZifSid = NullDefault.getInt(paramMdl.getSelectZifSid(), priMdl.getZifSid());
            reloadTime = priMdl.getZpcReload();
        }
        paramMdl.setSelectZifSid(String.valueOf(dspZifSid));
        paramMdl.setZsk010Reload(reloadTime);

        log__.debug("表示する座席表SID=>" + dspZifSid);
        ZaiInfoDao infoDao = new ZaiInfoDao(con);
        ZaiInfoPlusModel infoPlus = infoDao.getZaiInfoPlusModel(dspZifSid);
        //座席表画像情報取得
        __getImageFile(paramMdl, infoPlus, con, domain);
        ZaiIndexDao indexDao = new ZaiIndexDao(con);
        ArrayList<ZaiIndexModel> indexList = indexDao.select(dspZifSid);

        ZsjCommonBiz cmnBiz = new ZsjCommonBiz(reqMdl__);
        ArrayList<Integer> users = cmnBiz.getElementSids(
                indexList, GSConstZaiseki.ELEMENT_KBN_USR, sessionUsrSid);

        ZskSortModel sortMdl = cmnBiz.getSortData(con, sessionUsrSid);

        UserSearchDao uDao = new UserSearchDao(con);
        ArrayList<UserSearchModel> uList = uDao.getUsersInfoJtkb(
                users,
                sortMdl.getSort1(),
                sortMdl.getOrder1(),
                sortMdl.getSort2(),
                sortMdl.getOrder2());
        UserSearchModel myMdl = uDao.getUserInfoJtkb(sessionUsrSid, GSConstUser.USER_JTKBN_ACTIVE);


        //ユーザリストの先頭はセッションユーザ
        uList.add(0, myMdl);
        //ショートメールプラグインを使用していないユーザを除外する。
        //送信制限されているユーザを除外する。
        List<Integer> smlUsrs = new ArrayList<Integer>(users);
        smlUsrs.add(0, reqMdl__.getSmodel().getUsrsid());
        smlUsrs = (ArrayList<Integer>) commonBiz.getCanUseSmailUser(con, smlUsrs);
        SmlCommonBiz smlCommonBiz = new SmlCommonBiz(con, reqMdl__);
        smlUsrs = smlCommonBiz.getValiableDestUsrSid(con,
                reqMdl__.getSmodel().getUsrsid(), smlUsrs);
        //ショートメール有効無効設定
        for (UserSearchModel um:uList) {
            if (!smlUsrs.contains(um.getUsrSid())) {
                um.setSmlAble(0);
            } else {
                um.setSmlAble(1);
            }
        }

        //hidden エレメントKEY
        paramMdl.setElementKeyList(getElementList(paramMdl, dspZifSid, indexList, con));
        //ユーザリスト
        paramMdl.setUserList(uList);

        //在席ステータスコンボ
        ZsjCommonBiz zbiz = new ZsjCommonBiz(reqMdl__);
        List<LabelValueBean> label = zbiz.createZskUioStatusLabel();
        paramMdl.setStatusLabelList(label);
        //セッションユーザの在席ステータス
        CmnUsrInoutModel param = new CmnUsrInoutModel();
        param.setUioSid(sessionUsrSid);
        CmnUsrInoutDao ioDao = new CmnUsrInoutDao(con);
        CmnUsrInoutModel ret = ioDao.select(param);

        //レコード無し(初ログイン or 初ログインから一度も在席を更新していない)
        if (ret == null) {
            paramMdl.setUioStatus(
                    NullDefault.getString(
                            paramMdl.getUioStatus(),
                            String.valueOf(GSConst.UIOSTS_IN)));
            paramMdl.setUioStatusBiko(
                    NullDefault.getString(
                            paramMdl.getUioStatusBiko(),
                            ""));
            paramMdl.setUioStatusDb(String.valueOf(GSConst.UIOSTS_IN));
        //レコード有り
        } else {
            paramMdl.setUioStatus(
                    NullDefault.getString(
                            paramMdl.getUioStatus(),
                            String.valueOf(ret.getUioStatus())));
            paramMdl.setUioStatusBiko(
                    NullDefault.getString(
                            paramMdl.getUioStatusBiko(),
                            NullDefault.getString(ret.getUioBiko(), "")));
            //最新状況を設定
            paramMdl.setUioStatusDb(String.valueOf(ret.getUioStatus()));
        }

        return paramMdl;
    }

    /**
     * 座席表SIDを指定し表示エレメントのKEY配列を取得します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk010ParamModel
     * @param zifSid 座席表SID
     * @param con コネクション
     * @param indexList 座標リスト
     * @return ArrayList エレメントKEYリスト
     */
    public ArrayList<String> getElementList(
            Zsk010ParamModel paramMdl,
            int zifSid,
            ArrayList<ZaiIndexModel> indexList,
            Connection con) {

        ArrayList<String> ret = new ArrayList<String>();
        StringBuilder buf = null;
        if (indexList.size() < 1) {
            return null;
        }

//        int cnt = 0;
//        for (ZaiIndexModel model : indexList) {
//            cnt++;
        for (int cnt = 1; cnt <= indexList.size(); cnt++) {
            buf = new StringBuilder();
            buf.append(GSConstZaiseki.ELEMENT_KEY);
            buf.append(GSConstZaiseki.ELEMENT_SEPARATOR);
            buf.append(cnt);
            ret.add(buf.toString());
        }

        return ret;

    }

    /**
     * <br>[機  能] 項目座標情報保存/読込用ObjectFileを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return ObjectFile
     */
    public static ObjectFile getZskInfoObjctFile(String tempDir) {
        return new ObjectFile(tempDir, GSConstZaiseki.FNAME_ELEMENT_INFO);
    }

    /**
     * 座席表コンボを生成します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @return List コンボリスト
     * @throws SQLException SQL実行時例外
     */
    private List<LabelValueBean> __getZasekiHyoLabel(int sessionUsrSid, Connection con)
    throws SQLException {
        LabelValueBean bean = null;
        List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        ZaiInfoDao infoDao = new ZaiInfoDao(con);
        ArrayList<ZaiInfoModel> infoList = infoDao.select();

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = gsMsg.getMessage("cmn.select.plz");


        bean = new LabelValueBean();
        bean.setValue(String.valueOf(GSConstZaiseki.NONE_SELECT));
        bean.setLabel(msg);
        ret.add(bean);
        for (ZaiInfoModel infoMdl : infoList) {
            bean = new LabelValueBean();
            bean.setValue(String.valueOf(infoMdl.getZifSid()));
            bean.setLabel(infoMdl.getZifName());
            ret.add(bean);
        }
        return ret;
    }
    /**
     * 座席表情報を取得します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk010ParamModel
     * @param model 座席表情報 + 画像情報
     * @param con コネクション
     * @param domain ドメイン
     * @throws IOToolsException 情報取得に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private void __getImageFile(
            Zsk010ParamModel paramMdl,
            ZaiInfoPlusModel model,
            Connection con,
            String domain) throws IOToolsException, TempFileException {

        if (model != null) {
            CommonBiz cmnBiz = new CommonBiz();
            CmnBinfModel cmnBinMdl = cmnBiz.getBinInfo(con, model.getBinSid(), domain);

            //写真
            paramMdl.setZsk010binSid(model.getBinSid());
            paramMdl.setImageFileName(cmnBinMdl.getBinFileName());
        }
    }


    /**
     * <br>[機  能] 在席ステータスを更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ステータス
     */
    public int updateZskStatus(Zsk010ParamModel paramMdl, Connection con)
            throws SQLException {

        //在席状況＆コメントを変更
        int userSid = NullDefault.getInt(paramMdl.getUioUpdateUsrSid(), 0);
        int status = NullDefault.getInt(paramMdl.getUioUpdateStatus(), GSConst.UIOSTS_IN);

        boolean commitFlg = false;
        con.setAutoCommit(false);
        ZsjCommonBiz biz = new ZsjCommonBiz(reqMdl__);
        try {
            biz.updateUserZskStatusOnly(userSid, status, con);
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("在席ステータスの更新に失敗しました。" + e);
            throw new SQLException();
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                con.rollback();
            }
        }
        return status;
    }

}
