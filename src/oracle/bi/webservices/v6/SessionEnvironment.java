/**
 * SessionEnvironment.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;

public class SessionEnvironment  implements java.io.Serializable {
    private java.lang.String userName;

    private oracle.bi.webservices.v6.ItemInfo homeDirectory;

    private oracle.bi.webservices.v6.ItemInfo[] sharedDirectories;

    public SessionEnvironment() {
    }

    public SessionEnvironment(
           java.lang.String userName,
           oracle.bi.webservices.v6.ItemInfo homeDirectory,
           oracle.bi.webservices.v6.ItemInfo[] sharedDirectories) {
           this.userName = userName;
           this.homeDirectory = homeDirectory;
           this.sharedDirectories = sharedDirectories;
    }


    /**
     * Gets the userName value for this SessionEnvironment.
     * 
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this SessionEnvironment.
     * 
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }


    /**
     * Gets the homeDirectory value for this SessionEnvironment.
     * 
     * @return homeDirectory
     */
    public oracle.bi.webservices.v6.ItemInfo getHomeDirectory() {
        return homeDirectory;
    }


    /**
     * Sets the homeDirectory value for this SessionEnvironment.
     * 
     * @param homeDirectory
     */
    public void setHomeDirectory(oracle.bi.webservices.v6.ItemInfo homeDirectory) {
        this.homeDirectory = homeDirectory;
    }


    /**
     * Gets the sharedDirectories value for this SessionEnvironment.
     * 
     * @return sharedDirectories
     */
    public oracle.bi.webservices.v6.ItemInfo[] getSharedDirectories() {
        return sharedDirectories;
    }


    /**
     * Sets the sharedDirectories value for this SessionEnvironment.
     * 
     * @param sharedDirectories
     */
    public void setSharedDirectories(oracle.bi.webservices.v6.ItemInfo[] sharedDirectories) {
        this.sharedDirectories = sharedDirectories;
    }

    public oracle.bi.webservices.v6.ItemInfo getSharedDirectories(int i) {
        return this.sharedDirectories[i];
    }

    public void setSharedDirectories(int i, oracle.bi.webservices.v6.ItemInfo _value) {
        this.sharedDirectories[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SessionEnvironment)) return false;
        SessionEnvironment other = (SessionEnvironment) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName()))) &&
            ((this.homeDirectory==null && other.getHomeDirectory()==null) || 
             (this.homeDirectory!=null &&
              this.homeDirectory.equals(other.getHomeDirectory()))) &&
            ((this.sharedDirectories==null && other.getSharedDirectories()==null) || 
             (this.sharedDirectories!=null &&
              java.util.Arrays.equals(this.sharedDirectories, other.getSharedDirectories())));
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
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        if (getHomeDirectory() != null) {
            _hashCode += getHomeDirectory().hashCode();
        }
        if (getSharedDirectories() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSharedDirectories());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSharedDirectories(), i);
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
        new org.apache.axis.description.TypeDesc(SessionEnvironment.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "SessionEnvironment"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "userName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("homeDirectory");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "homeDirectory"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ItemInfo"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sharedDirectories");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "sharedDirectories"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "ItemInfo"));
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
