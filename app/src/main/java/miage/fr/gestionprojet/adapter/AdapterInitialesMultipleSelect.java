package miage.fr.gestionprojet.adapter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;
import java.util.List;

import miage.fr.gestionprojet.R;
import miage.fr.gestionprojet.models.Ressource;

/**
 * Created by lloison on 12/04/2018.
 */

public class AdapterInitialesMultipleSelect  extends ArrayAdapter<Ressource> {
    private List<Ressource> lstInitiales;
    private AppCompatActivity activity;
    private List<String> emailSelected;

    public AdapterInitialesMultipleSelect(AppCompatActivity context, int resource, List<Ressource> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.lstInitiales = objects;
        emailSelected = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return lstInitiales.size();
    }

    @Override
    public Ressource getItem(int position) {
        return lstInitiales.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterInitialesMultipleSelect.ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // on récupère la vue à laquelle doit être ajouter l'image
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lst_view_users, parent, false);
            holder = new AdapterInitialesMultipleSelect.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AdapterInitialesMultipleSelect.ViewHolder) convertView.getTag();
        }

        //on récupère la première lettre du domaine associé au travail
        final Ressource item = getItem(position);

        if (item != null) {
            String firstLetter = String.valueOf(item.getInitiales());
            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
            // generate random color
            int color = generator.getColor(item);

            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(firstLetter, color); // radius in px

            // on ajoute l'image de l'initial du domaine
            holder.imageView.setImageDrawable(drawable);

            String emailFormate = "(" +  item.getEmail() +  ")";
            holder.emailText.setText(emailFormate);

            holder.checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        if (!item.getEmail().isEmpty() && !getEmailSelected().contains(item.getEmail())) {
                            getEmailSelected().add(item.getEmail());
                        }
                    } else {
                        if (!item.getEmail().isEmpty() && getEmailSelected().contains(item.getEmail())) {
                            getEmailSelected().remove(item.getEmail());
                        }
                    }
                }
            });
        }

        return convertView;
    }

    public List<String> getEmailSelected() {
        return emailSelected;
    }

    private class ViewHolder {
        private ImageView imageView;
        private CheckBox checked;
        private TextView emailText;

        public ViewHolder(View v) {
            imageView = v.findViewById(R.id.icon_ttravail);
            checked = v.findViewById(R.id.checkBox);
            emailText = v.findViewById(R.id.emailText);
        }
    }
}
