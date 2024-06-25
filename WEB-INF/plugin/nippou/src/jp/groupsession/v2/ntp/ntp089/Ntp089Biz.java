package jp.groupsession.v2.ntp.ntp089;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.ntp.dao.NtpSpaccessDao;
import jp.groupsession.v2.ntp.dao.NtpSpaccessPermitDao;
import jp.groupsession.v2.ntp.dao.NtpSpaccessTargetDao;
import jp.groupsession.v2.ntp.model.NtpSpaccessModel;
import jp.groupsession.v2.ntp.model.NtpSpaccessPermitModel;
import jp.groupsession.v2.ntp.model.NtpSpaccessTargetModel;
import jp.groupsession.v2.ntp.ntp088.Ntp088Const;

/**
 * <br>[機  能] 日報 テンプレート登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp089Biz {

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws Exception 実行時例外
     */
    public void setInitData(Connection con, Ntp089ParamModel paramMdl, RequestModel reqMdl)
    throws Exception {

        //初期表示
        if (paramMdl.getNtp089initFlg() == Ntp088Const.DSP_FIRST) {
            if (paramMdl.getNtp088editMode() == Ntp088Const.EDITMODE_EDIT) {
                //編集
                _setAccessData(con, paramMdl);
            }

            paramMdl.setNtp089initFlg(Ntp088Const.DSP_ALREADY);
        }

        //グループが未選択の場合、デフォルトグループを設定
        GroupBiz grpBiz = new GroupBiz();
        if (paramMdl.getNtp089subjectGroup() == -1) {
            int defGrp = grpBiz.getDefaultGroupSid(reqMdl.getSmodel().getUsrsid(), con);
            paramMdl.setNtp089subjectGroup(defGrp);
        }
        if (paramMdl.getNtp089accessGroup() == -1) {
            int defGrp = grpBiz.getDefaultGroupSid(reqMdl.getSmodel().getUsrsid(), con);
            paramMdl.setNtp089accessGroup(defGrp);
        }

        //役職を設定
        List<LabelValueBean> positionCombo = new ArrayList<LabelValueBean>();
        CmnPositionDao positionDao = new CmnPositionDao(con);
        List<CmnPositionModel>positionList = positionDao.getPosList(true);
        for (CmnPositionModel positionData : positionList) {
            LabelValueBean label
                = new LabelValueBean(positionData.getPosName(),
                                                String.valueOf(positionData.getPosSid()));
            if (positionData.getPosSid() != GSConst.POS_DEFAULT) {
                label.setLabel(label.getLabel() + "以上");
            }
            positionCombo.add(label);
        }
        paramMdl.setNtp089positionCombo(positionCombo);
    }

    /**
     * <br>[機  能] 特例アクセス情報をパラメータ情報へ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    protected void _setAccessData(Connection con, Ntp089ParamModel paramMdl) throws SQLException {
        int nsaSid = paramMdl.getNtp088editData();

        //特例アクセス情報の設定
        NtpSpaccessDao accessDao = new NtpSpaccessDao(con);
        NtpSpaccessModel accessMdl = accessDao.select(nsaSid);
        paramMdl.setNtp089name(accessMdl.getNsaName());
        paramMdl.setNtp089biko(accessMdl.getNsaBiko());

        //特例アクセス_制限対象の設定
        List<String> targetList = new ArrayList<String>();
        NtpSpaccessTargetDao accessTargetDao = new NtpSpaccessTargetDao(con);
        List<NtpSpaccessTargetModel> nstTargetList = accessTargetDao.getTargetList(nsaSid);
        for (NtpSpaccessTargetModel targetMdl : nstTargetList) {
            String targetSid = Integer.toString(targetMdl.getNstTsid());
            if (targetMdl.getNstType() == GSConst.ST_TYPE_GROUP) {
                //グループ
                targetSid = "G" + targetSid;
            }
            targetList.add(targetSid);
        }
        paramMdl.setNtp089subject((String[]) targetList.toArray(new String[targetList.size()]));


        //特例アクセス_許可対象の設定
        List<String> editList = new ArrayList<String>();
        List<String> viewList = new ArrayList<String>();
        NtpSpaccessPermitDao accessPermitDao = new NtpSpaccessPermitDao(con);
        List<NtpSpaccessPermitModel> nspPermitList = accessPermitDao.getPermitList(nsaSid);
        for (NtpSpaccessPermitModel permitMdl : nspPermitList) {
            String permitSid = Integer.toString(permitMdl.getNspPsid());
            if (permitMdl.getNspType() == GSConst.SP_TYPE_POSITION) {
                //役職
                paramMdl.setNtp089position(Integer.parseInt(permitSid));
                if (permitMdl.getNspAuth() == GSConst.SP_AUTH_EDIT) {
                    paramMdl.setNtp089positionAuth(Ntp089Form.POTION_AUTH_EDIT);
                } else {
                    paramMdl.setNtp089positionAuth(Ntp089Form.POTION_AUTH_VIEW);
                }

            } else {
                if (permitMdl.getNspType() == GSConst.SP_TYPE_GROUP) {
                    //グループ
                    permitSid = "G" + permitSid;
                }
                if (permitMdl.getNspAuth() == GSConst.SP_AUTH_EDIT) {
                    editList.add(permitSid);
                } else {
                    viewList.add(permitSid);
                }
            }
        }

        paramMdl.setNtp089editUser((String[]) editList.toArray(new String[editList.size()]));
        paramMdl.setNtp089accessUser((String[]) viewList.toArray(new String[viewList.size()]));

    }

    /**
     * <br>[機  能] 特例アクセスの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void deleteAccess(Connection con, Ntp089ParamModel paramMdl)
    throws SQLException {

        NtpSpaccessDao accessDao = new NtpSpaccessDao(con);
        NtpSpaccessTargetDao accessTargetDao = new NtpSpaccessTargetDao(con);
        NtpSpaccessPermitDao accessPermitDao = new NtpSpaccessPermitDao(con);

        int ssaSid = paramMdl.getNtp088editData();
        accessDao.delete(ssaSid);
        accessTargetDao.delete(ssaSid);
        accessPermitDao.delete(ssaSid);
   }

}