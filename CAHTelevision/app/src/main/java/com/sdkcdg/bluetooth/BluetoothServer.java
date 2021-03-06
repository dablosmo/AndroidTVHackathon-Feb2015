package com.sdkcdg.bluetooth;

import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

public class BluetoothServer extends Thread
{
	private static final String NAME = "CAH Phone";
	private static final String TAG = "Bluetooth Server";
	private static final UUID MY_UUID = UUID.fromString("23708e76-aeff-11e4-9822-12e3f512a338");
	private BluetoothServerSocket mServerSocket;
	private static boolean serverOnline = true;
	private Handler mHandler;
	
	public BluetoothServer(Handler handler, BluetoothAdapter adapter)
	{
		this.mHandler = handler;
		BluetoothServerSocket tempSocket = null;
		try
		{
			tempSocket = adapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
		}
		catch(Exception e)
		{ 
			Log.e(TAG, "Error getting server socket ");
			serverOnline = false;
		}
		this.mServerSocket = tempSocket;
		serverOnline = false;
	}
	
	public void run()
	{
		BluetoothSocket socket = null;
		if(mServerSocket == null)
		{
			return;
		}
		while(true)
		{
			try
			{
				Log.d(TAG, "Opening new server socket");
				socket = mServerSocket.accept(); //Blocking call until socket is accepted	
				try
				{
					DataTransferThread dtt = new DataTransferThread(socket, mHandler);
					dtt.start();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			catch(Exception e)
			{
				Log.e(TAG,"Server Socket Closed - Server Thread canceled?");
				
			}
		}
	}
	
	public boolean isServerOnline()
	{
		return serverOnline;
	}
	public void cancel()
	{
		try
		{
			Log.d(TAG, "Closing Server Socket");
			mServerSocket.close();
			mServerSocket = null;
		}
		catch(Exception e)
		{
			Log.e(TAG, "Error Closing Server Socket");
			mServerSocket = null;
		}
	}
}
