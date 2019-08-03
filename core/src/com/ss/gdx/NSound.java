package com.ss.gdx;

public class NSound {
    public static native void loadSound(String name )/*-{
            $wnd.loadSound(name);
        }-*/;


    public static native void playSound(String name)/*-{
            $wnd.playSound(name);
        }-*/;

    public static native void pauseSound(String name)/*-{
            $wnd.pauseSound(name);
        }-*/;

    public static native void resumeSound(String name)/*-{
            $wnd.resumeSound(name);
        }-*/;


    public static native void playLoopSound(String name)/*-{
            $wnd.playLoopSound(name);
        }-*/;

    public static native void stopSound(String name)/*-{
            $wnd.stopSound(name);
        }-*/;
    public static native void pauseAllSound()/*-{
            $wnd.pauseAllSound(name);
        }-*/;
    public static native void stopAllSound()/*-{
            $wnd.stopAllSound(name);
        }-*/;
}
