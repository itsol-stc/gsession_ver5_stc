package jp.groupsession.v2.zsk.zsk140;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;
import jp.groupsession.v2.zsk.dao.ZaiAdmConfDao;
import jp.groupsession.v2.zsk.model.ZaiAdmConfModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 在席管理 管理者設定 座席表メンバーデフォルト表示設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk140Biz {

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     */
    public Zsk140Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
   }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Zsk140ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws Exception SQL実行エラー
     */
    public void setInitData(Zsk140ParamModel paramMdl,
                             BaseUserModel umodel,
                             Connection con) throws Exception {

        if (paramMdl.getZsk140initKbn() <= 0) {
            //表示順設定
            ZsjCommonBiz biz = new ZsjCommonBiz(reqMdl__);
            ZaiAdmConfModel model = biz.getZaiAdminData(con);

            if (model != null) {
                paramMdl.setZsk140SortKbn(model.getZacSortKbn());
                paramMdl.setZsk140SortKey1(model.getZacSortKey1());
                paramMdl.setZsk140SortKey2(model.getZacSortKey2());
                paramMdl.setZsk140SortOrder1(model.getZacSortOrder1());
                paramMdl.setZsk140SortOrder2(model.getZacSortOrder2());
            }

            paramMdl.setZsk140initKbn(1);
        }

        setLabelData(paramMdl);
    }

    /**
     * <br>[機  能] コンボ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Zsk140ParamModel
     * @throws Exception SQL実行エラー
     */
    public void setLabelData(Zsk140ParamModel paramMdl) throws Exception {

        ArrayList<LabelValueBean> sortLabel = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String name = gsMsg.getMessage("cmn.name");
        String number = gsMsg.getMessage("cmn.employee.staff.number");
        String post = gsMsg.getMessage("cmn.post");
        String birth = gsMsg.getMessage("cmn.birthday");
        String zaiseki = gsMsg.getMessage("zsk.20");
        String comment = gsMsg.getMessage("zsk.23");
        String sortkey1 = gsMsg.getMessage("cmn.sortkey") + "1";
        String sortkey2 = gsMsg.getMessage("cmn.sortkey") + "2";
        String[] sortZskText = {name, number, post, birth, zaiseki, comment,
                sortkey1, sortkey2};

        for (int i = 0; i < sortZskText.length; i++) {
            String label = sortZskText[i];
            String value = Integer.toString(GSConstZaiseki.SORT_KEY_ZSK[i]);
            sortLabel.add(new LabelValueBean(label, value));
        }
        paramMdl.setZsk140SortKeyLabel(sortLabel);

    }

    /**
     * <br>[機  能] 設定された管理者設定情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk140ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void updateAconfSort(Zsk140ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        int sessionUserSid = umodel.getUsrsid();
        UDate now = new UDate();

        //在席管理管理者設定を更新
        ZaiAdmConfModel aconf = new ZaiAdmConfModel();
        aconf.setZacSortKbn(paramMdl.getZsk140SortKbn());
        aconf.setZacSortKey1(paramMdl.getZsk140SortKey1());
        aconf.setZacSortOrder1(paramMdl.getZsk140SortOrder1());
        aconf.setZacSortKey2(paramMdl.getZsk140SortKey2());
        aconf.setZacSortOrder2(paramMdl.getZsk140SortOrder2());
        aconf.setZacEid(sessionUserSid);
        aconf.setZacEdate(now);

        ZaiAdmConfDao aconfDao = new ZaiAdmConfDao(con);
        aconfDao.updateSort(aconf);
    }

}