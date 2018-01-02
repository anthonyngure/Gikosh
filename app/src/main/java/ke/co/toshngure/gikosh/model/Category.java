package ke.co.toshngure.gikosh.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

/**
 * Created by Anthony Ngure on 31/12/2017.
 * Email : anthonyngure25@gmail.com.
 */
@Entity(nameInDb = "categories")
public class Category implements Parcelable {


    @Id
    private long id;
    private long parentId;
    private String name;
    private String icon;
    private int depth;

    @Transient
    private boolean selected;

    /**
     * A # separated string
     */
    private String sizeOptions;

    @ToMany(referencedJoinProperty = "parentId")
    private List<Category> children;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 40161530)
    private transient CategoryDao myDao;


    public Category() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public int getDepth() {
        return depth;
    }


    public long getParentId() {
        return parentId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 42505565)
    public List<Category> getChildren() {
        if (children == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoryDao targetDao = daoSession.getCategoryDao();
            List<Category> childrenNew = targetDao._queryCategory_Children(id);
            synchronized (this) {
                if (children == null) {
                    children = childrenNew;
                }
            }
        }
        return children;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1590975152)
    public synchronized void resetChildren() {
        children = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    public void setSizeOptions(String sizeOptions) {
        this.sizeOptions = sizeOptions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.parentId);
        dest.writeString(this.name);
        dest.writeString(this.icon);
        dest.writeInt(this.depth);
        dest.writeString(this.sizeOptions);
        dest.writeTypedList(this.children);
    }

    public String getSizeOptions() {
        return this.sizeOptions;
    }

    protected Category(Parcel in) {
        this.id = in.readLong();
        this.parentId = in.readLong();
        this.name = in.readString();
        this.icon = in.readString();
        this.depth = in.readInt();
        this.sizeOptions = in.readString();
        this.children = in.createTypedArrayList(Category.CREATOR);
    }

    @Generated(hash = 2142830489)
    public Category(long id, long parentId, String name, String icon, int depth, String sizeOptions) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.icon = icon;
        this.depth = depth;
        this.sizeOptions = sizeOptions;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 503476761)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCategoryDao() : null;
    }
}
