package jp.groupsession.v2.api.webmail.templist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.wml.model.MailTempFileModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p> WEBメール一覧情報を更新する際に使用するDAOクラス
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ApiWmlTempListDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlTempListDao.class);

    /**
     * <p>Default Constructor
     */
    public ApiWmlTempListDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ApiWmlTempListDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] webmailのアカウント情報(WAC_SIDとWAC_NAME)の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl     リクエストモデル
     * @param wtpSidList ディレクトリSID一覧
     * @return List in WML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, List<MailTempFileModel>> getTempFileList(RequestModel reqMdl,
                                               List<Integer> wtpSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet  rs  = null;
        Connection con = getCon();
        HashMap<Integer, List<MailTempFileModel>> ret
                                = new HashMap<Integer, List<MailTempFileModel>>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WTP_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   WML_MAIL_TEMPLATE_FILE");
            sql.addSql(" where ");
            sql.addSql("   WTP_SID in (");

            for (int idx = 0; idx < wtpSidList.size(); idx++) {
                sql.addSql((idx > 0 ? "   ,?" : "   ?"));
                sql.addIntValue(wtpSidList.get(idx));
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            List<String> binSidList = new ArrayList<String>();
            while (rs.next()) {
                // バイナリSID一覧取得
                String wtpSid = rs.getString("WTP_SID");
                String binSid = rs.getString("BIN_SID");
                if (wtpSid != null && wtpSid.length() > 0
                 && binSid != null && binSid.length() > 0) {
                    Integer key = Integer.valueOf(wtpSid);
                    List<MailTempFileModel> binList = ret.get(key);
                    if (binList == null) {
                        binList = new ArrayList<MailTempFileModel>();
                        ret.put(key, binList);
                    }
                    MailTempFileModel mdl = new MailTempFileModel();
                    mdl.setBinSid(Long.valueOf(binSid));
                    binList.add(mdl);
                    binSidList.add(binSid);
                }
            }

            // バイナリSID一覧からデータ一覧取得
            HashMap<Long, CmnBinfModel> binMap = new HashMap<Long, CmnBinfModel>();
            if (binSidList.size() > 0) {
                CommonBiz cmnBiz = new CommonBiz();
                String[] binSidArr = new String[binSidList.size()];
                binSidList.toArray(binSidArr);
                List<CmnBinfModel> binList = cmnBiz.getBinInfo(con, binSidArr, reqMdl.getDomain());
                if (binList != null && binList.size() > 0) {
                    for (CmnBinfModel mdl : binList) {
                        binMap.put(mdl.getBinSid(), mdl);
                    }
                }
            }

            // バイナリデータ一覧を応答用の配列へ変換
            if (ret.size() > 0 && binMap.size() > 0) {
                for (Integer key : ret.keySet()) {
                    List<MailTempFileModel> binList = ret.get(key);
                    if (binList.size() > 0) {
                        for (MailTempFileModel bin : binList) {
                            CmnBinfModel mdl = binMap.get(bin.getBinSid());
                            if (mdl != null) {
                                bin.setFileName(mdl.getBinFileName());
                                bin.setFilePath(mdl.getBinFilePath());
                                bin.setFileSize(mdl.getBinFileSize());
                                bin.setFileSizeDsp(mdl.getBinFileSizeDsp());
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        } catch (TempFileException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return ret;
    }
}
