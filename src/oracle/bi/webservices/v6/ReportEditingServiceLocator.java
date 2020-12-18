/**
 * ReportEditingServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;

public class ReportEditingServiceLocator extends org.apache.axis.client.Service implements oracle.bi.webservices.v6.ReportEditingService {

    public ReportEditingServiceLocator() {
    }


    public ReportEditingServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ReportEditingServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ReportEditingServiceSoap
    private java.lang.String ReportEditingServiceSoap_address = "http://system14:7001/analytics/saw.dll?SoapImpl=reportService";

    public java.lang.String getReportEditingServiceSoapAddress() {
        return ReportEditingServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ReportEditingServiceSoapWSDDServiceName = "ReportEditingServiceSoap";

    public java.lang.String getReportEditingServiceSoapWSDDServiceName() {
        return ReportEditingServiceSoapWSDDServiceName;
    }

    public void setReportEditingServiceSoapWSDDServiceName(java.lang.String name) {
        ReportEditingServiceSoapWSDDServiceName = name;
    }

    public oracle.bi.webservices.v6.ReportEditingServiceSoap getReportEditingServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ReportEditingServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getReportEditingServiceSoap(endpoint);
    }

    public oracle.bi.webservices.v6.ReportEditingServiceSoap getReportEditingServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            oracle.bi.webservices.v6.ReportEditingServiceStub _stub = new oracle.bi.webservices.v6.ReportEditingServiceStub(portAddress, this);
            _stub.setPortName(getReportEditingServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setReportEditingServiceSoapEndpointAddress(java.lang.String address) {
        ReportEditingServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (oracle.bi.webservices.v6.ReportEditingServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                oracle.bi.webservices.v6.ReportEditingServiceStub _stub = new oracle.bi.webservices.v6.ReportEditingServiceStub(new java.net.URL(ReportEditingServiceSoap_address), this);
                _stub.setPortName(getReportEditingServiceSoapWSDDServiceName());
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
        if ("ReportEditingServiceSoap".equals(inputPortName)) {
            return getReportEditingServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportEditingService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ReportEditingServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ReportEditingServiceSoap".equals(portName)) {
            setReportEditingServiceSoapEndpointAddress(address);
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
