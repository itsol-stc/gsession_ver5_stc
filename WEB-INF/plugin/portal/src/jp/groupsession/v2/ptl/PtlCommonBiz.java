package jp.groupsession.v2.ptl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionDao;
import jp.groupsession.v2.man.model.base.PtlPortalPositionModel;
import jp.groupsession.v2.ptl.dao.PtlPortletImageDao;
import jp.groupsession.v2.ptl.model.PtlPortletImageModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ポータル全般で使用する共通ビジネスロジッククラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PtlCommonBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PtlCommonBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public PtlCommonBiz() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public PtlCommonBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 追加側のコンボで選択中のメンバーをメンバーリストに追加する
     *
     * <br>[解  説] 画面右側のコンボ表示に必要なSID(複数)をメンバーリスト(memberSid)で持つ。
     *              画面で追加矢印ボタンをクリックした場合、
     *              追加側のコンボで選択中の値(addSelectSid)をメンバーリストに追加する。
     *
     * <br>[備  考] 追加側のコンボで値が選択されていない場合はメンバーリストをそのまま返す
     *
     * @param addSelectSid 追加側のコンボで選択中の値
     * @param memberSid メンバーリスト
     * @return 追加済みのメンバーリスト
     */
    public String[] getAddMember(String[] addSelectSid, String[] memberSid) {

        if (addSelectSid == null) {
            return memberSid;
        }
        if (addSelectSid.length < 1) {
            return memberSid;
        }


        //追加後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();

        if (memberSid != null) {
            for (int j = 0; j < memberSid.length; j++) {
                if (!memberSid[j].equals("-1")) {
                    list.add(memberSid[j]);
                }
            }
        }

        for (int i = 0; i < addSelectSid.length; i++) {
            if (!addSelectSid[i].equals("-1")) {
                list.add(addSelectSid[i]);
            }
        }

        log__.debug("追加後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }

    /**
     * <br>[機  能] 登録に使用する側のコンボで選択中のメンバーをメンバーリストから削除する
     *
     * <br>[解  説] 登録に使用する側のコンボ表示に必要なSID(複数)をメンバーリスト(memberSid)で持つ。
     *              画面で削除矢印ボタンをクリックした場合、
     *              登録に使用する側のコンボで選択中の値(deleteSelectSid)をメンバーリストから削除する。
     *
     * <br>[備  考] 登録に使用する側のコンボで値が選択されていない場合はメンバーリストをそのまま返す
     *
     * @param deleteSelectSid 登録に使用する側のコンボで選択中の値
     * @param memberSid メンバーリスト
     * @return 削除済みのメンバーリスト
     */
    public String[] getDeleteMember(String[] deleteSelectSid, String[] memberSid) {

        if (deleteSelectSid == null) {
            return memberSid;
        }
        if (deleteSelectSid.length < 1) {
            return memberSid;
        }

        if (memberSid == null) {
            memberSid = new String[0];
        }

        //削除後に画面にセットするメンバーリストを作成
        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < memberSid.length; i++) {
            boolean setFlg = true;

            for (int j = 0; j < deleteSelectSid.length; j++) {
                if (memberSid[i].equals(deleteSelectSid[j])) {
                    setFlg = false;
                    break;
                }
            }

            if (setFlg) {
                list.add(memberSid[i]);
            }
        }

        log__.debug("削除後メンバーリストサイズ = " + list.size());
        return list.toArray(new String[list.size()]);
    }

    /**
     * <br>[機  能] インフォメーション情報の登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param ptlSid ポータルSID
     * @param userSid セッションユーザSID
     * @param plyPosition 追加位置
     * @param ptpSort ソート
     * @throws SQLException SQL実行例外
     */
    public void insertInfomation(int ptlSid,
                                int userSid,
                                int plyPosition,
                                int ptpSort)
    throws SQLException {

        PtlPortalPositionDao ptlPositionDao = new PtlPortalPositionDao(con__);
        UDate now = new UDate();
        String itemId = now.getTimeStamp();

        //ポータル位置情報を登録する。
        PtlPortalPositionModel posiModel = new PtlPortalPositionModel();
        posiModel.setPtlSid(ptlSid);
        posiModel.setPtpItemid(itemId);
        posiModel.setPlyPosition(plyPosition);
        posiModel.setPtpSort(ptpSort);
        posiModel.setPtpType(GSConstPortal.PTP_TYPE_INFORMATION);
        posiModel.setPltSid(-1);
        posiModel.setPctPid(GSConst.PLUGINID_MAIN);
        posiModel.setMscId("");
        posiModel.setPtpView(GSConstPortal.PTL_OPENKBN_OK);
        posiModel.setPtpParamkbn(GSConstPortal.PTP_PARAMKBN_OFF);
        ptlPositionDao.insert(posiModel);

    }

    /**
     * <br>[機  能] ポートレット 画像情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param pltSid ポートレットSID
     * @param userSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void deletePortletImage(int pltSid, int userSid, Connection con)
    throws SQLException {


        //ポートレット 画像情報と関連するバイナリSID一覧取得
        PtlPortletImageDao pltImageDao = new PtlPortletImageDao(con);
        List<PtlPortletImageModel> pltBinList = pltImageDao.getImageList(pltSid);

        if (pltBinList != null && !pltBinList.isEmpty()) {
            //バイナリ情報を論理削除
            List<Long> binSidList
                = pltBinList.stream()
                .map(s -> s.getBinSid())
                .collect(Collectors.toList());
            CmnBinfDao binDao = new CmnBinfDao(con);
            CmnBinfModel cbMdl = new CmnBinfModel();
            cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
            cbMdl.setBinUpuser(userSid);
            cbMdl.setBinUpdate(new UDate());
            binDao.updateJKbn(cbMdl, binSidList);
        }

        //内容添付情報を削除
        pltImageDao.deletePortletImage(pltSid);
    }

    /**
     * ポータル全般のログ出力を行う
     * @param map マップ
     * @param reqMdl リクエスト情報
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     */
    public void outPutLog(
            ActionMapping map,
            RequestModel reqMdl,
            String opCode,
            String level,
            String value) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textPortal = gsMsg.getMessage("ptl.1");

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(GSConstPortal.PLUGIN_ID);
        logMdl.setLogPluginName(textPortal);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType(), reqMdl));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @param reqMdl リクエスト情報
     * @return String
     */
    public String getPgName(String id, RequestModel reqMdl) {
        if (id == null) {
            return "";
        }

        log__.info("プログラムID==>" + id);
        GsMessage gsMsg = new GsMessage(reqMdl);

        if (id.equals("jp.groupsession.v2.ptl.ptl030.Ptl030Action")) {
            return gsMsg.getMessage("ptl.2");

        } else if (id.equals("jp.groupsession.v2.ptl.ptl040.Ptl040Action")) {
            return gsMsg.getMessage("ptl.ptl040.1");

        } else if (id.equals("jp.groupsession.v2.ptl.ptl050.Ptl050Action")) {
            return gsMsg.getMessage("ptl.ptl050.1");

        } else if (id.equals("jp.groupsession.v2.ptl.ptl050kn.Ptl050knAction")) {
            return gsMsg.getMessage("ptl.ptl050kn.1");

        } else if (id.equals("jp.groupsession.v2.ptl.ptl060kn.Ptl060knAction")) {
            return gsMsg.getMessage("ptl.ptl060kn.1");

        } else if (id.equals("jp.groupsession.v2.ptl.ptl080.Ptl080Action")) {
            return gsMsg.getMessage("ptl.ptl080.1");

        } else if (id.equals("jp.groupsession.v2.ptl.ptl081.Ptl081Action")) {
            return gsMsg.getMessage("ptl.ptl081.1");

        } else if (id.equals("jp.groupsession.v2.ptl.ptl090.Ptl090Action")) {
            return gsMsg.getMessage("ptl.9");

        } else if (id.equals("jp.groupsession.v2.ptl.ptl100kn.Ptl100knAction")) {
            return gsMsg.getMessage("ptl.ptl100kn.1");

        } else if (id.equals("jp.groupsession.v2.ptl.ptl110.Ptl110Action")) {
            return gsMsg.getMessage("cmn.categorylist");

        } else if (id.equals("jp.groupsession.v2.ptl.ptl120kn.Ptl120knAction")) {
            return gsMsg.getMessage("ptl.ptl120kn.1");

        } else if (id.equals("jp.groupsession.v2.ptl.ptl130kn.Ptl140knAction")) {
            return gsMsg.getMessage("cmn.setting.permissions.kn");

        } else if (id.equals("jp.groupsession.v2.ptl.ptl150kn.Ptl150knAction")) {
            return gsMsg.getMessage("cmn.default.setting");

        } else if (id.equals("jp.groupsession.v2.ptl.ptl160.Ptl160Action")) {
            return gsMsg.getMessage("cmn.cmn110.1");

        }

        return "";
    }
}
