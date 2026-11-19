package doorsmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;

public class DoorsModClient implements ClientModInitializer {
	private int battery = 100;
	private boolean flashlightOn = false;
	private int tickCounter = 0;

	private static final KeyMapping FLASHLIGHT_KEY = new KeyMapping(
			"key.doorsmod.flashlight",
			InputConstants.Type.KEYBOARD,
			InputConstants.KEY_H,
			KeyMapping.Category.MISC
	);

	@Override
	public void onInitializeClient() {
		KeyMappingHelper.registerKeyMapping(FLASHLIGHT_KEY);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (FLASHLIGHT_KEY.consumeClick()) {
				if (battery > 0) {
					flashlightOn = !flashlightOn;
				}
			}
			tickCounter++;

			if (tickCounter >= 60) {
				tickCounter = 0;

				if (battery <= 0) {
					flashlightOn = false;
					System.out.println("Flashlight Can't Turn on");
				}

				if (flashlightOn) {
					battery -= 1;
					System.out.println("Flashlight Turn On");
				}
			}
		});
	}
}