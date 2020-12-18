/**
 * HtmlViewServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;

public class HtmlViewServiceLocator extends org.apache.axis.client.Service implements oracle.bi.webservices.v6.HtmlViewService {

    public HtmlViewServiceLocator() {
    }


    public HtmlViewServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public HtmlViewServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for HtmlViewService
    private java.lang.String HtmlViewService_address = "http://system14:7001/analytics/saw.dll?SoapImpl=htmlViewService";

    public java.lang.String getHtmlViewServiceAddress() {
        return HtmlViewService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String HtmlViewServiceWSDDServiceName = "HtmlViewService";

    public java.lang.String getHtmlViewServiceWSDDServiceName() {
        return HtmlViewServiceWSDDServiceName;
    }

    public void setHtmlViewServiceWSDDServiceName(java.lang.String name) {
        HtmlViewServiceWSDDServiceName = name;
    }

    public oracle.bi.webservices.v6.HtmlViewServiceSoap getHtmlViewService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(HtmlViewService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getHtmlViewService(endpoint);
    }

    public oracle.bi.webservices.v6.HtmlViewServiceSoap getHtmlViewService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            oracle.bi.webservices.v6.HtmlViewServiceStub _stub = new oracle.bi.webservices.v6.HtmlViewServiceStub(portAddress, this);
            _stub.setPortName(getHtmlViewServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setHtmlViewServiceEndpointAddress(java.lang.String address) {
        HtmlViewService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (oracle.bi.webservices.v6.HtmlViewServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                oracle.bi.webservices.v6.HtmlViewServiceStub _stub = new oracle.bi.webservices.v6.HtmlViewServiceStub(new java.net.URL(HtmlViewService_address), this);
                _stub.setPortName(getHtmlViewServiceWSDDServiceName());
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
        if ("HtmlViewService".equals(inputPortName)) {
            return getHtmlViewService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "HtmlViewService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "HtmlViewService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("HtmlViewService".equals(portName)) {
            setHtmlViewServiceEndpointAddress(address);
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
