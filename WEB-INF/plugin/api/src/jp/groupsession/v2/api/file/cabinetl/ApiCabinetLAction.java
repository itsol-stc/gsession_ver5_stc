package jp.groupsession.v2.api.file.cabinetl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil010.Fil010Biz;
import jp.groupsession.v2.fil.fil010.FileCabinetDspModel;

/**
 * <br>[機  能] キャビネット一覧を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiCabinetLAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiCabinetLAction.class);

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        log__.debug("createXml start");

        ApiCabinetLForm thisForm = (ApiCabinetLForm) form;

        //ファイル管理プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstFile.PLUGIN_ID_FILE, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstFile.PLUGIN_ID_FILE));
            return null;
        }

        FilCommonBiz filCmnBiz = new FilCommonBiz(getRequestModel(req), con);

        ArrayList<FileCabinetDspModel> cabList = null;
        Fil010Biz fil010Biz = new Fil010Biz(getRequestModel(req));
        try {
            int errlKbn = thisForm.getCabinetKbn();
            int cabKbn = GSConstFile.CABINET_KBN_PUBLIC;
            if (errlKbn == GSConstFile.ERRL_KBN_ON) {
                cabKbn = GSConstFile.CABINET_KBN_ERRL;
            }
            //キャビネット一覧を取得する。
            cabList = fil010Biz.getAccessCabinetList(umodel, con, true, cabKbn);
        } catch (SQLException e) {
            log__.error("キャビネット一覧の取得に失敗", e);
        }

        //ルートエレメントResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        //XMLデータ作成
        for (FileCabinetDspModel cabMdl : cabList) {

            Element result = new Element("Result");
            resultSet.addContent(result);

            //FcbSid キャビネットSID
            result.addContent(_createElement("FcbSid", cabMdl.getFcbSid()));

            //FcbName キャビネット名
            result.addContent(_createElement("FcbName", cabMdl.getFcbName()));

            //FcbAccessKbn アクセス権限
            result.addContent(_createElement("FcbAccessKbn", cabMdl.getFcbAccessKbn()));

            //FcbCapaKbn 容量設定区分
            result.addContent(_createElement("FcbCapaKbn", cabMdl.getFcbCapaKbn()));

            //FcbCapaSize 容量設定
            result.addContent(_createElement("FcbCapaSize", cabMdl.getFcbCapaSize()));

            //FcbCapaWarn 警告パーセンテージ
            result.addContent(_createElement("FcbCapaWarn", cabMdl.getFcbCapaWarn()));

            //FcbVerKbn バージョン管理区分
            result.addContent(_createElement("FcbVerKbn", cabMdl.getFcbVerKbn()));

            //FcbVerAllKbn バージョン管理一括設定区分
            result.addContent(_createElement("FcbVerAllKbn", cabMdl.getFcbVerallKbn()));

            //FcbBiko 備考
            result.addContent(_createElement("FcbBiko", cabMdl.getFcbBiko()));

            //FcbSort 表示順
            result.addContent(_createElement("FcbSort", cabMdl.getFcbSort()));

            //FcbAuid 登録者ID
            result.addContent(_createElement("FcbAuid", cabMdl.getFcbAuid()));

            //FcbAdate 登録日時
            Element fcbadate = new Element("FcbAdate");
            fcbadate.addContent(cabMdl.getFcbAdate().getTimeStamp());
            result.addContent(fcbadate);

            //FcbEuid 更新者ID
            result.addContent(_createElement("FcbEuid", cabMdl.getFcbEuid()));

            //FcbEdate 更新日時
            Element fcbedate = new Element("FcbEdate");
            fcbedate.addContent(cabMdl.getFcbEdate().getTimeStamp());
            result.addContent(fcbedate);

            //WseArea 使用領域
            result.addContent(_createElement("UseArea", cabMdl.getDiskUsedString()));

            //WarnKbn 使用領域警告区分
            result.addContent(_createElement("WarnKbn", cabMdl.getDiskUsedWarning()));

            //ReadKbn 閲覧権限
            result.addContent(_createElement("ReadKbn", 0));

            //WriteKbn 編集権限
            if (filCmnBiz.isEditCabinetUser(cabMdl.getFcbSid(), -1)) {
                result.addContent(_createElement("WriteKbn", 0));
            } else {
                result.addContent(_createElement("WriteKbn", 1));
            }

            //キャビネット区分
            result.addContent(_createElement("FcbErrlKbn", cabMdl.getFcbErrl()));
            String sortFolder = "";
            String sortFolder1 = "";
            String sortFolder2 = "";
            String sortFolder3 = "";

            if (cabMdl.getFcbErrl() == GSConstFile.ERRL_KBN_ON) {
                sortFolder = String.valueOf(cabMdl.getFcbSortFolder());
                sortFolder1 = String.valueOf(cabMdl.getFcbSortFolder1());
                sortFolder2 = String.valueOf(cabMdl.getFcbSortFolder2());
                sortFolder3 = String.valueOf(cabMdl.getFcbSortFolder3());
            }
            //保存先振り分け機能
            result.addContent(_createElement("FcbSortFolder", sortFolder));
            //振り分けフォルダ1
            result.addContent(_createElement("FcbSortFolder1", sortFolder1));
            //振り分けフォルダ2
            result.addContent(_createElement("FcbSortFolder2", sortFolder2));
            //振り分けフォルダ3
            result.addContent(_createElement("FcbSortFolder3", sortFolder3));
        }

        log__.debug("createXml end");
        return doc;
    }
}
