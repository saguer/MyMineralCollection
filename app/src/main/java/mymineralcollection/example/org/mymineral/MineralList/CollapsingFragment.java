package mymineralcollection.example.org.mymineral.MineralList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import mymineralcollection.example.org.mymineral.R;

/**
 * Created by Santiago on 12/17/2016.
 */
public class CollapsingFragment extends Fragment {

    private Context context;

    private net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout collapsingToolbarLayout = null;
    private CarouselView carouselView;

    private ArrayList<String> thumbImgArray = new ArrayList<>();

    private CollapsingFragment_interface mInterface;

    private ImageView EditImageView;
    private View view;

    private Toolbar fragmentToolbar;
    private int fragId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.collapsing_layout_fragment, container, false);
        this.view = v;
        context = getActivity();

        try {
            mInterface = (CollapsingFragment_interface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCommunicator");
        }

        EditImageView = (ImageView) v.findViewById(R.id.EditImageView);
        EditImageView.setVisibility(View.INVISIBLE);

        carouselView = (CarouselView) v.findViewById(R.id.carouselView);
        collapsingToolbarLayout = (net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout) v.findViewById(R.id.multiline_collapsing_toolbar);

        fragmentToolbar = (Toolbar) v.findViewById(R.id.myToolbar);

        Bundle args = getArguments();
        if(args != null) {
            fragId = args.getInt("fragId");
        }

        Bundle myArgs = mInterface.setCollapsingFragmentData_interface(fragId).getExtras();

        if(myArgs != null) {

            final int id = myArgs.getInt("tableId",-1);
            final String description = myArgs.getString("Description");
            //String dataText = myArgs.getString("dataText");

            collapsingToolbarLayout.setTitle(description);

            if(myArgs.containsKey("mineralImgUri")) {
                thumbImgArray = myArgs.getStringArrayList("mineralImgUri");
                if (thumbImgArray != null) {
                    carouselView.setPageCount(thumbImgArray.size());
                }
                carouselView.setImageListener(imageListener);
            }

            int color_Int = ContextCompat.getColor(context, R.color.material_deep_orange_A700);

            if(myArgs.containsKey("Color_Int")) {
                color_Int = myArgs.getInt("Color_Int");
            }

            dynamicToolbarColor(color_Int);

            //--- INIT MENU ---
            Drawable myIcon = ContextCompat.getDrawable(context, R.drawable.menu_dots);

            fragmentToolbar.setOverflowIcon(myIcon);
            fragmentToolbar.showOverflowMenu();

            //now ready to  get the menu's method, which is responsible for icons, and change its properties
            Menu menu = fragmentToolbar.getMenu();

            try {
                Method menuMethod = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                menuMethod.setAccessible(true);
                menuMethod.invoke(menu, true);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }

            //now all the other stuff necessary for the toolbar operation
            fragmentToolbar.inflateMenu(R.menu.toolbar_menu);
            fragmentToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem arg0) {

                    switch (arg0.getItemId()) {
                        case R.id.action_edit: {
                            //Toast.makeText(_context, "Edit!", Toast.LENGTH_SHORT).show();
                            mInterface.replaceFragment_interface(fragId);
                            break;
                        }
                        case R.id.action_delete:{
                            mInterface.dialogDeleteItemId(description,id);
                            break;
                        }
                    }
                    return false;
                }
            });
        }
        //--- END INIT MENU ---


        return v;
    }

    public void showEdit(boolean showEdit){
        if(showEdit) {
            EditImageView.setVisibility(View.VISIBLE);
        }else {
            EditImageView.setVisibility(View.INVISIBLE);
        }
    }

    public void showMenu(boolean showMenu){
        if(showMenu) {
            fragmentToolbar.setVisibility(View.VISIBLE);
        }else {
            fragmentToolbar.setVisibility(View.INVISIBLE);
        }
    }


    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            File f = new File(thumbImgArray.get(position));
            Uri uri = Uri.fromFile(f);

            imageView.setImageURI(uri);
        }
    };


    public void dynamicToolbarColor(final int color) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.add_crop);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(color);
                collapsingToolbarLayout.setStatusBarScrimColor(color);
            }
        });
    }


    public interface CollapsingFragment_interface {
        Intent setCollapsingFragmentData_interface(int fragId);

        void replaceFragment_interface(int resId);

        /**
         * When the item is erased go back to the MineralListActivity
         */
        void dialogDeleteItemId(String description, final int id);
    }
}
