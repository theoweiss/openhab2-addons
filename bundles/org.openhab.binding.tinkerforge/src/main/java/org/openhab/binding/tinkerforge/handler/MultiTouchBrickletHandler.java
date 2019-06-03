/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.tinkerforge.handler;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.CommonTriggerEvents;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.m1theo.tinkerforge.client.CallbackListener;
import org.m1theo.tinkerforge.client.Device;
import org.m1theo.tinkerforge.client.DeviceAdminListener;
import org.m1theo.tinkerforge.client.DeviceChangeType;
import org.m1theo.tinkerforge.client.DeviceInfo;
import org.m1theo.tinkerforge.client.Notifier;
import org.m1theo.tinkerforge.client.devices.DeviceType;
import org.m1theo.tinkerforge.client.devices.multitouch.ChannelId;
import org.m1theo.tinkerforge.client.devices.multitouch.MultiTouchBricklet;
import org.m1theo.tinkerforge.client.devices.multitouch.MultiTouchDeviceConfig;
import org.m1theo.tinkerforge.client.types.HighLowValue;
import org.m1theo.tinkerforge.client.types.TinkerforgeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link MultiTouchBrickletHandler} is responsible for handling commands,
 * which are sent to one of the channels.
 *
 * @author Theo Weiss <theo@m1theo.org> - Initial contribution
 */
@NonNullByDefault

public class MultiTouchBrickletHandler extends BaseThingHandler implements CallbackListener, DeviceAdminListener {

	private final Logger logger = LoggerFactory.getLogger(MultiTouchBrickletHandler.class);
	private @Nullable MultiTouchDeviceConfig config;
	private @Nullable BrickdBridgeHandler bridgeHandler;
	private @Nullable MultiTouchBricklet device;
	private @Nullable String uid;
	private boolean enabled = false;

	public MultiTouchBrickletHandler(Thing thing) {
		super(thing);
	}

	@Override
	public void handleCommand(ChannelUID channelUID, Command command) {

	}

