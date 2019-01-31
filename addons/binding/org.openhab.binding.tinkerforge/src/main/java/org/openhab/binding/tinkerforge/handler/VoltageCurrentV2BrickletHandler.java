/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.tinkerforge.handler;


import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.library.types.*;
import org.eclipse.smarthome.core.thing.*;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.m1theo.tinkerforge.client.config.BaseDeviceConfig;
import org.m1theo.tinkerforge.client.DeviceAdminListener;
import org.m1theo.tinkerforge.client.DeviceChangeType;
import org.m1theo.tinkerforge.client.DeviceInfo;
import org.m1theo.tinkerforge.client.Device;
import org.m1theo.tinkerforge.client.devices.voltagecurrentv2.VoltageCurrentV2Config;
import org.m1theo.tinkerforge.client.devices.voltagecurrentv2.VoltageCurrentV2Bricklet;
import org.m1theo.tinkerforge.client.devices.DeviceType;
import org.m1theo.tinkerforge.client.devices.voltagecurrentv2.ChannelId;
import org.m1theo.tinkerforge.client.types.*;

import org.m1theo.tinkerforge.client.devices.voltagecurrentv2.VoltageChannel;
import org.m1theo.tinkerforge.client.devices.voltagecurrentv2.CurrentChannel;
import org.m1theo.tinkerforge.client.devices.voltagecurrentv2.PowerChannel;

