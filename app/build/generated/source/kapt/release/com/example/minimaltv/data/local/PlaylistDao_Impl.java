package com.example.minimaltv.data.local;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.minimaltv.data.model.Playlist;
import com.example.minimaltv.data.model.PlaylistType;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PlaylistDao_Impl implements PlaylistDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Playlist> __insertionAdapterOfPlaylist;

  private final SharedSQLiteStatement __preparedStmtOfDeletePlaylistById;

  public PlaylistDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlaylist = new EntityInsertionAdapter<Playlist>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `playlists` (`id`,`name`,`url`,`channelCount`,`lastUpdated`,`type`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Playlist entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getUrl() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getUrl());
        }
        statement.bindLong(4, entity.getChannelCount());
        if (entity.getLastUpdated() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getLastUpdated());
        }
        statement.bindString(6, __PlaylistType_enumToString(entity.getType()));
      }
    };
    this.__preparedStmtOfDeletePlaylistById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM playlists WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertPlaylist(final Playlist playlist,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPlaylist.insert(playlist);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePlaylistById(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePlaylistById.acquire();
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeletePlaylistById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Playlist>> getAllPlaylists() {
    final String _sql = "SELECT * FROM playlists";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"playlists"}, new Callable<List<Playlist>>() {
      @Override
      @NonNull
      public List<Playlist> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfChannelCount = CursorUtil.getColumnIndexOrThrow(_cursor, "channelCount");
          final int _cursorIndexOfLastUpdated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastUpdated");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final List<Playlist> _result = new ArrayList<Playlist>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Playlist _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final int _tmpChannelCount;
            _tmpChannelCount = _cursor.getInt(_cursorIndexOfChannelCount);
            final String _tmpLastUpdated;
            if (_cursor.isNull(_cursorIndexOfLastUpdated)) {
              _tmpLastUpdated = null;
            } else {
              _tmpLastUpdated = _cursor.getString(_cursorIndexOfLastUpdated);
            }
            final PlaylistType _tmpType;
            _tmpType = __PlaylistType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            _item = new Playlist(_tmpId,_tmpName,_tmpUrl,_tmpChannelCount,_tmpLastUpdated,_tmpType);
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

  private String __PlaylistType_enumToString(@NonNull final PlaylistType _value) {
    switch (_value) {
      case LOCAL: return "LOCAL";
      case URL: return "URL";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private PlaylistType __PlaylistType_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "LOCAL": return PlaylistType.LOCAL;
      case "URL": return PlaylistType.URL;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
