package com.sdkcdg.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.sdkcdg.cahphone.PlayerUtils;
import com.sdkcdg.proto.PlayerCommands;
import com.sdkcdg.proto.PlayerProto.PlayerMessage;

public class BluetoothService extends Service
{
	private static BluetoothClient mClient;
	private static BluetoothServer mServer;
	private static Handler serverHandler;
	private static Handler clientHandler;
	private static BluetoothAdapter mAdapter;
	private static BluetoothDevice mTv;
	private static final String TAG = "CAH Bluetooth Service";
	
	@Override
	public void onCreate()
	{
		mAdapter = BluetoothAdapter.getDefaultAdapter();
		mTv = mAdapter.getRemoteDevice("F8:8F:CA:22:28:77");
		serverHandler = new Handler() 
		{
	        @Override
	        public void handleMessage(Message message) 
	        {
	            switch (message.what) 
	            {
		            case MessageType.DATA_RECEIVED:
		            {
		            	Toast.makeText(BluetoothService.this, "Data Received, Now Parsing", Toast.LENGTH_SHORT).show();
		            	byte[] buffer = (byte[]) message.obj;
		            	try
		            	{
		            		PlayerMessage receivedmsg = PlayerMessage.parseFrom(buffer);
		            		readResponse(receivedmsg);
		            	}
		            	catch(Exception e)
		            	{
		            		e.printStackTrace();
		            	}
		            	break;
		            }
	            }
	        }
	    };
	    
	    clientHandler = new Handler() 
		{
	        @Override
	        public void handleMessage(Message message) 
	        {
	            switch (message.what) 
	            {
                    case MessageType.DATA_SENT_OK: 
                    {
                        Toast.makeText(BluetoothService.this, "Data was sent successfully", Toast.LENGTH_SHORT).show();
                        break;
                    }
	            }
	        }
	    };
	
	    Log.d(TAG, "Initalizing server");
		mServer = new BluetoothServer(serverHandler, mAdapter);
		mServer.start();
		
	    if(mTv != null)
	    {
	    	mClient = new BluetoothClient(clientHandler, mTv);
	    	mClient.connect(mTv);
	    }
	    else
	    {
	    	mClient = new BluetoothClient(clientHandler);
	    }
	    PlayerMessage msg = PlayerMessage.newBuilder()
				.setMName("David")
				.setMAction(1)
				.build();
	    System.out.println("Sending Data");
		BluetoothService.sendMessage(msg);
	}
	
	private void readResponse(PlayerMessage msg)
	{
		int mCommand = msg.getMAction();
		switch(mCommand)
		{
			case PlayerCommands.PLAY_CARD:
			{
				PlayerUtils.sendCardToServer();
				break;
			}
			case PlayerCommands.RECEIVE_CARD:
			{
				PlayerUtils.addCard(msg);
				break;
			}
			default:
				break;
		}
	}
	
	public static void sendMessage(PlayerMessage msg)
	{
		//Ensures that the bluetooth server is open to receive response
		if(!mServer.isServerOnline())
    	{
    		Log.d(TAG, "Initalizing server");
    		mServer = new BluetoothServer(serverHandler, mAdapter);
    		mServer.start();
    	}
		
		byte[] outbuffer = msg.toByteArray();
		mClient.send(outbuffer, mTv);
	}
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

}
