package jp.groupsession.v2.ptl;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.biz.config.bodylimit.IBodyLimitConfig;
import jp.groupsession.v2.man.GSConstPortal;

public class PortletBodyLimitConfig implements IBodyLimitConfig {

    @Override
    public int getMaxLength(String appRootPath) {
        String portletMaxLength = ConfigBundle.getValue(GSConstPortal.PTLCONF_BODY_LIMIT);
        int intPortletMaxLength = NullDefault.getInt(
            portletMaxLength,
            GSConstPortal.PORTLET_CONTENT_LIMIT_DEFAULT);

        if (intPortletMaxLength < 0) {
            intPortletMaxLength = GSConstPortal.PORTLET_CONTENT_LIMIT_DEFAULT;
        }

        return intPortletMaxLength;
    }

}
