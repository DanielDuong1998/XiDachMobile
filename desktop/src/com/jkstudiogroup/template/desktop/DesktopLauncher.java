package com.jkstudiogroup.template.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.platform.IPlatform;
import com.ss.GMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new GMain(new IPlatform() {
			@Override
			public void log(String str) {

			}

			@Override
			public String GetDefaultLanguage() {
				return null;
			}

			@Override
			public boolean isVideoRewardReady() {
				return false;
			}

			@Override
			public void ShowVideoReward(OnVideoRewardClosed callback) {

			}

			@Override
			public void ShowFullscreen() {

			}

			@Override
			public void ShowBanner(boolean visible) {

			}

			@Override
			public int GetConfigIntValue(String name, int defaultValue) {
				return 0;
			}

			@Override
			public String GetConfigStringValue(String name, String defaultValue) {
				return null;
			}

			@Override
			public void TrackCustomEvent(String event) {

			}

			@Override
			public void TrackLevelInfo(String event, int mode, int difficult, int level) {

			}

			@Override
			public void TrackPlayerInfo(String event, int mode, int difficult, int level) {

			}

			@Override
			public void TrackPlaneInfo(String event, int planeid, int level) {

			}

			@Override
			public void TrackVideoReward(String type) {

			}

			@Override
			public void TrackPlayerDead(String event, int mode, int difficult, int level, int parentModel, int shooterModel, boolean isBoss) {

			}
		}), config);
	}
}
