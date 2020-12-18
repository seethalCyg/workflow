/**
 * ReportEditingServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;

public interface ReportEditingServiceSoap extends java.rmi.Remote {
    public java.lang.Object applyReportParams(oracle.bi.webservices.v6.ReportRef reportRef, oracle.bi.webservices.v6.ReportParams reportParams, boolean encodeInString, java.lang.String sessionID) throws java.rmi.RemoteException;
    public java.lang.String generateReportSQL(oracle.bi.webservices.v6.ReportRef reportRef, oracle.bi.webservices.v6.ReportParams reportParams, java.lang.String sessionID) throws java.rmi.RemoteException;
    public java.lang.String applyReportDefaults(oracle.bi.webservices.v6.ReportRef reportRefs, java.lang.String sessionID) throws java.rmi.RemoteException;
}
