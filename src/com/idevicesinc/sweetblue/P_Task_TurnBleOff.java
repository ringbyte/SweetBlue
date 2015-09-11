package com.idevicesinc.sweetblue;

import android.bluetooth.BluetoothAdapter;

/**
 * 
 * 
 *
 */
class P_Task_TurnBleOff extends PA_Task
{
	private final boolean m_implicit;
	
	public P_Task_TurnBleOff(BleManager manager, boolean implicit)
	{
		this(manager, implicit, null);
	}
	
	public P_Task_TurnBleOff(BleManager manager, boolean implicit, I_StateListener listener)
	{
		super(manager, listener);
		
		m_implicit = implicit;
	}
	
	public boolean isImplicit()
	{
		return m_implicit;
	}
	
	@Override public void execute()
	{
		if( getManager().getNative().getAdapter().getState() == BluetoothAdapter.STATE_OFF )
		{
			redundant();
		}
		else if( getManager().getNative().getAdapter().getState() == BluetoothAdapter.STATE_TURNING_OFF )
		{
			// DRK > Nothing to do, already turning off.
		}
		else
		{
			if( m_implicit )
			{
				this.fail();
			}
			else if( false == getManager().getNative().getAdapter().disable() )
			{
				this.fail();
			}
			else
			{
				// SUCCESS, for now...
			}
		}
	}
	
	@Override public PE_TaskPriority getPriority()
	{
		return PE_TaskPriority.CRITICAL;
	}
	
	@Override protected BleTask getTaskType()
	{
		return BleTask.TURN_BLE_OFF;
	}
}
