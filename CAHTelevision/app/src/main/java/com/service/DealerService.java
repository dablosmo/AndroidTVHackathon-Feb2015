package com.service;

import java.util.*;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.sdkcdg.proto.PlayerCommands;

import com.sdkcdg.bluetooth.BluetoothClient;
import com.sdkcdg.bluetooth.BluetoothServer;
import com.sdkcdg.bluetooth.MessageType;
import com.sdkcdg.proto.PlayerProto.PlayerMessage;
import com.sdkcdg.proto.CardsProto.CardsMessage;

import com.google.protobuf.InvalidProtocolBufferException;

public class DealerService extends Service
{
	private static BluetoothClient mClient;
	private static BluetoothServer mServer;
	private static Handler serverHandler;
	private static Handler clientHandler;
	private static BluetoothAdapter mAdapter;
	private static BluetoothDevice mTv;
	private static final String TAG = "CAH TV Bluetooth Service";

    String BlackCard1 = "After I moved in with my significant other I learned they had a secret obsession with ______?";
    String BlackCard2 = "I don't want to live in a world without______";
    String BlackCard3 = "If it weren't for modern medicine, how would you have died?";

    String WhiteCard1 = "Family Guy";
    String WhiteCard2 = "Jewelery";
    String WhiteCard3 = "Donuts";
    String WhiteCard4 = "Brown People";
    String WhiteCard5 = "Bubonic Plague";
    String WhiteCard6 = "Lack of Hair";
    String WhiteCard7 = "Julia Roberts";
    String WhiteCard8 = "Robert Downey Jr.";
    String WhiteCard9 = "Guitar Hero";
    String WhiteCard10 = "Cheese";
	
	@Override
	public void onCreate()
	{
        System.out.println("Service successfully started.");
		mAdapter = BluetoothAdapter.getDefaultAdapter();
        mAdapter.getRemoteDevice("40:78:6A:52:89:BB").createBond();
        mAdapter.getRemoteDevice("E4:12:1D:8B:8F:B5").createBond();
	    mTv = null;//mAdapter.getRemoteDevice(address);
		serverHandler = new Handler() 
		{
	        @Override
	        public void handleMessage(Message message) 
	        {
	            switch (message.what) 
	            {
		            case MessageType.DATA_RECEIVED:
		            {
		            	Toast.makeText(DealerService.this, "Data Received, Now Parsing", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(DealerService.this, "Data was sent successfully", Toast.LENGTH_SHORT).show();
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

        setBlackCard(BlackCard1, "David");
        setBlackCard(BlackCard1, "Garland");
        sendCard(WhiteCard1, "David");
        sendCard(WhiteCard2, "David");
        sendCard(WhiteCard3, "David");
        sendCard(WhiteCard4, "David");
        sendCard(WhiteCard5, "David");
        sendCard(WhiteCard6, "Garland");
        sendCard(WhiteCard7, "Garland");
        sendCard(WhiteCard8, "Garland");
        sendCard(WhiteCard9, "Garland");
        sendCard(WhiteCard10, "Garland");
	}
	
	private void readResponse(PlayerMessage msg)
	{
		int mCommand = msg.getMAction();
		switch(mCommand)
		{
			case PlayerCommands.PLAY_CARD: {
                Toast.makeText(getApplicationContext(), "Play Card Recieved", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Play Card Recieved");
                PlayerMessage playerMessage = null;
                try {
                    playerMessage = PlayerMessage.parseFrom(msg.getMessageByteString());
                } catch (InvalidProtocolBufferException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                CardsMessage cardsMessage = null;
                try {
                    CardsMessage.parseFrom(playerMessage.getMessageByteString());
                } catch (InvalidProtocolBufferException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                String cardMessage = cardsMessage.getFirstCard();

                break;
            }
            default:
				break;
		}
	}

    public void setBlackCard(String CardMessage, String PlayerName) {

        CardsMessage cardMsg = CardsMessage.newBuilder()
                .setFirstCard(CardMessage)
                .build();

        PlayerMessage playerMsg = PlayerMessage.newBuilder()
                .setMName(PlayerName)
                .setMAction(2)
                .setMessageByteString(cardMsg.toByteString())
                .build();

        sendMessage(playerMsg, PlayerName);
    }

    public void sendCard(String CardMessage, String PlayerName) {
        CardsMessage cardMsg = CardsMessage.newBuilder()
                .setFirstCard(CardMessage)
                .build();

        PlayerMessage playerMsg = PlayerMessage.newBuilder()
                .setMName(PlayerName)
                .setMAction(1)
                .setMessageByteString(cardMsg.toByteString())
                .build();

        sendMessage(playerMsg, PlayerName);
    }
	
	public static void sendMessage(PlayerMessage msg, String Name)
	{
		//Ensures that the bluetooth server is open to receive response
		if(!mServer.isServerOnline())
    	{
    		Log.d(TAG, "Initalizing server");
    		mServer = new BluetoothServer(serverHandler, mAdapter);
    		mServer.start();
    	}

        if(Name.equals("David")) {
            mTv = mAdapter.getRemoteDevice("E4:12:1D:8B:8F:B5");
        } else {
            mTv = mAdapter.getRemoteDevice("40:78:6A:52:89:BB");
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

