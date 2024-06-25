package jp.groupsession.v2.ntp.ntp190;

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
import jp.groupsession.v2.ntp.dao.NtpContactSortDao;
import jp.groupsession.v2.ntp.model.NtpContactModel;

/**
 * <br>[機  能] 日報 顧客源泉一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp190Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp190Biz.class);
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Ntp190Biz(RequestModel reqMdl) {
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
            Ntp190ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

//        //コンタクト一覧の取得・設定
//        NtpContactDao contactDao = new NtpContactDao(con);
//        paramMdl.setNtp190ContactList((ArrayList)contactDao.select());

        //活動方法情報を取得する
        Ntp190Dao contactDao = new Ntp190Dao(con);
        List<NtpContactModel> contactList = contactDao.getKthouhouList();
        List<Ntp190ContactDspModel> contDspList = new ArrayList<Ntp190ContactDspModel>();
        Ntp190ContactDspModel contDspMdl = null;

        for (NtpContactModel kthMdl : contactList) {
            contDspMdl = new Ntp190ContactDspModel();

            contDspMdl.setContSid(kthMdl.getNcnSid());
            contDspMdl.setContCode(kthMdl.getNcnCode());
            contDspMdl.setContName(kthMdl.getNcnName());
            contDspMdl.setContSort(kthMdl.getNcsSort());
            contDspMdl.setContValue(String.valueOf(kthMdl.getNcnSid()));
            contDspList.add(contDspMdl);
        }
        paramMdl.setNtp190ContactList(contDspList);

        if (StringUtil.isNullZeroString(paramMdl.getNtp190sortContact())
                && contactList.size() > 0) {

            Ntp190ContactDspModel appspMdl = contDspList.get(0);
            paramMdl.setNtp190sortContact(String.valueOf(appspMdl.getContSid()));
        }
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl フォーム
     * @param userSid ユーザSID
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Ntp190ParamModel paramMdl, int userSid, int changeKbn)
            throws SQLException {

        SortResult<NtpContactModel> result = null;
        final Ntp190Dao dao = new Ntp190Dao(con);
        final NtpContactSortDao ncsDao = new NtpContactSortDao(con);

        //ラジオ選択値取得
        String selectedKey = paramMdl.getNtp190sortContact();
        if (StringUtil.isNullZeroString(selectedKey) || !ValidateUtil.isNumber(selectedKey)) {
            return;
        }

        //選択された項目の施設グループSID + ソート順
        int motoSid = Integer.parseInt(selectedKey);

        SortChangeBiz<NtpContactModel> sortBiz =
                SortChangeBiz.<NtpContactModel> builder()
                .setFuncTargetList(() -> {
                    return dao.getKthouhouList();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getNcnSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getNcsSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getNcnSid() == m2.getNcnSid()) {
                        return 0;
                    } else {
                        return (m1.getNcnSid() - m2.getNcnSid()) 
                                / Math.abs(m1.getNcnSid() - m2.getNcnSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    ncsDao.updateSort(m.getNcnSid(), newSort);
                })
                .build();
        
        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        
        if (result != null) {
            String newSortRadio = String.valueOf(result.getMdl().getNcnSid());
            paramMdl.setNtp190sortContact(newSortRadio);
        }
    }
}
