/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.can;

import com.sun.cldc.jna.*;
import com.sun.cldc.jna.ptr.IntByReference;
import edu.wpi.first.wpilibj.communication.Semaphore;

public class JaguarCANDriver {

    public static final int kMaxMessageDataSize = 8;
    private static final TaskExecutor taskExecutor = new TaskExecutor("FRC CANJaguar Task executor");

    // void FRC_NetworkCommunication_JaguarCANDriver_sendMessage(UINT32 messageID, const UINT8 *data, UINT8 dataSize, INT32 *status);
    private static final Function sendMessageFn = NativeLibrary.getDefaultInstance().getFunction("FRC_NetworkCommunication_JaguarCANDriver_sendMessage");

    /**
     * Send a message on the CAN bus
     * @param messageID CAN MessageID to send on the CAN
     * @param data Data payload to send with the message
     */
    public static void sendMessage(int messageID, byte[] data, int dataSize) throws CANTimeoutException {
        Pointer sendDataBufferPointer = new Pointer(kMaxMessageDataSize);
        sendDataBufferPointer.setBytes(0, data, 0, dataSize);
        IntByReference sendStatus = new IntByReference(0);
        sendStatus.setValue(0);
        sendMessageFn.call4(messageID, sendDataBufferPointer, dataSize, sendStatus.getPointer().address().toUWord().toPrimitive());
        int statusValue = sendStatus.getValue();
        sendStatus.free();
        sendDataBufferPointer.free();
        CANExceptionFactory.checkStatus(statusValue, messageID);
    }

    // void FRC_NetworkCommunication_JaguarCANDriver_receiveMessage(UINT32 *messageID, UINT8 *data, UINT8 *dataSize, UINT32 timeoutMs, INT32 *status);
    private static final BlockingFunction receiveMessageFn = NativeLibrary.getDefaultInstance().getBlockingFunction("FRC_NetworkCommunication_JaguarCANDriver_receiveMessage");
    public int receivedMessageId;

    static {
        receiveMessageFn.setTaskExecutor(taskExecutor);
    }

    /**
     * Wait for a message to be received from the CAN bus.
     * @param messageID MessageID filter to specify what message ID to be expected.
     * @param data Buffer for received data
     * @param timeout Number of seconds to wait for the expected message
     * @return Actual size of the valid bytes in data
     */
    public byte receiveMessage(int messageID, byte[] data, double timeout) throws CANTimeoutException {
        byte dataSize = 0;
        IntByReference recvStatus = new IntByReference(0);
        recvStatus.setValue(0);
        Pointer messageIdPtr = new Pointer(4);
        messageIdPtr.setInt(0, messageID);
        Pointer dataSizePtr = new Pointer(1);
        dataSizePtr.setByte(0, (byte) 0);
        Pointer recvDataBufferPointer = new Pointer(kMaxMessageDataSize);
        receiveMessageFn.call5(messageIdPtr, recvDataBufferPointer, dataSizePtr, (int) (timeout * 1000.0), recvStatus.getPointer().address().toUWord().toPrimitive());
        int statusValue = recvStatus.getValue();
        if (statusValue >= 0) {
            dataSize = dataSizePtr.getByte(0);
            receivedMessageId = messageIdPtr.getInt(0);
            recvDataBufferPointer.getBytes(0, data, 0, data.length < dataSize ? data.length : dataSize);
        }
        recvDataBufferPointer.free();
        dataSizePtr.free();
        messageIdPtr.free();
        recvStatus.free();
        CANExceptionFactory.checkStatus(statusValue, messageID);
        return dataSize;
    }

    /**
     * Call receiveMessage with a default timeout parameter of 100ms
     * @param messageID MessageID filter to specify what message ID to be expected.
     * @param data Buffer for received data
     * @return Actual size of the valid bytes in data
     */
    public byte receiveMessage(int messageID, byte[] data) throws CANTimeoutException {
        return receiveMessage(messageID, data, 0.01);
    }

    // INT32 FRC_NetworkCommunication_JaguarCANDriver_receiveMessageStart_sem(UINT32 messageID, UINT32 semaphoreID, UINT32 timeoutMs, INT32 *status);
    private static final Function receiveMessageStart_semFn = NativeLibrary.getDefaultInstance().getFunction("FRC_NetworkCommunication_JaguarCANDriver_receiveMessageStart_sem");

    /**
     * Start waiting for a message to be received from the CAN bus.
     * @param messageID MessageID filter to specify what message ID to be expected.
     * @param sem Semaphore that indicates that the receive call has completed.
     * @param timeout Number of seconds to wait for the expected message.
     * @return Data available now... call complete
     */
    public boolean receiveMessageStart(int messageID, Semaphore sem, double timeout) throws CANTimeoutException {
        int retVal = 0;
        IntByReference recvStatus = new IntByReference(0);
        recvStatus.setValue(0);
        retVal = receiveMessageStart_semFn.call4(messageID, sem.getPointer(), (int) (timeout * 1000.0), recvStatus.getPointer().address().toUWord().toPrimitive());
        int statusValue = recvStatus.getValue();
        recvStatus.free();
        CANExceptionFactory.checkStatus(statusValue, messageID);
        return retVal != 0;
    }

    // void FRC_NetworkCommunication_JaguarCANDriver_receiveMessageComplete(UINT32 *messageID, UINT8 *data, UINT8 *dataSize, INT32 *status);
    private static final Function receiveMessageCompleteFn = NativeLibrary.getDefaultInstance().getFunction("FRC_NetworkCommunication_JaguarCANDriver_receiveMessageComplete");

    /**
     * Get the result of waiting for a message to be received from the CAN bus.
     * @param messageID MessageID filter to specify what message ID to be expected.
     * @param data Buffer for received data
     * @return Actual size of the valid bytes in data
     */
    public byte receiveMessageComplete(int messageID, byte[] data) throws CANTimeoutException {
        byte dataSize = 0;
        IntByReference recvStatus = new IntByReference(0);
        recvStatus.setValue(0);
        Pointer messageIdPtr = new Pointer(4);
        messageIdPtr.setInt(0, messageID);
        Pointer dataSizePtr = new Pointer(1);
        dataSizePtr.setByte(0, (byte) 0);
        Pointer recvDataBufferPointer = new Pointer(kMaxMessageDataSize);
        receiveMessageCompleteFn.call4(messageIdPtr, recvDataBufferPointer, dataSizePtr, recvStatus.getPointer().address().toUWord().toPrimitive());
        int statusValue = recvStatus.getValue();
        if (statusValue >= 0) {
            dataSize = dataSizePtr.getByte(0);
            receivedMessageId = messageIdPtr.getInt(0);
            recvDataBufferPointer.getBytes(0, data, 0, data.length < dataSize ? data.length : dataSize);
        }
        recvDataBufferPointer.free();
        dataSizePtr.free();
        messageIdPtr.free();
        recvStatus.free();
        CANExceptionFactory.checkStatus(statusValue, messageID);
        return dataSize;
    }
}
