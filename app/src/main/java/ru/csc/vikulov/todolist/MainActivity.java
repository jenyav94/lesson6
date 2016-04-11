package ru.csc.vikulov.todolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "MainActivity";

    public static final Uri ENTRIES_URI = Uri.withAppendedPath(ToDoListContentProvider.CONTENT_URI, "entries");

    private static final String SORT_ORDER = FeedsTable.COLUMN_DONE + " , "
            + FeedsTable.COLUMN_DATE + " ASC , "
            + FeedsTable.COLUMN_PRIOR + " DESC";

    private static final String FALSE = "FALSE";

    private Button addButton;
    private EditText addEditText;
    private ListView listViewTasks;
    private TaskListAdapter cursorAdapterListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewTasks = (ListView) findViewById(R.id.lvTasks);
        cursorAdapterListView = new TaskListAdapter(this, null, 0);
        listViewTasks.setAdapter(cursorAdapterListView);

        addButton = (Button) findViewById(R.id.add_button);
        addEditText = (EditText) findViewById(R.id.add_editText);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                SimpleDateFormat format = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());

                values.put(FeedsTable.COLUMN_CONTENT, String.valueOf((addEditText.getText())));
                values.put(FeedsTable.COLUMN_DATE, format.format(new Date()));
                values.put(FeedsTable.COLUMN_DONE, FALSE);
                values.put(FeedsTable.COLUMN_PRIOR, FALSE);
                getContentResolver().insert(ENTRIES_URI, values);

                addEditText.setText("");
            }
        });

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, ENTRIES_URI, null, null, null, SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapterListView.swapCursor(data);
    }


    @Override
    public void onLoaderReset(Loader loader) {
        cursorAdapterListView.swapCursor(null);
    }
}
