package com.example.aidlserver.serv;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import androidx.annotation.NonNull;

import static com.example.aidlserver.cons.Const.KEY_CLIENT_MSG;
import static com.example.aidlserver.cons.Const.KEY_SERVER_MSG;

public class MessengerService extends Service {


    @Override
    public IBinder onBind(Intent intent) {
        MessengerHandler messengerHandler = new MessengerHandler(
                Looper.getMainLooper()
        );
        Messenger serverMessenger = new Messenger(messengerHandler);
        return serverMessenger.getBinder();
    }

    static class MessengerHandler extends Handler {
        public MessengerHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                System.out.println("服务端 收到消息 "
                        + msg.getData().getString(KEY_CLIENT_MSG));
                Messenger clientMessenger = msg.replyTo;
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString(KEY_SERVER_MSG, "I'm fine,fuck u.");
                message.setData(bundle);
                System.out.println("服务端 向客户端远程发送消息 "
                        + message.getData().getString(KEY_SERVER_MSG));
                clientMessenger.send(message);
            } catch (RemoteException pE) {
                pE.printStackTrace();
            }
        }
    }
}
