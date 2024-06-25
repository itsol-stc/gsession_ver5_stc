package jp.groupsession.v2.ntp.ntp140;

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
import jp.groupsession.v2.ntp.dao.NtpGyomuDao;
import jp.groupsession.v2.ntp.dao.NtpGyomuSortDao;
import jp.groupsession.v2.ntp.model.NtpGyomuModel;

/**
 * <br>[機  能] 日報 業種一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp140Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp140Biz.class);

    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Ntp140Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp140ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

//        //業務一覧の取得・設定
//        NtpGyomuDao gyomuDao = new NtpGyomuDao(con);
//        paramMdl.setNtp140GyomuList((ArrayList)gyomuDao.select());
        //活動方法情報を取得する
        Ntp140Dao gyomuDao = new Ntp140Dao(con);
        List<NtpGyomuModel> kthouhouList = gyomuDao.getGyoumuList();
        List<Ntp140GyomuDspModel> kthDspList = new ArrayList<Ntp140GyomuDspModel>();
        Ntp140GyomuDspModel kthDspMdl = null;

        for (NtpGyomuModel kthMdl : kthouhouList) {
            kthDspMdl = new Ntp140GyomuDspModel();

            kthDspMdl.setGymSid(kthMdl.getNgySid());
            kthDspMdl.setGymCode(kthMdl.getNgyCode());
            kthDspMdl.setGymName(kthMdl.getNgyName());
            kthDspMdl.setGymSort(kthMdl.getNgsSort());
            kthDspMdl.setGymValue(String.valueOf(kthMdl.getNgySid()));
            kthDspList.add(kthDspMdl);
        }
        paramMdl.setNtp140GyomuList(kthDspList);

        if (StringUtil.isNullZeroString(paramMdl.getNtp140sortGyomu())
                && kthouhouList.size() > 0) {

            Ntp140GyomuDspModel appspMdl = kthDspList.get(0);
            paramMdl.setNtp140sortGyomu(String.valueOf(appspMdl.getGymSid()));
        }

    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Ntp140ParamModel
     * @param userSid ユーザSID
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con,
                           Ntp140ParamModel paramMdl,
                           int userSid,
                           int changeKbn)
                           throws SQLException {

        SortResult<NtpGyomuModel> result = null;
        final NtpGyomuDao dao = new NtpGyomuDao(con);
        final NtpGyomuSortDao sortDao = new NtpGyomuSortDao(con);

        //ラジオ選択値取得
        String selectedKey = paramMdl.getNtp140sortGyomu();
        if (StringUtil.isNullZeroString(selectedKey) || !ValidateUtil.isNumber(selectedKey)) {
            return;
        }

        //選択された項目の施設グループSID + ソート順
        int motoSid = Integer.parseInt(selectedKey);

        SortChangeBiz<NtpGyomuModel> sortBiz =
                SortChangeBiz.<NtpGyomuModel> builder()
                .setFuncTargetList(() -> {
                    return dao.select();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getNgySid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getNgsSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getNgySid() == m2.getNgySid()) {
                        return 0;
                    } else {
                        return (m1.getNgySid() - m2.getNgySid()) 
                                / Math.abs(m1.getNgySid() - m2.getNgySid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    sortDao.updateSort(m.getNgySid(), newSort);
                })
                .build();
        
        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        
        if (result != null) {
            String newSortRadio = String.valueOf(result.getMdl().getNgySid());
            paramMdl.setNtp140sortGyomu(newSortRadio);
        }
    }
}
