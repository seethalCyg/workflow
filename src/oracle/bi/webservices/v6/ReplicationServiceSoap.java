/**
 * ReplicationServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;

public interface ReplicationServiceSoap extends java.rmi.Remote {
    public void export(java.lang.String filename, oracle.bi.webservices.v6.CatalogItemsFilter filter, oracle.bi.webservices.v6.ExportFlags flag, boolean exportSecurity, java.lang.String sessionID) throws java.rmi.RemoteException;
    public oracle.bi.webservices.v6.ImportError[] _import(java.lang.String filename, oracle.bi.webservices.v6.ImportFlags flag, java.util.Calendar lastPurgedLog, boolean updateReplicationLog, boolean returnErrors, oracle.bi.webservices.v6.CatalogItemsFilter filter, oracle.bi.webservices.v6.PathMapEntry[] pathMap, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void markForReplication(java.lang.String item, boolean replicate, java.lang.String sessionID) throws java.rmi.RemoteException;
    public void purgeLog(java.lang.String[] items, java.util.Calendar timestamp, java.lang.String sessionID) throws java.rmi.RemoteException;
}
