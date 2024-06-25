package jp.groupsession.v2.rsv.biz;

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
import jp.groupsession.v2.rsv.model.RsvDataPubModel;
import jp.groupsession.v2.rsv.model.RsvSisKyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisRyrkModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.rsv.model.RsvYrkGroupModel;

public interface IRsvYoyakuRegister {

    /**
     *
     * <br>[機  能] 登録処理の実行
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
     * <br>[機  能] 施設予約グループ情報リストを取得
     * <br>[解  説] 登録実行後に登録した情報の取得に利用する
     * <br>[備  考]
     * @return スケジュールグループ情報リスト
     */
    List<RsvYrkGroupModel> getRsyGrpsList();
    /**
     * <p>rsyGrpsSidMap を取得します。
     * @return rsyGrpsSidMap
     * @see jp.groupsession.v2.rsv.biz.RsvYoyakuRegister#rsyGrpsSidMap__
     */
    /**
     *
     * <br>[機  能] 施設予約グループ毎の施設予約SIDマップを返す
     * <br>[解  説] 登録実行後に登録した情報の取得に利用する
     * <br>[備  考]
     * @return 施設予約SIDマップ { 施設グループ : { 施設SID : 施設予約SID } }
     */
    Map<RsvYrkGroupModel, Map<Integer, Integer>> getRsySidMap();

    /**
     *
     * <br>[機  能] ビルダー生成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param cntCon 採番コントローラ
     * @param appRootPath アプリケーションパス
     * @param baseYrk スケジュールモデル
     * @return ビルダー
     */
    public static Builder simpleRegistBuilder(
            Connection con,
            RequestModel reqMdl,
            MlCountMtController cntCon,
            String appRootPath,
            RsvSisYrkModel baseYrk
            ) {
       String clsName = "jp.groupsession.v2.rsv.biz.RsvYoyakuRegister";

       try {
           @SuppressWarnings("unchecked")
           Class<IRsvYoyakuRegister> cls = (Class<IRsvYoyakuRegister>) Class.forName(clsName);
           Method m = cls.getMethod("simpleRegistBuilder",
                   Connection.class,
                   RequestModel.class,
                   MlCountMtController.class,
                   String.class,
                   RsvSisYrkModel.class);
           return (Builder) m.invoke(cls, con, reqMdl, cntCon, appRootPath, baseYrk);
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
     * @param appRootPath アプリケーションパス
     * @param baseYrk スケジュールモデル
     * @param dateList 対象日
     * @param exMdl 繰り返し登録モデル
     * @return ビルダー
     */
    public static Builder kurikaesiRegistBuilder(
            Connection con,
            RequestModel reqMdl,
            MlCountMtController cntCon,
            String appRootPath,
            RsvSisYrkModel baseYrk,
            List<UDate> dateList,
            RsvSisRyrkModel exMdl
            ) {

        String clsName = "jp.groupsession.v2.rsv.biz.RsvYoyakuRegister";

        try {
            @SuppressWarnings("unchecked")
            Class<IRsvYoyakuRegister> cls = (Class<IRsvYoyakuRegister>) Class.forName(clsName);
            Method m = cls.getMethod("kurikaesiRegistBuilder",
                   Connection.class,
                   RequestModel.class,
                   MlCountMtController.class,
                   String.class,
                   RsvSisYrkModel.class,
                   List.class,
                   RsvSisRyrkModel.class);
            return (Builder) m.invoke(
                    cls, con, reqMdl, cntCon, appRootPath, baseYrk, dateList, exMdl);
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
     * @param appRootPath アプリケーションパス
     * @param baseYrk スケジュールモデル
     * @param ikkatuKeyList 一括登録キーリスト
     * @return ビルダー
     */
    public static Builder ikkatuRegistBuilder(
            Connection con,
            RequestModel reqMdl,
            MlCountMtController cntCon,
            String appRootPath,
            RsvSisYrkModel baseYrk,
            List<String> ikkatuKeyList
            ) {
        String clsName = "jp.groupsession.v2.rsv.biz.RsvYoyakuRegister";

        try {
            @SuppressWarnings("unchecked")
            Class<IRsvYoyakuRegister> cls = (Class<IRsvYoyakuRegister>) Class.forName(clsName);
            Method m = cls.getMethod("ikkatuRegistBuilder",
                   Connection.class,
                   RequestModel.class,
                   MlCountMtController.class,
                   String.class,
                   RsvSisYrkModel.class,
                   List.class);
            return (Builder) m.invoke(
                    cls, con, reqMdl, cntCon, appRootPath, baseYrk, ikkatuKeyList);
        } catch (IllegalAccessException  | ClassNotFoundException
               | IllegalArgumentException | InvocationTargetException
               | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     *
     * <br>[機  能] ビルダーインタフェース
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static interface Builder {


        /**
         * <p>pubList をセットします。
         * @param pubList pubList
         * @return this
         * @see jp.groupsession.v2.rsv.biz.RsvYoyakuRegister.Builder#pubList__
         */
        Builder setPubList(List<RsvDataPubModel> pubList);

        /**
         * <p>schResSidMap をセットします。
         * @param schResSidMap schResSidMap
         * @return this
         * @see jp.groupsession.v2.rsv.biz.RsvYoyakuRegister.Builder#schResSidMap__
         */
        Builder setSchResSidMap(Map<String, Integer> schResSidMap);

        /**
         * <p>isUseSch をセットします。
         * @param isUseSch isUseSch
         * @return this
         * @see jp.groupsession.v2.rsv.biz.Builder#isUseSch__
         */
        Builder setUseSch(boolean isUseSch);

        /**
         * <p>rsdSids をセットします。
         * @param rsdSids rsdSids
         * @return this
         * @see jp.groupsession.v2.rsv.biz.RsvYoyakuRegister.Builder#rsdSids__
         */
        Builder setRsdSids(Set<Integer> rsdSids);
        /**
         * <p>kyrkMap をセットします。
         * @param kyrkMap kyrkMap
         * @return this
         * @see jp.groupsession.v2.rsv.biz.RsvYoyakuRegister.Builder#kyrkMap__
         */
        Builder setKyrkMap(
                Map<Integer, RsvSisKyrkModel> kyrkMap);

        /**
         * <br>[機  能] ビルド実行
         * <br>[解  説]
         * <br>[備  考]
         * @return 生成したインスタンス
         * @throws SQLException SQL実行時例外
         */
        RsvYoyakuRegister build() throws SQLException;


    }


}