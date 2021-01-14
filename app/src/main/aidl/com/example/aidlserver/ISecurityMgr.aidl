// ISecurityMgr.aidl
package com.example.aidlserver;

// Declare any non-default types here with import statements

interface ISecurityMgr {
    String encrypt(String content);
    String decipher(String password);
}
