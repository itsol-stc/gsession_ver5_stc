package jp.groupsession.v2.enq;

import java.util.Comparator;

import jp.groupsession.v2.enq.model.EnqTypeListModel;

/**
 * <br>[機  能] アンケート種類一覧を表示順でソートするクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqTypeListComparatorImpl implements Comparator<EnqTypeListModel> {

    @Override
    public int compare(EnqTypeListModel arg0, EnqTypeListModel arg1) {
        // TODO 自動生成されたメソッド・スタブ
        int dspSeq0 = arg0.getEtpDspSeq();
        int dspSeq1 = arg1.getEtpDspSeq();

        if (dspSeq0 > dspSeq1) {
            return 1;
        } else if (dspSeq0 == dspSeq1) {
            return 0;
        } else {
            return -1;
        }
    }

}
