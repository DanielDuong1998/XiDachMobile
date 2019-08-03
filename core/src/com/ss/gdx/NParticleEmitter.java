package com.ss.gdx;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.ss.GMain;

import java.io.BufferedReader;
import java.io.IOException;

public class NParticleEmitter extends ParticleEmitter {
    private boolean scaleLink;
    private boolean fixFrame = false;
    private String imagePath;
    private int activeCount2;
    private boolean[] active2;
    private float accumulator2;
    private float delay2, delayTimer2;
    private boolean firstUpdate2;
    private boolean allowCompletion2;
    public static float fixDeltaTime = 0.016666668f;


    public NParticleEmitter (BufferedReader reader) throws IOException {
        super(reader);

    }

    public NParticleEmitter(NParticleEmitter particleEmitter) {
        super(particleEmitter);
        this.scaleLink = particleEmitter.scaleLink;

    }

    public void setImagePath(String string) {
        this.imagePath = string;
    }

    public String getImagePath() {
        return this.imagePath;
    }


    public void load (BufferedReader reader) throws IOException{
        this.getYScale().setAlwaysActive(true);

        String line = "";
        String string2 = "";
        try {
            line = reader.readLine();
            string2 = NParticleEmitter.readVesion((String) line);
        }
        catch(Exception e){
            GMain.platform.log("Error loading emitter " + line + " " + e.getMessage());
            string2 = null;
            line = "error1";
        }


        if (string2 == null) {
            this.setName(line);
            load_v5(reader);
            if (this.scaleLink) {
                this.getYScale().setAlwaysActive(false);
                this.getYScale().setActive(false);
            }
            return;
        }
        if (!string2.equals("v6")) return;
        this.load_v6(reader);


        if (this.scaleLink) {
            this.getYScale().setAlwaysActive(false);
            this.getYScale().setActive(false);
        }


    }

    void load_v5(BufferedReader bufferedReader) throws IOException {
        String test = "";
        test = bufferedReader.readLine();
        this.getDelay().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getDuration().load(bufferedReader);
        test = bufferedReader.readLine();
        this.setMinParticleCount(readInt((BufferedReader)bufferedReader, (String)"minParticleCount"));
        this.setMaxParticleCount(readInt((BufferedReader)bufferedReader, (String)"maxParticleCount"));
        test = bufferedReader.readLine();
        this.getEmission().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getLife().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getLifeOffset().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getXOffsetValue().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getYOffsetValue().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getSpawnShape().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getSpawnWidth().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getSpawnHeight().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getXScale().load(bufferedReader);
        this.getYScale().load(getXScale());
        test = bufferedReader.readLine();
        this.getVelocity().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getAngle().load(bufferedReader);
        bufferedReader.readLine();
        this.getRotation().load(bufferedReader);
        bufferedReader.readLine();
        this.getWind().load(bufferedReader);
        bufferedReader.readLine();
        this.getGravity().load(bufferedReader);
        bufferedReader.readLine();
        this.getTint().load(bufferedReader);
        bufferedReader.readLine();
        this.getTransparency().load(bufferedReader);
        bufferedReader.readLine();
        this.setAttached(readBoolean((BufferedReader)bufferedReader, (String)"attached"));
        this.setContinuous(readBoolean((BufferedReader)bufferedReader, (String)"continuous"));
        this.setAligned(readBoolean((BufferedReader)bufferedReader, (String)"aligned"));
        this.setAdditive(readBoolean((BufferedReader)bufferedReader, (String)"additive"));
        readBoolean((BufferedReader)bufferedReader, (String)"behind");
       // this.scaleLink = ParticleEmitter.readBoolean((BufferedReader)bufferedReader, (String)"behind");
        this.scaleLink = true;
    }

