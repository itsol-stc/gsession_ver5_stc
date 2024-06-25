package jp.groupsession.v2.ip.ipk010;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.IpkAdminInfo;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.IpkUsedDataBiz;
import jp.groupsession.v2.ip.dao.IpkAddAdmDao;
import jp.groupsession.v2.ip.dao.IpkAddDao;
import jp.groupsession.v2.ip.dao.IpkBinDao;
import jp.groupsession.v2.ip.dao.IpkNetAdmDao;
import jp.groupsession.v2.ip.dao.IpkNetDao;
import jp.groupsession.v2.ip.model.IpkNetModel;

/**
 * <br>[機  能] IP管理 ネットワーク一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ipk010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ipk010Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException 実行例外
     */
    public void setInitData(Ipk010ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException {
        try {
            //セッションユーザSIDを取得する。
            BaseUserModel usModel = reqMdl.getSmodel();
            int sessionUsrSid = usModel.getUsrsid();

            //ネットワーク一覧を設定する。
            IpkAdminInfo ipkAdminInfo = new IpkAdminInfo();
            ArrayList<String> admList = ipkAdminInfo.getUsrAdminNetList(sessionUsrSid, con);

            //GS管理者･IP管理者か判定する。
            boolean allAdmin = ipkAdminInfo.isGsIpAdm(reqMdl, con);
            if (allAdmin) {
                paramMdl.setAllAdm(IpkConst.KANRI_KENGEN_ARI);
            }
            //IPアドレス一覧情報を取得
            IpkNetDao ipkDao = new IpkNetDao(con);
            ArrayList<IpkNetModel> net010Data = ipkDao.select();

            //IPアドレス一覧情報をセット
            ArrayList<IpkNetModel> newNet010Data = new ArrayList<IpkNetModel>();
            for (IpkNetModel model : net010Data) {
                //変更・削除ボタンの表示フラグをセット
                if (allAdmin) {
                    model.setNetAdm(IpkConst.KANRI_KENGEN_ARI);
                } else {
                    for (String usrAdmIntSid : admList) {
                        if (model.getNetSid() == NullDefault.getInt(usrAdmIntSid, 0)) {
                            model.setNetAdm(IpkConst.KANRI_KENGEN_ARI);
                            break;
                        }
                        model.setNetAdm(IpkConst.KANRI_KENGEN_NASI);
                    }
                }

                //非公開のネットワーワークは閲覧権限がなければ表示しない。
                if (model.isNetAdm() == IpkConst.KANRI_KENGEN_NASI
                        & model.getNetDsp().equals(IpkConst.IPK_NET_NOT_DSP)) {
                    continue;
                }

                model.setNetSid(model.getNetSid());
                model.setNetSabnet(model.getNetSabnet());
                model.setNetNetad(model.getNetNetad());
                model.setNetName(model.getNetName());
                //コメントをhtml表示用に変換
                model.setNetMsg(NullDefault.getString(
                        StringUtilHtml.transToHTmlPlusAmparsant(
                                model.getNetMsg()), ""));
                model.setNetMsg(model.getNetMsg());
                model.setNetSort(model.getNetSort());
                newNet010Data.add(model);
            }
            paramMdl.setIpkNetList(newNet010Data);
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * <br>[機  能] ネットワーク情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException 実行例外
     */
    public void deleteNetwork(int netSid, Connection con, int sessionUsrSid)
    throws SQLException {
        //コミットフラグ
        boolean commitFlg = false;
        con.setAutoCommit(false);
        try {
            //ネットワーク情報のデータ使用量を登録(削除対象のデータ使用量を減算)
            IpkUsedDataBiz usedDataBiz = new IpkUsedDataBiz(con);
            usedDataBiz.insertNetworkSize(netSid, false);

            //ネットワーク情報の削除
            IpkNetDao ipkDao = new IpkNetDao(con);

            //ネットワーク管理者情報を削除する。
            IpkNetAdmDao ipkNetAdmDao = new IpkNetAdmDao(con);
            ipkNetAdmDao.deleteAdmin(netSid);
            IpkAddDao ipkAddDao = new IpkAddDao(con);

            //バイナリー情報の論理削除、添付情報の削除を行う
            IpkBinDao ipkBinDao = new IpkBinDao(con);
            UDate now = new UDate();
            ipkBinDao.removeIpkBinData(netSid, sessionUsrSid, now);
            ipkBinDao.deleteNetworkTemp(netSid);

            //ネットワークのIPアドレスリストを取得する。
            ArrayList<String> iadSidList = ipkAddDao.selectIadSid(netSid);
            ArrayList<Integer> intIadSidList = new ArrayList<Integer>();
            if (iadSidList.size() != 0) {
                for (String iadSid : iadSidList) {
                    intIadSidList.add(Integer.parseInt(iadSid));
                }
                //IPアドレスの管理者削除
                IpkAddAdmDao ipkAddAdmDao = new IpkAddAdmDao(con);
                ipkAddAdmDao.deleteHukusuu(intIadSidList);

                //1つのネットワークのIPアドレス削除する。
                ipkAddDao.deleteNetwork(netSid);
            }
            //ネットワーク情報の削除
            ipkDao.deleteNetwork(netSid);
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
     * <br>[機  能] ネットワーク名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param con コネクション
     * @return netName ネットワーク名
     * @throws SQLException 実行例外
     */
    public String getNetName(String netSid, Connection con)
    throws SQLException {

        IpkNetDao dao = new IpkNetDao(con);
        ArrayList<IpkNetModel> netInfo = dao.selectNetwork(NullDefault.getInt(netSid, -1));
        String netName = "";

        if (netInfo != null && netInfo.size() > 0) {

            for (IpkNetModel model : netInfo) {
                netName = model.getNetName();
        }

        }
        return netName;
    }

}