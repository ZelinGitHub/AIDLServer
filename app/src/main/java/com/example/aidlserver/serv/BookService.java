package com.example.aidlserver.serv;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.aidlserver.IBookClientMgr;
import com.example.aidlserver.IBookServerMgr;
import com.example.aidlserver.bean.Book;
import com.example.aidlserver.cons.Const;

import java.util.concurrent.CopyOnWriteArrayList;

public class BookService extends Service {
    private CopyOnWriteArrayList<Book> mBooks = new CopyOnWriteArrayList<>();

    @Override
    public IBinder onBind(Intent intent) {
        return new BookBinder();
    }

//    @Override
//    public IBinder onBind(Intent intent) {
//        if (checkClientPermission_(this)) {
//            return new BookBinder();
//        } else {
//            return null;
//        }
//    }

    private boolean checkClientPermission_(Context pContext) {
        return pContext.checkCallingOrSelfPermission(
                Const.BOOK_SERVER_PERMISSION
        ) != PackageManager.PERMISSION_DENIED;
    }

    private boolean checkClientPackage(Context pContext) {
        String[] packages = pContext.getPackageManager()
                .getPackagesForUid(Binder.getCallingUid());
        if (packages != null && packages.length != 0) {
            String packageName = packages[0];
            return packageName.startsWith("com.example");
        } else {
            return false;
        }
    }

    class BookBinder extends IBookServerMgr.Stub {

        @Override
        public void addBook(Book book) throws RemoteException {
            System.out.println("服务端 新增一本书 " + book.toString());
            mBooks.add(book);
        }

        @Override
        public Book removeBook() throws RemoteException {
            if (mBooks.isEmpty()) {
                return null;
            } else {
                Book book = mBooks.remove(0);
                System.out.println("服务端 丢弃一本书 " + book.toString());
                return book;
            }
        }

        @Override
        public boolean isHaveBook(String name) throws RemoteException {
            for (Book book : mBooks) {
                if (book.name.equals(name)) {
                    System.out.println("服务端 有这本书 " + book.toString());
                    return true;
                }
            }
            System.out.println("服务端 没有这本书");
            return false;
        }

        @Override
        public void setClientBinder(final IBookClientMgr clientBinder) throws RemoteException {
            System.out.println("服务端 得到客户端Binder");
            //IPC方法可能会阻塞，应该在子线程调用
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("服务端 查询客户端的等级");
                        int level = clientBinder.getLevel();
                        System.out.println("服务端 得到的客户端等级是 " + level);
                        System.out.println("服务端 设置客户端的等级为-1");
                        clientBinder.setLevel(-1);
                    } catch (RemoteException pE) {
                        pE.printStackTrace();
                    }
                }
            }).start();
        }

//        @Override
//        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
//            if (checkClientPermission_(BookService.this)) {
//                return super.onTransact(code, data, reply, flags);
//            } else {
//                return false;
//            }
//        }
//        @Override
//        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
//            if (checkClientPackage(BookService.this)) {
//                return super.onTransact(code, data, reply, flags);
//            } else {
//                return false;
//            }
//        }
    }


}
