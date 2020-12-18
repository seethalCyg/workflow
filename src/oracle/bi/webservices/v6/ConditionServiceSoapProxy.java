package oracle.bi.webservices.v6;

public class ConditionServiceSoapProxy implements oracle.bi.webservices.v6.ConditionServiceSoap {
  private String _endpoint = null;
  private oracle.bi.webservices.v6.ConditionServiceSoap conditionServiceSoap = null;
  
  public ConditionServiceSoapProxy() {
    _initConditionServiceSoapProxy();
  }
  
  public ConditionServiceSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initConditionServiceSoapProxy();
  }
  
  private void _initConditionServiceSoapProxy() {
    try {
      conditionServiceSoap = (new oracle.bi.webservices.v6.ConditionServiceLocator()).getConditionServiceSoap();
      if (conditionServiceSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)conditionServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)conditionServiceSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (conditionServiceSoap != null)
      ((javax.xml.rpc.Stub)conditionServiceSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public oracle.bi.webservices.v6.ConditionServiceSoap getConditionServiceSoap() {
    if (conditionServiceSoap == null)
      _initConditionServiceSoapProxy();
    return conditionServiceSoap;
  }
  
  public boolean evaluateCondition(java.lang.String path, java.lang.String[] reportCustomizationParameters, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (conditionServiceSoap == null)
      _initConditionServiceSoapProxy();
    return conditionServiceSoap.evaluateCondition(path, reportCustomizationParameters, sessionID);
  }
  
  public boolean evaluateInlineCondition(java.lang.String conditionXML, java.lang.String[] reportCustomizationParameters, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (conditionServiceSoap == null)
      _initConditionServiceSoapProxy();
    return conditionServiceSoap.evaluateInlineCondition(conditionXML, reportCustomizationParameters, sessionID);
  }
  
  public java.lang.String[] getConditionCustomizableReportElements(java.lang.String path, java.lang.String sessionID) throws java.rmi.RemoteException{
    if (conditionServiceSoap == null)
      _initConditionServiceSoapProxy();
    return conditionServiceSoap.getConditionCustomizableReportElements(path, sessionID);
  }
  
  
}