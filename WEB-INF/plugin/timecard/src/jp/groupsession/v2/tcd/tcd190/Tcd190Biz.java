package jp.groupsession.v2.tcd.tcd190;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.TimecardYukyuConfBiz;
import jp.groupsession.v2.tcd.dao.TcdYukyudataDao;
import jp.groupsession.v2.tcd.dao.TimecardDao;

/**
 * <br>[機  能] タイムカード管理者設定 有休日数一覧画面のビジネスロジッククラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd190Biz {
    
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    
    /** コンストラクタ */
    public Tcd190Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 表示内容の設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    protected void _setInitData(Tcd190ParamModel paramMdl, Connection con) throws SQLException {
        
        TimecardYukyuConfBiz tycBiz = new TimecardYukyuConfBiz();
        TimecardBiz tcdBiz = new TimecardBiz();
        
        //表示年度の設定
        List<LabelValueBean> nendoList = tycBiz.getNendoList(con);
        paramMdl.setTcdNendoList(nendoList);

        //表示グループセレクトボックスの選択
        BaseUserModel usMdl = reqMdl__.getSmodel();
        int usrSid = usMdl.getUsrsid();
        int usrKbn = tcdBiz.getUserKbn(con, usMdl);
        List<LabelValueBean> groupList = tcdBiz.getGroupLabelList(con, usrSid, usrKbn, reqMdl__);
        paramMdl.setTcdGroupList(groupList);
        
        //年度が未選択の場合、現在年度を設定
        boolean nendoFlg = false;
        for (LabelValueBean nendoBean : nendoList) {
            if (String.valueOf(paramMdl.getTcd190nendo()).equals(nendoBean.getValue())) {
                nendoFlg = true;
                break;
            }
        }
        if (!nendoFlg) {
            paramMdl.setTcd190nendo(tycBiz.getThisNendo(con));
        }
        
        //グループが未選択の場合、デフォルトグループを設定
        boolean groupFlg = false;
        for (LabelValueBean groupBean : groupList) {
            if (String.valueOf(paramMdl.getTcd190group()).equals(groupBean.getValue())) {
                groupFlg = true;
                break;
            }
        }
        if (!groupFlg) {
            paramMdl.setTcd190group(String.valueOf(tcdBiz.getInitGpSid(usrSid, groupList, con)));
        }
        
        //ページングコンボボックスの選択値リストの作成
        TcdYukyudataDao tydDao = new TcdYukyudataDao(con);
        //期首月を取得
        TimecardDao tcdDao = new TimecardDao(con);
        int kishuMonth = NullDefault.getInt(
                String.valueOf(tcdDao.getKishuMonth()), GSConstCommon.DEFAULT_KISYU);
        Tcd190SearchModel searchMdl = __getSearchModel(paramMdl, kishuMonth);
        int dataCount = tydDao.getYukyuDataCount(searchMdl);
        int maxPage = (int) Math.ceil(dataCount / (double) GSConstTimecard.MAX_YUKYU_GET_COUNT);
        if (maxPage < 1) {
            maxPage = 1;
        }
        List<LabelValueBean> pageCombo = new ArrayList<LabelValueBean>();
        for (int i = 1; i <= maxPage; i++) {
            LabelValueBean bean = new LabelValueBean();
            bean.setValue(String.valueOf(i));
            bean.setLabel(String.valueOf(i) + " / " + String.valueOf(maxPage));
            pageCombo.add(bean);
        }
        if (pageCombo.size() > 1) {
            paramMdl.setTcd190pageList(pageCombo);    
        }
        
        //ソートキーチェック
        if (paramMdl.getTcd190sortKey() != GSConstTimecard.SORT_SIMEI
                && paramMdl.getTcd190sortKey() != GSConstTimecard.SORT_SYAINNO
                && paramMdl.getTcd190sortKey() != GSConstTimecard.SORT_YUKYUDAYS
                && paramMdl.getTcd190sortKey() != GSConstTimecard.SORT_YUKYU_USEDAYS
                && paramMdl.getTcd190sortKey() != GSConstTimecard.SORT_YUKYU_USEPERCENT) {
            
            paramMdl.setTcd190sortKey(GSConstTimecard.SORT_SIMEI);
            paramMdl.setTcd190order(GSConst.ORDER_KEY_ASC);
        }
        
        //表示ページチェック
        int page = paramMdl.getTcd190page(); 
        if (page < 1) {
            paramMdl.setTcd190page(1);
        } else if (page > maxPage) {
            paramMdl.setTcd190page(maxPage);
        }
        searchMdl = __getSearchModel(paramMdl, kishuMonth);
        
        //画面に表示する有休情報一覧を設定
        List<Tcd190YukyuModel> yukyuList = tydDao.getYukyuDataList(searchMdl);
        paramMdl.setTcd190YukyuModelList(yukyuList);
    }
    
    /**
     * <br>[機  能] パラメータ情報, 期首月から検索用オブジェクトを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return 検索用オブジェクト
     */
    private Tcd190SearchModel __getSearchModel(Tcd190ParamModel paramMdl, int kishuMonth) {
        Tcd190SearchModel ret = new Tcd190SearchModel();
        ret.setGroupSid(Integer.parseInt(paramMdl.getTcd190group()));
        ret.setNendo(paramMdl.getTcd190nendo());
        ret.setSortKey(paramMdl.getTcd190sortKey());
        ret.setSortOrder(paramMdl.getTcd190order());
        ret.setPage(paramMdl.getTcd190page());
        ret.setKishuMonth(kishuMonth);
        int kimatuMonth;
        if (kishuMonth == 1) {
            kimatuMonth = 12;
        } else {
            kimatuMonth = kishuMonth -1;
        }
        ret.setKimatuMonth(kimatuMonth);
        return ret;
    }
    
}
