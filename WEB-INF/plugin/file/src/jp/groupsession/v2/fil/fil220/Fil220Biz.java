package jp.groupsession.v2.fil.fil220;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.model.FileCabinetModel;

/**
 * <br>[機  能] 管理者設定 キャビネット管理設定画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil220Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil220Biz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Fil220Biz(Connection con) {
        con__ = con;
    }
    
    /**
     * <br>[機  能] 初期表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil220ParamModel
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Fil220ParamModel paramMdl) throws SQLException {

        log__.debug("fil020Biz Start");

        FileCabinetDao cabiDao = new FileCabinetDao(con__);
        int personalFlg = paramMdl.getFil220cabinetKbn();
        List<FileCabinetModel> cabiList
                    = cabiDao.selectPersonalCabinet(personalFlg);

        paramMdl.setFil220cabinetList(cabiList);


    }
    
    /**
     * <br>[機  能] 表示順を変更する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Fil220ParamModel
     * @param buMdl BaseUserModel
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行例外
     */
    public void updateSort(
            Fil220ParamModel paramMdl, BaseUserModel buMdl, int changeKbn) throws SQLException {
        
        final FileCabinetDao dao = new FileCabinetDao(con__);

        //ラジオ選択値取得
        String selectSid = paramMdl.getFil220sltRadio();
        if (StringUtil.isNullZeroString(selectSid) || !ValidateUtil.isNumber(selectSid)) {
            return;
        }
        int motoSid = Integer.parseInt(selectSid);
        int cabinetKbn = paramMdl.getFil220cabinetKbn();
        
        SortChangeBiz<FileCabinetModel> sortBiz =
                SortChangeBiz.<FileCabinetModel> builder()
                .setFuncTargetList(() -> {
                    return dao.selectPersonalCabinet(cabinetKbn);
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getFcbSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getFcbSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getFcbSid() == m2.getFcbSid()) {
                        return 0;
                    } else {
                        return (m1.getFcbSid() - m2.getFcbSid()) 
                                / Math.abs(m1.getFcbSid() - m2.getFcbSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    m.setFcbSort(newSort);
                    dao.updateSort(m);
                })
                .build();
        
        if (changeKbn == GSConst.SORT_UP) {
            sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            sortBiz.down();
        }
    }
}
