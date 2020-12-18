/**
 * ACL.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;

public class ACL  implements java.io.Serializable {
    private java.lang.String dummy;

    private oracle.bi.webservices.v6.AccessControlToken[] accessControlTokens;

    public ACL() {
    }

    public ACL(
           java.lang.String dummy,
           oracle.bi.webservices.v6.AccessControlToken[] accessControlTokens) {
           this.dummy = dummy;
           this.accessControlTokens = accessControlTokens;
    }


    /**
     * Gets the dummy value for this ACL.
     * 
     * @return dummy
     */
    public java.lang.String getDummy() {
        return dummy;
    }


    /**
     * Sets the dummy value for this ACL.
     * 
     * @param dummy
     */
    public void setDummy(java.lang.String dummy) {
        this.dummy = dummy;
    }


    /**
     * Gets the accessControlTokens value for this ACL.
     * 
     * @return accessControlTokens
     */
    public oracle.bi.webservices.v6.AccessControlToken[] getAccessControlTokens() {
        return accessControlTokens;
    }


    /**
     * Sets the accessControlTokens value for this ACL.
     * 
     * @param accessControlTokens
     */
    public void setAccessControlTokens(oracle.bi.webservices.v6.AccessControlToken[] accessControlTokens) {
        this.accessControlTokens = accessControlTokens;
    }

    public oracle.bi.webservices.v6.AccessControlToken getAccessControlTokens(int i) {
        return this.accessControlTokens[i];
    }

    public void setAccessControlTokens(int i, oracle.bi.webservices.v6.AccessControlToken _value) {
        this.accessControlTokens[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ACL)) return false;
        ACL other = (ACL) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dummy==null && other.getDummy()==null) || 
             (this.dummy!=null &&
              this.dummy.equals(other.getDummy()))) &&
            ((this.accessControlTokens==null && other.getAccessControlTokens()==null) || 
             (this.accessControlTokens!=null &&
              java.util.Arrays.equals(this.accessControlTokens, other.getAccessControlTokens())));
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
        if (getDummy() != null) {
            _hashCode += getDummy().hashCode();
        }
        if (getAccessControlTokens() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAccessControlTokens());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAccessControlTokens(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ACL.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ACL"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dummy");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "dummy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accessControlTokens");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "accessControlTokens"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "AccessControlToken"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setMaxOccursUnbounded(true);
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