	@Override
	public void initialize() {
		config = getConfigAs(MultiTouchDeviceConfig.class);
		String uid = config.getUid();
		if (uid != null) {
			this.uid = uid;
			BrickdBridgeHandler brickdBridgeHandler = getBrickdBridgeHandler();
			if (brickdBridgeHandler != null) {
				brickdBridgeHandler.registerDeviceStatusListener(this);
				enable();
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

	private void enable() {
		logger.debug("executing enable");
		Bridge bridge = getBridge();
		ThingStatus bridgeStatus = (bridge == null) ? null : bridge.getStatus();
		BrickdBridgeHandler brickdBridgeHandler = getBrickdBridgeHandler();
		if (brickdBridgeHandler != null) {
			brickdBridgeHandler.registerCallbackListener(this, uid);
			if (bridgeStatus == ThingStatus.ONLINE) {
				Device<?, ?> deviceIn = brickdBridgeHandler.getBrickd().getDevice(uid);
				if (deviceIn != null) {
					if (deviceIn.getDeviceType() == DeviceType.multitouch) {
						MultiTouchBricklet device = (MultiTouchBricklet) deviceIn;
						device.setDeviceConfig(config);

						device.enable();
						this.device = device;
						enabled = true;
						updateStatus(ThingStatus.ONLINE);
						updateChannelStates();

					} else {
						logger.error("configuration error");
						updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR);
					}
				} else {
					logger.error("deviceIn is null");
					updateStatus(ThingStatus.OFFLINE);
				}
			} else {
				logger.error("bridge is offline");
				updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE);
			}
		} else {
			logger.error("brickdBridgeHandler is null");
			updateStatus(ThingStatus.OFFLINE);
		}
	}

	@Override
	public void notify(@Nullable Notifier notifier, @Nullable TinkerforgeValue lastValue,
			@Nullable TinkerforgeValue newValue) {
		if (notifier == null) {
			return;
		}
		if (!notifier.getDeviceId().equals(uid)) {
			return;
		}
		if (notifier.getExternalDeviceId() != null) {
			// TODO
		} else {

			if (notifier.getChannelId().equals(ChannelId.electrode0.name())) {

				if (newValue instanceof HighLowValue) {
					logger.debug("new value {}", newValue);

					String value = newValue == HighLowValue.HIGH ? CommonTriggerEvents.PRESSED
							: CommonTriggerEvents.RELEASED;
					triggerChannel(notifier.getChannelId(), value);

					return;
				}

			}

			if (notifier.getChannelId().equals(ChannelId.electrode1.name())) {

				if (newValue instanceof HighLowValue) {
					logger.debug("new value {}", newValue);

					String value = newValue == HighLowValue.HIGH ? CommonTriggerEvents.PRESSED
							: CommonTriggerEvents.RELEASED;
					triggerChannel(notifier.getChannelId(), value);

					return;
				}

			}

			if (notifier.getChannelId().equals(ChannelId.electrode2.name())) {

				if (newValue instanceof HighLowValue) {
					logger.debug("new value {}", newValue);

					String value = newValue == HighLowValue.HIGH ? CommonTriggerEvents.PRESSED
							: CommonTriggerEvents.RELEASED;
					triggerChannel(notifier.getChannelId(), value);

					return;
				}

			}

			if (notifier.getChannelId().equals(ChannelId.electrode3.name())) {

				if (newValue instanceof HighLowValue) {
					logger.debug("new value {}", newValue);

					String value = newValue == HighLowValue.HIGH ? CommonTriggerEvents.PRESSED
							: CommonTriggerEvents.RELEASED;
					triggerChannel(notifier.getChannelId(), value);

					return;
				}

			}

			if (notifier.getChannelId().equals(ChannelId.electrode4.name())) {

				if (newValue instanceof HighLowValue) {
					logger.debug("new value {}", newValue);

					String value = newValue == HighLowValue.HIGH ? CommonTriggerEvents.PRESSED
							: CommonTriggerEvents.RELEASED;
					triggerChannel(notifier.getChannelId(), value);

					return;
				}

			}

			if (notifier.getChannelId().equals(ChannelId.electrode5.name())) {

				if (newValue instanceof HighLowValue) {
					logger.debug("new value {}", newValue);

					String value = newValue == HighLowValue.HIGH ? CommonTriggerEvents.PRESSED
							: CommonTriggerEvents.RELEASED;
					triggerChannel(notifier.getChannelId(), value);

					return;
				}

			}

			if (notifier.getChannelId().equals(ChannelId.electrode6.name())) {

				if (newValue instanceof HighLowValue) {
					logger.debug("new value {}", newValue);

					String value = newValue == HighLowValue.HIGH ? CommonTriggerEvents.PRESSED
							: CommonTriggerEvents.RELEASED;
					triggerChannel(notifier.getChannelId(), value);

					return;
				}

			}

			if (notifier.getChannelId().equals(ChannelId.electrode7.name())) {

				if (newValue instanceof HighLowValue) {
					logger.debug("new value {}", newValue);

					String value = newValue == HighLowValue.HIGH ? CommonTriggerEvents.PRESSED
							: CommonTriggerEvents.RELEASED;
					triggerChannel(notifier.getChannelId(), value);

					return;
				}

			}

			if (notifier.getChannelId().equals(ChannelId.electrode8.name())) {

				if (newValue instanceof HighLowValue) {
					logger.debug("new value {}", newValue);

					String value = newValue == HighLowValue.HIGH ? CommonTriggerEvents.PRESSED
							: CommonTriggerEvents.RELEASED;
					triggerChannel(notifier.getChannelId(), value);

					return;
				}

			}

			if (notifier.getChannelId().equals(ChannelId.electrode9.name())) {

				if (newValue instanceof HighLowValue) {
					logger.debug("new value {}", newValue);

					String value = newValue == HighLowValue.HIGH ? CommonTriggerEvents.PRESSED
							: CommonTriggerEvents.RELEASED;
					triggerChannel(notifier.getChannelId(), value);

					return;
				}

			}

			if (notifier.getChannelId().equals(ChannelId.electrode10.name())) {

				if (newValue instanceof HighLowValue) {
					logger.debug("new value {}", newValue);

					String value = newValue == HighLowValue.HIGH ? CommonTriggerEvents.PRESSED
							: CommonTriggerEvents.RELEASED;
					triggerChannel(notifier.getChannelId(), value);

					return;
				}

			}

			if (notifier.getChannelId().equals(ChannelId.electrode11.name())) {

				if (newValue instanceof HighLowValue) {
					logger.debug("new value {}", newValue);

					String value = newValue == HighLowValue.HIGH ? CommonTriggerEvents.PRESSED
							: CommonTriggerEvents.RELEASED;
					triggerChannel(notifier.getChannelId(), value);

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
				logger.debug("{} added", uid);
				enable();
			} else {
				updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.GONE);
			}
		}
	}

	@Override
	public void channelLinked(ChannelUID channelUID) {
		if (enabled) {
			switch (channelUID.getId()) {

			default:
				break;
			}
		}
	}

	private void updateChannelStates() {

	}

	@Override
	public void dispose() {

		BrickdBridgeHandler brickdBridgeHandler = getBrickdBridgeHandler();
		if (brickdBridgeHandler != null) {
			brickdBridgeHandler.unregisterDeviceStatusListener(this);
			brickdBridgeHandler.unregisterCallbackListener(this, uid);
		}
		if (device != null) {
			device.disable();
		}

		enabled = false;
	}

}