package com.longcheng.lifecareplan.autolayout;

import android.view.View;

import com.longcheng.lifecareplan.autolayout.attr.AutoAttr;

import java.util.ArrayList;
import java.util.List;


public class AutoLayoutInfo {
    private List<AutoAttr> autoAttrs = new ArrayList<AutoAttr>();

    public void addAttr(AutoAttr autoAttr) {
        autoAttrs.add(autoAttr);
    }


    public void fillAttrs(View view) {
        for (AutoAttr autoAttr : autoAttrs) {
            autoAttr.apply(view);
        }
    }

    @Override
    public String toString() {
        return "AutoLayoutInfo{" +
                "autoAttrs=" + autoAttrs +
                '}';
    }
}