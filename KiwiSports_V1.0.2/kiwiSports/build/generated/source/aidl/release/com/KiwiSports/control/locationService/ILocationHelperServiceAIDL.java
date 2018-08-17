/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\kiwi_git\\studio\\KiwiSports_V1.0.2\\kiwiSports\\src\\main\\aidl\\com\\KiwiSports\\control\\locationService\\ILocationHelperServiceAIDL.aidl
 */
package com.KiwiSports.control.locationService;
// Declare any non-default types here with import statements

public interface ILocationHelperServiceAIDL extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.KiwiSports.control.locationService.ILocationHelperServiceAIDL
{
private static final java.lang.String DESCRIPTOR = "com.KiwiSports.control.locationService.ILocationHelperServiceAIDL";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.KiwiSports.control.locationService.ILocationHelperServiceAIDL interface,
 * generating a proxy if needed.
 */
public static com.KiwiSports.control.locationService.ILocationHelperServiceAIDL asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.KiwiSports.control.locationService.ILocationHelperServiceAIDL))) {
return ((com.KiwiSports.control.locationService.ILocationHelperServiceAIDL)iin);
}
return new com.KiwiSports.control.locationService.ILocationHelperServiceAIDL.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_onFinishBind:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onFinishBind(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.KiwiSports.control.locationService.ILocationHelperServiceAIDL
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
    * 定位service绑定完毕后，会通知helperservice自己绑定的notiId
    * @param notiId 定位service的notiId
    */
@Override public void onFinishBind(int notiId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(notiId);
mRemote.transact(Stub.TRANSACTION_onFinishBind, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onFinishBind = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
/**
    * 定位service绑定完毕后，会通知helperservice自己绑定的notiId
    * @param notiId 定位service的notiId
    */
public void onFinishBind(int notiId) throws android.os.RemoteException;
}