import org.eclipse.smarthome.core.library.unit.SmartHomeUnits;
import org.eclipse.smarthome.core.library.unit.MetricPrefix;
import org.eclipse.smarthome.core.library.unit.*;
import org.m1theo.tinkerforge.client.Notifier;
import org.m1theo.tinkerforge.client.CallbackListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The {@link VoltageCurrentV2BrickletHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Theo Weiss <theo@m1theo.org> - Initial contribution
 */
@NonNullByDefault

public class VoltageCurrentV2BrickletHandler extends BaseThingHandler implements CallbackListener, DeviceAdminListener {

    private final Logger logger = LoggerFactory.getLogger(VoltageCurrentV2BrickletHandler.class);
    private @Nullable VoltageCurrentV2Config config;
    private @Nullable BrickdBridgeHandler bridgeHandler;
    private @Nullable String uid;

    public VoltageCurrentV2BrickletHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {

    }

    @Override
    public void initialize() {
        config = getConfigAs(VoltageCurrentV2Config.class);
        String configUid = config.getUid();
        if (configUid != null) {
            uid = configUid;
            Bridge bridge = getBridge();
            ThingStatus bridgeStatus = (bridge == null) ? null : bridge.getStatus();
            BrickdBridgeHandler brickdBridgeHandler = getBrickdBridgeHandler();
            if (brickdBridgeHandler != null) {
                brickdBridgeHandler.registerDeviceStatusListener(this);
                if (bridgeStatus == ThingStatus.ONLINE) {
                    Device<?,?> device = brickdBridgeHandler.getBrickd().getDevice(uid);
                    if (device != null) {
                      if (device.getDeviceType() == DeviceType.voltagecurrentV2){
                        VoltageCurrentV2Bricklet device2 = (VoltageCurrentV2Bricklet) device;
                        device2.setDeviceConfig(config);
                        device2.enable();
                        updateStatus(ThingStatus.ONLINE);

                      } else {
                        updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR);
                      }
                    } else {
                        updateStatus(ThingStatus.OFFLINE);
                    }
                } else {
                    updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE);
                }
            } else {
                updateStatus(ThingStatus.OFFLINE);
            }
        } else {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "uid is missing in configuration");
        }
    }

    private synchronized @Nullable BrickdBridgeHandler getBrickdBridgeHandler() {
        if (bridgeHandler == null) {
            Bridge bridge = getBridge();
            if (bridge == null) {
                return null;
            }
            ThingHandler handler = bridge.getHandler();
            if (handler instanceof BrickdBridgeHandler) {
                bridgeHandler = (BrickdBridgeHandler) handler;
                bridgeHandler.registerCallbackListener(this);
            }
        }
        return bridgeHandler;
    }


    @Override
    public void notify(@Nullable Notifier notifier, @Nullable TinkerforgeValue lastValue, @Nullable TinkerforgeValue
    newValue) {
        if (notifier == null) {
            return;
        }
        if (!notifier.getDeviceId().equals(uid)) {
            return;
        }
        if (notifier.getExternalDeviceId() != null) {
            // TODO
        } else {
            
            
            if (notifier.getChannelId().equals(ChannelId.voltage.name())) {
                
                if (newValue instanceof DecimalValue) {
                    logger.debug("new value {}", newValue);
                    updateState(notifier.getChannelId(), new QuantityType<>(new DecimalType(((DecimalValue) newValue).bigDecimalValue()), MetricPrefix.MILLI(SmartHomeUnits.VOLT)));
                    
                    return;
                }
                
            }
            
            
            
            if (notifier.getChannelId().equals(ChannelId.current.name())) {
                
                if (newValue instanceof DecimalValue) {
                    logger.debug("new value {}", newValue);
                    updateState(notifier.getChannelId(), new QuantityType<>(new DecimalType(((DecimalValue) newValue).bigDecimalValue()), MetricPrefix.MILLI(SmartHomeUnits.AMPERE)));
                    
                    return;
                }
                
            }
            
            
            
            if (notifier.getChannelId().equals(ChannelId.power.name())) {
                
                if (newValue instanceof DecimalValue) {
                    logger.debug("new value {}", newValue);
                    updateState(notifier.getChannelId(), new QuantityType<>(new DecimalType(((DecimalValue) newValue).bigDecimalValue()), MetricPrefix.MILLI(SmartHomeUnits.WATT)));
                    
                    return;
                }
                
            }
            
            
        }
    }


    @Override
    public void deviceChanged(@Nullable DeviceChangeType changeType, @Nullable DeviceInfo info) {
        if (changeType == null || info == null) {
            logger.debug("device changed but devicechangtype or deviceinfo are null");
            return;
        }

        if (info.getUid().equals(uid)) {
            if (changeType == DeviceChangeType.ADD) {
                updateStatus(ThingStatus.ONLINE);
            } else {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.GONE);
            }
        }
    }

    @Override
    public void channelLinked(ChannelUID channelUID) {
        switch (channelUID.getId()) {


          case "voltage":
              getvoltage();
              break;


          case "current":
              getcurrent();
              break;


          case "power":
              getpower();
              break;

          default:
            break;
        }
    }




    private void getvoltage() {
        BrickdBridgeHandler brickdBridgeHandler = getBrickdBridgeHandler();
        if (brickdBridgeHandler != null) {
            Device<?, ?> device = brickdBridgeHandler.getBrickd().getDevice(uid);
            if (device != null) {
                VoltageCurrentV2Bricklet device2 = (VoltageCurrentV2Bricklet) device;
                VoltageChannel channel = (VoltageChannel) device2.getChannel("voltage");
                Object newValue = channel.getValue();
                
                if (newValue instanceof DecimalValue) {
                    logger.debug("new value {}", newValue);
                    updateState(ChannelId.voltage.name(), new QuantityType<>(new DecimalType(((DecimalValue) newValue).bigDecimalValue()), MetricPrefix.MILLI(SmartHomeUnits.VOLT)));
                    
                    return;
                }
                
            }
        }
    }



    private void getcurrent() {
        BrickdBridgeHandler brickdBridgeHandler = getBrickdBridgeHandler();
        if (brickdBridgeHandler != null) {
            Device<?, ?> device = brickdBridgeHandler.getBrickd().getDevice(uid);
            if (device != null) {
                VoltageCurrentV2Bricklet device2 = (VoltageCurrentV2Bricklet) device;
                CurrentChannel channel = (CurrentChannel) device2.getChannel("current");
                Object newValue = channel.getValue();
                
                if (newValue instanceof DecimalValue) {
                    logger.debug("new value {}", newValue);
                    updateState(ChannelId.current.name(), new QuantityType<>(new DecimalType(((DecimalValue) newValue).bigDecimalValue()), MetricPrefix.MILLI(SmartHomeUnits.AMPERE)));
                    
                    return;
                }
                
            }
        }
    }



    private void getpower() {
        BrickdBridgeHandler brickdBridgeHandler = getBrickdBridgeHandler();
        if (brickdBridgeHandler != null) {
            Device<?, ?> device = brickdBridgeHandler.getBrickd().getDevice(uid);
            if (device != null) {
                VoltageCurrentV2Bricklet device2 = (VoltageCurrentV2Bricklet) device;
                PowerChannel channel = (PowerChannel) device2.getChannel("power");
                Object newValue = channel.getValue();
                
                if (newValue instanceof DecimalValue) {
                    logger.debug("new value {}", newValue);
                    updateState(ChannelId.power.name(), new QuantityType<>(new DecimalType(((DecimalValue) newValue).bigDecimalValue()), MetricPrefix.MILLI(SmartHomeUnits.WATT)));
                    
                    return;
                }
                
            }
        }
    }





@Override
public void dispose() {

}

}