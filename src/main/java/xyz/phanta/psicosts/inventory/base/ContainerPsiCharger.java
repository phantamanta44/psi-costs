package xyz.phanta.psicosts.inventory.base;

import io.github.phantamanta44.libnine.gui.L9Container;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import xyz.phanta.psicosts.capability.PsiProvider;
import xyz.phanta.psicosts.tile.base.TilePsiCharger;

public class ContainerPsiCharger<T extends TilePsiCharger> extends L9Container {

    protected final T tile;

    public ContainerPsiCharger(T tile, InventoryPlayer ipl) {
        super(ipl);
        this.tile = tile;
        addSlotToContainer(new SlotItemHandler(tile.getInputSlot(), 0, 80, 35));
    }

    public PsiProvider getPsiProvider() {
        return tile.getPsiProvider();
    }

    public ItemStack getInputStack() {
        return tile.getInputSlot().getStackInSlot();
    }

}
