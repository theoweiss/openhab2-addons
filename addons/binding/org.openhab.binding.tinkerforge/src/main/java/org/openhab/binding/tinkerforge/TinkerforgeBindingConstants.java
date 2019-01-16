/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.tinkerforge;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link TinkerforgeBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Theo Weiss <theo@m1theo.org> - Initial contribution
 */
 @NonNullByDefault
public class TinkerforgeBindingConstants {

    public static final String BINDING_ID = "tinkerforge";
    public static final String DEVICE_UID_PARAM = "uid";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_BRICKD = new ThingTypeUID(BINDING_ID, "brickd");
    
    
    public static final ThingTypeUID THING_TYPE_OUTDOORWEATHER = new ThingTypeUID(BINDING_ID, "outdoorweather");
    
    
    public static final ThingTypeUID THING_TYPE_TEMPERATURE = new ThingTypeUID(BINDING_ID, "temperature");
    
    
    public static final ThingTypeUID THING_TYPE_LOADCELL = new ThingTypeUID(BINDING_ID, "loadcell");
    
    
    public static final ThingTypeUID THING_TYPE_SOUNDINTENSITY = new ThingTypeUID(BINDING_ID, "soundintensity");
    
    
    public static final ThingTypeUID THING_TYPE_SOUNDPRESSURELEVEL = new ThingTypeUID(BINDING_ID, "soundpressurelevel");
    
    
    public static final ThingTypeUID THING_TYPE_AMBIENTLIGHT = new ThingTypeUID(BINDING_ID, "ambientlight");
    
    
    public static final ThingTypeUID THING_TYPE_AMBIENTLIGHTV2 = new ThingTypeUID(BINDING_ID, "ambientlightV2");
    
    
    public static final ThingTypeUID THING_TYPE_INDUSTRIALDUALANALOGIN = new ThingTypeUID(BINDING_ID, "industrialdualanalogIn");
    
    
    public static final ThingTypeUID THING_TYPE_INDUSTRIALDUALANALOGINV2 = new ThingTypeUID(BINDING_ID, "industrialdualanalogInV2");
    
    
    public static final ThingTypeUID THING_TYPE_PTC = new ThingTypeUID(BINDING_ID, "ptc");
    
    
    public static final ThingTypeUID THING_TYPE_PTCV2 = new ThingTypeUID(BINDING_ID, "ptcV2");
    
    
    public static final ThingTypeUID THING_TYPE_BAROMETER = new ThingTypeUID(BINDING_ID, "barometer");
    
    
    public static final ThingTypeUID THING_TYPE_BAROMETERV2 = new ThingTypeUID(BINDING_ID, "barometerV2");
    
    
    public static final ThingTypeUID THING_TYPE_HUMIDITY = new ThingTypeUID(BINDING_ID, "humidity");
    
    
    public static final ThingTypeUID THING_TYPE_HUMIDITYV2 = new ThingTypeUID(BINDING_ID, "humidityV2");
    
    
    public static final ThingTypeUID THING_TYPE_MOTIONDETECTOR = new ThingTypeUID(BINDING_ID, "motiondetector");
    
    
    public static final ThingTypeUID THING_TYPE_MOTIONDETECTORV2 = new ThingTypeUID(BINDING_ID, "motiondetectorV2");
    
    
    public static final ThingTypeUID THING_TYPE_REALTIMECLOCK = new ThingTypeUID(BINDING_ID, "realtimeclock");
    
    
    public static final ThingTypeUID THING_TYPE_REALTIMECLOCKV2 = new ThingTypeUID(BINDING_ID, "realtimeclockV2");
    
    
    public static final ThingTypeUID THING_TYPE_ROTARYENCODER = new ThingTypeUID(BINDING_ID, "rotaryencoder");
    
    
    public static final ThingTypeUID THING_TYPE_VOLTAGECURRENT = new ThingTypeUID(BINDING_ID, "voltagecurrent");
    
    
    public static final ThingTypeUID THING_TYPE_VOLTAGECURRENTV2 = new ThingTypeUID(BINDING_ID, "voltagecurrentV2");
    
    
    public static final ThingTypeUID THING_TYPE_DISTANCEUS = new ThingTypeUID(BINDING_ID, "distanceus");
    
    private static final ThingTypeUID[] SUPPORTED_THING_TYPES_UIDS_ARRAY = new ThingTypeUID[] {
      THING_TYPE_BRICKD,
    
    
      THING_TYPE_OUTDOORWEATHER, 
    
      THING_TYPE_TEMPERATURE, 
    
      THING_TYPE_LOADCELL, 
    
      THING_TYPE_SOUNDINTENSITY, 
    
      THING_TYPE_SOUNDPRESSURELEVEL, 
    
      THING_TYPE_AMBIENTLIGHT, 
    
      THING_TYPE_AMBIENTLIGHTV2, 
    
      THING_TYPE_INDUSTRIALDUALANALOGIN, 
    
      THING_TYPE_INDUSTRIALDUALANALOGINV2, 
    
      THING_TYPE_PTC, 
    
      THING_TYPE_PTCV2, 
    
      THING_TYPE_BAROMETER, 
    
      THING_TYPE_BAROMETERV2, 
    
      THING_TYPE_HUMIDITY, 
    
      THING_TYPE_HUMIDITYV2, 
    
      THING_TYPE_MOTIONDETECTOR, 
    
      THING_TYPE_MOTIONDETECTORV2, 
    
      THING_TYPE_REALTIMECLOCK, 
    
      THING_TYPE_REALTIMECLOCKV2, 
    
      THING_TYPE_ROTARYENCODER, 
    
      THING_TYPE_VOLTAGECURRENT, 
    
      THING_TYPE_VOLTAGECURRENTV2, 
    
      THING_TYPE_DISTANCEUS
    };
    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = new HashSet<>(Arrays.asList(SUPPORTED_THING_TYPES_UIDS_ARRAY));

}