/**
 * DescribeSubjectAreaResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package oracle.bi.webservices.v6;

public class DescribeSubjectAreaResult  implements java.io.Serializable {
    private oracle.bi.webservices.v6.SASubjectArea subjectArea;

    public DescribeSubjectAreaResult() {
    }

    public DescribeSubjectAreaResult(
           oracle.bi.webservices.v6.SASubjectArea subjectArea) {
           this.subjectArea = subjectArea;
    }


    /**
     * Gets the subjectArea value for this DescribeSubjectAreaResult.
     * 
     * @return subjectArea
     */
    public oracle.bi.webservices.v6.SASubjectArea getSubjectArea() {
        return subjectArea;
    }


    /**
     * Sets the subjectArea value for this DescribeSubjectAreaResult.
     * 
     * @param subjectArea
     */
    public void setSubjectArea(oracle.bi.webservices.v6.SASubjectArea subjectArea) {
        this.subjectArea = subjectArea;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DescribeSubjectAreaResult)) return false;
        DescribeSubjectAreaResult other = (DescribeSubjectAreaResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.subjectArea==null && other.getSubjectArea()==null) || 
             (this.subjectArea!=null &&
              this.subjectArea.equals(other.getSubjectArea())));
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
        if (getSubjectArea() != null) {
            _hashCode += getSubjectArea().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DescribeSubjectAreaResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", ">describeSubjectAreaResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subjectArea");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "subjectArea"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v6", "SASubjectArea"));
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
