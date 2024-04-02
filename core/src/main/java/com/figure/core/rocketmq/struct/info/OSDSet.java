package com.figure.core.rocketmq.struct.info;

import com.figure.core.model.transcode.TranscodeRuleInfo;
import lombok.Data;

@Data
public class OSDSet {

    private Integer enableOSD;

    private Integer[] textPosition = new Integer[2];

    private Integer textFontSize;

    private Integer showFrontStation;

    private Integer showLogicPosition;

    private Integer showSignalType;

    private Integer showChannelName;

    private Integer[] clockPosition = new Integer[2];

    private Integer clockFontSize;

    private Integer showClock;

    public OSDSet() {
    }

    public OSDSet(TranscodeRuleInfo transcodeRuleInfo) {
        this.enableOSD = transcodeRuleInfo.getIsOSD();

        this.textPosition = new Integer[]{transcodeRuleInfo.getTextXPosition(), transcodeRuleInfo.getTextYPosition()};
        this.textFontSize = transcodeRuleInfo.getTextFontSize();

        this.showFrontStation = transcodeRuleInfo.getShowFrontName();
        this.showLogicPosition = transcodeRuleInfo.getShowMonitorPoint();
        this.showSignalType = transcodeRuleInfo.getShowSignal();
        this.showChannelName = transcodeRuleInfo.getShowChannelName();

        this.clockPosition = new Integer[]{transcodeRuleInfo.getTimeYPosition(), transcodeRuleInfo.getTimeXPosition()};
        this.clockFontSize = transcodeRuleInfo.getTimeFontSize();
        this.showClock = transcodeRuleInfo.getShowTimeInfo();

    }
}
