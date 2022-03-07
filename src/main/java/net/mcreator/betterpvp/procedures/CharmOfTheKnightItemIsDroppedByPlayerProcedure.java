package net.mcreator.betterpvp.procedures;

import net.minecraftforge.fmllegacy.server.ServerLifecycleHooks;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.ChatType;
import net.minecraft.Util;

public class CharmOfTheKnightItemIsDroppedByPlayerProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (!world.isClientSide()) {
			MinecraftServer mcserv = ServerLifecycleHooks.getCurrentServer();
			if (mcserv != null)
				mcserv.getPlayerList().broadcastMessage(
						new TextComponent(("The Charm Of The Knight Has Been Dropped" + (entity.getX() + "" + (entity.getY() + "" + entity.getZ())))),
						ChatType.SYSTEM, Util.NIL_UUID);
		}
	}
}
