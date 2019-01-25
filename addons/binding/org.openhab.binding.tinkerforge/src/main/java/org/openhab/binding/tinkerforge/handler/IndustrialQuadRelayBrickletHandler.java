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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.openhab.binding.tinkerforge.internal.CommandConverter;
import org.m1theo.tinkerforge.client.ActuatorChannel;


/**
 * The {@link IndustrialQuadRelayBrickletHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Theo Weiss <theo@m1theo.org> - Initial contribution
 */
@NonNullByDefault

public class IndustrialQuadRelayBrickletHandler extends BaseThingHandler implements DeviceAdminListener {

    private final Logger logger = LoggerFactory.getLogger(IndustrialQuadRelayBrickletHandler.class);
    private @Nullable BaseDeviceConfig config;
    private @Nullable BrickdBridgeHandler bridgeHandler;
    private @Nullable String uid;

    public IndustrialQuadRelayBrickletHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {

        switch (channelUID.getId()) {
        
          
            case "relay0":
                
                if(command instanceof OnOffType) {
                  ActuatorChannel channel = (ActuatorChannel) bridgeHandler.getBrickd().getChannel(uid, channelUID.getId());
                  channel.setValue(CommandConverter.convert(command));
                }
                
                //TODO do something
                break;
          
        
          
            case "relay1":
                
                if(command instanceof OnOffType) {
                  ActuatorChannel channel = (ActuatorChannel) bridgeHandler.getBrickd().getChannel(uid, channelUID.getId());
                  channel.setValue(CommandConverter.convert(command));
                }
                
                //TODO do something
                break;
          
        
          
            case "relay2":
                
                if(command instanceof OnOffType) {
                  ActuatorChannel channel = (ActuatorChannel) bridgeHandler.getBrickd().getChannel(uid, channelUID.getId());
                  channel.setValue(CommandConverter.convert(command));
                }
                
                //TODO do something
                break;
          
        
          
            case "relay3":
                
                if(command instanceof OnOffType) {
                  ActuatorChannel channel = (ActuatorChannel) bridgeHandler.getBrickd().getChannel(uid, channelUID.getId());
                  channel.setValue(CommandConverter.convert(command));
                }
                
                //TODO do something
                break;
          
        
            default:
                break;
        }

    }

    @Override
    public void initialize() {
        config = getConfigAs(BaseDeviceConfig.class);
        String configUid = config.getUid();
        if (configUid != null) {
            uid = configUid;
            Bridge bridge = getBridge();
            ThingStatus bridgeStatus = (bridge == null) ? null : bridge.getStatus();
            BrickdBridgeHandler brickdBridgeHandler = getBrickdBridgeHandler();
            if (brickdBridgeHandler != null) {
                brickdBridgeHandler.registerDeviceStatusListener(this);
                if (bridgeStatus == ThingStatus.ONLINE) {
                    if (brickdBridgeHandler.getBrickd().getDevice(uid) != null) {
                        updateStatus(ThingStatus.ONLINE);
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
                
            }
        }
        return bridgeHandler;
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

}