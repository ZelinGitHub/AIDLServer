// IBookServerMgr.aidl
package com.example.aidlserver;

// Declare any non-default types here with import statements
import com.example.aidlserver.bean.Book;

interface IBookClientMgr {
    int getLevel();
    void setLevel(int level);
}
