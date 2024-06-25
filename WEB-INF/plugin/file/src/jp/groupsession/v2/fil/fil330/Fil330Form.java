package jp.groupsession.v2.fil.fil330;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil300.Fil300Biz;
import jp.groupsession.v2.fil.fil300.Fil300Form;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] 仮登録ファイル削除画面
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil330Form extends Fil300Form {
    /** 削除選択対象*/
    private String[] fil330SelectDel__;
    /** 表示ファイル情報一覧 */
    private List<Fil330DspFileModel> fil330FileList__;
    /** ファイル一覧ページ選択値 */
    private int fil330FileListPageNo__ = 1;
    /** ファイル一覧ページ最大値 */
    private List<LabelValueBean> fil330FileListPageLabel__ = new ArrayList<>();
    /** 表示フォルダ選択*/
    private String fil330SelectDsp__;
    /** 表示フォルダ選択キャビネット部*/
    private String fil330SelectCabinet__;
    /** フォルダツリー開閉状態 */
    private String fil330Tree__;


    /**
     * <p>fil330SelectDel を取得します。
     * @return fil330SelectDel
     * @see jp.groupsession.v2.fil.fil330.Fil330Form#fil330SelectDel__
     */
    public String[] getFil330SelectDel() {
        return fil330SelectDel__;
    }

    /**
     * <p>fil330SelectDel をセットします。
     * @param fil330SelectDel fil330SelectDel
     * @see jp.groupsession.v2.fil.fil330.Fil330Form#fil330SelectDel__
     */
    public void setFil330SelectDel(String[] fil330SelectDel) {
        fil330SelectDel__ = fil330SelectDel;
    }
    /**
     * <p>fil330FileList を取得します。
     * @return fil330FileList
     * @see jp.groupsession.v2.fil.fil330.Fil330Form#fil330FileList__
     */
    public List<Fil330DspFileModel> getFil330FileList() {
        return fil330FileList__;
    }

    /**
     * <p>fil330FileList をセットします。
     * @param fil330FileList fil330FileList
     * @see jp.groupsession.v2.fil.fil330.Fil330Form#fil330FileList__
     */
    public void setFil330FileList(List<Fil330DspFileModel> fil330FileList) {
        fil330FileList__ = fil330FileList;
    }

    /**
     * <p>fil330FileListPageNo を取得します。
     * @return fil330FileListPageNo
     * @see jp.groupsession.v2.fil.fil330.Fil330Form#fil330FileListPageNo__
     */
    public int getFil330FileListPageNo() {
        return fil330FileListPageNo__;
    }

    /**
     * <p>fil330FileListPageNo をセットします。
     * @param fil330FileListPageNo fil330FileListPageNo
     * @see jp.groupsession.v2.fil.fil330.Fil330Form#fil330FileListPageNo__
     */
    public void setFil330FileListPageNo(int fil330FileListPageNo) {
        fil330FileListPageNo__ = fil330FileListPageNo;
    }

    /**
     * <p>fil330FileListPageLabel を取得します。
     * @return fil330FileListPageLabel
     * @see jp.groupsession.v2.fil.fil330.Fil330Form#fil330FileListPageLabel__
     */
    public List<LabelValueBean> getFil330FileListPageLabel() {
        return fil330FileListPageLabel__;
    }

    /**
     * <p>fil330FileListPageLabel をセットします。
     * @param fil330FileListPageLabel fil330FileListPageLabel
     * @see jp.groupsession.v2.fil.fil330.Fil330Form#fil330FileListPageLabel__
     */
    public void setFil330FileListPageLabel(List<LabelValueBean> fil330FileListPageLabel) {
        fil330FileListPageLabel__ = fil330FileListPageLabel;
    }


    /**
     *
     * <br>[機  能] 削除入力チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return エラーメッセージ
     * @throws SQLException
     */
    public ActionErrors validateDelete(
            Connection con, RequestModel reqMdl) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textDelTarget = gsMsg.getMessage("cmn.remove.directory");

        //未選択チェック
        if (fil330SelectDel__ == null || fil330SelectDel__.length < 1) {
            msg = new ActionMessage(
                    "error.select.required.text",
                    textDelTarget);
            StrutsUtil.addMessage(errors, msg, "fil040SelectDel");
            return errors;
        }
        
        //登録画面で登録したファイルかをチェック
        if (getFil300BeforeDspFlg() == GSConstFile.BEFORE_DSP_FIL080) {
            Fil330ParamModel paramMdl = new Fil330ParamModel();
            paramMdl.setParam(this);
            Fil300Biz biz = new Fil300Biz(reqMdl, con);
            List<String> insertFile = biz.getErrlFile080(paramMdl);
            if (!insertFile.containsAll(Arrays.asList(fil330SelectDel__))) {
                StrutsUtil.addMessage(errors,
                        new ActionMessage(
                                "error.delete.notfound",
                                gsMsg.getMessage("fil.187"),
                                gsMsg.getMessage("cmn.delete")
                                ),
                        "error.edit.power.notfound");
                
                return errors;
            }
        }

        //不正SID選択チェック
        FilCommonBiz filCmnBiz = new FilCommonBiz(reqMdl, con);
        if (filCmnBiz.checkEditErrlData(
                        Stream.of(fil330SelectDel__)
                            .map(str -> NullDefault.getInt(str, 0))
                            .collect(Collectors.toSet())
                        )
                == false
                ) {
            StrutsUtil.addMessage(errors,
                    new ActionMessage(
                            "error.edit.power.notfound",
                            gsMsg.getMessage("fil.187"),
                            gsMsg.getMessage("cmn.delete")
                            ),
                    "error.edit.power.notfound");
            return errors;

        }



        return errors;
    }

    /**
     * <p>fil330SelectDsp を取得します。
     * @return fil330SelectDsp
     * @see jp.groupsession.v2.fil.fil330.Fil330Form#fil330SelectDsp__
     */
    public String getFil330SelectDsp() {
        return fil330SelectDsp__;
    }

    /**
     * <p>fil330SelectDsp をセットします。
     * @param fil330SelectDsp fil330SelectDsp
     * @see jp.groupsession.v2.fil.fil330.Fil330Form#fil330SelectDsp__
     */
    public void setFil330SelectDsp(String fil330SelectDsp) {
        fil330SelectDsp__ = fil330SelectDsp;
    }

    /**
     * <p>fil330SelectCabinet を取得します。
     * @return fil330SelectCabinet
     * @see jp.groupsession.v2.fil.fil330.Fil330Form#fil330SelectCabinet__
     */
    public String getFil330SelectCabinet() {
        return fil330SelectCabinet__;
    }

    /**
     * <p>fil330SelectCabinet をセットします。
     * @param fil330SelectCabinet fil330SelectCabinet
     * @see jp.groupsession.v2.fil.fil330.Fil330Form#fil330SelectCabinet__
     */
    public void setFil330SelectCabinet(String fil330SelectCabinet) {
        fil330SelectCabinet__ = fil330SelectCabinet;
    }

    /**
     * <p>fil330Tree を取得します。
     * @return fil330Tree
     * @see jp.groupsession.v2.fil.fil330.Fil330Form#fil330Tree__
     */
    public String getFil330Tree() {
        return fil330Tree__;
    }

    /**
     * <p>fil330Tree をセットします。
     * @param fil330Tree fil330Tree
     * @see jp.groupsession.v2.fil.fil330.Fil330Form#fil330Tree__
     */
    public void setFil330Tree(String fil330Tree) {
        fil330Tree__ = fil330Tree;
    }

}
