package jp.groupsession.v2.sch.restapi.groups.member;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.co.sjts.util.struts.RequestLocal;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;

public class SchGroupMemberGetExeCash {
    /** グループ情報キャッシュ*/
    private final Map<String, CmnGroupmModel> groupMap__ =
            new HashMap<String, CmnGroupmModel>();
    private SchGroupMemberGetExeCash() {

    }
    /**
     *
     * <br>[機  能] インスタンス取得
     * <br>[解  説] リクエストスレッド内でインスタンスはシングルトン
     * <br>[備  考]
     * @return インスタンス
     */
    public static SchGroupMemberGetExeCash getInstance() {
        if (RequestLocal.containsKey(SchGroupMemberGetExeCash.class)) {
            return RequestLocal.get(SchGroupMemberGetExeCash.class, SchGroupMemberGetExeCash.class);
        }
        SchGroupMemberGetExeCash ret = new SchGroupMemberGetExeCash();
        RequestLocal.put(SchGroupMemberGetExeCash.class, ret);
        return ret;
    }

    /**
     *
     * <br>[機  能] ターゲットグループ情報取得
     * <br>[解  説] 2回目以降はキャッシュ利用
     * <br>[備  考]
     * @param ctx コンテキスト
     * @param groupId グループID
     * @return グループ情報取得
     */
    public CmnGroupmModel getRequestGroup(RestApiContext ctx, String groupId) {

        CmnGroupmDao grpDao = new CmnGroupmDao(ctx.getCon());

        if (groupId == null) {
            return null;
        }

        if (groupMap__.containsKey(groupId)) {
            return groupMap__.get(groupId);
        }

        String[] grpIds =
                Stream.of(groupId)
                .toArray(String[]::new);
        try {
            groupMap__.putAll(
                    grpDao.selectGrpData(grpIds, GSConst.JTKBN_TOROKU)
                    .stream()
                    .collect(Collectors.toMap(
                            grp -> grp.getGrpId(),
                            grp -> grp))

            );
        } catch (SQLException e) {
            throw new RuntimeException("SQL 実行時例外", e);
        }
        return groupMap__.get(groupId);
    }


}
