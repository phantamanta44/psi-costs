package xyz.phanta.psicosts.integration.embers;

import io.github.phantamanta44.libnine.util.data.ByteUtils;
import io.github.phantamanta44.libnine.util.data.ISerializable;
import net.minecraft.nbt.NBTTagCompound;
import teamroots.embers.api.power.IEmberCapability;
import teamroots.embers.power.DefaultEmberCapability;

public class EmberPool implements IEmberCapability, ISerializable {

    private final DefaultEmberCapability backing;
    private final Runnable observer;

    public EmberPool(double capacity, Runnable observer) {
        this.observer = observer;
        this.backing = new DefaultEmberCapability();
        backing.setEmberCapacity(capacity);
    }

    @Override
    public double getEmber() {
        return backing.getEmber();
    }

    @Override
    public double getEmberCapacity() {
        return backing.getEmberCapacity();
    }

    @Override
    public void setEmber(double value) {
        backing.setEmber(value);
    }

    @Override
    public void setEmberCapacity(double value) {
        backing.setEmberCapacity(value);
    }

    @Override
    public double addAmount(double value, boolean doAdd) {
        return backing.addAmount(value, doAdd);
    }

    @Override
    public double removeAmount(double value, boolean doRemove) {
        return backing.removeAmount(value, doRemove);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        backing.writeToNBT(tag);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        backing.readFromNBT(tag);
    }

    @Override
    public void onContentsChanged() {
        observer.run();
    }

    @Override
    public void serBytes(ByteUtils.Writer data) {
        data.writeDouble(backing.getEmber());
        data.writeDouble(backing.getEmberCapacity());
    }

    @Override
    public void deserBytes(ByteUtils.Reader data) {
        backing.setEmber(data.readDouble());
        backing.setEmberCapacity(data.readDouble());
    }

    @Override
    public void serNBT(NBTTagCompound tag) {
        writeToNBT(tag);
    }

    @Override
    public void deserNBT(NBTTagCompound tag) {
        readFromNBT(tag);
    }

}
