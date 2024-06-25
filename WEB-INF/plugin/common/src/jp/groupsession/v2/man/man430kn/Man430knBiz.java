package jp.groupsession.v2.man.man430kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnExtPageDao;
import jp.groupsession.v2.cmn.dao.base.CmnPermittedDomainDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnExtPageModel;
import jp.groupsession.v2.cmn.model.base.CmnPermittedDomainModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能]ユーザ連携自動インポート機能設定画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man430knBiz {



    /**
     *
     * <br>[機  能] 入力された外部ドメインを改行ごとに区切り、パラメータモデルへセットします。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     */
    public void setCmnExtDomain(Man430knParamModel paramMdl,
            Connection con) {

        //指定された外部ドメインを改行ごとに区切る
        String extDomainArea = paramMdl.getMan430ExtDomainArea();
        String[] extDomainText = null;
        if (extDomainArea != null && !StringUtil.isNullZeroStringSpace(extDomainArea)) {
            extDomainText = extDomainArea.split("\n");
            //余分なスペースの除去
            for (int i = 0; i < extDomainText.length; i++) {
                extDomainText[i] = extDomainText[i].trim();
            }

            paramMdl.setMan430knPermittedDomains(extDomainText);
        }
    }

    /**
     *
     * <br>[機  能] ページの表示を許可する外部ドメインを登録します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @param reqMdl リクエストモデル
     * @throws SQLException SQL実行例外
     */
    public void insertDomains(Connection con,
            Man430knParamModel paramMdl,
            RequestModel reqMdl) throws SQLException {

        //セッションユーザID取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();
        //現在の日時を取得
        UDate now = new UDate();

        con.setAutoCommit(true);

        //外部ページ表示制限の有無を登録
        CmnExtPageDao cepDao = new CmnExtPageDao(con);
        CmnExtPageModel cepMdl = new CmnExtPageModel();
        cepMdl.setCepLimitDsp(paramMdl.getMan430ExtPageDspKbn());
        cepMdl.setCepAuid(sessionUsrSid);
        cepMdl.setCepAdate(now);
        cepMdl.setCepEuid(sessionUsrSid);
        cepMdl.setCepEdate(now);

        boolean commit = false;
        try {
            //データがない場合は新規登録
            if (cepDao.update(cepMdl) <= 0) {
                cepDao.insert(cepMdl);
            }

            //外部ドメイン登録
            String[] domains = paramMdl.getMan430knPermittedDomains();
            CmnPermittedDomainDao cpdDao = new CmnPermittedDomainDao(con);

            //登録済みのデータを削除
            cpdDao.delete();

            if (domains.length > 0) {
                CmnPermittedDomainModel cpdMdl = new CmnPermittedDomainModel();
                cpdMdl.setCpdAuid(sessionUsrSid);
                cpdMdl.setCpdAdate(now);
                cpdMdl.setCpdEuid(sessionUsrSid);
                cpdMdl.setCpdEdate(now);
                //登録する外部ドメインの数だけデータを登録する
                for (int i = 0; i < domains.length; i++) {
                    cpdMdl.setCpdExtDomain(StringUtilHtml.deleteHtmlTag(domains[i]));
                    cpdDao.insert(cpdMdl);
                }
            }
            con.commit();
            commit = true;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con);
            }
        }

        con.setAutoCommit(false);
    }

    /**
    *
    * <br>[機  能]オペレーションログに表示する本文を作成する
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl パラメータモデル
    * @param gsMsg メッセージリソース
    * @return ログ本文
    */
   public String setLogValue(Man430knParamModel paramMdl, GsMessage gsMsg) {

       StringBuilder logValue = new StringBuilder();
       logValue.append("[" + gsMsg.getMessage("main.man430.2") + "] ");

       if (paramMdl.getMan430ExtPageDspKbn() == GSConstMain.DSP_EXT_PAGE_NO_LIMIT) {
           logValue.append(gsMsg.getMessage("cmn.not.limit"));
       } else if (paramMdl.getMan430ExtPageDspKbn() == GSConstMain.DSP_EXT_PAGE_LIMITED) {
           logValue.append(gsMsg.getMessage("cmn.do.limit"));
           //ページの表示を許可する外部ドメイン
           logValue.append("\r\n" + "[" + gsMsg.getMessage("main.man430.12") + "]");
           if (paramMdl.getMan430knPermittedDomains().length != 0) {
               String[] extdomains = paramMdl.getMan430knPermittedDomains();
               for (int i = 0; i < extdomains.length; i++) {
                   logValue.append("\r\n");
                   logValue.append(gsMsg.getMessage("wml.231"));
                   logValue.append(extdomains[i]);
               }
           } else {
               logValue.append("\r\n");
               logValue.append(gsMsg.getMessage("wml.231"));
               logValue.append(gsMsg.getMessage("main.man430.13"));
           }
       }

       return logValue.toString();
   }

}


