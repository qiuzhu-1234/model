package com.cnnp.model.db;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class StudentInfo implements Parcelable {
    @Id
    private Long id;
    private String name;
    private int chniese;
    private int math;
    private int english;
    private String total;

    protected StudentInfo(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        chniese = in.readInt();
        math = in.readInt();
        english = in.readInt();
        total = in.readString();
    }

    @Generated(hash = 18059585)
    public StudentInfo(Long id, String name, int chniese, int math, int english,
            String total) {
        this.id = id;
        this.name = name;
        this.chniese = chniese;
        this.math = math;
        this.english = english;
        this.total = total;
    }

    @Generated(hash = 2016856731)
    public StudentInfo() {
    }

    public static final Creator<StudentInfo> CREATOR = new Creator<StudentInfo>() {
        @Override
        public StudentInfo createFromParcel(Parcel in) {
            return new StudentInfo(in);
        }

        @Override
        public StudentInfo[] newArray(int size) {
            return new StudentInfo[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChniese() {
        return chniese;
    }

    public void setChniese(int chniese) {
        this.chniese = chniese;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(name);
        dest.writeInt(chniese);
        dest.writeInt(math);
        dest.writeInt(english);
        dest.writeString(total);
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", chniese=" + chniese +
                ", math=" + math +
                ", english=" + english +
                ", total='" + total + '\'' +
                '}';
    }
}
