package com.project.organizer.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.organizer.R;
import com.project.organizer.database.DatabaseService;
import com.project.organizer.helper.OverviewService;
import com.project.organizer.helper.adapter.OrderStateSpinnerAdapter;
import com.project.organizer.helper.adapter.ShipperSpinnerAdapter;
import com.project.organizer.helper.adapter.ShippingStateSpinnerAdapter;
import com.project.organizer.helper.adapter.TraderSpinnerAdapter;
import com.project.organizer.model.Order;
import com.project.organizer.model.shipper.ManualShipper;
import com.project.organizer.model.shipper.Shipper;
import com.project.organizer.model.shipper.ShippingStatus;
import com.project.organizer.model.trader.ManualTrader;
import com.project.organizer.model.trader.OrderStatus;
import com.project.organizer.model.trader.Trader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class order_details extends AppCompatActivity {

    boolean isCreateMode = false;
    boolean isManagedMode = false;
    Order currentOrder;
    EditText text_order_nr;
    Spinner spinner_order_trader;
    Spinner spinner_order_shipper;
    Spinner spinner_order_state;
    Spinner spinner_shipping_state;
    Button btn_save_order;
    String orderId;
    EditText order_number;
    EditText order_date;
    EditText shipping_number;
    TextView tv_text_order_status;
    TextView tv_order_trader;
    TextView tv_order_shipper;
    TextView tv_order_date;
    TextView tv_order_number;
    TextView tv_shipping_number;
    TextView tv_shipping_state;
    TextView tv_order_state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        text_order_nr = findViewById(R.id.text_order_nr);
        spinner_order_trader = findViewById(R.id.spinner_order_trader);
        spinner_order_shipper = findViewById(R.id.spinner_order_shipper);
        btn_save_order = findViewById(R.id.btn_save_order);
        order_number = findViewById(R.id.order_number);
        order_date = findViewById(R.id.order_date);
        shipping_number = findViewById(R.id.shipping_number);
        spinner_order_state = findViewById(R.id.spinner_order_state);
        spinner_shipping_state = findViewById(R.id.spinner_shipping_state);
        tv_text_order_status = findViewById(R.id.tv_text_order_status);
        tv_order_trader = findViewById(R.id.tv_order_trader);
        tv_order_shipper = findViewById(R.id.tv_order_shipper);
        tv_order_date = findViewById(R.id.tv_order_date);
        tv_order_number = findViewById(R.id.tv_order_number);
        tv_shipping_number = findViewById(R.id.tv_shipping_number);
        tv_shipping_state = findViewById(R.id.tv_shipping_state);
        tv_order_state = findViewById(R.id.tv_order_state);


        order_date.setOnClickListener(this::setDate);

        order_date.setFocusable(false);
        order_date.setClickable(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String action = extras.getString("ACTION");
            if (action == null || action.equals("CREATE")) {
                isCreateMode = true;
            } else {
                orderId = extras.getString("IDENTITY");
                if (orderId != null) {
                    isCreateMode = false;
                } else {
                    isCreateMode = true;
                }
            }
        } else {
            isCreateMode = true;
        }
        loadData();
        btn_save_order.setOnClickListener(this::click_SaveBtn);
    }

    private void setDate(View view) {
        View d_view = View.inflate(order_details.this, R.layout.date_time_picker, null);
        AlertDialog alert_dialog = new AlertDialog.Builder(order_details.this).create();

        d_view.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = (DatePicker) d_view.findViewById(R.id.date_picker);
                order_date.setText(datePicker.getDayOfMonth() + "." + datePicker.getMonth() + "." + datePicker.getYear());
                alert_dialog.dismiss();
            }
        });
        alert_dialog.setView(d_view);
        alert_dialog.show();
    }

    private void click_SaveBtn(View view) {

        LocalDateTime date;
        try {
            date = LocalDateTime.of(Integer.parseInt(order_date.getText().toString().split("\\.")[2]), Month.of(Integer.parseInt(order_date.getText().toString().split("\\.")[1])), Integer.parseInt(order_date.getText().toString().split("\\.")[0]), 0, 0);
        } catch (Exception ex) {
            Toast.makeText(this, R.string.date_incorrect, Toast.LENGTH_LONG).show();
            return;
        }
        if (isCreateMode) {
            currentOrder = new Order(0, false, text_order_nr.getText().toString(), date, ((ShipperSpinnerAdapter) spinner_order_shipper.getAdapter()).getItem(), shipping_number.getText().toString(), ((ShippingStateSpinnerAdapter) spinner_shipping_state.getAdapter()).getItem(), ((TraderSpinnerAdapter) spinner_order_trader.getAdapter()).getItem(), order_number.getText().toString(), ((OrderStateSpinnerAdapter) spinner_order_state.getAdapter()).getItem());
        } else {
            currentOrder = new Order(currentOrder.getId(), false, text_order_nr.getText().toString(), date, ((ShipperSpinnerAdapter) spinner_order_shipper.getAdapter()).getItem(), shipping_number.getText().toString(), ((ShippingStateSpinnerAdapter) spinner_shipping_state.getAdapter()).getItem(), ((TraderSpinnerAdapter) spinner_order_trader.getAdapter()).getItem(), order_number.getText().toString(), ((OrderStateSpinnerAdapter) spinner_order_state.getAdapter()).getItem());
        }
        try {
            OverviewService.saveOrder(currentOrder);
            Intent i = new Intent(order_details.this, overview.class);
            startActivity(i);
        } catch (Exception ex) {
            Toast.makeText(this, R.string.save_error, Toast.LENGTH_LONG).show();
        }
    }

    private void loadData() {

        if (isCreateMode) {
            loadManualMode();
            setTitle(R.string.headline_order_create);
        } else {
            DatabaseService databaseService = DatabaseService.getInstance();
            try {
                currentOrder = databaseService.getOrder(Integer.parseInt(orderId));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (currentOrder != null) {
                if (currentOrder.getTrader().isApi()) {
                    isManagedMode = true;
                    setTitle(R.string.headline_order_show);
                    loadManagedMode();
                } else {
                    isManagedMode = false;
                    setTitle(R.string.headline_order_change);
                    loadManualMode();
                }
            } else {
                Toast.makeText(this, R.string.load_order_failure, Toast.LENGTH_SHORT).show();
                setTitle(R.string.headline_order_create);
                loadManualMode();
            }
        }
        setVisibleMode();
    }

    private void loadManualMode() {
        setSpinner();
        if (!isCreateMode) {
            text_order_nr.setText(currentOrder.getNote());
            spinner_order_trader.setSelection(((TraderSpinnerAdapter) spinner_order_trader.getAdapter()).getPosition(currentOrder.getTrader().getTraderId()));
            spinner_order_shipper.setSelection(((ShipperSpinnerAdapter) spinner_order_shipper.getAdapter()).getPosition(currentOrder.getShipper().getShipperId()));
            order_date.setText(currentOrder.getDate().getDayOfMonth() + "." + currentOrder.getDate().getMonth().getValue() + "." + currentOrder.getDate().getYear());
            order_number.setText(currentOrder.getOrdernumber());
            shipping_number.setText(currentOrder.getTrackingnumber());
        }
    }

    private void loadManagedMode() {
        tv_text_order_status.setText(currentOrder.getNote());
        tv_order_trader.setText(currentOrder.getTrader().getName());
        tv_order_shipper.setText(currentOrder.getShipper().getName());
        tv_order_date.setText(currentOrder.getDate().getDayOfMonth() + "." + currentOrder.getDate().getMonth().getValue() + "." + currentOrder.getDate().getYear());
        tv_order_number.setText(currentOrder.getOrdernumber());
        tv_shipping_number.setText(currentOrder.getTrackingnumber());
        tv_shipping_state.setText(currentOrder.getShippingStatus().getName());
        tv_order_state.setText(currentOrder.getOrderStatus().getName());
    }

    private void setVisibleMode() {
        if (isManagedMode) {
            text_order_nr.setVisibility(View.GONE);
            spinner_order_trader.setVisibility(View.GONE);
            spinner_order_shipper.setVisibility(View.GONE);
            spinner_order_state.setVisibility(View.GONE);
            spinner_shipping_state.setVisibility(View.GONE);
            order_date.setVisibility(View.GONE);
            order_number.setVisibility(View.GONE);
            shipping_number.setVisibility(View.GONE);
            btn_save_order.setVisibility(View.GONE);
        } else {
            tv_text_order_status.setVisibility(View.GONE);
            tv_order_trader.setVisibility(View.GONE);
            tv_order_shipper.setVisibility(View.GONE);
            tv_order_date.setVisibility(View.GONE);
            tv_order_number.setVisibility(View.GONE);
            tv_shipping_number.setVisibility(View.GONE);
            tv_shipping_state.setVisibility(View.GONE);
            tv_order_state.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_order_mail, menu);
        inflater.inflate(R.menu.toolbar_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mail_trader) {
            sendMail();
        } else if (id == R.id.delete) {
            deleteOrder();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendMail() {
        if (currentOrder != null && currentOrder.getTrader().getMail() != null) {
            Intent i = new Intent(Intent.ACTION_SEND);
            try {
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{currentOrder.getTrader().getMail()});
                i.putExtra(Intent.EXTRA_SUBJECT, R.string.order_request + currentOrder.getOrdernumber());
                startActivity(Intent.createChooser(i, ""));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, R.string.no_mail_client, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, R.string.not_able_mail, Toast.LENGTH_SHORT).show();
            }
            startActivity(i);
        } else {
            Toast.makeText(this, R.string.no_mail_adress, Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteOrder() {
        if (isManagedMode) {
            Toast.makeText(this, R.string.managed_not_editable, Toast.LENGTH_LONG).show();
        } else {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_POSITIVE) {
                        if (currentOrder != null) {
                            try {
                                currentOrder.setClosed(true);
                                OverviewService.saveOrder(currentOrder);
                                Intent i = new Intent(order_details.this, overview.class);
                                startActivity(i);
                            } catch (Exception e) {
                                Toast.makeText(order_details.this, R.string.delete_error, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(order_details.this, R.string.not_saved, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.rly_delete).setPositiveButton(R.string.yes, dialogClickListener)
                    .setNegativeButton(R.string.no, dialogClickListener).show();
        }
    }


    private void setSpinner() {
        ArrayList<Trader> list_trader = new ArrayList<Trader>();
        ArrayList<Shipper> list_shipper = new ArrayList<Shipper>();

        DatabaseService databaseService = DatabaseService.getInstance();
        try {
            list_trader = (ArrayList) databaseService.getTrader();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            list_shipper = (ArrayList) databaseService.getShipper();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (list_trader.size() > 0) {
            TraderSpinnerAdapter trader_adapter = new TraderSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, list_trader);
            spinner_order_trader.setAdapter(trader_adapter);
            spinner_order_trader.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view,
                                           int position, long id) {
                    ((TraderSpinnerAdapter) spinner_order_trader.getAdapter()).setPos(position);
                    setSpinnerOrderState(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        if (list_shipper.size() > 0) {
            ShipperSpinnerAdapter shipper_adapter = new ShipperSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, list_shipper);
            spinner_order_shipper.setAdapter(shipper_adapter);
            spinner_order_shipper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view,
                                           int position, long id) {
                    ((ShipperSpinnerAdapter) spinner_order_shipper.getAdapter()).setPos(position);
                    setSpinnerShippingState(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void setSpinnerOrderState(int position) {
        Trader trader = ((TraderSpinnerAdapter) spinner_order_trader.getAdapter()).getItem(position);
        ArrayList<OrderStatus> order_status_list = new ArrayList<>();
        if(trader instanceof ManualTrader){
            ManualTrader manualTrader = (ManualTrader)trader;
            order_status_list = (ArrayList<OrderStatus>) manualTrader.getOrderStatusList();
        }
        OrderStateSpinnerAdapter orderStateSpinnerAdapter = new OrderStateSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, order_status_list);
        spinner_order_state.setAdapter(orderStateSpinnerAdapter);
        spinner_order_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                ((OrderStateSpinnerAdapter) spinner_order_state.getAdapter()).setPos(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (currentOrder != null && currentOrder.getOrderStatus() != null) {
            spinner_order_state.setSelection(orderStateSpinnerAdapter.getPosition(currentOrder.getOrderStatus().getId()));
        }
    }

    private void setSpinnerShippingState(int position) {
        ManualShipper shipper = (ManualShipper) ((ShipperSpinnerAdapter) spinner_order_shipper.getAdapter()).getItem(position);
        ArrayList<ShippingStatus> shipping_status_list = (ArrayList<ShippingStatus>) shipper.getShipperStatusList();
        ShippingStateSpinnerAdapter shippingStateSpinnerAdapter = new ShippingStateSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, shipping_status_list);
        spinner_shipping_state.setAdapter(shippingStateSpinnerAdapter);
        spinner_shipping_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                ((ShippingStateSpinnerAdapter) spinner_shipping_state.getAdapter()).setPos(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (currentOrder != null && currentOrder.getShippingStatus() != null) {
            int new_position = shippingStateSpinnerAdapter.getPosition(currentOrder.getShippingStatus().getId());
            if (new_position != -1) {
                spinner_shipping_state.setSelection(new_position);
            }
        }
    }
}