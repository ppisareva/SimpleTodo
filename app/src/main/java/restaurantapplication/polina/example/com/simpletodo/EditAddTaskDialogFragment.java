package restaurantapplication.polina.example.com.simpletodo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditAddTaskDialogFragment extends DialogFragment {
    String text;
    int idTask;
    String date;
    int priority;
    int completion;
    int position = -1;
    EditText etName;
    DatePicker dpTask;
    Spinner spTask;
    CheckBox cbTask;
    DatabaseHelper dbHelper;
    Boolean isEdit = false;
    public static final String POSITION_TASK = "position";
    EditAddTaskDialogListener  mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (EditAddTaskDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    interface EditAddTaskDialogListener{
        void onDialogPositiveClick(Task task, int position);
        void onDialogNegativeClick(DialogFragment dialog);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(getContext());
       Bundle bundle =  getArguments();
        if(bundle!=null){
            isEdit=true;
            position = bundle.getInt(POSITION_TASK);
            idTask = bundle.getInt(DatabaseHelper.KEY_ID);
            text = bundle.getString(DatabaseHelper.NAME);
            date = bundle.getString(DatabaseHelper.DATE);
            priority = bundle.getInt(DatabaseHelper.PRIORITY);
            completion = bundle.getInt(DatabaseHelper.COMPLETION);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View content = inflater.inflate(R.layout.edit_add_task_dialog, null);
        builder.setView(content).setTitle(isEdit ? R.string.edit_task : R.string.new_task)
                .setPositiveButton(isEdit?R.string.edit : R.string.create, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        text = etName.getText().toString();
                        date = getDateFromDatePicker(dpTask);
                        Task task = new Task();
                        if(isEdit){
                             task = new Task(text, date, priority, completion);
                            dbHelper.updateTask(task);
                        } else {
                        task = new Task(text, date, priority, completion);
                        idTask = dbHelper.addItem(task);
                        task.setId(idTask);
                        }
                        mListener.onDialogPositiveClick(task, position);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(EditAddTaskDialogFragment.this);
                    }
                });
        iniView(content);
        return builder.create();
    }

    private void iniView(View content) {
        etName = (EditText) content.findViewById(R.id.etNameTaskDialog);
        dpTask = (DatePicker)content.findViewById(R.id.dpTask);
        spTask = (Spinner) content.findViewById(R.id.spinnerPriority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTask.setAdapter(adapter);
        spTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priority = position+1;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        cbTask = (CheckBox)content.findViewById(R.id.cbCompletion);
        cbTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                completion = isChecked?1:0;
            }
        });

        if(isEdit){
            etName.setText(text);
            initDataPicker(dpTask, date);
            spTask.setSelection(priority-1);
            cbTask.setChecked(completion==1?true:false);
        }
            }

    private void initDataPicker(DatePicker dpTask, String date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(SystemClock.currentThreadTimeMillis());
        try {
            d = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        dpTask.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH), null);

    }

    public static EditAddTaskDialogFragment newInstance(Task task, int position) {
        EditAddTaskDialogFragment fragment = new EditAddTaskDialogFragment();
        Bundle args = new Bundle();
        args.putInt(POSITION_TASK, position);
        args.putInt(DatabaseHelper.KEY_ID, task.getId());
        args.putString(DatabaseHelper.NAME, task.getTaskName());
        args.putString(DatabaseHelper.DATE, task.getDueDate());
        args.putInt(DatabaseHelper.PRIORITY, task.getTaskPriority());
        args.putInt(DatabaseHelper.COMPLETION, task.isTaskCompletion());
        fragment.setArguments(args);
        return fragment;
    }

    public  String getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateResult = df.format(date);
        return dateResult;
    }
}
