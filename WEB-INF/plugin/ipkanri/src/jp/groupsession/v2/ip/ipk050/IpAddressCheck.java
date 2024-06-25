package jp.groupsession.v2.ip.ipk050;

import jp.groupsession.v2.ip.ipk020.SubNetMaskCheck;

/**
 * <br>[機  能] IP管理 IPアドレス情報のチェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpAddressCheck {
    /**
     * <br>[機  能] IPアドレスのどこまでがネットワークアドレス部か判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param subNetMask : サブネットマスク (例 192.168.1.87)
     * @return num int ネットワークアドレス部がどこまでか
     */
    public static int checkSubNet(String subNetMask) {
        int num = 0;
        //サブネットマスクを配列に置き換える。
        String[] sbnAry = subNetMask.replaceAll("\\.", ",").split(",");
        for (int i = 0; i < sbnAry.length; i++) {
            if (!sbnAry[i].equals("255")) {
                num = i + 1;
                break;
            }
        }
        return num;
    }

    /**
     * <br>[機  能] IPアドレスが範囲内かどうかを判定
     * <br>[解  説]
     * <br>[備  考]
     * @param ipAddress : チェック対象のIPアドレス
     * @param networkAddress : ネットワークアドレス(例 192.168.1.1)
     * @param subnetmask : サブネットマスク(例 255.255.255.0)
     * @return IPアドレスが範囲内だった場合 true,範囲外だった場合 false
     */
    public static boolean ipadCheck(String ipAddress,
                                   String networkAddress,
                                   String subnetmask) {

        //サブネットマスクの 2進数での桁数
        long ms = SubNetMaskCheck.toBitNumber(subnetmask);
        //2進数に変換
        String[] netadAry = networkAddress.replaceAll("\\.", ",").split(",");
        String netadd = "";
        //2進数に変換
        for (String net : netadAry) {
            netadd += SubNetMaskCheck.toBit(net);
        }

        String net = "";
        for (int i = 0; i < ms; i++) {
            net = net + netadd.substring(i, i + 1);
        }
        String bro = new String(net);
        for (int i = 0; i < 32 - ms; i++) {
            //マスクより下位ビットを0にする
            net = net + "0";
            //マスクより下位ビットを1にする
            bro = bro + "1";
        }
        int[] netAryUnder = new int[4];
        int[] netAry = new int[4];
        int[] broAry = new int[4];
        for (int i = 0, j = 0; i < 4; i++, j = j + 8) {
            netAryUnder[i] = Integer.parseInt(net.substring(j, j + 8), 2);
            netAry[i] = Integer.parseInt(netadd.substring(j, j + 8), 2);
            broAry[i] = Integer.parseInt(bro.substring(j, j + 8), 2);
        }
        String[] ipadAry = ipAddress.replaceAll("\\.", ",").split(",");
        for (int i = 0; i < 4; i++) {
            int ip = Integer.parseInt(ipadAry[i]);
            if (ip < netAry[i] || ip > broAry[i]) {
                return false;
            }
        }
        String netWorkAddress = netAry[0] + "." + netAry[1] + "." + netAry[2] + "." + netAry[3];
        String broadCast = broAry[0] + "." + broAry[1] + "." + broAry[2] + "." + broAry[3];
        //ネットワークアドレスは入力不可
        if (ipAddress.equals(netWorkAddress)) {
            return false;
        }
        //ブロードキャストアドレスは入力不可
        if (ipAddress.equals(broadCast)) {
            return false;
        }
        return true;
    }

    /**
     * <br>[機  能] 同じIPアドレスがないかどうかを判定
     * <br>[解  説]
     * <br>[備  考]
     * @param ipadCount 入力されたIPアドレスと同じIPアドレスの個数
     * @return IPアドレスが登録済みだった場合 false
     */
    public static boolean ipadExistCheck(
            int ipadCount) {
        if (ipadCount == 1) {
            return false;
        } else if (ipadCount >= 2) {
            return false;
        }
        return true;
    }
}