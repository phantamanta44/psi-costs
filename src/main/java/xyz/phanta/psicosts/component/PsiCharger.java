package xyz.phanta.psicosts.component;

import io.github.phantamanta44.libnine.component.reservoir.IIntReservoir;
import io.github.phantamanta44.libnine.component.reservoir.SimpleIntReservoir;
import io.github.phantamanta44.libnine.util.data.ByteUtils;
import io.github.phantamanta44.libnine.util.data.ISerializable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.capability.PsiCell;
import xyz.phanta.psicosts.capability.PsiProvider;
import xyz.phanta.psicosts.capability.impl.PsiReservoir;
import xyz.phanta.psicosts.init.PsioCaps;

import java.util.Objects;

public class PsiCharger implements ISerializable {

    private final PsioConfig.ConversionConfig conv;
    private final IIntReservoir buffer;

    public PsiCharger(PsioConfig.ConversionConfig conv, Runnable observer) {
        this.conv = conv;
        this.buffer = new SimpleIntReservoir(conv.psiBuffer);
        buffer.onQuantityChange((o, n) -> observer.run());
    }

    public PsiProvider createCapabilityPsi() {
        return new PsiReservoir(buffer);
    }

    public boolean isNotFull() {
        return buffer.getQuantity() < buffer.getCapacity();
    }

    public double offer(double provided) {
        return buffer.offer((int)Math.ceil(provided * conv.ratio), true) / conv.ratio;
    }

    public void tryCharge(ItemStack stack) {
        int stored = buffer.getQuantity();
        if (stored > 0 && stack.hasCapability(PsioCaps.PSI_CELL, null)) {
            PsiCell cell = Objects.requireNonNull(stack.getCapability(PsioCaps.PSI_CELL, null));
            if (cell.getStoredCharge() < cell.getMaxCharge()) {
                buffer.offsetQuantity(-cell.injectCharge(stored));
            }
        }
    }

    @Override
    public void serBytes(ByteUtils.Writer data) {
        buffer.serBytes(data);
    }

    @Override
    public void deserBytes(ByteUtils.Reader data) {
        buffer.deserBytes(data);
    }

    @Override
    public void serNBT(NBTTagCompound tag) {
        buffer.serNBT(tag);
    }

    @Override
    public void deserNBT(NBTTagCompound tag) {
        buffer.deserNBT(tag);
    }

}
