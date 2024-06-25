package jp.groupsession.v2.fil.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil010.FileCabinetDspModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.ptl050.FileCabinetInfoModel;

/**
 * <p>FILE_CABINET Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileCabinetDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileCabinetDao.class);

    /**
     * <p>Default Constructor
     */
    public FileCabinetDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileCabinetDao(Connection con) {
        super(con);
    }

    /**
     * <p>Drop Table
     * @throws SQLException SQL実行例外
     */
    public void dropTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("drop table FILE_CABINET");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create Table
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create table FILE_CABINET (");
            sql.addSql("   FCB_SID NUMBER(10,0) not null,");
            sql.addSql("   FCB_NAME varchar(150) not null,");
            sql.addSql("   FCB_ACCESS_KBN NUMBER(10,0) not null,");
            sql.addSql("   FCB_CAPA_KBN NUMBER(10,0) not null,");
            sql.addSql("   FCB_CAPA_SIZE NUMBER(10,0),");
            sql.addSql("   FCB_CAPA_WARN NUMBER(10,0),");
            sql.addSql("   FCB_VER_KBN NUMBER(10,0) not null,");
            sql.addSql("   FCB_VERALL_KBN NUMBER(10,0),");
            sql.addSql("   FCB_BIKO NUMBER(10,0),");
            sql.addSql("   FCB_SORT NUMBER(10,0) not null,");
            sql.addSql("   FCB_MARK NUMBER(10,0) not null,");
            sql.addSql("   FCB_AUID NUMBER(10,0) not null,");
            sql.addSql("   FCB_ADATE varchar(23) not null,");
            sql.addSql("   FCB_EUID NUMBER(10,0) not null,");
            sql.addSql("   FCB_EDATE varchar(23) not null,");
            sql.addSql("   FCB_PERSONAL_FLG NUMBER(10,0) not null,");
            sql.addSql("   FCB_OWNER_SID NUMBER(10,0) not null,");
            sql.addSql("   FCB_ERRL integer not null,");
            sql.addSql("   FCB_SORT_FOLDER integer not null,");
            sql.addSql("   FCB_SORT_FOLDER1 integer,");
            sql.addSql("   FCB_SORT_FOLDER2 integer,");
            sql.addSql("   FCB_SORT_FOLDER3 integer,");
            sql.addSql("   FCB_JKBN integer not null,");
            sql.addSql("   primary key (FCB_SID)");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Insert FILE_CABINET Data Bindding JavaBean
     * @param bean FILE_CABINET Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FileCabinetModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_CABINET(");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FCB_NAME,");
            sql.addSql("   FCB_ACCESS_KBN,");
            sql.addSql("   FCB_CAPA_KBN,");
            sql.addSql("   FCB_CAPA_SIZE,");
            sql.addSql("   FCB_CAPA_WARN,");
            sql.addSql("   FCB_VER_KBN,");
            sql.addSql("   FCB_VERALL_KBN,");
            sql.addSql("   FCB_BIKO,");
            sql.addSql("   FCB_SORT,");
            sql.addSql("   FCB_MARK,");
            sql.addSql("   FCB_AUID,");
            sql.addSql("   FCB_ADATE,");
            sql.addSql("   FCB_EUID,");
            sql.addSql("   FCB_EDATE,");
            sql.addSql("   FCB_PERSONAL_FLG,");
            sql.addSql("   FCB_OWNER_SID,");
            sql.addSql("   FCB_ERRL,");
            sql.addSql("   FCB_SORT_FOLDER,");
            sql.addSql("   FCB_SORT_FOLDER1,");
            sql.addSql("   FCB_SORT_FOLDER2,");
            sql.addSql("   FCB_SORT_FOLDER3,");
            sql.addSql("   FCB_JKBN");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFcbSid());
            sql.addStrValue(bean.getFcbName());
            sql.addIntValue(bean.getFcbAccessKbn());
            sql.addIntValue(bean.getFcbCapaKbn());
            sql.addIntValue(bean.getFcbCapaSize());
            sql.addIntValue(bean.getFcbCapaWarn());
            sql.addIntValue(bean.getFcbVerKbn());
            sql.addIntValue(bean.getFcbVerallKbn());
            sql.addStrValue(bean.getFcbBiko());
            sql.addIntValue(bean.getFcbSort());
            sql.addLongValue(bean.getFcbMark());
            sql.addIntValue(bean.getFcbAuid());
            sql.addDateValue(bean.getFcbAdate());
            sql.addIntValue(bean.getFcbEuid());
            sql.addDateValue(bean.getFcbEdate());
            sql.addIntValue(bean.getFcbPersonalFlg());
            sql.addIntValue(bean.getFcbOwnerSid());
            sql.addIntValue(bean.getFcbErrl());
            sql.addIntValue(bean.getFcbSortFolder());
            sql.addIntValue(bean.getFcbSortFolder1());
            sql.addIntValue(bean.getFcbSortFolder2());
            sql.addIntValue(bean.getFcbSortFolder3());
            sql.addIntValue(bean.getFcbJkbn());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Insert FILE_CABINET Data Bindding JavaBean
     * @param beanList FILE_CABINET DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<FileCabinetModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_CABINET(");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FCB_NAME,");
            sql.addSql("   FCB_ACCESS_KBN,");
            sql.addSql("   FCB_CAPA_KBN,");
            sql.addSql("   FCB_CAPA_SIZE,");
            sql.addSql("   FCB_CAPA_WARN,");
            sql.addSql("   FCB_VER_KBN,");
            sql.addSql("   FCB_VERALL_KBN,");
            sql.addSql("   FCB_BIKO,");
            sql.addSql("   FCB_SORT,");
            sql.addSql("   FCB_MARK,");
            sql.addSql("   FCB_AUID,");
            sql.addSql("   FCB_ADATE,");
            sql.addSql("   FCB_EUID,");
            sql.addSql("   FCB_EDATE,");
            sql.addSql("   FCB_PERSONAL_FLG,");
            sql.addSql("   FCB_OWNER_SID,");
            sql.addSql("   FCB_ERRL,");
            sql.addSql("   FCB_SORT_FOLDER,");
            sql.addSql("   FCB_SORT_FOLDER1,");
            sql.addSql("   FCB_SORT_FOLDER2,");
            sql.addSql("   FCB_SORT_FOLDER3,");
            sql.addSql("   FCB_JKBN");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (FileCabinetModel bean : beanList) {
                sql.addIntValue(bean.getFcbSid());
                sql.addStrValue(bean.getFcbName());
                sql.addIntValue(bean.getFcbAccessKbn());
                sql.addIntValue(bean.getFcbCapaKbn());
                sql.addIntValue(bean.getFcbCapaSize());
                sql.addIntValue(bean.getFcbCapaWarn());
                sql.addIntValue(bean.getFcbVerKbn());
                sql.addIntValue(bean.getFcbVerallKbn());
                sql.addStrValue(bean.getFcbBiko());
                sql.addIntValue(bean.getFcbSort());
                sql.addLongValue(bean.getFcbMark());
                sql.addIntValue(bean.getFcbAuid());
                sql.addDateValue(bean.getFcbAdate());
                sql.addIntValue(bean.getFcbEuid());
                sql.addDateValue(bean.getFcbEdate());
                sql.addIntValue(bean.getFcbPersonalFlg());
                sql.addIntValue(bean.getFcbOwnerSid());
                sql.addIntValue(bean.getFcbErrl());
                sql.addIntValue(bean.getFcbSortFolder());
                sql.addIntValue(bean.getFcbSortFolder1());
                sql.addIntValue(bean.getFcbSortFolder2());
                sql.addIntValue(bean.getFcbSortFolder3());
                sql.addIntValue(bean.getFcbJkbn());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();

                sql.clearValue();

            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update FILE_CABINET Data Bindding JavaBean
     * @param bean FILE_CABINET Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FileCabinetModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" set ");
            sql.addSql("   FCB_NAME=?,");
            sql.addSql("   FCB_ACCESS_KBN=?,");
            sql.addSql("   FCB_CAPA_KBN=?,");
            sql.addSql("   FCB_CAPA_SIZE=?,");
            sql.addSql("   FCB_CAPA_WARN=?,");
            sql.addSql("   FCB_VER_KBN=?,");
            sql.addSql("   FCB_VERALL_KBN=?,");
            sql.addSql("   FCB_BIKO=?,");
//            sql.addSql("   FCB_SORT=?,");
            sql.addSql("   FCB_MARK=?,");
            sql.addSql("   FCB_EUID=?,");
            sql.addSql("   FCB_EDATE=?,");
            sql.addSql("   FCB_ERRL=?,");
            sql.addSql("   FCB_SORT_FOLDER=?,");
            sql.addSql("   FCB_SORT_FOLDER1=?,");
            sql.addSql("   FCB_SORT_FOLDER2=?,");
            sql.addSql("   FCB_SORT_FOLDER3=?,");
            sql.addSql("   FCB_JKBN=?");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getFcbName());
            sql.addIntValue(bean.getFcbAccessKbn());
            sql.addIntValue(bean.getFcbCapaKbn());
            sql.addIntValue(bean.getFcbCapaSize());
            sql.addIntValue(bean.getFcbCapaWarn());
            sql.addIntValue(bean.getFcbVerKbn());
            sql.addIntValue(bean.getFcbVerallKbn());
            sql.addStrValue(bean.getFcbBiko());
