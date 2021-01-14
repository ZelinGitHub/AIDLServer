package com.example.aidlserver.serv;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.aidlserver.IComputeMgr;
import com.example.aidlserver.IPoolMgr;
import com.example.aidlserver.ISecurityMgr;

import static com.example.aidlserver.cons.Const.BINDER_CODE_COMPUTE;
import static com.example.aidlserver.cons.Const.BINDER_CODE_SECURITY;

public class PoolService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new PoolBinder();
    }

    static class PoolBinder extends IPoolMgr.Stub {

        @Override
        public IBinder getBinder(int code) throws RemoteException {
            System.out.println("服务端 生成Binder");
            switch (code) {
                case BINDER_CODE_SECURITY: {

                    return new SecurityBinder();
                }
                case BINDER_CODE_COMPUTE: {

                    return new ComputeBinder();
                }
            }
            return null;
        }
    }

    static class SecurityBinder extends ISecurityMgr.Stub {

        @Override
        public String encrypt(String content) throws RemoteException {
            System.out.println("服务端 加密");
            return content + "密文";
        }

        @Override
        public String decipher(String password) throws RemoteException {
            System.out.println("服务端 解密");
            return password + "明文";
        }
    }

    static class ComputeBinder extends IComputeMgr.Stub {

        @Override
        public int plus(int a, int b) throws RemoteException {
            System.out.println("服务端 计算 a+b");
            return a + b;
        }
    }
}
