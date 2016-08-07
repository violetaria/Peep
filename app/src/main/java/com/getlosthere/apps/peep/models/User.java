package com.getlosthere.apps.peep.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by violetaria on 8/3/16.
 */
@Parcel(analyze = { User.class })
@Table(name = "Users")
public class User extends Model {
    @Column(name = "Name")
    public String name;

    @Column(name = "UID", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public long uid;

    @Column(name = "ScreenName")
    public String screenName;

    @Column(name = "ProfileImageUrl")
    public String profileImageUrl;

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public long getUid() {
        return uid;
    }

    // empty constructor needed by the Parceler library
    public User() {
        super();
    }

    public static User findOrCreateFromJson(JSONObject jsonObject) {
        long rid = 0;
        try {
            rid = jsonObject.getLong("id");
            User existingUser = new Select().from(User.class).where("uid = ?",rid).executeSingle();
            if (existingUser != null) {
                return existingUser;
            } else {
                User user = User.fromJSON(jsonObject);
                user.save();
                return user;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User fromJSON(JSONObject jsonObject) {
        User user = new User();

        try {
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }
}
