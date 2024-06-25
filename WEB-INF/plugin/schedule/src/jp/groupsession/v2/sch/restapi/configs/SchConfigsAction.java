package jp.groupsession.v2.sch.restapi.configs;

import java.awt.Color;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchColMsgDao;
import jp.groupsession.v2.sch.dao.SchInitPubDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchColMsgModel;
import jp.groupsession.v2.sch.model.SchInitPubModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.restapi.configs.SchConfigsResultModel.ColorInf;
import jp.groupsession.v2.sch.restapi.entities.PublicTargetGroupInf;
import jp.groupsession.v2.sch.restapi.entities.PublicTargetUserInf;
@Plugin(GSConst.PLUGINID_SCH)
public class SchConfigsAction extends AbstractRestApiAction {
    /** タイトル色固定データ */
    private static Color[] colorArray__ = {
        PdfUtil.FONT_COLOR_BLUE,
        PdfUtil.FONT_COLOR_RED,
        PdfUtil.FONT_COLOR_GREEN,
        PdfUtil.FONT_COLOR_ORANGE,
        PdfUtil.FONT_COLOR_BLACK,
        PdfUtil.FONT_COLOR_NAVY,
        PdfUtil.FONT_COLOR_MAROON,
        PdfUtil.FONT_COLOR_CYAN,
        PdfUtil.FONT_COLOR_GRAY,
        PdfUtil.FONT_COLOR_AQUA
    };

