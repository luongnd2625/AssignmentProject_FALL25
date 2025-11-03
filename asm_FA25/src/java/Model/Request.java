package Model;

import java.util.Date;

public class Request {
    private int reqID;
    private String title;
    private int userID;
    private Date fromDate;
    private Date toDate;
    private String reason;
    private int statusID;
    private Integer approverID;
    private String approverNote;

    public Request(int reqID, String title, int userID, Date fromDate, Date toDate, String reason, int statusID, Integer approverID, String approverNote) {
        this.reqID = reqID;
        this.title = title;
        this.userID = userID;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.statusID = statusID;
        this.approverID = approverID;
        this.approverNote = approverNote;
    }

    public int getReqID() {
        return reqID;
    }

    public void setReqID(int reqID) {
        this.reqID = reqID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public Integer getAppriverID() {
        return approverID;
    }

    public void setAppriverID(Integer appriverID) {
        this.approverID = appriverID;
    }

    public String getApproverNote() {
        return approverNote;
    }

    public void setApproverNote(String approverNote) {
        this.approverNote = approverNote;
    }

    @Override
    public String toString() {
        return "Request{" + "reqID=" + reqID + ", title=" + title + ", userID=" + userID + ", fromDate=" + fromDate + ", toDate=" + toDate + ", reason=" + reason + ", statusID=" + statusID + ", approverID=" + approverID + ", approverNote=" + approverNote + '}';
    }
    
    
}
