// IBookServerMgr.aidl
package com.example.aidlserver;

// Declare any non-default types here with import statements
import com.example.aidlserver.bean.Book;
import com.example.aidlserver.IBookClientMgr;

interface IBookServerMgr {
void addBook(in Book book);
    Book removeBook();
    boolean isHaveBook(String name);
    void setClientBinder(IBookClientMgr clientBinder);
}
