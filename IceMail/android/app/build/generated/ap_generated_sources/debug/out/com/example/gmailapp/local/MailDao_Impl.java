package com.example.gmailapp.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MailDao_Impl implements MailDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MailEntity> __insertionAdapterOfMailEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public MailDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMailEntity = new EntityInsertionAdapter<MailEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `mails` (`id`,`from`,`to`,`subject`,`content`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final MailEntity entity) {
        statement.bindLong(1, entity.id);
        if (entity.from == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.from);
        }
        if (entity.to == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.to);
        }
        if (entity.subject == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.subject);
        }
        if (entity.content == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.content);
        }
        if (entity.createdAt == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.createdAt);
        }
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM mails";
        return _query;
      }
    };
  }

  @Override
  public void insertAll(final List<MailEntity> mails) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfMailEntity.insert(mails);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void clearAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfClearAll.release(_stmt);
    }
  }

  @Override
  public LiveData<List<MailEntity>> getAllMails() {
    final String _sql = "SELECT * FROM mails ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"mails"}, false, new Callable<List<MailEntity>>() {
      @Override
      @Nullable
      public List<MailEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFrom = CursorUtil.getColumnIndexOrThrow(_cursor, "from");
          final int _cursorIndexOfTo = CursorUtil.getColumnIndexOrThrow(_cursor, "to");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<MailEntity> _result = new ArrayList<MailEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MailEntity _item;
            _item = new MailEntity();
            _item.id = _cursor.getInt(_cursorIndexOfId);
            if (_cursor.isNull(_cursorIndexOfFrom)) {
              _item.from = null;
            } else {
              _item.from = _cursor.getString(_cursorIndexOfFrom);
            }
            if (_cursor.isNull(_cursorIndexOfTo)) {
              _item.to = null;
            } else {
              _item.to = _cursor.getString(_cursorIndexOfTo);
            }
            if (_cursor.isNull(_cursorIndexOfSubject)) {
              _item.subject = null;
            } else {
              _item.subject = _cursor.getString(_cursorIndexOfSubject);
            }
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _item.content = null;
            } else {
              _item.content = _cursor.getString(_cursorIndexOfContent);
            }
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _item.createdAt = null;
            } else {
              _item.createdAt = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
