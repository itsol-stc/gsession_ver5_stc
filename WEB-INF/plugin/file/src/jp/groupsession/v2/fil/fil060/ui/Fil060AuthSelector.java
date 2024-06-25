package jp.groupsession.v2.fil.fil060.ui;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ExclusionConf;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ICustomUserGroupSelector;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileDAccessConfDao;
/**
 *
 * <br>[機  能] フォルダ登録画面 アクセス権限 選択UIクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil060AuthSelector extends UserGroupSelector
implements ICustomUserGroupSelector {
    /**
     *
     * コンストラクタ
     * @param param
     */
    protected Fil060AuthSelector(
            ParamForm<? extends Fil060AuthSelector> param) {
        super(param);
    }

    /**
     *
     * <br>[機  能] ビルダークラスの取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダー
     */
    public static ParamForm<? extends Fil060AuthSelector> builder() {
        ParamForm<Fil060AuthSelector> ret
            = new ParamForm<>(Fil060AuthSelector.class);
        return ret;
    }

    /**
     *
     * <br>[機  能] グループ選択除外設定を返すリスナメソッド
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl
     * @param con
     * @param paramModel
     * @return 除外設定
     * @throws SQLException
     */
    public ExclusionConf answerGroupExclusion(RequestModel reqMdl, Connection con,
            ParameterObject paramModel) throws SQLException {

        int parentSid = _getParentSid(con, paramModel, reqMdl);
        int personalFlg = _getPersonalFlg(paramModel);

        ExclusionConf result = new ExclusionConf(false);

        int authType = Integer.parseInt(GSConstFile.ACCESS_KBN_READ);

        //親ディレクトリのアクセス制限を取得
        //編集候補に閲覧候補も選択できるようにする
        authType = Integer.parseInt(GSConstFile.ACCESS_KBN_READ);

        //共有キャビネット内であれば、アクセス可能なグループのみ取得 (個人キャビネットは全グループ選択可)
        if (personalFlg == Integer.parseInt(GSConstFile.DSP_CABINET_PUBLIC)) {
            result = new ExclusionConf(true);

            //アクセス許可グループを設定
            FileDAccessConfDao daConfDao = new FileDAccessConfDao(con);
            String[] grpSids = daConfDao.getAccessParentGroup(parentSid, authType);
            result.addAll(
                    Arrays.asList(grpSids).stream()
                    .map(s -> s = s.substring(1))
                    .collect(Collectors.toList()));

            //アクセス許可ユーザの所属グループを設定
            result.addAll(
                    Arrays.asList(
                            daConfDao.getAccessParentUserForBelongGroup(parentSid, authType)));

            result.addAll(Arrays.asList(grpSids));
        }

        return result;
    }

    /**
     *
     * <br>[機  能] ユーザ選択除外設定を返すリスナメソッド
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl
     * @param con
     * @param paramModel
     * @return 除外設定
     * @throws SQLException
     */
    public ExclusionConf answerUserExclusion(RequestModel reqMdl,
            Connection con, ParameterObject paramModel) throws SQLException {

        int parentSid = _getParentSid(con, paramModel, reqMdl);
        int personalFlg = _getPersonalFlg(paramModel);
        ExclusionConf ret = new ExclusionConf(false);

        //共有キャビネット内にある場合は親ディレクトリのアクセス権限に従う
        if (personalFlg == Integer.parseInt(GSConstFile.DSP_CABINET_PUBLIC)) {

            //親ディレクトリのアクセス権限(許可するユーザ・グループ)を取得する
            List<String> accessSidList = new ArrayList<String>();
            accessSidList.addAll(__getParentAccessSids(con, parentSid,
                                            Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE)));
            accessSidList.addAll(__getParentAccessSids(con, parentSid,
                                            Integer.parseInt(GSConstFile.ACCESS_KBN_READ)));

            if (!accessSidList.isEmpty()) {
                ret = new ExclusionConf(true);
                ret.addAll(accessSidList);
            }

        } else if (personalFlg == GSConstFile.CABINET_KBN_PRIVATE) {
            ret.add(String.valueOf(reqMdl.getSmodel().getUsrsid()));
        }

        return ret;
    }

    protected int _getPersonalFlg(ParameterObject paramModel) {
        return (Integer) paramModel.get("fil040PersonalFlg");
    }

    protected int _getParentSid(Connection con, ParameterObject paramModel, RequestModel reqMdl)
    throws SQLException {
        FilCommonBiz filBiz = new FilCommonBiz(reqMdl, con);
        int parentSid = NullDefault.getInt((String) paramModel.get("fil050ParentDirSid"), -1);
        if (parentSid < 1) {
            int dirSid = NullDefault.getInt((String) paramModel.get("fil050DirSid"), -1);
            parentSid = filBiz.getParentDirSid(dirSid);
        }

        return parentSid;
    }

    /**
     * 親ディレクトリのアクセス権限を取得する
     * @param con DBコネクション
     * @param parentSid 親ディレクトリSID
     * @param auth 権限区分
     * @return SID
     * @throws NumberFormatException 実行例外
     * @throws SQLException 実行例外
     */
    private List<String> __getParentAccessSids(Connection con, int parentSid, int auth)
    throws NumberFormatException, SQLException {

        FileDAccessConfDao daConfDao = new FileDAccessConfDao(con);
        List<String> sids = new ArrayList<String>();
        String[] parentSids = daConfDao.getAccessUser(parentSid, auth);
        if (parentSids != null) {
            sids.addAll(Arrays.asList(parentSids));
        }

        return sids;
    }
}