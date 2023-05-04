package com.example.javaav.Model;

import java.util.Date;

public class Service {
    Date serviceStart;
    Date serviceEnd;
    boolean isRunning;

    String seconds;
    public Service(Date serviceStart, Date serviceEnd, boolean isRunning, String seconds) {
        this.serviceStart = serviceStart;
        this.serviceEnd = serviceEnd;
        this.isRunning = isRunning;
        this.seconds = seconds;
    }

    public String getSeconds(){ return  seconds; }

    public void setSeconds(String seconds){
        this.seconds = seconds;
    }
    public Date getServiceStart() {
        return serviceStart;
    }
    public void setServiceStart(Date serviceStart) {
        this.serviceStart = serviceStart;
    }
    public Date getServiceEnd() {
        return serviceEnd;
    }
    public void setServiceEnd(Date serviceEnd) {
        this.serviceEnd = serviceEnd;
    }
    public boolean isRunning() {
        return isRunning;
    }
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    
}
