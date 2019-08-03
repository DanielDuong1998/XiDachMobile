package com.platform;

public interface IPlatform {
    public void log(String str);

    public String GetDefaultLanguage();
    public boolean isVideoRewardReady();
    public void ShowVideoReward(OnVideoRewardClosed callback);
    public void ShowFullscreen();
    public void ShowBanner(boolean visible);

    public int GetConfigIntValue(String name, int defaultValue);
    public String GetConfigStringValue(String name, String defaultValue);


    public void TrackCustomEvent(String event);
    public void TrackLevelInfo(String event, int mode, int difficult, int level);
    public void TrackPlayerInfo(String event, int mode, int difficult, int level);
    public void TrackPlaneInfo(String event, int planeid, int level);
    public void TrackVideoReward(String type);
    public void TrackPlayerDead(String event, int mode, int difficult, int level, int parentModel, int shooterModel, boolean isBoss);

    /*public void TrackLevelStart(String mode, int difficult, int level);
    public void TrackLevelFailed(String mode, int difficult, int level);
    public void TrackLevelCompleted(String mode, int difficult, int level);
    public void TrackCustomEvent(String event);
    public void TrackPlayerDead(String mode, int difficult,  int level);
    public void TrackPlayerReborn(String mode, int level);
    public void TrackPlayerUpgrade(int planeid, int level);
    public void TrackPlayerUseItem(String mode, int level, String item);
    public void TrackPlayerEnchance(int enchanceid);
    ;*/



    public interface OnVideoRewardClosed{
        public void OnEvent(boolean success);
    }
}
