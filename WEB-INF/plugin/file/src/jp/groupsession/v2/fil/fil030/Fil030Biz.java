package jp.groupsession.v2.fil.fil030;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileAccessConfDao;
import jp.groupsession.v2.fil.dao.FileAconfDao;
import jp.groupsession.v2.fil.dao.FileCabinetAdminDao;
import jp.groupsession.v2.fil.dao.FileCabinetDao;
import jp.groupsession.v2.fil.dao.FileDao;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * キャビネット登録・編集画面で使用するビジネスロジッククラス
 * @author JTS
 */
public class Fil030Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil030Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** リクエスト情報 */
    private Connection con__ = null;

    /**
     * <p>コンストラクタ
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Fil030Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl Fil030ParamModel
     * @param tempDir テンポラリディレクトリパス
     * @param appRoot アプリケーションルートパス
     * @param cmd コマンド
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException ファイル操作時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setInitData(Fil030ParamModel paramMdl,
            String tempDir, String appRoot,
            String cmd)
    throws SQLException, IOToolsException, IOException, TempFileException {

        log__.debug("キャビネット登録初期表示");

        FileCabinetDao cabDao = new FileCabinetDao(con__);

        paramMdl.setOwnerSid(null); // キャビネット所有ユーザSIDを一旦初期化

        if (paramMdl.getCmnMode().equals(GSConstFile.CMN_MODE_ADD)) {
            //新規
            setInitDataNew(paramMdl, tempDir, appRoot);

        } else if (paramMdl.getCmnMode().equals(GSConstFile.CMN_MODE_EDT)) {
            //編集
            setInitDataEdit(paramMdl, tempDir, appRoot, cmd);

        } else if (paramMdl.getCmnMode().equals(GSConstFile.CMN_MODE_MLT)) {
            //一括編集
            if (paramMdl.getFil220sltCheck().length == 1) {

                //単一SIDなら編集モードに移行
                //編集
                setInitDataEdit(paramMdl, tempDir, appRoot, cmd);

            } else if (paramMdl.getFil220sltCheck().length > 1) {
                String[] sidlist = paramMdl.getFil220sltCheck();
                ArrayList<FileCabinetModel> ret = new ArrayList<FileCabinetModel>();

                //キャビネット区分の判定
                for (String sid : sidlist) {
                    FileCabinetModel mdl = cabDao.select(Integer.parseInt(sid));
                    if (mdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON) {
                        paramMdl.setFil010DspCabinetKbn(GSConstFile.DSP_CABINET_ERRL);
                        break;
                    }
                }
                
                setInitDataNew(paramMdl, tempDir, appRoot);
                
                //複数SIDからキャビネットリストを作成
                for (String sid : sidlist) {
                    FileCabinetModel mdl = cabDao.select(Integer.parseInt(sid));
                    if (mdl != null && mdl.getFcbPersonalFlg() == paramMdl.getFil030PersonalFlg()) {
                        // 表示中のキャビネット区分(共有or個人)に該当するキャビネット情報のみ配列へ追加
                        ret.add(mdl);
                    }
                }

                StringBuilder cabinetsName = new StringBuilder();
                if (paramMdl.getFil030PersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
                    //個人キャビネットの場合
                    ArrayList<Integer> usrSids = new ArrayList<Integer>();
                    for (FileCabinetModel mdl : ret) {
                        usrSids.add(Integer.valueOf(mdl.getFcbOwnerSid()));
                    }

                    // ユーザ名一覧取得
                    FileDao filDao = new FileDao(con__);
                    HashMap<Integer, String> userNameMap = filDao.getUserNameMap(usrSids);

                    // キャビネット名を更新
                    if (userNameMap.size() > 0) {
                        for (FileCabinetModel mdl : ret) {
                            Integer usrSid = Integer.valueOf(mdl.getFcbOwnerSid());
                            if (userNameMap.containsKey(usrSid)) {
                                mdl.setFcbName(userNameMap.get(usrSid));
                            }
                        }
                    }
                }

                for (FileCabinetModel mdl : ret) {
                    if (cabinetsName.length() > 0) {
                        cabinetsName.append(", ");
                    }
                    cabinetsName.append(mdl.getFcbName());
                }

                paramMdl.setFil030CabinetName(cabinetsName.toString());
            }

        }

        //個人キャビネットの場合
        if (paramMdl.getFil030PersonalFlg() == GSConstFile.CABINET_KBN_PRIVATE) {
            //管理者設定を反映
            FileAconfDao facDao = new FileAconfDao(con__);
            FileAconfModel facMdl = facDao.select();
            //容量設定
            paramMdl.setFil030CapaKbn(String.valueOf(facMdl.getFacPersonalCapa()));
            paramMdl.setFil030CapaSize(String.valueOf(facMdl.getFacPersonalSize()));
            paramMdl.setFil030CapaWarn(String.valueOf(facMdl.getFacPersonalWarn()));
            //バージョン管理
            paramMdl.setFil030VerKbn(String.valueOf(facMdl.getFacPersonalVer()));
            paramMdl.setFil030VerAllKbn(String.valueOf(GSConstFile.VERSION_ALL_KBN_ON));
        }

        //電子帳簿保存法 自動振り分け機能 第1フォルダ
        paramMdl.setFile030ErrlAutoFolderList1(__getAutoFolderList(paramMdl, true));
        //電子帳簿保存法 自動振り分け機能 第2フォルダ
        paramMdl.setFile030ErrlAutoFolderList2(__getAutoFolderList(paramMdl, false));
        //電子帳簿保存法 自動振り分け機能 第3フォルダ
        paramMdl.setFile030ErrlAutoFolderList3(__getAutoFolderList(paramMdl, false));


        FilCommonBiz cmnBiz = new FilCommonBiz(reqMdl__, con__);

        //使用率ラベル
        paramMdl.setFil030CapaWarnLavel(cmnBiz.getCapaWarnLabelList());

        //管理者設定バージョン管理区分
        int verKbnAdm = __setVerKbnAdmin(paramMdl, con__);

        if (verKbnAdm == GSConstFile.VERSION_KBN_ON) {
            //バージョン世代ラベル設定
            paramMdl.setFil030VerKbnLavel(cmnBiz.getVersionLabelList());
        }
    }
    /**
     * <br>新規モード時の初期表示を設定します
     * @param paramMdl Fil030ParamModel
     * @param tempDir テンポラリディレクトリパス
     * @param appRoot アプリケーションルートパス
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException ファイル操作時例外
     */
    public void setInitDataNew(Fil030ParamModel paramMdl,
            String tempDir, String appRoot)
    throws SQLException, IOToolsException, IOException {

        //個人キャビネット判定フラグ
        int personalFlg = 0;
        if (Integer.parseInt(paramMdl.getFil010DspCabinetKbn())
                == GSConstFile.CABINET_KBN_PRIVATE) {
            personalFlg = GSConstFile.CABINET_KBN_PRIVATE;
        }
        paramMdl.setFil030PersonalFlg(personalFlg);

        //新規
        int accessKbn = GSConstFile.ACCESS_KBN_OFF;
        if (paramMdl.getFil010DspCabinetKbn().equals(GSConstFile.DSP_CABINET_ERRL)) {
            accessKbn = GSConstFile.ACCESS_KBN_ON;
        }
        paramMdl.setFil030AccessKbn(
                NullDefault.getString(
                        paramMdl.getFil030AccessKbn(),
                        String.valueOf(accessKbn)));
        paramMdl.setFil030CapaKbn(
                NullDefault.getString(
                        paramMdl.getFil030CapaKbn(),
                        String.valueOf(GSConstFile.CAPA_KBN_OFF)));

        if (paramMdl.getFile030ErrlAutoKbn() == -1) {
            paramMdl.setFile030ErrlAutoKbn(GSConstFile.SORT_FOLDER_NOT_USE);
        }
        if (paramMdl.getFile030ErrlAutoFolder1() == -1) {
            paramMdl.setFile030ErrlAutoFolder1(GSConstFile.SORT_FOLDER_UPLOAD_DATE);
        }
        if (paramMdl.getFile030ErrlAutoFolder2() == -1) {
            paramMdl.setFile030ErrlAutoFolder2(GSConstFile.SORT_FOLDER_NONE);
        }
        if (paramMdl.getFile030ErrlAutoFolder3() == -1) {
            paramMdl.setFile030ErrlAutoFolder3(GSConstFile.SORT_FOLDER_NONE);
        }
        //個人キャビネットの場合
        if (paramMdl.getFil030PersonalFlg() == Integer.parseInt(GSConstFile.DSP_CABINET_PRIVATE)) {
            //キャビネット名はユーザ名と同じ
            BaseUserModel usModel = reqMdl__.getSmodel();
            int sessionUsrSid = usModel.getUsrsid();
            CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con__);
            CmnUsrmInfModel usrMdl = usrDao.select(sessionUsrSid);
            String personalCabName = usrMdl.getUsiSei() + usrMdl.getUsiMei();
            paramMdl.setFil030CabinetName(personalCabName);

            if (paramMdl.getCmnMode().equals(GSConstFile.CMN_MODE_ADD)) {
                // キャビネット登録時のみログインユーザSID = キャビネット所有者SID
                int userSid = usrMdl.getUsrSid();
                paramMdl.setOwnerSid(String.valueOf(userSid));
            }
        }

    }

    /**
     * <br>編集モード時の初期表示を設定します
     * @param paramMdl Fil030ParamModel
     * @param tempDir テンポラリディレクトリパス
     * @param appRoot アプリケーションルートパス
     * @param cmd コマンド
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     * @throws IOException ファイル操作時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setInitDataEdit(Fil030ParamModel paramMdl,
            String tempDir, String appRoot, String cmd)
    throws SQLException, IOToolsException, IOException, TempFileException {

        CommonBiz cmnBiz = new CommonBiz();

        //編集
        int cabSid = 0;
        if (paramMdl.getCmnMode().equals(GSConstFile.CMN_MODE_MLT)) {
            cabSid = NullDefault.getInt(paramMdl.getFil220sltCheck()[0], -1);
            paramMdl.setFil030SelectCabinet(paramMdl.getFil220sltCheck()[0]);
            paramMdl.setCmnMode(GSConstFile.CMN_MODE_EDT);
        } else {
            cabSid = NullDefault.getInt(paramMdl.getFil030SelectCabinet(), -1);
        }

        FileCabinetDao cabDao = new FileCabinetDao(con__);
        FileCabinetModel cabModel = cabDao.select(cabSid);

        if (cabModel != null) {
            int personalFlg = cabModel.getFcbPersonalFlg();
            paramMdl.setFil030PersonalFlg(personalFlg);
            
            if (cabModel.getFcbErrl() == GSConstFile.ERRL_KBN_ON) {
                paramMdl.setFil010DspCabinetKbn(GSConstFile.DSP_CABINET_ERRL);
            }

            paramMdl.setFil030CabinetName(
                    NullDefault.getString(paramMdl.getFil030CabinetName(), cabModel.getFcbName()));

            paramMdl.setFil030AccessKbn(
                    NullDefault.getString(
                            paramMdl.getFil030AccessKbn(),
                            String.valueOf(cabModel.getFcbAccessKbn())));

            if (personalFlg == Integer.parseInt(GSConstFile.DSP_CABINET_PRIVATE)) {
                //キャビネット所有者SID
                paramMdl.setOwnerSid(String.valueOf(cabModel.getFcbOwnerSid()));
            }

            //アクセス制御情報
            FileAccessConfDao acDao = new FileAccessConfDao(con__);
            if (paramMdl.getFil030Biko() == null) {
                paramMdl.setFil030SvAcRead(
                        acDao.getAccessUser(cabModel.getFcbSid(),
                                Integer.parseInt(GSConstFile.ACCESS_KBN_READ)));

                FileCabinetAdminDao admDao = new FileCabinetAdminDao(con__);
                paramMdl.setFil030SvAdm(admDao.getAdminUserSid(cabModel.getFcbSid()));
                //共有キャビネットの場合のみ、フルアクセス権限をユーザ・グループを取得
                if (personalFlg == GSConstFile.CABINET_KBN_PUBLIC) {
                paramMdl.setFil030SvAcFull(
                        acDao.getAccessUser(cabModel.getFcbSid(),
                                Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE)));
                //個人キャビネットの場合
                } else {
                    //キャビネット名は常にユーザ名と一致する
                    if (personalFlg == Integer.parseInt(GSConstFile.DSP_CABINET_PRIVATE)) {
                        cabModel = cabDao.getPersonalCab(cabSid, personalFlg);
                    }
                }
            }

            paramMdl.setFil030CapaKbn(
                    NullDefault.getString(
                            paramMdl.getFil030CapaKbn(),
                            String.valueOf(cabModel.getFcbCapaKbn())));
            paramMdl.setFil030CapaSize(
                    NullDefault.getString(
                            paramMdl.getFil030CapaSize(),
                            String.valueOf(cabModel.getFcbCapaSize())));
            paramMdl.setFil030CapaWarn(
                    NullDefault.getString(
                            paramMdl.getFil030CapaWarn(),
                            String.valueOf(cabModel.getFcbCapaWarn())));
            paramMdl.setFil030VerKbn(
                    NullDefault.getString(
                            paramMdl.getFil030VerKbn(),
                            String.valueOf(cabModel.getFcbVerKbn())));
            paramMdl.setFil030VerAllKbn(
                    NullDefault.getString(
                            paramMdl.getFil030VerAllKbn(),
                            String.valueOf(cabModel.getFcbVerallKbn())));
            paramMdl.setFil030Biko(
                    NullDefault.getString(paramMdl.getFil030Biko(), cabModel.getFcbBiko()));

            if (paramMdl.getFile030ErrlAutoKbn() == -1) {
                paramMdl.setFile030ErrlAutoKbn(cabModel.getFcbSortFolder());
            }
            if (paramMdl.getFile030ErrlAutoFolder1() == -1) {
                paramMdl.setFile030ErrlAutoFolder1(cabModel.getFcbSortFolder1());
            }
            if (paramMdl.getFile030ErrlAutoFolder2() == -1) {
                paramMdl.setFile030ErrlAutoFolder2(cabModel.getFcbSortFolder2());
            }
            if (paramMdl.getFile030ErrlAutoFolder3() == -1) {
                paramMdl.setFile030ErrlAutoFolder3(cabModel.getFcbSortFolder3());
            }

            if (paramMdl.getFil030VerAllKbn().equals(
                    String.valueOf(GSConstFile.VERSION_ALL_KBN_OFF))) {
                paramMdl.setFil030VerKbn(String.valueOf(GSConstFile.VERSION_KBN_OFF));
            }

            if (paramMdl.getCmnMode().equals(GSConstFile.CMN_MODE_EDT)
                    && cabModel.getFcbMark() > 0 && !paramMdl.getFil030InitFlg().equals("1")) {
                if (cabModel.getFcbMark() > 0) {
                    CmnBinfModel binMdl = cmnBiz.getBinInfo(con__, cabModel.getFcbMark(),
                            reqMdl__.getDomain());
                    if (binMdl != null) {

                        //アイコンのテンポラリディレクトリパス
                        String markTempDir =
                                IOTools.replaceFileSep(tempDir);

                        //添付ファイルをテンポラリディレクトリに格納する。
                        String imageSaveName =
                                cmnBiz.saveSingleTempFile(binMdl, appRoot, markTempDir);
                        paramMdl.setFil030ImageName(binMdl.getBinFileName());
                        paramMdl.setFil030ImageSaveName(imageSaveName);
                    }
                }
            }

            //添付ファイルのラベルを設定する。
            paramMdl.setFil030SelectTempFilesMark(null);
        }
    }

    /**
     * <br>管理者設定のバージョン管理区分を取得します。
     * @param paramMdl Fil030ParamModel
     * @param con コネクション
     * @return verKbn バージョン管理区分（管理者設定）
     * @throws SQLException SQL実行時例外
     */
    private int __setVerKbnAdmin(Fil030ParamModel paramMdl, Connection con)
    throws SQLException {

        FileAconfDao aconfDao = new FileAconfDao(con);
        FileAconfModel aconfMdl = aconfDao.select();

        if (aconfMdl == null) {
            return GSConstFile.VERSION_KBN_ON;
        }
        paramMdl.setAdmVerKbn(aconfMdl.getFacVerKbn());

        return aconfMdl.getFacVerKbn();
    }
    /**
     * 電子帳簿保存法 自動振り分け機能の選択一覧を取得する
     * @param paramMdl Fil030ParamModel
     * @param firstFolder 第1階層フラグ
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<LabelValueBean> __getAutoFolderList(Fil030ParamModel paramMdl,
            boolean firstFolder)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
        if (!firstFolder) {
            list.add(new LabelValueBean(gsMsg.getMessage("cmn.no"), "0"));
        }
        list.add(new LabelValueBean(gsMsg.getMessage("fil.fil030.16"), "1"));
        list.add(new LabelValueBean(gsMsg.getMessage("fil.fil030.17"), "2"));
        list.add(new LabelValueBean(gsMsg.getMessage("fil.fil030.18"), "3"));
        list.add(new LabelValueBean(gsMsg.getMessage("user.35"), "4"));
        return list;
    }
    
    /**
     * <br>一括編集の際に、複数のキャビネット区分が混在していないかをチェックします。(削除済みチェックは行われていることが前提)
     * @param sid 一括編集対象キャビネットSID
     * @return true:キャビネット区分が混在していない, false:キャビネット区分が混在している
     * @throws SQLException SQL実行時例外
     */
    public boolean checkCabinetType(String[] sid) throws SQLException {

        FileCabinetDao fcbDao = new FileCabinetDao(con__);
        List<Integer> sidList = Stream.of(sid)
                .map(str -> NullDefault.getInt(str, -1))
                .collect(Collectors.toList());
        List<FileCabinetModel> fcbList = fcbDao.select(sidList);
        boolean firstFlg = true;
        int personalFlg = 0;
        int errlFlg = 0;
        for (FileCabinetModel mdl : fcbList) {
            if (firstFlg) {
                personalFlg = mdl.getFcbPersonalFlg();
                errlFlg = mdl.getFcbErrl();
                firstFlg = false;
            }
            if (personalFlg != mdl.getFcbPersonalFlg() || errlFlg != mdl.getFcbErrl()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * <br>指定された表示キャビネットが正しいかを判定します。
     * @param paramMdl パラメータ情報
     * @return true:キャビネット区分が正しい，false:キャビネット区分が不正
     * @throws SQLException SQL実行時例外
     */
    public boolean checkCabinetEdit(Fil030ParamModel paramMdl) throws SQLException {

        String dspCabinet = paramMdl.getFil010DspCabinetKbn();
        if (!dspCabinet.equals(GSConstFile.DSP_CABINET_PRIVATE)
                && !dspCabinet.equals(GSConstFile.DSP_CABINET_PUBLIC)
                && !dspCabinet.equals(GSConstFile.DSP_CABINET_ERRL)) {
            return false;
        }
        
        String mode = paramMdl.getCmnMode();
        if (mode.equals(GSConstFile.CMN_MODE_ADD)) {
            return true;
        }

        String cabSid = paramMdl.getFil030SelectCabinet();
        if (mode.equals(GSConstFile.CMN_MODE_MLT)) {
            cabSid = paramMdl.getFil220sltCheck()[0];
        }
                
        FileCabinetDao fcbDao = new FileCabinetDao(con__);
        FileCabinetModel fcbMdl = fcbDao.select(NullDefault.getInt(cabSid, -1));
        String fcbCabinet = GSConstFile.DSP_CABINET_PUBLIC;
        if (fcbMdl.getFcbPersonalFlg() == GSConstFile.CABINET_PRIVATE_USE) {
            fcbCabinet = GSConstFile.DSP_CABINET_PRIVATE;
        } else if (fcbMdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON) {
            fcbCabinet = GSConstFile.DSP_CABINET_ERRL;
        }
        if (!dspCabinet.equals(fcbCabinet)) {
            return false;
        }
        return true;
    }
}
