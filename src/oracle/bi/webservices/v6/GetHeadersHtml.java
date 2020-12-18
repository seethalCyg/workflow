/**
 * GetHeadersHtml.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;

public class GetHeadersHtml  implements java.io.Serializable {
    private java.lang.String pageID;

    private java.lang.String sessionID;

    public GetHeadersHtml() {
    }

    public GetHeadersHtml(
           java.lang.String pageID,
           java.lang.String sessionID) {
           this.pageID = pageID;
           this.sessionID = sessionID;
    }


    /**
     * Gets the pageID value for this GetHeadersHtml.
     * 
     * @return pageID
     */
    public java.lang.String getPageID() {
        return pageID;
    }


    /**
     * Sets the pageID value for this GetHeadersHtml.
     * 
     * @param pageID
     */
    public void setPageID(java.lang.String pageID) {
        this.pageID = pageID;
    }


    /**
     * Gets the sessionID value for this GetHeadersHtml.
     * 
     * @return sessionID
     */
    public java.lang.String getSessionID() {
        return sessionID;
    }


    /**
     * Sets the sessionID value for this GetHeadersHtml.
     * 
     * @param sessionID
     */
    public void setSessionID(java.lang.String sessionID) {
        this.sessionID = sessionID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetHeadersHtml)) return false;
        GetHeadersHtml other = (GetHeadersHtml) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.pageID==null && other.getPageID()==null) || 
             (this.pageID!=null &&
              this.pageID.equals(other.getPageID()))) &&
            ((this.sessionID==null && other.getSessionID()==null) || 
             (this.sessionID!=null &&
              this.sessionID.equals(other.getSessionID())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getPageID() != null) {
            _hashCode += getPageID().hashCode();
        }
        if (getSessionID() != null) {
            _hashCode += getSessionID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetHeadersHtml.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">getHeadersHtml"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageID");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "pageID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionID");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "sessionID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
