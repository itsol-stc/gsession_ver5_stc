package jp.groupsession.v2.prj.model;

import java.io.Serializable;

/**
 * <p>PRJ_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PrjDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** PRJ_TODO_SIZE mapping */
    private long prjTodoSize__;

    /**
     * <p>Default Constructor
     */
    public PrjDatausedSumModel() {
    }

    /**
     * <p>get SUM_TYPE value
     * @return SUM_TYPE value
     */
    public int getSumType() {
        return sumType__;
    }

    /**
     * <p>set SUM_TYPE value
     * @param sumType SUM_TYPE value
     */
    public void setSumType(int sumType) {
        sumType__ = sumType;
    }

    /**
     * <p>get PRJ_TODO_SIZE value
     * @return PRJ_TODO_SIZE value
     */
    public long getPrjTodoSize() {
        return prjTodoSize__;
    }

    /**
     * <p>set PRJ_TODO_SIZE value
     * @param prjTodoSize PRJ_TODO_SIZE value
     */
    public void setPrjTodoSize(long prjTodoSize) {
        prjTodoSize__ = prjTodoSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(prjTodoSize__);
        return buf.toString();
    }

}
