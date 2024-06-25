package jp.groupsession.v2.ip.ipk090;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.ip.dao.IpkSpecMstDao;
import jp.groupsession.v2.ip.model.IpkSpecMstModel;

/**
 * <br>[機  能] IP管理 スペックマスタメンテナンス一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ipk090Biz {

    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Ipk090ParamModel paramMdl, Connection con)
    throws SQLException {
        try {
            IpkSpecMstDao dao = new IpkSpecMstDao(con);
            IpkSpecMstModel model = null;
            ArrayList<IpkSpecMstModel> specList = new ArrayList<IpkSpecMstModel>();
            //一覧情報を取得する。
            ArrayList<IpkSpecMstModel> specInfList
                = dao.select(NullDefault.getInt(paramMdl.getSpecKbn(), 1));

            for (IpkSpecMstModel mdl : specInfList) {
                model = new IpkSpecMstModel();
                model.setIsmSid(mdl.getIsmSid());
                model.setIpk100name(StringUtilHtml.transToHTmlPlusAmparsant(mdl.getIpk100name()));
                model.setIpk100level(mdl.getIpk100level());
                //備考をHTML表示用に変換する。
                model.setIpk100biko(NullDefault.getString(
                        StringUtilHtml.transToHTmlPlusAmparsant(
                                mdl.getIpk100biko()), ""));
                specList.add(model);
            }
            paramMdl.setSpecInfList(specList);
        } catch (SQLException e) {
            throw e;
        }
    }
}