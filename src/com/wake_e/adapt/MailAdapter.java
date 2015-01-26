package com.wake_e.adapt;

import java.util.List;
import com.wake_e.MainActivity;
import com.wake_e.R;
import com.wake_e.model.Mail;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MailAdapter extends BaseAdapter {
	List<Mail> mails;
	LayoutInflater inflater;
    int index;
    ViewHolder holder;

	public MailAdapter(Context context, List<Mail> mails) {
		super();
	    inflater = LayoutInflater.from(context);
	    this.mails = mails;
	}

	@Override
	public boolean isEnabled(int position) {
	    return false;
	}

	@Override
	public int getCount() {
		return mails.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mails.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	private class ViewHolder {
	    TextView subject;
	    TextView from;
	    TextView content;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    if(convertView == null) {
	        holder = new ViewHolder();
	        convertView = inflater.inflate(R.layout.mail, null);
	    }

	        holder.from = (TextView)convertView.findViewById(R.id.mailFrom);
	        holder.from.setText(mails.get(position).getSender());
	        
	        holder.subject 	= (TextView)convertView.findViewById(R.id.mailSubject);
	        holder.subject.setText(mails.get(position).getSubject());

	        holder.content 	= (TextView)convertView.findViewById(R.id.mailBody);
	        holder.content.setText(Html.fromHtml(mails.get(position).getContent()));

	        holder.subject.setTypeface(MainActivity.future);
	        holder.from.setTypeface(MainActivity.future);
	        holder.content.setTypeface(MainActivity.future);

	        convertView.setTag(holder);
	        holder = (ViewHolder) convertView.getTag();
	    return convertView;
	}

}
