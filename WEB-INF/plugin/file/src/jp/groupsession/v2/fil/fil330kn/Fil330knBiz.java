package jp.groupsession.v2.fil.fil330kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.h2.util.StringUtils;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.fil.dao.FileErrlAdddataDao;
import jp.groupsession.v2.fil.fil300.FilErrlAdddataException;
import jp.groupsession.v2.fil.fil330.Fil330Biz;
import jp.groupsession.v2.fil.fil330.Fil330ParamModel;
import jp.groupsession.v2.fil.model.FileErrlAdddataModel;

public class Fil330knBiz {
    /** リクエストモデル*/
    private final RequestModel reqMdl__;
    /** コネクション*/
    private final Connection con__;

    public Fil330knBiz(RequestModel reqMdl, Connection con) {
        reqMdl__ = reqMdl;
        con__ = con;
    }
    /**
     *
     * <br>[機  能] 画面モデル初期化処理を実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl
     * @throws SQLException
     * @throws FilErrlAdddataException
     */
    public void setInitDsp(Fil330knParamModel paramMdl) throws SQLException {
        FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);
        List<FileErrlAdddataModel> dataList = new ArrayList<FileErrlAdddataModel>();
        //表示対象ファイル一覧の取得
        String[] sids = paramMdl.getFil330SelectDel();
        List<Integer> sidList = new ArrayList<Integer>();
        for (String sid : sids) {
            if (StringUtils.isNumber(sid)) {
                sidList.add(Integer.parseInt(sid));
            }
        }
        dataList.addAll(
                feaDao.getErrlAddDataList(sidList, reqMdl__.getSmodel())
                );

        int cabSid =
                dataList.stream()
                    .findAny()
                    .map(mdl -> mdl.getFcbSid())
                    .orElse(-1);
        Fil330Biz fil330Biz = new Fil330Biz(reqMdl__, con__);
        paramMdl.setFil330FileList(
                fil330Biz.createFileDspList(dataList,
                        cabSid
                        )
                );

    }
    /**
    *
    * <br>[機  能] 仮ファイル削除処理を実行する
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl
    * @return 削除ファイル名リスト
    */
   public List<String> deleteDirectory(
           Fil330ParamModel paramMdl
           ) throws SQLException, FilErrlAdddataException {

       FileErrlAdddataDao feaDao = new FileErrlAdddataDao(con__);
       List<FileErrlAdddataModel> feaList = feaDao.select(
               Stream.of(
                       paramMdl.getFil330SelectDel()
                       )
                   .map(str -> Integer.valueOf(str))
                   .collect(Collectors.toList())
               );
       if (feaList.size() == 0) {
           return List.of();
       }
       Fil330Biz fil330Biz = new Fil330Biz(reqMdl__, con__);
       int cabSid = feaList.stream()
                       .findAny()
                       .map(mdl -> mdl.getFcbSid())
                       .orElse(0);
       List<String> ret = fil330Biz.createFileDspList(
               feaList,
               cabSid
               )
               .stream()
               .map(mdl -> String.format(
                       "%s%s",
                       mdl.getPath(),
                       mdl.getBase().getFdrName()
                       )
                   )
               .collect(Collectors.toList());



       feaDao.delete(
               feaList.stream()
                   .map(mdl -> mdl.getFeaSid())
                   .collect(Collectors.toList())
               );

       CmnBinfDao binfDao = new CmnBinfDao(con__);
       CmnBinfModel binfMdl = new CmnBinfModel();
       binfMdl.setBinJkbn(GSConst.JTKBN_DELETE);
       binfMdl.setBinUpuser(reqMdl__.getSmodel().getUsrsid());
       binfMdl.setBinUpdate(new UDate());

       binfDao.updateJKbn(binfMdl,
               feaList.stream()
                   .map(mdl -> mdl.getBinSid())
                   .collect(Collectors.toList())
               );

       return ret;

   }


}
