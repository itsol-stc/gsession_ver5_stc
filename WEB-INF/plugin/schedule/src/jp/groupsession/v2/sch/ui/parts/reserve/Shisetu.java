package jp.groupsession.v2.sch.ui.parts.reserve;

import java.util.List;

import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.IChildType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumChildType;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;

public class Shisetu implements IChild {
    /** データ*/
    private RsvSisDataModel mdl__;

    /**
     *
     * コンストラクタ
     * @param mdl
     */
    public Shisetu(RsvSisDataModel mdl) {
        mdl__ = mdl;
    }


    @Override
    public String getName() {
        return mdl__.getRsdName();
    }

    @Override
    public String getValue() {
        return String.valueOf(mdl__.getRsdSid());
    }

    @Override
    public IChildType getType() {
        return EnumChildType.OTHER;
    }

    @Override
    public int getTeisiFlg() {
        return 0;
    }

    @Override
    public int getJkbn() {
        return 0;
    }

    @Override
    public List<String> getLabelList() {
        return List.of();
    }

    @Override
    public void setLabelList(List<String> userLabel) {

    }


}
