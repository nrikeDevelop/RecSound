package project.recsound.modules.main;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import project.recsound.R;
import project.recsound.common.Files;
import project.recsound.model.FileSound;

/**
 * Created by susy on 2/01/17.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.Holder>{

    ArrayList<FileSound> files ;
    Context context;
    ViewAdapter viewAdapter;

    public Adapter(ArrayList<FileSound> files, Context context, ViewAdapter viewAdapter) {
        this.files = files;
        this.context = context;
        this.viewAdapter = viewAdapter;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sound, parent, false);
        Holder holder = new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {

        final FileSound file = files.get(position);
        holder.title.setText(file.getTitle());
        holder.date.setText(file.getDate());
        //FONTS
        Typeface mavenpro_bold = Typeface.createFromAsset(context.getAssets(),"MavenPro-Bold.ttf");
        holder.title.setTypeface(mavenpro_bold);
        holder.date.setTypeface(mavenpro_bold);

        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View viewText = View.inflate(context,R.layout.dialog_text,null);
                final EditText renameFile = (EditText) viewText.findViewById(R.id.dialog_text);

                builder.setView(viewText);
                builder.setTitle(context.getResources().getString(R.string.alert_dialog_rename_title));
                builder.setPositiveButton(context.getResources().getString(R.string.alert_dialog_accept),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String renamed = renameFile.getText().toString();
                                if(renamed.equals("")){
                                    Toast.makeText(context, context.getResources().getString(R.string.toast_edit_text_empty), Toast.LENGTH_SHORT).show();
                                }else{
                                    if(Files.renameFile(file,renamed)){
                                        Toast.makeText(context, context.getResources().getString(R.string.toast_renamed), Toast.LENGTH_SHORT).show();
                                        viewAdapter.refreshList();
                                    }
                                }
                                dialog.dismiss();
                            }
                        });
                builder.setNegativeButton(context.getResources().getString(R.string.alert_dialog_cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create();
                builder.show();

                return  true;
            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Files.playFile(file,context);
            }
        });

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Files.shareFile(context,file);
            }
        });

        holder.eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getResources().getString(R.string.alert_dialog_erase_title));
                builder.setMessage(context.getResources().getString(R.string.alert_dialog_erase_message));
                builder.setPositiveButton(context.getResources().getString(R.string.alert_dialog_yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Erase
                                if(Files.removeFile(file)){
                                    Toast.makeText(context, context.getResources().getString(R.string.toast_delete_true), Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, context.getResources().getString(R.string.toast_delete_false), Toast.LENGTH_SHORT).show();
                                }
                                viewAdapter.refreshList();
                                dialog.dismiss();

                            }
                        });
                builder.setNegativeButton(context.getResources().getString(R.string.alert_dialog_no),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create();
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class Holder extends RecyclerView.ViewHolder{

        RelativeLayout relativeLayout;
        TextView title;
        TextView date;
        ImageButton eraseButton;
        ImageButton shareButton;

        public Holder(View itemView) {
            super(itemView);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.item_relative_layout);
            title = (TextView) itemView.findViewById(R.id.item_title);
            date = (TextView) itemView.findViewById(R.id.item_date);
            eraseButton = (ImageButton) itemView.findViewById(R.id.item_erase_button);
            shareButton = (ImageButton) itemView.findViewById(R.id.item_share_button);

        }
    }

}
