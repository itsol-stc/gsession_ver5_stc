package jp.groupsession.v2.man.man470;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs020.Bbs020Biz;
import jp.groupsession.v2.bbs.bbs030.Bbs030Biz;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.dao.BbsForMemDao;
import jp.groupsession.v2.bbs.dao.BbsForSumDao;
import jp.groupsession.v2.bbs.dao.BbsThreSumDao;
import jp.groupsession.v2.bbs.dao.BbsWriteInfDao;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BbsForSumModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.man440.CybCsvImportDao;
import jp.groupsession.v2.man.man440.Man440Biz;

/**
 * <br>[機  能] サイボウズLiveデータ移行 掲示板インポート画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man470Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man470Biz.class);

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Man470Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void setInitData(Man470ParamModel paramMdl,
                            Connection con,
                            String tempDir)
    throws SQLException, IOToolsException {


        /** 画面に表示するファイルのリストを作成、セット **********************/
        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        //画面に表示するファイルのリストを作成
        ArrayList<LabelValueBean> fileLblList = new ArrayList<LabelValueBean>();

        if (fileList != null) {

            log__.debug("ファイルの数×２(オブジェクトと本体) = " + fileList.size());

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                String[] value = fileName.split(GSConstCommon.ENDSTR_OBJFILE);

                //表示用リストへ追加
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                fileLblList.add(new LabelValueBean(fMdl.getFileName(), value[0]));
                log__.debug("ファイル名 = " + fMdl.getFileName());
                log__.debug("保存ファイル名 = " + fMdl.getSaveFileName());
            }
        }
        paramMdl.setMan470FileLabelList(fileLblList);

        Man440Biz biz = new Man440Biz(reqMdl__);
        paramMdl.setMan470grpName(biz.getGroupName(paramMdl, con));

        paramMdl.setMan470forumLabelList(__getForumLabelList(con));
    }

    /**
     * <br>[機  能] フォーラムラベル一覧取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    private ArrayList<LabelValueBean> __getForumLabelList(Connection con)
            throws SQLException {

        ArrayList<LabelValueBean> forumLabelList = new ArrayList<LabelValueBean>();

        List<BbsForInfModel> forumList = new ArrayList<BbsForInfModel>();

        //第1階層のフォーラム情報を取得
        BbsBiz bbsBiz = new BbsBiz();
        List<BbsForInfModel> bfiMdlList = bbsBiz.getSortedChildForum(
                        con, GSConstBulletin.BBS_DEFAULT_PFORUM_SID, true, -1);

        //下位のフォーラム情報を取得する
        List<Integer> topForumSidList = new ArrayList<Integer>();
        for (BbsForInfModel mdl : bfiMdlList) {
            topForumSidList.add(mdl.getBfiSid());
        }
        BbsForInfDao forInfDao = new BbsForInfDao(con);
        List<Integer> existsChildList = forInfDao.extractParentForum(topForumSidList);

        Bbs030Biz bbs030Biz = new Bbs030Biz();
        for (BbsForInfModel mdl : bfiMdlList) {
            //下位のフォーラム情報を持つフォーラムのみを対象とする
            forumList.add(mdl); // 親階層をリストへ追加
            if (existsChildList.contains(mdl.getBfiSid())) {
                bbs030Biz.getChildForum(
                        con, forumList, mdl.getBfiSid(), true, -1);
            }
        }

        // フォーラム一覧からフォーラム名のみを抽出してリスト化
        for (BbsForInfModel mdl : forumList) {
            int forumLevel = mdl.getBfiLevel();
            String bfiName = "";
            for (int i = 0; i < forumLevel; i++) {
                bfiName += "　";
            }
            bfiName += mdl.getBfiName();
            String bfiSid = String.valueOf(mdl.getBfiSid());
            LabelValueBean bbsForLabel = new LabelValueBean(bfiName, bfiSid);
            forumLabelList.add(bbsForLabel);
        }

        return forumLabelList;
    }

    /**
     * <br>[機  能] フォーラム情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid 登録ユーザSID
     * @param date 登録日時
     * @return フォーラムSID
     * @throws Exception
     */
    public int insertBbsForum(Connection con, RequestModel reqMdl, String forumName, int grpSid)
            throws Exception {

        UDate now = new UDate();

        int userSid = reqMdl.getSmodel().getUsrsid();

        //採番コントローラー取得
        MlCountMtController cntCon = GroupSession.getResourceManager().getCountController(reqMdl);

        //フォーラムSID採番
        int forumSid = (int) cntCon.getSaibanNumber(
                        GSConstBulletin.SBNSID_BULLETIN,
                        GSConstBulletin.SBNSID_SUB_BULLETIN_FORUM,
                        userSid);

        // フォーラム表示順
        Bbs020Biz bbs020Biz = new Bbs020Biz();
        int sort = bbs020Biz.getMaxBfiSort(con, GSConstBulletin.BBS_DEFAULT_PFORUM_SID) + 1;

        //登録用のモデルを設定する
        BbsForInfModel bbsInfMdl = new BbsForInfModel();
        bbsInfMdl.setBfiSid(forumSid);
        bbsInfMdl.setBfiName(forumName); // フォーラム名
        bbsInfMdl.setBfiCmt(""); // コメント
        bbsInfMdl.setBfiParentSid(GSConstBulletin.BBS_DEFAULT_PFORUM_SID);
        bbsInfMdl.setBfiLevel(GSConstBulletin.BBS_FORUM_MIN_LEVEL);
        bbsInfMdl.setBfiFollowParentMem(GSConstBulletin.FOLLOW_PARENT_MEMBER_NO); //親フォーラム準拠区分
        bbsInfMdl.setBfiSort(sort);   //表示順
        bbsInfMdl.setBfiReply(GSConstBulletin.BBS_THRE_REPLY_NO);
        bbsInfMdl.setBfiRead(GSConstBulletin.NEWUSER_THRE_VIEW_YES);
        bbsInfMdl.setBfiMread(GSConstBulletin.BBS_FORUM_MREAD_NO);
        bbsInfMdl.setBfiTemplateKbn(GSConstBulletin.BBS_THRE_TEMPLATE_NO);
        bbsInfMdl.setBfiTemplateType(GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN);
        bbsInfMdl.setBfiTemplate("");
        bbsInfMdl.setBfiTemplateWrite(GSConstBulletin.BBS_THRE_TEMPLATE_WRITE_NO);
        bbsInfMdl.setBfiWarnDisk(GSConstBulletin.BFI_WARN_DISK_NO);
        bbsInfMdl.setBfiWarnDiskTh(0);
        bbsInfMdl.setBfiDisk(GSConstBulletin.BFI_DISK_NOLIMIT);
        bbsInfMdl.setBfiDiskSize(0);
        bbsInfMdl.setBfiLimitOn(GSConstBulletin.THREAD_DISABLE);
        bbsInfMdl.setBfiLimit(GSConstBulletin.THREAD_LIMIT_NO);
        bbsInfMdl.setBfiUnlimited(GSConstBulletin.BFI_UNLIMITED_YES);
        bbsInfMdl.setBfiLimitDate(0);
        bbsInfMdl.setBfiKeep(GSConstBulletin.THREAD_KEEP_NO);
        bbsInfMdl.setBfiKeepDateY(-1);
        bbsInfMdl.setBfiKeepDateM(-1);
        bbsInfMdl.setBinSid(new Long(0)); //アイコン情報
        bbsInfMdl.setBfiAuid(userSid);
        bbsInfMdl.setBfiAdate(now);
        bbsInfMdl.setBfiEuid(userSid);
        bbsInfMdl.setBfiEdate(now);

        BbsForInfDao bbsInfDao = new BbsForInfDao(con);
        bbsInfDao.insert(bbsInfMdl);

        //フォーラム編集メンバーの登録(選択グループ)
        String[] memberSid = new String[1];
        memberSid[0] = GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR + String.valueOf(grpSid);
        BbsForMemDao bbsMemDao = new BbsForMemDao(con);
        bbsMemDao.insert(forumSid, memberSid, GSConstBulletin.ACCESS_KBN_WRITE);

        return forumSid;
    }

    /**
     * <br>[機  能] フォーラム集計情報の更新
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void updateForumSum(Connection con, RequestModel reqMdl,
                                 int mode, int bfiSid)
            throws SQLException {

        UDate now = new UDate();

        int userSid = reqMdl.getSmodel().getUsrsid();

        //スレッド数をカウント
        BulletinDao bbsDao = new BulletinDao(con);
        int bfsThreCnt = bbsDao.getThreadCount(bfiSid, now);

        //投稿数をカウント
        BbsWriteInfDao bbsWriteInfDao = new BbsWriteInfDao(con);
        int bfsWrtCnt = bbsWriteInfDao.getWriteCountInForum(bfiSid, now);

        //スレッド容量を集計
        BbsThreSumDao threSumDao = new BbsThreSumDao(con);
        long bfsSize = threSumDao.getTotalThreadSize(bfiSid);

        //最終更新日時
        CybCsvImportDao dao = new CybCsvImportDao(con);
        UDate lastDate = dao.getForumLastUpdate(bfiSid);

        BbsForSumModel bbsForSumMdl = new BbsForSumModel();
        bbsForSumMdl.setBfiSid(bfiSid);
        bbsForSumMdl.setBfsThreCnt(bfsThreCnt);
        bbsForSumMdl.setBfsWrtCnt(bfsWrtCnt);
        bbsForSumMdl.setBfsWrtDate(lastDate);
        bbsForSumMdl.setBfsSize(bfsSize);
        bbsForSumMdl.setBfsEuid(userSid);
        bbsForSumMdl.setBfsEdate(now);

        BbsForSumDao bbsForSumDao = new BbsForSumDao(con);
        if (mode == GSConst.CMDMODE_ADD) {
            //フォーラム集計情報の登録
            bbsForSumMdl.setBfsAuid(userSid);
            bbsForSumMdl.setBfsAdate(now);
            bbsForSumDao.insert(bbsForSumMdl);
        } else {
            //フォーラム集計情報の更新
            bbsForSumDao.update(bbsForSumMdl);
        }
    }

    /**
     * <br>[機  能] フォーラム名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param sid フォーラムSID
     * @param con コネクション
     * @return フォーラム名
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public String getForumName(int sid,
            Connection con)
    throws SQLException, IOToolsException {

        BbsForInfDao forInfDao = new BbsForInfDao(con);
        BbsForInfModel mdl = new BbsForInfModel();
        mdl.setBfiSid(sid);
        BbsForInfModel retMdl = forInfDao.select(mdl);
        return retMdl.getBfiName();
    }
}
