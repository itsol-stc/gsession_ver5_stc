package jp.groupsession.v2.ntp.ntp170;

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
import jp.groupsession.v2.ntp.dao.NtpkatudouBunruiSortDao;
import jp.groupsession.v2.ntp.model.NtpKtbunruiModel;

/**
 * <br>[機  能] 日報 活動分類一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp170Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp170Biz.class);
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Ntp170Biz(RequestModel reqMdl) {
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
            Ntp170ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {
//        //活動分類一覧の取得・設定
//        Ntp170Dao bunruiDao = new Ntp170Dao(con);
//        paramMdl.setNtp170KtbunruiList(bunruiDao.getKtBunruiList());


        //活動分類情報を取得する
        Ntp170Dao bunruiDao = new Ntp170Dao(con);
        List<NtpKtbunruiModel> ktbunruiList = bunruiDao.getKtBunruiList();
        List<Ntp170KtBunruiDspModel> ktbDspList = new ArrayList<Ntp170KtBunruiDspModel>();
        Ntp170KtBunruiDspModel ktbDspMdl = null;

        for (NtpKtbunruiModel ktbMdl : ktbunruiList) {
            ktbDspMdl = new Ntp170KtBunruiDspModel();

            ktbDspMdl.setKtbSid(ktbMdl.getNkbSid());
            ktbDspMdl.setKtbCode(ktbMdl.getNkbCode());
            ktbDspMdl.setKtbName(ktbMdl.getNkbName());
            ktbDspMdl.setKtbSort(ktbMdl.getNksSort());
            ktbDspMdl.setKtbValue(String.valueOf(ktbMdl.getNkbSid()));
            ktbDspList.add(ktbDspMdl);
        }
        paramMdl.setNtp170KtbunruiList(ktbDspList);

        //チェックされている並び順がNULLの場合、初期値設定
        if (StringUtil.isNullZeroString(paramMdl.getNtp170sortBunrui())
            && ktbunruiList.size() > 0) {

            Ntp170KtBunruiDspModel addspMdl = ktbDspList.get(0);
            paramMdl.setNtp170sortBunrui(String.valueOf(addspMdl.getKtbSid()));
        }

    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Ntp170ParamModel
     * @param userSid ユーザSID
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Ntp170ParamModel paramMdl, int userSid, int changeKbn)
        throws SQLException {

        SortResult<NtpKtbunruiModel> result = null;
        final Ntp170Dao dao = new Ntp170Dao(con);
        final NtpkatudouBunruiSortDao nbsDao = new NtpkatudouBunruiSortDao(con);

        //ラジオ選択値取得
        String selectedKey = paramMdl.getNtp170sortBunrui();
        if (StringUtil.isNullZeroString(selectedKey) || !ValidateUtil.isNumber(selectedKey)) {
            return;
        }

        //選択された項目の施設グループSID + ソート順
        int motoSid = Integer.parseInt(selectedKey);

        SortChangeBiz<NtpKtbunruiModel> sortBiz =
                SortChangeBiz.<NtpKtbunruiModel> builder()
                .setFuncTargetList(() -> {
                    return dao.getKtBunruiList();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getNkbSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getNksSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getNkbSid() == m2.getNkbSid()) {
                        return 0;
                    } else {
                        return (m1.getNkbSid() - m2.getNkbSid()) 
                                / Math.abs(m1.getNkbSid() - m2.getNkbSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    nbsDao.updateSort(m.getNkbSid(), newSort);
                })
                .build();
        
        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        
        if (result != null) {
            String newSortRadio = String.valueOf(result.getMdl().getNkbSid());
            paramMdl.setNtp170sortBunrui(newSortRadio);
        }
    }
}
