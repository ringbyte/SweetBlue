package com.idevicesinc.sweetblue;


import com.idevicesinc.sweetblue.utils.BleStatuses;

import java.lang.reflect.Method;

class P_Task_DiscoverServices extends P_Task_RequiresConnection
{

    public P_Task_DiscoverServices(BleDevice device, IStateListener listener)
    {
        super(device, listener);
    }

    @Override public P_TaskPriority getPriority()
    {
        return P_TaskPriority.MEDIUM;
    }

    @Override public void execute()
    {
        final boolean useGattRefresh = getDevice().getConfig() == null ? getManager().mConfig.useGattRefresh : getDevice().getConfig().useGattRefresh;

        if (useGattRefresh)
        {
            refresh();
        }

        getDevice().mGattManager.discoverServices(this);

    }

    @Override void onFail(boolean immediate)
    {
        super.onFail(immediate);
        getDevice().onConnectionFailed(BleStatuses.GATT_STATUS_NOT_APPLICABLE);
    }

    void discoverServicesImmediatelyFailed()
    {
        failImmediately();
        // TODO - Throw error to errorlistener
    }

    private void refresh()
    {
        try
        {
            Method mRefreshMethod = getDevice().getNativeGatt().getClass().getMethod("refresh", (Class[]) null);
            mRefreshMethod.invoke(getDevice().getNativeGatt(), (Object[]) null);
        }
        catch (Exception e)
        {
        }
    }
}