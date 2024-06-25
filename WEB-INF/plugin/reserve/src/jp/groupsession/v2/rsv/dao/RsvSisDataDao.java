package jp.groupsession.v2.rsv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.rsv.model.RsvHidModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.rsv070.Rsv070Model;
import jp.groupsession.v2.rsv.rsv090.model.Rsv090DspModel;
import jp.groupsession.v2.rsv.rsv200kn.Rsv200knModel;
import jp.groupsession.v2.rsv.rsv210.Rsv210Model;
import jp.groupsession.v2.rsv.rsv270.Rsv270SisGrpModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <p>RSV_SIS_DATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RsvSisDataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvSisDataDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvSisDataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvSisDataDao(Connection con) {
        super(con);
    }
    /**
     * <p>Insert RSV_SIS_DATA Data Bindding JavaBean
     * @param bean RSV_SIS_DATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvSisDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_SIS_DATA(");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSD_NAME,");
            sql.addSql("   RSD_SNUM,");
            sql.addSql("   RSD_PROP_1,");
            sql.addSql("   RSD_PROP_2,");
            sql.addSql("   RSD_PROP_3,");
            sql.addSql("   RSD_PROP_4,");
            sql.addSql("   RSD_PROP_5,");
            sql.addSql("   RSD_PROP_6,");
            sql.addSql("   RSD_PROP_7,");
            sql.addSql("   RSD_PROP_8,");
            sql.addSql("   RSD_PROP_9,");
            sql.addSql("   RSD_PROP_10,");
            sql.addSql("   RSD_BIKO,");
            sql.addSql("   RSD_SORT,");
            sql.addSql("   RSD_AUID,");
            sql.addSql("   RSD_ADATE,");
            sql.addSql("   RSD_EUID,");
            sql.addSql("   RSD_EDATE,");
            sql.addSql("   RSD_ID,");
            sql.addSql("   RSD_PLA_CMT,");
            sql.addSql("   RSD_IMG_CMT1,");
            sql.addSql("   RSD_IMG_CMT2,");
            sql.addSql("   RSD_IMG_CMT3,");
            sql.addSql("   RSD_IMG_CMT4,");
            sql.addSql("   RSD_IMG_CMT5,");
            sql.addSql("   RSD_IMG_CMT6,");
            sql.addSql("   RSD_IMG_CMT7,");
            sql.addSql("   RSD_IMG_CMT8,");
            sql.addSql("   RSD_IMG_CMT9,");
            sql.addSql("   RSD_IMG_CMT10,");
            sql.addSql("   RSD_ID_DF,");
            sql.addSql("   RSD_SNUM_DF,");
            sql.addSql("   RSD_PROP_1_DF,");
            sql.addSql("   RSD_PROP_2_DF,");
            sql.addSql("   RSD_PROP_3_DF,");
            sql.addSql("   RSD_PROP_4_DF,");
            sql.addSql("   RSD_PROP_5_DF,");
            sql.addSql("   RSD_PROP_6_DF,");
            sql.addSql("   RSD_PROP_7_DF,");
            sql.addSql("   RSD_PROP_8_DF,");
            sql.addSql("   RSD_PROP_9_DF,");
            sql.addSql("   RSD_PROP_10_DF,");
            sql.addSql("   RSD_PLA_CMT_DF,");
            sql.addSql("   RSD_IMG_CMT1_DF,");
            sql.addSql("   RSD_IMG_CMT2_DF,");
            sql.addSql("   RSD_IMG_CMT3_DF,");
            sql.addSql("   RSD_IMG_CMT4_DF,");
            sql.addSql("   RSD_IMG_CMT5_DF,");
            sql.addSql("   RSD_IMG_CMT6_DF,");
            sql.addSql("   RSD_IMG_CMT7_DF,");
            sql.addSql("   RSD_IMG_CMT8_DF,");
            sql.addSql("   RSD_IMG_CMT9_DF,");
            sql.addSql("   RSD_IMG_CMT10_DF,");
            sql.addSql("   RSD_BIKO_DF,");
            sql.addSql("   RSD_IMG_DF,");
            sql.addSql("   RSD_APPR_KBN,");
            sql.addSql("   RSD_APPR_KBN_DF");
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
            sql.addIntValue(bean.getRsgSid());
            sql.addIntValue(bean.getRsdSid());
            sql.addStrValue(bean.getRsdName());
            sql.addStrValue(bean.getRsdSnum());
            sql.addStrValue(bean.getRsdProp1());
            sql.addStrValue(bean.getRsdProp2());
            sql.addStrValue(bean.getRsdProp3());
            sql.addStrValue(bean.getRsdProp4());
            sql.addStrValue(bean.getRsdProp5());
            sql.addStrValue(bean.getRsdProp6());
            sql.addStrValue(bean.getRsdProp7());
            sql.addStrValue(bean.getRsdProp8());
            sql.addStrValue(bean.getRsdProp9());
            sql.addStrValue(bean.getRsdProp10());
            sql.addStrValue(bean.getRsdBiko());
            sql.addIntValue(bean.getRsdSort());
            sql.addIntValue(bean.getRsdAuid());
            sql.addDateValue(bean.getRsdAdate());
            sql.addIntValue(bean.getRsdEuid());
            sql.addDateValue(bean.getRsdEdate());
            sql.addStrValue(bean.getRsdId());
            sql.addStrValue(bean.getRsdPlaCmt());
            sql.addStrValue(bean.getRsdImgCmt1());
            sql.addStrValue(bean.getRsdImgCmt2());
            sql.addStrValue(bean.getRsdImgCmt3());
            sql.addStrValue(bean.getRsdImgCmt4());
            sql.addStrValue(bean.getRsdImgCmt5());
            sql.addStrValue(bean.getRsdImgCmt6());
            sql.addStrValue(bean.getRsdImgCmt7());
            sql.addStrValue(bean.getRsdImgCmt8());
            sql.addStrValue(bean.getRsdImgCmt9());
            sql.addStrValue(bean.getRsdImgCmt10());
            sql.addIntValue(bean.getRsdIdDf());
            sql.addIntValue(bean.getRsdSnumDf());
            sql.addIntValue(bean.getRsdProp1Df());
            sql.addIntValue(bean.getRsdProp2Df());
            sql.addIntValue(bean.getRsdProp3Df());
            sql.addIntValue(bean.getRsdProp4Df());
            sql.addIntValue(bean.getRsdProp5Df());
            sql.addIntValue(bean.getRsdProp6Df());
            sql.addIntValue(bean.getRsdProp7Df());
            sql.addIntValue(bean.getRsdProp8Df());
            sql.addIntValue(bean.getRsdProp9Df());
            sql.addIntValue(bean.getRsdProp10Df());
            sql.addIntValue(bean.getRsdPlaCmtDf());
            sql.addIntValue(bean.getRsdImgCmt1Df());
            sql.addIntValue(bean.getRsdImgCmt2Df());
            sql.addIntValue(bean.getRsdImgCmt3Df());
            sql.addIntValue(bean.getRsdImgCmt4Df());
            sql.addIntValue(bean.getRsdImgCmt5Df());
            sql.addIntValue(bean.getRsdImgCmt6Df());
            sql.addIntValue(bean.getRsdImgCmt7Df());
            sql.addIntValue(bean.getRsdImgCmt8Df());
            sql.addIntValue(bean.getRsdImgCmt9Df());
            sql.addIntValue(bean.getRsdImgCmt10Df());
            sql.addIntValue(bean.getRsdBikoDf());
            sql.addIntValue(bean.getRsdImgDf());
            sql.addIntValue(bean.getRsdApprKbn());
            sql.addIntValue(bean.getRsdApprKbnDf());

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
     * <br>[機  能] 施設を登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 登録データモデル
     * @throws SQLException SQL実行例外
     */
    public void insertNewSisetu(RsvSisDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert");
            sql.addSql(" into");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql("   (");
            sql.addSql("     RSG_SID,");
            sql.addSql("     RSD_SID,");
            sql.addSql("     RSD_ID,");
            sql.addSql("     RSD_NAME,");
            sql.addSql("     RSD_SNUM,");
            sql.addSql("     RSD_PROP_1,");
            sql.addSql("     RSD_PROP_2,");
            sql.addSql("     RSD_PROP_3,");
            sql.addSql("     RSD_PROP_4,");
            sql.addSql("     RSD_PROP_5,");
            sql.addSql("     RSD_PROP_6,");
            sql.addSql("     RSD_PROP_7,");
            sql.addSql("     RSD_BIKO,");
            sql.addSql("     RSD_PLA_CMT,");
            sql.addSql("     RSD_IMG_CMT1,");
            sql.addSql("     RSD_IMG_CMT2,");
            sql.addSql("     RSD_IMG_CMT3,");
            sql.addSql("     RSD_IMG_CMT4,");
            sql.addSql("     RSD_IMG_CMT5,");
            sql.addSql("     RSD_IMG_CMT6,");
            sql.addSql("     RSD_IMG_CMT7,");
            sql.addSql("     RSD_IMG_CMT8,");
            sql.addSql("     RSD_IMG_CMT9,");
            sql.addSql("     RSD_IMG_CMT10,");
            sql.addSql("     RSD_ID_DF,");
            sql.addSql("     RSD_SNUM_DF,");
            sql.addSql("     RSD_PROP_1_DF,");
            sql.addSql("     RSD_PROP_2_DF,");
            sql.addSql("     RSD_PROP_3_DF,");
            sql.addSql("     RSD_PROP_4_DF,");
            sql.addSql("     RSD_PROP_5_DF,");
            sql.addSql("     RSD_PROP_6_DF,");
            sql.addSql("     RSD_PROP_7_DF,");
            sql.addSql("     RSD_PROP_8_DF,");
            sql.addSql("     RSD_PROP_9_DF,");
            sql.addSql("     RSD_PROP_10_DF,");
            sql.addSql("     RSD_PLA_CMT_DF,");
            sql.addSql("     RSD_IMG_CMT1_DF,");
            sql.addSql("     RSD_IMG_CMT2_DF,");
            sql.addSql("     RSD_IMG_CMT3_DF,");
            sql.addSql("     RSD_IMG_CMT4_DF,");
            sql.addSql("     RSD_IMG_CMT5_DF,");
            sql.addSql("     RSD_IMG_CMT6_DF,");
            sql.addSql("     RSD_IMG_CMT7_DF,");
            sql.addSql("     RSD_IMG_CMT8_DF,");
            sql.addSql("     RSD_IMG_CMT9_DF,");
            sql.addSql("     RSD_IMG_CMT10_DF,");
            sql.addSql("     RSD_BIKO_DF,");
            sql.addSql("     RSD_IMG_DF,");
            sql.addSql("     RSD_SORT,");
            sql.addSql("     RSD_APPR_KBN,");
            sql.addSql("     RSD_APPR_KBN_DF,");
            sql.addSql("     RSD_AUID,");
            sql.addSql("     RSD_ADATE,");
            sql.addSql("     RSD_EUID,");
            sql.addSql("     RSD_EDATE");
            sql.addSql("   )");
            sql.addSql("   select");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     case when max(RSD_SORT) is null then 1 "
                          + "else max(RSD_SORT) + 1 end,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?");
            sql.addSql("   from");
            sql.addSql("     RSV_SIS_DATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsgSid());
            sql.addIntValue(bean.getRsdSid());
            sql.addStrValue(bean.getRsdId());
            sql.addStrValue(bean.getRsdName());
            sql.addStrValue(bean.getRsdSnum());
            sql.addStrValue(bean.getRsdProp1());
            sql.addStrValue(bean.getRsdProp2());
            sql.addStrValue(bean.getRsdProp3());
            sql.addStrValue(bean.getRsdProp4());
            sql.addStrValue(bean.getRsdProp5());
            sql.addStrValue(bean.getRsdProp6());
            sql.addStrValue(bean.getRsdProp7());
            sql.addStrValue(bean.getRsdBiko());
            sql.addStrValue(bean.getRsdPlaCmt());
            sql.addStrValue(bean.getRsdImgCmt1());
            sql.addStrValue(bean.getRsdImgCmt2());
            sql.addStrValue(bean.getRsdImgCmt3());
            sql.addStrValue(bean.getRsdImgCmt4());
            sql.addStrValue(bean.getRsdImgCmt5());
            sql.addStrValue(bean.getRsdImgCmt6());
            sql.addStrValue(bean.getRsdImgCmt7());
            sql.addStrValue(bean.getRsdImgCmt8());
            sql.addStrValue(bean.getRsdImgCmt9());
            sql.addStrValue(bean.getRsdImgCmt10());
            sql.addIntValue(bean.getRsdIdDf());
            sql.addIntValue(bean.getRsdSnumDf());
            sql.addIntValue(bean.getRsdProp1Df());
            sql.addIntValue(bean.getRsdProp2Df());
            sql.addIntValue(bean.getRsdProp3Df());
            sql.addIntValue(bean.getRsdProp4Df());
            sql.addIntValue(bean.getRsdProp5Df());
            sql.addIntValue(bean.getRsdProp6Df());
            sql.addIntValue(bean.getRsdProp7Df());
            sql.addIntValue(bean.getRsdProp8Df());
            sql.addIntValue(bean.getRsdProp9Df());
            sql.addIntValue(bean.getRsdProp10Df());
            sql.addIntValue(bean.getRsdPlaCmtDf());
            sql.addIntValue(bean.getRsdImgCmt1Df());
            sql.addIntValue(bean.getRsdImgCmt2Df());
            sql.addIntValue(bean.getRsdImgCmt3Df());
            sql.addIntValue(bean.getRsdImgCmt4Df());
            sql.addIntValue(bean.getRsdImgCmt5Df());
            sql.addIntValue(bean.getRsdImgCmt6Df());
            sql.addIntValue(bean.getRsdImgCmt7Df());
            sql.addIntValue(bean.getRsdImgCmt8Df());
            sql.addIntValue(bean.getRsdImgCmt9Df());
            sql.addIntValue(bean.getRsdImgCmt10Df());
            sql.addIntValue(bean.getRsdBikoDf());
            sql.addIntValue(bean.getRsdImgDf());
            sql.addIntValue(bean.getRsdApprKbn());
            sql.addIntValue(bean.getRsdApprKbnDf());
            sql.addIntValue(bean.getRsdAuid());
            sql.addDateValue(bean.getRsdAdate());
            sql.addIntValue(bean.getRsdEuid());
            sql.addDateValue(bean.getRsdEdate());

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
     * <p>Update RSV_SIS_DATA Data Bindding JavaBean
     * @param bean RSV_SIS_DATA Data Bindding JavaBean
     * @return count update count
     * @throws SQLException SQL実行例外
     */
    public int update(RsvSisDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" set ");
            sql.addSql("   RSG_SID=?,");
            sql.addSql("   RSD_ID=?,");
            sql.addSql("   RSD_NAME=?,");
            sql.addSql("   RSD_SNUM=?,");
            sql.addSql("   RSD_PROP_1=?,");
            sql.addSql("   RSD_PROP_2=?,");
            sql.addSql("   RSD_PROP_3=?,");
            sql.addSql("   RSD_PROP_4=?,");
            sql.addSql("   RSD_PROP_5=?,");
            sql.addSql("   RSD_PROP_6=?,");
            sql.addSql("   RSD_PROP_7=?,");
            sql.addSql("   RSD_PROP_8=?,");
            sql.addSql("   RSD_PROP_9=?,");
            sql.addSql("   RSD_PROP_10=?,");
            sql.addSql("   RSD_BIKO=?,");
            sql.addSql("   RSD_PLA_CMT=?,");
            sql.addSql("   RSD_IMG_CMT1=?,");
            sql.addSql("   RSD_IMG_CMT2=?,");
            sql.addSql("   RSD_IMG_CMT3=?,");
            sql.addSql("   RSD_IMG_CMT4=?,");
            sql.addSql("   RSD_IMG_CMT5=?,");
            sql.addSql("   RSD_IMG_CMT6=?,");
            sql.addSql("   RSD_IMG_CMT7=?,");
            sql.addSql("   RSD_IMG_CMT8=?,");
            sql.addSql("   RSD_IMG_CMT9=?,");
            sql.addSql("   RSD_IMG_CMT10=?,");
            sql.addSql("   RSD_ID_DF=?,");
            sql.addSql("   RSD_SNUM_DF=?,");
            sql.addSql("   RSD_PROP_1_DF=?,");
            sql.addSql("   RSD_PROP_2_DF=?,");
            sql.addSql("   RSD_PROP_3_DF=?,");
            sql.addSql("   RSD_PROP_4_DF=?,");
            sql.addSql("   RSD_PROP_5_DF=?,");
            sql.addSql("   RSD_PROP_6_DF=?,");
            sql.addSql("   RSD_PROP_7_DF=?,");
            sql.addSql("   RSD_PROP_8_DF=?,");
            sql.addSql("   RSD_PROP_9_DF=?,");
            sql.addSql("   RSD_PROP_10_DF=?,");
            sql.addSql("   RSD_PLA_CMT_DF=?,");
            sql.addSql("   RSD_IMG_CMT1_DF=?,");
            sql.addSql("   RSD_IMG_CMT2_DF=?,");
            sql.addSql("   RSD_IMG_CMT3_DF=?,");
            sql.addSql("   RSD_IMG_CMT4_DF=?,");
            sql.addSql("   RSD_IMG_CMT5_DF=?,");
            sql.addSql("   RSD_IMG_CMT6_DF=?,");
            sql.addSql("   RSD_IMG_CMT7_DF=?,");
            sql.addSql("   RSD_IMG_CMT8_DF=?,");
            sql.addSql("   RSD_IMG_CMT9_DF=?,");
            sql.addSql("   RSD_IMG_CMT10_DF=?,");
            sql.addSql("   RSD_BIKO_DF=?,");
            sql.addSql("   RSD_IMG_DF=?,");
            sql.addSql("   RSD_SORT=?,");
            sql.addSql("   RSD_APPR_KBN=?,");
            sql.addSql("   RSD_APPR_KBN_DF=?,");
            sql.addSql("   RSD_AUID=?,");
            sql.addSql("   RSD_ADATE=?,");
            sql.addSql("   RSD_EUID=?,");
            sql.addSql("   RSD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RSD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsgSid());
            sql.addStrValue(bean.getRsdId());
            sql.addStrValue(bean.getRsdName());
            sql.addStrValue(bean.getRsdSnum());
            sql.addStrValue(bean.getRsdProp1());
            sql.addStrValue(bean.getRsdProp2());
            sql.addStrValue(bean.getRsdProp3());
            sql.addStrValue(bean.getRsdProp4());
            sql.addStrValue(bean.getRsdProp5());
            sql.addStrValue(bean.getRsdProp6());
            sql.addStrValue(bean.getRsdProp7());
            sql.addStrValue(bean.getRsdProp8());
            sql.addStrValue(bean.getRsdProp9());
            sql.addStrValue(bean.getRsdProp10());
            sql.addStrValue(bean.getRsdBiko());
            sql.addStrValue(bean.getRsdPlaCmt());
            sql.addStrValue(bean.getRsdImgCmt1());
            sql.addStrValue(bean.getRsdImgCmt2());
            sql.addStrValue(bean.getRsdImgCmt3());
            sql.addStrValue(bean.getRsdImgCmt4());
            sql.addStrValue(bean.getRsdImgCmt5());
            sql.addStrValue(bean.getRsdImgCmt6());
            sql.addStrValue(bean.getRsdImgCmt7());
            sql.addStrValue(bean.getRsdImgCmt8());
            sql.addStrValue(bean.getRsdImgCmt9());
            sql.addStrValue(bean.getRsdImgCmt10());
            sql.addIntValue(bean.getRsdIdDf());
            sql.addIntValue(bean.getRsdSnumDf());
            sql.addIntValue(bean.getRsdProp1Df());
            sql.addIntValue(bean.getRsdProp2Df());
            sql.addIntValue(bean.getRsdProp3Df());
            sql.addIntValue(bean.getRsdProp4Df());
            sql.addIntValue(bean.getRsdProp5Df());
            sql.addIntValue(bean.getRsdProp6Df());
            sql.addIntValue(bean.getRsdProp7Df());
            sql.addIntValue(bean.getRsdProp8Df());
            sql.addIntValue(bean.getRsdProp9Df());
            sql.addIntValue(bean.getRsdProp10Df());
            sql.addIntValue(bean.getRsdPlaCmtDf());
            sql.addIntValue(bean.getRsdImgCmt1Df());
            sql.addIntValue(bean.getRsdImgCmt2Df());
            sql.addIntValue(bean.getRsdImgCmt3Df());
            sql.addIntValue(bean.getRsdImgCmt4Df());
            sql.addIntValue(bean.getRsdImgCmt5Df());
            sql.addIntValue(bean.getRsdImgCmt6Df());
            sql.addIntValue(bean.getRsdImgCmt7Df());
            sql.addIntValue(bean.getRsdImgCmt8Df());
            sql.addIntValue(bean.getRsdImgCmt9Df());
            sql.addIntValue(bean.getRsdImgCmt10Df());
            sql.addIntValue(bean.getRsdBikoDf());
            sql.addIntValue(bean.getRsdImgDf());
            sql.addIntValue(bean.getRsdSort());
            sql.addIntValue(bean.getRsdApprKbn());
            sql.addIntValue(bean.getRsdApprKbnDf());
            sql.addIntValue(bean.getRsdAuid());
            sql.addDateValue(bean.getRsdAdate());
            sql.addIntValue(bean.getRsdEuid());
            sql.addDateValue(bean.getRsdEdate());
            //where
            sql.addIntValue(bean.getRsdSid());

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
     * <br>[機  能] 施設を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 登録データモデル
     * @return count update count
     * @throws SQLException SQL実行例外
     */
    public int updateSisetuData(RsvSisDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" set ");
            sql.addSql("   RSG_SID=?,");
            sql.addSql("   RSD_ID=?,");
            sql.addSql("   RSD_NAME=?,");
            sql.addSql("   RSD_SNUM=?,");
            sql.addSql("   RSD_PROP_1=?,");
            sql.addSql("   RSD_PROP_2=?,");
            sql.addSql("   RSD_PROP_3=?,");
            sql.addSql("   RSD_PROP_4=?,");
            sql.addSql("   RSD_PROP_5=?,");
            sql.addSql("   RSD_PROP_6=?,");
            sql.addSql("   RSD_PROP_7=?,");
            sql.addSql("   RSD_PLA_CMT=?,");
            sql.addSql("   RSD_SORT=?,");
            sql.addSql("   RSD_IMG_CMT1=?,");
            sql.addSql("   RSD_IMG_CMT2=?,");
            sql.addSql("   RSD_IMG_CMT3=?,");
            sql.addSql("   RSD_IMG_CMT4=?,");
            sql.addSql("   RSD_IMG_CMT5=?,");
            sql.addSql("   RSD_IMG_CMT6=?,");
            sql.addSql("   RSD_IMG_CMT7=?,");
            sql.addSql("   RSD_IMG_CMT8=?,");
            sql.addSql("   RSD_IMG_CMT9=?,");
            sql.addSql("   RSD_IMG_CMT10=?,");
            sql.addSql("   RSD_ID_DF=?,");
            sql.addSql("   RSD_SNUM_DF=?,");
            sql.addSql("   RSD_PROP_1_DF=?,");
            sql.addSql("   RSD_PROP_2_DF=?,");
            sql.addSql("   RSD_PROP_3_DF=?,");
            sql.addSql("   RSD_PROP_4_DF=?,");
            sql.addSql("   RSD_PROP_5_DF=?,");
            sql.addSql("   RSD_PROP_6_DF=?,");
            sql.addSql("   RSD_PROP_7_DF=?,");
            sql.addSql("   RSD_PROP_8_DF=?,");
            sql.addSql("   RSD_PROP_9_DF=?,");
            sql.addSql("   RSD_PROP_10_DF=?,");
            sql.addSql("   RSD_PLA_CMT_DF=?,");
            sql.addSql("   RSD_IMG_CMT1_DF=?,");
            sql.addSql("   RSD_IMG_CMT2_DF=?,");
            sql.addSql("   RSD_IMG_CMT3_DF=?,");
            sql.addSql("   RSD_IMG_CMT4_DF=?,");
            sql.addSql("   RSD_IMG_CMT5_DF=?,");
            sql.addSql("   RSD_IMG_CMT6_DF=?,");
            sql.addSql("   RSD_IMG_CMT7_DF=?,");
            sql.addSql("   RSD_IMG_CMT8_DF=?,");
            sql.addSql("   RSD_IMG_CMT9_DF=?,");
            sql.addSql("   RSD_IMG_CMT10_DF=?,");
            sql.addSql("   RSD_BIKO_DF=?,");
            sql.addSql("   RSD_IMG_DF=?,");
            sql.addSql("   RSD_BIKO=?,");
            if (bean.getRsdApprKbn() >= 0) {
                sql.addSql("   RSD_APPR_KBN=?,");
            }
            sql.addSql("   RSD_APPR_KBN_DF=?,");
            sql.addSql("   RSD_EUID=?,");
            sql.addSql("   RSD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RSD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsgSid());
            sql.addStrValue(bean.getRsdId());
            sql.addStrValue(bean.getRsdName());
            sql.addStrValue(bean.getRsdSnum());
            sql.addStrValue(bean.getRsdProp1());
            sql.addStrValue(bean.getRsdProp2());
            sql.addStrValue(bean.getRsdProp3());
            sql.addStrValue(bean.getRsdProp4());
            sql.addStrValue(bean.getRsdProp5());
            sql.addStrValue(bean.getRsdProp6());
            sql.addStrValue(bean.getRsdProp7());
            sql.addStrValue(bean.getRsdPlaCmt());
            sql.addIntValue(bean.getRsdSort());
            sql.addStrValue(bean.getRsdImgCmt1());
            sql.addStrValue(bean.getRsdImgCmt2());
            sql.addStrValue(bean.getRsdImgCmt3());
            sql.addStrValue(bean.getRsdImgCmt4());
            sql.addStrValue(bean.getRsdImgCmt5());
            sql.addStrValue(bean.getRsdImgCmt6());
            sql.addStrValue(bean.getRsdImgCmt7());
            sql.addStrValue(bean.getRsdImgCmt8());
            sql.addStrValue(bean.getRsdImgCmt9());
            sql.addStrValue(bean.getRsdImgCmt10());
            sql.addIntValue(bean.getRsdIdDf());
            sql.addIntValue(bean.getRsdSnumDf());
            sql.addIntValue(bean.getRsdProp1Df());
            sql.addIntValue(bean.getRsdProp2Df());
            sql.addIntValue(bean.getRsdProp3Df());
            sql.addIntValue(bean.getRsdProp4Df());
            sql.addIntValue(bean.getRsdProp5Df());
            sql.addIntValue(bean.getRsdProp6Df());
            sql.addIntValue(bean.getRsdProp7Df());
            sql.addIntValue(bean.getRsdProp8Df());
            sql.addIntValue(bean.getRsdProp9Df());
            sql.addIntValue(bean.getRsdProp10Df());
            sql.addIntValue(bean.getRsdPlaCmtDf());
            sql.addIntValue(bean.getRsdImgCmt1Df());
            sql.addIntValue(bean.getRsdImgCmt2Df());
            sql.addIntValue(bean.getRsdImgCmt3Df());
            sql.addIntValue(bean.getRsdImgCmt4Df());
            sql.addIntValue(bean.getRsdImgCmt5Df());
            sql.addIntValue(bean.getRsdImgCmt6Df());
            sql.addIntValue(bean.getRsdImgCmt7Df());
            sql.addIntValue(bean.getRsdImgCmt8Df());
            sql.addIntValue(bean.getRsdImgCmt9Df());
            sql.addIntValue(bean.getRsdImgCmt10Df());
            sql.addIntValue(bean.getRsdBikoDf());
            sql.addIntValue(bean.getRsdImgDf());
            sql.addStrValue(bean.getRsdBiko());
            if (bean.getRsdApprKbn() >= 0) {
                sql.addIntValue(bean.getRsdApprKbn());
            }
            sql.addIntValue(bean.getRsdApprKbnDf());
            sql.addIntValue(bean.getRsdEuid());
            sql.addDateValue(bean.getRsdEdate());
            //where
            sql.addIntValue(bean.getRsdSid());

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
     * <br>[機  能] 施設を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 登録データモデル
     * @return count update count
     * @throws SQLException SQL実行例外
     */
    public int updateSisetuData2(RsvSisDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" set ");
            sql.addSql("   RSG_SID=?,");
            sql.addSql("   RSD_ID=?,");
            sql.addSql("   RSD_NAME=?,");
            sql.addSql("   RSD_SNUM=?,");
            sql.addSql("   RSD_PROP_1=?,");
            sql.addSql("   RSD_PROP_2=?,");
            sql.addSql("   RSD_PROP_3=?,");
            sql.addSql("   RSD_PROP_4=?,");
            sql.addSql("   RSD_PROP_5=?,");
            sql.addSql("   RSD_PROP_6=?,");
            sql.addSql("   RSD_PROP_7=?,");
            sql.addSql("   RSD_BIKO=?,");
            sql.addSql("   RSD_APPR_KBN=?,");
            sql.addSql("   RSD_EUID=?,");
            sql.addSql("   RSD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RSD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsgSid());
            sql.addStrValue(bean.getRsdId());
            sql.addStrValue(bean.getRsdName());
            sql.addStrValue(bean.getRsdSnum());
            sql.addStrValue(bean.getRsdProp1());
            sql.addStrValue(bean.getRsdProp2());
            sql.addStrValue(bean.getRsdProp3());
            sql.addStrValue(bean.getRsdProp4());
            sql.addStrValue(bean.getRsdProp5());
            sql.addStrValue(bean.getRsdProp6());
            sql.addStrValue(bean.getRsdProp7());
            sql.addStrValue(bean.getRsdBiko());
            sql.addIntValue(bean.getRsdApprKbn());
            sql.addIntValue(bean.getRsdEuid());
            sql.addDateValue(bean.getRsdEdate());
            //where
            sql.addIntValue(bean.getRsdSid());

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
     * <p>Select RSV_SIS_DATA All Data
     * @return List in RSV_SIS_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisDataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisDataModel> ret = new ArrayList<RsvSisDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSD_ID,");
            sql.addSql("   RSD_NAME,");
            sql.addSql("   RSD_SNUM,");
            sql.addSql("   RSD_PROP_1,");
            sql.addSql("   RSD_PROP_2,");
            sql.addSql("   RSD_PROP_3,");
            sql.addSql("   RSD_PROP_4,");
            sql.addSql("   RSD_PROP_5,");
            sql.addSql("   RSD_PROP_6,");
            sql.addSql("   RSD_PROP_7,");
            sql.addSql("   RSD_PROP_8,");
            sql.addSql("   RSD_PROP_9,");
            sql.addSql("   RSD_PROP_10,");
            sql.addSql("   RSD_BIKO,");
            sql.addSql("   RSD_PLA_CMT,");
            sql.addSql("   RSD_IMG_CMT1,");
            sql.addSql("   RSD_IMG_CMT2,");
            sql.addSql("   RSD_IMG_CMT3,");
            sql.addSql("   RSD_IMG_CMT4,");
            sql.addSql("   RSD_IMG_CMT5,");
            sql.addSql("   RSD_IMG_CMT6,");
            sql.addSql("   RSD_IMG_CMT7,");
            sql.addSql("   RSD_IMG_CMT8,");
            sql.addSql("   RSD_IMG_CMT9,");
            sql.addSql("   RSD_IMG_CMT10,");
            sql.addSql("   RSD_ID_DF,");
            sql.addSql("   RSD_SNUM_DF,");
            sql.addSql("   RSD_PROP_1_DF,");
            sql.addSql("   RSD_PROP_2_DF,");
            sql.addSql("   RSD_PROP_3_DF,");
            sql.addSql("   RSD_PROP_4_DF,");
            sql.addSql("   RSD_PROP_5_DF,");
            sql.addSql("   RSD_PROP_6_DF,");
            sql.addSql("   RSD_PROP_7_DF,");
            sql.addSql("   RSD_PROP_8_DF,");
            sql.addSql("   RSD_PROP_9_DF,");
            sql.addSql("   RSD_PROP_10_DF,");
            sql.addSql("   RSD_PLA_CMT_DF,");
            sql.addSql("   RSD_IMG_CMT1_DF,");
            sql.addSql("   RSD_IMG_CMT2_DF,");
            sql.addSql("   RSD_IMG_CMT3_DF,");
            sql.addSql("   RSD_IMG_CMT4_DF,");
            sql.addSql("   RSD_IMG_CMT5_DF,");
            sql.addSql("   RSD_IMG_CMT6_DF,");
            sql.addSql("   RSD_IMG_CMT7_DF,");
            sql.addSql("   RSD_IMG_CMT8_DF,");
            sql.addSql("   RSD_IMG_CMT9_DF,");
            sql.addSql("   RSD_IMG_CMT10_DF,");
            sql.addSql("   RSD_BIKO_DF,");
            sql.addSql("   RSD_IMG_DF,");
            sql.addSql("   RSD_SORT,");
            sql.addSql("   RSD_APPR_KBN,");
            sql.addSql("   RSD_APPR_KBN_DF,");
            sql.addSql("   RSD_AUID,");
            sql.addSql("   RSD_ADATE,");
            sql.addSql("   RSD_EUID,");
            sql.addSql("   RSD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_DATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisDataFromRs(rs));
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
     * <br>[機  能] 指定された施設情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rsdSid 施設SID
     * @return 表示用施設情報一覧
     * @throws SQLException SQL実行例外
     */
    public Rsv090DspModel getSisetuData(int rsdSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Rsv090DspModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSD_ID,");
            sql.addSql("   RSD_NAME,");
            sql.addSql("   RSD_SNUM,");
            sql.addSql("   RSD_PROP_1,");
            sql.addSql("   RSD_PROP_2,");
            sql.addSql("   RSD_PROP_3,");
            sql.addSql("   RSD_PROP_4,");
            sql.addSql("   RSD_PROP_5,");
            sql.addSql("   RSD_PROP_6,");
            sql.addSql("   RSD_PROP_7,");
            sql.addSql("   RSD_PROP_8,");
            sql.addSql("   RSD_PROP_9,");
            sql.addSql("   RSD_PROP_10,");
            sql.addSql("   RSD_BIKO,");
            sql.addSql("   RSD_PLA_CMT,");
            sql.addSql("   RSD_IMG_CMT1,");
            sql.addSql("   RSD_IMG_CMT2,");
            sql.addSql("   RSD_IMG_CMT3,");
            sql.addSql("   RSD_IMG_CMT4,");
            sql.addSql("   RSD_IMG_CMT5,");
            sql.addSql("   RSD_IMG_CMT6,");
            sql.addSql("   RSD_IMG_CMT7,");
            sql.addSql("   RSD_IMG_CMT8,");
            sql.addSql("   RSD_IMG_CMT9,");
            sql.addSql("   RSD_IMG_CMT10,");
            sql.addSql("   RSD_ID_DF,");
            sql.addSql("   RSD_SNUM_DF,");
            sql.addSql("   RSD_PROP_1_DF,");
            sql.addSql("   RSD_PROP_2_DF,");
            sql.addSql("   RSD_PROP_3_DF,");
            sql.addSql("   RSD_PROP_4_DF,");
            sql.addSql("   RSD_PROP_5_DF,");
            sql.addSql("   RSD_PROP_6_DF,");
            sql.addSql("   RSD_PROP_7_DF,");
            sql.addSql("   RSD_PROP_8_DF,");
            sql.addSql("   RSD_PROP_9_DF,");
            sql.addSql("   RSD_PROP_10_DF,");
            sql.addSql("   RSD_PLA_CMT_DF,");
            sql.addSql("   RSD_IMG_CMT1_DF,");
            sql.addSql("   RSD_IMG_CMT2_DF,");
            sql.addSql("   RSD_IMG_CMT3_DF,");
            sql.addSql("   RSD_IMG_CMT4_DF,");
            sql.addSql("   RSD_IMG_CMT5_DF,");
            sql.addSql("   RSD_IMG_CMT6_DF,");
            sql.addSql("   RSD_IMG_CMT7_DF,");
            sql.addSql("   RSD_IMG_CMT8_DF,");
            sql.addSql("   RSD_IMG_CMT9_DF,");
            sql.addSql("   RSD_IMG_CMT10_DF,");
            sql.addSql("   RSD_BIKO_DF,");
            sql.addSql("   RSD_IMG_DF,");
            sql.addSql("   RSD_SORT,");
            sql.addSql("   RSD_APPR_KBN,");
            sql.addSql("   RSD_APPR_KBN_DF,");
            sql.addSql("   RSD_AUID,");
            sql.addSql("   RSD_ADATE,");
            sql.addSql("   RSD_EUID,");
            sql.addSql("   RSD_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where ");
            sql.addSql("   RSD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsdSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvDspDataFromRs(rs);
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
     * <p>Select RSV_SIS_DATA
     * @param bean RSV_SIS_DATA Model
     * @return RSV_SIS_DATAModel
     * @throws SQLException SQL実行例外
     */
    public RsvSisDataModel select(RsvSisDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSisDataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSD_ID,");
            sql.addSql("   RSD_NAME,");
            sql.addSql("   RSD_SNUM,");
            sql.addSql("   RSD_PROP_1,");
            sql.addSql("   RSD_PROP_2,");
            sql.addSql("   RSD_PROP_3,");
            sql.addSql("   RSD_PROP_4,");
            sql.addSql("   RSD_PROP_5,");
            sql.addSql("   RSD_PROP_6,");
            sql.addSql("   RSD_PROP_7,");
            sql.addSql("   RSD_PROP_8,");
            sql.addSql("   RSD_PROP_9,");
            sql.addSql("   RSD_PROP_10,");
            sql.addSql("   RSD_BIKO,");
            sql.addSql("   RSD_PLA_CMT,");
            sql.addSql("   RSD_IMG_CMT1,");
            sql.addSql("   RSD_IMG_CMT2,");
            sql.addSql("   RSD_IMG_CMT3,");
            sql.addSql("   RSD_IMG_CMT4,");
            sql.addSql("   RSD_IMG_CMT5,");
            sql.addSql("   RSD_IMG_CMT6,");
            sql.addSql("   RSD_IMG_CMT7,");
            sql.addSql("   RSD_IMG_CMT8,");
            sql.addSql("   RSD_IMG_CMT9,");
            sql.addSql("   RSD_IMG_CMT10,");
            sql.addSql("   RSD_ID_DF,");
            sql.addSql("   RSD_SNUM_DF,");
            sql.addSql("   RSD_PROP_1_DF,");
            sql.addSql("   RSD_PROP_2_DF,");
            sql.addSql("   RSD_PROP_3_DF,");
            sql.addSql("   RSD_PROP_4_DF,");
            sql.addSql("   RSD_PROP_5_DF,");
            sql.addSql("   RSD_PROP_6_DF,");
            sql.addSql("   RSD_PROP_7_DF,");
            sql.addSql("   RSD_PROP_8_DF,");
            sql.addSql("   RSD_PROP_9_DF,");
            sql.addSql("   RSD_PROP_10_DF,");
            sql.addSql("   RSD_PLA_CMT_DF,");
            sql.addSql("   RSD_IMG_CMT1_DF,");
            sql.addSql("   RSD_IMG_CMT2_DF,");
            sql.addSql("   RSD_IMG_CMT3_DF,");
            sql.addSql("   RSD_IMG_CMT4_DF,");
            sql.addSql("   RSD_IMG_CMT5_DF,");
            sql.addSql("   RSD_IMG_CMT6_DF,");
            sql.addSql("   RSD_IMG_CMT7_DF,");
            sql.addSql("   RSD_IMG_CMT8_DF,");
            sql.addSql("   RSD_IMG_CMT9_DF,");
            sql.addSql("   RSD_IMG_CMT10_DF,");
            sql.addSql("   RSD_BIKO_DF,");
            sql.addSql("   RSD_IMG_DF,");
            sql.addSql("   RSD_SORT,");
            sql.addSql("   RSD_APPR_KBN,");
            sql.addSql("   RSD_APPR_KBN_DF,");
            sql.addSql("   RSD_AUID,");
            sql.addSql("   RSD_ADATE,");
            sql.addSql("   RSD_EUID,");
            sql.addSql("   RSD_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where ");
            sql.addSql("   RSD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsdSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvSisDataFromRs(rs);
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
     * <p>Select RSV_SIS_DATA
     * @param rsdIds RSV_SIS_DATA Model
     * @return RSV_SIS_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisDataModel> select(String[] rsdIds) throws SQLException {
        Connection con = null;
        List<RsvSisDataModel> ret = new ArrayList<>();
        if (rsdIds == null) {
            return ret;
        }
        Set<String> sids = Arrays.stream(rsdIds)
                                .collect(Collectors.toSet());
        if (sids == null || sids.isEmpty()) {
            return ret;
        }

        List<String> exeList = new ArrayList<>();
        Iterator<String> itr = sids.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" select");
        sb.append("   RSG_SID,");
        sb.append("   RSD_SID,");
        sb.append("   RSD_ID,");
        sb.append("   RSD_NAME,");
        sb.append("   RSD_SNUM,");
        sb.append("   RSD_PROP_1,");
        sb.append("   RSD_PROP_2,");
        sb.append("   RSD_PROP_3,");
        sb.append("   RSD_PROP_4,");
        sb.append("   RSD_PROP_5,");
        sb.append("   RSD_PROP_6,");
        sb.append("   RSD_PROP_7,");
        sb.append("   RSD_PROP_8,");
        sb.append("   RSD_PROP_9,");
        sb.append("   RSD_PROP_10,");
        sb.append("   RSD_BIKO,");
        sb.append("   RSD_PLA_CMT,");
        sb.append("   RSD_IMG_CMT1,");
        sb.append("   RSD_IMG_CMT2,");
        sb.append("   RSD_IMG_CMT3,");
        sb.append("   RSD_IMG_CMT4,");
        sb.append("   RSD_IMG_CMT5,");
        sb.append("   RSD_IMG_CMT6,");
        sb.append("   RSD_IMG_CMT7,");
        sb.append("   RSD_IMG_CMT8,");
        sb.append("   RSD_IMG_CMT9,");
        sb.append("   RSD_IMG_CMT10,");
        sb.append("   RSD_ID_DF,");
        sb.append("   RSD_SNUM_DF,");
        sb.append("   RSD_PROP_1_DF,");
        sb.append("   RSD_PROP_2_DF,");
        sb.append("   RSD_PROP_3_DF,");
        sb.append("   RSD_PROP_4_DF,");
        sb.append("   RSD_PROP_5_DF,");
        sb.append("   RSD_PROP_6_DF,");
        sb.append("   RSD_PROP_7_DF,");
        sb.append("   RSD_PROP_8_DF,");
        sb.append("   RSD_PROP_9_DF,");
        sb.append("   RSD_PROP_10_DF,");
        sb.append("   RSD_PLA_CMT_DF,");
        sb.append("   RSD_IMG_CMT1_DF,");
        sb.append("   RSD_IMG_CMT2_DF,");
        sb.append("   RSD_IMG_CMT3_DF,");
        sb.append("   RSD_IMG_CMT4_DF,");
        sb.append("   RSD_IMG_CMT5_DF,");
        sb.append("   RSD_IMG_CMT6_DF,");
        sb.append("   RSD_IMG_CMT7_DF,");
        sb.append("   RSD_IMG_CMT8_DF,");
        sb.append("   RSD_IMG_CMT9_DF,");
        sb.append("   RSD_IMG_CMT10_DF,");
        sb.append("   RSD_BIKO_DF,");
        sb.append("   RSD_IMG_DF,");
        sb.append("   RSD_SORT,");
        sb.append("   RSD_APPR_KBN,");
        sb.append("   RSD_APPR_KBN_DF,");
        sb.append("   RSD_AUID,");
        sb.append("   RSD_ADATE,");
        sb.append("   RSD_EUID,");
        sb.append("   RSD_EDATE");
        sb.append(" from");
        sb.append("   RSV_SIS_DATA");
        sb.append(" where ");

        con = getCon();

        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }

            //500件毎に実行
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());
            sql.addSql(" RSD_ID in (");

            Iterator<String> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                sql.addSql("   ?");
                sql.addStrValue(exeItr.next());

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            sql.addSql(" )");

            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                try (ResultSet rs = pstmt.executeQuery();) {

                    while (rs.next()) {
                        ret.add(__getRsvSisDataFromRs(rs));
                    }

                }

            }
            exeList.clear();
        }
        return ret;
    }

    /**
     * <p>Select RSV_SIS_DATA
     * @param rsdId RSD_ID
     * @return RSD_SID
     * @throws SQLException SQL実行例外
     */
    public int getRsdSid(String rsdId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSD_ID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where ");
            sql.addSql("   RSD_ID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(rsdId);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("RSD_SID");
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
     * <p>施設IDに該当する施設情報を取得する。
     * @param rsdId RSD_ID
     * @return 施設情報モデル
     * @throws SQLException SQL実行例外
     */
    public RsvSisDataModel getRsvSisData(String rsdId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSisDataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSD_ID,");
            sql.addSql("   RSD_NAME,");
            sql.addSql("   RSD_SNUM,");
            sql.addSql("   RSD_PROP_1,");
            sql.addSql("   RSD_PROP_2,");
            sql.addSql("   RSD_PROP_3,");
            sql.addSql("   RSD_PROP_4,");
            sql.addSql("   RSD_PROP_5,");
            sql.addSql("   RSD_PROP_6,");
            sql.addSql("   RSD_PROP_7,");
            sql.addSql("   RSD_PROP_8,");
            sql.addSql("   RSD_PROP_9,");
            sql.addSql("   RSD_PROP_10,");
            sql.addSql("   RSD_BIKO,");
            sql.addSql("   RSD_PLA_CMT,");
            sql.addSql("   RSD_IMG_CMT1,");
            sql.addSql("   RSD_IMG_CMT2,");
            sql.addSql("   RSD_IMG_CMT3,");
            sql.addSql("   RSD_IMG_CMT4,");
            sql.addSql("   RSD_IMG_CMT5,");
            sql.addSql("   RSD_IMG_CMT6,");
            sql.addSql("   RSD_IMG_CMT7,");
            sql.addSql("   RSD_IMG_CMT8,");
            sql.addSql("   RSD_IMG_CMT9,");
            sql.addSql("   RSD_IMG_CMT10,");
            sql.addSql("   RSD_ID_DF,");
            sql.addSql("   RSD_SNUM_DF,");
            sql.addSql("   RSD_PROP_1_DF,");
            sql.addSql("   RSD_PROP_2_DF,");
            sql.addSql("   RSD_PROP_3_DF,");
            sql.addSql("   RSD_PROP_4_DF,");
            sql.addSql("   RSD_PROP_5_DF,");
            sql.addSql("   RSD_PROP_6_DF,");
            sql.addSql("   RSD_PROP_7_DF,");
            sql.addSql("   RSD_PROP_8_DF,");
            sql.addSql("   RSD_PROP_9_DF,");
            sql.addSql("   RSD_PROP_10_DF,");
            sql.addSql("   RSD_PLA_CMT_DF,");
            sql.addSql("   RSD_IMG_CMT1_DF,");
            sql.addSql("   RSD_IMG_CMT2_DF,");
            sql.addSql("   RSD_IMG_CMT3_DF,");
            sql.addSql("   RSD_IMG_CMT4_DF,");
            sql.addSql("   RSD_IMG_CMT5_DF,");
            sql.addSql("   RSD_IMG_CMT6_DF,");
            sql.addSql("   RSD_IMG_CMT7_DF,");
            sql.addSql("   RSD_IMG_CMT8_DF,");
            sql.addSql("   RSD_IMG_CMT9_DF,");
            sql.addSql("   RSD_IMG_CMT10_DF,");
            sql.addSql("   RSD_BIKO_DF,");
            sql.addSql("   RSD_IMG_DF,");
            sql.addSql("   RSD_SORT,");
            sql.addSql("   RSD_APPR_KBN,");
            sql.addSql("   RSD_APPR_KBN_DF,");
            sql.addSql("   RSD_AUID,");
            sql.addSql("   RSD_ADATE,");
            sql.addSql("   RSD_EUID,");
            sql.addSql("   RSD_EDATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where ");
            sql.addSql("   RSD_ID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(rsdId);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new RsvSisDataModel();
                ret = __getRsvSisDataFromRs(rs);
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
     * <p>施設IDに該当する施設数を取得する。
     * @param rsdId RSD_ID
     * @param rsdSid 除外する施設SID
     * @return RSD_SID
     * @throws SQLException SQL実行例外
     */
    public int countRsdId(String rsdId, int rsdSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where ");
            sql.addSql("   RSD_ID=?");
            sql.addSql(" and ");
            sql.addSql("   RSD_SID<>?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(rsdId);
            sql.addIntValue(rsdSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
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
     * <p>Select RSV_SIS_DATA All Data
     * @return List in RSV_SIS_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<String> getRsdSidList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSD_ID");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_DATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("RSD_ID"));
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
     * <p>Delete RSV_SIS_DATA
     * @param bean RSV_SIS_DATA Model
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(RsvSisDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where ");
            sql.addSql("   RSD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsdSid());

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
     * <p>Delete RSV_SIS_DATA
     * @param grpSid groupsid
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int grpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where ");
            sql.addSql("   RSG_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);

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
     * <br>[機  能] 指定されたグループの施設リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param grpSid 施設グループSID
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisDataModel> selectSisetuList(int grpSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisDataModel> ret = new ArrayList<RsvSisDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSD_ID,");
            sql.addSql("   RSD_NAME,");
            sql.addSql("   RSD_SNUM,");
            sql.addSql("   RSD_PROP_1,");
            sql.addSql("   RSD_PROP_2,");
            sql.addSql("   RSD_PROP_3,");
            sql.addSql("   RSD_PROP_4,");
            sql.addSql("   RSD_PROP_5,");
            sql.addSql("   RSD_PROP_6,");
            sql.addSql("   RSD_PROP_7,");
            sql.addSql("   RSD_PROP_8,");
            sql.addSql("   RSD_PROP_9,");
            sql.addSql("   RSD_PROP_10,");
            sql.addSql("   RSD_BIKO,");
            sql.addSql("   RSD_PLA_CMT,");
            sql.addSql("   RSD_IMG_CMT1,");
            sql.addSql("   RSD_IMG_CMT2,");
            sql.addSql("   RSD_IMG_CMT3,");
            sql.addSql("   RSD_IMG_CMT4,");
            sql.addSql("   RSD_IMG_CMT5,");
            sql.addSql("   RSD_IMG_CMT6,");
            sql.addSql("   RSD_IMG_CMT7,");
            sql.addSql("   RSD_IMG_CMT8,");
            sql.addSql("   RSD_IMG_CMT9,");
            sql.addSql("   RSD_IMG_CMT10,");
            sql.addSql("   RSD_ID_DF,");
            sql.addSql("   RSD_SNUM_DF,");
            sql.addSql("   RSD_PROP_1_DF,");
            sql.addSql("   RSD_PROP_2_DF,");
            sql.addSql("   RSD_PROP_3_DF,");
            sql.addSql("   RSD_PROP_4_DF,");
            sql.addSql("   RSD_PROP_5_DF,");
            sql.addSql("   RSD_PROP_6_DF,");
            sql.addSql("   RSD_PROP_7_DF,");
            sql.addSql("   RSD_PROP_8_DF,");
            sql.addSql("   RSD_PROP_9_DF,");
            sql.addSql("   RSD_PROP_10_DF,");
            sql.addSql("   RSD_PLA_CMT_DF,");
            sql.addSql("   RSD_IMG_CMT1_DF,");
            sql.addSql("   RSD_IMG_CMT2_DF,");
            sql.addSql("   RSD_IMG_CMT3_DF,");
            sql.addSql("   RSD_IMG_CMT4_DF,");
            sql.addSql("   RSD_IMG_CMT5_DF,");
            sql.addSql("   RSD_IMG_CMT6_DF,");
            sql.addSql("   RSD_IMG_CMT7_DF,");
            sql.addSql("   RSD_IMG_CMT8_DF,");
            sql.addSql("   RSD_IMG_CMT9_DF,");
            sql.addSql("   RSD_IMG_CMT10_DF,");
            sql.addSql("   RSD_BIKO_DF,");
            sql.addSql("   RSD_IMG_DF,");
            sql.addSql("   RSD_SORT,");
            sql.addSql("   RSD_APPR_KBN,");
            sql.addSql("   RSD_APPR_KBN_DF,");
            sql.addSql("   RSD_AUID,");
            sql.addSql("   RSD_ADATE,");
            sql.addSql("   RSD_EUID,");
            sql.addSql("   RSD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_DATA");

            if (grpSid > GSConstReserve.COMBO_DEFAULT_VALUE) {
                sql.addSql(" where");
                sql.addSql("   RSG_SID = ?");
                sql.addIntValue(grpSid);
            }

            sql.addSql(" order by");
            sql.addSql("   RSD_SORT asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisDataFromRs(rs));
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
     * <br>[機  能] 指定されたグループの施設リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param grpSid 施設グループSID
     * @param usrSid セッションユーザSID
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisDataModel> selectSisetuList(int grpSid, int usrSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisDataModel> ret = new ArrayList<RsvSisDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_DATA.RSG_SID,");
            sql.addSql("   RSV_SIS_DATA.RSD_SID,");
            sql.addSql("   RSV_SIS_DATA.RSD_ID,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME,");
            sql.addSql("   RSV_SIS_DATA.RSD_SNUM,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_1,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_2,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_3,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_4,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_5,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_6,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_7,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_8,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_9,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_10,");
            sql.addSql("   RSV_SIS_DATA.RSD_BIKO,");
            sql.addSql("   RSV_SIS_DATA.RSD_PLA_CMT,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT1,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT2,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT3,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT4,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT5,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT6,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT7,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT8,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT9,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT10,");
            sql.addSql("   RSV_SIS_DATA.RSD_ID_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_SNUM_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_1_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_2_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_3_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_4_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_5_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_6_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_7_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_8_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_9_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_10_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PLA_CMT_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT1_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT2_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT3_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT4_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT5_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT6_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT7_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT8_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT9_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT10_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_BIKO_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_SORT,");
            sql.addSql("   RSV_SIS_DATA.RSD_APPR_KBN,");
            sql.addSql("   RSV_SIS_DATA.RSD_APPR_KBN_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_AUID,");
            sql.addSql("   RSV_SIS_DATA.RSD_ADATE,");
            sql.addSql("   RSV_SIS_DATA.RSD_EUID,");
            sql.addSql("   RSV_SIS_DATA.RSD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql("    left join");
            sql.addSql("      RSV_SIS_GRP");
            sql.addSql("    on");
            sql.addSql("      RSV_SIS_DATA.RSG_SID=RSV_SIS_GRP.RSG_SID");
            sql.addSql(" where");

            if (grpSid > GSConstReserve.COMBO_DEFAULT_VALUE) {
                sql.addSql("   RSV_SIS_DATA.RSG_SID = ?");
                sql.addIntValue(grpSid);

            } else {
                sql.addSql("  (");
                sql.addSql("   RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
                sql.addSql("  )");

                sql.addSql(" or");

                sql.addSql("   ( ");
                sql.addSql("    RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
                sql.addSql("  and ");
                sql.addSql("    exists");
                sql.addSql("    (");
                sql.addSql("      select");
                sql.addSql("        RSV_ACCESS_CONF.RSG_SID");
                sql.addSql("      from");
                sql.addSql("        RSV_ACCESS_CONF");
                sql.addSql("        left join");
                sql.addSql("          CMN_BELONGM");
                sql.addSql("        on");
                sql.addSql("          RSV_ACCESS_CONF.GRP_SID = CMN_BELONGM.GRP_SID");
                sql.addSql("      where");
                sql.addSql("        RSV_ACCESS_CONF.RSG_SID = RSV_SIS_GRP.RSG_SID");
                sql.addSql("      and");
                sql.addSql("        (");
                sql.addSql("          RSV_ACCESS_CONF.USR_SID = ?");
                sql.addSql("        or");
                sql.addSql("          CMN_BELONGM.USR_SID = ?");
                sql.addSql("        )");
                sql.addSql("    )");
                sql.addSql("  )");

                sql.addSql(" or");

                sql.addSql("  (");
                sql.addSql("    RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
                sql.addSql("  and ");
                sql.addSql("    not exists");
                sql.addSql("    (");
                sql.addSql("      select");
                sql.addSql("        RSV_ACCESS_CONF.RSG_SID");
                sql.addSql("      from");
                sql.addSql("        RSV_ACCESS_CONF");
                sql.addSql("        left join");
                sql.addSql("          CMN_BELONGM");
                sql.addSql("        on");
                sql.addSql("          RSV_ACCESS_CONF.GRP_SID = CMN_BELONGM.GRP_SID");
                sql.addSql("      where");
                sql.addSql("        RSV_ACCESS_CONF.RSG_SID = RSV_SIS_GRP.RSG_SID");
                sql.addSql("      and");
                sql.addSql("        (");
                sql.addSql("          RSV_ACCESS_CONF.USR_SID = ?");
                sql.addSql("        or");
                sql.addSql("          CMN_BELONGM.USR_SID = ?");
                sql.addSql("        )");
                sql.addSql("      and");
                sql.addSql("        RSV_ACCESS_CONF.RAC_AUTH=?");
                sql.addSql("    )");
                sql.addSql("  )");

                sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_FREE);
                sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_LIMIT);
                sql.addIntValue(usrSid);
                sql.addIntValue(usrSid);
                sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_PERMIT);
                sql.addIntValue(usrSid);
                sql.addIntValue(usrSid);
                sql.addIntValue(GSConstReserve.RSV_ACCESS_KBN_READ);
            }

            sql.addSql(" order by");
            sql.addSql("   RSV_SIS_DATA.RSD_SORT asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisDataFromRs(rs));
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
     * <br>[機  能] 指定されたグループの施設リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param grpSid 施設グループSID
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisDataModel> selectGrpSisetuList(int grpSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisDataModel> ret = new ArrayList<RsvSisDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_DATA.RSD_SID,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP,");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_GRP.RSG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_GRP.RSG_SID = RSV_SIS_DATA.RSG_SID");
            sql.addSql(" order by");
            sql.addSql("   RSV_SIS_DATA.RSD_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvSisDataModel mdl = new RsvSisDataModel();
                mdl.setRsdSid(rs.getInt("RSD_SID"));
                mdl.setRsdName(rs.getString("RSD_NAME"));
                ret.add(mdl);
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
     * <br>[機  能] 指定された施設一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sid 取得対象施設SID
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisDataModel> selectGrpSisetuList(String[] sid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisDataModel> ret = new ArrayList<RsvSisDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSD_NAME");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where");
            sql.addSql("   RSD_SID in(");

            for (int i = 0; i < sid.length; i++) {
                sql.addSql("?");
                if (i != sid.length - 1) {
                    sql.addSql(", ");
                }
                sql.addIntValue(Integer.parseInt(sid[i]));
            }
            sql.addSql(")");

            sql.addSql(" order by");
            sql.addSql("   RSD_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvSisDataModel mdl = new RsvSisDataModel();
                mdl.setRsdSid(rs.getInt("RSD_SID"));
                mdl.setRsdName(rs.getString("RSD_NAME"));
                ret.add(mdl);
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
     * <br>[機  能] 指定されたグループの施設リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sisSid 施設SID
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisDataModel> selectGrpSisetuList(List < Integer > sisSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisDataModel> ret = new ArrayList<RsvSisDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_DATA.RSD_ID,");
            sql.addSql("   RSV_SIS_DATA.RSD_SID,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql("   left join");
            sql.addSql("     RSV_SIS_GRP");
            sql.addSql("   on");
            sql.addSql("     RSV_SIS_DATA.RSG_SID = RSV_SIS_GRP.RSG_SID");

            if (sisSid != null && sisSid.size() > 0) {
                sql.addSql(" where");
                sql.addSql("   RSV_SIS_DATA.RSD_SID in( ");
                for (int i = 0; i < sisSid.size(); i++) {
                    if (i == 0) {
                        sql.addSql("  ? ");
                    } else {
                        sql.addSql("  ,? ");
                    }
                    sql.addIntValue(sisSid.get(i).intValue());
                }
                sql.addSql("  ) ");
            }

            sql.addSql(" order by");
            sql.addSql("   RSV_SIS_GRP.RSG_SORT asc,");
            sql.addSql("   RSV_SIS_DATA.RSD_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvSisDataModel mdl = new RsvSisDataModel();
                mdl.setRsdId(rs.getString("RSD_ID"));
                mdl.setRsdSid(rs.getInt("RSD_SID"));
                mdl.setRsdName(rs.getString("RSD_NAME"));
                ret.add(mdl);
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
     * <br>[機  能] 指定されたグループの施設リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sisSid 施設SID
     * @param usrSid セッションユーザSID
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisDataModel> selectGrpSisetuCanReadList(
            List < Integer > sisSid, int usrSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisDataModel> ret = new ArrayList<RsvSisDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_DATA.RSD_ID,");
            sql.addSql("   RSV_SIS_DATA.RSD_SID,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_DATA,");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_DATA.RSG_SID=RSV_SIS_GRP.RSG_SID");

            if (sisSid != null && sisSid.size() > 0) {
                sql.addSql(" and");
                sql.addSql("   RSV_SIS_DATA.RSD_SID in( ");
                for (int i = 0; i < sisSid.size(); i++) {
                    if (i == 0) {
                        sql.addSql("  ? ");
                    } else {
                        sql.addSql("  ,? ");
                    }
                    sql.addIntValue(sisSid.get(i).intValue());
                }
                sql.addSql("  ) ");
            }

            sql.addSql(" and");
            sql.addSql("(");
            sql.addSql("  (");
            sql.addSql("   RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  )");

            sql.addSql(" or");

            sql.addSql("   ( ");
            sql.addSql("    RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  and ");
            sql.addSql("    exists");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID");
            sql.addSql("      from");
            sql.addSql("        RSV_ACCESS_CONF");
            sql.addSql("        left join");
            sql.addSql("          CMN_BELONGM");
            sql.addSql("        on");
            sql.addSql("          RSV_ACCESS_CONF.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("      where");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql("      and");
            sql.addSql("        (");
            sql.addSql("          RSV_ACCESS_CONF.USR_SID = ?");
            sql.addSql("        or");
            sql.addSql("          CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("    )");
            sql.addSql("  )");

            sql.addSql(" or");

            sql.addSql("  (");
            sql.addSql("    RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  and ");
            sql.addSql("    not exists");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID");
            sql.addSql("      from");
            sql.addSql("        RSV_ACCESS_CONF");
            sql.addSql("        left join");
            sql.addSql("          CMN_BELONGM");
            sql.addSql("        on");
            sql.addSql("          RSV_ACCESS_CONF.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("      where");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql("      and");
            sql.addSql("        (");
            sql.addSql("          RSV_ACCESS_CONF.USR_SID = ?");
            sql.addSql("        or");
            sql.addSql("          CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("    )");
            sql.addSql("  )");
            sql.addSql(" )");


            sql.addSql(" order by");
            sql.addSql("   RSV_SIS_GRP.RSG_SORT asc,");
            sql.addSql("   RSV_SIS_DATA.RSD_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_FREE);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_LIMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_PERMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvSisDataModel mdl = new RsvSisDataModel();
                mdl.setRsdId(rs.getString("RSD_ID"));
                mdl.setRsdSid(rs.getInt("RSD_SID"));
                mdl.setRsdName(rs.getString("RSD_NAME"));
                ret.add(mdl);
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
     * <br>[機  能] 指定されたグループの施設リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param grpSid 施設グループSID
     * @param sisSid 施設SID
     * @param usrSid セッションユーザSID
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisDataModel> selectGrpSisetuCanReadList(
            int grpSid, ArrayList < Integer > sisSid, int usrSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisDataModel> ret = new ArrayList<RsvSisDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_DATA.RSD_SID,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_DATA,");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_DATA.RSG_SID=RSV_SIS_GRP.RSG_SID");
            if (grpSid != 0) {
                sql.addSql(" and");
                sql.addSql("   RSV_SIS_GRP.RSG_SID = ?");
                sql.addIntValue(grpSid);
            }

            if (sisSid != null && sisSid.size() > 0) {
                sql.addSql(" and");
                sql.addSql("   RSV_SIS_DATA.RSD_SID in( ");
                for (int i = 0; i < sisSid.size(); i++) {
                    if (i == 0) {
                        sql.addSql("  ? ");
                    } else {
                        sql.addSql("  ,? ");
                    }
                    sql.addIntValue(sisSid.get(i).intValue());
                }
                sql.addSql("  ) ");
            }

            sql.addSql(" and");
            sql.addSql("(");
            sql.addSql("  (");
            sql.addSql("   RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  )");

            sql.addSql(" or");

            sql.addSql("   ( ");
            sql.addSql("    RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  and ");
            sql.addSql("    exists");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID");
            sql.addSql("      from");
            sql.addSql("        RSV_ACCESS_CONF");
            sql.addSql("        left join");
            sql.addSql("          CMN_BELONGM");
            sql.addSql("        on");
            sql.addSql("          RSV_ACCESS_CONF.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("      where");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql("      and");
            sql.addSql("        (");
            sql.addSql("          RSV_ACCESS_CONF.USR_SID = ?");
            sql.addSql("        or");
            sql.addSql("          CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("    )");
            sql.addSql("  )");

            sql.addSql(" or");

            sql.addSql("  (");
            sql.addSql("    RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  and ");
            sql.addSql("    not exists");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID");
            sql.addSql("      from");
            sql.addSql("        RSV_ACCESS_CONF");
            sql.addSql("        left join");
            sql.addSql("          CMN_BELONGM");
            sql.addSql("        on");
            sql.addSql("          RSV_ACCESS_CONF.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("      where");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql("      and");
            sql.addSql("        (");
            sql.addSql("          RSV_ACCESS_CONF.USR_SID = ?");
            sql.addSql("        or");
            sql.addSql("          CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("    )");
            sql.addSql("  )");
            sql.addSql(" )");


            sql.addSql(" order by");
            sql.addSql("   RSV_SIS_DATA.RSD_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_FREE);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_LIMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_PERMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvSisDataModel mdl = new RsvSisDataModel();
                mdl.setRsdSid(rs.getInt("RSD_SID"));
                mdl.setRsdName(rs.getString("RSD_NAME"));
                ret.add(mdl);
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
     * <br>[機  能] 指定されたグループの施設リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param grpSid 施設グループSID
     * @param sisSid 除外する施設SID
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisDataModel> selectGrpSisetuList(int grpSid, ArrayList < Integer > sisSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisDataModel> ret = new ArrayList<RsvSisDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_DATA.RSD_SID,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME,");
            sql.addSql("   RSV_SIS_DATA.RSG_SID,");
            sql.addSql("   RSV_SIS_DATA.RSD_ID,");
            sql.addSql("   RSV_SIS_DATA.RSD_SNUM,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_1,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_2,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_3,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_4,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_5,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_6,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_7,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_8,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_9,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_10,");
            sql.addSql("   RSV_SIS_DATA.RSD_BIKO,");
            sql.addSql("   RSV_SIS_DATA.RSD_PLA_CMT,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT1,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT2,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT3,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT4,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT5,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT6,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT7,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT8,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT9,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT10,");
            sql.addSql("   RSV_SIS_DATA.RSD_ID_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_SNUM_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_1_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_2_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_3_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_4_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_5_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_6_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_7_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_8_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_9_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_10_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PLA_CMT_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT1_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT2_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT3_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT4_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT5_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT6_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT7_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT8_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT9_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT10_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_BIKO_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_SORT,");
            sql.addSql("   RSV_SIS_DATA.RSD_APPR_KBN,");
            sql.addSql("   RSV_SIS_DATA.RSD_APPR_KBN_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_AUID,");
            sql.addSql("   RSV_SIS_DATA.RSD_ADATE,");
            sql.addSql("   RSV_SIS_DATA.RSD_EUID,");
            sql.addSql("   RSV_SIS_DATA.RSD_EDATE");

            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP,");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_GRP.RSG_SID = RSV_SIS_DATA.RSG_SID");
            if (grpSid != 0) {
                sql.addSql(" and");
                sql.addSql("   RSV_SIS_GRP.RSG_SID = ?");
                sql.addIntValue(grpSid);
            }
            if (sisSid != null && sisSid.size() > 0) {
                sql.addSql(" and");
                sql.addSql("   RSV_SIS_DATA.RSD_SID not in( ");
                for (int i = 0; i < sisSid.size(); i++) {
                    if (i == 0) {
                        sql.addSql("  ? ");
                    } else {
                        sql.addSql("  ,? ");
                    }
                    sql.addIntValue(sisSid.get(i).intValue());
                }
                sql.addSql("  ) ");
            }
            sql.addSql(" order by");
            sql.addSql("   RSV_SIS_GRP.RSG_SORT asc,");
            sql.addSql("   RSV_SIS_DATA.RSD_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisDataFromRs(rs));
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
     * <br>[機  能] 指定されたグループの施設リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param grpSid 施設グループSID
     * @param sisSid 除外する施設SID
     * @param usrSid ユーザSID
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisDataModel> selectGrpSisetuCanEditList(
            int grpSid, ArrayList < Integer > sisSid, int usrSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisDataModel> ret = new ArrayList<RsvSisDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_DATA.RSD_SID,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME,");
            sql.addSql("   RSV_SIS_DATA.RSG_SID,");
            sql.addSql("   RSV_SIS_DATA.RSD_ID,");
            sql.addSql("   RSV_SIS_DATA.RSD_SNUM,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_1,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_2,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_3,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_4,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_5,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_6,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_7,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_8,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_9,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_10,");
            sql.addSql("   RSV_SIS_DATA.RSD_BIKO,");
            sql.addSql("   RSV_SIS_DATA.RSD_PLA_CMT,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT1,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT2,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT3,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT4,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT5,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT6,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT7,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT8,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT9,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT10,");
            sql.addSql("   RSV_SIS_DATA.RSD_ID_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_SNUM_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_1_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_2_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_3_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_4_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_5_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_6_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_7_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_8_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_9_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_10_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PLA_CMT_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT1_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT2_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT3_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT4_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT5_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT6_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT7_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT8_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT9_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT10_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_BIKO_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_SORT,");
            sql.addSql("   RSV_SIS_DATA.RSD_APPR_KBN,");
            sql.addSql("   RSV_SIS_DATA.RSD_APPR_KBN_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_AUID,");
            sql.addSql("   RSV_SIS_DATA.RSD_ADATE,");
            sql.addSql("   RSV_SIS_DATA.RSD_EUID,");
            sql.addSql("   RSV_SIS_DATA.RSD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP,");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_GRP.RSG_SID = RSV_SIS_DATA.RSG_SID");
            if (grpSid != 0) {
                sql.addSql(" and");
                sql.addSql("   RSV_SIS_GRP.RSG_SID = ?");
                sql.addIntValue(grpSid);
            }
            if (sisSid != null && sisSid.size() > 0) {
                sql.addSql(" and");
                sql.addSql("   RSV_SIS_DATA.RSD_SID not in( ");
                for (int i = 0; i < sisSid.size(); i++) {
                    if (i == 0) {
                        sql.addSql("  ? ");
                    } else {
                        sql.addSql("  ,? ");
                    }
                    sql.addIntValue(sisSid.get(i).intValue());
                }
                sql.addSql("  ) ");
            }

            sql.addSql(" and");
            sql.addSql("(");
            sql.addSql("  (");
            sql.addSql("   RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  )");

            sql.addSql(" or");

            sql.addSql("   ( ");
            sql.addSql("    RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  and ");
            sql.addSql("    exists");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        *");
            sql.addSql("      from");
            sql.addSql("        RSV_ACCESS_CONF");
            sql.addSql("        left join");
            sql.addSql("          CMN_BELONGM");
            sql.addSql("        on");
            sql.addSql("          RSV_ACCESS_CONF.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("      where");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql("      and");
            sql.addSql("        (");
            sql.addSql("          RSV_ACCESS_CONF.USR_SID = ?");
            sql.addSql("        or");
            sql.addSql("          CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("      and");
            sql.addSql("        RSV_ACCESS_CONF.RAC_AUTH = ?");
            sql.addSql("    )");
            sql.addSql("  )");

            sql.addSql(" or");

            sql.addSql("  (");
            sql.addSql("    RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  and ");
            sql.addSql("    not exists");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        *");
            sql.addSql("      from");
            sql.addSql("        RSV_ACCESS_CONF");
            sql.addSql("        left join");
            sql.addSql("          CMN_BELONGM");
            sql.addSql("        on");
            sql.addSql("          RSV_ACCESS_CONF.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("      where");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql("      and");
            sql.addSql("        (");
            sql.addSql("          RSV_ACCESS_CONF.USR_SID = ?");
            sql.addSql("        or");
            sql.addSql("          CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("    )");
            sql.addSql("  )");
            sql.addSql(" )");

            sql.addSql(" order by");
            sql.addSql("   RSV_SIS_GRP.RSG_SORT asc,");
            sql.addSql("   RSV_SIS_DATA.RSD_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_FREE);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_LIMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_KBN_WRITE);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_PERMIT);
            sql.addIntValue(usrSid);
            sql.addIntValue(usrSid);
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisDataFromRs(rs));
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
     * <br>[機  能] 指定された施設SIDの施設リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sisSids 検索する施設SID
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisDataModel> selectSisetuList(String[] sisSids)
        throws SQLException {
        if (sisSids == null) {
            return new ArrayList<RsvSisDataModel>();
        }
        ArrayList<Integer> list = new ArrayList<Integer>(sisSids.length);
        for (String sisSid : sisSids) {
            list.add(new Integer(sisSid));
        }
        return selectSisetuList(list);
    }

    /**
     * <br>[機  能] 指定された施設SIDの施設リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sisSid 検索する施設SID
     * @return ret ArrayList in RsvSisGrpModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisDataModel> selectSisetuList(ArrayList <Integer> sisSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisDataModel> ret = new ArrayList<RsvSisDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_DATA.RSD_SID,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME,");
            sql.addSql("   RSV_SIS_DATA.RSG_SID,");
            sql.addSql("   RSV_SIS_DATA.RSD_ID,");
            sql.addSql("   RSV_SIS_DATA.RSD_SNUM,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_1,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_2,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_3,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_4,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_5,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_6,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_7,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_8,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_9,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_10,");
            sql.addSql("   RSV_SIS_DATA.RSD_BIKO,");
            sql.addSql("   RSV_SIS_DATA.RSD_PLA_CMT,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT1,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT2,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT3,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT4,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT5,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT6,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT7,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT8,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT9,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT10,");
            sql.addSql("   RSV_SIS_DATA.RSD_ID_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_SNUM_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_1_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_2_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_3_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_4_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_5_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_6_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_7_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_8_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_9_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_10_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_PLA_CMT_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT1_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT2_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT3_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT4_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT5_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT6_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT7_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT8_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT9_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_CMT10_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_BIKO_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_IMG_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_SORT,");
            sql.addSql("   RSV_SIS_DATA.RSD_APPR_KBN,");
            sql.addSql("   RSV_SIS_DATA.RSD_APPR_KBN_DF,");
            sql.addSql("   RSV_SIS_DATA.RSD_AUID,");
            sql.addSql("   RSV_SIS_DATA.RSD_ADATE,");
            sql.addSql("   RSV_SIS_DATA.RSD_EUID,");
            sql.addSql("   RSV_SIS_DATA.RSD_EDATE");

            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP,");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_GRP.RSG_SID = RSV_SIS_DATA.RSG_SID");
            if (sisSid != null && sisSid.size() > 0) {
                sql.addSql(" and");
                sql.addSql("   RSV_SIS_DATA.RSD_SID in( ");
                for (int i = 0; i < sisSid.size(); i++) {
                    if (i == 0) {
                        sql.addSql("  ? ");
                    } else {
                        sql.addSql("  ,? ");
                    }
                    sql.addIntValue(sisSid.get(i).intValue());
                }
                sql.addSql("  ) ");
            } else {
                sql.addSql(" and");
                sql.addSql("   RSV_SIS_DATA.RSD_SID=?");
                sql.addIntValue(GSConstCommon.NUM_INIT);
            }
            sql.addSql(" order by");
            sql.addSql("   RSV_SIS_DATA.RSD_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisDataFromRs(rs));
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
     * <br>[機  能] 指定されたグループの施設リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param grpSid 施設グループSID
     * @return ret ArrayList in Integer
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getDeleteGrpSisetuList(int grpSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_DATA.RSD_SID");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP,");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_GRP.RSG_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_GRP.RSG_SID = RSV_SIS_DATA.RSG_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(grpSid);
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("RSD_SID"));
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
     * <br>[機  能] 指定された施設データ(ポップアップ用)を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sisetuSid 施設SID
     * @return ret 取得結果
     * @throws SQLException SQL実行例外
     */
    public Rsv070Model getPopUpSisetuData(int sisetuSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Rsv070Model ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_GRP.RSG_SID,");
            sql.addSql("   RSV_SIS_GRP.RSG_NAME,");
            sql.addSql("   RSV_SIS_KBN.RSK_SID,");
            sql.addSql("   RSV_SIS_KBN.RSK_NAME,");
            sql.addSql("   RSV_SIS_DATA.RSD_ID,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME,");
            sql.addSql("   RSV_SIS_DATA.RSD_SNUM,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_1,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_2,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_3,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_4,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_5,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_6,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_7,");
            sql.addSql("   RSV_SIS_DATA.RSD_BIKO,");
            sql.addSql("   RSD_PLA_CMT,");
            sql.addSql("   RSD_IMG_CMT1,");
            sql.addSql("   RSD_IMG_CMT2,");
            sql.addSql("   RSD_IMG_CMT3,");
            sql.addSql("   RSD_IMG_CMT4,");
            sql.addSql("   RSD_IMG_CMT5,");
            sql.addSql("   RSD_IMG_CMT6,");
            sql.addSql("   RSD_IMG_CMT7,");
            sql.addSql("   RSD_IMG_CMT8,");
            sql.addSql("   RSD_IMG_CMT9,");
            sql.addSql("   RSD_IMG_CMT10,");
            sql.addSql("   RSD_APPR_KBN,");
            sql.addSql("   RSD_APPR_KBN_DF");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP,");
            sql.addSql("   RSV_SIS_KBN,");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_DATA.RSD_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_DATA.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_GRP.RSK_SID = RSV_SIS_KBN.RSK_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sisetuSid);
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new Rsv070Model();
                ret.setRsgSid(rs.getInt("RSG_SID"));
                ret.setRsgName(rs.getString("RSG_NAME"));
                ret.setRskSid(rs.getInt("RSK_SID"));
                ret.setRskName(rs.getString("RSK_NAME"));
                ret.setRsdId(rs.getString("RSD_ID"));
                ret.setRsdName(rs.getString("RSD_NAME"));
                ret.setRsdSnum(rs.getString("RSD_SNUM"));
                ret.setRsdProp1Value(rs.getString("RSD_PROP_1"));
                ret.setRsdProp2Value(rs.getString("RSD_PROP_2"));
                ret.setRsdProp3Value(rs.getString("RSD_PROP_3"));
                ret.setRsdProp4Value(rs.getString("RSD_PROP_4"));
                ret.setRsdProp5Value(rs.getString("RSD_PROP_5"));
                ret.setRsdProp6Value(rs.getString("RSD_PROP_6"));
                ret.setRsdProp7Value(rs.getString("RSD_PROP_7"));
                ret.setRsdBiko(rs.getString("RSD_BIKO"));
                ret.setRsdPlaCmt(rs.getString("RSD_PLA_CMT"));
                ret.setRsdImgCmt1(rs.getString("RSD_IMG_CMT1"));
                ret.setRsdImgCmt2(rs.getString("RSD_IMG_CMT2"));
                ret.setRsdImgCmt3(rs.getString("RSD_IMG_CMT3"));
                ret.setRsdImgCmt4(rs.getString("RSD_IMG_CMT4"));
                ret.setRsdImgCmt5(rs.getString("RSD_IMG_CMT5"));
                ret.setRsdImgCmt6(rs.getString("RSD_IMG_CMT6"));
                ret.setRsdImgCmt7(rs.getString("RSD_IMG_CMT7"));
                ret.setRsdImgCmt8(rs.getString("RSD_IMG_CMT8"));
                ret.setRsdImgCmt9(rs.getString("RSD_IMG_CMT9"));
                ret.setRsdImgCmt10(rs.getString("RSD_IMG_CMT10"));
                ret.setRsdApprKbn(rs.getInt("RSD_APPR_KBN"));
                ret.setRsdApprKbnDf(rs.getInt("RSD_APPR_KBN_DF"));
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
     * <br>[機  能] 指定されたキーの施設・施設グループ情報マップを取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param keyArray 取得データキー配列
     * @return ret 取得結果
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, Rsv210Model> getIkkatuTorokuGroupMap(ArrayList<String> keyArray)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        HashMap<Integer, Rsv210Model> ret = new HashMap<Integer, Rsv210Model>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_GRP.RSG_NAME,");
            sql.addSql("   RSV_SIS_DATA.RSD_SID,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_6,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_7");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP,");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_DATA.RSD_SID in (");

            for (int i = 0; i < keyArray.size(); i++) {
                String key = keyArray.get(i);
                String sid = key.substring(key.indexOf("-") + 1);
                sql.addSql("?");
                sql.addIntValue(Integer.parseInt(sid));
                if (i != keyArray.size() - 1) {
                    sql.addSql(", ");
                }
            }

            sql.addSql("   )");

            sql.addSql(" and");
            sql.addSql("   RSV_SIS_DATA.RSG_SID = RSV_SIS_GRP.RSG_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Rsv210Model mdl = new Rsv210Model();
                mdl.setRsgName(rs.getString("RSG_NAME"));
                mdl.setRsdSid(rs.getInt("RSD_SID"));
                mdl.setRsdName(rs.getString("RSD_NAME"));
                mdl.setRsdProp6(rs.getString("RSD_PROP_6"));
                mdl.setRsdProp7(rs.getString("RSD_PROP_7"));
                if (!ret.containsKey(new Integer(mdl.getRsdSid()))) {
                    ret.put(new Integer(mdl.getRsdSid()), mdl);
                }
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
     * <br>[機  能] 指定されたSIDの施設・施設グループ情報を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sid 検索施設SID
     * @return ret 取得結果
     * @throws SQLException SQL実行例外
     */
    public Rsv210Model getGroupCheckModel(int sid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        Rsv210Model ret = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_GRP.RSG_NAME,");
            sql.addSql("   RSV_SIS_DATA.RSD_SID,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_6,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_7");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP,");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_DATA.RSD_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_DATA.RSG_SID = RSV_SIS_GRP.RSG_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sid);
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new Rsv210Model();
                ret.setRsgName(rs.getString("RSG_NAME"));
                ret.setRsdSid(rs.getInt("RSD_SID"));
                ret.setRsdName(rs.getString("RSD_NAME"));
                ret.setRsdProp6(rs.getString("RSD_PROP_6"));
                ret.setRsdProp7(rs.getString("RSD_PROP_7"));
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
     * <br>[機  能] 指定された施設データ(CSV出力用)を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sisetuKbnSid 施設区分SID
     * @return List in Rsv270SisGrpModel 取得結果
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Rsv270SisGrpModel>
               getCsvOutGrpSisetuData(int sisetuKbnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Rsv270SisGrpModel> rsv270MdlList = new ArrayList<Rsv270SisGrpModel>();
        Rsv270SisGrpModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSV_SIS_GRP.RSG_SID,");
            sql.addSql("   RSV_SIS_GRP.RSG_ID,");
            sql.addSql("   RSV_SIS_GRP.RSG_NAME,");
            sql.addSql("   RSV_SIS_DATA.RSD_ID,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME,");
            sql.addSql("   RSV_SIS_DATA.RSD_SNUM,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_1,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_2,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_3,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_4,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_5,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_6,");
            sql.addSql("   RSV_SIS_DATA.RSD_PROP_7,");
            sql.addSql("   RSV_SIS_DATA.RSD_APPR_KBN,");
            sql.addSql("   RSV_SIS_DATA.RSD_BIKO");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" left join");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" on");
            sql.addSql("   RSV_SIS_GRP.RSG_SID = RSV_SIS_DATA.RSG_SID");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_GRP.RSK_SID = ?");
            sql.addSql(" order by");
            sql.addSql("   RSV_SIS_GRP.RSG_ID");
            sql.addSql(" asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sisetuKbnSid);
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = new Rsv270SisGrpModel();
                ret.setRsgSid(rs.getInt("RSG_SID"));
                ret.setRsgId(rs.getString("RSG_ID"));
                ret.setRsgName(rs.getString("RSG_NAME"));
                ret.setRsdId(rs.getString("RSD_ID"));
                ret.setRsdName(rs.getString("RSD_NAME"));
                ret.setRsdSnum(rs.getString("RSD_SNUM"));
                ret.setRsdProp1(rs.getString("RSD_PROP_1"));
                ret.setRsdProp2(rs.getString("RSD_PROP_2"));
                ret.setRsdProp3(rs.getString("RSD_PROP_3"));
                ret.setRsdProp4(rs.getString("RSD_PROP_4"));
                ret.setRsdProp5(rs.getString("RSD_PROP_5"));
                ret.setRsdProp6(rs.getString("RSD_PROP_6"));
                ret.setRsdProp7(rs.getString("RSD_PROP_7"));
                ret.setRsdApprKbn(rs.getInt("RSD_APPR_KBN"));
                ret.setRsdBiko(rs.getString("RSD_BIKO"));

                rsv270MdlList.add(ret);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return rsv270MdlList;
    }

    /**
     * <br>[機  能] 施設の表示順序を入れ替える
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 更新モデル
     * @throws SQLException SQL実行例外
     */
    public void updateSisetuIkkatu(Rsv200knModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        boolean commaFlg = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" set");

            if (bean.isRsdProp1UpdFlg()) {
                sql.addSql("   RSD_PROP_1 = ?");
                sql.addStrValue(bean.getRsdProp1());
                commaFlg = true;
            }

            if (bean.isRsdProp2UpdFlg()) {
                if (commaFlg) {
                    sql.addSql("   ,");
                }
                commaFlg = true;
                sql.addSql("   RSD_PROP_2 = ?");
                sql.addStrValue(bean.getRsdProp2());
            }

            if (bean.isRsdProp3UpdFlg()) {
                if (commaFlg) {
                    sql.addSql("   ,");
                }
                commaFlg = true;
                sql.addSql("   RSD_PROP_3 = ?");
                sql.addStrValue(bean.getRsdProp3());
            }

            if (bean.isRsdProp4UpdFlg()) {
                if (commaFlg) {
                    sql.addSql("   ,");
                }
                commaFlg = true;
                sql.addSql("   RSD_PROP_4 = ?");
                sql.addStrValue(bean.getRsdProp4());
            }

            if (bean.isRsdProp5UpdFlg()) {
                if (commaFlg) {
                    sql.addSql("   ,");
                }
                commaFlg = true;
                sql.addSql("   RSD_PROP_5 = ?");
                sql.addStrValue(bean.getRsdProp5());
            }

            if (bean.isRsdProp6UpdFlg()) {
                if (commaFlg) {
                    sql.addSql("   ,");
                }
                commaFlg = true;
                sql.addSql("   RSD_PROP_6 = ?");
                sql.addStrValue(bean.getRsdProp6());
            }

            if (bean.isRsdProp7UpdFlg()) {
                if (commaFlg) {
                    sql.addSql("   ,");
                }
                commaFlg = true;
                sql.addSql("   RSD_PROP_7 = ?");
                sql.addStrValue(bean.getRsdProp7());
            }

            if (bean.isRsdRsdBikoUpdFlg()) {
                if (commaFlg) {
                    sql.addSql("   ,");
                }
                commaFlg = true;
                sql.addSql("   RSD_BIKO = ?");
                sql.addStrValue(bean.getRsdBiko());
            }

            if (commaFlg) {
                sql.addSql("   ,");
            }
            sql.addSql("   RSD_EUID = ?,");
            sql.addSql("   RSD_EDATE = ?");
            sql.addSql(" where");
            sql.addSql("   RSG_SID = ?");

            sql.addIntValue(bean.getRsdEuid());
            sql.addDateValue(bean.getRsdEdate());
            sql.addIntValue(bean.getRsgSid());

            sql.addSql(" and");
            sql.addSql("   RSD_SID in (");

            String[] target = bean.getTargetSiset();
            for (int i = 0; i < target.length; i++) {
                sql.addSql("?");
                if (i != target.length - 1) {
                    sql.addSql(",");
                }
                sql.addIntValue(Integer.parseInt(target[i]));
            }

            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <br>[機  能] 指定されたグループの施設リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sid 施設SIDリスト
     * @return ret ArrayList in RsvHidModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvHidModel> selectHidSisetuList(ArrayList<String> sid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvHidModel> ret = new ArrayList<RsvHidModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_GRP.RSG_SID,");
            sql.addSql("   RSV_SIS_GRP.RSG_NAME,");
            sql.addSql("   RSV_SIS_DATA.RSD_SID,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_GRP,");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_DATA.RSD_SID in (");

            for (int i = 0; i < sid.size(); i++) {
                sql.addSql("?");
                if (i != sid.size() - 1) {
                    sql.addSql(", ");
                }
                sql.addIntValue(Integer.parseInt(sid.get(i)));
            }

            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_DATA.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql(" order by");
            sql.addSql("   RSV_SIS_GRP.RSG_SORT,");
            sql.addSql("   RSV_SIS_DATA.RSD_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvHidModel mdl = new RsvHidModel();
                mdl.setRsgSid(rs.getInt("RSG_SID"));
                mdl.setRsgName(rs.getString("RSG_NAME"));
                mdl.setRsdSid(rs.getInt("RSD_SID"));
                mdl.setRsdName(rs.getString("RSD_NAME"));
                ret.add(mdl);
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
     * <p>Create RSV_SIS_DATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RsvSisDataModel
     * @throws SQLException SQL実行例外
     */
    private RsvSisDataModel __getRsvSisDataFromRs(ResultSet rs) throws SQLException {
        RsvSisDataModel bean = new RsvSisDataModel();
        bean.setRsgSid(rs.getInt("RSG_SID"));
        bean.setRsdSid(rs.getInt("RSD_SID"));
        bean.setRsdId(rs.getString("RSD_ID"));
        bean.setRsdName(rs.getString("RSD_NAME"));
        bean.setRsdSnum(rs.getString("RSD_SNUM"));
        bean.setRsdProp1(rs.getString("RSD_PROP_1"));
        bean.setRsdProp2(rs.getString("RSD_PROP_2"));
        bean.setRsdProp3(rs.getString("RSD_PROP_3"));
        bean.setRsdProp4(rs.getString("RSD_PROP_4"));
        bean.setRsdProp5(rs.getString("RSD_PROP_5"));
        bean.setRsdProp6(rs.getString("RSD_PROP_6"));
        bean.setRsdProp7(rs.getString("RSD_PROP_7"));
        bean.setRsdProp8(rs.getString("RSD_PROP_8"));
        bean.setRsdProp9(rs.getString("RSD_PROP_9"));
        bean.setRsdProp10(rs.getString("RSD_PROP_10"));
        bean.setRsdBiko(rs.getString("RSD_BIKO"));
        bean.setRsdPlaCmt(rs.getString("RSD_PLA_CMT"));
        bean.setRsdImgCmt1(rs.getString("RSD_IMG_CMT1"));
        bean.setRsdImgCmt2(rs.getString("RSD_IMG_CMT2"));
        bean.setRsdImgCmt3(rs.getString("RSD_IMG_CMT3"));
        bean.setRsdImgCmt4(rs.getString("RSD_IMG_CMT4"));
        bean.setRsdImgCmt5(rs.getString("RSD_IMG_CMT5"));
        bean.setRsdImgCmt6(rs.getString("RSD_IMG_CMT6"));
        bean.setRsdImgCmt7(rs.getString("RSD_IMG_CMT7"));
        bean.setRsdImgCmt8(rs.getString("RSD_IMG_CMT8"));
        bean.setRsdImgCmt9(rs.getString("RSD_IMG_CMT9"));
        bean.setRsdImgCmt10(rs.getString("RSD_IMG_CMT10"));
        bean.setRsdIdDf(rs.getInt("RSD_ID_DF"));
        bean.setRsdSnumDf(rs.getInt("RSD_SNUM_DF"));
        bean.setRsdProp1Df(rs.getInt("RSD_PROP_1_DF"));
        bean.setRsdProp2Df(rs.getInt("RSD_PROP_2_DF"));
        bean.setRsdProp3Df(rs.getInt("RSD_PROP_3_DF"));
        bean.setRsdProp4Df(rs.getInt("RSD_PROP_4_DF"));
        bean.setRsdProp5Df(rs.getInt("RSD_PROP_5_DF"));
        bean.setRsdProp6Df(rs.getInt("RSD_PROP_6_DF"));
        bean.setRsdProp7Df(rs.getInt("RSD_PROP_7_DF"));
        bean.setRsdProp8Df(rs.getInt("RSD_PROP_8_DF"));
        bean.setRsdProp9Df(rs.getInt("RSD_PROP_9_DF"));
        bean.setRsdProp10Df(rs.getInt("RSD_PROP_10_DF"));
        bean.setRsdPlaCmtDf(rs.getInt("RSD_PLA_CMT_DF"));
        bean.setRsdImgCmt1Df(rs.getInt("RSD_IMG_CMT1_DF"));
        bean.setRsdImgCmt2Df(rs.getInt("RSD_IMG_CMT2_DF"));
        bean.setRsdImgCmt3Df(rs.getInt("RSD_IMG_CMT3_DF"));
        bean.setRsdImgCmt4Df(rs.getInt("RSD_IMG_CMT4_DF"));
        bean.setRsdImgCmt5Df(rs.getInt("RSD_IMG_CMT5_DF"));
        bean.setRsdImgCmt6Df(rs.getInt("RSD_IMG_CMT6_DF"));
        bean.setRsdImgCmt7Df(rs.getInt("RSD_IMG_CMT7_DF"));
        bean.setRsdImgCmt8Df(rs.getInt("RSD_IMG_CMT8_DF"));
        bean.setRsdImgCmt9Df(rs.getInt("RSD_IMG_CMT9_DF"));
        bean.setRsdImgCmt10Df(rs.getInt("RSD_IMG_CMT10_DF"));
        bean.setRsdBikoDf(rs.getInt("RSD_BIKO_DF"));
        bean.setRsdImgDf(rs.getInt("RSD_IMG_DF"));
        bean.setRsdSort(rs.getInt("RSD_SORT"));
        bean.setRsdApprKbn(rs.getInt("RSD_APPR_KBN"));
        bean.setRsdApprKbnDf(rs.getInt("RSD_APPR_KBN_DF"));
        bean.setRsdAuid(rs.getInt("RSD_AUID"));
        bean.setRsdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSD_ADATE")));
        bean.setRsdEuid(rs.getInt("RSD_EUID"));
        bean.setRsdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSD_EDATE")));
        return bean;
    }

    /**
     * <p>表示用施設情報一覧を取得する
     * @param rs ResultSet
     * @throws SQLException SQL実行例外
     * @return ret RsvDspDataModel
     */
    private Rsv090DspModel __getRsvDspDataFromRs(ResultSet rs) throws SQLException {
        Rsv090DspModel bean = new Rsv090DspModel();
        bean.setRsgSid(rs.getInt("RSG_SID"));
        bean.setRsdSid(rs.getInt("RSD_SID"));
        bean.setRsdId(rs.getString("RSD_ID"));
        bean.setRsdName(rs.getString("RSD_NAME"));
        bean.setRsdSnum(rs.getString("RSD_SNUM"));
        bean.setRsdProp1(rs.getString("RSD_PROP_1"));
        bean.setRsdProp2(rs.getString("RSD_PROP_2"));
        bean.setRsdProp3(rs.getString("RSD_PROP_3"));
        bean.setRsdProp4(rs.getString("RSD_PROP_4"));
        bean.setRsdProp5(rs.getString("RSD_PROP_5"));
        bean.setRsdProp6(rs.getString("RSD_PROP_6"));
        bean.setRsdProp7(rs.getString("RSD_PROP_7"));
        bean.setRsdProp8(rs.getString("RSD_PROP_8"));
        bean.setRsdProp9(rs.getString("RSD_PROP_9"));
        bean.setRsdProp10(rs.getString("RSD_PROP_10"));
        bean.setRsdBiko(rs.getString("RSD_BIKO"));
        bean.setRsdPlaCmt(rs.getString("RSD_PLA_CMT"));
        bean.setRsdImgCmt1(rs.getString("RSD_IMG_CMT1"));
        bean.setRsdImgCmt2(rs.getString("RSD_IMG_CMT2"));
        bean.setRsdImgCmt3(rs.getString("RSD_IMG_CMT3"));
        bean.setRsdImgCmt4(rs.getString("RSD_IMG_CMT4"));
        bean.setRsdImgCmt5(rs.getString("RSD_IMG_CMT5"));
        bean.setRsdImgCmt6(rs.getString("RSD_IMG_CMT6"));
        bean.setRsdImgCmt7(rs.getString("RSD_IMG_CMT7"));
        bean.setRsdImgCmt8(rs.getString("RSD_IMG_CMT8"));
        bean.setRsdImgCmt9(rs.getString("RSD_IMG_CMT9"));
        bean.setRsdImgCmt10(rs.getString("RSD_IMG_CMT10"));
        bean.setRsdIdDf(rs.getInt("RSD_ID_DF"));
        bean.setRsdSnumDf(rs.getInt("RSD_SNUM_DF"));
        bean.setRsdProp1Df(rs.getInt("RSD_PROP_1_DF"));
        bean.setRsdProp2Df(rs.getInt("RSD_PROP_2_DF"));
        bean.setRsdProp3Df(rs.getInt("RSD_PROP_3_DF"));
        bean.setRsdProp4Df(rs.getInt("RSD_PROP_4_DF"));
        bean.setRsdProp5Df(rs.getInt("RSD_PROP_5_DF"));
        bean.setRsdProp6Df(rs.getInt("RSD_PROP_6_DF"));
        bean.setRsdProp7Df(rs.getInt("RSD_PROP_7_DF"));
        bean.setRsdProp8Df(rs.getInt("RSD_PROP_8_DF"));
        bean.setRsdProp9Df(rs.getInt("RSD_PROP_9_DF"));
        bean.setRsdProp10Df(rs.getInt("RSD_PROP_10_DF"));
        bean.setRsdPlaCmtDf(rs.getInt("RSD_PLA_CMT_DF"));
        bean.setRsdImgCmt1Df(rs.getInt("RSD_IMG_CMT1_DF"));
        bean.setRsdImgCmt2Df(rs.getInt("RSD_IMG_CMT2_DF"));
        bean.setRsdImgCmt3Df(rs.getInt("RSD_IMG_CMT3_DF"));
        bean.setRsdImgCmt4Df(rs.getInt("RSD_IMG_CMT4_DF"));
        bean.setRsdImgCmt5Df(rs.getInt("RSD_IMG_CMT5_DF"));
        bean.setRsdImgCmt6Df(rs.getInt("RSD_IMG_CMT6_DF"));
        bean.setRsdImgCmt7Df(rs.getInt("RSD_IMG_CMT7_DF"));
        bean.setRsdImgCmt8Df(rs.getInt("RSD_IMG_CMT8_DF"));
        bean.setRsdImgCmt9Df(rs.getInt("RSD_IMG_CMT9_DF"));
        bean.setRsdImgCmt10Df(rs.getInt("RSD_IMG_CMT10_DF"));
        bean.setRsdBikoDf(rs.getInt("RSD_BIKO_DF"));
        bean.setRsdImgDf(rs.getInt("RSD_IMG_DF"));
        bean.setRsdSort(rs.getInt("RSD_SORT"));
        bean.setRsdApprKbn(rs.getInt("RSD_APPR_KBN"));
        bean.setRsdApprKbnDf(rs.getInt("RSD_APPR_KBN_DF"));
        bean.setRsdAuid(rs.getInt("RSD_AUID"));
        bean.setRsdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSD_ADATE")));
        bean.setRsdEuid(rs.getInt("RSD_EUID"));
        bean.setRsdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSD_EDATE")));

        //施設画像添付ファイル情報を取得
        RsvBinDao rbDao = new RsvBinDao(getCon());
        bean.setTmpFileSisetuList(
                rbDao.getWriteTmpFileList(bean.getRsdSid(), GSConstReserve.TEMP_IMG_KBN_SISETU));

        //場所画像添付ファイル情報を取得
        bean.setTmpFilePlaceList(
                rbDao.getWriteTmpFileList(bean.getRsdSid(), GSConstReserve.TEMP_IMG_KBN_PLACE));

        return bean;
    }

    /**
     * <p>指定したユーザーＩＤからユーザーSIDを取得する
     * @param loginId ログインID
     * @throws SQLException SQL実行例外
     * @return ret ユーザーSID
     */
    public int getUserSid(String loginId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   USR_LGID=?");
            sql.addSql(" and ");
            sql.addSql("   USR_JKBN<>?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(loginId);
            sql.addIntValue(GSConstUser.USER_JTKBN_DELETE);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = rs.getInt("USR_SID");
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
     * <br>[機  能] 施設IDから施設区分を取得する
     * <br>[解  説] 部屋、物品、車、書籍、その他
     * <br>[備  考]
     * @param rsdId 施設ID
     * @return 施設区分
     * @throws SQLException SQL実行時エラー
     */
    public int getSisKbn(String rsdId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("    select");
            sql.addSql("      RSV_SIS_GRP.RSK_SID as RSK_SID");
            sql.addSql("    from");
            sql.addSql("      RSV_SIS_DATA,");
            sql.addSql("      RSV_SIS_GRP");
            sql.addSql("    where");
            sql.addSql("      RSV_SIS_DATA.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql("    and");
            sql.addSql("      RSV_SIS_DATA.RSD_ID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(rsdId);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("RSK_SID");
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
     * <br>[機  能] データサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rsdSidList 施設SID
     * @return データサイズ集計
     * @throws SQLException SQL実行時例外
     */
    public long getDataSize(List<Integer> rsdSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long dataSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(RSD_NAME)) as SIZE1,");
            sql.addSql("   sum(octet_length(RSD_SNUM)) as SIZE2,");
            sql.addSql("   sum(octet_length(RSD_PROP_1)) as SIZE3,");
            sql.addSql("   sum(octet_length(RSD_PROP_2)) as SIZE4,");
            sql.addSql("   sum(octet_length(RSD_PROP_3)) as SIZE5,");
            sql.addSql("   sum(octet_length(RSD_PROP_4)) as SIZE6,");
            sql.addSql("   sum(octet_length(RSD_PROP_5)) as SIZE7,");
            sql.addSql("   sum(octet_length(RSD_PROP_6)) as SIZE8,");
            sql.addSql("   sum(octet_length(RSD_PROP_7)) as SIZE9,");
            sql.addSql("   sum(octet_length(RSD_PROP_8)) as SIZE10,");
            sql.addSql("   sum(octet_length(RSD_PROP_9)) as SIZE11,");
            sql.addSql("   sum(octet_length(RSD_PROP_10)) as SIZE12,");
            sql.addSql("   sum(octet_length(RSD_IMG_CMT1)) as SIZE13,");
            sql.addSql("   sum(octet_length(RSD_IMG_CMT2)) as SIZE14,");
            sql.addSql("   sum(octet_length(RSD_IMG_CMT3)) as SIZE15,");
            sql.addSql("   sum(octet_length(RSD_IMG_CMT4)) as SIZE16,");
            sql.addSql("   sum(octet_length(RSD_IMG_CMT5)) as SIZE17,");
            sql.addSql("   sum(octet_length(RSD_IMG_CMT6)) as SIZE18,");
            sql.addSql("   sum(octet_length(RSD_IMG_CMT7)) as SIZE19,");
            sql.addSql("   sum(octet_length(RSD_IMG_CMT8)) as SIZE20,");
            sql.addSql("   sum(octet_length(RSD_IMG_CMT9)) as SIZE21,");
            sql.addSql("   sum(octet_length(RSD_IMG_CMT10)) as SIZE22,");
            sql.addSql("   sum(octet_length(RSD_BIKO)) as SIZE23,");
            sql.addSql("   sum(octet_length(RSD_PLA_CMT)) as SIZE24");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where");
            sql.addSql("   RSD_SID in (");

            for (int idx = 0; idx < rsdSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(rsdSidList.get(idx));
            }

            sql.addSql("  )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dataSize = rs.getLong("SIZE1");
                dataSize += rs.getLong("SIZE2");
                dataSize += rs.getLong("SIZE3");
                dataSize += rs.getLong("SIZE4");
                dataSize += rs.getLong("SIZE5");
                dataSize += rs.getLong("SIZE6");
                dataSize += rs.getLong("SIZE7");
                dataSize += rs.getLong("SIZE8");
                dataSize += rs.getLong("SIZE9");
                dataSize += rs.getLong("SIZE10");
                dataSize += rs.getLong("SIZE11");
                dataSize += rs.getLong("SIZE12");
                dataSize += rs.getLong("SIZE13");
                dataSize += rs.getLong("SIZE14");
                dataSize += rs.getLong("SIZE15");
                dataSize += rs.getLong("SIZE16");
                dataSize += rs.getLong("SIZE17");
                dataSize += rs.getLong("SIZE18");
                dataSize += rs.getLong("SIZE19");
                dataSize += rs.getLong("SIZE20");
                dataSize += rs.getLong("SIZE21");
                dataSize += rs.getLong("SIZE22");
                dataSize += rs.getLong("SIZE23");
                dataSize += rs.getLong("SIZE24");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return dataSize;
    }
}