//            sql.addValue(bean.getFcbSort());
            sql.addLongValue(bean.getFcbMark());
            sql.addIntValue(bean.getFcbEuid());
            sql.addDateValue(bean.getFcbEdate());
            sql.addIntValue(bean.getFcbErrl());
            sql.addIntValue(bean.getFcbSortFolder());
            sql.addIntValue(bean.getFcbSortFolder1());
            sql.addIntValue(bean.getFcbSortFolder2());
            sql.addIntValue(bean.getFcbSortFolder3());
            sql.addIntValue(bean.getFcbJkbn());
            //where
            sql.addIntValue(bean.getFcbSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Update 名称を更新しません
     * @param bean FILE_CABINET Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateNoName(FileCabinetModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" set ");
            //sql.addSql("   FCB_NAME=?,");
            sql.addSql("   FCB_ACCESS_KBN=?,");
            sql.addSql("   FCB_CAPA_KBN=?,");
            sql.addSql("   FCB_CAPA_SIZE=?,");
            sql.addSql("   FCB_CAPA_WARN=?,");
            sql.addSql("   FCB_VER_KBN=?,");
            sql.addSql("   FCB_VERALL_KBN=?,");
            sql.addSql("   FCB_BIKO=?,");
//            sql.addSql("   FCB_MARK=?,");
            sql.addSql("   FCB_EUID=?,");
            sql.addSql("   FCB_EDATE=?,");
            sql.addSql("   FCB_ERRL=?,");
            sql.addSql("   FCB_SORT_FOLDER=?,");
            sql.addSql("   FCB_SORT_FOLDER1=?,");
            sql.addSql("   FCB_SORT_FOLDER2=?,");
            sql.addSql("   FCB_SORT_FOLDER3=?,");
            sql.addSql("   FCB_JKBN=?");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //sql.addValue(bean.getFcbName());
            sql.addIntValue(bean.getFcbAccessKbn());
            sql.addIntValue(bean.getFcbCapaKbn());
            sql.addIntValue(bean.getFcbCapaSize());
            sql.addIntValue(bean.getFcbCapaWarn());
            sql.addIntValue(bean.getFcbVerKbn());
            sql.addIntValue(bean.getFcbVerallKbn());
            sql.addStrValue(bean.getFcbBiko());
//            sql.addValue(bean.getFcbMark());
            sql.addIntValue(bean.getFcbEuid());
            sql.addDateValue(bean.getFcbEdate());
            sql.addIntValue(bean.getFcbErrl());
            sql.addIntValue(bean.getFcbSortFolder());
            sql.addIntValue(bean.getFcbSortFolder1());
            sql.addIntValue(bean.getFcbSortFolder2());
            sql.addIntValue(bean.getFcbSortFolder3());
            sql.addIntValue(bean.getFcbJkbn());
            //where
            sql.addIntValue(bean.getFcbSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>表示順を更新する。
     * @param bean FILE_CABINET Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSort(FileCabinetModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" set ");
            sql.addSql("   FCB_SORT=?,");
            sql.addSql("   FCB_EUID=?,");
            sql.addSql("   FCB_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFcbSort());
            sql.addIntValue(bean.getFcbEuid());
            sql.addDateValue(bean.getFcbEdate());
            //where
            sql.addIntValue(bean.getFcbSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>個人キャビネットのバージョン区分を更新する。
     * @param personalVer 個人キャビネットバージョン管理区分
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updatePersonalVerKbn(int personalVer) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" set ");
            sql.addSql("   FCB_VER_KBN=?");
            sql.addSql(" where ");
            sql.addSql("   FCB_PERSONAL_FLG=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(personalVer);
            //where
            sql.addIntValue(GSConstFile.CABINET_KBN_PRIVATE);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] キャビネットの論理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @param usrSid ユーザSID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int deleteCabinet(int fcbSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" set ");
            sql.addSql("   FCB_JKBN=?,");
            sql.addSql("   FCB_EUID=?,");
            sql.addSql("   FCB_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstFile.JTKBN_DELETE);
            sql.addIntValue(usrSid);
            sql.addDateValue(new UDate());

            //where
            sql.addIntValue(fcbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Select FILE_CABINET All Data
     * @return List in FILE_CABINETModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileCabinetModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileCabinetModel> ret = new ArrayList<FileCabinetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FCB_NAME,");
            sql.addSql("   FCB_ACCESS_KBN,");
            sql.addSql("   FCB_CAPA_KBN,");
            sql.addSql("   FCB_CAPA_SIZE,");
            sql.addSql("   FCB_CAPA_WARN,");
            sql.addSql("   FCB_VER_KBN,");
            sql.addSql("   FCB_VERALL_KBN,");
            sql.addSql("   FCB_BIKO,");
            sql.addSql("   FCB_SORT,");
            sql.addSql("   FCB_MARK,");
            sql.addSql("   FCB_AUID,");
            sql.addSql("   FCB_ADATE,");
            sql.addSql("   FCB_EUID,");
            sql.addSql("   FCB_EDATE,");
            sql.addSql("   FCB_PERSONAL_FLG,");
            sql.addSql("   FCB_OWNER_SID,");
            sql.addSql("   FCB_ERRL,");
            sql.addSql("   FCB_SORT_FOLDER,");
            sql.addSql("   FCB_SORT_FOLDER1,");
            sql.addSql("   FCB_SORT_FOLDER2,");
            sql.addSql("   FCB_SORT_FOLDER3,");
            sql.addSql("   FCB_JKBN");
            sql.addSql(" from ");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" order by ");
            sql.addSql("   FCB_SORT ASC");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileCabinetFromRs(rs));
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
     * <p>全てのキャビネットを取得します
     * @return List in FileCabinetInfoModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileCabinetInfoModel> selectPtlCabinet() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileCabinetInfoModel> ret = new ArrayList<FileCabinetInfoModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FCB_NAME,");
            sql.addSql("   FCB_ACCESS_KBN,");
            sql.addSql("   FCB_CAPA_KBN,");
            sql.addSql("   FCB_CAPA_SIZE,");
            sql.addSql("   FCB_CAPA_WARN,");
            sql.addSql("   FCB_VER_KBN,");
            sql.addSql("   FCB_VERALL_KBN,");
            sql.addSql("   FCB_BIKO,");
            sql.addSql("   FCB_SORT,");
            sql.addSql("   FCB_MARK,");
            sql.addSql("   FCB_AUID,");
            sql.addSql("   FCB_ADATE,");
            sql.addSql("   FCB_EUID,");
            sql.addSql("   FCB_EDATE,");
            sql.addSql("   FCB_PERSONAL_FLG,");
            sql.addSql("   FCB_ERRL,");
            sql.addSql("   FCB_SORT_FOLDER,");
            sql.addSql("   FCB_SORT_FOLDER1,");
            sql.addSql("   FCB_SORT_FOLDER2,");
            sql.addSql("   FCB_SORT_FOLDER3,");
            sql.addSql("   FCB_JKBN");
            sql.addSql(" from ");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" order by ");
            sql.addSql("   FCB_SORT ASC");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileCabinetInfoFromRs(rs));
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
     * <p>全てのキャビネットを取得します。
     * @param personalFlg 個人キャビネット判定フラグ 0:共有キャビネット 1:個人キャビネット 2:電帳法キャビネット
     * @return List in FILE_CABINETModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileCabinetModel> selectPersonalCabinet(int personalFlg)
            throws SQLException {

        return selectSort(personalFlg, true);
    }

    /**
     * <p>表示用のキャビネット情報モデルを取得する
     * @param personalFlg 個人キャビネット判定フラグ 0:共有キャビネット 1:個人キャビネット 2:電帳法キャビネット
     * @return List in FILE_CABINETModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileCabinetDspModel> getFileCabinetDspModelsAll(int personalFlg)
            throws SQLException {
        return getFileCabinetDspModelsAll(personalFlg, true);
    }

    /**
     * <p>表示用のキャビネット情報モデルを取得する
     * @param personalFlg 個人キャビネット判定フラグ 0:共有キャビネット 1:個人キャビネット 2:電帳法キャビネット
     * @return List in FILE_CABINETModel
     * @param checkDelete true: 論理削除キャビネットは除外する false:削除キャビネットを含む
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileCabinetDspModel> getFileCabinetDspModelsAll(int personalFlg,
                                                                    boolean checkDelete)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileCabinetDspModel> ret = new ArrayList<FileCabinetDspModel>();
        FileCabinetDspModel bean = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FCB_NAME,");
            sql.addSql("   FCB_ACCESS_KBN,");
            sql.addSql("   FCB_CAPA_KBN,");
            sql.addSql("   FCB_CAPA_SIZE,");
            sql.addSql("   FCB_CAPA_WARN,");
            sql.addSql("   FCB_VER_KBN,");
            sql.addSql("   FCB_VERALL_KBN,");
            sql.addSql("   FCB_BIKO,");
            sql.addSql("   FCB_SORT,");
            sql.addSql("   FCB_MARK,");
            sql.addSql("   FCB_AUID,");
            sql.addSql("   FCB_ADATE,");
            sql.addSql("   FCB_EUID,");
            sql.addSql("   FCB_EDATE,");
            sql.addSql("   FCB_PERSONAL_FLG,");
            sql.addSql("   FCB_OWNER_SID,");
            sql.addSql("   FCB_ERRL,");
            sql.addSql("   FCB_SORT_FOLDER,");
            sql.addSql("   FCB_SORT_FOLDER1,");
            sql.addSql("   FCB_SORT_FOLDER2,");
            sql.addSql("   FCB_SORT_FOLDER3,");
            sql.addSql("   FCB_JKBN");
            sql.addSql(" from ");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" where ");
            sql.addSql("   FCB_PERSONAL_FLG=?");
            if (personalFlg == GSConstFile.CABINET_KBN_PRIVATE) {
                sql.addIntValue(personalFlg);
            } else {
                sql.addIntValue(GSConstFile.CABINET_KBN_PUBLIC);
                sql.addSql(" and ");
                sql.addSql("   FCB_ERRL=?");
                if (personalFlg == GSConstFile.CABINET_KBN_PUBLIC) {
                    sql.addIntValue(GSConstFile.SORT_FOLDER_NOT_USE);
                } else {
                    sql.addIntValue(GSConstFile.SORT_FOLDER_USE);
                }
            }
            if (checkDelete) {
                sql.addSql(" and ");
                sql.addSql("   FCB_JKBN=?");
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            }
            sql.addSql(" order by ");
            sql.addSql("   FCB_SORT ASC");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = __getFileCabinetDspFromRs(rs);
                bean.setAccessIconKbn(GSConstFile.ACCESS_KBN_WRITE);
                ret.add(bean);
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
     * <p>表示用のキャビネット情報モデルを取得する
     * @param usrSid ユーザSID
     * @param usrKbn ユーザ区分(0: ユーザ / 1: グループ)
     * @param personalFlg 個人キャビネット判定フラグ 0: 共有キャビネット 1:個人キャビネット
     * @param auth 権限フラグ
     * @return List in FILE_CABINETModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileCabinetDspModel> getFileCabinetDspModels(int usrSid, int usrKbn,
                                                                  int personalFlg, int auth)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileCabinetDspModel> ret = new ArrayList<FileCabinetDspModel>();
        FileCabinetDspModel bean = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FILE_CABINET.FCB_SID,");
            sql.addSql("   FILE_CABINET.FCB_NAME,");
            sql.addSql("   FILE_CABINET.FCB_ACCESS_KBN,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_KBN,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_SIZE,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_WARN,");
            sql.addSql("   FILE_CABINET.FCB_VER_KBN,");
            sql.addSql("   FILE_CABINET.FCB_VERALL_KBN,");
            sql.addSql("   FILE_CABINET.FCB_BIKO,");
            sql.addSql("   FILE_CABINET.FCB_SORT,");
            sql.addSql("   FILE_CABINET.FCB_MARK,");
            sql.addSql("   FILE_CABINET.FCB_AUID,");
            sql.addSql("   FILE_CABINET.FCB_ADATE,");
            sql.addSql("   FILE_CABINET.FCB_EUID,");
            sql.addSql("   FILE_CABINET.FCB_EDATE,");
            sql.addSql("   FILE_CABINET.FCB_PERSONAL_FLG,");
            sql.addSql("   FILE_CABINET.FCB_OWNER_SID,");
            sql.addSql("   FILE_CABINET.FCB_ERRL,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER1,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER2,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER3,");
            sql.addSql("   FILE_CABINET.FCB_JKBN,");
            sql.addSql("   FILE_DIRECTORY.FDA_AUTH,");
            sql.addSql("   FILE_DIRECTORY.USR_SID");
            sql.addSql(" from ");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" left join (");
            sql.addSql("     select");
            sql.addSql("       FILE_DIRECTORY.FDR_SID     as FDR_SID,");
            sql.addSql("       FILE_DIRECTORY.FCB_SID     as FCB_SID,");
            sql.addSql("       FILE_DACCESS_CONF.FDA_AUTH as FDA_AUTH,");
            if (usrKbn == GSConstFile.USER_KBN_GROUP) {
                sql.addSql("       CMN_BELONGM.USR_SID         as USR_SID");
                sql.addSql("     from");
                sql.addSql("       FILE_DIRECTORY");
                sql.addSql("     left join");
                sql.addSql("       FILE_DACCESS_CONF");
                sql.addSql("     on FILE_DIRECTORY.FDR_SID = FILE_DACCESS_CONF.FDR_SID");
                sql.addSql("     left join");
                sql.addSql("       CMN_BELONGM");
                sql.addSql("     on FILE_DACCESS_CONF.USR_SID = CMN_BELONGM.GRP_SID");
                sql.addSql("     where ");
                sql.addSql("       CMN_BELONGM.USR_SID = ?");
            } else {
                sql.addSql("       FILE_DACCESS_CONF.USR_SID  as USR_SID");
                sql.addSql("     from");
                sql.addSql("       FILE_DIRECTORY");
                sql.addSql("     left join");
                sql.addSql("       FILE_DACCESS_CONF");
                sql.addSql("     on FILE_DIRECTORY.FDR_SID = FILE_DACCESS_CONF.FDR_SID");
                sql.addSql("     where ");
                sql.addSql("       FILE_DACCESS_CONF.USR_SID = ?");
            }

            sql.addSql("     and ");
            sql.addSql("       FILE_DACCESS_CONF.USR_KBN = ?");
            sql.addIntValue(usrSid);
            sql.addIntValue(usrKbn);

            if (auth >= 0) {
                sql.addSql("     and ");
                sql.addSql("       FILE_DACCESS_CONF.FDA_AUTH = ?");
                sql.addIntValue(auth);
            }
            sql.addSql(" ) as FILE_DIRECTORY");
            sql.addSql(" on FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql(" where ");
            sql.addSql("   FILE_CABINET.FCB_PERSONAL_FLG = ?");
            if (personalFlg == GSConstFile.CABINET_KBN_PRIVATE) {
                sql.addIntValue(personalFlg);
            } else {
                sql.addIntValue(GSConstFile.CABINET_KBN_PUBLIC);
                sql.addSql(" and ");
                sql.addSql("   FILE_CABINET.FCB_ERRL=?");
                if (personalFlg == GSConstFile.CABINET_KBN_PUBLIC) {
                    sql.addIntValue(GSConstFile.SORT_FOLDER_NOT_USE);
                } else {
                    sql.addIntValue(GSConstFile.SORT_FOLDER_USE);
                }
            }
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.USR_SID is not NULL");
            sql.addSql(" and ");
            sql.addSql("   FILE_CABINET.FCB_JKBN=?");
            sql.addSql(" order by ");
            sql.addSql("   FCB_SORT ASC");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = __getFileCabinetDspFromRs(rs);
                bean.setAccessIconKbn(
                        NullDefault.getString(
                                rs.getString("FDA_AUTH"), GSConstFile.ACCESS_KBN_WRITE));
                ret.add(bean);
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
     * <p>表示順の降順でキャビネット情報を取得する。
     * @param personalFlg 個人キャビネット判定フラグ 0:共有キャビネット 1:個人キャビネット 2:電帳法キャビネット
     * @param isAsc ソートフラグ(true: 昇順[ASC] / false: 降順[DESC])
     * @return List in FILE_CABINETModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileCabinetModel> selectSort(int personalFlg, boolean isAsc)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileCabinetModel> ret = new ArrayList<FileCabinetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FILE_CABINET.FCB_SID as FCB_SID,");
            sql.addSql("   FILE_CABINET.FCB_ACCESS_KBN as FCB_ACCESS_KBN,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_KBN as FCB_CAPA_KBN,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_SIZE as FCB_CAPA_SIZE,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_WARN as FCB_CAPA_WARN,");
            sql.addSql("   FILE_CABINET.FCB_VER_KBN as FCB_VER_KBN,");
            sql.addSql("   FILE_CABINET.FCB_VERALL_KBN as FCB_VERALL_KBN,");
            sql.addSql("   FILE_CABINET.FCB_BIKO as FCB_BIKO,");
            sql.addSql("   FILE_CABINET.FCB_SORT as FCB_SORT,");
            sql.addSql("   FILE_CABINET.FCB_MARK as FCB_MARK,");
            sql.addSql("   FILE_CABINET.FCB_AUID as FCB_AUID,");
            sql.addSql("   FILE_CABINET.FCB_ADATE as FCB_ADATE,");
            sql.addSql("   FILE_CABINET.FCB_EUID as FCB_EUID,");
            sql.addSql("   FILE_CABINET.FCB_EDATE as FCB_EDATE,");
            sql.addSql("   FILE_CABINET.FCB_PERSONAL_FLG as FCB_PERSONAL_FLG,");
            sql.addSql("   FILE_CABINET.FCB_OWNER_SID as FCB_OWNER_SID,");
            sql.addSql("   FILE_CABINET.FCB_ERRL as FCB_ERRL,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER as FCB_SORT_FOLDER,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER1 as FCB_SORT_FOLDER1,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER2 as FCB_SORT_FOLDER2,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER3 as FCB_SORT_FOLDER3,");
            sql.addSql("   FILE_CABINET.FCB_JKBN as FCB_JKBN,");
            if (personalFlg == GSConstFile.CABINET_KBN_PRIVATE) {
                sql.addSql("   (");
                sql.addSql("     CMN_USRM_INF.USI_SEI || ' ' || CMN_USRM_INF.USI_MEI");
                sql.addSql("   )  as FCB_NAME");
                sql.addSql(" from (");
                sql.addSql("   select ");
                sql.addSql("     FCB_SID,");
                sql.addSql("     FCB_NAME,");
                sql.addSql("     FCB_ACCESS_KBN,");
                sql.addSql("     FCB_CAPA_KBN,");
                sql.addSql("     FCB_CAPA_SIZE,");
                sql.addSql("     FCB_CAPA_WARN,");
                sql.addSql("     FCB_VER_KBN,");
                sql.addSql("     FCB_VERALL_KBN,");
                sql.addSql("     FCB_BIKO,");
                sql.addSql("     FCB_SORT,");
                sql.addSql("     FCB_MARK,");
                sql.addSql("     FCB_AUID,");
                sql.addSql("     FCB_ADATE,");
                sql.addSql("     FCB_EUID,");
                sql.addSql("     FCB_EDATE,");
                sql.addSql("     FCB_PERSONAL_FLG,");
                sql.addSql("     FCB_OWNER_SID,");
                sql.addSql("     FCB_ERRL,");
                sql.addSql("     FCB_SORT_FOLDER,");
                sql.addSql("     FCB_SORT_FOLDER1,");
                sql.addSql("     FCB_SORT_FOLDER2,");
                sql.addSql("     FCB_SORT_FOLDER3,");
                sql.addSql("     FCB_JKBN");
                sql.addSql("   from ");
                sql.addSql("     FILE_CABINET");
                sql.addSql("   where ");
                sql.addSql("     FCB_PERSONAL_FLG=?");
                sql.addSql("   and");
                sql.addSql("     FCB_OWNER_SID is NOT NULL");
                sql.addSql(" ) as FILE_CABINET");
                sql.addSql(" left join");
                sql.addSql("   CMN_USRM_INF");
                sql.addSql(" on CMN_USRM_INF.USR_SID = FILE_CABINET.FCB_OWNER_SID");
                sql.addIntValue(GSConstFile.CABINET_KBN_PRIVATE);
                sql.addSql(" where ");
                sql.addSql("   FILE_CABINET.FCB_JKBN = ?");
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            } else {
                sql.addSql("   FILE_CABINET.FCB_NAME as FCB_NAME");
                sql.addSql(" from ");
                sql.addSql("   FILE_CABINET");
                sql.addSql(" where ");
                sql.addSql("   FILE_CABINET.FCB_JKBN = ?");
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);
                sql.addSql(" and ");
                sql.addSql("   FILE_CABINET.FCB_PERSONAL_FLG = ?");
                sql.addIntValue(GSConstFile.CABINET_KBN_PUBLIC);
                if (personalFlg == GSConstFile.CABINET_KBN_ERRL) {
                    sql.addSql(" and ");
                    sql.addSql("   FILE_CABINET.FCB_ERRL = ?");
                    sql.addIntValue(GSConstFile.ERRL_KBN_ON);
                } else {
                    sql.addSql(" and ");
                    sql.addSql("   FILE_CABINET.FCB_ERRL = ?");
                    sql.addIntValue(GSConstFile.ERRL_KBN_OFF);
                }
            }
            sql.addSql(" order by ");
            sql.addSql("   FCB_SORT " + (isAsc ? "ASC" : "DESC"));

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileCabinetFromRs(rs));
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
     * <p>指定したキャビネット情報を取得する。個人キャビネットの場合、所有ユーザ名をキャビネット名とする。
     * @param fcbSid キャビネットSID
     * @param personalFlg 個人キャビネット判定フラグ 0:共有キャビネット 1:個人キャビネット
     * @return List in FILE_CABINETModel
     * @throws SQLException SQL実行例外
     */
    public FileCabinetModel getPersonalCab(int fcbSid, int personalFlg)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileCabinetModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FILE_CABINET.FCB_SID as FCB_SID,");
            sql.addSql("   FILE_CABINET.FCB_ACCESS_KBN as FCB_ACCESS_KBN,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_KBN as FCB_CAPA_KBN,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_SIZE as FCB_CAPA_SIZE,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_WARN as FCB_CAPA_WARN,");
            sql.addSql("   FILE_CABINET.FCB_VER_KBN as FCB_VER_KBN,");
            sql.addSql("   FILE_CABINET.FCB_VERALL_KBN as FCB_VERALL_KBN,");
            sql.addSql("   FILE_CABINET.FCB_BIKO as FCB_BIKO,");
            sql.addSql("   FILE_CABINET.FCB_SORT as FCB_SORT,");
            sql.addSql("   FILE_CABINET.FCB_MARK as FCB_MARK,");
            sql.addSql("   FILE_CABINET.FCB_AUID as FCB_AUID,");
            sql.addSql("   FILE_CABINET.FCB_ADATE as FCB_ADATE,");
            sql.addSql("   FILE_CABINET.FCB_EUID as FCB_EUID,");
            sql.addSql("   FILE_CABINET.FCB_EDATE as FCB_EDATE,");
            sql.addSql("   FILE_CABINET.FCB_PERSONAL_FLG as FCB_PERSONAL_FLG,");
            sql.addSql("   FILE_CABINET.FCB_OWNER_SID as FCB_OWNER_SID,");
            sql.addSql("   FILE_CABINET.FCB_ERRL as FCB_ERRL,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER as FCB_SORT_FOLDER,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER1 as FCB_SORT_FOLDER1,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER2 as FCB_SORT_FOLDER2,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER3 as FCB_SORT_FOLDER3,");
            sql.addSql("   FILE_CABINET.FCB_JKBN as FCB_JKBN,");
            if (personalFlg == GSConstFile.CABINET_KBN_PRIVATE) {
                sql.addSql("   (");
                sql.addSql("     CMN_USRM_INF.USI_SEI || ' ' || CMN_USRM_INF.USI_MEI");
                sql.addSql("   )  as FCB_NAME");
                sql.addSql(" from (");
                sql.addSql("   select ");
                sql.addSql("     FCB_SID,");
                sql.addSql("     FCB_NAME,");
                sql.addSql("     FCB_ACCESS_KBN,");
                sql.addSql("     FCB_CAPA_KBN,");
                sql.addSql("     FCB_CAPA_SIZE,");
                sql.addSql("     FCB_CAPA_WARN,");
                sql.addSql("     FCB_VER_KBN,");
                sql.addSql("     FCB_VERALL_KBN,");
                sql.addSql("     FCB_BIKO,");
                sql.addSql("     FCB_SORT,");
                sql.addSql("     FCB_MARK,");
                sql.addSql("     FCB_AUID,");
                sql.addSql("     FCB_ADATE,");
                sql.addSql("     FCB_EUID,");
                sql.addSql("     FCB_EDATE,");
                sql.addSql("     FCB_PERSONAL_FLG,");
                sql.addSql("     FCB_OWNER_SID,");
                sql.addSql("     FCB_ERRL,");
                sql.addSql("     FCB_SORT_FOLDER,");
                sql.addSql("     FCB_SORT_FOLDER1,");
                sql.addSql("     FCB_SORT_FOLDER2,");
                sql.addSql("     FCB_SORT_FOLDER3,");
                sql.addSql("     FCB_JKBN");
                sql.addSql("   from ");
                sql.addSql("     FILE_CABINET");
                sql.addSql("   where ");
                sql.addSql("     FCB_PERSONAL_FLG=?");
                sql.addSql("   and");
                sql.addSql("     FCB_OWNER_SID is NOT NULL");
                sql.addSql(" ) as FILE_CABINET");
                sql.addSql(" left join");
                sql.addSql("   CMN_USRM_INF");
                sql.addSql(" on CMN_USRM_INF.USR_SID = FILE_CABINET.FCB_OWNER_SID");
                sql.addIntValue(GSConstFile.CABINET_KBN_PRIVATE);
                sql.addSql(" where");
                sql.addSql("   FCB_SID=?");
                sql.addIntValue(fcbSid);
            } else {
                sql.addSql("   FILE_CABINET.FCB_NAME as FCB_NAME");
                sql.addSql(" from ");
                sql.addSql("   FILE_CABINET");
                sql.addSql(" where ");
                sql.addSql("   FILE_CABINET.FCB_PERSONAL_FLG = ?");
                sql.addIntValue(GSConstFile.CABINET_KBN_PUBLIC);
                sql.addSql(" and");
                sql.addSql("   FCB_SID=?");
                sql.addIntValue(fcbSid);
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getFileCabinetFromRs(rs);
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
     * <p>キャビネット登録件数を取得する。
     * @return List in FILE_CABINETModel
     * @throws SQLException SQL実行例外
     */
    public int countCabinet() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   FILE_CABINET");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <p>Select FILE_CABINET
     * @param fcbSid FCB_SID
     * @return FILE_CABINETModel
     * @throws SQLException SQL実行例外
     */
    public FileCabinetModel select(int fcbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileCabinetModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FCB_NAME,");
            sql.addSql("   FCB_ACCESS_KBN,");
            sql.addSql("   FCB_CAPA_KBN,");
            sql.addSql("   FCB_CAPA_SIZE,");
            sql.addSql("   FCB_CAPA_WARN,");
            sql.addSql("   FCB_VER_KBN,");
            sql.addSql("   FCB_VERALL_KBN,");
            sql.addSql("   FCB_BIKO,");
            sql.addSql("   FCB_SORT,");
            sql.addSql("   FCB_MARK,");
            sql.addSql("   FCB_AUID,");
            sql.addSql("   FCB_ADATE,");
            sql.addSql("   FCB_EUID,");
            sql.addSql("   FCB_EDATE,");
            sql.addSql("   FCB_PERSONAL_FLG,");
            sql.addSql("   FCB_OWNER_SID,");
            sql.addSql("   FCB_ERRL,");
            sql.addSql("   FCB_SORT_FOLDER,");
            sql.addSql("   FCB_SORT_FOLDER1,");
            sql.addSql("   FCB_SORT_FOLDER2,");
            sql.addSql("   FCB_SORT_FOLDER3,");
            sql.addSql("   FCB_JKBN");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getFileCabinetFromRs(rs);
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
     * <p>Select FILE_CABINET
     * @param fcbSidList FCB_SIDリスト
     * @return FILE_CABINETModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileCabinetModel> select(List<Integer> fcbSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileCabinetModel> ret = new ArrayList<FileCabinetModel>();
        con = getCon();

        if (fcbSidList == null || fcbSidList.size() == 0) {
            return ret; // SIDが無い場合、空配列を返す
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FCB_NAME,");
            sql.addSql("   FCB_ACCESS_KBN,");
            sql.addSql("   FCB_CAPA_KBN,");
            sql.addSql("   FCB_CAPA_SIZE,");
            sql.addSql("   FCB_CAPA_WARN,");
            sql.addSql("   FCB_VER_KBN,");
            sql.addSql("   FCB_VERALL_KBN,");
            sql.addSql("   FCB_BIKO,");
            sql.addSql("   FCB_SORT,");
            sql.addSql("   FCB_MARK,");
            sql.addSql("   FCB_AUID,");
            sql.addSql("   FCB_ADATE,");
            sql.addSql("   FCB_EUID,");
            sql.addSql("   FCB_EDATE,");
            sql.addSql("   FCB_PERSONAL_FLG,");
            sql.addSql("   FCB_OWNER_SID,");
            sql.addSql("   FCB_ERRL,");
            sql.addSql("   FCB_SORT_FOLDER,");
            sql.addSql("   FCB_SORT_FOLDER1,");
            sql.addSql("   FCB_SORT_FOLDER2,");
            sql.addSql("   FCB_SORT_FOLDER3,");
            sql.addSql("   FCB_JKBN");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" where ");

            sql.addSql("   FCB_SID in (");
            for (int i = 0; i < fcbSidList.size(); i++) {
                if (i > 0) {
                    if (i % 1000 == 0) {
                        sql.addSql(" ) or FCB_SID in ("); // 1000件区切りで条件文を分ける
                    } else {
                        sql.addSql(", ");
                    }
                }
                sql.addSql(String.valueOf(fcbSidList.get(i)));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                FileCabinetModel fcbMdl = __getFileCabinetFromRs(rs);
                ret.add(fcbMdl);
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
     * <p>指定ユーザのマイキャビネットを取得します。
     *@param usrSid ユーザSID
     *@return SIDで指定したユーザの全マイキャビネット
     *@throws SQLException SQL実行例外
     * */
    public FileCabinetDspModel getMyCabinet(int usrSid)
    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileCabinetDspModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FILE_CABINET.FCB_SID as FCB_SID,");
            sql.addSql("   FILE_CABINET.FCB_ACCESS_KBN as FCB_ACCESS_KBN,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_KBN as FCB_CAPA_KBN,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_SIZE as FCB_CAPA_SIZE,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_WARN as FCB_CAPA_WARN,");
            sql.addSql("   FILE_CABINET.FCB_VER_KBN as FCB_VER_KBN,");
            sql.addSql("   FILE_CABINET.FCB_VERALL_KBN as FCB_VERALL_KBN,");
            sql.addSql("   FILE_CABINET.FCB_BIKO as FCB_BIKO,");
            sql.addSql("   FILE_CABINET.FCB_SORT as FCB_SORT,");
            sql.addSql("   FILE_CABINET.FCB_MARK as FCB_MARK,");
            sql.addSql("   FILE_CABINET.FCB_AUID as FCB_AUID,");
            sql.addSql("   FILE_CABINET.FCB_ADATE as FCB_ADATE,");
            sql.addSql("   FILE_CABINET.FCB_EUID as FCB_EUID,");
            sql.addSql("   FILE_CABINET.FCB_EDATE as FCB_EDATE,");
            sql.addSql("   FILE_CABINET.FCB_PERSONAL_FLG as FCB_PERSONAL_FLG,");
            sql.addSql("   FILE_CABINET.FCB_OWNER_SID as FCB_OWNER_SID,");
            sql.addSql("   FILE_CABINET.FCB_ERRL as FCB_ERRL,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER as FCB_SORT_FOLDER,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER1 as FCB_SORT_FOLDER1,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER2 as FCB_SORT_FOLDER2,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER2 as FCB_SORT_FOLDER3,");
            sql.addSql("   FILE_CABINET.FCB_JKBN as FCB_JKBN,");
            sql.addSql("   (");
            sql.addSql("     CMN_USRM_INF.USI_SEI || ' ' || CMN_USRM_INF.USI_MEI");
            sql.addSql("   )  as FCB_NAME");
            sql.addSql(" from (");
            sql.addSql("   select ");
            sql.addSql("     FCB_SID,");
            sql.addSql("     FCB_NAME,");
            sql.addSql("     FCB_ACCESS_KBN,");
            sql.addSql("     FCB_CAPA_KBN,");
            sql.addSql("     FCB_CAPA_SIZE,");
            sql.addSql("     FCB_CAPA_WARN,");
            sql.addSql("     FCB_VER_KBN,");
            sql.addSql("     FCB_VERALL_KBN,");
            sql.addSql("     FCB_BIKO,");
            sql.addSql("     FCB_SORT,");
            sql.addSql("     FCB_MARK,");
            sql.addSql("     FCB_AUID,");
            sql.addSql("     FCB_ADATE,");
            sql.addSql("     FCB_EUID,");
            sql.addSql("     FCB_EDATE,");
            sql.addSql("     FCB_PERSONAL_FLG,");
            sql.addSql("     FCB_OWNER_SID,");
            sql.addSql("     FCB_ERRL,");
            sql.addSql("     FCB_SORT_FOLDER,");
            sql.addSql("     FCB_SORT_FOLDER1,");
            sql.addSql("     FCB_SORT_FOLDER2,");
            sql.addSql("     FCB_SORT_FOLDER3,");
            sql.addSql("     FCB_JKBN");
            sql.addSql("   from ");
            sql.addSql("     FILE_CABINET");
            sql.addSql("   where ");
            sql.addSql("     FCB_PERSONAL_FLG=?");
            sql.addSql("   and");
            sql.addSql("     FCB_OWNER_SID is NOT NULL");
            sql.addSql(" ) as FILE_CABINET");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" on CMN_USRM_INF.USR_SID = FILE_CABINET.FCB_OWNER_SID");
            sql.addSql("   where ");
            sql.addSql("     FCB_OWNER_SID=?");
            sql.addSql("     and");
            sql.addSql("     FCB_JKBN=?");
            sql.addIntValue(GSConstFile.CABINET_KBN_PRIVATE);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getFileCabinetDspFromRs(rs);
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
     * <p>アクセス制限の無いキャビネットを取得
     * @return List in FILE_CABINETModel
     * @param personalFlg 個人キャビネット判定フラグ 0:共有キャビネット 1:個人キャビネット
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileCabinetDspModel> getFreeAccessCabinet(int userSid, int personalFlg)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileCabinetDspModel> ret = new ArrayList<FileCabinetDspModel>();
        FileCabinetDspModel bean = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FCB_NAME,");
            sql.addSql("   FCB_ACCESS_KBN,");
            sql.addSql("   FCB_CAPA_KBN,");
            sql.addSql("   FCB_CAPA_SIZE,");
            sql.addSql("   FCB_CAPA_WARN,");
            sql.addSql("   FCB_VER_KBN,");
            sql.addSql("   FCB_VERALL_KBN,");
            sql.addSql("   FCB_BIKO,");
            sql.addSql("   FCB_SORT,");
            sql.addSql("   FCB_MARK,");
            sql.addSql("   FCB_AUID,");
            sql.addSql("   FCB_ADATE,");
            sql.addSql("   FCB_EUID,");
            sql.addSql("   FCB_EDATE,");
            sql.addSql("   FCB_PERSONAL_FLG,");
            sql.addSql("   FCB_OWNER_SID,");
            sql.addSql("   FCB_ERRL,");
            sql.addSql("   FCB_SORT_FOLDER,");
            sql.addSql("   FCB_SORT_FOLDER1,");
            sql.addSql("   FCB_SORT_FOLDER2,");
            sql.addSql("   FCB_SORT_FOLDER3,");
            sql.addSql("   FCB_JKBN");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" where ");
            sql.addSql("   FCB_PERSONAL_FLG=?");
            if (personalFlg == GSConstFile.CABINET_KBN_PRIVATE) {
                sql.addIntValue(personalFlg);
                sql.addSql(" and ");
                sql.addSql("   FCB_OWNER_SID=?");
                sql.addIntValue(userSid);
            } else {
                sql.addIntValue(GSConstFile.CABINET_KBN_PUBLIC);
                sql.addSql(" and ");
                sql.addSql("   FCB_ERRL=?");
                if (personalFlg == GSConstFile.CABINET_KBN_PUBLIC) {
                    sql.addIntValue(GSConstFile.SORT_FOLDER_NOT_USE);
                } else {
                    sql.addIntValue(GSConstFile.SORT_FOLDER_USE);
                }
            }
            sql.addSql(" and ");
            sql.addSql("   FCB_ACCESS_KBN=?");
            sql.addIntValue(GSConstFile.ACCESS_KBN_OFF);
            sql.addSql(" and ");
            sql.addSql("   FCB_JKBN=?");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = __getFileCabinetDspFromRs(rs);
                bean.setAccessIconKbn(GSConstFile.ACCESS_KBN_WRITE);
                ret.add(bean);
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
     * <p>ユーザが管理者に設定されているキャビネットを取得
     * @param userSid ユーザSID
     * @param personalFlg 個人キャビネット判定フラグ 0:共有キャビネット 1:個人キャビネット
     * @return List in FILE_CABINETModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileCabinetDspModel> getAdminCabinet(
            int userSid, int personalFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileCabinetDspModel> ret = new ArrayList<FileCabinetDspModel>();
        FileCabinetDspModel bean = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_CABINET.FCB_SID,");
            sql.addSql("   FILE_CABINET.FCB_NAME,");
            sql.addSql("   FILE_CABINET.FCB_ACCESS_KBN,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_KBN,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_SIZE,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_WARN,");
            sql.addSql("   FILE_CABINET.FCB_VER_KBN,");
            sql.addSql("   FILE_CABINET.FCB_VERALL_KBN,");
            sql.addSql("   FILE_CABINET.FCB_BIKO,");
            sql.addSql("   FILE_CABINET.FCB_SORT,");
            sql.addSql("   FILE_CABINET.FCB_MARK,");
            sql.addSql("   FILE_CABINET.FCB_AUID,");
            sql.addSql("   FILE_CABINET.FCB_ADATE,");
            sql.addSql("   FILE_CABINET.FCB_EUID,");
            sql.addSql("   FILE_CABINET.FCB_EDATE,");
            sql.addSql("   FILE_CABINET.FCB_PERSONAL_FLG,");
            sql.addSql("   FILE_CABINET.FCB_OWNER_SID,");
            sql.addSql("   FILE_CABINET.FCB_ERRL,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER1,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER2,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER3,");
            sql.addSql("   FILE_CABINET.FCB_JKBN");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET,");
            sql.addSql("   FILE_CABINET_ADMIN");
            sql.addSql(" where ");
            sql.addSql("   FILE_CABINET_ADMIN.USR_SID=?");
            sql.addSql(" and ");
            sql.addSql("   FILE_CABINET.FCB_SID=FILE_CABINET_ADMIN.FCB_SID");
            sql.addSql(" and ");
            sql.addSql("   FILE_CABINET.FCB_PERSONAL_FLG=?");
            sql.addIntValue(userSid);
            if (personalFlg == GSConstFile.CABINET_KBN_PRIVATE) {
                sql.addIntValue(personalFlg);
                sql.addSql(" and ");
                sql.addSql("   FILE_CABINET.FCB_OWNER_SID=?");
                sql.addIntValue(userSid);
            } else {
                sql.addIntValue(GSConstFile.CABINET_KBN_PUBLIC);
                sql.addSql(" and ");
                sql.addSql("   FILE_CABINET.FCB_ERRL=?");
                if (personalFlg == GSConstFile.CABINET_KBN_PUBLIC) {
                    sql.addIntValue(GSConstFile.SORT_FOLDER_NOT_USE);
                } else {
                    sql.addIntValue(GSConstFile.SORT_FOLDER_USE);
                }
            }
            sql.addSql(" and ");
            sql.addSql("   FILE_CABINET.FCB_JKBN=?");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = __getFileCabinetDspFromRs(rs);
                bean.setAccessIconKbn(GSConstFile.ACCESS_KBN_WRITE);
                ret.add(bean);
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
     * <p>ユーザがアクセス設定に登録されているキャビネットを取得
     * @param userSid ユーザSID
     * @param auth 権限 -1の場合は検索条件に含めない
     * @param personalFlg 個人キャビネット判定フラグ 0:共有キャビネット 1:個人キャビネット
     * @return List in FILE_CABINETModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileCabinetDspModel> getCanAccessCabinet(
            int userSid, int auth, int personalFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileCabinetDspModel> ret = new ArrayList<FileCabinetDspModel>();
        FileCabinetDspModel bean = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_CABINET.FCB_SID,");
            sql.addSql("   FILE_CABINET.FCB_NAME,");
            sql.addSql("   FILE_CABINET.FCB_ACCESS_KBN,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_KBN,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_SIZE,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_WARN,");
            sql.addSql("   FILE_CABINET.FCB_VER_KBN,");
            sql.addSql("   FILE_CABINET.FCB_VERALL_KBN,");
            sql.addSql("   FILE_CABINET.FCB_BIKO,");
            sql.addSql("   FILE_CABINET.FCB_SORT,");
            sql.addSql("   FILE_CABINET.FCB_MARK,");
            sql.addSql("   FILE_CABINET.FCB_AUID,");
            sql.addSql("   FILE_CABINET.FCB_ADATE,");
            sql.addSql("   FILE_CABINET.FCB_EUID,");
            sql.addSql("   FILE_CABINET.FCB_EDATE,");
            sql.addSql("   FILE_CABINET.FCB_PERSONAL_FLG,");
            sql.addSql("   FILE_CABINET.FCB_OWNER_SID,");
            sql.addSql("   FILE_CABINET.FCB_ERRL,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER1,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER2,");
            sql.addSql("   FILE_CABINET.FCB_SORT_FOLDER3,");
            sql.addSql("   FILE_CABINET.FCB_JKBN,");
            sql.addSql("   FILE_ACCESS_CONF.FAA_AUTH");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET,");
            sql.addSql("   FILE_ACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   FILE_CABINET.FCB_PERSONAL_FLG=?");
            if (personalFlg == GSConstFile.CABINET_KBN_PRIVATE) {
                sql.addIntValue(personalFlg);
            } else {
                sql.addIntValue(GSConstFile.CABINET_KBN_PUBLIC);
                sql.addSql(" and ");
                sql.addSql("   FILE_CABINET.FCB_ERRL=?");
                if (personalFlg == GSConstFile.CABINET_KBN_PUBLIC) {
                    sql.addIntValue(GSConstFile.SORT_FOLDER_NOT_USE);
                } else {
                    sql.addIntValue(GSConstFile.SORT_FOLDER_USE);
                }
            }
            sql.addSql(" and ");
            sql.addSql("   FILE_ACCESS_CONF.USR_SID=?");
            sql.addSql(" and ");
            sql.addSql("   FILE_ACCESS_CONF.USR_KBN=?");
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            if (auth != -1) {
                sql.addSql(" and ");
                sql.addSql("   FILE_ACCESS_CONF.FAA_AUTH=?");
                sql.addIntValue(auth);
            }
            sql.addSql(" and ");
            sql.addSql("   FILE_CABINET.FCB_SID=FILE_ACCESS_CONF.FCB_SID");
            if (personalFlg == Integer.parseInt(GSConstFile.DSP_CABINET_PRIVATE)) {
                sql.addSql(" and ");
                sql.addSql("   FILE_CABINET.FCB_OWNER_SID=?");
                sql.addIntValue(userSid);
            }
            sql.addSql(" and ");
            sql.addSql("   FILE_CABINET.FCB_JKBN=?");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = __getFileCabinetDspFromRs(rs);
                bean.setAccessIconKbn(rs.getString("FAA_AUTH"));
                ret.add(bean);
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
     * <p>ユーザが所属するグループにアクセス設定登録されているキャビネットを取得
     * @param userSid ユーザSID
     * @param auth 権限 -1の場合は検索条件に含めない
     * @param personalFlg 個人キャビネット判定フラグ 0:共有キャビネット 1:個人キャビネット
     * @return List in FILE_CABINETModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileCabinetDspModel> getCanAccessGpCabinet(
            int userSid, int auth, int personalFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileCabinetDspModel> ret = new ArrayList<FileCabinetDspModel>();
        FileCabinetDspModel bean = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_CABINET.FCB_SID,");
            sql.addSql("   FCB_NAME,");
            sql.addSql("   FCB_ACCESS_KBN,");
            sql.addSql("   FCB_CAPA_KBN,");
            sql.addSql("   FCB_CAPA_SIZE,");
            sql.addSql("   FCB_CAPA_WARN,");
            sql.addSql("   FCB_VER_KBN,");
            sql.addSql("   FCB_VERALL_KBN,");
            sql.addSql("   FCB_BIKO,");
            sql.addSql("   FCB_SORT,");
            sql.addSql("   FCB_MARK,");
            sql.addSql("   FCB_AUID,");
            sql.addSql("   FCB_ADATE,");
            sql.addSql("   FCB_EUID,");
            sql.addSql("   FCB_EDATE,");
            sql.addSql("   FCB_PERSONAL_FLG,");
            sql.addSql("   FCB_OWNER_SID,");
            sql.addSql("   FCB_ERRL,");
            sql.addSql("   FCB_SORT_FOLDER,");
            sql.addSql("   FCB_SORT_FOLDER1,");
            sql.addSql("   FCB_SORT_FOLDER2,");
            sql.addSql("   FCB_SORT_FOLDER3,");
            sql.addSql("   FCB_JKBN,");
            sql.addSql("   FILE_ACCESS_CONF.FAA_AUTH");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET,");
            sql.addSql("   FILE_ACCESS_CONF");
            sql.addSql(" where ");
            sql.addSql("   exists (");
            sql.addSql("     select 1 from");
            sql.addSql("       CMN_GROUPM,");
            sql.addSql("       CMN_BELONGM");
            sql.addSql("     where");
            sql.addSql("       CMN_BELONGM.USR_SID = ?");
            sql.addSql("     and");
            sql.addSql("       CMN_BELONGM.GRP_SID = CMN_GROUPM.GRP_SID");
            sql.addSql("     and");
            sql.addSql("       FILE_ACCESS_CONF.USR_SID = CMN_GROUPM.GRP_SID");
            sql.addSql("   ) ");
            sql.addSql(" and ");
            sql.addSql("   FCB_PERSONAL_FLG=?");
            sql.addSql(" and ");
            sql.addSql("   FILE_ACCESS_CONF.USR_KBN=?");

            sql.addIntValue(userSid);
            if (personalFlg == GSConstFile.CABINET_KBN_PRIVATE) {
                sql.addIntValue(personalFlg);
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            } else {
                sql.addIntValue(GSConstFile.CABINET_KBN_PUBLIC);
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                sql.addSql(" and ");
                sql.addSql("   FILE_CABINET.FCB_ERRL=?");
                if (personalFlg == GSConstFile.CABINET_KBN_PUBLIC) {
                    sql.addIntValue(GSConstFile.SORT_FOLDER_NOT_USE);
                } else {
                    sql.addIntValue(GSConstFile.SORT_FOLDER_USE);
                }
            }

            if (auth != -1) {
                sql.addSql(" and ");
                sql.addSql("   FILE_ACCESS_CONF.FAA_AUTH=?");
                sql.addIntValue(auth);
            }
            sql.addSql(" and ");
            sql.addSql("   FILE_CABINET.FCB_SID=FILE_ACCESS_CONF.FCB_SID");
            if (personalFlg == Integer.parseInt(GSConstFile.DSP_CABINET_PRIVATE)) {
                sql.addSql(" and ");
                sql.addSql("   FILE_CABINET.FCB_OWNER_SID=?");
                sql.addIntValue(userSid);
            }
            sql.addSql(" and ");
            sql.addSql("   FCB_JKBN=?");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = __getFileCabinetDspFromRs(rs);
                bean.setAccessIconKbn(rs.getString("FAA_AUTH"));
                ret.add(bean);
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
     * <p>キャビネットSIDを指定しファイルのサイズ合計を取得する
     * @param fcbSid キャビネットSID
     * @return 使用サイズ合計
     * @throws SQLException SQL実行例外
     */
    public BigDecimal getCabinetUsedSize(int fcbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        BigDecimal ret = new BigDecimal(0);
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_DIRECTORY.FCB_SID,");
            sql.addSql("   sum(FILE_FILE_BIN.FFL_FILE_SIZE) as SUM_SIZE");
            sql.addSql(" from");
            sql.addSql("   FILE_DIRECTORY,");
            sql.addSql("   FILE_FILE_BIN");
            sql.addSql(" where ");
            sql.addSql("   FILE_DIRECTORY.FCB_SID=?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN=?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = FILE_FILE_BIN.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = FILE_FILE_BIN.FDR_VERSION");
            sql.addSql(" group by ");
            sql.addSql("   FILE_DIRECTORY.FCB_SID");
            sql.addSql("");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.addIntValue(GSConstFile.DIRECTORY_FILE);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getBigDecimal("SUM_SIZE");
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
     * <p>キャビネットSIDとバイナリSIDの組み合わせが存在するかチェックする
     * @param fcbSid キャビネットSID
     * @param binSid バイナリSID
     * @return true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckFileIcon(int fcbSid, Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");
            sql.addSql(" and ");
            sql.addSql("   FCB_MARK=?");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cnt = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return cnt > 0;
    }

    /**
     * <br>[機  能] 論理削除されているキャビネットのSIDを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param cabinetKbn キャビネット種別
     * @return true:論理削除されている/false:論理削除されていない
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, Long> selectLogicalDelCabinet(int cabinetKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Integer, Long> result = new HashMap<Integer, Long>();
        con = getCon();
        UDate date = new UDate();
        date.addYear(-10);

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FCB_SID,");
            sql.addSql("   FCB_MARK");
            sql.addSql(" from ");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" where ");
            sql.addSql("   FCB_JKBN = ?");
            sql.addSql(" and ");
            sql.addSql("   FCB_ERRL = ?");
            sql.addIntValue(GSConstFile.JTKBN_DELETE);
            sql.addIntValue(cabinetKbn);
            if (cabinetKbn == GSConstFile.SELECT_CABINET_ERRL) {
                sql.addSql("   and (");
                sql.addSql("     not exists (");
                sql.addSql("       select 1");
                sql.addSql("       from ");
                sql.addSql("         FILE_DIRECTORY");
                sql.addSql("       where ");
                sql.addSql("         FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");
                sql.addSql("       and ");
                sql.addSql("         FILE_DIRECTORY.FDR_KBN = ?");
                sql.addIntValue(GSConstFile.DIRECTORY_FILE);
                sql.addSql("     )");
                
                sql.addSql("     or (");
                sql.addSql("       not exists (");
                sql.addSql("         select 1");
                sql.addSql("         from ");
                sql.addSql("           FILE_DIRECTORY");
                sql.addSql("         where ");
                sql.addSql("           FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");
                sql.addSql("         and ");
                sql.addSql("           FILE_DIRECTORY.FDR_KBN = ?");
                sql.addSql("         and ");
                sql.addSql("           FILE_DIRECTORY.FDR_JTKBN = ?");
                sql.addSql("       )");
                sql.addIntValue(GSConstFile.DIRECTORY_FILE);
                sql.addIntValue(GSConstFile.JTKBN_DELETE);
                sql.addSql("       and not exists (");
                sql.addSql("         select 1");
                sql.addSql("         from ");
                sql.addSql("           FILE_DIRECTORY,");
                sql.addSql("           (");
                sql.addSql("             select ");
                sql.addSql("               FDR_SID,");
                sql.addSql("               max(FDR_VERSION) as MAXVERSION");
                sql.addSql("             from ");
                sql.addSql("               FILE_DIRECTORY DIR");
                sql.addSql("           group by FDR_SID) DIR_MAXVERSION");
                sql.addSql("         where ");
                sql.addSql("           FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");
                sql.addSql("         and ");
                sql.addSql("           FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
                sql.addSql("         and ");
                sql.addSql("           FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
                sql.addSql("         and ");
                sql.addSql("           FILE_DIRECTORY.FDR_JTKBN = ?");
                sql.addSql("         and ");
                sql.addSql("           FILE_DIRECTORY.FDR_KBN = ?");
                sql.addSql("         and ");
                sql.addSql("           FILE_DIRECTORY.FDR_EDATE > ?");
                sql.addSql("       )");
                sql.addSql("     )");
                sql.addSql("   )");
                sql.addIntValue(GSConstFile.JTKBN_NORMAL);
                sql.addIntValue(GSConstFile.DIRECTORY_FILE);
                sql.addDateValue(date);
            }
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                result.put(rs.getInt("FCB_SID"), rs.getLong("FCB_MARK"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }
    
    

    /**
     * <br>[機  能] キャビネットが論理削除されているかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @return true:論理削除されている/false:論理削除されていない
     * @throws SQLException SQL実行例外
     */
    public boolean isLogicalDelCabinet(int fcbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select 1");
            sql.addSql(" from ");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   FCB_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.addIntValue(GSConstFile.JTKBN_DELETE);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <br>[機  能] キャビネットアイコンのバイナリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param fcbSid キャビネットSID
     * @return ArrayList in String
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getFcbBinSids(int fcbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FCB_MARK");
            sql.addSql(" from ");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FCB_MARK > 0");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(String.valueOf(rs.getLong("FCB_MARK")));
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
     * <br>[機  能] ソート順の最大値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param personalFlg キャビネット区分 0:共有キャビネット, 1:個人キャビネット
     * @param errlFlg 電帳法区分 0:通常キャビネット, 1:電帳法キャビネット
     * @return ArrayList in String
     * @throws SQLException SQL実行例外
     */
    public int getMaxSort(int personalFlg, int errlFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   max(FCB_SORT) as SORT");
            sql.addSql(" from ");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" where ");
            sql.addSql("   FCB_JKBN = ?");
            sql.addSql(" and ");
            sql.addSql("   FCB_PERSONAL_FLG = ?");
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            sql.addIntValue(personalFlg);
            if (personalFlg != GSConstFile.CABINET_KBN_PRIVATE) {
                sql.addSql(" and ");
                sql.addSql("   FCB_ERRL = ?");
                sql.addIntValue(errlFlg);
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("SORT");
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
     * <p>Delete FILE_CABINET
     * @param fcbSid FCB_SID
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int fcbSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fcbSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Delete FILE_CABINET
     * @param fcbSids FCB_SIDリスト
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(ArrayList<Integer> fcbSids) throws SQLException {
        
        int count = 0;
        if (fcbSids == null || fcbSids.isEmpty()) {
            return count;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" where ");
            sql.addSql("   FCB_SID in (");
            boolean firstFlg = true;
            for (int fcbSid : fcbSids) {
                if (!firstFlg) {
                    sql.addSql("   ,");
                } else {
                    firstFlg = false;
                }
                sql.addSql("   ?");
                sql.addIntValue(fcbSid);
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }


    /**
     * <p>Create FILE_CABINET Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileCabinetModel
     * @throws SQLException SQL実行例外
     */
    private FileCabinetModel __getFileCabinetFromRs(ResultSet rs) throws SQLException {
        FileCabinetModel bean = new FileCabinetModel();
        bean.setFcbSid(rs.getInt("FCB_SID"));
        bean.setFcbName(rs.getString("FCB_NAME"));
        bean.setFcbAccessKbn(rs.getInt("FCB_ACCESS_KBN"));
        bean.setFcbCapaKbn(rs.getInt("FCB_CAPA_KBN"));
        bean.setFcbCapaSize(rs.getInt("FCB_CAPA_SIZE"));
        bean.setFcbCapaWarn(rs.getInt("FCB_CAPA_WARN"));
        bean.setFcbVerKbn(rs.getInt("FCB_VER_KBN"));
        bean.setFcbVerallKbn(rs.getInt("FCB_VERALL_KBN"));
        bean.setFcbBiko(rs.getString("FCB_BIKO"));
        bean.setFcbSort(rs.getInt("FCB_SORT"));
        bean.setFcbMark(rs.getLong("FCB_MARK"));
        bean.setFcbAuid(rs.getInt("FCB_AUID"));
        bean.setFcbAdate(UDate.getInstanceTimestamp(rs.getTimestamp("FCB_ADATE")));
        bean.setFcbEuid(rs.getInt("FCB_EUID"));
        bean.setFcbEdate(UDate.getInstanceTimestamp(rs.getTimestamp("FCB_EDATE")));
        bean.setFcbPersonalFlg(rs.getInt("FCB_PERSONAL_FLG"));
        bean.setFcbOwnerSid(rs.getInt("FCB_OWNER_SID"));
        bean.setFcbErrl(rs.getInt("FCB_ERRL"));
        bean.setFcbSortFolder(rs.getInt("FCB_SORT_FOLDER"));
        bean.setFcbSortFolder1(rs.getInt("FCB_SORT_FOLDER1"));
        bean.setFcbSortFolder2(rs.getInt("FCB_SORT_FOLDER2"));
        bean.setFcbSortFolder3(rs.getInt("FCB_SORT_FOLDER3"));
        bean.setFcbJkbn(rs.getInt("FCB_JKBN"));
        return bean;
    }

    /**
     * <p>Create FILE_CABINET Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileCabinetInfoModel
     * @throws SQLException SQL実行例外
     * プラグインポートレット表示用
     */
    private FileCabinetInfoModel __getFileCabinetInfoFromRs(ResultSet rs) throws SQLException {
        FileCabinetInfoModel bean = new FileCabinetInfoModel();
        bean.setFcbSid(rs.getInt("FCB_SID"));
        bean.setFcbName(rs.getString("FCB_NAME"));
        bean.setFcbAccessKbn(rs.getInt("FCB_ACCESS_KBN"));
        bean.setFcbCapaKbn(rs.getInt("FCB_CAPA_KBN"));
        bean.setFcbCapaSize(rs.getInt("FCB_CAPA_SIZE"));
        bean.setFcbCapaWarn(rs.getInt("FCB_CAPA_WARN"));
        bean.setFcbVerKbn(rs.getInt("FCB_VER_KBN"));
        bean.setFcbVerallKbn(rs.getInt("FCB_VERALL_KBN"));
        bean.setFcbBiko(rs.getString("FCB_BIKO"));
        bean.setFcbSort(rs.getInt("FCB_SORT"));
        bean.setFcbMark(rs.getLong("FCB_MARK"));
        bean.setFcbAuid(rs.getInt("FCB_AUID"));
        bean.setFcbAdate(UDate.getInstanceTimestamp(rs.getTimestamp("FCB_ADATE")));
        bean.setFcbEuid(rs.getInt("FCB_EUID"));
        bean.setFcbEdate(UDate.getInstanceTimestamp(rs.getTimestamp("FCB_EDATE")));
        bean.setFcbPersonalFlg(rs.getInt("FCB_PERSONAL_FLG"));
        bean.setFcbErrl(rs.getInt("FCB_ERRL"));
        bean.setFcbSortFolder(rs.getInt("FCB_SORT_FOLDER"));
        bean.setFcbSortFolder1(rs.getInt("FCB_SORT_FOLDER1"));
        bean.setFcbSortFolder2(rs.getInt("FCB_SORT_FOLDER2"));
        bean.setFcbSortFolder3(rs.getInt("FCB_SORT_FOLDER3"));
        bean.setFcbJkbn(rs.getInt("FCB_JKBN"));
        return bean;
    }

    /**
     * <p>Create FILE_CABINET Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileCabinetModel
     * @throws SQLException SQL実行例外
     */
    private FileCabinetDspModel __getFileCabinetDspFromRs(ResultSet rs) throws SQLException {
        FileCabinetDspModel bean = new FileCabinetDspModel();
        bean.setFcbSid(rs.getInt("FCB_SID"));
        bean.setFcbName(rs.getString("FCB_NAME"));
        bean.setFcbAccessKbn(rs.getInt("FCB_ACCESS_KBN"));
        bean.setFcbCapaKbn(rs.getInt("FCB_CAPA_KBN"));
        bean.setFcbCapaSize(rs.getInt("FCB_CAPA_SIZE"));
        bean.setFcbCapaWarn(rs.getInt("FCB_CAPA_WARN"));
        bean.setFcbVerKbn(rs.getInt("FCB_VER_KBN"));
        bean.setFcbVerallKbn(rs.getInt("FCB_VERALL_KBN"));
        bean.setFcbBiko(rs.getString("FCB_BIKO"));
        bean.setFcbSort(rs.getInt("FCB_SORT"));
        bean.setFcbMark(rs.getLong("FCB_MARK"));
        bean.setFcbAuid(rs.getInt("FCB_AUID"));
        bean.setFcbAdate(UDate.getInstanceTimestamp(rs.getTimestamp("FCB_ADATE")));
        bean.setFcbEuid(rs.getInt("FCB_EUID"));
        bean.setFcbEdate(UDate.getInstanceTimestamp(rs.getTimestamp("FCB_EDATE")));
        bean.setFcbPersonalFlg(rs.getInt("FCB_PERSONAL_FLG"));
        bean.setFcbOwnerSid(rs.getInt("FCB_OWNER_SID"));
        bean.setFcbErrl(rs.getInt("FCB_ERRL"));
        bean.setFcbSortFolder(rs.getInt("FCB_SORT_FOLDER"));
        bean.setFcbSortFolder1(rs.getInt("FCB_SORT_FOLDER1"));
        bean.setFcbSortFolder2(rs.getInt("FCB_SORT_FOLDER2"));
        bean.setFcbSortFolder3(rs.getInt("FCB_SORT_FOLDER3"));
        bean.setFcbJkbn(rs.getInt("FCB_JKBN"));
        return bean;
    }
}
