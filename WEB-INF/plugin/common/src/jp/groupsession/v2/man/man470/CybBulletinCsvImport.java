package jp.groupsession.v2.man.man470;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.GSValidateBulletin;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.dao.BbsThreInfDao;
import jp.groupsession.v2.bbs.dao.BbsThreSumDao;
import jp.groupsession.v2.bbs.dao.BbsThreViewDao;
import jp.groupsession.v2.bbs.dao.BbsWriteInfDao;
import jp.groupsession.v2.bbs.model.BbsThreInfModel;
import jp.groupsession.v2.bbs.model.BbsThreSumModel;
import jp.groupsession.v2.bbs.model.BbsThreViewModel;
import jp.groupsession.v2.bbs.model.BbsWriteInfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.man440.AbstractCybCsvImport;
import jp.groupsession.v2.man.man440.CybCsvCommentModel;
import jp.groupsession.v2.man.man440.CybCsvImportDao;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSValidateUser;

/**
 * <br>[機  能] サイボウズLive 掲示板インポートファイルのチェックを行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CybBulletinCsvImport extends AbstractCybCsvImport {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CybBulletinCsvImport.class);

    /** 登録モード(0:新規 / 1:更新) */
    private int cmdmode__ = GSConst.CMDMODE_ADD;
    /** フォーラム名(登録モード = 新規) */
    private String forumName__ = null;
    /** フォーラムSID(登録モード = 更新) */
    private int forumSid__ = -1;

    /** 最終更新日時 */
    private UDate lastUpdate__ = null;

    // -------------------------------
    //  クラス内でのみ使用する変数
    // -------------------------------

    /** ユーザ名マップ */
    private HashMap<String, Integer> unameMap__ = null;

    /** CSV列定義    */
    public enum COLNO {
      /** ID*/
      BBSID,
      /** タイトル*/
      TITLE,
      /** 本文*/
      VALUE,
      /** 作成者*/
      CREATE_USER,
      /** 作成日時*/
      CREATE_DATE,
      /** 更新者*/
      UPDATE_USER,
      /** 更新日時*/
      UPDATE_DATE,
      /** コメント*/
      COMMENT
    }

    /** csv列定義実態 モードごとの列の違いが反映された配列*/
    private COLNO[] colArr__;

    /**
     * <br>[機 能] コンストラクタ
     * <br>[解 説]
     * <br>[備 考]
     * @param error アクションエラー
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param form Man470Form
     */
    public CybBulletinCsvImport(ActionErrors error, RequestModel reqMdl,
                             Connection con, Man470Form form) {
        super(error, reqMdl, con);

        setGrpSid(form.getMan440GrpSid());
        setCmdmode(form.getMan470mode());
        setForumName(form.getMan470forumName());
        setForumSid(form.getMan470forumSid());

        EnumSet<COLNO> eset = EnumSet.allOf(COLNO.class);
        colArr__ = eset.toArray(new COLNO[eset.size()]);


    }

    /**
     * <br>[機  能] インポート画面毎に設定するチェック処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @throws Exception csv読込時例外
     */
    @Override
    protected void __validateImportData() throws Exception {

        if (cmdmode__ == GSConst.CMDMODE_ADD) {
            // フォーラム名チェック
            GsMessage gsMsg = new GsMessage(reqMdl__);
            GSValidateBulletin.validateTitle(errors__, forumName__, gsMsg.getMessage("bbs.4"),
                                             GSConstBulletin.MAX_LENGTH_FORUMNAME);

            //グループSID存在チェック
            if (this.getGrpSid() >= 0 && !GSValidateUser.existGroup(this.getGrpSid(), con__)) {
                String eprefix = "group.";
                String group = gsMsg.getMessage("cmn.group"); //グループ
                ActionMessage msg = new ActionMessage("search.data.notfound", group);
                StrutsUtil.addMessage(errors__, msg, eprefix + "search.data.notfound");
            }

        } else if (cmdmode__ == GSConst.CMDMODE_EDIT) {
            // 既存フォーラム
            BbsForInfDao dao = new BbsForInfDao(con__);
            if (dao.countBfi(forumSid__) <= 0) {
                // 指定フォーラムが存在しない
                GsMessage gsMsg = new GsMessage(reqMdl__);
                String prefix = "forumSid.";
                ActionMessage msg = new ActionMessage("error.nothing.selected",
                                                       gsMsg.getMessage("bbs.3"));
                errors__.add(prefix + "error.nothing.selected", msg);
            }
        } else {
            // モードの値が不正
            GsMessage gsMsg = new GsMessage(reqMdl__);
            String prefix = "cmdmode.";
            ActionMessage msg = new ActionMessage("error.input.notvalidate.data",
                                             gsMsg.getMessage("cmn.mode"));
            errors__.add(prefix + "error.input.notvalidate.data", msg);
        }
    }

    /**
     * <br>[機  能] csvファイル一行のチェック処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     */
    @Override
    protected void __check(long num, String lineStr) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);

        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        //CSV項目数
        String textCsvitems = gsMsg.getMessage("cmn.csv.number.items");
        //行目
        String textLine = gsMsg.getMessage("cmn.line", new String[] {String.valueOf(num)});
        //行目の
        String textLine2 = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        try {

            int j = 0;
            ActionMessage msg;
            String buff = "";
            String eprefix = "inputFile.";
            int ecnt = errors__.size();

            CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

            log__.debug("項目数=" + stringTokenizer.length());
            COLNO[] colArr = colArr__;
            if (stringTokenizer.length() != colArr.length) {
                msg = new ActionMessage(
                        "error.input.format.file",
                        textCaptureFile,
                        textCsvitems + "(" + textLine + ")");
                StrutsUtil.addMessage(errors__, msg, eprefix + num + "error.input.format.file");
            } else {
                while (stringTokenizer.hasMoreTokens()) {
                    buff = stringTokenizer.nextToken();

                    if (buff != null && buff.equals("\"")) {
                        buff = ""; // 空データの場合、ダブルクォーテーションが残ってしまうのでその対策
                    }

                    switch (colArr[j]) {
                        // ID
                        case BBSID:
                            // 取り込みデータには該当しないので、チェックなし
                            break;

                        //タイトル
                        case TITLE:
                            if (StringUtil.isNullZeroStringSpace(buff)) {
                                // タイトル未入力チェック
                                String textTitle = textLine2 + gsMsg.getMessage("cmn.title");
                                String fieldFix = String.valueOf(num) + "title.";

                                msg = new ActionMessage("error.input.required.text", textTitle);
                                StrutsUtil.addMessage(errors__, msg, fieldFix + "error.input.required.text");

                            } else if (!GSValidateUtil.isGsJapaneaseString(buff)) {
                                String textTitle = textLine2 + gsMsg.getMessage("cmn.title");
                                String fieldFix = String.valueOf(num) + "title.";

                                //JIS第2水準チェック
                                //利用不可能な文字を入力した場合
                                String nstr = GSValidateUtil.getNotGsJapaneaseString(buff);
                                msg = new ActionMessage("error.input.njapan.text",
                                                         textTitle, nstr);
                                StrutsUtil.addMessage(errors__, msg,
                                                        fieldFix + "error.input.njapan.text");
                            }
                            break;

                        //本文
                        // ・文字数制限なし(文字数オーバーした場合、カットする)
                        case VALUE:
                            if (!StringUtil.isNullZeroString(buff)) {
                                String textContent = textLine2 + gsMsg.getMessage("cmn.body");
                                String fieldFix    = String.valueOf(num) + "value.";
                                //JIS第2水準チェック
                                if (!GSValidateUtil.isGsJapaneaseStringTextArea(buff)) {
                                    //利用不可能な文字を入力した場合
                                    String nstr =
                                            GSValidateUtil.getNotGsJapaneaseStringTextArea(buff);
                                    msg = new ActionMessage(
                                                    "error.input.njapan.text", textContent, nstr);
                                    StrutsUtil.addMessage(errors__,
                                                       msg, fieldFix + "error.input.njapan.text");
                                }
                            }
                            break;

                        //作成者
                        case CREATE_USER:
                            if (!StringUtil.isNullZeroString(buff)) {
                                String textTitle = textLine2 + gsMsg.getMessage("main.man470.6");
                                String fieldFix = String.valueOf(num) + "cruser.";
                                // ※タブ文字については、スペースに変換する
                                //JIS第2水準チェック
                                if (!GSValidateUtil.isGsJapaneaseString(buff)) {
                                    //利用不可能な文字を入力した場合
                                    String nstr = GSValidateUtil.getNotGsJapaneaseString(buff);
                                    msg = new ActionMessage("error.input.njapan.text",
                                                             textTitle, nstr);
                                    StrutsUtil.addMessage(errors__, msg,
                                                            fieldFix + "error.input.njapan.text");
                                }
                            }
                            break;

                        //更新者
                        case UPDATE_USER:
                            if (!StringUtil.isNullZeroString(buff)) {
                                String textTitle = textLine2 + gsMsg.getMessage("main.man470.8");
                                String fieldFix = String.valueOf(num) + "upuser.";
                                // ※タブ文字については、スペースに変換する
                                //JIS第2水準チェック
                                if (!GSValidateUtil.isGsJapaneaseString(buff)) {
                                    //利用不可能な文字を入力した場合
                                    String nstr = GSValidateUtil.getNotGsJapaneaseString(buff);
                                    msg = new ActionMessage("error.input.njapan.text",
                                                             textTitle, nstr);
                                    StrutsUtil.addMessage(errors__, msg,
                                                            fieldFix + "error.input.njapan.text");
                                }
                            }
                            break;


                        // 作成日時
                        case CREATE_DATE:
                            String textCrDate = textLine2 + gsMsg.getMessage("main.man470.7");
                            __isOkDateTime(errors__, buff, textCrDate,
                                           String.valueOf(num) + "crdate.");
                            break;

                        // 更新日時
                        case UPDATE_DATE:
                            String textUpDate = textLine2 + gsMsg.getMessage("main.man470.9");
                            __isOkDateTime(errors__, buff, textUpDate,
                                           String.valueOf(num) + "update.");
                            break;

                        //コメント
                        // ・文字数制限なし(文字数オーバーした場合、カットする)
                        case COMMENT:
                            if (!StringUtil.isNullZeroString(buff)) {
                                String textMemo = textLine2 + gsMsg.getMessage("cmn.comment");
                                String fieldFix = String.valueOf(num) + "comment.";
                                //JIS第2水準チェック
                                if (!GSValidateUtil.isGsJapaneaseStringTextArea(buff)) {
                                    //利用不可能な文字を入力した場合
                                    String nstr =
                                            GSValidateUtil.getNotGsJapaneaseStringTextArea(buff);
                                    msg = new ActionMessage(
                                                    "error.input.njapan.text", textMemo, nstr);
                                    StrutsUtil.addMessage(errors__,
                                                       msg, fieldFix + "error.input.njapan.text");
                                }
                            }
                            break;

                        default:
                            break;
                    }

                    j++;
                }
            }

            //エラー有り
            if (ecnt < errors__.size()) {
                //エラー存在フラグON
                setErrorFlg(true);
            } else {
                //明細データ以降
                if (num >= 2) {
                    //有効データ件数カウントアップ
                    int cnt = getCount();
                    cnt += 1;
                    setCount(cnt);
                }
            }

        } catch (Exception e) {
            log__.error("CSVファイル読込み時例外");
            throw e;
        }
    }

    /**
     * <br>[機  能] csvファイル一行のインポート処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     */
    @Override
    protected void __import(long num, String lineStr) throws Exception {
        int j = 0;
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        COLNO[] colArr = colArr__;

        int bfiSid  = forumSid__;   //フォーラムSID
        List<Integer> memberList = new ArrayList<Integer>();

        CybCsvImportDao dao = new CybCsvImportDao(con__);

        if (cmdmode__ == GSConst.CMDMODE_ADD) {

            // 新規作成時にはグループメンバーのみ
            CmnBelongmDao grpMemDao = new CmnBelongmDao(con__);
            memberList = grpMemDao.selectBelongLiveUserSid(grpSid__);
        } else {
            // 既存のフォーラムの場合、
            memberList = dao.getForumMemberList(bfiSid);
        }

        //スレッド情報の登録
        BbsThreInfModel bbsThreInfMdl = __createBbsThreadModel(bfiSid, sessionUser__);

        CybBbsCsvModel csvMdl = new CybBbsCsvModel();
        StringBuffer commentStr = new StringBuffer();

        // 行データ解析
        while (stringTokenizer.hasMoreTokens()) {

            COLNO col = colArr[j];
            buff = stringTokenizer.nextToken();

            if (buff != null && buff.equals("\"")) {
                buff = ""; // 空データの場合、ダブルクォーテーションが残ってしまうのでその対策
            }
            __setBbsModel(col, bbsThreInfMdl, csvMdl, commentStr, buff);

            j++;
        }

        this.setUserName(csvMdl.getCruser());
        this.setUserName(csvMdl.getUpuser());

        ArrayList<CybCsvCommentModel> commentList = new ArrayList<CybCsvCommentModel>();
        // コメント行解析
        if (commentStr.length() > 0) {
            commentList = __getCommentFromStr(commentStr.toString());
            for (CybCsvCommentModel comment : commentList) {
                this.setUserName(comment.getName());
            }
        }

        dao.setUserSidToNameMap(this.getUserNameMap()); // 必要なユーザSIDをDBから取得

        int btiSid = bbsThreInfMdl.getBtiSid();
        int cruser = this.getUserSid(csvMdl.getCruser());
        int upuser = this.getUserSid(csvMdl.getUpuser());
        UDate crdate = csvMdl.getCrdate();
        UDate update = csvMdl.getUpdate();

        // ----------------------------------------------------------------
        // スレッド登録
        if (bbsThreInfMdl.getBtiTitle().length() > GSConstBulletin.MAX_LENGTH_THRETITLE) {
            // タイトルの文字数オーバー時にはカットして、内容にも記載する
            String title = bbsThreInfMdl.getBtiTitle().substring(0,
                                                    GSConstBulletin.MAX_LENGTH_THRETITLE);
            String value = bbsThreInfMdl.getBtiTitle();
            if (!StringUtil.isNullZeroStringSpace(csvMdl.getValue())) {
                value = value + RCODE + csvMdl.getValue();
            }
            csvMdl.setValue(value);
            bbsThreInfMdl.setBtiTitle(title);
        }

        bbsThreInfMdl.setBtiAuid(cruser);
        bbsThreInfMdl.setBtiEuid(upuser);
        bbsThreInfMdl.setBtiAdate(crdate);
        bbsThreInfMdl.setBtiEdate(update);
        BbsThreInfDao bbsThreInfDao = new BbsThreInfDao(con__);
        bbsThreInfDao.insert(bbsThreInfMdl);

        // ----------------------------------------------------------------
        // 投稿データ(スレッド投稿データ ＋ コメント)登録

        if (StringUtil.isNullZeroStringSpace(csvMdl.getValue())) {
            // スレッド本文が無い場合、「内容無し」を入力
            GsMessage gsMsg = new GsMessage(reqMdl__);
            csvMdl.setValue(gsMsg.getMessage("main.man470.10")); // 内容なし
        }

        List<BbsWriteInfModel> bbsWriteInfList = new ArrayList<BbsWriteInfModel>();

        __addBbsWriteModel(bbsWriteInfList, csvMdl, bfiSid, btiSid, sessionUser__, 1); // リストへ追加

        for (CybCsvCommentModel mdl : commentList) {
            CybBbsCsvModel writeMdl = new CybBbsCsvModel();
            writeMdl.setValue(mdl.getValue());
            writeMdl.setCruser(mdl.getName());
            writeMdl.setCrdate(mdl.getDate());
            writeMdl.setUpuser(mdl.getName());
            writeMdl.setUpdate(mdl.getDate());
            __addBbsWriteModel(bbsWriteInfList, writeMdl, bfiSid, btiSid, sessionUser__, 0);

        }

        BbsWriteInfDao bbsWriteInfDao = new BbsWriteInfDao(con__);
        bbsWriteInfDao.insert(bbsWriteInfList);

        // ----------------------------------------------------------------
        //スレッド閲覧済み登録
        if (memberList != null && memberList.size() > 0) {
            BbsThreViewDao threViewDao = new BbsThreViewDao(con__);
            List<BbsThreViewModel> threViewList = new ArrayList<BbsThreViewModel>();
            for (Integer memberSid : memberList) {
                BbsThreViewModel threViewMdl = new BbsThreViewModel();
                threViewMdl.setBtiSid(btiSid);
                threViewMdl.setBfiSid(bfiSid);
                threViewMdl.setUsrSid(memberSid.intValue());
                threViewMdl.setBivViewKbn(GSConstBulletin.BBS_THRE_VIEW_YES);
                threViewMdl.setBivAuid(cruser);
                threViewMdl.setBivAdate(crdate);
                threViewMdl.setBivEuid(upuser);
                threViewMdl.setBivEdate(update);
                threViewList.add(threViewMdl);
            }
            threViewDao.insert(threViewList);
        }

        // ----------------------------------------------------------------
        //スレッド集計情報の登録

        // スレッドデータのデータサイズ
        long diskSize = 0;
        diskSize += __getDataSize(bbsThreInfMdl.getBtiTitle());
        for (BbsWriteInfModel writeData : bbsWriteInfList) {
            diskSize += __getDataSize(writeData.getBwiValue());
        }

        BbsThreSumModel bbsThreSumMdl = new BbsThreSumModel();
        int bfsWrtCnt = bbsWriteInfList.size() + 1; //  投稿数＋スレッド投稿


        // スレッド内で更新日時が新しいものを取得
        UDate lastUpdate = null;
        for (BbsWriteInfModel mdl : bbsWriteInfList) {
            if (mdl.getBwiEdate() != null) {
                if (lastUpdate == null
                 || lastUpdate.compare(mdl.getBwiEdate(), lastUpdate) == UDate.LARGE) {
                    lastUpdate = mdl.getBwiEdate();
                }
            }
        }

        bbsThreSumMdl.setBtiSid(btiSid);
        bbsThreSumMdl.setBtsWrtCnt(bfsWrtCnt);
        bbsThreSumMdl.setBtsWrtDate(lastUpdate); // スレッドの最終書き込み日時
        bbsThreSumMdl.setBtsAuid(sessionUser__);
        bbsThreSumMdl.setBtsAdate(sysUd__);
        bbsThreSumMdl.setBtsEuid(sessionUser__);
        bbsThreSumMdl.setBtsEdate(sysUd__);
        bbsThreSumMdl.setBtsSize(diskSize);
        bbsThreSumMdl.setBtsTempflg(GSConstBulletin.BTS_TEMPFLG_NOTHING);

        BbsThreSumDao bbsThreSumDao = new BbsThreSumDao(con__);
        bbsThreSumDao.insert(bbsThreSumMdl);

        // フォーラムで更新日時が新しいものを取得
        if (lastUpdate != null) {
            if (lastUpdate__ == null
             || lastUpdate__.compare(lastUpdate, lastUpdate__) == UDate.LARGE) {
                lastUpdate__ = lastUpdate;
            }
        }
    }

    /**
     * <br>[機  能] スレッド情報を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid 登録ユーザSID
     * @param date 登録日時
     * @param date 登録日時
     * @throws SQLException SQL実行時例外
     * @return フォーラムSID
     */
    private BbsThreInfModel __createBbsThreadModel(int bfiSid, int userSid)
            throws SQLException {
        //スレッドSID採番
        int btiSid = (int) cntCon__.getSaibanNumber(GSConstBulletin.SBNSID_BULLETIN,
                                                GSConstBulletin.SBNSID_SUB_BULLETIN_THREAD,
                                                userSid);

        //スレッド情報の登録
        BbsThreInfModel bbsThreInfMdl = new BbsThreInfModel();
        bbsThreInfMdl.setBtiSid(btiSid);
        bbsThreInfMdl.setBfiSid(bfiSid);
        bbsThreInfMdl.setBtiAgid(0); // スレッド登録ユーザ
        bbsThreInfMdl.setBtiEgid(0); // スレッド更新ユーザ
        bbsThreInfMdl.setBtiImportance(GSConstBulletin.BTI_IMPORTANCE_NO); // 重要度
        bbsThreInfMdl.setBtiLimit(GSConstBulletin.THREAD_LIMIT_NO); // 掲示期間なし

        return bbsThreInfMdl;
    }

    /**
     * <br>[機  能] 投稿情報を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param list 投稿情報を格納するリスト
     * @param comment 投稿情報の作成元になるモデル
     * @param bfiSid フォーラムSID
     * @param btiSid スレッドSID
     * @param upuser 更新ユーザSID
     * @param update 更新日時
     * @throws SQLException SQL実行時例外
     * @return フォーラムSID
     */
    private void __addBbsWriteModel(List<BbsWriteInfModel> list,
                                      CybBbsCsvModel comment,
                                      int bfiSid, int btiSid, int userSid, int parentFlg)
            throws SQLException {
        //投稿SID採番
        int bwiSid = (int) cntCon__.getSaibanNumber(GSConstBulletin.SBNSID_BULLETIN,
                                                GSConstBulletin.SBNSID_SUB_BULLETIN_WRITE,
                                                userSid);
        int   usid   = this.getUserSid(comment.getCruser());
        String value = comment.getValue();

        //投稿情報の登録
        BbsWriteInfModel bbsWriteInfMdl = new BbsWriteInfModel();
        bbsWriteInfMdl.setBwiSid(bwiSid); // 投稿SID
        bbsWriteInfMdl.setBfiSid(bfiSid); // フォーラムSID
        bbsWriteInfMdl.setBtiSid(btiSid); // スレッドSID
        bbsWriteInfMdl.setBwiType(GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN); // 本文タイプ
        bbsWriteInfMdl.setBwiValuePlain("");
        bbsWriteInfMdl.setBwiAgid(0);
        bbsWriteInfMdl.setBwiEgid(0);
        bbsWriteInfMdl.setBwiParentFlg(parentFlg);

        // スレッド投稿データ登録
        if (StringUtil.isNullZeroStringSpace(value)) {
            return; // データが無い場合はスキップ
        }

        CybBbsCsvModel nextComment = null;

        if (value.length() > GSConstBulletin.MAX_LENGTH_WRITEVALUE) {
            // 本文がオーバーしている場合には、オーバーした分を別の投稿とする
            String nextValue = value.substring(GSConstBulletin.MAX_LENGTH_WRITEVALUE);
            UDate nextCrdate = comment.getCrdate().cloneUDate();
            UDate nextUpdate = comment.getUpdate().cloneUDate();
            nextCrdate.addSecond(1); // 投稿日時を1秒ずらす
            nextUpdate.addSecond(1); // 投稿日時を1秒ずらす
            value = value.substring(0, GSConstBulletin.MAX_LENGTH_WRITEVALUE);

            nextComment = new CybBbsCsvModel();
            nextComment.setCruser(comment.getCruser());
            nextComment.setCrdate(nextCrdate);
            nextComment.setUpuser(comment.getUpuser());
            nextComment.setUpdate(nextUpdate);
            nextComment.setValue(nextValue);
        }

        bbsWriteInfMdl.setBwiValue(value);  // 本文
        bbsWriteInfMdl.setBwiAuid(this.getUserSid(comment.getCruser()));  // 投稿登録ユーザSID
        bbsWriteInfMdl.setBwiAdate(comment.getCrdate()); // 投稿登録日時
        bbsWriteInfMdl.setBwiEuid(this.getUserSid(comment.getUpuser()));  // 投稿編集ユーザSID
        bbsWriteInfMdl.setBwiEdate(comment.getUpdate()); // 投稿編集日時
        list.add(bbsWriteInfMdl);

        if (nextComment != null) {
            __addBbsWriteModel(list, nextComment, bfiSid, btiSid, userSid, 0);
        }
    }

    /**
     * <br>[機  能] フォーラム情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param col 項目クラス
     * @param impMdl SchDataModel
     * @param buff 読込文字列
     * @throws SQLException SQL実行時例外
     * @throws EncryptionException パスワード変換時例外
     */
    private void __setBbsModel(COLNO col,
            BbsThreInfModel thMdl,
            CybBbsCsvModel csvMdl,
            StringBuffer comment,
            String buff) throws SQLException {

        switch (col) {
        //ID
        case BBSID:
            // データ登録なし
            break;

        //タイトル
        case TITLE:
            if (ValidateUtil.isTab(buff)) {
                //タブ文字が含まれている場合、スペースに変換する
                buff = buff.replaceAll("\t", " ");
            }
            thMdl.setBtiTitle(buff.trim()); // 先頭or末尾のスペースを除去してセット
            break;

        //本文
        case VALUE:
            csvMdl.setValue(buff);
            break;

        //コメント
        case COMMENT:
            comment.append(buff);
            break;

        //作成者
        case CREATE_USER:
            buff = _getUserNameFromBuffer(buff); // ユーザ名として取得
            if (!StringUtil.isNullZeroStringSpace(buff)) {
                csvMdl.setCruser(buff);
            }
            break;

        //更新者
        case UPDATE_USER:
            buff = _getUserNameFromBuffer(buff); // ユーザ名として取得
            if (!StringUtil.isNullZeroStringSpace(buff)) {
                csvMdl.setUpuser(buff);
            }
            break;

        //作成日時
        case CREATE_DATE:
            csvMdl.setCrdate(__getDateFromStr(buff));
            break;

        //更新日時
        case UPDATE_DATE:
            csvMdl.setUpdate(__getDateFromStr(buff));
            break;

        default:
            break;
        }
    }

    /**
     * <p>cmdmode を取得します。
     * @return cmdmode
     */
    public int getCmdmode() {
        return cmdmode__;
    }

    /**
     * <p>cmdmode をセットします。
     * @param cmdmode cmdmode
     */
    public void setCmdmode(int cmdmode) {
        cmdmode__ = cmdmode;
    }

    /**
     * <p>forumName を取得します。
     * @return forumName
     */
    public String getForumName() {
        return forumName__;
    }

    /**
     * <p>forumName をセットします。
     * @param forumName forumName
     */
    public void setForumName(String forumName) {
        forumName__ = forumName;
    }

    /**
     * <p>forumSid を取得します。
     * @return forumSid
     */
    public int getForumSid() {
        return forumSid__;
    }

    /**
     * <p>forumSid をセットします。
     * @param forumSid forumSid
     */
    public void setForumSid(int forumSid) {
        forumSid__ = forumSid;
    }

    /**
     * <p>lastUpdate を取得します。
     * @return lastUpdate
     */
    public UDate getLastUpdate() {
        return lastUpdate__;
    }

    /**
     * <p>lastUpdate をセットします。
     * @param lastUpdate lastUpdate
     */
    public void setLastUpdate(UDate lastUpdate) {
        lastUpdate__ = lastUpdate;
    }

    /**
     * <p>ユーザ名マップ を取得します。
     * @return ユーザ名マップ
     */
    public HashMap<String, Integer> getUserNameMap() {
        if (unameMap__ == null) {
            unameMap__ = new HashMap<String, Integer>();
        }
        return unameMap__;
    }


    /**
     * <p>ユーザ名 をセットします。
     * @param name ユーザ名
     */
    public void setUserName(String name) {
        if (name != null) {
            if (unameMap__ == null) {
                unameMap__ = new HashMap<String, Integer>();
            }
            if (!unameMap__.containsKey(name)) {
                unameMap__.put(name, Integer.valueOf(GSConst.SYSTEM_USER_ADMIN));
            }
        }
    }

    /**
     * <p>ユーザSID をセットします。
     * @param usid ユーザSID
     */
    public void setUserSid(String name, int usid) {
        if (name != null) {
            if (unameMap__ == null) {
                unameMap__ = new HashMap<String, Integer>();
            }
            unameMap__.put(name, Integer.valueOf(usid));
        }
    }

    /**
     * <p>ユーザSID を取得します。
     * @return ユーザSID
     */
    public int getUserSid(String name) {
        if (name != null && unameMap__ != null && unameMap__.containsKey(name)) {
            return unameMap__.get(name);
        }
        return GSConst.SYSTEM_USER_ADMIN;
    }

    /**
     * <br>[機  能] スレッド情報のサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param btiTitle スレッドタイトル
     * @return スレッド情報のサイズ
     * @throws UnsupportedEncodingException スレッド情報のエンコードに失敗
     */
    public long __getDataSize(String data) throws UnsupportedEncodingException {
        if (data == null) {
            return 0;
        }
        return 20 + data.getBytes(GSConst.ENCODING).length + 24 + 4;
    }
}
