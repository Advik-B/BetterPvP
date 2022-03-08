
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.mcreator.betterpvp.init;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;

public class BetterpvpModTabs {
	public static CreativeModeTab TAB_BETTER_PV_P;

	public static void load() {
		TAB_BETTER_PV_P = new CreativeModeTab("tabbetter_pv_p") {
			@Override
			public ItemStack makeIcon() {
				return new ItemStack(BetterpvpModItems.CHARM_OF_THE_KNIGHT);
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return true;
			}
		}.setBackgroundSuffix("item_search.png");
	}
}
