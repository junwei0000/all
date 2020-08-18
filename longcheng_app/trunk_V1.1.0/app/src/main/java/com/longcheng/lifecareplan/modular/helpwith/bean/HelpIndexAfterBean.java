package com.longcheng.lifecareplan.modular.helpwith.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者：jun on
 * 时间：2018/8/21 13:12
 * 意图：返回对象
 */

public class HelpIndexAfterBean {
    @SerializedName("myBlessHelpCount")
    private String myBlessHelpCount;
    @SerializedName("blessMeHelpCount")
    private String blessMeHelpCount;

    @SerializedName("solarTermsEnsImg")
    private List<String> solarTermsEnsImg;
    @SerializedName("zangfus")
    private List<String> zangfus;
    @SerializedName("user")
    private HelpIndexItemBean user;

    @SerializedName("automationHelpUrl")
    private String automationHelpUrl;
    @SerializedName("myDedicationUrl")
    private String myDedicationUrl;
    @SerializedName("myGratitudeUrl")
    private String myGratitudeUrl;
    @SerializedName("lifeUrl")
    private String lifeUrl;
    @SerializedName("lifeUrlWorld")
    private String lifeUrlWorld;
    @SerializedName("lifeBasicApplyUrl")
    private String lifeBasicApplyUrl;
    @SerializedName("isVolunteer")
    private int isVolunteer;
    @SerializedName("become_volunteer_url")
    private String become_volunteer_url;

    public String getBecome_volunteer_url() {
        return become_volunteer_url;
    }

    public void setBecome_volunteer_url(String become_volunteer_url) {
        this.become_volunteer_url = become_volunteer_url;
    }

    public int getIsVolunteer() {
        return isVolunteer;
    }

    public void setIsVolunteer(int isVolunteer) {
        this.isVolunteer = isVolunteer;
    }

    public String getLifeBasicApplyUrl() {
        return lifeBasicApplyUrl;
    }

    public void setLifeBasicApplyUrl(String lifeBasicApplyUrl) {
        this.lifeBasicApplyUrl = lifeBasicApplyUrl;
    }

    public String getLifeUrlWorld() {
        return lifeUrlWorld;
    }

    public void setLifeUrlWorld(String lifeUrlWorld) {
        this.lifeUrlWorld = lifeUrlWorld;
    }

    public String getLifeUrl() {
        return lifeUrl;
    }

    public void setLifeUrl(String lifeUrl) {
        this.lifeUrl = lifeUrl;
    }

    public String getAutomationHelpUrl() {
        return automationHelpUrl;
    }

    public void setAutomationHelpUrl(String automationHelpUrl) {
        this.automationHelpUrl = automationHelpUrl;
    }

    public String getMyDedicationUrl() {
        return myDedicationUrl;
    }

    public void setMyDedicationUrl(String myDedicationUrl) {
        this.myDedicationUrl = myDedicationUrl;
    }

    public String getMyGratitudeUrl() {
        return myGratitudeUrl;
    }

    public void setMyGratitudeUrl(String myGratitudeUrl) {
        this.myGratitudeUrl = myGratitudeUrl;
    }


    public String getMyBlessHelpCount() {
        return myBlessHelpCount;
    }

    public void setMyBlessHelpCount(String myBlessHelpCount) {
        this.myBlessHelpCount = myBlessHelpCount;
    }

    public String getBlessMeHelpCount() {
        return blessMeHelpCount;
    }

    public void setBlessMeHelpCount(String blessMeHelpCount) {
        this.blessMeHelpCount = blessMeHelpCount;
    }

    public List<String> getSolarTermsEnsImg() {
        return solarTermsEnsImg;
    }

    public void setSolarTermsEnsImg(List<String> solarTermsEnsImg) {
        this.solarTermsEnsImg = solarTermsEnsImg;
    }

    public List<String> getZangfus() {
        return zangfus;
    }

    public void setZangfus(List<String> zangfus) {
        this.zangfus = zangfus;
    }

    public HelpIndexItemBean getUser() {
        return user;
    }

    public void setUser(HelpIndexItemBean user) {
        this.user = user;
    }
}
