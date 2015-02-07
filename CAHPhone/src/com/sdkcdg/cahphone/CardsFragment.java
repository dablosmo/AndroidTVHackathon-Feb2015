package com.sdkcdg.cahphone;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cahphone.R;
import com.sdkcdg.proto.CardsProto.CardsMessage;

/**
 * Created by schhan on 2/7/15.
 */
public class CardsFragment extends Fragment {

	private ArrayList<String> mCards;

	private int mCounter = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		CardsMessage c0 = CardsMessage.newBuilder().setFirstCard("1").build();
		CardsMessage c1 = CardsMessage.newBuilder().setFirstCard("2").build();
		CardsMessage c2 = CardsMessage.newBuilder().setFirstCard("3").build();

		PlayerUtils.mHand.add(c0);
		PlayerUtils.mHand.add(c1);
		PlayerUtils.mHand.add(c2);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.cards_fragment, parent, false);
		
		final TextView card = (TextView) v.findViewById(R.id.tv_card);
		
		card.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PlayerUtils.selectCards(mCounter);
				
			}
		});

		Button next = (Button) v.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(mCounter < PlayerUtils.mHand.size()){
				
					card.setText(PlayerUtils.mHand.get(mCounter).getFirstCard());
					
					mCounter += 1;
					
				} else {
					mCounter = PlayerUtils.mHand.size() - 1;
					
					card.setText(PlayerUtils.mHand.get(mCounter).getFirstCard());
				}
			}
		});

		Button last = (Button) v.findViewById(R.id.last);
		last.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				mCounter -= 1;

				if(mCounter >= 0) {
					
					card.setText(PlayerUtils.mHand.get(mCounter).getFirstCard());
					
					mCounter -= 1;
					
				} else {
					
					mCounter = 0;
					
					card.setText(PlayerUtils.mHand.get(mCounter).getFirstCard());
					
				}
			}
		});

		return v;
	}
}
