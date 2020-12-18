/**
 * ReplicationServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;

public class ReplicationServiceLocator extends org.apache.axis.client.Service implements oracle.bi.webservices.v6.ReplicationService {

    public ReplicationServiceLocator() {
    }


    public ReplicationServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ReplicationServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ReplicationServiceSoap
    private java.lang.String ReplicationServiceSoap_address = "http://system14:7001/analytics/saw.dll?SoapImpl=replicationService";

    public java.lang.String getReplicationServiceSoapAddress() {
        return ReplicationServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ReplicationServiceSoapWSDDServiceName = "ReplicationServiceSoap";

    public java.lang.String getReplicationServiceSoapWSDDServiceName() {
        return ReplicationServiceSoapWSDDServiceName;
    }

    public void setReplicationServiceSoapWSDDServiceName(java.lang.String name) {
        ReplicationServiceSoapWSDDServiceName = name;
    }

    public oracle.bi.webservices.v6.ReplicationServiceSoap getReplicationServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ReplicationServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getReplicationServiceSoap(endpoint);
    }

    public oracle.bi.webservices.v6.ReplicationServiceSoap getReplicationServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            oracle.bi.webservices.v6.ReplicationServiceStub _stub = new oracle.bi.webservices.v6.ReplicationServiceStub(portAddress, this);
            _stub.setPortName(getReplicationServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setReplicationServiceSoapEndpointAddress(java.lang.String address) {
        ReplicationServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (oracle.bi.webservices.v6.ReplicationServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                oracle.bi.webservices.v6.ReplicationServiceStub _stub = new oracle.bi.webservices.v6.ReplicationServiceStub(new java.net.URL(ReplicationServiceSoap_address), this);
                _stub.setPortName(getReplicationServiceSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ReplicationServiceSoap".equals(inputPortName)) {
            return getReplicationServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReplicationService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReplicationServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ReplicationServiceSoap".equals(portName)) {
            setReplicationServiceSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