    void load_v6(BufferedReader bufferedReader) throws IOException {
        String name = "";
        try {
            name = readString((BufferedReader) bufferedReader, (String) "name");
        }
        catch(Exception e){
            GMain.platform.log("load_v6 name failed " + e.getMessage());
            name = "error";
        }
        this.setName(name);
        String test = "";
        test = bufferedReader.readLine();
        this.getDelay().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getDuration().load(bufferedReader);
        test = bufferedReader.readLine();
        this.setMinParticleCount(readInt((BufferedReader)bufferedReader, (String)"minParticleCount"));
        this.setMaxParticleCount(readInt((BufferedReader)bufferedReader, (String)"maxParticleCount"));
        test = bufferedReader.readLine();
        this.getEmission().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getLife().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getLifeOffset().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getXOffsetValue().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getYOffsetValue().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getSpawnShape().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getSpawnWidth().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getSpawnHeight().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getXScale().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getYScale().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getVelocity().load(bufferedReader);
        test = bufferedReader.readLine();
        this.getAngle().load(bufferedReader);
        bufferedReader.readLine();
        this.getRotation().load(bufferedReader);
        bufferedReader.readLine();
        this.getWind().load(bufferedReader);
        bufferedReader.readLine();
        this.getGravity().load(bufferedReader);
        bufferedReader.readLine();
        this.getTint().load(bufferedReader);
        bufferedReader.readLine();
        this.getTransparency().load(bufferedReader);
        bufferedReader.readLine();
        this.setAttached(readBoolean((BufferedReader)bufferedReader, (String)"attached"));
        this.setContinuous(readBoolean((BufferedReader)bufferedReader, (String)"continuous"));
        this.setAligned(readBoolean((BufferedReader)bufferedReader, (String)"aligned"));
        this.setAdditive(readBoolean((BufferedReader)bufferedReader, (String)"additive"));
        this.scaleLink = readBoolean((BufferedReader)bufferedReader, (String)"scaleLink");
        this.fixFrame = readBoolean((BufferedReader)bufferedReader, (String)"fixFrame");
    }

   /* void test(){
        var2_2.xScale = this.xScaleValue.newLowValue() / var8_9;
        var2_2.xScaleDiff = this.xScaleValue.newHighValue() / var8_9;
        var2_2.yScale = this.yScaleValue.newLowValue() / var9_10;
        var2_2.yScaleDiff = this.yScaleValue.newHighValue() / var9_10;
        if (this.isScaleLink()) {
            if (!this.xScaleValue.isRelative()) {
                var2_2.xScaleDiff -= var2_2.xScale;
            }
            var2_2.setScale(var2_2.xScale + var2_2.xScaleDiff * this.xScaleValue.getScale(0.0f));
        } else {
            if (!this.xScaleValue.isRelative()) {
                var2_2.xScaleDiff -= var2_2.xScale;
            }
            var2_2.setScale(var2_2.xScale + var2_2.xScaleDiff * this.xScaleValue.getScale(0.0f), var2_2.getScaleY());
            if (!this.yScaleValue.isRelative()) {
                var2_2.yScaleDiff -= var2_2.yScale;
            }
            var2_2.setScale(var2_2.getScaleX(), var2_2.yScale + var2_2.yScaleDiff * this.yScaleValue.getScale(0.0f));
        }



        particle.xScale = xScaleValue.newLowValue() / spriteWidth;
        particle.xScaleDiff = xScaleValue.newHighValue() / spriteWidth;
        if (!xScaleValue.isRelative()) particle.xScaleDiff -= particle.xScale;

        if (yScaleValue.active) {
            particle.yScale = yScaleValue.newLowValue() / spriteHeight;
            particle.yScaleDiff = yScaleValue.newHighValue() / spriteHeight;
            if (!yScaleValue.isRelative()) particle.yScaleDiff -= particle.yScale;
            particle.setScale(particle.xScale + particle.xScaleDiff * xScaleValue.getScale(0),
                    particle.yScale + particle.yScaleDiff * yScaleValue.getScale(0));
        } else {
            particle.setScale(particle.xScale + particle.xScaleDiff * xScaleValue.getScale(0));
        }


    }
    */

    static int readInt (BufferedReader reader, String name) throws IOException {
        return Integer.parseInt(readString(reader, name));
    }

    static String readString (BufferedReader reader, String name) throws IOException {
        String line = reader.readLine();
        if (line == null) throw new IOException("Missing value: " + name);
        return readString(line);
    }

    static String readString (String line) throws IOException {
        return line.substring(line.indexOf(":") + 1).trim();
    }

    static boolean readBoolean (BufferedReader reader, String name) throws IOException {
        return Boolean.parseBoolean(readString(reader, name));
    }

    public static boolean parseBoolean(String var0) {
        return var0 != null && var0.equalsIgnoreCase("true");
    }

    static String readVesion(String string) throws IOException {
        if (string == null) {
            throw new IOException("Missing Vesion: ");
        }
        if (string.startsWith("version:")) return string.substring(string.indexOf(":") + 1).trim();
        return null;
    }

    public void draw(Batch batch, float f) {
        if (this.fixFrame) {
            f = fixDeltaTime;
        }
        super.draw(batch, f);
    }

}
