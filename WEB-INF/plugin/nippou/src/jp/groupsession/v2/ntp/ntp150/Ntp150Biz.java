package jp.groupsession.v2.ntp.ntp150;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.dao.NtpGyomuDao;
import jp.groupsession.v2.ntp.dao.NtpProcessSortDao;
import jp.groupsession.v2.ntp.model.NtpGyomuModel;
import jp.groupsession.v2.ntp.model.NtpProcessModel;

/**
 * <br>[機  能] 日報 プロセス一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp150Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp150Biz.class);
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Ntp150Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp150ParamModel
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Ntp150ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        //業務コンボ設定
        paramMdl.setNtp150GyomuList(getGyomuList(con));

        //活動分類情報を取得する
        Ntp150ProcessDao processDao = new Ntp150ProcessDao(con);
        List<NtpProcessModel> processList;
        if (paramMdl.getNtp150DispNgySid().equals("all")
                || paramMdl.getNtp150DispNgySid().equals("-1")) {
            paramMdl.setNtp150DispNgySid("all");
            processList = processDao.select();
        } else {
            processList = processDao.select(Integer.valueOf(paramMdl.getNtp150DispNgySid()));
        }
        List<Ntp150ProcessModel> proDspList = new ArrayList<Ntp150ProcessModel>();
        Ntp150ProcessModel proDspMdl = null;

        for (NtpProcessModel proMdl : processList) {
            proDspMdl = new Ntp150ProcessModel();

            proDspMdl.setNgpSid(proMdl.getNgpSid());
            proDspMdl.setNgySid(proMdl.getNgySid());
            proDspMdl.setNgpCode(proMdl.getNgpCode());
            proDspMdl.setNgpName(proMdl.getNgpName());
            proDspMdl.setNgyName(proMdl.getNgyName());
            proDspMdl.setNgpSort(proMdl.getNgpSort());
            proDspMdl.setNgpValue(String.valueOf(proMdl.getNgpSid()));
            proDspList.add(proDspMdl);
        }
        paramMdl.setNtp150ProcessList(proDspList);

        //チェックされている並び順がNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getNtp150SortProcess())
            && processList.size() > 0) {

            Ntp150ProcessModel addspMdl = proDspList.get(0);
            paramMdl.setNtp150SortProcess(String.valueOf(addspMdl.getNgpSid()));
        }
    }

    /**
     * <br>[機  能] 業務一覧リストを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @return 業務一覧リスト
     */
    public List<LabelValueBean> getGyomuList(Connection con) throws SQLException {

        List<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("全件", "all"));

        // 業務一覧を取得
        NtpGyomuDao gyomuDao = new NtpGyomuDao(con);
        List<NtpGyomuModel> gyomuMdl = gyomuDao.select();

        for (NtpGyomuModel mdl : gyomuMdl) {
            labelList.add(
                    new LabelValueBean(mdl.getNgyName(), String.valueOf(mdl.getNgySid())));
        }
        return labelList;
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Ntp150ParamModel
     * @param userSid ユーザSID
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Ntp150ParamModel paramMdl, int userSid, int changeKbn)
        throws SQLException {

        SortResult<NtpProcessModel> result = null;
        final Ntp150ProcessDao dao = new Ntp150ProcessDao(con);
        final NtpProcessSortDao npsDao = new NtpProcessSortDao(con);

        //ラジオ選択値取得
        String selectedKey = paramMdl.getNtp150SortProcess();
        if (StringUtil.isNullZeroString(selectedKey) || !ValidateUtil.isNumber(selectedKey)) {
            return;
        }

        //選択された項目の施設グループSID + ソート順
        int motoSid = Integer.parseInt(selectedKey);
        
        if (paramMdl.getNtp150DispNgySid().equals("all")
                || paramMdl.getNtp150DispNgySid().equals("-1")) {
            paramMdl.setNtp150DispNgySid("all");
        }

        SortChangeBiz<NtpProcessModel> sortBiz =
                SortChangeBiz.<NtpProcessModel> builder()
                .setFuncTargetList(() -> {
                    if (paramMdl.getNtp150DispNgySid().equals("all")) {
                        return dao.select();
                    } else {
                        return dao.select(Integer.valueOf(paramMdl.getNtp150DispNgySid()));
                    }
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getNgpSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getNpsSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getNgpSid() == m2.getNgpSid()) {
                        return 0;
                    } else {
                        return (m1.getNgpSid() - m2.getNgpSid()) 
                                / Math.abs(m1.getNgpSid() - m2.getNgpSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    npsDao.updateSort(m.getNgySid(), m.getNgpSid(), newSort);
                })
                .build();
        
        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        
        if (result != null) {
            String newSortRadio = String.valueOf(result.getMdl().getNgpSid());
            paramMdl.setNtp150SortProcess(newSortRadio);
        }
    }
}
