package jp.groupsession.v2.fil.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil100.Fil100SearchParameterModel;
import jp.groupsession.v2.fil.model.FileModel;

/**
 * <br>[機  能] ファイル詳細検索処理DAO
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FileSearchDao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileSearchDao.class);
    /** リクエスト情報 */
    @SuppressWarnings("unused")
    private RequestModel reqMdl__ = null;
    /**
     * <p>デフォルトコンストラクタ
     * @param con DBコネクション
     * @param reqMdl RequestModel
     */
    public FileSearchDao(Connection con, RequestModel reqMdl) {
        super(con);
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] ファイル詳細検索結果を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param prmModel 検索条件Model
     * @param usModel セッションユーザ情報
     * @return List int FileDspModel
     * @throws SQLException SQL実行例外
     */
    public List<FileModel> getSearchData(Fil100SearchParameterModel prmModel,
                                          BaseUserModel usModel)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<FileModel> ret = new ArrayList<FileModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __getCreateSearchSql(prmModel, usModel, con, false);

            sql.setPagingValue(prmModel.getStart() - 1, prmModel.getLimit());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getFileSrchdataFromRs(rs));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] SQLを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param prmModel パラメータモデル
     * @param usModel セッションユーザ情報
     * @param con コネクション
     * @param isCount カウントを取得する場合、true
     * @return sql SQL
     * @throws SQLException SQL実行例外
     */
    private SqlBuffer __getCreateSearchSql(Fil100SearchParameterModel prmModel,
                                        BaseUserModel usModel, Connection con,
                                        boolean isCount)
    throws SQLException {

        CommonBiz  commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstFile.PLUGIN_ID_FILE);
        List<String>  keywordList = prmModel.getKeyWord();

        boolean isTextSearch = false;
        if (prmModel.getSearchFlg() && keywordList != null
                && keywordList.size() > 0 && prmModel.isSrchTargetText()) {
            isTextSearch = true;
        }

        //SQL文
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" select");
        if (isCount) {
            sql.addSql("   count(*) CNT");
        } else {
            sql.addSql("   DIRECTORY.FDR_SID,");
            sql.addSql("   DIRECTORY.FDR_VERSION,");
            sql.addSql("   DIRECTORY.FCB_SID,");
            sql.addSql("   DIRECTORY.FDR_PARENT_SID,");
            sql.addSql("   DIRECTORY.FDR_KBN,");
            sql.addSql("   DIRECTORY.FDR_VER_KBN,");
            sql.addSql("   DIRECTORY.FDR_LEVEL,");
            sql.addSql("   DIRECTORY.FDR_NAME,");
            sql.addSql("   DIRECTORY.FDR_BIKO,");
            sql.addSql("   DIRECTORY.FDR_JTKBN,");
            sql.addSql("   DIRECTORY.FDR_EUID,");
            sql.addSql("   DIRECTORY.FDR_EDATE,");
            sql.addSql("   DIRECTORY.FDR_TRADE_DATE,");
            sql.addSql("   DIRECTORY.FDR_TRADE_TARGET,");
            sql.addSql("   DIRECTORY.FDR_TRADE_MONEY,");
            sql.addSql("   DIRECTORY.FDR_TRADE_MONEYKBN,");
            sql.addSql("   DIRECTORY.EMT_SID as EMT_SID,");
            sql.addSql("   DIRECTORY.FCB_JKBN,");
            sql.addSql("   DIRECTORY.FCB_PERSONAL_FLG,");
            sql.addSql("   DIRECTORY.FCB_OWNER_SID,");
            sql.addSql("   DIRECTORY.BIN_SID,");
            sql.addSql("   DIRECTORY.FFL_EXT,");
            sql.addSql("   DIRECTORY.FFL_FILE_SIZE,");
            sql.addSql("   DIRECTORY.FFL_LOCK_KBN,");
            sql.addSql("   DIRECTORY.FFL_LOCK_USER,");
            sql.addSql("   DIRECTORY.USR_SID,");
            sql.addSql("   DIRECTORY.USI_SEI,");
            sql.addSql("   DIRECTORY.USI_MEI,");
            sql.addSql("   DIRECTORY.USR_JKBN,");
            sql.addSql("   DIRECTORY.USR_UKO_FLG,");
            sql.addSql("   DIRECTORY.FDR_EGID,");
            sql.addSql("   CMN_GROUPM.GRP_NAME,");
            sql.addSql("   CMN_GROUPM.GRP_JKBN,");
            sql.addSql("   FILE_MONEY_MASTER.FMM_NAME as FMM_NAME");
        }
        sql.addSql(" from");
        sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION ");
        sql.addSql("     from FILE_DIRECTORY group by FDR_SID) as DIR_MAXVERSION,");

        sql.addSql("   (");
        sql.addSql("   select");
        sql.addSql("     FILE_DIRECTORY.FDR_SID,");
        sql.addSql("     FILE_DIRECTORY.FDR_VERSION,");
        sql.addSql("     FILE_DIRECTORY.FCB_SID,");
        sql.addSql("     FILE_DIRECTORY.FDR_PARENT_SID,");
        sql.addSql("     FILE_DIRECTORY.FDR_KBN,");
        sql.addSql("     FILE_DIRECTORY.FDR_VER_KBN,");
        sql.addSql("     FILE_DIRECTORY.FDR_LEVEL,");
        sql.addSql("     FILE_DIRECTORY.FDR_NAME,");
        sql.addSql("     FILE_DIRECTORY.FDR_BIKO,");
        sql.addSql("     FILE_DIRECTORY.FDR_JTKBN,");
        sql.addSql("     FILE_DIRECTORY.FDR_EUID,");
        sql.addSql("     FILE_DIRECTORY.FDR_EGID,");
        sql.addSql("     FILE_DIRECTORY.FDR_EDATE,");
        sql.addSql("     FILE_DIRECTORY.FDR_ACCESS_SID,");
        sql.addSql("     FILE_DIRECTORY.FDR_TRADE_DATE,");
        sql.addSql("     FILE_DIRECTORY.FDR_TRADE_TARGET,");
        sql.addSql("     FILE_DIRECTORY.FDR_TRADE_MONEY,");
        sql.addSql("     FILE_DIRECTORY.FDR_TRADE_MONEYKBN,");
        sql.addSql("     FILE_DIRECTORY.EMT_SID,");

        if (isCount) {
            //sql.addSql("   FILE_DIRECTORY DIRECTORY");
            sql.addSql("     FILE_CABINET.FCB_PERSONAL_FLG,");
            sql.addSql("     FILE_CABINET.FCB_ERRL,");
            sql.addSql("     FILE_CABINET.FCB_JKBN,");
            sql.addSql("     FILE_CABINET.FCB_OWNER_SID");
            sql.addSql("   from");
            sql.addSql("     FILE_DIRECTORY left join FILE_CABINET");
            sql.addSql("   on FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql("  ) as DIRECTORY ");
        } else {
            sql.addSql("     FILE_CABINET.FCB_PERSONAL_FLG,");
            sql.addSql("     FILE_CABINET.FCB_ERRL,");
            sql.addSql("     FILE_CABINET.FCB_JKBN,");
            sql.addSql("     FILE_CABINET.FCB_OWNER_SID,");
            sql.addSql("     FILE_FILE_BIN.BIN_SID,");
            sql.addSql("     FILE_FILE_BIN.FFL_EXT,");
            sql.addSql("     FILE_FILE_BIN.FFL_FILE_SIZE,");
            sql.addSql("     FILE_FILE_BIN.FFL_LOCK_KBN,");
            sql.addSql("     FILE_FILE_BIN.FFL_LOCK_USER,");
            sql.addSql("     FILE_CALL_CONF.USR_SID,");
            sql.addSql("     CMN_USRM_INF.USI_SEI,");
            sql.addSql("     CMN_USRM_INF.USI_MEI,");
            sql.addSql("     CMN_USRM.USR_UKO_FLG,");
            sql.addSql("     CMN_USRM.USR_JKBN");
            sql.addSql("   from");
            sql.addSql("    (((((FILE_DIRECTORY left join FILE_FILE_BIN");
            sql.addSql("       on FILE_DIRECTORY.FDR_SID = FILE_FILE_BIN.FDR_SID ");
            sql.addSql("       and FILE_DIRECTORY.FDR_VERSION = FILE_FILE_BIN.FDR_VERSION)");
            sql.addSql(" left join FILE_CABINET");
            sql.addSql("       on FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID)");
            sql.addSql(" left join FILE_CALL_CONF");
            sql.addSql("       on FILE_DIRECTORY.FDR_SID = FILE_CALL_CONF.FDR_SID");
            sql.addSql("       and FILE_CALL_CONF.USR_SID = ?)");
            sql.addSql(" left join CMN_USRM");
            sql.addSql("       on FILE_DIRECTORY.FDR_EUID = CMN_USRM.USR_SID)");
            sql.addSql(" left join CMN_USRM_INF");
            sql.addSql("       on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID)");
            sql.addSql("  ) as DIRECTORY ");
            sql.addSql("     left join CMN_GROUPM");
            sql.addSql("       on DIRECTORY.FDR_EGID = CMN_GROUPM.GRP_SID");
            sql.addIntValue(prmModel.getUsrSid());

            //外貨マスタ
            sql.addSql("     left join FILE_MONEY_MASTER ");
            sql.addSql("     on DIRECTORY.EMT_SID = FILE_MONEY_MASTER.FMM_SID");
        }

        if (!isAdmin) {
            // キャビネット管理者 参照のための join句
            sql.addSql("     left join FILE_CABINET_ADMIN");
            sql.addSql("       on FILE_CABINET_ADMIN.FCB_SID = DIRECTORY.FCB_SID");
            sql.addSql("      and FILE_CABINET_ADMIN.USR_SID = ?");
            sql.addIntValue(prmModel.getUsrSid());
        }

        //全文検索使用時
        if (isTextSearch) {
            //ファイル内容検索テーブル join

            //キーワード検索
            String fullTextSql = "";
            boolean orFlg = prmModel.getWordKbn() == GSConstFile.KEY_WORD_KBN_OR;

            if (CommonBiz.isPGroongaUse()) {
                //PGroonga使用
                if (orFlg) {
                    fullTextSql = "&@| ARRAY[";
                } else {
                    fullTextSql = "&@~ '";
                }

                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        if (orFlg) {
                            fullTextSql = fullTextSql.concat(",");
                        }
                        fullTextSql = fullTextSql.concat(" ");
                    }

                    String word = JDBCUtil.encFullTextSearch(keywordList.get(i));
                    if (orFlg) {
                        fullTextSql = fullTextSql.concat("'");
                        fullTextSql = fullTextSql.concat(word);
                        fullTextSql = fullTextSql.concat("'");
                    } else {
                        fullTextSql = fullTextSql.concat(word);
                    }

                }

                if (orFlg) {
                    fullTextSql = fullTextSql.concat("]");
                } else {
                    fullTextSql = fullTextSql.concat("'");
                }
            }

            sql.addSql("     left join");
            sql.addSql("       (select");
            sql.addSql("          FILE_FILE_TEXT.FDR_SID,");
            sql.addSql("          FILE_FILE_TEXT.FDR_VERSION,");
            sql.addSql("          count(*) as MATCH_CNT");
            sql.addSql("        from");
            sql.addSql("          FILE_FILE_TEXT,");
            sql.addSql("          (select FDR_SID, max(FDR_VERSION) as MAXVERSION ");
            sql.addSql("            from FILE_DIRECTORY group by FDR_SID) as DIR_MAXVERSION");
            sql.addSql("        where");
            sql.addSql("          FILE_FILE_TEXT.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql("        and");
            sql.addSql("          FILE_FILE_TEXT.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addSql("        and");
            sql.addSql("          FILE_FILE_TEXT.FFT_READ_KBN = ?");
            sql.addSql("        and");
            sql.addIntValue(GSConstFile.READ_KBN_SUCCESSFUL);
            if (prmModel.getCabinet() != -1) {
                sql.addSql("          FILE_FILE_TEXT.FCB_SID = ?");
                sql.addSql("        and");
                sql.addIntValue(prmModel.getCabinet());
            }
//            sql.addSql("          to_tsvector('japanese', FILE_FILE_TEXT.FFT_TEXT)"
//                    + " @@ to_tsquery('japanese', '" + tsSearchWord + "')");
//            sql.addSql("          FILE_FILE_TEXT.FFT_TEXT " + tsSearchWord);

            if (CommonBiz.isPGroongaUse()) {
                //PGroonga使用
                sql.addSql("          FILE_FILE_TEXT.FFT_TEXT " + fullTextSql);

            } else {
                //PGroonga未使用(like検索)
                sql.addSql("          (");
                for (int keywordIdx = 0; keywordIdx < keywordList.size(); keywordIdx++) {
                    if (keywordIdx > 0) {
                        if (orFlg) {
                            sql.addSql("          or ");
                        } else {
                            sql.addSql("          and ");
                        }
                    }

                    sql.addSql("            FILE_FILE_TEXT.FFT_TEXT like '%"
                                + JDBCUtil.escapeForLikeSearch(keywordList.get(keywordIdx))
                                + "%' ESCAPE '"
                                + JDBCUtil.def_esc
                                + "'");
                }

                sql.addSql("          )");
            }
            sql.addSql("        group by");
            sql.addSql("          FILE_FILE_TEXT.FDR_SID,");
            sql.addSql("          FILE_FILE_TEXT.FDR_VERSION) as FILE_TEXT");
            sql.addSql("       on DIRECTORY.FDR_SID = FILE_TEXT.FDR_SID ");
            sql.addSql("       and DIRECTORY.FDR_VERSION = FILE_TEXT.FDR_VERSION");
        }
        sql.addSql("  where");

        if (prmModel.getCabinet() != -1) {

            sql.addSql("    DIRECTORY.FCB_SID = ?");
            sql.addSql(" and");
            sql.addIntValue(prmModel.getCabinet());
        }

        sql.addSql("    DIRECTORY.FCB_PERSONAL_FLG = ?");
        sql.addIntValue(prmModel.getPersonalFlg());

        if (prmModel.getErrlFlg() == GSConstFile.ERRL_KBN_ON) {
            //電帳法キャビネットの場合
            sql.addSql(" and");
            sql.addSql("    DIRECTORY.FCB_ERRL = ?");
            sql.addIntValue(GSConstFile.ERRL_KBN_ON);

            //管理者ユーザ以外、またはキャビネットに"全て"を選択した場合、削除キャビネット内の情報を閲覧不可とする
            if (!isAdmin || prmModel.getCabinet() == -1) {
                sql.addSql(" and");
                sql.addSql("    DIRECTORY.FCB_JKBN = ?");
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            }

            if (!StringUtil.isNullZeroString(prmModel.getSrchTradeTargetName())) {
                //取引先が入力されている場合
                sql.addSql(" and");
                sql.addSql("   DIRECTORY.FDR_TRADE_TARGET like '%"
                        + JDBCUtil.escapeForLikeSearch(prmModel.getSrchTradeTargetName())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'");
            }

            if (prmModel.getSrchTradeMoneyKbn() == GSConstFile.MONEY_KBN_OFF) {
                //取引金額指定なし
                sql.addSql(" and");
                sql.addSql("    DIRECTORY.FDR_TRADE_MONEYKBN = ?");
                sql.addIntValue(prmModel.getSrchTradeMoneyKbn());
            }

            if (prmModel.getSrchTradeMoney() != null
                    && prmModel.getSrchTradeMoneyKbn() == GSConstFile.MONEY_KBN_ON) {
                //取引金額が設定されている場合
                sql.addSql(" and");
                sql.addSql("   DIRECTORY.EMT_SID = ?");
                sql.addIntValue(prmModel.getSrchTradeMoneyType());

                sql.addSql(" and");
                sql.addDecimalValue(prmModel.getSrchTradeMoney());
                if (prmModel.getSrchTradeMoneyJudge() == GSConstFile.MONEY_JUEDGE_EQUAL) {
                    sql.addSql("   DIRECTORY.FDR_TRADE_MONEY = ?");
                } else if (prmModel.getSrchTradeMoneyJudge() == GSConstFile.MONEY_JUEDGE_MORE) {
                    sql.addSql("   DIRECTORY.FDR_TRADE_MONEY >= ?");
                } else if (prmModel.getSrchTradeMoneyJudge() == GSConstFile.MONEY_JUEDGE_LESS) {
                    sql.addSql("   DIRECTORY.FDR_TRADE_MONEY <= ?");
                } else if (prmModel.getSrchTradeMoneyJudge() == GSConstFile.MONEY_JUEDGE_BETWEEN) {
                    sql.addSql("   DIRECTORY.FDR_TRADE_MONEY >= ?");
                    sql.addSql(" and");
                    sql.addSql("   DIRECTORY.FDR_TRADE_MONEY <= ?");
                    sql.addDecimalValue(prmModel.getSrchTradeMoneyTo());
                }
            }

            if (prmModel.getSrchTradeDateFlg() == GSConstFile.SEARCH_USE) {
                //取引年月日が指定ありの場合
                if (prmModel.getSrchTradeDateFrom() != null) {
                    //開始が入力されている場合
                    sql.addSql(" and");
                    sql.addSql("   DIRECTORY.FDR_TRADE_DATE >= ?");
                    sql.addDateValue(prmModel.getSrchTradeDateFrom());
                }
                if (prmModel.getSrchTradeDateTo() != null) {
                    //終了が入力されている場合
                    sql.addSql(" and");
                    sql.addSql("   DIRECTORY.FDR_TRADE_DATE <= ?");
                    sql.addDateValue(prmModel.getSrchTradeDateTo());
                }
            }
        } else {
            //個人・共有キャビネットの場合
            sql.addSql(" and");
            sql.addSql("    DIRECTORY.FCB_ERRL = ?");
            sql.addSql(" and");
            sql.addSql("    DIRECTORY.FDR_JTKBN = ?");
            sql.addSql(" and");
            sql.addSql("    DIRECTORY.FCB_JKBN = ?");
            sql.addIntValue(GSConstFile.ERRL_KBN_OFF);
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);
        }

        sql.addSql(" and");
        sql.addSql("    DIRECTORY.FDR_PARENT_SID != ?");
        sql.addSql(" and");
        sql.addSql("    DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
        sql.addSql(" and");
        sql.addSql("    DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
        sql.addIntValue(GSConstFile.DIRECTORY_ROOT);

        //==== 検索条件 : 対象 ====
        boolean normalFolderFlg
            = NullDefault.getInt(prmModel.getTargetFolder(), -1)
                == GSConstFile.GET_TARGET_FOLDER;
        boolean normalFileFlg
            = NullDefault.getInt(prmModel.getTargetFile(), -1)
                == GSConstFile.GET_TARGET_FILE;
        boolean deleteFolderFlg
            = NullDefault.getInt(prmModel.getTargetDeletedFolder(), -1)
                == GSConstFile.GET_TARGET_DELETED;
        boolean deleteFileFlg
            = NullDefault.getInt(prmModel.getTargetDeletedFile(), -1)
                == GSConstFile.GET_TARGET_DELETED;

        if (normalFolderFlg || normalFileFlg || deleteFolderFlg || deleteFileFlg) {

            //フォルダ区分
            boolean folderFlg = normalFolderFlg || deleteFolderFlg;
            boolean fileFlg = normalFileFlg || deleteFileFlg;

            if (folderFlg && !fileFlg) {
                //対象項目がフォルダのみ
                sql.addSql(" and");
                sql.addSql("   DIRECTORY.FDR_KBN = ?");
                sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
            } else if (!folderFlg && fileFlg) {
                //対象項目がファイルのみ
                sql.addSql(" and");
                sql.addSql("   DIRECTORY.FDR_KBN = ?");
                sql.addIntValue(GSConstFile.DIRECTORY_FILE);
            }

            //削除キャビネット指定時はファイル・フォルダの削除状態を確認しない
            if (!prmModel.isDelCabinetFlg()) {

                boolean normalTargetFlg = normalFolderFlg || normalFileFlg;
                boolean deleteTargetFlg = deleteFolderFlg || deleteFileFlg;
                if (normalTargetFlg && !deleteTargetFlg) {
                    //通常のみ
                    sql.addSql(" and");
                    sql.addSql("   DIRECTORY.FDR_JTKBN = ?");
                    sql.addIntValue(GSConstFile.JTKBN_NORMAL);
                } else if (!normalTargetFlg && deleteTargetFlg) {
                    //削除のみ
                    sql.addSql(" and");
                    sql.addSql("   DIRECTORY.FDR_JTKBN = ?");
                    sql.addIntValue(GSConstFile.JTKBN_DELETE);
                } else {

                    sql.addSql(" and");
                    sql.addSql("   (");
                    sql.addSql("     (");
                    sql.addSql("       DIRECTORY.FDR_JTKBN = ?");
                    sql.addIntValue(GSConstFile.JTKBN_NORMAL);
                    if (normalFolderFlg && !normalFileFlg) {
                        //対象項目がフォルダのみ
                        sql.addSql("     and");
                        sql.addSql("       DIRECTORY.FDR_KBN = ?");
                        sql.addIntValue(GSConstFile.DIRECTORY_FOLDER);
                    } else if (!normalFolderFlg && normalFileFlg) {
                        //対象項目がファイルのみ
                        sql.addSql("     and");
                        sql.addSql("       DIRECTORY.FDR_KBN = ?");
                        sql.addIntValue(GSConstFile.DIRECTORY_FILE);
                    }
                    sql.addSql("     )");

                    sql.addSql("   or");
                    sql.addSql("     (");
                    sql.addSql("       DIRECTORY.FDR_JTKBN = ?");
                    sql.addIntValue(GSConstFile.JTKBN_DELETE);

                    sql.addSql("     and");
                    sql.addSql("       (");

                    if (deleteFolderFlg) {
                        sql.addSql("         DIRECTORY.FDR_KBN = ?");
                        sql.addIntValue(GSConstFile.DIRECTORY_KBN_FOLDER);
                    }

                    if (deleteFileFlg) {
                        if (deleteFolderFlg) {
                            sql.addSql("       or");
                        }
                        sql.addSql("         (");
                        sql.addSql("           DIRECTORY.FDR_KBN = ?");
                        sql.addSql("         and");
                        sql.addSql("           DIRECTORY.FDR_EDATE >= ?");
                        sql.addSql("         )");

                        sql.addIntValue(GSConstFile.DIRECTORY_KBN_FILE);
                        UDate lastEdate = new UDate();
                        lastEdate.addYear(-10);
                        sql.addDateValue(lastEdate);
                    }

                    sql.addSql("       )");
                    sql.addSql("     )");
                    sql.addSql("   )");
                }
            }
        }
        //==== 検索条件 : 対象 ====

        //時間指定ありの場合
        if (prmModel.getUpdateKbn() == GSConstFile.UPDATE_KBN_OK) {
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     DIRECTORY.FDR_EDATE");
            sql.addSql("     between ?");
            sql.addDateValue(prmModel.getUpFrDate());
            sql.addSql("     and ?");
            sql.addDateValue(prmModel.getUpToDate());
            sql.addSql("   )");
        }
        // KEYワード
        __createKeyWord(prmModel, sql);

        // キャビネット作成権限ユーザ以下の場合、各アクセス権限を参照
        if (!isAdmin) {
            sql.addSql(" and ");
            sql.addSql("   (");

            // キャビネット管理者の場合、ＯＫ
            sql.addSql("   FILE_CABINET_ADMIN.FCB_SID is not null or");

            // アクセス設定なし
            sql.addSql("   DIRECTORY.FDR_ACCESS_SID = ? or");
            sql.addIntValue(GSConstFile.DIRECTORY_ROOT);

            // アクセス設定に該当ユーザがいるかどうか
            sql.addSql("   exists ");
            sql.addSql("   (select 1");
            sql.addSql("    from");
            sql.addSql("      FILE_DACCESS_CONF A");
            sql.addSql("    where");
            sql.addSql("      A.FDR_SID = DIRECTORY.FDR_ACCESS_SID");
            sql.addSql("    and (");
            sql.addSql("      (A.USR_KBN = ? and");
            sql.addSql("       A.USR_SID = ?) or");
            sql.addSql("      (A.USR_KBN = ? and");
            sql.addSql("       exists");
            sql.addSql("         (select 1");
            sql.addSql("          from");
            sql.addSql("            CMN_BELONGM B");
            sql.addSql("          where");
            sql.addSql("            B.GRP_SID = A.USR_SID");
            sql.addSql("          and");
            sql.addSql("            B.USR_SID = ?");
            sql.addSql("         )))");
            sql.addSql("   )");
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(prmModel.getUsrSid());
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            sql.addIntValue(prmModel.getUsrSid());

            sql.addSql("   )");
        }

        // 個人キャビネットの場合、使用許可ユーザチェック
        if (prmModel.getPersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE
         && prmModel.getAconfMdl() != null) {
            if (prmModel.getAconfMdl().getFacUseKbn() == GSConstFile.CABINET_AUTH_USER) {
                sql.addSql("   and ");
                sql.addSql("     DIRECTORY.FCB_SID IN ( ");
                sql.addSql("       select ");
                sql.addSql("         FCB_SID ");
                sql.addSql("       from ");
                sql.addSql("         FILE_CABINET ");
                sql.addSql("       where ");
                sql.addSql("         FCB_PERSONAL_FLG = ? ");
                sql.addSql("       and ");
                sql.addSql("         FCB_OWNER_SID IN ( ");
                sql.addSql("           select");
                sql.addSql("             CMN_BELONGM.USR_SID as USR_SID");
                sql.addSql("           from");
                sql.addSql("             FILE_PCB_OWNER");
                sql.addSql("           left join");
                sql.addSql("             CMN_BELONGM");
                sql.addSql("           on CMN_BELONGM.GRP_SID = FILE_PCB_OWNER.USR_SID");
                sql.addSql("           where");
                sql.addSql("             USR_KBN = ?");
                sql.addSql("           union all");
                sql.addSql("           select");
                sql.addSql("             FILE_PCB_OWNER.USR_SID as USR_SID");
                sql.addSql("           from");
                sql.addSql("             FILE_PCB_OWNER");
                sql.addSql("           where");
                sql.addSql("             USR_KBN = ?");
                sql.addSql("         ) ");
                sql.addSql("     ) ");
                sql.addIntValue(GSConstFile.CABINET_KBN_PRIVATE);
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                sql.addIntValue(GSConstFile.USER_KBN_USER);
            }
            sql.addSql("   and ");
            sql.addSql("     DIRECTORY.FDR_SID IN ( ");
            sql.addSql("       select ");
            sql.addSql("         FILE_DACCESS_CONF.FDR_SID ");
            sql.addSql("       from ");
            sql.addSql("         FILE_DACCESS_CONF ");
            sql.addSql("       where ");
            sql.addSql("         (");
            sql.addSql("            FILE_DACCESS_CONF.USR_SID = ? ");
            sql.addSql("          and ");
            sql.addSql("            FILE_DACCESS_CONF.USR_KBN = ? ");
            sql.addSql("         )");
            sql.addSql("       or ");
            sql.addSql("         (");
            sql.addSql("            FILE_DACCESS_CONF.USR_SID IN ");
            sql.addSql("              ( ");
            sql.addSql("               select ");
            sql.addSql("                 GRP_SID ");
            sql.addSql("               from ");
            sql.addSql("                 CMN_BELONGM ");
            sql.addSql("               where ");
            sql.addSql("                 CMN_BELONGM.USR_SID = ?");
            sql.addSql("              ) ");
            sql.addSql("          and ");
            sql.addSql("            FILE_DACCESS_CONF.USR_KBN = ? ");
            sql.addSql("         )");
            sql.addSql("     ) ");
            sql.addIntValue(prmModel.getUsrSid());
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(prmModel.getUsrSid());
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);
        }

        if (!isCount) {
            String orderStr = "";
            // オーダー
            if (prmModel.getSearchOrderKey() == GSConstFile.ORDER_KEY_ASC) {
                orderStr = "  asc";
            } else if (prmModel.getSearchOrderKey() == GSConstFile.ORDER_KEY_DESC) {
                orderStr = "  desc";
            }

            sql.addSql(" order by ");
            switch (prmModel.getSearchSortKey()) {

            case GSConstFile.SORT_NAME:
                sql.addSql("   DIRECTORY.FDR_NAME " + orderStr);
                break;

            case GSConstFile.SORT_SIZE:
                sql.addSql("   DIRECTORY.FFL_FILE_SIZE " + orderStr);
                break;

            case GSConstFile.SORT_CALL:
                sql.addSql("   DIRECTORY.USR_SID " + orderStr);
                break;

            case GSConstFile.SORT_EDATE:
                sql.addSql("   DIRECTORY.FDR_EDATE " + orderStr);
                break;

            case GSConstFile.SORT_EUSR:
                sql.addSql("   DIRECTORY.FDR_EUID " + orderStr);
                break;

            case GSConstFile.SORT_TRADE_DATE:
                sql.addSql("   DIRECTORY.FDR_TRADE_DATE " + orderStr);
                break;

            case GSConstFile.SORT_TRADE_TARGET:
                sql.addSql("   DIRECTORY.FDR_TRADE_TARGET " + orderStr);
                break;

            case GSConstFile.SORT_TRADE_MONEY:
                sql.addSql("   DIRECTORY.FDR_TRADE_MONEY " + orderStr);
                break;

            default:
                break;

            }
        }

        return sql;
    }

    /**
     * <br>[機  能] ファイル詳細検索結果一覧情報を格納したモデルを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @return bean FileDspModel
     * @throws SQLException SQL実行例外
     */
    private FileModel __getFileSrchdataFromRs(ResultSet rs) throws SQLException {
        FileModel bean = new FileModel();

        bean.setFdrSid(rs.getInt("FDR_SID"));
        bean.setFdrVersion(rs.getInt("FDR_VERSION"));
        bean.setFcbSid(rs.getInt("FCB_SID"));
        bean.setFdrParentSid(rs.getInt("FDR_PARENT_SID"));
        bean.setFdrKbn(rs.getInt("FDR_KBN"));
        bean.setFdrVerKbn(rs.getInt("FDR_VER_KBN"));
        bean.setFdrLevel(rs.getInt("FDR_LEVEL"));
        bean.setFdrName(rs.getString("FDR_NAME"));
        bean.setFdrBiko(rs.getString("FDR_BIKO"));
        bean.setFdrJtkbn(rs.getInt("FDR_JTKBN"));
        bean.setFdrEuid(rs.getInt("FDR_EUID"));
        bean.setFdrEgid(rs.getInt("FDR_EGID"));
        bean.setFdrEdate(UDate.getInstanceTimestamp(rs.getTimestamp("FDR_EDATE")));
        bean.setFdrTradeDate(UDate.getInstanceTimestamp(rs.getTimestamp("FDR_TRADE_DATE")));
        bean.setFdrTradeTarget(NullDefault.getString(rs.getString("FDR_TRADE_TARGET"), ""));
        bean.setFdrTradeMoney(rs.getBigDecimal("FDR_TRADE_MONEY"));
        bean.setFdrTradeMoneyKbn(rs.getInt("FDR_TRADE_MONEYKBN"));

        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setFflExt(rs.getString("FFL_EXT"));

        BigDecimal bdSize = rs.getBigDecimal("FFL_FILE_SIZE");
        if (bdSize != null) {
            //B→KBへ変換
            String strSize = StringUtil.toCommaFromBigDecimal(
                    bdSize.divide(GSConstFile.KB_TO_MB, 1, RoundingMode.HALF_UP));
            bean.setFflFileSizeStr(strSize + " KB");
        }

        bean.setFflLockKbn(rs.getInt("FFL_LOCK_KBN"));
        bean.setFflLockUser(rs.getInt("FFL_LOCK_USER"));
        bean.setFcbJkbn(rs.getInt("FCB_JKBN"));

        bean.setUsrSid(rs.getInt("USR_SID"));

        bean.setEmtSid(rs.getInt("EMT_SID"));
        bean.setFmmName(rs.getString("FMM_NAME"));

        if (bean.getFdrEgid() > 0) {
            bean.setUsrSei(rs.getString("GRP_NAME"));
            bean.setUsrMei("");
            bean.setUsrJKbn(rs.getInt("GRP_JKBN"));
        } else {
            bean.setUsrSei(rs.getString("USI_SEI"));
            bean.setUsrMei(rs.getString("USI_MEI"));
            bean.setUsrJKbn(rs.getInt("USR_JKBN"));
            bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        }

        return bean;
    }

    /**
     *
     * <br>[機 能] SQL(キーワード入力時の検索条件)を作成
     * <br>[解 説]
     * <br>[備 考]
     * @param bean パラメータモデル
     * @param sql SQL文
     */
    private void __createKeyWord(Fil100SearchParameterModel bean, SqlBuffer sql) {
        //キーワード入力時
        List<String>  keywordList = bean.getKeyWord();
        if (keywordList != null && keywordList.size() > 0) {

            String keywordJoin = "  and";
            if (bean.getWordKbn() == GSConstFile.KEY_WORD_KBN_OR) {
                keywordJoin = "   or";
            }

            boolean isTarget = false;

            if (bean.isSrchTargetName()) {
                if (isTarget) {
                    sql.addSql("   or");
                } else {
                    sql.addSql(" and");
                    sql.addSql(" (");
                    isTarget = true;
                }

                sql.addSql("   (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       DIRECTORY.FDR_NAME like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }
                sql.addSql("   )");
            }

            if (bean.isSrchTargetBiko()) {
                if (isTarget) {
                    sql.addSql("   or");
                } else {
                    sql.addSql(" and");
                    sql.addSql(" (");
                    isTarget = true;
                }

                sql.addSql("   (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       DIRECTORY.FDR_BIKO like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }
                sql.addSql("   )");
            }
            if (bean.isSrchTargetText()) {
                if (isTarget) {
                    sql.addSql("   or");
                } else {
                    sql.addSql(" and");
                    sql.addSql(" (");
                    isTarget = true;
                }

                sql.addSql("       coalesce(FILE_TEXT.MATCH_CNT,0) > 0");
            }

            if (isTarget) {
                sql.addSql(" )");
            }
        }
    }

    /**
     * <br>[機  能] レコード件数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  prmModel 検索パラメータモデル
     * @param  usModel  セッションユーザ情報
     * @return count    カウント件数
     * @throws SQLException SQL実行例外
     */
    public int recordCount(Fil100SearchParameterModel prmModel,
                            BaseUserModel usModel) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __getCreateSearchSql(prmModel, usModel, con, true);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 引数で指定した単語をSQLで使用できる形式に加工します。
     * <br>[解  説]
     * <br>[備  考]
     * @param strItem 処理したい文字列
     * @param esc エスケープする文字列
     * @return  strItem を処理した文字列
     */
    public static String encFullStringTS(String strItem, String esc) {

        //文字列が0の時はそのまま返す
        if (strItem.length() == 0) {
            return "";
        }
        // 文字列中に「'」「&」「|」「!」「(」「)」「:」「\t」がなければそのまま返す
        if (
            (strItem.indexOf("\'") < 0)
            &&
            (strItem.indexOf("&") < 0)
            &&
            (strItem.indexOf("|") < 0)
            &&
            (strItem.indexOf("!") < 0)
            &&
            (strItem.indexOf("(") < 0)
            &&
            (strItem.indexOf(")") < 0)
            &&
            (strItem.indexOf(":") < 0)
            &&
            (strItem.indexOf("\t") < 0)     //タブ
            &&
            (strItem.indexOf("\\") < 0)
        ) {
            return strItem;
        }

        //文字列変換部分
        StringBuilder strBuf = new StringBuilder();
        for (int intCnt = 0; intCnt < strItem.length(); intCnt++) {
            char ch = strItem.charAt(intCnt);
            if ('\'' == ch) {
                strBuf.append(esc);
                strBuf.append("''");
            } else if ('\\' == ch) {
                strBuf.append(esc);
                strBuf.append("\\\\");
            } else if ('&' == ch
                     || '|' == ch
                     || '!' == ch
                     || '(' == ch
                     || ')' == ch
                     || ':' == ch
                     || '\t' == ch) {
                strBuf.append(esc);
                strBuf.append(ch);
            } else {
                strBuf.append(ch);
            }
        }
        return new String(strBuf);
    }

    /**
     * <br>[機  能] 引数で指定した単語をSQLで使用できる形式に加工します。
     * <br>[解  説]
     * <br>[備  考]
     * @param   strItem 処理したい文字列
     * @return  strItem を処理した文字列
     */
    public static String encFullStringLikeTS(String strItem) {
        return encFullStringLikeTS(strItem, "\\\\");
    }

    /**
     * <br>[機  能] 引数で指定した単語をSQLで使用できる形式に加工します。
     * <br>[解  説]
     * <br>[備  考]
     * @param strItem 処理したい文字列
     * @param esc エスケープする文字列
     * @return  strItem を処理した文字列
     */
    public static String encFullStringLikeTS(String strItem, String esc) {

        //文字列が0の時はそのまま返す
        if (strItem == null || strItem.length() == 0) {
            return strItem;
        }

        String encStr = strItem.toString();
        if (strItem.indexOf(esc) >= 0) {
            //文字列変換部分
            StringBuilder strBuf = new StringBuilder();
            for (int intCnt = 0; intCnt < strItem.length(); intCnt++) {
                char ch = strItem.charAt(intCnt);
                if (esc.charAt(0) == ch) {
                    strBuf.append(esc);
                    strBuf.append(ch);
                } else {
                    strBuf.append(ch);
                }
            }

            encStr = strBuf.toString();
        }

        return encFullStringTS(encStr, esc);
    }
}