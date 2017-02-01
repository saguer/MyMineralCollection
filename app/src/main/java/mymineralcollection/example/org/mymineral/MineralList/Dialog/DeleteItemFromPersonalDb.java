package mymineralcollection.example.org.mymineral.MineralList.Dialog;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;

import mymineralcollection.example.org.mymineral.AddMineral.StaticDataClass.PathObject;
import mymineralcollection.example.org.mymineral.Class.Constant;
import mymineralcollection.example.org.mymineral.MineralList.ExtractJsonNew;
import mymineralcollection.example.org.mymineral.SQLiteDatabases.PersonalDatabaseHandler;
import mymineralcollection.example.org.myviews.RecycleBen;

/**
 * Created by Santiago on 1/12/2017.
 */
public class DeleteItemFromPersonalDb {
    Context context;

    public DeleteItemFromPersonalDb(Context _context) {
        this.context = _context;
    }



    public void dialogEraseFromDbAtId(final int position, final RecycleBen bean){

        //Toast.makeText(context, "Long click! \n MineralListActivity \n"+bean.getDescription(), Toast.LENGTH_SHORT).show();

        new MaterialDialog.Builder(context)
                .title("Are You Sure You Want to Erase?"+"\n"+bean.getDescription())
                //.content("TEST")
                .positiveText("Delete")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mOnEventListener.dialogPositivePositionOnClick(position);

                        int id = bean.getTableId();
                        eraseFromDbAtID(id);
                    }
                })
                .show();
    }
    public void dialogEraseFromDbAtId(String description, final int id){

        //Toast.makeText(context, "Long click! \n MineralListActivity \n"+bean.getDescription(), Toast.LENGTH_SHORT).show();

        new MaterialDialog.Builder(context)
                .title("Are You Sure You Want to Erase?"+"\n"+description)
                //.content("TEST")
                .positiveText("Delete")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        eraseFromDbAtID(id);
                        mOnEventListener.dialogPositiveOnClick();
                    }
                })
                .show();
    }

    public void eraseFromDbAtID(int id){


        //Methods.listFile(context);

        String mineralImgUri = null;
        String userSetInfo = null;

        PersonalDatabaseHandler personal = new PersonalDatabaseHandler(context, Constant.dB_personal_history);

        Cursor row = personal.getData(id);

        if(row.moveToFirst()) {
            mineralImgUri = row.getString(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_pathList));
            userSetInfo = row.getString(row.getColumnIndex(PersonalDatabaseHandler.fieldObject_userSetInfo));
        }

        Log.e("deleteURI", "mineralImgUri:\n"+mineralImgUri);

        //// TODO: 1/14/2017 - When deleting a record we have to erase the pictures that are on the application dir too - DONE
        deleteMineralUriArray(mineralImgUri);

        row.close();

        //PersonalDatabaseHandler personal = new PersonalDatabaseHandler(context, Constant.dB_personal_history);
        //boolean test =
        personal.deleteTitle(id);
        //Toast.makeText(context, id + "\nDeleted? - "+test, Toast.LENGTH_SHORT).show();
        personal.close();
    }

    public static void deleteMineralUriArray(String _mineralImgUri){

        for(PathObject pathObject: ExtractJsonNew.getMineralImgUri(_mineralImgUri)){
            //Log.d("deleteMineralUriArray", "pathObject.getPath() -> " + pathObject.getPath());


            File ImgFile = new File(pathObject.getPath());
            File ThumbFile = new File(pathObject.getThumbPath());

            boolean deleteImgThumbFile = ThumbFile.delete();
            boolean deleteImgFile = ImgFile.delete();

            Log.d("deleteURI", "delete>" +
                    "\nIMG:   "+pathObject.getPath()      + " - "+ deleteImgFile +
                    "\nTHUMP: "+pathObject.getThumbPath() + " - "+ deleteImgThumbFile);

        }

    }

    private mListener mOnEventListener;

    public void setMyDialogOnClickListener(mListener listener) {
        mOnEventListener = listener;
    }



    public interface mListener {
        void dialogPositivePositionOnClick(int position);
        void dialogPositiveOnClick();
    }

}
