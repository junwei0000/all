package com.longcheng.lifecareplan.modular.mine.emergencys.bean;

import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.CurrentHelpNeed;

public class EmergencyListUserBean extends ResponseBean {

    public int isCanApply;
    public CurrentHelpNeed currentHelpNeed;

    public int getIsCanApply() {
        return isCanApply;
    }

    public void setIsCanApply(int isCanApply) {
        this.isCanApply = isCanApply;
    }


    public CurrentHelpNeed getCurrentHelpNeed() {
        return currentHelpNeed;
    }

    public void setCurrentHelpNeed(CurrentHelpNeed currentHelpNeed) {
        this.currentHelpNeed = currentHelpNeed;
    }

    @Override
    public String toString() {
        return "EmergencyListUserBean{" +
                "isCanApply=" + isCanApply +
                ", currentHelpNeed=" + currentHelpNeed +
                '}';
    }
}
