package jp.groupsession.v2.cht.cht090;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cht.cht080.Cht080Const;
import jp.groupsession.v2.cht.dao.ChtSpaccessDao;
import jp.groupsession.v2.cht.dao.ChtSpaccessPermitDao;
import jp.groupsession.v2.cht.dao.ChtSpaccessTargetDao;
import jp.groupsession.v2.cht.model.ChtSpaccessModel;
import jp.groupsession.v2.cht.model.ChtSpaccessPermitModel;
import jp.groupsession.v2.cht.model.ChtSpaccessTargetModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;

/**
 * <br>[機  能] チャット 特例アクセス登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht090Biz {

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws Exception 実行時例外
     */
    public void setInitData(Connection con, Cht090ParamModel paramMdl, RequestModel reqMdl)
    throws Exception {

        //初期表示
        if (paramMdl.getCht090initFlg() == Cht080Const.DSP_FIRST) {
            if (paramMdl.getCht080editMode() == Cht080Const.EDITMODE_EDIT) {
                //編集
                _setAccessData(con, paramMdl);
            }

            paramMdl.setCht090initFlg(Cht080Const.DSP_ALREADY);
        }

        //グループが未選択の場合、デフォルトグループを設定
        GroupBiz grpBiz = new GroupBiz();
        if (paramMdl.getCht090subjectGroup() == -1) {
            int defGrp = grpBiz.getDefaultGroupSid(reqMdl.getSmodel().getUsrsid(), con);
            paramMdl.setCht090subjectGroup(defGrp);
        }
        if (paramMdl.getCht090accessGroup() == -1) {
            int defGrp = grpBiz.getDefaultGroupSid(reqMdl.getSmodel().getUsrsid(), con);
            paramMdl.setCht090accessGroup(defGrp);
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
        paramMdl.setCht090positionCombo(positionCombo);
    }

    /**
     * <br>[機  能] 特例アクセス情報をパラメータ情報へ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    protected void _setAccessData(Connection con, Cht090ParamModel paramMdl) throws SQLException {
        int chsSid = paramMdl.getCht080editData();

        //特例アクセス情報の設定
        ChtSpaccessDao accessDao = new ChtSpaccessDao(con);
        ChtSpaccessModel accessMdl = accessDao.select(chsSid);
        paramMdl.setCht090name(accessMdl.getChsName());
        paramMdl.setCht090biko(accessMdl.getChsBiko());

        //特例アクセス_制限対象の設定
        List<String> targetList = new ArrayList<String>();
        ChtSpaccessTargetDao accessTargetDao = new ChtSpaccessTargetDao(con);
        List<ChtSpaccessTargetModel> cstTargetList = accessTargetDao.getTargetList(chsSid);
        for (ChtSpaccessTargetModel targetMdl : cstTargetList) {
            String targetSid = Integer.toString(targetMdl.getCstPsid());
            if (targetMdl.getCstType() == Cht080Const.SP_TYPE_GROUP) {
                //グループ
                targetSid = "G" + targetSid;
            }
            targetList.add(targetSid);
        }
        paramMdl.setCht090subject((String[]) targetList.toArray(new String[targetList.size()]));


        //特例アクセス_許可対象の設定
        List<String> viewList = new ArrayList<String>();
        ChtSpaccessPermitDao accessPermitDao = new ChtSpaccessPermitDao(con);
        List<ChtSpaccessPermitModel> cspPermitList = accessPermitDao.getPermitList(chsSid);
        for (ChtSpaccessPermitModel permitMdl : cspPermitList) {
            String permitSid = Integer.toString(permitMdl.getCspPsid());
            if (permitMdl.getCspType() == Cht080Const.SP_TYPE_POSITION) {
                //役職
                paramMdl.setCht090position(Integer.parseInt(permitSid));
            } else {
                if (permitMdl.getCspType() == Cht080Const.SP_TYPE_GROUP) {
                    //グループ
                    permitSid = "G" + permitSid;
                }
                viewList.add(permitSid);
            }
        }
        paramMdl.setCht090accessUser((String[]) viewList.toArray(new String[viewList.size()]));
    }

    /**
     * <br>[機  能] 特例アクセスの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void deleteAccess(Connection con, Cht090ParamModel paramMdl)
    throws SQLException {

        ChtSpaccessDao accessDao = new ChtSpaccessDao(con);
        ChtSpaccessTargetDao accessTargetDao = new ChtSpaccessTargetDao(con);
        ChtSpaccessPermitDao accessPermitDao = new ChtSpaccessPermitDao(con);

        int ssaSid = paramMdl.getCht080editData();
        accessDao.delete(ssaSid);
        accessTargetDao.delete(ssaSid);
        accessPermitDao.delete(ssaSid);
   }

}