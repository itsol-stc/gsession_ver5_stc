package jp.groupsession.v2.api.reserve.yoyaku.search;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.RelationBetweenScdAndRsvChkBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.biz.RsvScheduleBiz;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.other.RsvSchAdmConfModel;
import jp.groupsession.v2.rsv.model.other.ScheduleRsvModel;
import jp.groupsession.v2.rsv.rsv100.Rsv100SisYrkModel;
import jp.groupsession.v2.rsv.rsv100.Rsv100searchModel;
import jp.groupsession.v2.rsv.rsv110.Rsv110Biz;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiRsvSisYrkSearchBiz  extends AbstractReserveBiz {
    /** ログ */
    private static Log log__ =
            LogFactory.getLog(ApiRsvSisYrkSearchBiz.class);

    /**
     *
     * <br>[機  能] org.jdom2.Documentインスタンスを生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param param 検索パラメータ
     * @return org.jdom2.Document
     * @throws SQLException SQL実行時例外
     */
    public Document createDoc(
            Connection con, RequestModel reqMdl,
            ApiRsvSisYrkSearchParamModel param) throws SQLException {
        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        RsvSisYrkDao dao = new RsvSisYrkDao(con);
        Attribute atMaxCount = new Attribute("TotalCount", Integer.toString(0));
        Attribute atCount = new Attribute("Count", Integer.toString(0));
        int start = NullDefault.getInt(param.getStart(), 0);
        int results = NullDefault.getInt(param.getResults(), 50);
        resultSet.setAttribute("Start", Integer.toString(start));
        Rsv100searchModel searchModel = makeSearchModel(param, reqMdl.getSmodel().getUsrsid());

        Map<Integer, RsvRsSchedule> sameUsrMap
        = new HashMap<Integer, ApiRsvSisYrkSearchBiz.RsvRsSchedule>();
        HashMap<Integer, RsvSisDataModel> rsvSisMap
        = new HashMap<Integer, RsvSisDataModel>();

        ArrayList<Rsv100SisYrkModel> list = new ArrayList<Rsv100SisYrkModel>();
        boolean adminKbn = isAdmin(reqMdl, con);
        //全体行数
        int maxCnt = dao.getYrkReferenceCount(searchModel, adminKbn);
        atMaxCount.setValue(Integer.toString(maxCnt));

        //検索結果リストを取得
        list = dao.getYrkReferenceList(searchModel, adminKbn, start, results, reqMdl);
        atCount.setValue(Integer.toString(list.size()));

        __putRelationMap(con, reqMdl, list, sameUsrMap, rsvSisMap);

        List<Integer> ablAcsessRsgSidList =
                __getAbleAcsessRsgSidList(con, reqMdl, list, adminKbn);
        List<Integer> editableRsySidList =
                __getEditableRsySidList(con, reqMdl, list, ablAcsessRsgSidList);
        List<Integer> viewableRsySidList =
                __getViewableRsySidList(con, reqMdl, list, ablAcsessRsgSidList);

        for (Rsv100SisYrkModel yrkMdl : list) {
            Element sisYrk = new Element("Result");
            resultSet.addContent(sisYrk);
            // 施設予約SID
            sisYrk.addContent(__createContent("RsySid",
                    String.valueOf(yrkMdl.getRsySisetuSid())));
            // 施設グループSID
            sisYrk.addContent(__createContent("RsgSid", String.valueOf(yrkMdl.getRsgSid())));
            // 施設SID
            sisYrk.addContent(__createContent("RsdSid", String.valueOf(yrkMdl.getRsdSid())));
            // 施設区分SID
            sisYrk.addContent(__createContent("RskSid", String.valueOf(yrkMdl.getRskSid())));
            // 施設名
            sisYrk.addContent(__createContent("RsdName", yrkMdl.getRsySisetu()));
            // 利用開始日   yyyy/MM/dd hh:mm:ss
            sisYrk.addContent(__createContent("StartDateTime",
                    yrkMdl.getRsyFrom()));
            // 利用終了日   yyyy/MM/dd hh:mm:ss
            sisYrk.addContent(__createContent("EndDateTime",
                    yrkMdl.getRsyTo()));
            // 利用目的
            sisYrk.addContent(__createContent("Title", String.valueOf(yrkMdl.getRsyContent())));

            // 内容
            //施設予約の公開範囲内の場合のみ表示
            if (viewableRsySidList.contains(yrkMdl.getRsySisetuSid())) {
                sisYrk.addContent(__createContent("Body", String.valueOf(yrkMdl.getRsyBiko())));
            }
            // 承認状況    0:通常（承認済）,1:承認待
            sisYrk.addContent(__createContent("Status",
                    String.valueOf(yrkMdl.getRsyApprStatus())));
            // 承認区分    0:未承認,1:承認,2:否認
            sisYrk.addContent(__createContent("ApprKbn",
                    String.valueOf(yrkMdl.getRsyApprKbn())));

            //Busyo   担当部署
            sisYrk.addContent(__createContent("Busyo",
                    NullDefault.getString(yrkMdl.getRkyBusyo(), "")));

            //Tanto   担当者名
            sisYrk.addContent(__createContent("Tanto",
                    NullDefault.getString(yrkMdl.getRkyName(), "")));
            //OtherUsrCount   その他人数
            sisYrk.addContent(__createContent("OtherUsrCount",
                    NullDefault.getString(yrkMdl.getRkyNum(), "")));
            //UseKbn  利用区分    0:未設定 1:会議 2:研修 3:その他
            sisYrk.addContent(__createContent("UseKbn",
                    String.valueOf(yrkMdl.getRkyUseKbn())));

            RsvSisDataModel sisMdl = rsvSisMap.get(yrkMdl.getRsdSid());

            //RsdIｄ   施設ID
            sisYrk.addContent(__createContent("RsdId", sisMdl.getRsdId()));
            // 資産管理番号
            sisYrk.addContent(__createContent("RsdKanriNo", sisMdl.getRsdSnum()));
            // 備考
            sisYrk.addContent(__createContent("Biko", sisMdl.getRsdBiko()));
            // 場所
            sisYrk.addContent(__createContent("Place", sisMdl.getRsdPlaCmt()));

            //    以下のフィールドは特定の施設区分によって追加される
            Integer rskSid = yrkMdl.getRskSid();
            if (rskSid == GSConstReserve.RSK_KBN_HEYA
                    || rskSid == GSConstReserve.RSK_KBN_CAR) {
                //    ＜施設区分が部屋または車の場合＞
                // 座席数/乗員数
                sisYrk.addContent(__createContent("ZasekiCount", sisMdl.getRsdProp1()));
                // 喫煙
                sisYrk.addContent(__createContent("AbleSmoke", sisMdl.getRsdProp2()));
                //Contact 連絡先
                sisYrk.addContent(__createContent("Contact",
                        NullDefault.getString(yrkMdl.getRkyContact(), "")));
            }
            if (rskSid == GSConstReserve.RSK_KBN_BUPPIN
                    || rskSid == GSConstReserve.RSK_KBN_BOOK) {
                //    ＜施設区分が物品または書籍の場合＞
                // 個数（冊数）
                sisYrk.addContent(__createContent("Kosuu", sisMdl.getRsdProp1()));
                // 社外持出
                sisYrk.addContent(__createContent("Motidashi", sisMdl.getRsdProp3()));
            }

            if (rskSid == GSConstReserve.RSK_KBN_CAR) {
                //    ＜施設区分が車の場合＞
                // ナンバー
                sisYrk.addContent(__createContent("SyaryouNp", sisMdl.getRsdProp4()));
                //Destination 行き先
                sisYrk.addContent(__createContent("Destination",
                        NullDefault.getString(yrkMdl.getRkyDest(), "")));
            }
            if (rskSid == GSConstReserve.RSK_KBN_BOOK) {
                //    ＜施設区分が書籍の場合＞
                // ISBN
                sisYrk.addContent(__createContent("ISBN", sisMdl.getRsdProp5()));
            }

            //以下のフィールドは施設が部屋の場合に追加される
            if (yrkMdl.getRskSid() == GSConstReserve.RSK_KBN_HEYA) {
                //Guide   会議名案内
                sisYrk.addContent(__createContent("Guide",
                        NullDefault.getString(yrkMdl.getRkyGuide(), "")));
                //ParkCount   駐車場見込み台数
                sisYrk.addContent(__createContent("ParkCount",
                        NullDefault.getString(yrkMdl.getRkyParkNum(), "")));
            }

            // 編集権限区分  0:制限無し,1:本人・登録者のみ,2:所属グループ・登録者のみ
            sisYrk.addContent(__createContent("EditKbn", String.valueOf(yrkMdl.getRsyEdit())));
            // 編集権限    0:編集不可 1:編集可能
            sisYrk.addContent(__createContent("AbleEdit",
                    editableRsySidList.contains(yrkMdl.getRsySisetuSid()) ? "1" : "0"
                    ));

            //公開区分 0:公開,1:予定有り,2:所属グループのみ
            sisYrk.addContent(__createContent("Public", String.valueOf(yrkMdl.getRsyPublic())));
            //閲覧権限 0:閲覧不可 1:閲覧可
            sisYrk.addContent(__createContent("AbleView",
                    viewableRsySidList.contains(yrkMdl.getRsySisetuSid()) ? "1" : "0"
                    ));

            // 利用者ユーザSID
            sisYrk.addContent(__createContent("AddUsrSid",
                    String.valueOf(yrkMdl.getRsyAuid())));
            // 利用者名
            sisYrk.addContent(__createContent("AddUsrName", yrkMdl.getRsySeiMei()));
            // 利用者ユーザ無効フラグ
            sisYrk.addContent(__createContent("AddUsrUkoFlg",
                                           String.valueOf(yrkMdl.getUsrUkoFlg())));

            // 登録日時    yyyy/MM/dd hh:mm:ss
            sisYrk.addContent(__createContent("AdateTime", new StringBuilder()
            .append(UDateUtil.getSlashYYMD(yrkMdl.getRsyAdate())).append(" ")
            .append(UDateUtil.getSeparateHMS(yrkMdl.getRsyAdate())).toString()
                    ));
            // 編集日時    yyyy/MM/dd hh:mm:ss
            sisYrk.addContent(__createContent("EdateTime", new StringBuilder()
            .append(UDateUtil.getSlashYYMD(yrkMdl.getRsyEdate())).append(" ")
            .append(UDateUtil.getSeparateHMS(yrkMdl.getRsyEdate())).toString()
                    ));

            //以下のフィールドはスケジュール登録がある場合追加される
            if (sameUsrMap.containsKey(yrkMdl.getScdRsSid())) {
                RsvRsSchedule rsSchMdl = sameUsrMap.get(yrkMdl.getScdRsSid());
                int scdSid = rsSchMdl.getSchSid();

                // スケジュールSID
                sisYrk.addContent(
                            __createContent("SchSid", String.valueOf(scdSid))
                        );

                //スケジュールを閲覧可能かを判定する
                RelationBetweenScdAndRsvChkBiz scdRsvBiz
                    = new RelationBetweenScdAndRsvChkBiz(reqMdl, con);

                int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
                int scdUsrKbn = rsSchMdl.getUserKbn();
                boolean schAccessFlg = false;
                if (scdUsrKbn == GSConstSchedule.USER_KBN_USER) {
                    //ユーザスケジュール
                    List<CmnUsrmInfModel> userList = rsSchMdl.getUsers();
                    if (userList != null && !userList.isEmpty()) {
                        List<CmnUsrmInfModel> accessUserList = new ArrayList<CmnUsrmInfModel>();

                        for (CmnUsrmInfModel userData : userList) {
                            if (scdRsvBiz.canAccessSchedule(
                                    userData.getUsrSid(),
                                    scdUsrKbn, sessionUsrSid)) {
                                accessUserList.add(userData);
                            }
                        }
                        rsSchMdl.setUsers(accessUserList);
                        schAccessFlg = !accessUserList.isEmpty();
                    }
                } else {
                    schAccessFlg = scdRsvBiz.canAccessSchedule(
                                                        rsSchMdl.getGroup().getKey(),
                                                        scdUsrKbn, sessionUsrSid);
                }

                if (schAccessFlg) {
                    if (scdUsrKbn == GSConstSchedule.USER_KBN_USER
                            && rsSchMdl.getUsers() != null) {
                        List<CmnUsrmInfModel> userList = rsSchMdl.getUsers();
                        //＜ユーザスケジュールの場合＞
                        // 同時登録ユーザ情報配列
                        Element users = new Element("SameScheduleUserSet");
                        //次の属性を持つ
                        // 検索結果にマッチした件数
                        users.setAttribute("Count", String.valueOf(rsSchMdl.getUsers().size()));
                        sisYrk.addContent(users);
                        for (CmnUsrmInfModel cmnUsrmInfModel__ : userList) {
                            Element user = new Element("User");
                            users.addContent(user);
                            // ユーザ名
                            user.addContent(__createContent("Name",
                                    new StringBuilder(cmnUsrmInfModel__.getUsiSei())
                                            .append(" ").append(cmnUsrmInfModel__.getUsiMei())
                                            .toString()));
                            // ユーザSID
                            user.addContent(__createContent("UsrSid",
                                    String.valueOf(cmnUsrmInfModel__.getUsrSid())));
                            // ユーザ無効フラグ
                            user.addContent(__createContent("UsrUkoFlg",
                                    String.valueOf(cmnUsrmInfModel__.getUsrUkoFlg())));
                        }
                    }
                    if (rsSchMdl.getUserKbn() == GSConstSchedule.USER_KBN_GROUP) {
                        //＜グループスケジュールの場合＞
                        // グループ  SID
                        sisYrk.addContent(__createContent("GrpSid",
                                String.valueOf(rsSchMdl.getGroup().getKey())));
                        // グループ名
                        sisYrk.addContent(__createContent("GrpName",
                                String.valueOf(rsSchMdl.getGroup().getValue())));
                    }
                }
            }

        }

        resultSet.setAttribute(atCount);
        resultSet.setAttribute(atMaxCount);

        if (list.size() <= 0) {
            log__.debug("検索結果がありません");
        }

        return doc;
    }

    /**
     * <br>[機  能] org.jdom2.Elementインスタンスを生成します。
     * <br>[解  説]
     * <br>[備  考] content内のXML利用不可文字は削除されます。
     * @param name 名称
     * @param value 内容
     * @return org.jdom2.Elementインスタンス
     */
    private Element __createContent(String name, String value) {
        Element elm = new Element(name);

        elm.addContent(value);
        return elm;
    }

    /**
     * <br>[機  能] 管理者フラグを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return true:管理者 false:非管理者
     * @throws SQLException SQL実行時例外
     */
    protected boolean isAdmin(RequestModel reqMdl, Connection con)
    throws SQLException {

        BaseUserModel usModel = reqMdl.getSmodel();
        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, usModel, GSConstReserve.PLUGIN_ID_RESERVE);

        return adminUser;
    }

    /**
     *
     * <br>[機  能] 探索モデル作成
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータモデル
     * @param sessionUsrSid セッションユーザSID
     * @return 探索モデル
     */
    public Rsv100searchModel makeSearchModel(
            ApiRsvSisYrkSearchParamModel param, int sessionUsrSid) {
        Rsv100searchModel mdl = new Rsv100searchModel();

        boolean dateKbn = (param.getStartTime() != null || param.getEndTime() != null);

        mdl.setRsvDateKbn(dateKbn);
        if (dateKbn) {
            //fromの作成
            String fromStr = NullDefault.getString(param.getStartTime(), "0001/01/01 00:00:00");
            UDate from = __makeSearchDateTime(fromStr, false);

            //toの作成
            String toStr = NullDefault.getString(param.getEndTime(), "9999/12/31 23:59:59");
            UDate to = __makeSearchDateTime(toStr, false);

            mdl.setRsvFrom(from);
            mdl.setRsvTo(to);
        }

        int rsgSid = NullDefault.getInt(param.getRsgSid(), 0);
        mdl.setRsvGrp1(rsgSid);

        int svGrp2 = -1;
        if (rsgSid == 0) {
            svGrp2 = 0;
        } else {
            svGrp2 = NullDefault.getInt(param.getRsdSid(), 0);
        }
        mdl.setRsvGrp2(svGrp2);

        //キーワード
        if (!StringUtil.isNullZeroString(param.getKeyWord())) {
            List<String> keywordList = new ArrayList<String>();
            String searchKey =
                    StringUtil.substitute(param.getKeyWord(), "　", " ");
            StringTokenizer st = new StringTokenizer(searchKey, " ");
            while (st.hasMoreTokens()) {
                String str = st.nextToken();
                if (!StringUtil.isNullZeroString(str)) {
                    keywordList.add(str);
                }
            }
            mdl.setRsvKeyWord(keywordList);
        }

        //検索条件
        mdl.setRsvSearchCondition(NullDefault.getInt(param.getKeyWordKbn(), 0));

        //検索対象(利用目的)
        mdl.setRsvTargetMok(NullDefault.getInt(param.getKeytitle(), 0) ^ 1);

        //検索対象(内容)
        mdl.setRsvTargetNiyo(NullDefault.getInt(param.getKeybody(), 0) ^ 1);

        //登録日FROM addTimeFrom
        mdl.setRsvADateFrom(__makeSearchDateTime(param.getAddTimeFrom(), false));
        //登録日TO   addTimeTo
        mdl.setRsvADateTo(__makeSearchDateTime(param.getAddTimeTo(), true));
        //編集日FROM EditTimeFrom
        mdl.setRsvEDateFrom(__makeSearchDateTime(param.getEditTimeFrom(), false));
        //編集日TO   EditTimeTo
        mdl.setRsvEDateTo(__makeSearchDateTime(param.getEditTimeTo(), true));

        //承認状況
        mdl.setRsvApprStatus(NullDefault.getInt(param.getStatusKbn(), 0));

        //第一ソートキー
        mdl.setRsvSelectedKey1(NullDefault.getInt(param.getSort1(), 2));

        //第二ソートキー
        mdl.setRsvSelectedKey2(NullDefault.getInt(param.getSort2(), 3));

        //第一ソートキー ソート
        mdl.setRsvSelectedKey1Sort(NullDefault.getInt(param.getOrder1(), 0));

        //第二ソートキー ソート
        mdl.setRsvSelectedKey2Sort(NullDefault.getInt(param.getOrder2(), 0));

        //セッションユーザSIDを設定
        mdl.setUsrSid(sessionUsrSid);

        return mdl;
    }

    /**
     *
     * <br>[機  能] 文字列から検索用日付型に変換
     * <br>[解  説]
     * <br>[備  考]
     * @param dateStr 日付文字列
     * @param isTo 終端日付
     * @return UDate
     */
    private UDate __makeSearchDateTime(String dateStr, boolean isTo) {
        if (dateStr == null) {
            return null;
        }
        UDate ret = UDateUtil.getUDate(dateStr.substring(0, 4),
                dateStr.substring(5, 7),
                dateStr.substring(8, 10));
//      if (isTo) {
//          ret.setMaxHhMmSs();
//      } else {
//          ret.setZeroHhMmSs();
//      }
//      if (dateStr.length() > 10) {
        ret.setHour(Integer.valueOf(dateStr.substring(11, 13)));
        ret.setMinute(Integer.valueOf(dateStr.substring(14, 16)));
        ret.setSecond(Integer.valueOf(dateStr.substring(17, 19)));

//      }
        return ret;
    }

    /**
     *
     * <br>[機  能] 施設予約関連スケジュールモデル
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    private static class RsvRsSchedule {
        /** スケジュールSID*/
        private int schSid__;
        /** ユーザ区分*/
        private int userKbn__;
        /** 同時登録ユーザ*/
        private List<CmnUsrmInfModel> users__;
        /** 同時登録グループ*/
        private SimpleEntry<Integer, String> group__;
        /**
         * <p>schSid を取得します。
         * @return schSid
         */
        public int getSchSid() {
            return schSid__;
        }
        /**
         * <p>schSid をセットします。
         * @param schSid schSid
         */
        public void setSchSid(int schSid) {
            schSid__ = schSid;
        }
        /**
         * <p>userKbn を取得します。
         * @return userKbn
         */
        public int getUserKbn() {
            return userKbn__;
        }
        /**
         * <p>userKbn をセットします。
         * @param userKbn userKbn
         */
        public void setUserKbn(int userKbn) {
            userKbn__ = userKbn;
        }
        /**
         * <p>users を取得します。
         * @return users
         */
        public List<CmnUsrmInfModel> getUsers() {
            return users__;
        }
        /**
         * <p>users をセットします。
         * @param users users
         */
        public void setUsers(List<CmnUsrmInfModel> users) {
            users__ = users;
        }
        /**
         * <p>group を取得します。
         * @return group
         */
        public SimpleEntry<Integer, String> getGroup() {
            return group__;
        }
        /**
         * <p>group をセットします。
         * @param group group
         */
        public void setGroup(SimpleEntry<Integer, String> group) {
            group__ = group;
        }
    }

    /**
     *
     * <br>[機  能] 関連Mapの設定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param list 施設予約一覧
     * @param targetUsrShedule 保存先施設関連スケジュールマップ
     * @param targetRsvSisData 保存先施設情報マップ
     * @throws SQLException SQL実行時例外
     *
     */
    private void __putRelationMap(
            Connection con,
            RequestModel reqMdl,
            List<Rsv100SisYrkModel> list,
            Map<Integer, RsvRsSchedule> targetUsrShedule,
            Map<Integer, RsvSisDataModel> targetRsvSisData
            ) throws SQLException {
        RsvScheduleBiz rsvSchBiz = new RsvScheduleBiz();

        //スケジュール管理者設定(共有範囲など)を取得
        RsvSchAdmConfModel adminConf = rsvSchBiz.getAdmConfModel(con);

        HashMap<Integer, ScheduleRsvModel> rsvScdMap = new HashMap<Integer, ScheduleRsvModel>();
        ArrayList<Integer> rsdSids = new ArrayList<Integer>();
        for (Rsv100SisYrkModel rsv100SisYrkModel__ : list) {
            __putUsrSheduleMap(con, reqMdl, rsv100SisYrkModel__,
                    targetUsrShedule, rsvScdMap, adminConf);

            rsdSids.add(rsv100SisYrkModel__.getRsdSid());
        }
        RsvSisDataDao dataDao = new RsvSisDataDao(con);

        List<RsvSisDataModel> rsdList = dataDao.selectSisetuList(rsdSids);
        for (RsvSisDataModel rsvSisDataModel__ : rsdList) {
            targetRsvSisData.put(rsvSisDataModel__.getRsdSid(), rsvSisDataModel__);
        }

    }

    /**
     *
     * <br>[機  能] 関連スケジュールデータをマップに追加
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param mdl 施設予約モデル
     * @param targetUsrShedule 保存先施設スケジュールマップ
     * @param rsvScdMap 保存先スケジュールマップ
     * @param adminConf 管理者権限情報
     * @throws SQLException SQL実行時例外
     *
     */
    private void __putUsrSheduleMap(
            Connection con,
            RequestModel reqMdl,
            Rsv100SisYrkModel mdl,
            Map<Integer, RsvRsSchedule> targetUsrShedule,
            Map<Integer, ScheduleRsvModel> rsvScdMap,
            RsvSchAdmConfModel adminConf
            ) throws SQLException {
        int rsSid = mdl.getScdRsSid();
        if (rsSid > 0) {
            if (targetUsrShedule.containsKey(Integer.valueOf(rsSid))) {
                return;
            }
            Rsv110Biz rsv110Biz = new Rsv110Biz(reqMdl, con);

            int scdSid = rsv110Biz.getScdSid(rsSid);
            if (scdSid > 0) {
                ScheduleRsvModel schMdl;
                schMdl = rsvScdMap.get(Integer.valueOf(scdSid));
                if (schMdl == null) {
                    schMdl =
                            rsv110Biz.getSchData(reqMdl, scdSid, adminConf, con);
                    rsvScdMap.put(scdSid, schMdl);
                }
                RsvRsSchedule users = new RsvRsSchedule();
                users.setSchSid(scdSid);
                if (schMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
                    users.setUserKbn(GSConstSchedule.USER_KBN_GROUP);
                    users.setGroup(new SimpleEntry<Integer, String>(
                            schMdl.getScdUsrSid(), schMdl.getScdUsrSei()));
                } else {
                    users.setUserKbn(GSConstSchedule.USER_KBN_USER);
                    if (schMdl.getUsrInfList() == null
                            || schMdl.getUsrInfList().isEmpty()) {
                        ArrayList<CmnUsrmInfModel> myList =
                                new ArrayList<CmnUsrmInfModel>();
                        CmnUsrmInfModel myMdl = new CmnUsrmInfModel();
                        myMdl.setUsrSid(schMdl.getScdUsrSid());
                        myMdl.setUsiSei(schMdl.getScdUsrSei());
                        myMdl.setUsiMei(schMdl.getScdUsrMei());
                        myMdl.setUsrUkoFlg(schMdl.getScdUsrUkoFlg());

                        myList.add(myMdl);
                        schMdl.setUsrInfList(myList);
                    }
                    users.setUsers(schMdl.getUsrInfList());
                }
                targetUsrShedule.put(rsSid, users);
            }

        }

    }

    /**
     *
     * <br>[機  能] 施設予約一覧からアクセス可能な施設グループのSID一覧を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param list 施設予約一覧
     * @param admFlg 管理者権限
     * @return アクセス可能施設グループSID一覧
     * @throws SQLException SQL実行時例外
     */
    private List<Integer> __getAbleAcsessRsgSidList(Connection con,
            RequestModel reqMdl,
            List<Rsv100SisYrkModel> list,
            boolean admFlg) throws SQLException {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        HashSet<Integer> checked = new HashSet<Integer>();
        /***********************************************
         *
         * アクセス権限を判定
         *
         ***********************************************/
        for (Rsv100SisYrkModel mdl : list) {
            int rsgSid = mdl.getRsgSid();
            if (checked.contains(rsgSid)) {
                continue;
            }
            boolean accessOkFlg = false;
            accessOkFlg = _isEditRsvGrp(con, rsgSid, reqMdl.getSmodel().getUsrsid(), admFlg);
            if (accessOkFlg) {
                ret.add(rsgSid);
            }
            checked.add(rsgSid);
        }
        return ret;
    }

    /**
     *
     * <br>[機  能] 編集権限のある施設予約SIDリストの取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param list 施設予約一覧
     * @param rsgSidList アクセス可能施設SIDリスト
     * @throws SQLException SQL実行時例外
     * @return 編集権限のある施設予約SIDリスト
     */
    private List<Integer> __getEditableRsySidList(Connection con,
            RequestModel reqMdl,
            List<Rsv100SisYrkModel> list,
            List<Integer> rsgSidList
            ) throws SQLException {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        ArrayList<Rsv100SisYrkModel> roop = new ArrayList<Rsv100SisYrkModel>(list);
        for (Rsv100SisYrkModel mdl : roop) {
            if (!rsgSidList.contains(mdl.getRsgSid())) {
                continue;
            }
            RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
            int powflg = rsvCmnBiz.isCanEdit(reqMdl, con, mdl.getRsySisetuSid(),
                    reqMdl.getSmodel().getUsrsid());
            if (powflg == GSConstReserve.POWRANGE_DEL) {
                list.remove(mdl);
                continue;
            }
            if (powflg == GSConstReserve.POWRANGE_IN) {
                ret.add(mdl.getRsySisetuSid());
            }
        }
        return ret;
    }

    /**
     *
     * <br>[機  能] 閲覧権限のある施設予約SIDリストの取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param list 施設予約一覧
     * @param rsgSidList アクセス可能施設SIDリスト
     * @throws SQLException SQL実行時例外
     * @return 編集権限のある施設予約SIDリスト
     */
    private List<Integer> __getViewableRsySidList(Connection con,
            RequestModel reqMdl,
            List<Rsv100SisYrkModel> list,
            List<Integer> rsgSidList)
                    throws SQLException {
        ArrayList<Integer> ret = new ArrayList<Integer>();
        ArrayList<Rsv100SisYrkModel> roop = new ArrayList<Rsv100SisYrkModel>(list);
        for (Rsv100SisYrkModel mdl : roop) {
            if (!rsgSidList.contains(mdl.getRsgSid())) {
                continue;
            }
            RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
            int view = rsvCmnBiz.isWithinPubilicRange(con,
                    mdl.getRsySisetuSid(), reqMdl.getSmodel().getUsrsid());
            if (view == GSConstReserve.POWRANGE_DEL) {
                list.remove(mdl);
                continue;
            }
            if (view == GSConstReserve.POWRANGE_IN) {
                ret.add(mdl.getRsySisetuSid());
            }
        }
        return ret;
    }

}
