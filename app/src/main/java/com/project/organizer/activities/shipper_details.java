package com.project.organizer.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.organizer.R;
import com.project.organizer.database.DatabaseService;
import com.project.organizer.helper.OverviewService;
import com.project.organizer.helper.adapter.ShipperListViewAdapter;
import com.project.organizer.helper.adapter.TraderListViewAdapter;
import com.project.organizer.model.shipper.ManualShipper;
import com.project.organizer.model.shipper.Shipper;
import com.project.organizer.model.shipper.ShippingStatus;
import com.project.organizer.model.trader.ManualTrader;
import com.project.organizer.model.trader.OrderStatus;
import com.project.organizer.model.trader.Trader;

import java.util.ArrayList;

public class shipper_details extends AppCompatActivity {
    Shipper currentShipper;
    boolean isCreateMode = false;
    boolean isManagedMode = false;
    EditText text_shipper_name;
    String identifier;


    Button btn_new_shipper;
    TextView tv_shipper_name;
    TextView tv_state_shipper;
    TextView tv_state_possible_shipper;
    Button btn_remove_state_shipper;
    Button btn_add_state_shipper;
    EditText text_new_state_shipper;
    ListView shipper_listview;
    LinearLayout ll_state_shipper;
    ShipperListViewAdapter lvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_details);

        btn_new_shipper = findViewById(R.id.btn_new_shipper);
        text_shipper_name = findViewById(R.id.text_shipper_name);
         tv_shipper_name = findViewById(R.id.tv_shipper_name);
         tv_state_shipper = findViewById(R.id.tv_state_shipper);
         tv_state_possible_shipper = findViewById(R.id.tv_state_possible_shipper);
         btn_remove_state_shipper = findViewById(R.id.btn_remove_state_shipper);
         btn_add_state_shipper = findViewById(R.id.btn_add_state_shipper);
         text_new_state_shipper = findViewById(R.id.text_new_state_shipper);
         shipper_listview = findViewById(R.id.shipper_listview);
         ll_state_shipper = findViewById(R.id.ll_state_shipper);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String action = extras.getString("ACTION");

            if (action == null || action.equals("CREATE")) {
                isCreateMode = true;
            } else {
                isCreateMode = false;

                identifier = extras.getString("IDENTITY");
                if (identifier != null) {
                    loadData();
                }
            }
        } else {
            isCreateMode = true;
        }

        btn_new_shipper.setOnClickListener(this::saveData);

        loadData();

        btn_add_state_shipper.setOnClickListener(this::addState);
        btn_remove_state_shipper.setOnClickListener(this::removeState);

    }

    private void addState(View view) {
        ShippingStatus newState = new ShippingStatus(0, text_new_state_shipper.getText().toString());
        text_new_state_shipper.setText("");
        lvAdapter.addList(newState);
    }

    private void removeState(View view) {
        lvAdapter.removeList();
    }


    private void saveData(View view) {

        if (text_shipper_name.getText().toString() != "") {
            if (isCreateMode) {
                currentShipper = new ManualShipper(0, text_shipper_name.getText().toString(), lvAdapter.getList());
            } else {
                currentShipper = new ManualShipper(currentShipper.getShipperId(), text_shipper_name.getText().toString(), lvAdapter.getList());
            }

            try {
                DatabaseService.getInstance().saveShipper(currentShipper);
                Intent i = new Intent(shipper_details.this, overview_shipper.class);
                startActivity(i);
            } catch (Exception e) {
                Toast.makeText(this, R.string.save_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, R.string.missing_name, Toast.LENGTH_LONG).show();
        }
    }

    private void loadData()
    {
        if (isCreateMode) {
            setTitle(R.string.headline_shipper_create);

            lvAdapter = new ShipperListViewAdapter(this, new ArrayList<>());
            shipper_listview.setAdapter(lvAdapter);
            shipper_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    lvAdapter.setPos(position);
                }
            });
        } else {
            try {
                currentShipper = OverviewService.getShipper(Integer.parseInt(identifier));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (currentShipper != null) {
                if (currentShipper.isApi()) {
                    isManagedMode = true;
                    setTitle(R.string.headline_shipper_show);
                    loadManagedMode();
                } else {
                    isManagedMode = false;
                    setTitle(R.string.headline_shipper_change);
                    loadManualMode();
                }
            } else {
                Toast.makeText(this, R.string.load_shipper_failure, Toast.LENGTH_SHORT).show();
                setTitle(R.string.headline_shipper_create);
            }
        }
        setVisibleMode();
    }

    private void setVisibleMode() {
        if (isManagedMode) {
            tv_state_shipper.setVisibility(View.GONE);
            tv_state_possible_shipper.setVisibility(View.GONE);
            tv_shipper_name.setVisibility(View.GONE);
            text_new_state_shipper.setVisibility(View.GONE);
            ll_state_shipper.setVisibility(View.GONE);
            btn_remove_state_shipper.setVisibility(View.GONE);
            btn_add_state_shipper.setVisibility(View.GONE);
            btn_new_shipper.setVisibility(View.GONE);
        }
    }

    private void loadManualMode() {
        text_shipper_name.setText(currentShipper.getName());
        lvAdapter = new ShipperListViewAdapter(this, (ArrayList<ShippingStatus>) ((ManualShipper) currentShipper).getShipperStatusList());
        shipper_listview.setAdapter(lvAdapter);
        shipper_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                lvAdapter.setPos(position);
            }
        });
    }

    private void loadManagedMode() {
        text_shipper_name.setText(currentShipper.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    if (currentShipper != null) {
                        try {
                            currentShipper.setActive(false);
                            DatabaseService.getInstance().saveShipper(currentShipper);
                            Intent i = new Intent(shipper_details.this, overview_shipper.class);
                            startActivity(i);
                        } catch (Exception e) {
                            Toast.makeText(shipper_details.this, R.string.delete_error, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(shipper_details.this, R.string.not_saved, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.rly_delete).setPositiveButton(R.string.yes, dialogClickListener)
                .setNegativeButton(R.string.no, dialogClickListener).show();
        return super.onOptionsItemSelected(item);
    }
}