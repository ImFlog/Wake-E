package com.wake_e.adapt;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import com.wake_e.MainActivity;
import com.wake_e.R;
import com.wake_e.model.Meteo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MeteoAdapter extends BaseAdapter {
	List<Meteo> meteos;
	LayoutInflater inflater;
    int index;
    ViewHolder holder;

	public MeteoAdapter(Context context,List<Meteo> meteos) {
	    inflater = LayoutInflater.from(context);
	    this.meteos = meteos;
	}
	public void setTasks(List<Meteo> meteos){
		this.meteos = meteos;
	}
	@Override
	public int getCount() {
		return meteos.size();
	}

	@Override
	public Object getItem(int arg0) {
		return meteos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	private class ViewHolder {
	    TextView city;
	    TextView temp;
	    TextView humi;
	    TextView wind;
	    ImageView icon;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		


	    if(convertView == null) {
	        holder = new ViewHolder();
	        convertView = inflater.inflate(R.layout.meteo, null);
	        
	        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.FRANCE);
	        otherSymbols.setDecimalSeparator('.');
	        DecimalFormat df = new DecimalFormat ();
	        df.setDecimalFormatSymbols(otherSymbols);
	        df.setMaximumFractionDigits ( 2 ) ;
	        df.setMinimumFractionDigits ( 2 ) ; 
	        df.setDecimalSeparatorAlwaysShown ( true ) ; 
//	        System.out.println ( Double.parseDouble(df.format(-4.327)));

	        holder.city 	= (TextView)convertView.findViewById(R.id.city);
	        holder.temp 	= (TextView)convertView.findViewById(R.id.temperature);
	        holder.humi 	= (TextView)convertView.findViewById(R.id.humidity);
	        holder.wind 	= (TextView)convertView.findViewById(R.id.wind);
	        holder.icon 	= (ImageView)convertView.findViewById(R.id.icon);
	        
	        
	        holder.city.setTypeface(MainActivity.future);
	        holder.temp.setTypeface(MainActivity.future);
	        holder.humi.setTypeface(MainActivity.future);
	        holder.wind.setTypeface(MainActivity.future);
	        
	        holder.city.setText(meteos.get(position).getCity());
	        holder.temp.setText(
	        		Double.parseDouble(df.format (
	        				meteos.get(position).getTemperature()))
	        		+ " °C");
	        holder.humi.setText("Humidité " + 
	        		Double.parseDouble(df.format (
	        				meteos.get(position).getHumidite()))
	        		+ " %");
	        holder.wind.setText("Vent " + Double.parseDouble(df.format (
	        		meteos.get(position).getWindStrength()))
	        		+ " hm/h");
	        holder.icon.setImageResource(convertView.getResources().getIdentifier("d" + meteos.get(position).getIcon(), "drawable", MainActivity.that.getPackageName()));
	        holder.icon 	= (ImageView)convertView.findViewById(R.id.icon);
	        convertView.setTag(holder);
	    } else {
	        holder = (ViewHolder) convertView.getTag();
	    }
	    return convertView;
	}

}
