package jp.groupsession.v2.ntp.ntp180;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.dao.NtpkatudouHouhouSortDao;
import jp.groupsession.v2.ntp.model.NtpKthouhouModel;

/**
 * <br>[機  能] 日報 活動方法一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp180Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp180Biz.class);
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Ntp180Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp180ParamModel
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp180ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

//        //活動方法一覧の取得・設定
//        NtpKthouhouDao houhouDao = new NtpKthouhouDao(con);
//        paramMdl.setNtp180KthouhouList(houhouDao.select());

        //活動方法情報を取得する
        Ntp180Dao houhouDao = new Ntp180Dao(con);
        List<NtpKthouhouModel> kthouhouList = houhouDao.getKthouhouList();
        List<Ntp180KtHouhouDspModel> kthDspList = new ArrayList<Ntp180KtHouhouDspModel>();
        Ntp180KtHouhouDspModel kthDspMdl = null;

        for (NtpKthouhouModel kthMdl : kthouhouList) {
            kthDspMdl = new Ntp180KtHouhouDspModel();

            kthDspMdl.setKthSid(kthMdl.getNkhSid());
            kthDspMdl.setKthCode(kthMdl.getNkhCode());
            kthDspMdl.setKthName(kthMdl.getNkhName());
            kthDspMdl.setKthSort(kthMdl.getNhsSort());
            kthDspMdl.setKthValue(String.valueOf(kthMdl.getNkhSid()));
            kthDspList.add(kthDspMdl);
        }
        paramMdl.setNtp180KthouhouList(kthDspList);

        if (StringUtil.isNullZeroString(paramMdl.getNtp180sortHouhou())
                && kthouhouList.size() > 0) {

            Ntp180KtHouhouDspModel appspMdl = kthDspList.get(0);
            paramMdl.setNtp180sortHouhou(String.valueOf(appspMdl.getKthSid()));
        }
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Ntp180ParamModel
     * @param userSid ユーザSID
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Ntp180ParamModel paramMdl, int userSid, int changeKbn)
            throws SQLException {

        SortResult<NtpKthouhouModel> result = null;
        final Ntp180Dao dao = new Ntp180Dao(con);
        final NtpkatudouHouhouSortDao nhsDao = new NtpkatudouHouhouSortDao(con);

        //ラジオ選択値取得
        String selectedKey = paramMdl.getNtp180sortHouhou();
        if (StringUtil.isNullZeroString(selectedKey) || !ValidateUtil.isNumber(selectedKey)) {
            return;
        }

        //選択された項目の施設グループSID + ソート順
        int motoSid = Integer.parseInt(selectedKey);

        SortChangeBiz<NtpKthouhouModel> sortBiz =
                SortChangeBiz.<NtpKthouhouModel> builder()
                .setFuncTargetList(() -> {
                    return dao.getKthouhouList();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getNkhSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getNhsSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getNkhSid() == m2.getNkhSid()) {
                        return 0;
                    } else {
                        return (m1.getNkhSid() - m2.getNkhSid()) 
                                / Math.abs(m1.getNkhSid() - m2.getNkhSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    nhsDao.updateSort(m.getNkhSid(), newSort);
                })
                .build();
        
        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        
        if (result != null) {
            String newSortRadio = String.valueOf(result.getMdl().getNkhSid());
            paramMdl.setNtp180sortHouhou(newSortRadio);
        }
    }
}
