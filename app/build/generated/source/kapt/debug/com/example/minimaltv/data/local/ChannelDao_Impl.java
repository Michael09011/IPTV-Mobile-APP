package com.example.minimaltv.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.minimaltv.data.model.Channel;
import java.lang.Class;
import java.lang.Exception;
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
public final class ChannelDao_Impl implements ChannelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Channel> __insertionAdapterOfChannel;

  private final EntityDeletionOrUpdateAdapter<Channel> __updateAdapterOfChannel;

  private final SharedSQLiteStatement __preparedStmtOfDeleteChannelsByPlaylist;

  public ChannelDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChannel = new EntityInsertionAdapter<Channel>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `channels` (`id`,`playlistId`,`name`,`thumbnail`,`category`,`currentProgram`,`isFavorite`,`streamUrl`,`resolution`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Channel entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getPlaylistId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getPlaylistId());
        }
        if (entity.getName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getName());
        }
        if (entity.getThumbnail() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getThumbnail());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCategory());
        }
        if (entity.getCurrentProgram() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCurrentProgram());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.getStreamUrl() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getStreamUrl());
        }
        if (entity.getResolution() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getResolution());
        }
      }
    };
    this.__updateAdapterOfChannel = new EntityDeletionOrUpdateAdapter<Channel>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `channels` SET `id` = ?,`playlistId` = ?,`name` = ?,`thumbnail` = ?,`category` = ?,`currentProgram` = ?,`isFavorite` = ?,`streamUrl` = ?,`resolution` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Channel entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getPlaylistId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getPlaylistId());
        }
        if (entity.getName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getName());
        }
        if (entity.getThumbnail() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getThumbnail());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCategory());
        }
        if (entity.getCurrentProgram() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCurrentProgram());
        }
        final int _tmp = entity.isFavorite() ? 1 : 0;
        statement.bindLong(7, _tmp);
        if (entity.getStreamUrl() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getStreamUrl());
        }
        if (entity.getResolution() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getResolution());
        }
        if (entity.getId() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getId());
        }
      }
    };
    this.__preparedStmtOfDeleteChannelsByPlaylist = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM channels WHERE playlistId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertChannels(final List<Channel> channels,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfChannel.insert(channels);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateChannel(final Channel channel, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfChannel.handle(channel);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteChannelsByPlaylist(final String playlistId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteChannelsByPlaylist.acquire();
        int _argIndex = 1;
        if (playlistId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, playlistId);
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
          __preparedStmtOfDeleteChannelsByPlaylist.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Channel>> getChannelsByPlaylist(final String playlistId) {
    final String _sql = "SELECT * FROM channels WHERE playlistId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (playlistId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, playlistId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"channels"}, new Callable<List<Channel>>() {
      @Override
      @NonNull
      public List<Channel> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCurrentProgram = CursorUtil.getColumnIndexOrThrow(_cursor, "currentProgram");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfStreamUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "streamUrl");
          final int _cursorIndexOfResolution = CursorUtil.getColumnIndexOrThrow(_cursor, "resolution");
          final List<Channel> _result = new ArrayList<Channel>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Channel _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpPlaylistId;
            if (_cursor.isNull(_cursorIndexOfPlaylistId)) {
              _tmpPlaylistId = null;
            } else {
              _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpCurrentProgram;
            if (_cursor.isNull(_cursorIndexOfCurrentProgram)) {
              _tmpCurrentProgram = null;
            } else {
              _tmpCurrentProgram = _cursor.getString(_cursorIndexOfCurrentProgram);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpStreamUrl;
            if (_cursor.isNull(_cursorIndexOfStreamUrl)) {
              _tmpStreamUrl = null;
            } else {
              _tmpStreamUrl = _cursor.getString(_cursorIndexOfStreamUrl);
            }
            final String _tmpResolution;
            if (_cursor.isNull(_cursorIndexOfResolution)) {
              _tmpResolution = null;
            } else {
              _tmpResolution = _cursor.getString(_cursorIndexOfResolution);
            }
            _item = new Channel(_tmpId,_tmpPlaylistId,_tmpName,_tmpThumbnail,_tmpCategory,_tmpCurrentProgram,_tmpIsFavorite,_tmpStreamUrl,_tmpResolution);
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

  @Override
  public Flow<List<Channel>> getFavoriteChannels() {
    final String _sql = "SELECT * FROM channels WHERE isFavorite = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"channels"}, new Callable<List<Channel>>() {
      @Override
      @NonNull
      public List<Channel> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCurrentProgram = CursorUtil.getColumnIndexOrThrow(_cursor, "currentProgram");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfStreamUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "streamUrl");
          final int _cursorIndexOfResolution = CursorUtil.getColumnIndexOrThrow(_cursor, "resolution");
          final List<Channel> _result = new ArrayList<Channel>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Channel _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpPlaylistId;
            if (_cursor.isNull(_cursorIndexOfPlaylistId)) {
              _tmpPlaylistId = null;
            } else {
              _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpCurrentProgram;
            if (_cursor.isNull(_cursorIndexOfCurrentProgram)) {
              _tmpCurrentProgram = null;
            } else {
              _tmpCurrentProgram = _cursor.getString(_cursorIndexOfCurrentProgram);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpStreamUrl;
            if (_cursor.isNull(_cursorIndexOfStreamUrl)) {
              _tmpStreamUrl = null;
            } else {
              _tmpStreamUrl = _cursor.getString(_cursorIndexOfStreamUrl);
            }
            final String _tmpResolution;
            if (_cursor.isNull(_cursorIndexOfResolution)) {
              _tmpResolution = null;
            } else {
              _tmpResolution = _cursor.getString(_cursorIndexOfResolution);
            }
            _item = new Channel(_tmpId,_tmpPlaylistId,_tmpName,_tmpThumbnail,_tmpCategory,_tmpCurrentProgram,_tmpIsFavorite,_tmpStreamUrl,_tmpResolution);
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

  @Override
  public Object getFavoriteChannelsByPlaylistSync(final String playlistId,
      final Continuation<? super List<Channel>> $completion) {
    final String _sql = "SELECT * FROM channels WHERE playlistId = ? AND isFavorite = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (playlistId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, playlistId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Channel>>() {
      @Override
      @NonNull
      public List<Channel> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPlaylistId = CursorUtil.getColumnIndexOrThrow(_cursor, "playlistId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfThumbnail = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnail");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCurrentProgram = CursorUtil.getColumnIndexOrThrow(_cursor, "currentProgram");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavorite");
          final int _cursorIndexOfStreamUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "streamUrl");
          final int _cursorIndexOfResolution = CursorUtil.getColumnIndexOrThrow(_cursor, "resolution");
          final List<Channel> _result = new ArrayList<Channel>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Channel _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpPlaylistId;
            if (_cursor.isNull(_cursorIndexOfPlaylistId)) {
              _tmpPlaylistId = null;
            } else {
              _tmpPlaylistId = _cursor.getString(_cursorIndexOfPlaylistId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpThumbnail;
            if (_cursor.isNull(_cursorIndexOfThumbnail)) {
              _tmpThumbnail = null;
            } else {
              _tmpThumbnail = _cursor.getString(_cursorIndexOfThumbnail);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpCurrentProgram;
            if (_cursor.isNull(_cursorIndexOfCurrentProgram)) {
              _tmpCurrentProgram = null;
            } else {
              _tmpCurrentProgram = _cursor.getString(_cursorIndexOfCurrentProgram);
            }
            final boolean _tmpIsFavorite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp != 0;
            final String _tmpStreamUrl;
            if (_cursor.isNull(_cursorIndexOfStreamUrl)) {
              _tmpStreamUrl = null;
            } else {
              _tmpStreamUrl = _cursor.getString(_cursorIndexOfStreamUrl);
            }
            final String _tmpResolution;
            if (_cursor.isNull(_cursorIndexOfResolution)) {
              _tmpResolution = null;
            } else {
              _tmpResolution = _cursor.getString(_cursorIndexOfResolution);
            }
            _item = new Channel(_tmpId,_tmpPlaylistId,_tmpName,_tmpThumbnail,_tmpCategory,_tmpCurrentProgram,_tmpIsFavorite,_tmpStreamUrl,_tmpResolution);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
