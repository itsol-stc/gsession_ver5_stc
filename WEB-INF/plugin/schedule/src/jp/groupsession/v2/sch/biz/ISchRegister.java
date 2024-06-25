package jp.groupsession.v2.sch.biz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.model.SchDataGroupModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchDataPubModel;
import jp.groupsession.v2.sch.model.SchExdataModel;
import jp.groupsession.v2.sch.model.SchPriPushModel;
/**
 *
 * <br>[機  能] スケジュール登録を実行するビジネスロジック
 * <br>[解  説] 複数スケジュール、関連テーブルの一括登録を行う
 * <br>[備  考] 登録したスケジュール 、施設予約紐づけSID等を登録実行後に取得できる。
 *
 * @author JTS
 */
public interface ISchRegister {

    /**
     *
     * <br>[機  能] スケジュール登録処理の実行
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    void regist() throws SQLException;

    /**
     *
     * <br>[機  能] 施設予約繰り返し登録拡張SID取得
     * <br>[解  説] 登録実行後に登録した情報の取得に利用する
     * <br>[備  考]
     * @return 施設予約繰り返し登録拡張SID
     */
    int getRsvExtSid();

    /**
     *
     * <br>[機  能] スケジュールグループ情報リストを取得
     * <br>[解  説] 登録実行後に登録した情報の取得に利用する
     * <br>[備  考]
     * @return スケジュールグループ情報リスト
     */
    List<SchDataGroupModel> getScdGrpsList();
    /**
     *
     * <br>[機  能] ビルダー生成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param cntCon 採番コントローラ
     * @param baseSch スケジュールモデル
     * @return ビルダー
     */
    public static Builder simpleRegistBuilder(
            Connection con,
            RequestModel reqMdl,
            MlCountMtController cntCon,
            SchDataModel baseSch
            ) {

       String clsName = "jp.groupsession.v2.sch.biz.SchRegister";

       try {
           @SuppressWarnings("unchecked")
           Class<ISchRegister> cls = (Class<ISchRegister>) Class.forName(clsName);
           Method m = cls.getMethod("simpleRegistBuilder",
                   Connection.class,
                   RequestModel.class,
                   MlCountMtController.class,
                   SchDataModel.class);
           return (Builder) m.invoke(cls, con, reqMdl, cntCon, baseSch);
       } catch (IllegalAccessException  | ClassNotFoundException
               | IllegalArgumentException | InvocationTargetException
               | NoSuchMethodException | SecurityException e) {
           throw new RuntimeException(e);
       }
    }
    /**
     *
     * <br>[機  能] ビルダー生成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param cntCon 採番コントローラ
     * @param baseSch スケジュールモデル
     * @param dateList 対象日
     * @param exMdl 繰り返し登録モデル
     * @return ビルダー
     */
    public static Builder kurikaesiRegistBuilder(
            Connection con,
            RequestModel reqMdl,
            MlCountMtController cntCon,
            SchDataModel baseSch,
            List<UDate> dateList,
            SchExdataModel exMdl
            ) {

       String clsName = "jp.groupsession.v2.sch.biz.SchRegister";

       try {
           @SuppressWarnings("unchecked")
           Class<ISchRegister> cls = (Class<ISchRegister>) Class.forName(clsName);
           Method m = cls.getMethod("kurikaesiRegistBuilder",
                   Connection.class,
                   RequestModel.class,
                   MlCountMtController.class,
                   SchDataModel.class,
                   List.class,
                   SchExdataModel.class);
           return (Builder) m.invoke(cls, con, reqMdl, cntCon, baseSch, dateList, exMdl);
       } catch (IllegalAccessException  | ClassNotFoundException
               | IllegalArgumentException | InvocationTargetException
               | NoSuchMethodException | SecurityException e) {
           throw new RuntimeException(e);
       }

    }
    /**
     *
     * <br>[機  能] ビルダー生成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param cntCon 採番コントローラ
     * @param baseSch スケジュールモデル
     * @param ikkatuKeyList 一括登録キーリスト
     * @return ビルダー
     */
    public static Builder ikkatuRegistBuilder(
            Connection con,
            RequestModel reqMdl,
            MlCountMtController cntCon,
            SchDataModel baseSch,
            List<String> ikkatuKeyList
            ) {

       String clsName = "jp.groupsession.v2.sch.biz.SchRegister";

       try {
           @SuppressWarnings("unchecked")
           Class<ISchRegister> cls = (Class<ISchRegister>) Class.forName(clsName);
           Method m = cls.getMethod("ikkatuRegistBuilder",
                   Connection.class,
                   RequestModel.class,
                   MlCountMtController.class,
                   SchDataModel.class,
                   List.class);
           return (Builder) m.invoke(cls, con, reqMdl, cntCon, baseSch, ikkatuKeyList);
       } catch (IllegalAccessException  | ClassNotFoundException
               | IllegalArgumentException | InvocationTargetException
               | NoSuchMethodException | SecurityException e) {
           throw new RuntimeException(e);
       }

    }

