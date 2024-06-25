package jp.groupsession.v2.zsk.zsk040kn;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.biz.ZskUsedDataBiz;
import jp.groupsession.v2.zsk.dao.ZaiInfoDao;
import jp.groupsession.v2.zsk.model.ZaiInfoModel;

/**
 * <br>[機  能] 在席管理 座席表登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk040knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Zsk040knBiz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説] 処理モード = 編集の場合、スレッド情報を設定する
     * <br>[備  考]
     * @param paramMdl Zsk040knParamModel
     * @param con コネクション
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    public void setInitData(Zsk040knParamModel paramMdl, Connection con,
                            String appRoot, String tempDir)
    throws IOException, IOToolsException {
        log__.debug("START");

        //添付ファイル一覧を設定
        CommonBiz cmnBiz = new CommonBiz();
        paramMdl.setZsk040FileLabelList(cmnBiz.getTempFileLabelList(tempDir));

        log__.debug("End");
    }
    /**
     * <br>[機  能] 座席情報の登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Zsk040knParamModel
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid セッションユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @throws Exception 実行例外
     */
    public void insertZifData(Zsk040knParamModel paramMdl,
                                Connection con,
                                MlCountMtController cntCon,
                                int userSid,
                                String appRootPath,
                                String tempDir)
    throws Exception {
        log__.debug("START");

        UDate now = new UDate();

        //バイナリー情報の登録
        CommonBiz cmnBiz = new CommonBiz();
        List < String > binSid = cmnBiz.insertBinInfo(con, tempDir, appRootPath,
                                                    cntCon, userSid, now);
        Long binarySid = new Long(0);
        if (binSid != null) {
            binarySid = Long.parseLong(binSid.get(0));
        }

        //座席情報SID採番
        int zifSid = (int) cntCon.getSaibanNumber(
                GSConstZaiseki.SBNSID_ZAISEKI,
                GSConstZaiseki.SBNSID_SUB_ZAISEKIINFO,
                userSid);

        int sortNum = NullDefault.getInt(paramMdl.getZasekiSortNum(), 0);
        //スレッド情報の登録
        ZaiInfoModel infoMdl = new ZaiInfoModel();
        infoMdl.setZifSid(zifSid);
        infoMdl.setZifName(paramMdl.getZsk040name());
        infoMdl.setBinSid(binarySid);
        infoMdl.setZifSort(sortNum);
        infoMdl.setZifAuid(userSid);
        infoMdl.setZifAdate(now);
        infoMdl.setZifEuid(userSid);
        infoMdl.setZifEdate(now);
        ZaiInfoDao dao = new ZaiInfoDao(con);
        dao.insert(infoMdl);

        //座席表情報のデータ使用量を登録
        ZskUsedDataBiz usedDataBiz = new ZskUsedDataBiz(con);
        usedDataBiz.insertZasekiSize(zifSid, true);

        log__.debug("End");

    }
}
