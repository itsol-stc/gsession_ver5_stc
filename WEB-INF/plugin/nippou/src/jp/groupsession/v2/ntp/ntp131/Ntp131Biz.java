package jp.groupsession.v2.ntp.ntp131;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpShohinCategoryDao;
import jp.groupsession.v2.ntp.dao.NtpShohinDao;
import jp.groupsession.v2.ntp.model.NtpShohinCategoryModel;
import jp.groupsession.v2.ntp.model.NtpShohinModel;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 日報 商品登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp131Biz {

    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Ntp131Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp131ParamModel
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp131ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        //カテゴリコンボ作成
        ArrayList<LabelValueBean> ntpShohinCatList = new ArrayList<LabelValueBean>();
        NtpShohinCategoryDao catDao = new NtpShohinCategoryDao(con);
        List<NtpShohinCategoryModel> catMdlList = catDao.select();
        for (NtpShohinCategoryModel mdl : catMdlList) {
            String catName = mdl.getNscName();
            String catSid = String.valueOf(mdl.getNscSid());
            ntpShohinCatList.add(new LabelValueBean(catName, catSid));
        }
        paramMdl.setNtp130CategoryList(ntpShohinCatList);
        paramMdl.setNtp130SelCategorySid(paramMdl.getNtp130SelCategorySid());

        if (paramMdl.getNtp130ProcMode().equals(GSConstNippou.CMD_EDIT)) {
            //変更処理
            NtpShohinDao shohinDao = new NtpShohinDao(con);
            NtpShohinModel shohinMdl = shohinDao.select(paramMdl.getNtp130NhnSid());
            if (shohinMdl != null) {
                paramMdl.setNtp130SelCategorySid(shohinMdl.getNscSid());
                paramMdl.setNtp131ShohinCode(shohinMdl.getNhnCode());
                paramMdl.setNtp131ShohinName(shohinMdl.getNhnName());
                paramMdl.setNtp131PriceSale(StringUtil.toCommaFromString(
                        Integer.toString(shohinMdl.getNhnPriceSale())));
                paramMdl.setNtp131PriceCost(StringUtil.toCommaFromString(
                        Integer.toString(shohinMdl.getNhnPriceCost())));
                paramMdl.setNtp131Hosoku(shohinMdl.getNhnHosoku());
            }
        }

        if (paramMdl.getNtp131CopyFlg() == 1) {
            paramMdl.setNtp131ShohinCode("");
        }
    }
}
