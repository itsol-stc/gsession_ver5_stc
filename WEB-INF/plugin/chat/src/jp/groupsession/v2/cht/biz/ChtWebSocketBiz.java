package jp.groupsession.v2.cht.biz;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.dao.ChtGroupUserDao;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.websocket.IWebSocketSender;
import jp.groupsession.v2.cmn.websocket.WebSocketSender;

/**
 *
 * <br>[機  能] チャットプラグインで使用するWebSocket通信ロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ChtWebSocketBiz {

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>コンストラクタ
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * */
    public ChtWebSocketBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }
    /**
     *
     * <br>[機  能] List<Integer>をint[]に変換
     * <br>[解  説]
     * <br>[備  考]
     * @param memberList ユーザSIDリスト
     * @return int配列
     */
    private int[] __toIntArr(List<Integer> memberList) {

        int[] msgTo = new int[memberList.size()];
        for (int i = 0; i < memberList.size(); i++) {
            msgTo[i] = memberList.get(i);
        }
        return msgTo;
    }


    /**
     *
     * <br>[機  能] チャットグループの表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonData JSONデータ
     * @param members 所属ユーザ
     * @param oldMembers 編集前の所属ユーザ
     * @throws Exception Exception
     */
    public void editChatGroup(
            JSONObject jsonData,
            String[] members,
            String[] oldMembers)
            throws Exception {

        if (members == null) {
            return;
        }

        // gsドメイン
        String domain = reqMdl__.getDomain();
        // JSONデータを文字列へ変換
        String message = jsonData.toString();

        int procMode = jsonData.getInt("chatGroupProcMode");
        // 送信先メンバー
        List<Integer> memberList = new ArrayList<Integer>();
        for (String str : members) {
            int sid = NullDefault.getInt(str, 0);
            if (sid <= 0) {
                continue;
            }
            memberList.add(sid);
        }

        IWebSocketSender sender =  WebSocketSender.getInstance();

        // 新規登録
        if (procMode == GSConstChat.CHAT_MODE_ADD) {
            // プラグイン使用確認
            List<Integer> permitMemberList = permitPluginUser(memberList, domain);

            sender.sendText(domain, __toIntArr(permitMemberList), message);

        // 編集・削除
        } else if (procMode == GSConstChat.CHAT_MODE_EDIT) {
            // プラグイン使用確認
            List<Integer> permitMemberList = permitPluginUser(memberList, domain);

            sender.sendText(domain, __toIntArr(permitMemberList), message);

            // メンバーから外れたユーザ
            List<Integer> oldMemberList = new ArrayList<Integer>();

            for (String str : oldMembers) {
                int sid = NullDefault.getInt(str, 0);
                if (sid <= 0) {
                    continue;
                }
                oldMemberList.add(sid);
            }
            oldMemberList.removeAll(permitMemberList);

            JSONObject forRemove = jsonData;
            forRemove.put("remove", true);
            String removeMsg = forRemove.toString();


            List<Integer> oldPermitMemberList = permitPluginUser(oldMemberList, domain);

            sender.sendText(domain, __toIntArr(oldPermitMemberList), removeMsg);

        }

    }

    /**
     * <p>プラグインを使用できるユーザ一覧を取得
     * @param targetMember メンバー一覧
     * @param domain ドメイン名
     * @throws SQLException SQL実行例外
     * @throws IOException 入出力実行例外
     * @return ユーザ一覧
      */
    public List<Integer> permitPluginUser(
            List<Integer> targetMember, String domain)
            throws SQLException, IOException {

        if (targetMember.size() <= 0) {
            return new ArrayList<Integer>();
        }

        CommonBiz biz = new CommonBiz();

        List<Integer> member = targetMember;
        member = biz.getCanUsePluginUser(con__, GSConstChat.PLUGIN_ID_CHAT, member);

        List<Integer> ret = member;

        return ret;
    }

    /**
     * <p>WebSocketへデータを送信します
     * @param jsonData 送信先や送信内容などを含むデータ
     * @throws Exception Exception
     * */
    public void sendToWebSocket(JSONObject jsonData)
            throws Exception {
        // メッセージの種類
        String type = jsonData.getString("type");
        // gsドメイン
        String domain = reqMdl__.getDomain();
        // チャットメッセージ送信
        if (type.equals("message")) {
            // メッセージ送信先区分( 1:ユーザ 2:グループ)
            int selectKbn = jsonData.getInt("selectKbn");
            // メッセージ送信先のSID
            int selectSid = Integer.parseInt(jsonData.getString("selectSid"));
            // メッセージ送信者のユーザSID
            int senderSid = Integer.parseInt(jsonData.getString("senderSid"));

            String message = jsonData.toString();
            List<Integer> member = new ArrayList<Integer>();

            // データの送信
            if (selectKbn == GSConstChat.CHAT_KBN_GROUP) {

                // グループチャット
                ChtGroupUserDao cguDao = new ChtGroupUserDao(con__);
                member = cguDao.membersOfChatGroup(selectSid);

            } else if (selectKbn == GSConstChat.CHAT_KBN_USER) {

                // 送信者自身
                member.add(senderSid);
                if (senderSid != selectSid) {
                    // 送信先
                    member.add(selectSid);
                }
            }

            // プラグイン使用確認
            member =  permitPluginUser(member, domain);

            int[] msgTo = new int[member.size()];
            for (int i = 0; i < member.size(); i++) {
                msgTo[i] = member.get(i);
            }

            IWebSocketSender sender =  WebSocketSender.getInstance();
            sender.sendText(domain, msgTo, message);

        }
    }

}

