package aaac.dirtprosmobileapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//=============================================================================
public class RecordCollectionInfoScreen extends Activity {
//-----------------------------------------------------------------------------
	private final int ADD_BAG_ACTIVITY = 42;
	private final int CONFIRMATION_DIALOG = 27;
//-----------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_collection_info_screen);
	}
//-----------------------------------------------------------------------------
	public void myClickHandler(View view){
		
		Intent nextActivity;
		
		switch(view.getId()){
		case R.id.submit_data_button:
			showDialog(CONFIRMATION_DIALOG);
			break;
		case R.id.add_bag_button:
			nextActivity = new Intent();
            nextActivity.setClassName("aaac.dirtprosmobileapp",
"aaac.dirtprosmobileapp.AddBagScreen");
            startActivityForResult(nextActivity,ADD_BAG_ACTIVITY);
			break;
		default:
			break;
		}
	}
//-----------------------------------------------------------------------------
	public void onActivityResult(int requestCode,int resultCode,Intent data) {
	
		switch(requestCode){
		case ADD_BAG_ACTIVITY:
			
			
			break;
		default:
			break;
		}
	}
//-----------------------------------------------------------------------------
    protected Dialog onCreateDialog(int dialogId) {
	        
	    AlertDialog.Builder dialogBuilder;
	        
	    dialogBuilder = new AlertDialog.Builder(this);
	    switch (dialogId) {
        case CONFIRMATION_DIALOG:
            dialogBuilder.setMessage("Are you sure you want to submit this data?");
            dialogBuilder.setPositiveButton("Confirm Submission",confirmationListener);
            dialogBuilder.setNegativeButton("Cancel Submission",confirmationListener);
            dialogBuilder.setCancelable(false);
            break;
        default:
            break;
        }
        return(dialogBuilder.create());
    }
//-----------------------------------------------------------------------------
    DialogInterface.OnClickListener confirmationListener = 
new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int whatWasClicked) {
            
            switch (whatWasClicked) {
            case DialogInterface.BUTTON_POSITIVE:
                finish();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
            default:
                break;
            }
        }
    };
//-----------------------------------------------------------------------------
}
//=============================================================================