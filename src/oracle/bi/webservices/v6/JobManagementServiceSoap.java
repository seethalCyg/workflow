/**
 * JobManagementServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;

public interface JobManagementServiceSoap extends java.rmi.Remote {
    public oracle.bi.webservices.v6.JobInfo writeListFiles(oracle.bi.webservices.v6.ReportRef report, oracle.bi.webservices.v6.ReportParams reportParams, java.lang.String segmentPath, oracle.bi.webservices.v6.TreeNodePath treeNodePath, oracle.bi.webservices.v6.SegmentationOptions segmentationOptions, java.lang.String filesystem, java.math.BigInteger timeout, java.lang.String sessionID) throws java.rmi.RemoteException;
    public oracle.bi.webservices.v6.JobInfo getJobInfo(java.math.BigInteger jobID, java.lang.String sessionID) throws java.rmi.RemoteException;
    public oracle.bi.webservices.v6.JobInfo cancelJob(java.math.BigInteger jobID, java.lang.String sessionID) throws java.rmi.RemoteException;
    public oracle.bi.webservices.v6.JobInfo getCounts(java.lang.String segmentPath, java.lang.String treePath, oracle.bi.webservices.v6.SegmentationOptions segmentationOptions, java.lang.String sessionID) throws java.rmi.RemoteException;
    public java.lang.String[] getPromptedColumns(java.lang.String segmentPath, java.lang.String treePath, java.lang.String sessionID) throws java.rmi.RemoteException;
    public oracle.bi.webservices.v6.JobInfo purgeCache(java.lang.String segmentPath, java.lang.String treePath, java.lang.Boolean ignoreCacheRef, java.lang.String sessionID) throws java.rmi.RemoteException;
    public oracle.bi.webservices.v6.JobInfo prepareCache(java.lang.String segmentPath, java.lang.String treePath, java.lang.Boolean refresh, java.lang.String sessionID) throws java.rmi.RemoteException;
    public oracle.bi.webservices.v6.JobInfo saveResultSet(java.lang.String segmentPath, oracle.bi.webservices.v6.TreeNodePath treeNodePath, java.lang.String savedSegmentPath, oracle.bi.webservices.v6.SegmentationOptions segmentationOptions, java.lang.String SRCustomLabel, java.lang.Boolean appendStaticSegment, java.lang.String sessionID) throws java.rmi.RemoteException;
    public oracle.bi.webservices.v6.JobInfo deleteResultSet(java.lang.String targetLevel, java.lang.String[] GUIDs, java.lang.String segmentPath, java.lang.String sessionID) throws java.rmi.RemoteException;
}
