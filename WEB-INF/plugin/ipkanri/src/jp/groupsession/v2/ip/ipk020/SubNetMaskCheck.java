package jp.groupsession.v2.ip.ipk020;

import java.util.ArrayList;
import java.util.StringTokenizer;

import jp.groupsession.v2.ip.IpkConst;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] サブネットマスクデータを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class SubNetMaskCheck {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SubNetMaskCheck.class);

    /**
     * <br>[機  能] サブネットマスクの2進数表現を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param subnetmask サブネットマスク
     * @return サブネットマスクの2進数表現
     */
    public static String toBit(String subnetmask) {
        StringTokenizer sbST = new StringTokenizer(subnetmask, ".");
        String termBit = "";

        while (sbST.hasMoreTokens()) {
            termBit += __toBit(sbST.nextToken());
        }
        return termBit;
    }

    /**
     * <br>[機  能] サブネットマスクのbit換算値を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param subnetmask サブネットマスク
     * @return サブネットマスクのbit換算値
     */
    public static long toBitNumber(String subnetmask) {
        return __getBitNumber(toBit(subnetmask));
    }

    /**
     * <br>[機  能] サブネットマスクが不正かどうかを判定
     * <br>[解  説]
     * <br>[備  考]
     * @param subnetmask : サブネットマスク (例 255.255.255.0)
     * @return true boolean
     */
    public static boolean checkSubNetMask(String subnetmask) {

        String[] arySubNetMask = new String[4];
        boolean errorFlg = false;
        arySubNetMask = subnetmask.replaceAll("\\.", ",").split(",");
        if (arySubNetMask.length < 4) {
            return false;
        }
        int[] mask = IpkConst.SUBNETMASK_OK_NUM;
        String strSubNetMask = "";

        for (int i = 0; i < 4; i++) {
            int subNetMask = Integer.parseInt(arySubNetMask[i]);
            errorFlg = false;
            for (int maskcount = 0; maskcount < mask.length; maskcount++) {
                if (mask[maskcount] == subNetMask) {
                    errorFlg = true;
                    break;
                }
            }
            if (!errorFlg) {
                return false;
            }
        //サブネットマスクを「1111111100000000」の形にする。
            strSubNetMask += __toBit(subNetMask);
        }
        log__.debug("===subNetMask=== " + strSubNetMask);
        long ms = __getBitNumber(strSubNetMask);
        if (ms < 2 || ms >= 31) {
            return false;
        }
        if (__bitContinue(strSubNetMask) != true) {
            return false;
        }
        return true;
    }

    /**
     * <br>[機  能] 10進数を2進数へ変換
     * <br>[解  説]
     * <br>[備  考]
     * @param decimal : 10進数
     * @return 2進数
     */
    public static String __getBCD(String decimal) {
        String strBinary = "";

        long lgDecimal = Long.parseLong(decimal);
        if (lgDecimal == 0) {
            return "0";
        }
        while (lgDecimal > 0) {
            strBinary = Long.toString(lgDecimal % 2) + strBinary;
            lgDecimal = lgDecimal / 2;
        }
        return strBinary;
    }

    /**
     * <br>[機  能] 渡された値を8桁の2進数に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param dec : 変換する10進数
     * @return 8桁の2進数
     */
    public static String __toBit(int dec) {
        return __toBit(Integer.toString(dec));
    }

    /**
     * <br>[機  能] 渡された値を8桁の2進数に変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param dec : 変換する10進数
     * @return 8桁の2進数
     */
    public static String __toBit(String dec) {

        String decBit = "";
        // decを2進数に変更
        decBit = __getBCD(dec);
        // decBitを8桁の2進数に変更
        int len = 8 - decBit.length();
        for (int i = 0; i < len; i++) {
            decBit = "0" + decBit;
        }
        return decBit;
    }

    /**
     * <br>[機  能] サブネットマスクの2進数表現からbit数を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param binary : サブネットマスクの2進数表現
     * @return decumal long
     */
    public static long __getBitNumber(String binary) {

        long decimal = 0;
        if (binary == null) {
            return decimal;
        }
        for (int i = 0; i < binary.length(); i++) {
            if (binary.substring(i, i + 1).equals("0")) {
                decimal = i;
                break;
            }
            if (i + 1 == binary.length()) {
                decimal = binary.length();
            }
        }
        return decimal;
    }

    /**
     * <br>[機  能] サブネットマスクが連続するビット列であるか
     * <br>[解  説]（例）255.255.255.0 ture,  255.255.0.255 false
     * <br>[備  考]
     * @param binary : サブネットマスクの2進数表現
     * @return bitflg boolean
     */
    public static boolean __bitContinue(String binary) {

        boolean bitflg = true;
        int i = 0;
        int tfflg = 0;
        if (binary == null) {
            return false;
        }
        //１の間ループ
        for (i = 0; i < binary.length(); i++) {
            if (binary.substring(i, i + 1).equals("0")) {
                tfflg = 1;
            }

            //残り０であるならば正常、途中で１があるならば正しくない
            if (binary.substring(i, i + 1).equals("1") && tfflg == 1) {
                bitflg = false;
                break;
            }

        }
        return bitflg;
    }

    /**
     * <br>[機  能] ネットワークアドレスが既に存在しているかを判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param networkaddress ネットワークアドレス
     * @param netadList ネットワークアドレスリスト
     * @param beforeNetad 変更前のネットワークアドレス
     * @return true 存在していない場合 true、存在していうネットワークアドレス場合 false
     */
    public static boolean networkExistCheck(
            String networkaddress, ArrayList<String> netadList, String beforeNetad) {

        //ネットワークアドレスが存在しているか判定
        for (String netad : netadList) {
            if (netad.equals(networkaddress) && !networkaddress.equals(beforeNetad)) {
                return false;
            }
        }
    return true;
    }
}