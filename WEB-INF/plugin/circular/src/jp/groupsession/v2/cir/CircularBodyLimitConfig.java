package jp.groupsession.v2.cir;

import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cmn.biz.config.bodylimit.IBodyLimitConfig;

public class CircularBodyLimitConfig implements IBodyLimitConfig {

    @Override
    public int getMaxLength(String appRootPath) {
        return CirCommonBiz.getBodyLimitLength();
    }

}
