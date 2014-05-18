package com.palominolabs.wemo;

import org.cybergarage.upnp.Action;
import org.cybergarage.upnp.Argument;
import org.cybergarage.upnp.ArgumentList;
import org.cybergarage.upnp.Device;
import org.cybergarage.upnp.Service;


public class InsightSwitch {
    private final Device device;

    public InsightSwitch(Device device) {
        this.device = device;
    }

    public PowerUsage getPowerUsage() throws InsightSwitchOperationException {
        Action action = device.getAction("GetInsightParams");
        performAction(action);

        return new PowerUsage(action.getArgumentValue("InsightParams"));
    }

    public void switchOn() throws InsightSwitchOperationException {
        setSwitchIsOn(true);
    }

    public void switchOff() throws InsightSwitchOperationException {
        setSwitchIsOn(false);
    }

    public void setSwitchIsOn(boolean on) throws InsightSwitchOperationException {
//    	Debug.on();
    	Service service = device.getService("urn:Belkin:serviceId:basicevent1");
    	Action action = new Action(service.getServiceNode());
    	ArgumentList al = new ArgumentList();
    	Argument binState = new Argument("BinaryState", "");
    	binState.setDirection(Argument.IN);
    	al.add(binState);
    	action.setArgumentList(al);
    	action.setName("SetBinaryState");
    	
//        Action action = device.getAction("SetBinaryState");
        action.setArgumentValue("BinaryState", on ? 1 : 0);

        performAction(action);
    }

    public String getFriendlyName() throws InsightSwitchOperationException {
//        Action action = device.getAction("GetFriendlyName");
//        performAction(action);
//        return action.getArgumentValue("FriendlyName");
    	return device.getFriendlyName();
    }

    private void performAction(Action action) throws InsightSwitchOperationException {
        if (!action.postControlAction()) {
            throw new InsightSwitchOperationException(action.getStatus());
        }
    }

}
