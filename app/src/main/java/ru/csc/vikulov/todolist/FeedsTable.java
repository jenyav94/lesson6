package ru.csc.vikulov.todolist;

import android.provider.BaseColumns;


public interface FeedsTable extends BaseColumns {
    String TABLE_NAME = "feeds";

    String COLUMN_CONTENT = "content";
    String COLUMN_DATE = "date";
    String COLUMN_DONE = "done";
    String COLUMN_PRIOR = "prior";
}
