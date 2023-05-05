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

    
    /** 
     * @param seconds
     */
    public void setSeconds(String seconds){
        this.seconds = seconds;
    }
    
    /** 
     * @return Date
     */
    public Date getServiceStart() {
        return serviceStart;
    }
    
    /** 
     * @param serviceStart
     */
    public void setServiceStart(Date serviceStart) {
        this.serviceStart = serviceStart;
    }
    
    /** 
     * @return Date
     */
    public Date getServiceEnd() {
        return serviceEnd;
    }
    
    /** 
     * @param serviceEnd
     */
    public void setServiceEnd(Date serviceEnd) {
        this.serviceEnd = serviceEnd;
    }
    
    /** 
     * @return boolean
     */
    public boolean isRunning() {
        return isRunning;
    }
    
    /** 
     * @param isRunning
     */
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    
}
