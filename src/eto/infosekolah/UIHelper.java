package eto.infosekolah;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class UIHelper {
	@SuppressWarnings("deprecation")
	public static void showDialog(Context context, String message, String title)
	{
		final AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	            alertDialog.dismiss();  
	          }
	      });
		alertDialog.show();
	}
	
	public static void showDialog(Context context, String message)
	{
		UIHelper.showDialog(context, message, "Information");
	}
	
	public static void showConfirmation(Context context)
	{
		
	}
}
