package jp.groupsession.v2.ntp.ntp230;

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
import jp.groupsession.v2.ntp.dao.NtpTargetSortDao;
import jp.groupsession.v2.ntp.model.NtpTargetModel;

/**
 * <br>[機  能] 日報 目標一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp230Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp230Biz.class);

    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Ntp230Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp230ParamModel
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp230ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        //目標一覧の取得・設定
//        NtpTargetDao targetDao = new NtpTargetDao(con);
//        paramMdl.setNtp230TargetList((ArrayList)targetDao.select());

        //目標情報を取得する
        Ntp230Dao targetDao = new Ntp230Dao(con);
        List<NtpTargetModel> targetList = targetDao.getTargetList();
        List<Ntp230TargetDspModel> targetDspList = new ArrayList<Ntp230TargetDspModel>();
        Ntp230TargetDspModel targetDspMdl = null;

        for (NtpTargetModel trgMdl : targetList) {
            targetDspMdl = new Ntp230TargetDspModel();

            targetDspMdl.setTargetSid(trgMdl.getNtgSid());
            targetDspMdl.setTargetName(trgMdl.getNtgName());
            targetDspMdl.setTargetSort(trgMdl.getNtgSort());
            targetDspMdl.setTargetValue(String.valueOf(trgMdl.getNtgSid()));
            targetDspList.add(targetDspMdl);
        }
        paramMdl.setNtp230TargetList(targetDspList);

        if (StringUtil.isNullZeroString(paramMdl.getNtp230sortTarget())
                && targetList.size() > 0) {

            Ntp230TargetDspModel appspMdl = targetDspList.get(0);
            paramMdl.setNtp230sortTarget(String.valueOf(appspMdl.getTargetSid()));
        }
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Ntp230ParamModel
     * @param userSid ユーザSID
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Ntp230ParamModel paramMdl, int userSid, int changeKbn)
            throws SQLException {

        SortResult<NtpTargetModel> result = null;
        final Ntp230Dao dao = new Ntp230Dao(con);
        final NtpTargetSortDao ntsDao = new NtpTargetSortDao(con);

        //ラジオ選択値取得
        String selectedKey = paramMdl.getNtp230sortTarget();
        if (StringUtil.isNullZeroString(selectedKey) || !ValidateUtil.isNumber(selectedKey)) {
            return;
        }

        //選択された項目の施設グループSID + ソート順
        int motoSid = Integer.parseInt(selectedKey);

        SortChangeBiz<NtpTargetModel> sortBiz =
                SortChangeBiz.<NtpTargetModel> builder()
                .setFuncTargetList(() -> {
                    return dao.getTargetList();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getNtgSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getNtgSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getNtgSid() == m2.getNtgSid()) {
                        return 0;
                    } else {
                        return (m1.getNtgSid() - m2.getNtgSid()) 
                                / Math.abs(m1.getNtgSid() - m2.getNtgSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    ntsDao.updateSort(m.getNtgSid(), newSort);
                })
                .build();
        
        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        
        if (result != null) {
            String newSortRadio = String.valueOf(result.getMdl().getNtgSid());
            paramMdl.setNtp230sortTarget(newSortRadio);
        }
    }
}
