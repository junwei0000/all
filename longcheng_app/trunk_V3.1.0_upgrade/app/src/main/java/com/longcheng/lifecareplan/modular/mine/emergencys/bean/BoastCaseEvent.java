package com.longcheng.lifecareplan.modular.mine.emergencys.bean;

import java.util.List;

public class BoastCaseEvent {
    private List<LoveBroadcasts> loveBroadcasts;

    public BoastCaseEvent(List<LoveBroadcasts> loveBroadcasts) {
        this.loveBroadcasts = loveBroadcasts;

    }

    public List<LoveBroadcasts> getEvent() {
        return loveBroadcasts;
    }

    public String getEvent2() {
        return "getEvent2";
    }

}
