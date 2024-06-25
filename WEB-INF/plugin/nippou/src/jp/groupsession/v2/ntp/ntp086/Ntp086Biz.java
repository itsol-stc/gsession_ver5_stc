package jp.groupsession.v2.ntp.ntp086;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.dao.NtpTemplateSortDao;
import jp.groupsession.v2.ntp.dao.NtpTmpMemberDao;
import jp.groupsession.v2.ntp.model.NtpTemplateModel;
import jp.groupsession.v2.ntp.model.NtpTmpMemberModel;
import net.sf.json.JSONObject;

/**
 * <br>[機  能] 日報 日報テンプレート一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp086Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp086Biz.class);
    /** DBコネクション */
    public Connection con__ = null;
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /** ユーザポップアップ最大件数 */
    private static final int USR_POP_SIZE = 10;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Ntp086Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Ntp086ParamModel
     * @param con コネクション
     * @throws Exception SQL実行エラー
     */
    public void setInitData(Ntp086ParamModel paramMdl,
            Connection con) throws Exception {

        //目標情報を取得する
        Ntp086Dao templateDao = new Ntp086Dao(con);
        List<NtpTemplateModel> templateList = templateDao.getTemplateList();
        List<Ntp086TemplateDspModel> templateDspList = new ArrayList<Ntp086TemplateDspModel>();
        Ntp086TemplateDspModel templateDspMdl = null;

        for (NtpTemplateModel trgMdl : templateList) {
            templateDspMdl = new Ntp086TemplateDspModel();

            templateDspMdl.setTemplateSid(trgMdl.getNttSid());
            templateDspMdl.setTemplateName(trgMdl.getNttName());
            templateDspMdl.setTemplateSort(trgMdl.getNttSort());
            templateDspMdl.setTemplateValue(String.valueOf(trgMdl.getNttSid()));
            templateDspList.add(templateDspMdl);
        }
        paramMdl.setNtp086TemplateList(templateDspList);

        if (StringUtil.isNullZeroString(paramMdl.getNtp086sortTemplate())
                && templateList.size() > 0) {

            Ntp086TemplateDspModel appspMdl = templateDspList.get(0);
            paramMdl.setNtp086sortTemplate(String.valueOf(appspMdl.getTemplateSid()));
        }
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl フォーム
     * @param userSid ユーザSID
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Ntp086ParamModel paramMdl, int userSid, int changeKbn)
            throws SQLException {
        
        SortResult<NtpTemplateModel> result = null;
        final Ntp086Dao dao = new Ntp086Dao(con);
        final NtpTemplateSortDao ntsDao = new NtpTemplateSortDao(con);

        //ラジオ選択値取得
        String selectedKey = paramMdl.getNtp086sortTemplate();
        if (StringUtil.isNullZeroString(selectedKey) || !ValidateUtil.isNumber(selectedKey)) {
            return;
        }

        //選択された項目の施設グループSID + ソート順
        int motoSid = Integer.parseInt(selectedKey);

        SortChangeBiz<NtpTemplateModel> sortBiz =
                SortChangeBiz.<NtpTemplateModel> builder()
                .setFuncTargetList(() -> {
                    return dao.getTemplateList();
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getNttSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getNttSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getNttSid() == m2.getNttSid()) {
                        return 0;
                    } else {
                        return (m1.getNttSid() - m2.getNttSid()) 
                                / Math.abs(m1.getNttSid() - m2.getNttSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    ntsDao.updateSort(m.getNttSid(), newSort);
                })
                .build();
        
        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        
        if (result != null) {
            String newSortRadio = String.valueOf(result.getMdl().getNttSid());
            paramMdl.setNtp086sortTemplate(newSortRadio);
        }
    }

    /**
     * <br>[機  能] ユーザ一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param nttSid テンプレートSID
     * @param pageNum ページ番号
     * @throws Exception SQL実行時例外
     * @return jsonData jsonコメントリスト
     */
    public JSONObject getJsonUsrList(Connection con,
                            int nttSid, int pageNum) throws Exception {


        NtpTmpMemberDao npmDao = new NtpTmpMemberDao(con);
        CmnUsrmInfDao cmnUsrInfDao = new CmnUsrmInfDao(con);

        JSONObject jsonData = new JSONObject();
        ArrayList<NtpTmpMemberModel> npmList = null;
        ArrayList<Integer> gpSidList = new ArrayList<Integer>();
        ArrayList<Integer> usrSidIntList = new ArrayList<Integer>();
        ArrayList<Integer> gpUsrSidIntList = new ArrayList<Integer>();
        ArrayList<String> usrSidStrList = new ArrayList<String>();
        List<CmnUsrmInfModel> usrInfList = new ArrayList<CmnUsrmInfModel>();
        List<CmnUsrmInfModel> usrInfDataList = new ArrayList<CmnUsrmInfModel>();

        npmList = npmDao.select(nttSid);

        if (pageNum == 0) {
            pageNum = 1;
        }

        if (!npmList.isEmpty()) {
            for (NtpTmpMemberModel nsmMdl : npmList) {

                if (nsmMdl.getGrpSid() != -1) {
                    gpSidList.add(nsmMdl.getGrpSid());
                } else if (nsmMdl.getUsrSid() != -1) {
                    usrSidStrList.add(String.valueOf(nsmMdl.getUsrSid()));
                    usrSidIntList.add(nsmMdl.getUsrSid());
                }
            }

            //ユーザSIDからユーザ情報を取得
            if (!usrSidStrList.isEmpty()) {
                usrInfList = cmnUsrInfDao.getUsersInfList(
                 usrSidStrList.toArray(
                         new String[usrSidStrList.size()]), new CmnCmbsortConfModel());

            }

            //グループSIDからユーザ情報を取得
            if (!gpSidList.isEmpty()) {
                UserSearchDao usearchDao = new UserSearchDao(con);
                gpUsrSidIntList = usearchDao.getBelongUserSids(gpSidList, usrSidIntList);
                if (!gpUsrSidIntList.isEmpty()) {
                    ArrayList<String> usrSidList = new ArrayList<String>();
                    for (int usrSid : gpUsrSidIntList) {
                        usrSidList.add(String.valueOf(usrSid));
                    }
                    usrInfList.addAll(cmnUsrInfDao.getUsersInfList(
                            usrSidList.toArray(
                             new String[usrSidList.size()]), new CmnCmbsortConfModel()));
                }
            }


            //Jsonデータ成形
            if (!usrInfList.isEmpty()) {

                int maxPageSize = PageUtil.getPageCount(usrInfList.size(), USR_POP_SIZE);

                if (pageNum > maxPageSize) {
                    pageNum = maxPageSize;
                }

                int maxSize = pageNum * USR_POP_SIZE;

                Ntp086UsrInfModel ntp086UsrInfMdl = new Ntp086UsrInfModel();
                CmnUsrmInfModel usrInfMdl = null;

                ntp086UsrInfMdl.setMaxPageSize(maxPageSize);
                ntp086UsrInfMdl.setPageNum(pageNum);

                for (int i = (pageNum - 1) * USR_POP_SIZE; i < usrInfList.size(); i++) {
                    if (i < maxSize) {
                        usrInfMdl = usrInfList.get(i);
                        usrInfMdl.setUsiAdate(null);
                        usrInfMdl.setUsiBdate(null);
                        usrInfMdl.setUsiEdate(null);
                        usrInfMdl.setUsiLtlgin(null);
                        usrInfMdl.setUsiEntranceDate(null);
                        usrInfDataList.add(usrInfMdl);
                    } else {
                        break;
                    }
                }

                ntp086UsrInfMdl.setUsrInfDataList(usrInfDataList);

                jsonData = JSONObject.fromObject(ntp086UsrInfMdl);

            }
        }

        return jsonData;

    }
}