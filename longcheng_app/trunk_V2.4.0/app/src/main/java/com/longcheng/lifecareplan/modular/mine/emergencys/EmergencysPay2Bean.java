package com.longcheng.lifecareplan.modular.mine.emergencys;

import java.io.Serializable;

public class EmergencysPay2Bean implements Serializable {
    public int help_need_id;

    public int getHelp_need_id() {
        return help_need_id;
    }

    public void setHelp_need_id(int help_need_id) {
        this.help_need_id = help_need_id;
    }

    @Override
    public String toString() {
        return "EmergencysPay2Bean{" +
                "help_need_id=" + help_need_id +
                '}';
    }
}
