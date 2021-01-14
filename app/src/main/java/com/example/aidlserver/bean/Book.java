package com.example.aidlserver.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class Book implements Parcelable {
    public int code;
    public String name;

    public Book() {
    }

    protected Book(Parcel in) {
        code = in.readInt();
        name = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(name);
    }

    @NonNull
    @Override
    public String toString() {
        return "Book{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}
