package jp.groupsession.v2.tcd.tcd220;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdYukyuAlertDao;
import jp.groupsession.v2.tcd.model.TcdYukyuAlertModel;

/**
 * <br>[機  能] タイムカード 有休警告設定画面のビジネスロジッククラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd220Biz {

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    protected void _setInitData(
            Connection con, Tcd220ParamModel paramMdl) throws SQLException {
        
        TcdYukyuAlertDao tyaDao = new TcdYukyuAlertDao(con);
        List<TcdYukyuAlertModel> tyaMdlList = tyaDao.getYukyuAlertList();
        int count = 1;
        for (TcdYukyuAlertModel tyaMdl : tyaMdlList) {
            switch (count) {
                case 1:
                    paramMdl.setTcd220Days1(String.valueOf(tyaMdl.getTyaBasedays()));
                    paramMdl.setTcd220DispMonth1(String.valueOf(tyaMdl.getTyaMonth()));
                    paramMdl.setTcd220Message1(tyaMdl.getTyaMessage());
                    paramMdl.setTcd220Row1(tyaMdl.getTyaNo());
                    break;
                case 2:
                    paramMdl.setTcd220Days2(String.valueOf(tyaMdl.getTyaBasedays()));
                    paramMdl.setTcd220DispMonth2(String.valueOf(tyaMdl.getTyaMonth()));
                    paramMdl.setTcd220Message2(tyaMdl.getTyaMessage());
                    paramMdl.setTcd220Row2(tyaMdl.getTyaNo());
                    break;
                case 3:
                    paramMdl.setTcd220Days3(String.valueOf(tyaMdl.getTyaBasedays()));
                    paramMdl.setTcd220DispMonth3(String.valueOf(tyaMdl.getTyaMonth()));
                    paramMdl.setTcd220Message3(tyaMdl.getTyaMessage());
                    paramMdl.setTcd220Row3(tyaMdl.getTyaNo());
                    break;
                case 4:
                    paramMdl.setTcd220Days4(String.valueOf(tyaMdl.getTyaBasedays()));
                    paramMdl.setTcd220DispMonth4(String.valueOf(tyaMdl.getTyaMonth()));
                    paramMdl.setTcd220Message4(tyaMdl.getTyaMessage());
                    paramMdl.setTcd220Row4(tyaMdl.getTyaNo());
                    break;
                case 5:
                    paramMdl.setTcd220Days5(String.valueOf(tyaMdl.getTyaBasedays()));
                    paramMdl.setTcd220DispMonth5(String.valueOf(tyaMdl.getTyaMonth()));
                    paramMdl.setTcd220Message5(tyaMdl.getTyaMessage());
                    paramMdl.setTcd220Row5(tyaMdl.getTyaNo());
                    break;
                case 6:
                    paramMdl.setTcd220Days6(String.valueOf(tyaMdl.getTyaBasedays()));
                    paramMdl.setTcd220DispMonth6(String.valueOf(tyaMdl.getTyaMonth()));
                    paramMdl.setTcd220Message6(tyaMdl.getTyaMessage());
                    paramMdl.setTcd220Row6(tyaMdl.getTyaNo());
                    break;
                case 7:
                    paramMdl.setTcd220Days7(String.valueOf(tyaMdl.getTyaBasedays()));
                    paramMdl.setTcd220DispMonth7(String.valueOf(tyaMdl.getTyaMonth()));
                    paramMdl.setTcd220Message7(tyaMdl.getTyaMessage());
                    paramMdl.setTcd220Row7(tyaMdl.getTyaNo());
                    break;
                case 8:
                    paramMdl.setTcd220Days8(String.valueOf(tyaMdl.getTyaBasedays()));
                    paramMdl.setTcd220DispMonth8(String.valueOf(tyaMdl.getTyaMonth()));
                    paramMdl.setTcd220Message8(tyaMdl.getTyaMessage());
                    paramMdl.setTcd220Row8(tyaMdl.getTyaNo());
                    break;
                case 9:
                    paramMdl.setTcd220Days9(String.valueOf(tyaMdl.getTyaBasedays()));
                    paramMdl.setTcd220DispMonth9(String.valueOf(tyaMdl.getTyaMonth()));
                    paramMdl.setTcd220Message9(tyaMdl.getTyaMessage());
                    paramMdl.setTcd220Row9(tyaMdl.getTyaNo());
                    break;
                case 10:
                    paramMdl.setTcd220Days10(String.valueOf(tyaMdl.getTyaBasedays()));
                    paramMdl.setTcd220DispMonth10(String.valueOf(tyaMdl.getTyaMonth()));
                    paramMdl.setTcd220Message10(tyaMdl.getTyaMessage());
                    paramMdl.setTcd220Row10(tyaMdl.getTyaNo());
                    break;
                case 11:
                    paramMdl.setTcd220Days11(String.valueOf(tyaMdl.getTyaBasedays()));
                    paramMdl.setTcd220DispMonth11(String.valueOf(tyaMdl.getTyaMonth()));
                    paramMdl.setTcd220Message11(tyaMdl.getTyaMessage());
                    paramMdl.setTcd220Row11(tyaMdl.getTyaNo());
                    break;
                case 12:
                    paramMdl.setTcd220Days12(String.valueOf(tyaMdl.getTyaBasedays()));
                    paramMdl.setTcd220DispMonth12(String.valueOf(tyaMdl.getTyaMonth()));
                    paramMdl.setTcd220Message12(tyaMdl.getTyaMessage());
                    paramMdl.setTcd220Row12(tyaMdl.getTyaNo());
                    break;
                case 13:
                    paramMdl.setTcd220Days13(String.valueOf(tyaMdl.getTyaBasedays()));
                    paramMdl.setTcd220DispMonth13(String.valueOf(tyaMdl.getTyaMonth()));
                    paramMdl.setTcd220Message13(tyaMdl.getTyaMessage());
                    paramMdl.setTcd220Row13(tyaMdl.getTyaNo());
                    break;
                case 14:
                    paramMdl.setTcd220Days14(String.valueOf(tyaMdl.getTyaBasedays()));
                    paramMdl.setTcd220DispMonth14(String.valueOf(tyaMdl.getTyaMonth()));
                    paramMdl.setTcd220Message14(tyaMdl.getTyaMessage());
                    paramMdl.setTcd220Row14(tyaMdl.getTyaNo());
                    break;
                case 15:
                    paramMdl.setTcd220Days15(String.valueOf(tyaMdl.getTyaBasedays()));
                    paramMdl.setTcd220DispMonth15(String.valueOf(tyaMdl.getTyaMonth()));
                    paramMdl.setTcd220Message15(tyaMdl.getTyaMessage());
                    paramMdl.setTcd220Row15(tyaMdl.getTyaNo());
                    break;
            }
            count++;
        }
        paramMdl.setTcd220MonthLabelList(_getMonthLabel());
    }
    
    /**
     * <br>[機  能] 有休警告設定の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param map アクションマッピング
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    protected void _doInsert(
            Connection con,
            Tcd220ParamModel paramMdl,
            ActionMapping map,
            RequestModel reqMdl) throws SQLException {
        
        TcdYukyuAlertDao tyaDao = new TcdYukyuAlertDao(con);
        List<TcdYukyuAlertModel> tyaMdlList = __getYukyuAlertList(paramMdl);
        
        boolean commitFlg = false;
        GsMessage gsMsg = new GsMessage(reqMdl);
        //オペレーションログに出力する内容
        String value = "";
        try {
            tyaDao.delete();
            for (TcdYukyuAlertModel tyaMdl : tyaMdlList) {
                tyaDao.insert(tyaMdl);
                StringBuilder buff = new StringBuilder();
                buff.append("[" + gsMsg.getMessage("tcd.tcd220.03") +"]");
                buff.append(tyaMdl.getTyaMonth());
                buff.append(" [" + gsMsg.getMessage("tcd.137") +"]");
                buff.append(tyaMdl.getTyaBasedays());
                buff.append(" [" + gsMsg.getMessage("cmn.warning") + gsMsg.getMessage("cmn.message"));
                buff.append(tyaMdl.getTyaMessage() + "]\r\n");
                value += buff.toString();
            }
            con.commit();
            commitFlg = true;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con);
            }
        }
        
        //ログの出力
        TimecardBiz tcdBiz = new TimecardBiz();
        //変更
        String opCode = gsMsg.getMessage("cmn.edit");
        tcdBiz.outPutTimecardLog(map, reqMdl, con, opCode, GSConstLog.LEVEL_INFO, value);
    }
    
    /**
     * <br>[機  能] 月コンボを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @return ArrayList (in LabelValueBean)  月コンボ
     */
    protected ArrayList<LabelValueBean> _getMonthLabel() {
        int month = 1;
        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(new LabelValueBean("", ""));
        for (int i = 0; i < 12; i++) {
            String strMonth = String.valueOf(month);
            labelList.add(
                    new LabelValueBean(strMonth, strMonth));
            month++;
        }
        return labelList;
    }
    
    /**
     * <br>[機  能] パラメータ情報から有休警告情報モデルのリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return 有休警告情報モデルのリスト
     */
    private List<TcdYukyuAlertModel> __getYukyuAlertList(Tcd220ParamModel paramMdl) {
        List<TcdYukyuAlertModel> ret = new ArrayList<TcdYukyuAlertModel>();
        
        int rowCount = 1;
        //入力値チェック
        for (int idx = 0; idx < 15; idx++) {
            String message = null;
            String month = null;
            String days = null;
            switch (idx) {
                case 0:
                    if ((paramMdl.getTcd220Days1().equals("") || paramMdl.getTcd220Days1().equals("undefined"))
                            && (paramMdl.getTcd220DispMonth1().equals("") || paramMdl.getTcd220DispMonth1().equals("undefined"))
                            && (paramMdl.getTcd220Message1().equals("") || paramMdl.getTcd220Message1().equals("undefined"))) {
                        continue; 
                    }
                    message = paramMdl.getTcd220Message1();
                    month = paramMdl.getTcd220DispMonth1();
                    days = paramMdl.getTcd220Days1();
                    break;
                case 1:
                    if ((paramMdl.getTcd220Days2().equals("") || paramMdl.getTcd220Days2().equals("undefined"))
                            && (paramMdl.getTcd220DispMonth2().equals("") || paramMdl.getTcd220DispMonth2().equals("undefined"))
                            && (paramMdl.getTcd220Message2().equals("") || paramMdl.getTcd220Message2().equals("undefined"))) {
                        continue; 
                    }
                    message = paramMdl.getTcd220Message2();
                    month = paramMdl.getTcd220DispMonth2();
                    days = paramMdl.getTcd220Days2();
                    break;
                case 2:
                    if ((paramMdl.getTcd220Days3().equals("") || paramMdl.getTcd220Days3().equals("undefined"))
                            && (paramMdl.getTcd220DispMonth3().equals("") || paramMdl.getTcd220DispMonth3().equals("undefined"))
                            && (paramMdl.getTcd220Message3().equals("") || paramMdl.getTcd220Message3().equals("undefined"))) {
                        continue; 
                    }
                    message = paramMdl.getTcd220Message3();
                    month = paramMdl.getTcd220DispMonth3();
                    days = paramMdl.getTcd220Days3();
                    break;
                case 3:
                    if ((paramMdl.getTcd220Days4().equals("") || paramMdl.getTcd220Days4().equals("undefined"))
                            && (paramMdl.getTcd220DispMonth4().equals("") || paramMdl.getTcd220DispMonth4().equals("undefined"))
                            && (paramMdl.getTcd220Message4().equals("") || paramMdl.getTcd220Message4().equals("undefined"))) {
                        continue; 
                    }
                    message = paramMdl.getTcd220Message4();
                    month = paramMdl.getTcd220DispMonth4();
                    days = paramMdl.getTcd220Days4();
                    break;
                case 4:
                    if ((paramMdl.getTcd220Days5().equals("") || paramMdl.getTcd220Days5().equals("undefined"))
                            && (paramMdl.getTcd220DispMonth5().equals("") || paramMdl.getTcd220DispMonth5().equals("undefined"))
                            && (paramMdl.getTcd220Message5().equals("") || paramMdl.getTcd220Message5().equals("undefined"))) {
                        continue; 
                    }
                    message = paramMdl.getTcd220Message5();
                    month = paramMdl.getTcd220DispMonth5();
                    days = paramMdl.getTcd220Days5();
                    break;
                case 5:
                    if ((paramMdl.getTcd220Days6().equals("") || paramMdl.getTcd220Days6().equals("undefined"))
                            && (paramMdl.getTcd220DispMonth6().equals("") || paramMdl.getTcd220DispMonth6().equals("undefined"))
                            && (paramMdl.getTcd220Message6().equals("") || paramMdl.getTcd220Message6().equals("undefined"))) {
                        continue; 
                    }
                    message = paramMdl.getTcd220Message6();
                    month = paramMdl.getTcd220DispMonth6();
                    days = paramMdl.getTcd220Days6();
                    break;
                case 6:
                    if ((paramMdl.getTcd220Days7().equals("") || paramMdl.getTcd220Days7().equals("undefined"))
                            && (paramMdl.getTcd220DispMonth7().equals("") || paramMdl.getTcd220DispMonth7().equals("undefined"))
                            && (paramMdl.getTcd220Message7().equals("") || paramMdl.getTcd220Message7().equals("undefined"))) {
                        continue; 
                    }
                    message = paramMdl.getTcd220Message7();
                    month = paramMdl.getTcd220DispMonth7();
                    days = paramMdl.getTcd220Days7();
                    break;
                case 7:
                    if ((paramMdl.getTcd220Days8().equals("") || paramMdl.getTcd220Days8().equals("undefined"))
                            && (paramMdl.getTcd220DispMonth8().equals("") || paramMdl.getTcd220DispMonth8().equals("undefined"))
                            && (paramMdl.getTcd220Message8().equals("") || paramMdl.getTcd220Message8().equals("undefined"))) {
                        continue; 
                    }
                    message = paramMdl.getTcd220Message8();
                    month = paramMdl.getTcd220DispMonth8();
                    days = paramMdl.getTcd220Days8();
                    break;
                case 8:
                    if ((paramMdl.getTcd220Days9().equals("") || paramMdl.getTcd220Days9().equals("undefined"))
                            && (paramMdl.getTcd220DispMonth9().equals("") || paramMdl.getTcd220DispMonth9().equals("undefined"))
                            && (paramMdl.getTcd220Message9().equals("") || paramMdl.getTcd220Message9().equals("undefined"))) {
                        continue; 
                    }
                    message = paramMdl.getTcd220Message9();
                    month = paramMdl.getTcd220DispMonth9();
                    days = paramMdl.getTcd220Days9();
                    break;
                case 9:
                    if ((paramMdl.getTcd220Days10().equals("") || paramMdl.getTcd220Days10().equals("undefined"))
                            && (paramMdl.getTcd220DispMonth10().equals("") || paramMdl.getTcd220DispMonth10().equals("undefined"))
                            && (paramMdl.getTcd220Message10().equals("") || paramMdl.getTcd220Message10().equals("undefined"))) {
                        continue; 
                    }
                    message = paramMdl.getTcd220Message10();
                    month = paramMdl.getTcd220DispMonth10();
                    days = paramMdl.getTcd220Days10();
                    break;
                case 10:
                    if ((paramMdl.getTcd220Days11().equals("") || paramMdl.getTcd220Days11().equals("undefined"))
                            && (paramMdl.getTcd220DispMonth11().equals("") || paramMdl.getTcd220DispMonth11().equals("undefined"))
                            && (paramMdl.getTcd220Message11().equals("") || paramMdl.getTcd220Message11().equals("undefined"))) {
                        continue; 
                    }
                    message = paramMdl.getTcd220Message11();
                    month = paramMdl.getTcd220DispMonth11();
                    days = paramMdl.getTcd220Days11();
                    break;
                case 11:
                    if ((paramMdl.getTcd220Days12().equals("") || paramMdl.getTcd220Days12().equals("undefined"))
                            && (paramMdl.getTcd220DispMonth12().equals("") || paramMdl.getTcd220DispMonth12().equals("undefined"))
                            && (paramMdl.getTcd220Message12().equals("") || paramMdl.getTcd220Message12().equals("undefined"))) {
                        continue; 
                    }
                    message = paramMdl.getTcd220Message12();
                    month = paramMdl.getTcd220DispMonth12();
                    days = paramMdl.getTcd220Days12();
                    break;
                case 12:
                    if ((paramMdl.getTcd220Days13().equals("") || paramMdl.getTcd220Days13().equals("undefined"))
                            && (paramMdl.getTcd220DispMonth13().equals("") || paramMdl.getTcd220DispMonth13().equals("undefined"))
                            && (paramMdl.getTcd220Message13().equals("") || paramMdl.getTcd220Message13().equals("undefined"))) {

                        continue; 
                    }
                    message = paramMdl.getTcd220Message13();
                    month = paramMdl.getTcd220DispMonth13();
                    days = paramMdl.getTcd220Days13();
                    break;
                case 13:
                    if ((paramMdl.getTcd220Days14().equals("") || paramMdl.getTcd220Days14().equals("undefined"))
                            && (paramMdl.getTcd220DispMonth14().equals("") || paramMdl.getTcd220DispMonth14().equals("undefined"))
                            && (paramMdl.getTcd220Message14().equals("") || paramMdl.getTcd220Message14().equals("undefined"))) {
                            
                        continue; 
                    }
                    message = paramMdl.getTcd220Message14();
                    month = paramMdl.getTcd220DispMonth14();
                    days = paramMdl.getTcd220Days14();
                    break;
                case 14:
                    if ((paramMdl.getTcd220Days15().equals("") || paramMdl.getTcd220Days15().equals("undefined"))
                            && (paramMdl.getTcd220DispMonth15().equals("") || paramMdl.getTcd220DispMonth15().equals("undefined"))
                            && (paramMdl.getTcd220Message15().equals("") || paramMdl.getTcd220Message15().equals("undefined"))) {
                            
                        continue; 
                    }
                    message = paramMdl.getTcd220Message15();
                    month = paramMdl.getTcd220DispMonth15();
                    days = paramMdl.getTcd220Days15();
                    break;
                 default:
            }
            
            TcdYukyuAlertModel tyaMdl = new TcdYukyuAlertModel();
            tyaMdl.setTyaBasedays(Integer.parseInt(days));
            tyaMdl.setTyaMessage(message);
            tyaMdl.setTyaMonth(Integer.parseInt(month));
            tyaMdl.setTyaNo(rowCount);
            ret.add(tyaMdl);
            rowCount += 1;
        }
        
        return ret;
    }
}