    public static interface Builder {

        /**
         * <p>con をセットします。
         * @param con con
         * @return this;
         */
        Builder setCon(Connection con);

        /**
         * <p>reqMdl をセットします。
         * @param reqMdl reqMdl
         * @return this;
         */
        Builder setReqMdl(RequestModel reqMdl);

        /**
         * <p>binSidList をセットします。
         * @param binSidList binSidList
         * @return this;
         */
        Builder setBinSidList(List<Long> binSidList);

        /**
         * <p>pubList をセットします。
         * @param pubList pubList
         * @return this;
         */
        Builder setPubList(List<SchDataPubModel> pubList);

        /**
         * <p>acoSidArr をセットします。
         * @param acoSidArr acoSidArr
         * @return this;
         */
        Builder setAcoSidArr(String[] acoSidArr);

        /**
         * <p>abaSidArr をセットします。
         * @param abaSidArr abaSidArr
         * @return this;
         */
        Builder setAbaSidArr(String[] abaSidArr);

        /**
         * <p>adrSidArr をセットします。
         * @param adrSidArr adrSidArr
         * @return this;
         */
        Builder setAdrSidArr(String[] adrSidArr);

        /**
         * <p>登録対象ユーザをセットします。
         * @param users users
         * @return this;
         */
        Builder setUsers(Set<Integer> users);

        /**
         * <p>oldAttendMap をセットします。
         * @param oldAttendMap oldAttendMap
         * @return this;
         */
        Builder setOldAttendMap(Map<Integer, Integer> oldAttendMap);

        /**
         * <p>oldAttendCommentMap をセットします。
         * @param oldAttendCommentMap oldAttendCommentMap
         * @return this;
         */
        Builder setOldAttendCommentMap(Map<Integer, String> oldAttendCommentMap);

        /**
         * <p>oldPushMap をセットします。
         * @param oldPushMap oldPushMap
         * @return this;
         */
        Builder setOldPushMap(Map<Integer, SchPriPushModel> oldPushMap);

        /**
         * <p>schExtSid をセットします。
         * @param schExtSid schExtSid
         * @return this;
         */
        Builder setSchExtSid(int schExtSid);

        /**
         * <p>rsvExtSid をセットします。
         * @param rsvExtSid rsvExtSid
         * @return this;
         */
        Builder setRsvExtSid(int rsvExtSid);

        /**
         * <p>isUseRsv をセットします。
         * @param isUseRsv 施設予約の利用の有無
         * @return this;
         */
        Builder setUseRsv(boolean isUseRsv);

        /**
         * <p>isUseContact をセットします。
         * @param isUseContact isUseContact
         * @return this;
         */
        Builder setUseContact(boolean isUseContact);

        /**
         * <p>schResSidMap をセットします。
         * @param schResSidMap schResSidMap
         * @return this;
         */
        Builder setSchResSidMap(Map<String, Integer> schResSidMap);

        /**
         *
         * <br>[機  能] ビルド実行
         * <br>[解  説]
         * <br>[備  考]
         * @return 生成したインスタンス
         * @throws SQLException
         */
        ISchRegister build() throws SQLException;

        /**
         * <p>oldSchGrpSid をセットします。
         * @param oldSchGrpSid oldSchGrpSid
         * @return this
         * @see jp.groupsession.v2.sch.biz.SchRegister.Builder#oldSchGrpSid__
         */
        jp.groupsession.v2.sch.biz.SchRegister.Builder setOldSchGrpSid(
                int oldSchGrpSid);

    }

    /**
     * <p>登録した スケジュールSID Map を取得します。
     * @return スケジュールSIDマップ { スケジュール登録グループ : { 対象SID : スケジュールSID } }
     * @see jp.groupsession.v2.sch.biz.SchRegister#scdGrpsSidMap__
     */
    Map<SchDataGroupModel, Map<Integer, Integer>> getScdSidMap();

    /**
     *
     * <br>[機  能] スケジュールモデルを取得する
     * <br>[解  説] 引数とベーススケジュールを基に生成して返す
     * <br>[備  考] push通知情報、出席確認情報は反映されていない
     * @param grp スケジュール登録グループ
     * @param targetSid スケジュール登録対象SID
     * @return スケジュールモデル
     */
    SchDataModel getSchModel(SchDataGroupModel grp, int targetSid);
    

}