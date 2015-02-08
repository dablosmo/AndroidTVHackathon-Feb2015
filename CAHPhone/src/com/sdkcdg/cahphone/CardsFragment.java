package com.sdkcdg.cahphone;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cahphone.R;

/**
 * Created by schhan on 2/7/15.
 */
public class CardsFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.cards_fragment, parent, false);
		
		final TextView card = (TextView) v.findViewById(R.id.tv_card);
		card.setText(PlayerUtils.mHand.get(PlayerUtils.mCounter).getFirstCard());
		card.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				PlayerUtils.selectCards(PlayerUtils.mCounter);
			}
		});

		Button next = (Button) v.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) 
			{
				if(PlayerUtils.mCounter < PlayerUtils.mHand.size()-1)
				{
					PlayerUtils.mCounter++;
					card.setText(PlayerUtils.mHand.get(PlayerUtils.mCounter).getFirstCard());
				}
			}
		});

		Button last = (Button) v.findViewById(R.id.last);
		last.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(PlayerUtils.mCounter > 0 )
				{
					PlayerUtils.mCounter--;
					card.setText(PlayerUtils.mHand.get(PlayerUtils.mCounter).getFirstCard());
				}
			}
		});

		return v;
	}
}
