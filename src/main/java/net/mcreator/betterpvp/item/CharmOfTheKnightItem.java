
package net.mcreator.betterpvp.item;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockSource;

import net.mcreator.betterpvp.procedures.CharmOfTheKnightRightClickedInAirProcedure;
import net.mcreator.betterpvp.procedures.CharmOfTheKnightItemIsDroppedByPlayerProcedure;
import net.mcreator.betterpvp.init.BetterpvpModTabs;
import net.mcreator.betterpvp.init.BetterpvpModItems;

import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap;

@Mod.EventBusSubscriber
public class CharmOfTheKnightItem extends Item {
	public CharmOfTheKnightItem() {
		super(new Item.Properties().tab(BetterpvpModTabs.TAB_BETTER_PV_P).durability(1).fireResistant().rarity(Rarity.COMMON));
		setRegistryName("charm_of_the_knight");
	}

	@Override
	public int getUseDuration(ItemStack itemstack) {
		return 0;
	}

	@Override
	public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
		return 10F;
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
		if (equipmentSlot == EquipmentSlot.MAINHAND) {
			ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
			builder.putAll(super.getDefaultAttributeModifiers(equipmentSlot));
			builder.put(Attributes.ATTACK_DAMAGE,
					new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 3d, AttributeModifier.Operation.ADDITION));
			builder.put(Attributes.ATTACK_SPEED,
					new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Item modifier", -2.4, AttributeModifier.Operation.ADDITION));
		}
		return super.getDefaultAttributeModifiers(equipmentSlot);
	}

	@Override
	public boolean isFoil(ItemStack itemstack) {
		return true;
	}

	@Override
	public boolean isCorrectToolForDrops(BlockState state) {
		return true;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		ItemStack itemstack = ar.getObject();
		double x = entity.getX();
		double y = entity.getY();
		double z = entity.getZ();

		CharmOfTheKnightRightClickedInAirProcedure.execute(world, x, y, z, entity, itemstack);
		return ar;
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		InteractionResult retval = super.useOn(context);
		CharmOfTheKnightRightClickedInAirProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(),
				context.getClickedPos().getZ(), context.getPlayer(), context.getItemInHand());
		return retval;
	}

	@Override
	public boolean onDroppedByPlayer(ItemStack itemstack, Player entity) {
		CharmOfTheKnightItemIsDroppedByPlayerProcedure.execute(entity.level, entity);
		return true;
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> DispenserBlock.registerBehavior(BetterpvpModItems.CHARM_OF_THE_KNIGHT, new OptionalDispenseItemBehavior() {
			public ItemStack execute(BlockSource blockSource, ItemStack stack) {
				ItemStack itemstack = stack.copy();
				Level world = blockSource.getLevel();
				Direction direction = blockSource.getBlockState().getValue(DispenserBlock.FACING);
				int x = blockSource.getPos().getX();
				int y = blockSource.getPos().getY();
				int z = blockSource.getPos().getZ();
				this.setSuccess(true);
				if (this.isSuccess())
					itemstack.shrink(1);
				return itemstack;
			}
		}));
	}
}