    @Get
    public void doGet(RestApiContext ctx, HttpServletResponse res) throws SQLException {

        SchConfigsResultModel result = new SchConfigsResultModel();

        //セッション情報を取得
        int sessionUsrSid = ctx.getRequestUserSid(); //セッションユーザSID
        RequestModel reqMdl = ctx.getRequestModel();
        Connection con = ctx.getCon();


        SchCommonBiz biz = new SchCommonBiz(reqMdl);
        SchPriConfModel pconf = biz.getSchPriConfModel(con, sessionUsrSid);
        //管理者設定を取得
        SchAdmConfModel adminConf = biz.getAdmConfModel(con);

        SchColMsgDao colDao = new SchColMsgDao(con);
        ArrayList<SchColMsgModel> colorList = colDao.select();



        //日間開始時刻
        result.setDayFromTime(pconf.getSccFrDate().getIntHour());
        //日間終了時刻
        result.setDayToTime(pconf.getSccToDate().getIntHour());

        //デフォルト開始時間
        UDate fr = pconf.getSccIniFrDate();
        if (adminConf.getSadIniTimeStype() == GSConstSchedule.SAD_INITIME_STYPE_ADM) {
            fr.setHour(adminConf.getSadIniFrH());
            fr.setMinute(adminConf.getSadIniFrM());
        }
        fr.setSecond(GSConstSchedule.DAY_START_SECOND);
        fr.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        result.setDefaultFromTime(fr);

        //デフォルト終了時間
        UDate to = pconf.getSccIniToDate();
        if (adminConf.getSadIniTimeStype() == GSConstSchedule.SAD_INITIME_STYPE_ADM) {
            to.setHour(adminConf.getSadIniToH());
            to.setMinute(adminConf.getSadIniToM());
        }
        to.setSecond(GSConstSchedule.DAY_START_SECOND);
        to.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        result.setDefaultToTime(to);


        //タイトルカラー
        int clrKbn = pconf.getSccIniFcolor();
        result.setDefaultColorType(clrKbn);
        //公開区分
        int pubKbn = biz.getInitPubAuth(con, pconf);  // pconf.getSccIniPublic();
        result.setDefaultPublicType(pubKbn);
        //編集区分
        int ediKbn = biz.getInitEditAuth(con, pconf); // pconf.getSccIniEdit();
        result.setDefaultEditType(ediKbn);
        //同時編集区分
        int samKbn = biz.getInitSameAuth(con, pconf); // pconf.getSccIniSame();
        result.setDefaultSameEditFlg(samKbn);
        //時間間隔
        int hourDivCount = biz.getDayScheduleHourMemoriCount(con);
        int hourMemCount = 60 / hourDivCount;
        result.setMinutesLengeNum(hourMemCount);

        //公開対象
        List<PublicTargetUserInf> targetUser = new ArrayList<>();
        result.setDefaultPublicTargetUserArray(targetUser);
        List<PublicTargetGroupInf> targetGroup = new ArrayList<>();
        result.setDefaultPublicTargetGroupArray(targetGroup);

        SchInitPubDao sipDao = new SchInitPubDao(con);
        List<SchInitPubModel> sipMdlList = new ArrayList<SchInitPubModel>();
        int publicStype = adminConf.getSadInitPublicStype();
        if (publicStype == GSConstSchedule.SAD_INIPUBLIC_STYPE_ADM
        && adminConf.getSadIniPublic() == GSConstSchedule.DSP_USRGRP) {
            sipMdlList = sipDao.select(0);
        } else if (publicStype == GSConstSchedule.SAD_INIPUBLIC_STYPE_USER
        && pconf.getSccIniPublic() == GSConstSchedule.DSP_USRGRP) {
            sipMdlList = sipDao.select(sessionUsrSid);
        }

        //ユーザ情報Map
        Map<Integer, CmnUserModel> userMap = new HashMap<Integer, CmnUserModel>();
        userMap.putAll(
                new UserSearchDao(con)
                .getUsersDataList(
                        sipMdlList.stream()
                            .filter(sipMdl -> sipMdl.getSipType() != GSConstSchedule.USER_KBN_GROUP)
                            .map(sipMdl -> sipMdl.getSipPsid())
                            .collect(Collectors.toSet())
                        )
                .stream()
                .collect(Collectors.toMap(
                        usr -> usr.getUsrSid(),
                        usr -> usr))
                );

        //グループ情報Map
        Map<Integer, CmnGroupmModel> groupMap = new HashMap<Integer, CmnGroupmModel>();
        int[] grpSids = sipMdlList.stream()
            .filter(sipMdl -> sipMdl.getSipType() == GSConstSchedule.USER_KBN_GROUP)
            .mapToInt(sipMdl -> sipMdl.getSipPsid())
            .toArray();
        if (grpSids.length > 0) {
            groupMap.putAll(
                    new GroupDao(con)
                    .getGroups(grpSids)
                    .stream()
                    .collect(Collectors.toMap(
                            grp -> grp.getGrpSid(),
                            grp -> grp))
                    );
        }
        for (SchInitPubModel sipMdl : sipMdlList) {
            if (sipMdl.getSipType() == GSConstSchedule.USER_KBN_GROUP
                    && groupMap.containsKey(sipMdl.getSipPsid())) {
                targetGroup.add(
                        new PublicTargetGroupInf(
                                groupMap.get(sipMdl.getSipPsid()
                                        )
                                )
                        );
                continue;
            }
            if (userMap.containsKey(sipMdl.getSipPsid())) {
                targetUser.add(
                        new PublicTargetUserInf(
                                userMap.get(sipMdl.getSipPsid()
                                        )
                                )
                        );
            }
        }

        //カラー区分
        boolean defaultColor = !(colorList != null && colorList.size() > 0);
        List<ColorInf> colorResultList = new ArrayList<>();
        result.setColorArray(colorResultList);
        // タイトル色一覧
        for (int i = 0; i < colorArray__.length; i++) {
            int     colNum   = (colorArray__[i].getRGB()) & 0xffffff;
            String  colRgb   = String.format("#%06x", Integer.valueOf(colNum));
            int     colSid   = i + 1;
            String  colTitle = "";
            boolean isUsed   = false;

            // 一覧からデータ抽出
            if (defaultColor) {
                // データがない場合 → デフォルト仕様(5色対応)
                isUsed = (i < 5);
            } else {
                for (int j = 0; j < colorList.size(); j++) {
                    SchColMsgModel col = colorList.get(j);
                    if (col.getScmId() == colSid) {
                        colTitle = col.getScmMsg();
                        isUsed   = true;
                        colorList.remove(j); // リストから除外
                        break;
                    }
                }
            }
            ColorInf cmdl = new ColorInf();
            colorResultList.add(cmdl);
            cmdl.setType(colSid);
            cmdl.setCommentText(colTitle);
            cmdl.setValueText(colRgb);
            if (isUsed) {
                cmdl.setUseFlg(1);
            }
        }
        //リマインダー設定
        result.setDefaultRemindTimingType(pconf.getSccReminder());

        RestApiResponseWriter.builder(res, ctx)
        .addResult(result)
        .build().execute();
    }
}
