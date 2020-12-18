/**
 * DescribeColumnResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;

public class DescribeColumnResult  implements java.io.Serializable {
    private oracle.bi.webservices.v6.SAColumn column;

    public DescribeColumnResult() {
    }

    public DescribeColumnResult(
           oracle.bi.webservices.v6.SAColumn column) {
           this.column = column;
    }


    /**
     * Gets the column value for this DescribeColumnResult.
     * 
     * @return column
     */
    public oracle.bi.webservices.v6.SAColumn getColumn() {
        return column;
    }


    /**
     * Sets the column value for this DescribeColumnResult.
     * 
     * @param column
     */
    public void setColumn(oracle.bi.webservices.v6.SAColumn column) {
        this.column = column;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DescribeColumnResult)) return false;
        DescribeColumnResult other = (DescribeColumnResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.column==null && other.getColumn()==null) || 
             (this.column!=null &&
              this.column.equals(other.getColumn())));
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
        if (getColumn() != null) {
            _hashCode += getColumn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DescribeColumnResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">describeColumnResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("column");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "column"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "SAColumn"));
        elemField.setNillable(false);
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
